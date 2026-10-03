package cliffordha.totvw.networking;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.config.VWConfig;
import cliffordha.totvw.networking.packets.ClientPrefsPayload;
import cliffordha.totvw.networking.packets.OpenTetherBlacklistPayload;
import cliffordha.totvw.networking.packets.TetherBlacklistPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = TOTVW.MOD_ID)
public class VWNetworking {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent registry) {
        PayloadRegistrar registrar = registry.registrar("1");

        registrar.playToClient(OpenTetherBlacklistPayload.TYPE, OpenTetherBlacklistPayload.STREAM_CODEC);

        registrar.playToServer(ClientPrefsPayload.TYPE, ClientPrefsPayload.STREAM_CODEC, ServerboundPackets::handleClientPrefs);
        registrar.playToServer(TetherBlacklistPayload.TYPE, TetherBlacklistPayload.STREAM_CODEC, ServerboundPackets::handleTetherBlacklistUpdate);
    }

    public static void sendPrefs() {
        ClientPacketDistributor.sendToServer(
                new ClientPrefsPayload(
                        VWConfig.get().CLIENT_SHOW_ATROCITY_COUNTER,
                        VWConfig.get().CLIENT_ENABLE_NOTIFIERS,

                        VWConfig.get().SERVER_BENEDICTION_HEALTH_THRESHOLD,
                        VWConfig.get().SERVER_WOLF_SHARES_BENEDICTION_STACK,
                        VWConfig.get().SERVER_ALWAYS_TRIGGER_BLESSING,
                        VWConfig.get().SERVER_TELEPORT_AFTER_SAVE,
                        VWConfig.get().SERVER_WOLF_TP_METHOD,
                        VWConfig.get().SERVER_PLAYER_TP_METHOD,
                        VWConfig.get().SERVER_WOLF_TP_ALL
        ));
    }
    public static void onPlayerJoinEvents(ClientPlayerNetworkEvent.LoggingIn event) {
        sendPrefs();
    }
}
