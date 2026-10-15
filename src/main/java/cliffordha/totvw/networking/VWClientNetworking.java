package cliffordha.totvw.networking;

import cliffordha.totvw.ClientConfig;
import cliffordha.totvw.TOTVW;
import cliffordha.totvw.networking.packets.ClientPrefsPayload;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@Mod(value = TOTVW.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = TOTVW.MOD_ID, value = Dist.CLIENT)
public class VWClientNetworking {

    @SubscribeEvent
    public static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        sendPrefs();
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() != ClientConfig.CLIENT) return;
        sendPrefs();
    }

    private static void sendPrefs() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.getConnection() == null || mc.player == null) return; // not in a world yet

        ClientPacketDistributor.sendToServer(new ClientPrefsPayload(
                ClientConfig.SHOW_ATROCITY_COUNTER.get(),
                ClientConfig.ENABLE_NOTIFIERS.get(),
                ClientConfig.BENEDICTION_HEALTH_THRESHOLD.get(),
                ClientConfig.WOLF_SHARES_BENEDICTION_STACK.get(),
                ClientConfig.ALWAYS_TRIGGER_BLESSING.get(),
                ClientConfig.TELEPORT_AFTER_SAVE.get(),
                ClientConfig.WOLF_TP_METHOD.get(),
                ClientConfig.PLAYER_TP_METHOD.get(),
                ClientConfig.WOLF_TP_ALL.get(),
                ClientConfig.LOG_ENCHANTMENT_SHOW_WOLF_CD.get(),
                ClientConfig.LOG_ENCHANTMENT_SHOW_PLAYER_CD.get()
        ));
    }
}
