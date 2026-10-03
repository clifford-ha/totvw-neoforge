package cliffordha.totvw.registry.attachments;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.registry.attachments.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static cliffordha.totvw.registry.attachments.AttachmentUtil.*;

public class VWAttachments {
    public static final Supplier<AttachmentType<Boolean>> HAS_VERDANT_OMEN = registerBool("has_verdant_omen", false);

    public static final Supplier<AttachmentType<Integer>> PRESSURE_DIFFERENCE = registerInt("pressure_difference", false);
    public static final Supplier<AttachmentType<Boolean>> HAS_IMPLODED = registerBool("has_imploded", false);

    public static final Supplier<AttachmentType<Integer>> VERDANT_BLOOM_STACK = registerInt("verdant_bloom_stack", false);

    public static final Supplier<AttachmentType<BlockPos>> LAST_OVERWORLD_POS = registerBlockPos("last_overworld_pos", true);
    public static final Supplier<AttachmentType<BlockPos>> LAST_NOLAYAN_POS = registerBlockPos("last_nolayan_pos", true);
    public static final Supplier<AttachmentType<Boolean>> HAS_ENTERED_NOLAYAN = registerBool("has_entered_nolayan", true);

    public static final Supplier<AttachmentType<UUID>> WOLF_PLAYER_SHARED_ID = registerUUID("wolf_player_shared_id", true);


    public static UUID getWolfPlayerSharedId(LivingEntity entity) {
        return entity.getData(WOLF_PLAYER_SHARED_ID.get());
    }



    public static final List<AttachmentType<?>> WOLF_ATTACHMENTS = List.of(
            WolfAttachment.IS_VERDANT_TYPE.get(),
            WolfAttachment.IS_VILLAGE_GUARD.get(),
            WolfAttachment.TIMER_AIR_SUPPLY.get(),
            WolfAttachment.NOTIFY_AIR_SUPPLY.get(),
            WolfAttachment.CD_BLESSING_OF_THE_VERDANT_WIND.get(),
            WolfAttachment.CD_BLOODLUST_SKILL_PARALYZE.get(),
            WolfAttachment.CD_MIGHT_SKILL_RUPTURE.get(),
            WolfAttachment.CD_IGNORE_HIGH_DAMAGE.get(),
            WolfAttachment.NOTIFY_MIGHT_SKILL_RUPTURE.get(),
            WolfAttachment.NOTIFY_BLOODLUST_SKILL_PARALYZE.get(),
            WolfAttachment.NOTIFY_BLESSING_OF_THE_VERDANT_WIND.get(),
            WolfAttachment.BENEDICTION.get(),
            WolfAttachment.SOUL_ID.get(),
            WolfAttachment.FAMILY_ID.get(),
            WolfAttachment.TRY_SAVE_POINTS.get(),
            WolfAttachment.TRY_SAVE_STATUS.get(),
            WolfAttachment.RESPAWN_POINT.get(),
            WolfAttachment.AGGRESSOR_LIST.get(),
            WolfAttachment.TRUSTED_PLAYERS.get(),
            WolfAttachment.TETHERED_ENTITY_TYPES.get(),
            WolfAttachment.RUNESTONE_TYPE.get(),
            WolfAttachment.TETHER_ENTITY_BLACKLIST.get(),
            WolfAttachment.ATTACK_CYCLE.get()
    );
    public static final List<AttachmentType<?>> PLAYER_ATTACHMENTS = List.of(
            PlayerAttachment.IS_DEV_MODE.get(),

            PlayerAttachment.RECEIVED_ENCHANTMENTS_HANDBOOK.get(),
            PlayerAttachment.RECEIVED_EFFECTS_HANDBOOK.get(),
            PlayerAttachment.RECEIVED_ITEMS_HANDBOOK.get(),
            PlayerAttachment.RECEIVED_FEATURES_HANDBOOK.get(),

            PlayerAttachment.GENESIS_RUNESTONE_ACQUISITION_COUNT.get(),
            PlayerAttachment.WOLF_SOULS.get(),
            PlayerAttachment.VILLAGER_ATROCITY_COUNT.get(),
            PlayerAttachment.WOLF_ATROCITY_COUNT.get(),
            PlayerAttachment.CD_BLESSING_OF_THE_VERDANT_WIND.get(),
            PlayerAttachment.CD_HAVOC.get(),
            PlayerAttachment.HAVOC_TYPE.get(),
            PlayerAttachment.HAVOC_USAGE_COUNT.get(),
            PlayerAttachment.NOTIFY_BLESSING_OF_THE_VERDANT_WIND.get(),
            PlayerAttachment.RESPAWN_POINT.get(),
            PlayerAttachment.TRUSTED_PLAYERS.get()
    );
    public static final List<AttachmentType<?>> VILLAGER_ATTACHMENTS = List.of(
            VillagerAttachment.IS_VERDANT_TYPE.get(),
            VillagerAttachment.CD_HEAL_OTHERS.get(),
            VillagerAttachment.CD_HEAL_WOLF.get(),
            VillagerAttachment.CD_HEAL_IRON_GOLEM.get(),
            VillagerAttachment.CD_DISCOUNT_REROLL.get(),
            VillagerAttachment.DISCOUNT_MODIFIER.get()
    );
    public static final List<AttachmentType<?>> MISC_ATTACHMENTS = List.of(
            HAS_VERDANT_OMEN.get(),
            PRESSURE_DIFFERENCE.get(),
            HAS_IMPLODED.get(),
            VERDANT_BLOOM_STACK.get(),
            LAST_OVERWORLD_POS.get(),
            LAST_NOLAYAN_POS.get(),
            HAS_ENTERED_NOLAYAN.get()
    );
    public static final List<AttachmentType<?>> PLAYER_PREFS = List.of(
            PlayerPrefs.SHOW_ATROCITY_COUNTER.get(),
            PlayerPrefs.ENABLE_NOTIFIERS.get(),

            PlayerPrefs.BENEDICTION_HEALTH_THRESHOLD.get(),
            PlayerPrefs.BENEDICTION_SHARE_STACK.get(),
            PlayerPrefs.BENEDICTION_ALWAYS_TRIGGER_BLESSING.get(),
            PlayerPrefs.BENEDICTION_TELEPORT_AFTER_SAVE.get(),
            PlayerPrefs.BENEDICTION_WOLF_TP_METHOD.get(),
            PlayerPrefs.BENEDICTION_PLAYER_TP_METHOD.get(),
            PlayerPrefs.BENEDICTION_WOLF_TP_ALL.get()
    );
    public static final int TOTAL = WOLF_ATTACHMENTS.size() + PLAYER_ATTACHMENTS.size() + VILLAGER_ATTACHMENTS.size() + MISC_ATTACHMENTS.size() + PLAYER_PREFS.size();


    public static void register() {
        TOTVW.sendClassRegisterLog(
                "Custom Attachments (" +
                        "Wolf: " + WOLF_ATTACHMENTS.size() + ", " +
                        "Villager: " + VILLAGER_ATTACHMENTS.size() + ", " +
                        "Player: " + PLAYER_ATTACHMENTS.size() + ", " +
                        "Misc: " + MISC_ATTACHMENTS.size() + ", " +
                        "PlayerPrefs: " + PLAYER_PREFS.size() + "): " +
                        TOTAL + " in total has been"
        );
    }
}