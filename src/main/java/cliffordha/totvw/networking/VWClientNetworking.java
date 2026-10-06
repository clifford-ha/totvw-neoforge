package cliffordha.totvw.networking;

import cliffordha.totvw.ClientConfig;
import cliffordha.totvw.TOTVW;
import cliffordha.totvw.networking.packets.ClientPrefsPayload;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@Mod(value = TOTVW.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = TOTVW.MOD_ID, value = Dist.CLIENT)
public class VWClientNetworking {

    @SubscribeEvent
    public static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        ClientPacketDistributor.sendToServer(new ClientPrefsPayload(
                ClientConfig.CLIENT_SHOW_ATROCITY_COUNTER.get(),
                ClientConfig.CLIENT_ENABLE_NOTIFIERS.get(),
                ClientConfig.SERVER_BENEDICTION_HEALTH_THRESHOLD.get(),
                ClientConfig.SERVER_WOLF_SHARES_BENEDICTION_STACK.get(),
                ClientConfig.SERVER_ALWAYS_TRIGGER_BLESSING.get(),
                ClientConfig.SERVER_TELEPORT_AFTER_SAVE.get(),
                ClientConfig.SERVER_WOLF_TP_METHOD.get(),
                ClientConfig.SERVER_PLAYER_TP_METHOD.get(),
                ClientConfig.SERVER_WOLF_TP_ALL.get(),

                ClientConfig.LOG_ENCHANTMENT_SHOW_WOLF_CD.get(),
                ClientConfig.LOG_ENCHANTMENT_SHOW_PLAYER_CD.get()
        ));
    }
}
