package cliffordha.totvw.networking;

import cliffordha.totvw.client.ClientScreens;
import cliffordha.totvw.networking.packets.*;
import cliffordha.totvw.registry.attachments.VWAttachment;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;

public class ClientboundPackets {
    public static void handleOpenTetherManager(OpenTetherManagerPayload payload, IPayloadContext context) {
        ClientScreens.openTetherManagerScreen(payload);
    }

    public static void handleMobEffectInstance(MobEffectInstancePayload payload, IPayloadContext context) {
        if (level(context).getEntity(payload.entityId()) instanceof Wolf wolf) {
            wolf.setData(VWAttachment.MOB_EFFECTS, new ArrayList<>(payload.instance()));
        }
    }

    public static void handleWolfData(WolfDataPayload payload, IPayloadContext context) {
        if (level(context).getEntity(payload.wolfId()) instanceof Wolf wolf) {
            if (payload.armorData().isEmpty()) wolf.removeData(WolfAttachment.ARMOR_DATA);
            else wolf.setData(WolfAttachment.ARMOR_DATA, payload.armorData());
        }
    }

    public static void handlePlayerData(PlayerDataPayload payload, IPayloadContext context) {
        context.player().setData(PlayerAttachment.EQUIPMENTS, new ArrayList<>(payload.equipments()));
    }

    public static void handleAllianceConfirmation(ConfirmAlliance payload, IPayloadContext context) {
        ClientScreens.openAllianceScreen(payload, context);
    }

    private static Level level(IPayloadContext context) {
        return context.player().level();
    }
}
