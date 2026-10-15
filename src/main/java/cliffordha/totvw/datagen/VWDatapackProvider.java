package cliffordha.totvw.datagen;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.datagen.villager.VWVillagerTrades;
import cliffordha.totvw.registry.VWEnchantments;
import cliffordha.totvw.world.VWBiomeModifications;
import cliffordha.totvw.world.VWBiomes;
import cliffordha.totvw.worldgen.VWConfiguredFeatures;
import cliffordha.totvw.worldgen.VWPlacedFeatures;
import cliffordha.totvw.worldgen.dimension.VWDimensions;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class VWDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder WORLD_BUILDER = new RegistrySetBuilder()
        .add(Registries.ENCHANTMENT, VWEnchantments::bootstrap)
        .add(Registries.DAMAGE_TYPE, VWDamageTypes::bootstrap)

        .add(Registries.WOLF_VARIANT, VWWolfVariants::bootstrap)
        .add(Registries.VILLAGER_TRADE, VWVillagerTrades::bootstrap)

        .add(Registries.BIOME, VWBiomes::bootstrap)
        .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, VWBiomeModifications::bootstrap)
        .add(Registries.CONFIGURED_FEATURE, VWConfiguredFeatures::configure)
        .add(Registries.PLACED_FEATURE, VWPlacedFeatures::configure)

        .add(Registries.DIMENSION_TYPE, VWDimensions::bootstrapType)
        .add(Registries.LEVEL_STEM, VWDimensions::bootstrapStem);

    public VWDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, WORLD_BUILDER, Set.of(TOTVW.MOD_ID));
    }
}
