package cliffordha.totvw.entity.skill;

import cliffordha.totvw.config.VWConfig;
import cliffordha.totvw.registry.VWSounds;
import cliffordha.totvw.registry.attachments.PlayerPrefs;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

import static cliffordha.totvw.util.ExtUtil.playStaticSound;
import static cliffordha.totvw.util.ExtUtil.sendToChat;

public class SkillManager {
    public static void startCooldown(LivingEntity entity, SkillDefinition skill, int duration) {
        if (!VWConfig.get().SERVER_SKILL_COOLDOWNS) return;
        if (entity instanceof Wolf wolf) {
            wolf.setData(skill.cooldown(), duration);
            wolf.setData(skill.notifier(), 1);
        } else if (entity instanceof Player player) {
            player.setData(skill.cooldown(), duration);
            player.setData(skill.notifier(), 1);
        }
    }

    public static boolean isOnCooldown(LivingEntity entity, SkillDefinition skill) {
        return entity.getData(skill.cooldown()) > 0;
    }

    public static void depleteCooldown(LivingEntity entity, Supplier<AttachmentType<Integer>> skillCD) {
        int current = entity.getData(skillCD);
        if (current <= 0) {
            entity.removeData(skillCD);
            return;
        }
        entity.setData(skillCD, current - 1);
    }

    public static void playNotification(LivingEntity entity) {
        if (entity instanceof Player player) {
            if (cannotPlaySound(player)) return;
            playStaticSound(player, VWSounds.NOTIFY.get(), SoundSource.PLAYERS, false);

        } else if (entity instanceof Wolf wolf && wolf.getOwner() instanceof Player player) {
            if (cannotPlaySound(player)) return;
            playStaticSound(player, VWSounds.NOTIFY.get(), SoundSource.PLAYERS, false);
        }
    }
    private static boolean cannotPlaySound(Player player) {
        return !player.getData(PlayerPrefs.ENABLE_NOTIFIERS);
    }

    public static void processCDNotify(LivingEntity entity, SkillDefinition skill) {
        int cd = entity.getData(skill.cooldown());
        int notifyFlag = entity.getData(skill.notifier());

        if (notifyFlag == 1 && cd == 0) {
            if (entity instanceof Wolf wolf) {
                sendToChat(wolf, false, wolf.getPlainTextName() + ": " + skill.skillName() + " is ready!");
            } else if (entity instanceof Player player) {
                sendToChat(player, false, skill.skillName() + " is ready!");
            }
            playStaticSound(entity, VWSounds.NOTIFY.get(), SoundSource.PLAYERS, false);
            entity.removeData(skill.notifier());
        }
    }

    public static void setPlayerConfiguration(Player player) {
        int CD_VERDANT_BLESSING = player.getData(PlayerAttachment.CD_BLESSING_OF_THE_VERDANT_WIND);
        int CD_HAVOC_AMP = player.getData(PlayerAttachment.CD_HAVOC);

        if (CD_VERDANT_BLESSING > 0) player.removeData(PlayerAttachment.CD_BLESSING_OF_THE_VERDANT_WIND);
        if (CD_HAVOC_AMP > 0) player.removeData(PlayerAttachment.CD_HAVOC);
    }

    public static void setPlayerOtherConfiguration(Player player) {
        int COUNTER_VILLAGER_ATROCITY = player.getData(PlayerAttachment.VILLAGER_ATROCITY_COUNT);
        int COUNTER_WOLF_ATROCITY = player.getData(PlayerAttachment.WOLF_ATROCITY_COUNT);

        if (COUNTER_VILLAGER_ATROCITY > 0) player.removeData(PlayerAttachment.CD_BLESSING_OF_THE_VERDANT_WIND);
        if (COUNTER_WOLF_ATROCITY > 0) player.removeData(PlayerAttachment.CD_BLESSING_OF_THE_VERDANT_WIND);
    }

    public static void resetWolfCooldowns(Wolf wolf) {
        wolf.removeData(WolfAttachment.CD_BLESSING_OF_THE_VERDANT_WIND);
        wolf.removeData(WolfAttachment.CD_BLOODLUST_SKILL_PARALYZE);
        wolf.removeData(WolfAttachment.CD_MIGHT_SKILL_RUPTURE);
        wolf.removeData(WolfAttachment.CD_IGNORE_HIGH_DAMAGE);
        wolf.removeData(WolfAttachment.CD_WOLF_FRAGMENT_SUMMON);
    }
}