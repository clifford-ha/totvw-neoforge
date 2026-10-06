package cliffordha.totvw.networking;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.client.VWClientScreens;
import cliffordha.totvw.networking.packets.OpenTetherBlacklistPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@Mod(value = TOTVW.MOD_ID, dist = Dist.CLIENT)
public class ClientboundPackets {
    public static void handleOpenTetherBlacklist(OpenTetherBlacklistPayload payload, IPayloadContext context) {
        VWClientScreens.openTetherBlacklist(payload.entityId());
    }
}
