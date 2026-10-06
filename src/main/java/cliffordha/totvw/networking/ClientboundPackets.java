package cliffordha.totvw.networking;

import cliffordha.totvw.client.screen.TetherBlacklistScreen;
import cliffordha.totvw.networking.packets.OpenTetherBlacklistPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientboundPackets {
    public static void handleOpenTetherBlacklist(OpenTetherBlacklistPayload payload, IPayloadContext context) {
        Minecraft.getInstance().setScreenAndShow(new TetherBlacklistScreen(payload.entityId()));
    }
}
