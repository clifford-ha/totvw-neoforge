package cliffordha.totvw.datagen;

import cliffordha.totvw.registry.VWBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;

import java.util.concurrent.CompletableFuture;

public class VWDatamapProvider extends DataMapProvider {
    public VWDatamapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.STRIPPABLES)
                .add(VWBlocks.VERDANT_SPRUCE_LOG, new Strippable(VWBlocks.STRIPPED_VERDANT_SPRUCE_LOG.get()), false)
                .add(VWBlocks.VERDANT_SPRUCE_WOOD, new Strippable(VWBlocks.STRIPPED_VERDANT_SPRUCE_WOOD.get()), false);
    }
}
