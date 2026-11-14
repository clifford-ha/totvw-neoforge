package cliffordha.totvw.entity.status;

import cliffordha.totvw.registry.attachments.HavocData;
import cliffordha.totvw.registry.attachments.NameHolder;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static cliffordha.totvw.registry.attachments.entity.PlayerAttachment.*;

public record PlayerStats(
        String name,
        String UUID,

        boolean hasHelmet,
        boolean hasChestplate,
        boolean hasLeggings,
        boolean hasBoots,
        boolean hasWeaponInMainhand,
        boolean hasShield,

        int helmetDurability,
        int chestplateDurability,
        int leggingsDurability,
        int bootsDurability,
        int mainhandDurability,
        int offHandDurability,

        int maxHelmetDurability,
        int maxChestplateDurability,
        int maxLeggingsDurability,
        int maxBootsDurability,
        int maxMainhandDurability,
        int maxShieldDurability,

        int wolfSouls,
        int wolfAtrocityCount,
        int villagerAtrocityCount,

        int genesisRunestoneAcquisitionCount,

        HavocData havocData,

        List<NameHolder> trustedPlayers,

        boolean receivedEnchantmentsHandbook,
        boolean receivedItemsHandbook,
        boolean receivedFeaturesHandbook,
        boolean receivedEffectsHandbook
) {
    public static PlayerStats valueOf(Player player) {
        List<CompoundTag> souls = player.getData(PlayerAttachment.WOLF_SOULS);

        boolean enchantmentHandbook = player.getData(PlayerAttachment.RECEIVED_ENCHANTMENTS_HANDBOOK) > 0;
        boolean itemHandbook = player.getData(PlayerAttachment.RECEIVED_ITEMS_HANDBOOK) > 0;
        boolean featureHandbook = player.getData(PlayerAttachment.RECEIVED_FEATURES_HANDBOOK) > 0;
        boolean effectHandbook = player.getData(PlayerAttachment.RECEIVED_EFFECTS_HANDBOOK) > 0;

        ItemStack helmet = getEquipments(player, EquipmentSlot.HEAD);
        ItemStack chestplate = getEquipments(player, EquipmentSlot.CHEST);
        ItemStack leggings = getEquipments(player, EquipmentSlot.LEGS);
        ItemStack boots = getEquipments(player, EquipmentSlot.FEET);
        ItemStack mainhand = getEquipments(player, EquipmentSlot.MAINHAND);
        ItemStack shield = getEquipments(player, EquipmentSlot.OFFHAND);

        boolean hasHelmet = helmet != null;
        boolean hasChestplate = chestplate != null;
        boolean hasLeggings = leggings != null;
        boolean hasBoots = boots != null;
        boolean hasItemInMainhand = mainhand != null;
        boolean hasOffhandItem = shield != null;

        int helmetDurability = hasHelmet ? getDamageValue(helmet) : 0;
        int chestplateDurability = hasChestplate ? getDamageValue(chestplate) : 0;
        int leggingsDurability = hasLeggings ? getDamageValue(leggings) : 0;
        int bootsDurability = hasBoots ? getDamageValue(boots) : 0;
        int mainhandDurability = hasItemInMainhand ? getDamageValue(mainhand) : 0;
        int offHandDurability = hasOffhandItem ? getDamageValue(shield) : 0;

        int maxHelmetDurability = hasHelmet ? getMaxDamageValue(helmet) : 0;
        int maxChestplateDurability = hasChestplate ? getMaxDamageValue(chestplate) : 0;
        int maxLeggingsDurability = hasLeggings ? getMaxDamageValue(leggings) : 0;
        int maxBootsDurability = hasBoots ? getMaxDamageValue(boots) : 0;
        int maxMainhandDurability = hasItemInMainhand ? getMaxDamageValue(mainhand) : 0;
        int maxOffhandDurability = hasOffhandItem ? getMaxDamageValue(shield) : 0;

        return new PlayerStats(
                player.getPlainTextName(),
                player.getStringUUID(),

                hasHelmet,
                hasChestplate,
                hasLeggings,
                hasBoots,
                hasItemInMainhand,
                hasOffhandItem,

                helmetDurability,
                chestplateDurability,
                leggingsDurability,
                bootsDurability,
                mainhandDurability,
                offHandDurability,

                maxHelmetDurability,
                maxChestplateDurability,
                maxLeggingsDurability,
                maxBootsDurability,
                maxMainhandDurability,
                maxOffhandDurability,

                souls.size(),
                player.getData(PlayerAttachment.WOLF_ATROCITY_COUNT),
                player.getData(PlayerAttachment.VILLAGER_ATROCITY_COUNT),

                player.getData(PlayerAttachment.GENESIS_RUNESTONE_ACQUISITION_COUNT),

                HavocData.getData(player),

                PlayerAttachment.getTrustedPlayers(player),

                enchantmentHandbook,
                itemHandbook,
                featureHandbook,
                effectHandbook
        );
    }
}
