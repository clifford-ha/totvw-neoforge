package cliffordha.totvw.networking;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.networking.packets.ClientPrefsPayload;
import cliffordha.totvw.networking.packets.OpenTetherBlacklistPayload;
import cliffordha.totvw.networking.packets.TetherBlacklistPayload;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = TOTVW.MOD_ID)
public class VWNetworking {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.MAIN);
        registrar.playToServer(ClientPrefsPayload.TYPE, ClientPrefsPayload.STREAM_CODEC, ServerboundPackets::handleClientPrefs);
        registrar.playToServer(TetherBlacklistPayload.TYPE, TetherBlacklistPayload.STREAM_CODEC, ServerboundPackets::handleTetherBlacklistUpdate);
        registrar.playToClient(OpenTetherBlacklistPayload.TYPE, OpenTetherBlacklistPayload.STREAM_CODEC, ClientboundPackets::handleOpenTetherBlacklist);
    }
}
