package cliffordha.totvw.networking;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.config.VWConfig;
import cliffordha.totvw.networking.packets.ClientPrefsPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

@Mod(value = TOTVW.MOD_ID)
@EventBusSubscriber(modid = TOTVW.MOD_ID, value = Dist.CLIENT)
public class VWClientNetworking {

    @SubscribeEvent
    public static void onLogin(ClientPlayerNetworkEvent.LoggingIn event) {
        sendPrefs();
    }

    public static void sendPrefs() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.getConnection() == null || mc.player == null) return; // not in a world yet

        VWConfig c = VWConfig.get();
        ClientPacketDistributor.sendToServer(new ClientPrefsPayload(
                c.SYNCED_SHOW_ATROCITY_COUNTER,
                c.SYNCED_ENABLE_NOTIFIERS,
                c.SYNCED_SHOW_WOLF_EFFECT_INDICATORS,

                c.SYNCED_BENEDICTION_HEALTH_THRESHOLD,
                c.SYNCED_WOLF_SHARES_BENEDICTION_STACK,
                c.SYNCED_ALWAYS_TRIGGER_BLESSING,
                c.SYNCED_TELEPORT_AFTER_SAVE,
                c.SYNCED_WOLF_TP_METHOD,
                c.SYNCED_PLAYER_TP_METHOD,
                c.SYNCED_WOLF_TP_ALL
        ));
    }
}
