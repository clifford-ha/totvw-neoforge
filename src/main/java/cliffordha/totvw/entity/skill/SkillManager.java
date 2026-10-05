package cliffordha.totvw.entity.skill;

import cliffordha.totvw.Config;
import cliffordha.totvw.registry.VWSounds;
import cliffordha.totvw.registry.attachments.PlayerPrefs;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;
import cliffordha.totvw.util.VWUtil;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

import static cliffordha.totvw.util.VWUtil.sendToChat;

public class SkillManager {
    public static void startCooldown(Wolf wolf, WolfSkillDefinition skill, int duration) {
        if (!Config.SERVER_SKILL_COOLDOWNS.get()) return;
        wolf.setData(skill.cooldown(), duration);
        wolf.setData(skill.notifier(), 1);
    }
    public static void startCooldown(Player player, PlayerSkillDefinition skill, int duration) {
        if (!Config.SERVER_SKILL_COOLDOWNS.get()) return;
        player.setData(skill.cooldown(), duration);
        player.setData(skill.notifier(), 1);
    }

    public static boolean isOnCooldown(LivingEntity entity, WolfSkillDefinition skill) {
        return entity.getData(skill.cooldown()) > 0;
    }

    public static void notifyReset(Wolf wolf, WolfSkillDefinition skill) {
        int cooldown = wolf.getData(skill.cooldown());
        int notify = wolf.getData(skill.notifier());
        if (cooldown <= 0 && notify == 1) {
            wolf.setData(skill.notifier(), 0);
            sendToChat(wolf, skill.notifierColor(), true, name(wolf) + skill.skillName() + " is ready!");
        }
    }
    public static void notifyReset(Player player, PlayerSkillDefinition skill) {
        int cooldown = player.getData(skill.cooldown());
        int notify = player.getData(skill.notifier());
        if (cooldown <= 0 && notify == 1) {
            player.setData(skill.notifier(), 0);
            sendToChat(player, skill.notifierColor(), true, name(player) + skill.skillName() + " is ready!");
        }
    }

    private static String name(LivingEntity entity) {
        if (entity instanceof Player player) {
            return player.getName().getString() + ": ";
        } else if (entity instanceof Wolf wolf) {
            return wolf.getName().getString() + ": ";
        }
        return "Invalid Entity";
    }

    public static void depleteCooldown(LivingEntity entity, Supplier<AttachmentType<Integer>> skillCD) {
        int current = entity.getData(skillCD);
        if (current <= 0) return;
        entity.setData(skillCD, current - 1);
    }

    public static void playNotification(LivingEntity entity) {
        if (entity instanceof Wolf wolf && wolf.getOwner() instanceof Player player) {
            if (cannotPlaySound(player)) return;
            player.level().playSound(null, player.blockPosition(), VWSounds.NOTIFY.get(), SoundSource.PLAYERS);
        } else if (entity instanceof Player player) {
            if (cannotPlaySound(player)) return;
            player.level().playSound(null, player.blockPosition(), VWSounds.NOTIFY.get(), SoundSource.PLAYERS);
        }
    }
    private static boolean cannotPlaySound(Player player) {
        return !player.getData(PlayerPrefs.ENABLE_NOTIFIERS);
    }

    public static void processCDNotify(LivingEntity entity, Supplier<AttachmentType<Integer>> cooldown, Supplier<AttachmentType<Integer>> notify, int color, String... msg) {
        int cd = entity.getData(cooldown);
        int notifyFlag = entity.getData(notify);

        if (notifyFlag == 1 && cd == 0) {
            if (entity instanceof Wolf wolf) {
                sendToChat(wolf, color, msg);
            } else if (entity instanceof Player player) {
                sendToChat(player, color, msg);
            }
            VWUtil.playSound(entity, VWSounds.NOTIFY.get(), SoundSource.PLAYERS, true);
            entity.setData(notify, 0);
        }
    }

    public static void setPlayerConfiguration(Player player, int config) {
        int CD_VERDANT_BLESSING = player.getData(PlayerAttachment.CD_BLESSING_OF_THE_VERDANT_WIND);
        int CD_HAVOC_AMP = player.getData(PlayerAttachment.CD_HAVOC);

        if (config == 0) {
            if (CD_VERDANT_BLESSING > 0) showLog(player, "VerdantBlessingCD", CD_VERDANT_BLESSING);
            if (CD_HAVOC_AMP > 0) showLog(player, "HavocCD", CD_HAVOC_AMP);
        } else {
            if (CD_VERDANT_BLESSING > 0) player.setData(PlayerAttachment.CD_BLESSING_OF_THE_VERDANT_WIND, 0);
            if (CD_HAVOC_AMP > 0) player.setData(PlayerAttachment.CD_HAVOC, 0);
        }
    }

    public static void setPlayerOtherConfig(Player player) {
        int COUNTER_VILLAGER_ATROCITY = player.getData(PlayerAttachment.VILLAGER_ATROCITY_COUNT);
        int COUNTER_WOLF_ATROCITY = player.getData(PlayerAttachment.WOLF_ATROCITY_COUNT);

        if (COUNTER_VILLAGER_ATROCITY > 0) player.setData(PlayerAttachment.CD_BLESSING_OF_THE_VERDANT_WIND, 0);
        if (COUNTER_WOLF_ATROCITY > 0) player.setData(PlayerAttachment.CD_BLESSING_OF_THE_VERDANT_WIND, 0);
    }


    /** 0 = sendLog, 1 = resetCD **/
    public static void setWolfConfiguration(Wolf wolf, int config) {
        String name = wolf.getPlainTextName();
        int CD_VERDANT_BLESSING = wolf.getData(WolfAttachment.CD_BLESSING_OF_THE_VERDANT_WIND);
        int CD_BLOODLUST_SKILL_PARALYZE = wolf.getData(WolfAttachment.CD_BLOODLUST_SKILL_PARALYZE);
        int CD_MIGHT_RUPTURE = wolf.getData(WolfAttachment.CD_MIGHT_SKILL_RUPTURE);
        int CD_IGNORE_DMG = wolf.getData(WolfAttachment.CD_IGNORE_HIGH_DAMAGE);

        if (config == 0) {
            if (CD_VERDANT_BLESSING > 0) showLog(wolf, name + " | VerdantBlessingCD", CD_VERDANT_BLESSING);
            if (CD_BLOODLUST_SKILL_PARALYZE > 0) showLog(wolf, name + " | ParalyzeCD", CD_BLOODLUST_SKILL_PARALYZE);
            if (CD_MIGHT_RUPTURE > 0) showLog(wolf, name + " | MightCD", CD_MIGHT_RUPTURE);
            if (CD_IGNORE_DMG > 0) showLog(wolf, name + " | IgnoreHighDMG", CD_IGNORE_DMG);
        } else {
            if (CD_VERDANT_BLESSING > 0) wolf.setData(WolfAttachment.CD_BLESSING_OF_THE_VERDANT_WIND, 0);
            if (CD_BLOODLUST_SKILL_PARALYZE > 0) wolf.setData(WolfAttachment.CD_BLOODLUST_SKILL_PARALYZE, 0);
            if (CD_MIGHT_RUPTURE > 0) wolf.setData(WolfAttachment.CD_MIGHT_SKILL_RUPTURE, 0);
            if (CD_IGNORE_DMG > 0) wolf.setData(WolfAttachment.CD_IGNORE_HIGH_DAMAGE, 0);
        }
    }
    private static void showLog(LivingEntity entity, String value, int attachment) {
        if (entity instanceof Wolf wolf) {
            sendToChat(wolf, false, value + " | " + attachment + " sec");
        } else if (entity instanceof Player player) {
            sendToChat(player, false, value + " | " + attachment + " sec");
        }
    }
    private static void showLog(LivingEntity entity, String value, String attachment) {
        if (entity instanceof Wolf wolf) {
            sendToChat(wolf, false, value + " | " + attachment);
        } else if (entity instanceof Player player) {
            sendToChat(player, false, value + " | " + attachment);
        }
    }
}