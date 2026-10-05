package cliffordha.totvw.tag;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.datagen.villager.VWVillagerTrades;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VillagerTradesTagsProvider;
import net.minecraft.tags.VillagerTradeTags;

import java.util.concurrent.CompletableFuture;

public class VWVillagerTradeTags extends VillagerTradesTagsProvider {
    public VWVillagerTradeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TOTVW.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(VillagerTradeTags.WEAPONSMITH_LEVEL_2)
                .addOptionalElement(VWVillagerTrades.WEAPONSMITH_2_RUNESTONE_FRAGMENT_3.identifier())
                .addOptionalElement(VWVillagerTrades.WEAPONSMITH_2_WOLF_ATK_ENCHANTMENTS.identifier());


        getOrCreateRawBuilder(VillagerTradeTags.WEAPONSMITH_LEVEL_3)
                .addOptionalElement(VWVillagerTrades.WEAPONSMITH_3_LODESTONE_WIND_CORE.identifier());


        getOrCreateRawBuilder(VillagerTradeTags.LIBRARIAN_LEVEL_2)
                .addOptionalElement(VWVillagerTrades.LIBRARIAN_2_WOLF_ATK_ENCHANTMENTS.identifier())
                .addOptionalElement(VWVillagerTrades.LIBRARIAN_2_VERIXIUM_PAPER.identifier());


        getOrCreateRawBuilder(VillagerTradeTags.CLERIC_LEVEL_2)
                .addOptionalElement(VWVillagerTrades.CLERIC_2_VERIXIUM_POWDER.identifier())
                .addOptionalElement(VWVillagerTrades.CLERIC_2_EMERALD.identifier());


        getOrCreateRawBuilder(VillagerTradeTags.ARMORER_LEVEL_1)
                .addOptionalElement(VWVillagerTrades.ARMORER_1_WOLF_ENHANCEMENT_KIT.identifier());
        getOrCreateRawBuilder(VillagerTradeTags.ARMORER_LEVEL_4)
                .addOptionalElement(VWVillagerTrades.ARMORER_4_VERIXIUM_WOLF_ARMOR.identifier());
        getOrCreateRawBuilder(VillagerTradeTags.ARMORER_LEVEL_5)
                .addOptionalElement(VWVillagerTrades.ARMORER_5_VERIXIUM_ARMOR_UPGRADE_TEMPLATE.identifier());


        getOrCreateRawBuilder(VillagerTradeTags.WANDERING_TRADER_COMMON)
                .addOptionalElement(VWVillagerTrades.WANDERING_VERDANT_SPRUCE_TREE_SAPLING.identifier());
        getOrCreateRawBuilder(VillagerTradeTags.WANDERING_TRADER_UNCOMMON)
                .addOptionalElement(VWVillagerTrades.WANDERING_FAR_AWAY_ENCHANTMENTS.identifier());
    }
}
