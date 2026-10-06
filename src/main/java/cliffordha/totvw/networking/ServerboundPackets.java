package cliffordha.totvw.networking;

import cliffordha.totvw.ClientConfig;
import cliffordha.totvw.TOTVW;
import cliffordha.totvw.networking.packets.ClientPrefsPayload;
import cliffordha.totvw.networking.packets.TetherBlacklistPayload;
import cliffordha.totvw.registry.attachments.ClientPref;
import cliffordha.totvw.registry.attachments.Runestone;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

@Mod(value = TOTVW.MOD_ID, dist = Dist.DEDICATED_SERVER)
@EventBusSubscriber(modid = TOTVW.MOD_ID, value = Dist.DEDICATED_SERVER)
public class ServerboundPackets {
    public static void handleClientPrefs(ClientPrefsPayload payload, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) context.player();
        player.setData(ClientPref.ENABLE_NOTIFIERS, payload.enableNotifiers());
        player.setData(ClientPref.SHOW_ATROCITY_COUNTER, payload.showAtrocityCounter());
        player.setData(ClientPref.BENEDICTION_HEALTH_THRESHOLD, Mth.clamp(payload.benedictionLowHealthThreshold(), 15, 90));
        player.setData(ClientPref.BENEDICTION_SHARE_STACK, payload.benedictionShareStack());
        player.setData(ClientPref.BENEDICTION_ALWAYS_TRIGGER_BLESSING, payload.benedictionAlwaysTriggerBlessing());
        player.setData(ClientPref.BENEDICTION_TELEPORT_AFTER_SAVE, payload.benedictionTeleportAfterSave());
        player.setData(ClientPref.BENEDICTION_WOLF_TP_METHOD, Mth.clamp(payload.benedictionWolfTPMethod(), 0, 1));
        player.setData(ClientPref.BENEDICTION_PLAYER_TP_METHOD, Mth.clamp(payload.benedictionPlayerTPMethod(), 0, 1));
        player.setData(ClientPref.BENEDICTION_WOLF_TP_ALL, payload.benedictionWolfTPAll());

        player.setData(ClientPref.SHOW_WOLF_LOG, payload.showWolfLog());
        player.setData(ClientPref.SHOW_PLAYER_LOG, payload.showPlayerLog());
    }
    public static void handleTetherBlacklistUpdate(TetherBlacklistPayload payload, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) context.player();

        if (!(player.level().getEntity(payload.entityId()) instanceof Wolf wolf)) return;
        if (wolf.getOwner() != player || player.distanceTo(wolf) > 8 || !Runestone.hasTether(wolf)) return;

        Identifier id = Identifier.tryParse(payload.entityTypeId());
        if (id == null || !BuiltInRegistries.ENTITY_TYPE.containsKey(id)) return;

        List<String> list = new ArrayList<>(WolfAttachment.getTetherBlacklist(wolf));
        String key = id.toString();
        if (payload.blacklisted()) {
            if (!list.contains(key) && list.size() < 64) list.add(key);
        } else {
            list.remove(key);
        }
        wolf.setData(WolfAttachment.TETHER_ENTITY_BLACKLIST, list);
    }

    @SubscribeEvent
    public static void onPlayerJoinEvents(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player.level() instanceof ServerLevel level) {
            level.getServer().execute(() -> sendPrefs(player));
        }
    }

    public static void sendPrefs(Player player) {
        player.setData(ClientPref.SHOW_ATROCITY_COUNTER, ClientConfig.CLIENT_SHOW_ATROCITY_COUNTER.get());
        player.setData(ClientPref.ENABLE_NOTIFIERS, ClientConfig.CLIENT_ENABLE_NOTIFIERS.get());

        player.setData(ClientPref.BENEDICTION_HEALTH_THRESHOLD, ClientConfig.SERVER_BENEDICTION_HEALTH_THRESHOLD.get());
        player.setData(ClientPref.BENEDICTION_SHARE_STACK, ClientConfig.SERVER_WOLF_SHARES_BENEDICTION_STACK.get());
        player.setData(ClientPref.BENEDICTION_ALWAYS_TRIGGER_BLESSING, ClientConfig.SERVER_ALWAYS_TRIGGER_BLESSING.get());
        player.setData(ClientPref.BENEDICTION_TELEPORT_AFTER_SAVE, ClientConfig.SERVER_TELEPORT_AFTER_SAVE.get());
        player.setData(ClientPref.BENEDICTION_WOLF_TP_METHOD, ClientConfig.SERVER_WOLF_TP_METHOD.get());
        player.setData(ClientPref.BENEDICTION_PLAYER_TP_METHOD, ClientConfig.SERVER_PLAYER_TP_METHOD.get());
        player.setData(ClientPref.BENEDICTION_WOLF_TP_ALL, ClientConfig.SERVER_WOLF_TP_ALL.get());
    }
}
