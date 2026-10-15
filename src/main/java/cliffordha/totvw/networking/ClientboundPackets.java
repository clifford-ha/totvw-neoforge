package cliffordha.totvw.networking;

import cliffordha.totvw.client.VWClientScreens;
import cliffordha.totvw.networking.packets.OpenTetherBlacklistPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientboundPackets {
    public static void handleOpenTetherBlacklist(OpenTetherBlacklistPayload payload, IPayloadContext context) {
        VWClientScreens.openTetherBlacklist(payload.entityId());
    }
}
