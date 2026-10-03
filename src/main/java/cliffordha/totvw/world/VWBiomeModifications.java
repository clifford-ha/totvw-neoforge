package cliffordha.totvw.world;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.tag.VWBiomeTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class VWBiomeModifications {
    public static final ResourceKey<BiomeModifier> VERDANT_CLASSIC_VINES = registerKey("verdant_classic_vines");
    public static final ResourceKey<BiomeModifier> VERDANT_PATCH_SUGAR_CANE = registerKey("verdant_patch_sugar_cane");
    public static final ResourceKey<BiomeModifier> VERDANT_SCULK_PATCH_ANCIENT_CITY = registerKey("verdant_sculk_patch_ancient_city");
    public static final ResourceKey<BiomeModifier> VERDANT_PATCH_FIREFLY_BUSH_NEAR_WATER = registerKey("verdant_patch_firefly_bush_near_water");


    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var biomes = context.lookup(Registries.BIOME);

        context.register(VERDANT_CLASSIC_VINES,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(VWBiomeTags.IS_VERDANT_BIOMES),
                        feature(context, CavePlacements.CLASSIC_VINES),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
        context.register(VERDANT_PATCH_SUGAR_CANE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(VWBiomeTags.IS_VERDANT_BIOMES),
                        feature(context, VegetationPlacements.PATCH_SUGAR_CANE),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
        context.register(VERDANT_SCULK_PATCH_ANCIENT_CITY,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(VWBiomeTags.IS_VERDANT_MOUNTAINS),
                        feature(context, CavePlacements.SCULK_PATCH_ANCIENT_CITY),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
        context.register(VERDANT_PATCH_FIREFLY_BUSH_NEAR_WATER,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(VWBiomeTags.IS_VERDANT_FOREST),
                        feature(context, VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                ));
    }
    private static HolderSet<PlacedFeature> feature(BootstrapContext<BiomeModifier> context, ResourceKey<PlacedFeature> featureResourceKey) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        return HolderSet.direct(placedFeatures.getOrThrow(featureResourceKey));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, TOTVW.registerID(name));
    }
}
