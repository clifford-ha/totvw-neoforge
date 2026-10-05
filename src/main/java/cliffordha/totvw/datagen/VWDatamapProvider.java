package cliffordha.totvw.datagen;

import cliffordha.totvw.registry.VWBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.BlockTransformAppender;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class VWDatamapProvider extends DataMapProvider {
    public VWDatamapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        builder(NeoForgeDataMaps.BLOCK_TRANSFORM_APPENDERS)
                .add(BlockTransformers.AXE, new BlockTransformAppender(
                        List.of(BlockTransformer.BlockTransformData.builder(BlockPredicate.matchesBlocks(VWBlocks.VERDANT_SPRUCE_LOG.get()), VWBlocks.STRIPPED_VERDANT_SPRUCE_LOG.get()).build(),
                                BlockTransformer.BlockTransformData.builder(BlockPredicate.matchesBlocks(VWBlocks.VERDANT_SPRUCE_WOOD.get()), VWBlocks.STRIPPED_VERDANT_SPRUCE_WOOD.get()).build())
                ), false);
    }
}
