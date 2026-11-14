package cliffordha.totvw.networking;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.config.VWConfig;
import cliffordha.totvw.entity.WolfBenedictionHelper;
import cliffordha.totvw.networking.packets.*;
import cliffordha.totvw.registry.VWColors;
import cliffordha.totvw.registry.attachments.*;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

import static cliffordha.totvw.util.ExtUtil.sendToChat;

@EventBusSubscriber(modid = TOTVW.MOD_ID)
public class ServerboundPackets {

    @SubscribeEvent
    public static void onPlayerJoinEvents(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ServerLevel level = player.level();

        level.getServer().execute(() -> sendPrefs(player));
        if (TOTVW.VERSION.contains("beta") || TOTVW.IN_DEVELOPMENT) {
            sendToChat(player, VWColors.DEFAULT, false, "TOTVW mod version is a development build.");
            sendToChat(player, VWColors.DEFAULT, false, "Bugs and potential crashes are to be expected");

        }

        if (WolfBenedictionHelper.loadWolfFiles(player, level)) {
            WolfBenedictionHelper.deleteLoadedWolfFiles(player, level);
        }
    }

    public static void sendPrefs(Player player) {
        player.setData(PlayerPrefs.SHOW_ATROCITY_COUNTER, VWConfig.get().SYNCED_SHOW_ATROCITY_COUNTER);
        player.setData(PlayerPrefs.ENABLE_NOTIFIERS, VWConfig.get().SYNCED_ENABLE_NOTIFIERS);

        player.setData(PlayerPrefs.BENEDICTION_HEALTH_THRESHOLD, VWConfig.get().SYNCED_BENEDICTION_HEALTH_THRESHOLD);
        player.setData(PlayerPrefs.BENEDICTION_SHARE_STACK, VWConfig.get().SYNCED_WOLF_SHARES_BENEDICTION_STACK);
        player.setData(PlayerPrefs.BENEDICTION_ALWAYS_TRIGGER_BLESSING, VWConfig.get().SYNCED_ALWAYS_TRIGGER_BLESSING);
        player.setData(PlayerPrefs.BENEDICTION_TELEPORT_AFTER_SAVE, VWConfig.get().SYNCED_TELEPORT_AFTER_SAVE);
        player.setData(PlayerPrefs.BENEDICTION_WOLF_TP_METHOD, VWConfig.get().SYNCED_WOLF_TP_METHOD);
        player.setData(PlayerPrefs.BENEDICTION_PLAYER_TP_METHOD, VWConfig.get().SYNCED_PLAYER_TP_METHOD);
        player.setData(PlayerPrefs.BENEDICTION_WOLF_TP_ALL, VWConfig.get().SYNCED_WOLF_TP_ALL);
    }

    public static void handleClientPrefs(ClientPrefsPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) return;
        player.setData(PlayerPrefs.ENABLE_NOTIFIERS, payload.enableNotifiers());
        player.setData(PlayerPrefs.SHOW_ATROCITY_COUNTER, payload.showAtrocityCounter());
        player.setData(PlayerPrefs.BENEDICTION_HEALTH_THRESHOLD, Mth.clamp(payload.benedictionLowHealthThreshold(), 15, 90));
        player.setData(PlayerPrefs.BENEDICTION_SHARE_STACK, payload.benedictionShareStack());
        player.setData(PlayerPrefs.BENEDICTION_ALWAYS_TRIGGER_BLESSING, payload.benedictionAlwaysTriggerBlessing());
        player.setData(PlayerPrefs.BENEDICTION_TELEPORT_AFTER_SAVE, payload.benedictionTeleportAfterSave());
        player.setData(PlayerPrefs.BENEDICTION_WOLF_TP_METHOD, Mth.clamp(payload.benedictionWolfTPMethod(), 0, 1));
        player.setData(PlayerPrefs.BENEDICTION_PLAYER_TP_METHOD, Mth.clamp(payload.benedictionPlayerTPMethod(), 0, 1));
        player.setData(PlayerPrefs.BENEDICTION_WOLF_TP_ALL, payload.benedictionWolfTPAll());
    }

    public static void handleTetherData(TetherDataPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) return;
        player.level().getServer().execute(() -> {
            if (!(player.level().getEntity(payload.entityId()) instanceof Wolf wolf)) return;
            if (wolf.getOwner() != player || player.distanceTo(wolf) > 8 || !Runestone.hasTether(wolf)) return;

            TetherData data = wolf.getData(WolfAttachment.TETHER_DATA);
            List<String> newBlacklist = new ArrayList<>();
            for (String type : payload.tetherData().blacklistedEntities()) {
                Identifier id = Identifier.tryParse(type);
                if (id == null || !BuiltInRegistries.ENTITY_TYPE.containsKey(id)) return;
                newBlacklist.add(id.toString());
            }
            List<String> tethered = new ArrayList<>(data.tetheredEntities());
            tethered.removeIf(newBlacklist::contains);
            wolf.setData(WolfAttachment.TETHER_DATA, new TetherData(
                    List.copyOf(tethered), List.copyOf(newBlacklist),
                    payload.tetherData().autoAttackTethered(),
                    payload.tetherData().tetherChunkTicks()));
        });
    }

    public static void handleWolfEvents(WolfEventsPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) return;
        player.level().getServer().execute(() -> {
            if (!(player.level().getEntity(payload.integerFlags().getFirst()) instanceof Wolf wolf)) return;
            if (wolf.getOwner() != player || player.distanceTo(wolf) > 12) return;

            ReturnPointData data = wolf.getData(WolfAttachment.RETURN_POINT);

            int type = payload.integerFlags().get(1);
            switch (type) {
                case WolfEventsPayload.DROP_RUNESTONE_FROM_SHEARED -> {
                    Runestone.dropRunestone(wolf, player);
                }
                case WolfEventsPayload.CHANGE_BENEDICTION_RETURN_METHOD -> {
                    wolf.setData(WolfAttachment.RETURN_POINT, ReturnPointData.changeReturnMethod(data, !data.recordAfter()));
                }
                case WolfEventsPayload.REMOVE_RETURN_POINT -> {
                    wolf.setData(WolfAttachment.RETURN_POINT, ReturnPointData.removeReturnPoint(data));
                }
                default -> {}
            }
        });
    }

    public static void handleAllianceProcess(ProcessAlliancePayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) return;
        player.level().getServer().execute(() -> {
            if (payload.uuid().equals(AttachmentUtil.EMPTY_UUID)) {
                player.setData(PlayerAttachment.BLOCK_ALLIANCE_REQUEST, payload.disableRequests());
                return;
            }

            ServerPlayer other = null;
            for (Level level : player.level().getServer().getAllLevels()) {
                other = level.getServer().getPlayerList().getPlayer(payload.uuid());
                break;
            }

            if (payload.remove()) {
                PlayerAttachment.removeTrustedPlayer(player, payload.uuid());
                if (other != null) {
                    PlayerAttachment.removeTrustedPlayer(other, player);
                }
                return;
            }
            if (other == null) {
                sendToChat(player, VWColors.DEFAULT, false, "Player not found");
                return;
            }
            PlayerAttachment.addTrustedPlayer(player, other);
            PlayerAttachment.addTrustedPlayer(other, player);
            sendToChat(player, false, "You now trust " + other.getPlainTextName());
            sendToChat(other, false, "You now trust " + player.getPlainTextName());
        });
    }
}
