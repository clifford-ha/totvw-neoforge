package cliffordha.totvw.networking;

import cliffordha.totvw.networking.packets.ClientPrefsPayload;
import cliffordha.totvw.networking.packets.TetherBlacklistPayload;
import cliffordha.totvw.registry.attachments.PlayerPrefs;
import cliffordha.totvw.registry.attachments.Runestone;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public class ServerboundPackets {
    public static void handleClientPrefs(ClientPrefsPayload payload, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) context.player();
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
}
