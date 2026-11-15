package cliffordha.totvw.registry.attachments.entity;

import cliffordha.totvw.registry.attachments.*;
import cliffordha.totvw.util.ExtUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static cliffordha.totvw.registry.attachments.AttachmentUtil.*;

public class WolfAttachment {
    private static final String WOLF = "wolf_";

    public static final Supplier<AttachmentType<ArmorData>> ARMOR_DATA = registerArmorData(WOLF + "armor_data");
    
    public static final Supplier<AttachmentType<Boolean>> IS_VERDANT_TYPE = registerBool(WOLF + "is_verdant_type");
    public static final Supplier<AttachmentType<Boolean>> IS_VILLAGE_GUARD = registerBool(WOLF + "is_village_guard");

    public static final Supplier<AttachmentType<Integer>> TIMER_AIR_SUPPLY = registerInt(WOLF + "timer_air_supply");
    public static final Supplier<AttachmentType<Integer>> NOTIFY_AIR_SUPPLY = registerInt(WOLF + "notify_air_supply");

    public static final Supplier<AttachmentType<Integer>> TRY_SAVE_POINTS = registerInt(WOLF + "try_save_points");
    public static final Supplier<AttachmentType<Integer>> TRY_SAVE_STATUS = registerInt(WOLF + "try_save_status");

    public static final Supplier<AttachmentType<Integer>> CD_BLESSING_OF_THE_VERDANT_WIND = registerInt(WOLF + "cd_blessing_of_the_verdant_wind");
    public static final Supplier<AttachmentType<Integer>> CD_BLOODLUST_SKILL_PARALYZE = registerInt(WOLF + "cd_bloodlust_skill_paralyze");
    public static final Supplier<AttachmentType<Integer>> CD_MIGHT_SKILL_RUPTURE = registerInt(WOLF + "cd_might_skill_rupture");
    public static final Supplier<AttachmentType<Integer>> CD_IGNORE_HIGH_DAMAGE = registerInt(WOLF + "cd_ignored_insurmountable_damage");
    public static final Supplier<AttachmentType<Integer>> CD_WOLF_FRAGMENT_SUMMON = registerInt("cd_wolf_fragment_summon");

    public static final Supplier<AttachmentType<Integer>> NOTIFY_MIGHT_SKILL_RUPTURE = registerInt(WOLF + "notify_might_skill_rupture");
    public static final Supplier<AttachmentType<Integer>> NOTIFY_BLOODLUST_SKILL_PARALYZE = registerInt(WOLF + "notify_bloodlust_skill_paralyze");
    public static final Supplier<AttachmentType<Integer>> NOTIFY_BLESSING_OF_THE_VERDANT_WIND = registerInt(WOLF + "notify_blessing_of_the_verdant_wind");
    public static final Supplier<AttachmentType<Integer>> NOTIFY_WOLF_FRAGMENT_SUMMON = registerInt(WOLF + "notify_wolf_fragment_summon");

    public static final Supplier<AttachmentType<Integer>> BENEDICTION = registerInt(WOLF + "benediction");

    public static final Supplier<AttachmentType<UUID>> SOUL_ID = registerUUID(WOLF + "soul_id");
    public static final Supplier<AttachmentType<UUID>> FAMILY_ID = registerUUID(WOLF + "family_id");

    public static final Supplier<AttachmentType<ReturnPointData>> RETURN_POINT = registerReturnPointData(WOLF + "return_point");
    public static final Supplier<AttachmentType<List<NameHolder>>> AGGRESSOR_LIST = registerTrustData(WOLF + "aggressor_list");
    public static final Supplier<AttachmentType<List<NameHolder>>> TRUSTED_PLAYERS = registerTrustData(WOLF + "trusted_players");
    public static final Supplier<AttachmentType<List<String>>> TETHERED_ENTITY_TYPES = registerStringList(WOLF + "tethered_entity_types");

    public static final Supplier<AttachmentType<Runestone>> RUNESTONE_TYPE = registerRunestone(WOLF + "runestone_type");
    public static final Supplier<AttachmentType<TetherData>> TETHER_DATA = registerTetherData(WOLF + "tether_data");
    public static final Supplier<AttachmentType<Integer>> ATTACK_CYCLE = registerInt(WOLF + "attack_cycle");
    public static final Supplier<AttachmentType<Integer>> FLASH_FREEZE_STACK = registerInt(WOLF + "flash_freeze_stack");

    public static final Supplier<AttachmentType<WolfFragmentData>> FRAGMENT_DATA = registerWitherwolf("fragment_data");



    public static boolean tetherChunkTicks(Wolf wolf) {
        if (!Runestone.hasTether(wolf)) return false;
        TetherData data = wolf.getData(TETHER_DATA);
        if (data.equals(TetherData.EMPTY)) return false;
        return data.tetherChunkTicks();
    }
    public static List<String> getTetherBlacklist(Wolf wolf) {
        return wolf.getData(TETHER_DATA).blacklistedEntities();
    }
    public static boolean isTetherBlacklisted(Wolf wolf, Entity entity) {
        List<String> blacklist = getTetherBlacklist(wolf);
        if (blacklist.isEmpty()) return false;
        return blacklist.contains(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString());
    }

    public static boolean isFamilyRelated(Wolf wolf, Wolf baby) {
        UUID wolfID = wolf.getData(FAMILY_ID);
        UUID babyID = baby.getData(FAMILY_ID);
        if (wolfID.equals(EMPTY_UUID)) return false;
        if (babyID.equals(EMPTY_UUID)) return false;

        return wolfID.equals(babyID);
    }
    public static boolean isVerdant(Wolf wolf) {
        return wolf.getData(IS_VERDANT_TYPE);
    }
    public static int benedictionStack(Wolf wolf) {
        return wolf.getData(BENEDICTION);
    }

    /**
     * Only call when a wolf should attack another wolf or player
     */
    public static boolean shouldAttackEntity(Wolf wolf, LivingEntity target) {
        List<NameHolder> TRUSTED = getTrustedPlayers(wolf);
        NameHolder mobData = NameHolder.of(target);
        for (NameHolder data : TRUSTED) {
            if (data.uuid().equals(mobData.uuid())) {
                return false;
            }
        }
        return true;
    }
    public static List<NameHolder> getTrustedPlayers(Wolf wolf) {
        return wolf.getData(TRUSTED_PLAYERS);
    }
    public static boolean isTrusted(Wolf wolf, LivingEntity other) {
        return getTrustedPlayers(wolf).stream().anyMatch(data -> data.uuid().equals(other.getUUID()));
    }
    public static void removeTrustedPlayer(Wolf wolf, Entity entity, boolean notify) {
        if (notify) {
            ExtUtil.sendToChat(wolf, false, wolf.getPlainTextName() + " removed " + entity.getPlainTextName() + " from trusted players");
        }
        removeTrustedPlayer(wolf, entity);
    }
    public static void removeTrustedPlayer(Wolf wolf, Entity entity) {
        if ((!(entity instanceof LivingEntity attacker))) return;
        List<NameHolder> newData = new ArrayList<>(getTrustedPlayers(wolf));
        newData.removeIf(data -> data.uuid().equals(attacker.getUUID()));
        wolf.setData(TRUSTED_PLAYERS, List.copyOf(newData));
    }
    public static void syncTrustedPlayers(Wolf wolf) {
        if (!(wolf.getOwner() instanceof Player player && wolf.distanceTo(player) <= 16)) return;
        wolf.setData(TRUSTED_PLAYERS, PlayerAttachment.getTrustedPlayers(player));
        List<NameHolder> aggressors = new ArrayList<>(getAggressors(wolf));
        aggressors.removeIf(getTrustedPlayers(wolf)::contains);
        wolf.setData(AGGRESSOR_LIST, List.copyOf(aggressors));
    }
    public static List<NameHolder> getAggressors(Wolf wolf) {
        return wolf.getData(AGGRESSOR_LIST);
    }
    public static boolean isListedAggressor(Wolf wolf, UUID aggressor) {
        List<NameHolder> aggressors = getAggressors(wolf);
        if (aggressors.isEmpty()) return false;

        List<NameHolder> check = aggressors.stream().filter(data -> data.uuid().equals(aggressor)).toList();
        return !check.isEmpty();
    }
    public static void addPlayerToAggressors(Wolf wolf, Player player) {
        List<NameHolder> oldPlayerData = getAggressors(wolf);
        List<NameHolder> newPlayerData = new ArrayList<>(oldPlayerData);

        NameHolder mobData = NameHolder.of(player);

        boolean hasPriorData = oldPlayerData.stream().anyMatch(data -> data.uuid().equals(mobData.uuid()));
        if (oldPlayerData.size() >= 6 && !hasPriorData) {
            return;
        }
        if (hasPriorData) {
            var index = oldPlayerData.stream().findFirst().filter(data -> data.uuid().equals(mobData.uuid())).map(oldPlayerData::indexOf).orElse(null);
            if (index == null) return;

            getTrustedPlayers(wolf).removeIf(data -> data.uuid().equals(mobData.uuid()));

            NameHolder data = oldPlayerData.get(index);

            if (!data.sameName(mobData) && data.sameUUID(mobData)) {
                NameHolder update = new NameHolder(mobData.name(), mobData.uuid());

                newPlayerData.set(newPlayerData.indexOf(data), update);
                wolf.setData(AGGRESSOR_LIST, List.copyOf(newPlayerData));
            }
        } else {
            newPlayerData.add(mobData);
            wolf.setData(AGGRESSOR_LIST, List.copyOf(newPlayerData));
        }
    }
}
