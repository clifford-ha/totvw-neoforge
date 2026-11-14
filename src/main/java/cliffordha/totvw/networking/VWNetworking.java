package cliffordha.totvw.networking;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.networking.packets.*;
import cliffordha.totvw.registry.attachments.ArmorData;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.List;

@EventBusSubscriber(modid = TOTVW.MOD_ID)
public class VWNetworking {
    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1").executesOn(HandlerThread.MAIN);
        registrar.playToServer(ClientPrefsPayload.TYPE, ClientPrefsPayload.STREAM_CODEC, ServerboundPackets::handleClientPrefs);
        registrar.playToServer(TetherDataPayload.TYPE, TetherDataPayload.STREAM_CODEC, ServerboundPackets::handleTetherData);
        registrar.playToServer(WolfEventsPayload.TYPE, WolfEventsPayload.STREAM_CODEC, ServerboundPackets::handleWolfEvents);
        registrar.playToServer(ProcessAlliancePayload.TYPE, ProcessAlliancePayload.STREAM_CODEC, ServerboundPackets::handleAllianceProcess);

        registrar.playToClient(OpenTetherManagerPayload.TYPE, OpenTetherManagerPayload.STREAM_CODEC, ClientboundPackets::handleOpenTetherManager);
        registrar.playToClient(MobEffectInstancePayload.TYPE, MobEffectInstancePayload.STREAM_CODEC, ClientboundPackets::handleMobEffectInstance);
        registrar.playToClient(WolfDataPayload.TYPE, WolfDataPayload.STREAM_CODEC, ClientboundPackets::handleWolfData);
        registrar.playToClient(PlayerDataPayload.TYPE, PlayerDataPayload.STREAM_CODEC, ClientboundPackets::handlePlayerData);
        registrar.playToClient(ConfirmAlliance.TYPE, ConfirmAlliance.STREAM_CODEC, ClientboundPackets::handleAllianceConfirmation);
    }

    public static void sendPacket2S(CustomPacketPayload payload) {
        ClientPacketDistributor.sendToServer(payload);
    }
    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }
    public static void syncPlayerArmor(Player player) {
        if (player instanceof ServerPlayer sp) {
            sp.setData(PlayerAttachment.ARMOR_DATA, ArmorData.of(sp));
            sendToPlayer(sp, new PlayerDataPayload(ArmorData.of(sp), PlayerAttachment.getArmorList(player)));
        }
    }

    public static void syncWolfArmor(Wolf wolf) {
        if (!(wolf.getOwner() instanceof ServerPlayer owner)) return;
        ItemStack armor = wolf.getBodyArmorItem();
        AttributeMap map = wolf.getAttributes();
        ArmorData data = armor.isEmpty() ? ArmorData.NONE : new ArmorData(
                armor.getItemName(), ArmorData.getID(armor),
                armor.getMaxDamage(), armor.getDamageValue(),
                EquipmentSlot.BODY,
                map.getBaseValue(Attributes.ATTACK_DAMAGE),
                map.getValue(Attributes.ATTACK_DAMAGE));

        sendToPlayer(owner, new WolfDataPayload(wolf.getId(), data));
    }
    public static void syncWolfEffects(Wolf wolf) {
        if (!(wolf.getOwner() instanceof ServerPlayer owner)) return;
        sendToPlayer(owner, new MobEffectInstancePayload(wolf.getId(), List.copyOf(wolf.getActiveEffects())));
    }
    public static void syncPlayerEffects(Player player) {
        if (!(player instanceof ServerPlayer sp)) return;
        sendToPlayer(sp, new MobEffectInstancePayload(sp.getId(), List.copyOf(sp.getActiveEffects())));
    }
}
