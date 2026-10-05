package cliffordha.totvw.tag;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.world.VWBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class VWBiomeTags extends BiomeTagsProvider {
    public VWBiomeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TOTVW.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {

        /*getOrCreateRawBuilder(BiomeTags.IS_OVERWORLD)
                .addTag(VWBiomeTags.IS_VERDANT_BIOMES.location());*/

        getOrCreateRawBuilder(IS_VERDANT_BIOMES)
                .addOptionalElement(VWBiomes.VERDANT_MOUNTAINS.identifier())
                .addOptionalElement(VWBiomes.VERDANT_FOREST.identifier());

        getOrCreateRawBuilder(FOREST_WHERE_WOLVES_HOWL)
                .addOptionalTag(VWBiomeTags.IS_VERDANT_BIOMES.location())
                .addOptionalElement(Biomes.FOREST.identifier())
                .addOptionalElement(Biomes.FLOWER_FOREST.identifier())
                .addOptionalElement(Biomes.DARK_FOREST.identifier());

        getOrCreateRawBuilder(IS_VERDANT_MOUNTAINS)
                .addOptionalElement(VWBiomes.VERDANT_MOUNTAINS.identifier());

        getOrCreateRawBuilder(IS_VERDANT_FOREST)
                .addOptionalElement(VWBiomes.VERDANT_FOREST.identifier());

        getOrCreateRawBuilder(HAS_VERDANT_FOREST_VILLAGE)
                .addOptionalTag(IS_VERDANT_FOREST.location());

        getOrCreateRawBuilder(HAS_VERDANT_MOUNTAINS_VILLAGE)
                .addOptionalElement(VWBiomes.VERDANT_MOUNTAINS.identifier());

        getOrCreateRawBuilder(BiomeTags.IS_MOUNTAIN)
                .addOptionalElement(VWBiomes.VERDANT_MOUNTAINS.identifier());

        getOrCreateRawBuilder(BiomeTags.HAS_ANCIENT_CITY)
                .addOptionalElement(VWBiomes.VERDANT_MOUNTAINS.identifier());

        getOrCreateRawBuilder(BiomeTags.HAS_BURIED_TREASURE)
                .addOptionalTag(VWBiomeTags.IS_VERDANT_BIOMES.location());

        getOrCreateRawBuilder(BiomeTags.HAS_JUNGLE_TEMPLE)
                .addOptionalElement(VWBiomes.VERDANT_FOREST.identifier());

        getOrCreateRawBuilder(BiomeTags.HAS_MINESHAFT)
                .addOptionalTag(VWBiomeTags.IS_VERDANT_BIOMES.location());

        getOrCreateRawBuilder(BiomeTags.HAS_PILLAGER_OUTPOST)
                .addOptionalTag(VWBiomeTags.IS_VERDANT_BIOMES.location());

        getOrCreateRawBuilder(BiomeTags.HAS_RUINED_PORTAL_MOUNTAIN)
                .addOptionalElement(VWBiomes.VERDANT_MOUNTAINS.identifier());

        getOrCreateRawBuilder(BiomeTags.HAS_RUINED_PORTAL_JUNGLE)
                .addOptionalElement(VWBiomes.VERDANT_FOREST.identifier());

        getOrCreateRawBuilder(BiomeTags.HAS_STRONGHOLD)
                .addOptionalTag(VWBiomeTags.IS_VERDANT_BIOMES.location());

        getOrCreateRawBuilder(BiomeTags.HAS_SWAMP_HUT)
                .addOptionalElement(VWBiomes.VERDANT_FOREST.identifier());

        getOrCreateRawBuilder(BiomeTags.HAS_TRIAL_CHAMBERS)
                .addOptionalTag(VWBiomeTags.IS_VERDANT_BIOMES.location());

        getOrCreateRawBuilder(BiomeTags.SPAWNS_COLD_VARIANT_FROGS)
                .addOptionalTag(VWBiomeTags.IS_VERDANT_BIOMES.location());

        getOrCreateRawBuilder(BiomeTags.PRODUCES_CORALS_FROM_BONEMEAL)
                .addOptionalTag(VWBiomeTags.IS_VERDANT_BIOMES.location());

    }
    public static final TagKey<Biome> IS_VERDANT_BIOMES = create("is_verdant_biomes");
    public static final TagKey<Biome> IS_VERDANT_MOUNTAINS = create("is_verdant_mountains");
    public static final TagKey<Biome> IS_VERDANT_FOREST = create("is_verdant_forest");
    public static final TagKey<Biome> HAS_VERDANT_FOREST_VILLAGE = create("has_verdant_forest_village");
    public static final TagKey<Biome> HAS_VERDANT_MOUNTAINS_VILLAGE = create("has_verdant_mountains_village");
    public static final TagKey<Biome> FOREST_WHERE_WOLVES_HOWL = create("forest_where_wolves_howl");

    private static TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, TOTVW.registerID(name)); }
}
