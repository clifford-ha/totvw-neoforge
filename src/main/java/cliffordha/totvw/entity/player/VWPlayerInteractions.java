package cliffordha.totvw.entity.player;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.networking.packets.OpenTetherBlacklistPayload;
import cliffordha.totvw.item.events.VWItemBlessings;
import cliffordha.totvw.registry.VWEnchantments;
import cliffordha.totvw.registry.VWItems;
import cliffordha.totvw.registry.VWSounds;
import cliffordha.totvw.registry.attachments.Runestone;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import cliffordha.totvw.tag.VWItemTags;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

import static cliffordha.totvw.util.VWUtil.sendToChat;

@EventBusSubscriber(modid = TOTVW.MOD_ID)
public class VWPlayerInteractions {

    @SubscribeEvent
    public static void onInteractEvents(PlayerInteractEvent.EntityInteract interact) {
        if (interact.getEntity() instanceof Player player) {
            interact.setCancellationResult(
                    onEntityInteractEvent(player, interact.getTarget(), interact.getHand())
            );
        }
    }

    @SubscribeEvent
    public static void onRightClickItemEvent(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity() instanceof Player player) {
            event.setCancellationResult(
                    onUseItemEvent(player)
            );
        }
    }

    private static InteractionResult onEntityInteractEvent(Player player, Entity entity, InteractionHand hand) {
        if (player.level().isClientSide()) return InteractionResult.PASS;
        if (!(entity instanceof LivingEntity)) return InteractionResult.PASS;

        ItemStack stack = player.getMainHandItem();
        boolean hasPaper = stack.is(Items.PAPER) || stack.is(VWItems.VERIXIUM_PAPER);

        if (player.isShiftKeyDown() && entity instanceof Wolf wolf
                && wolf.getOwner() == player && Runestone.hasTether(wolf)) {
            if (player instanceof ServerPlayer serverPlayer) {
                ClientPacketDistributor.sendToServer(new OpenTetherBlacklistPayload(wolf.getId()));
            }
            return InteractionResult.SUCCESS;
        }

        if (hasPaper && entity instanceof Player otherPlayer) {
            if (player == otherPlayer) return InteractionResult.PASS;

            AttachmentType<List<Pair<String, UUID>>> T_LIST = PlayerAttachment.TRUSTED_PLAYERS.get();
            List<Pair<String, UUID>> oldPlayerData = player.getData(T_LIST);
            List<Pair<String, UUID>> newPlayerData = new ArrayList<>(oldPlayerData);

            Pair<String, UUID> mobData = PlayerAttachment.getNameAndUUID(otherPlayer);

            boolean hasPriorData = oldPlayerData.stream().anyMatch(data -> data.getB() == mobData.getB());
            if (oldPlayerData.size() >= 4 && !hasPriorData) {
                sendToChat(player, true, "You can only list 4 trusted players at a time.");
                return InteractionResult.FAIL;
            }
            if (hasPriorData) {
                var index = oldPlayerData.stream().findFirst().filter(data -> data.getB() == mobData.getB()).map(oldPlayerData::indexOf).orElse(null);
                if (index == null) return InteractionResult.PASS;

                Pair<String, UUID> data = oldPlayerData.get(index);

                if (!data.getA().equals(mobData.getA()) && data.getB() == mobData.getB()) {
                    Pair<String, UUID> update = new Pair<>(mobData.getA(), mobData.getB());

                    newPlayerData.set(newPlayerData.indexOf(data), update);
                    player.setData(T_LIST, List.copyOf(newPlayerData));
                    sendToChat(player, true, "Updated name for " + data.getA() + " to " + update.getA() + ".");
                    return InteractionResult.SUCCESS;

                } else {
                    sendToChat(player, true, "You already trust " + mobData.getA());
                    return InteractionResult.FAIL;
                }
            } else {
                newPlayerData.add(mobData);
                player.setData(PlayerAttachment.TRUSTED_PLAYERS, List.copyOf(newPlayerData));

                sendToChat(player, false, otherPlayer.getPlainTextName() + " is now a trusted player!");
                player.playSound(VWSounds.NOTIFY.get());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    private static InteractionResult onUseItemEvent(Player player) {
        if (player.level().isClientSide()) return InteractionResult.PASS;

        ItemStack mainHand = player.getMainHandItem();
        boolean BENEDICTION_SUPPORTED_ITEMS = (mainHand.tags().anyMatch(Predicate.isEqual(VWItemTags.BENEDICTION_ENCHANTMENT_USE_QUALIFIED_TOOLS))
                || mainHand.tags().anyMatch(Predicate.isEqual(VWItemTags.BENEDICTION_ENCHANTMENT_USE_QUALIFIED_ITEMS)));

        boolean HAS_BENEDICTION  = VWEnchantments.getBenediction(player);

        if (BENEDICTION_SUPPORTED_ITEMS && HAS_BENEDICTION && player.isCrouching()) {
            boolean applied = VWItemBlessings.tryApply(player);
            if (applied) return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.PASS;
    }
}
