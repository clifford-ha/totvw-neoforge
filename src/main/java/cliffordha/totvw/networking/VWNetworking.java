package cliffordha.totvw.networking;

import cliffordha.totvw.Config;
import cliffordha.totvw.TOTVW;
import cliffordha.totvw.networking.packets.ClientPrefsPayload;
import cliffordha.totvw.networking.packets.OpenTetherBlacklistPayload;
import cliffordha.totvw.networking.packets.TetherBlacklistPayload;
import cliffordha.totvw.registry.attachments.PlayerPrefs;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = TOTVW.MOD_ID)
public class VWNetworking {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(ClientPrefsPayload.TYPE, ClientPrefsPayload.STREAM_CODEC, ServerboundPackets::handleClientPrefs);
        registrar.playToServer(TetherBlacklistPayload.TYPE, TetherBlacklistPayload.STREAM_CODEC, ServerboundPackets::handleTetherBlacklistUpdate);
        registrar.playToClient(OpenTetherBlacklistPayload.TYPE, OpenTetherBlacklistPayload.STREAM_CODEC, ClientboundPackets::handleOpenTetherBlacklist);
    }

    public static void sendPrefs(Player player) {
        player.setData(PlayerPrefs.SHOW_ATROCITY_COUNTER, Config.CLIENT_SHOW_ATROCITY_COUNTER.get());
        player.setData(PlayerPrefs.ENABLE_NOTIFIERS, Config.CLIENT_ENABLE_NOTIFIERS.get());

        player.setData(PlayerPrefs.BENEDICTION_HEALTH_THRESHOLD, Config.SERVER_BENEDICTION_HEALTH_THRESHOLD.get());
        player.setData(PlayerPrefs.BENEDICTION_SHARE_STACK, Config.SERVER_WOLF_SHARES_BENEDICTION_STACK.get());
        player.setData(PlayerPrefs.BENEDICTION_ALWAYS_TRIGGER_BLESSING, Config.SERVER_ALWAYS_TRIGGER_BLESSING.get());
        player.setData(PlayerPrefs.BENEDICTION_TELEPORT_AFTER_SAVE, Config.SERVER_TELEPORT_AFTER_SAVE.get());
        player.setData(PlayerPrefs.BENEDICTION_WOLF_TP_METHOD, Config.SERVER_WOLF_TP_METHOD.get());
        player.setData(PlayerPrefs.BENEDICTION_PLAYER_TP_METHOD, Config.SERVER_PLAYER_TP_METHOD.get());
        player.setData(PlayerPrefs.BENEDICTION_WOLF_TP_ALL, Config.SERVER_WOLF_TP_ALL.get());
    }

    @SubscribeEvent
    public static void onPlayerJoinEvents(ClientPlayerNetworkEvent.LoggingIn event) {
        Player player = event.getPlayer();
        if (player.level() instanceof ServerLevel level) {
            level.getServer().execute(() -> sendPrefs(player));
        }
    }
}
