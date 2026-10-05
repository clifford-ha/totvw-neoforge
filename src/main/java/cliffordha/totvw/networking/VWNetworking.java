package cliffordha.totvw.networking;

import cliffordha.totvw.Config;
import cliffordha.totvw.TOTVW;
import cliffordha.totvw.networking.packets.ClientPrefsPayload;
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

        //registrar.playToClient(OpenTetherBlacklistPayload.TYPE, OpenTetherBlacklistPayload.STREAM_CODEC);

        //registrar.playToServer(ClientPrefsPayload.TYPE, ClientPrefsPayload.STREAM_CODEC, ServerboundPackets::handleClientPrefs);
        //registrar.playToServer(TetherBlacklistPayload.TYPE, TetherBlacklistPayload.STREAM_CODEC, ServerboundPackets::handleTetherBlacklistUpdate);
    }

    public static void sendPrefs() {
        ClientPacketDistributor.sendToServer(
                new ClientPrefsPayload(
                        Config.CLIENT_SHOW_ATROCITY_COUNTER.get(),
                        Config.CLIENT_ENABLE_NOTIFIERS.get(),

                        Config.SERVER_BENEDICTION_HEALTH_THRESHOLD.get(),
                        Config.SERVER_WOLF_SHARES_BENEDICTION_STACK.get(),
                        Config.SERVER_ALWAYS_TRIGGER_BLESSING.get(),
                        Config.SERVER_TELEPORT_AFTER_SAVE.get(),
                        Config.SERVER_WOLF_TP_METHOD.get(),
                        Config.SERVER_PLAYER_TP_METHOD.get(),
                        Config.SERVER_WOLF_TP_ALL.get()
        ));
    }
    public static void onPlayerJoinEvents(ClientPlayerNetworkEvent.LoggingIn event) {
        sendPrefs();
    }
}
