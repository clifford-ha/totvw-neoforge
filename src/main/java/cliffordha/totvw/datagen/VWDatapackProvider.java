package cliffordha.totvw.datagen;

import cliffordha.totvw.datagen.villager.VWVillagerTrades;
import cliffordha.totvw.loot.VWBlockLootTableProvider;
import cliffordha.totvw.loot.VWLootTables;
import cliffordha.totvw.registry.VWEnchantments;
import cliffordha.totvw.world.VWBiomeModifications;
import cliffordha.totvw.world.VWBiomes;
import cliffordha.totvw.worldgen.VWConfiguredFeatures;
import cliffordha.totvw.worldgen.VWPlacedFeatures;
import cliffordha.totvw.worldgen.dimension.VWDimensions;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;

public class VWDatapackProvider {
    public static final RegistrySetBuilder WORLD_BUILDER = new RegistrySetBuilder()
        .add(Registries.ENCHANTMENT, VWEnchantments::bootstrap)
        .add(Registries.DAMAGE_TYPE, VWDamageTypes::bootstrap)

        .add(Registries.WOLF_VARIANT, VWWolfVariants::bootstrap)
        .add(Registries.VILLAGER_TRADE, VWVillagerTrades::bootstrap)

        .add(Registries.BIOME, VWBiomes::bootstrap)
        .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, VWBiomeModifications::bootstrap)
        .add(Registries.FEATURE, VWConfiguredFeatures::configure)
        .add(Registries.PLACED_FEATURE, VWPlacedFeatures::configure)

        .add(Registries.DIMENSION_TYPE, VWDimensions::bootstrapType)
        .add(Registries.LEVEL_STEM, VWDimensions::bootstrapStem)
            ;

    public static final RegistrySetBuilder RELOADABLE_BUILDER = new RegistrySetBuilder()
            .add(Registries.ADVANCEMENT, new AdvancementProvider(List.of(VWAdvancements::create)))
            .add(VWRecipeProvider.create())
            .add(Registries.LOOT_TABLE, new LootTableProvider(Set.of(), List.of(
                    new LootTableProvider.SubProviderEntry(VWBlockLootTableProvider::new, LootContextParamSets.BLOCK),
                    new LootTableProvider.SubProviderEntry(VWLootTables::new, LootContextParamSets.ALL_PARAMS)
            )));
}
