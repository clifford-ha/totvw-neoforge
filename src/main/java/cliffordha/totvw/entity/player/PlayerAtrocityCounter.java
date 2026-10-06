package cliffordha.totvw.entity.player;

import cliffordha.totvw.registry.VWColors;
import cliffordha.totvw.registry.attachments.ClientPref;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;

import static cliffordha.totvw.util.VWUtil.sendToChat;
import static cliffordha.totvw.util.VWUtil.setDifficultyBasedValue;

public class PlayerAtrocityCounter {
    public static void processAtrocity(Player player, LivingEntity victim, boolean death) {
        Level level = player.level();

        float multiplier = setDifficultyBasedValue(level, 0.5f, 0.75f, 1.0f, 2.0f);
        int maybeAddMore = level.getRandom().nextIntBetweenInclusive(0, 3);
        int deduction = Mth.ceil(3 * multiplier) + maybeAddMore;
        int finalDeduction = death ? deduction * 4 : deduction;

        ServerPlayer serverPlayer = (ServerPlayer) player;

        if (victim instanceof Wolf wolf) {
            AttachmentType<Integer> WOLF_COUNTER = PlayerAttachment.WOLF_ATROCITY_COUNT.get();
            boolean maybeForgive = wolf.getOwner() != null && wolf.getOwner().is(player) && level.getRandom().nextBoolean();
            if (maybeForgive) return;

            int current = player.getData(WOLF_COUNTER);
            player.setData(WOLF_COUNTER, current + finalDeduction);

            showAtrocityCounter(serverPlayer, wolf, player.getData(WOLF_COUNTER));
        } else if (victim instanceof Villager || victim instanceof WanderingTrader) {
            AttachmentType<Integer> VILLAGER_COUNTER = PlayerAttachment.VILLAGER_ATROCITY_COUNT.get();

            int current = player.getData(VILLAGER_COUNTER);
            player.setData(VILLAGER_COUNTER, current + finalDeduction);

            showAtrocityCounter(serverPlayer, victim, player.getData(VILLAGER_COUNTER));
        }
    }
    private static void showAtrocityCounter(Player player, LivingEntity victim, int count) {
        if (!player.getData(ClientPref.SHOW_ATROCITY_COUNTER)) return;
        if (victim instanceof Wolf) {
            sendToChat(player, VWColors.BLOODLUST_EFFECT_MUTED, true, "Wolf atrocity count: " + count);
        } else if (victim instanceof Villager || victim instanceof WanderingTrader) {
            sendToChat(player, VWColors.BLOODLUST_EFFECT_MUTED, true, "Villager atrocity count: " + count);
        }
    }
}
