package cliffordha.totvw.loot;

import cliffordha.totvw.registry.VWItems;
import cliffordha.totvw.registry.VWBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Set;

public class VWBlockLootTableProvider extends BlockLootSubProvider {
    public VWBlockLootTableProvider(LootTableSubProvider.Context context) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), context);
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
    }

    @Override
    public void generate() {
        add(VWBlocks.VERIXIUM_STONE_ORE, createOreDrop(
                VWBlocks.VERIXIUM_STONE_ORE,
                VWItems.VERIXIUM_CHUNK
        ));
        add(VWBlocks.VERIXIUM_DEEPSLATE_ORE, createOreDrop(
                VWBlocks.VERIXIUM_DEEPSLATE_ORE,
                VWItems.VERIXIUM_CHUNK
        ));
        add(VWBlocks.VERIXIUM_POWDER_BLOCK, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(VWItems.VERIXIUM_POWDER)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(3, 9)))
                                .when(doesNotHaveSilkTouch())
                        )
                        .add(LootItem.lootTableItem(VWBlocks.VERIXIUM_POWDER_BLOCK)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                .when(hasSilkTouch())
                        )
                        .add(LootItem.lootTableItem(VWItems.VERIXIUM_POWDER)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 9)))
                                .when(ExplosionCondition.survivesExplosion())
                        )
                )
        );
        dropSelf(VWBlocks.VERDANT_MOSS_BLOCK);
        add(VWBlocks.VERDANT_SPRUCE_LEAVES, createLeavesDrops(
                VWBlocks.VERDANT_SPRUCE_LEAVES.get(),
                VWBlocks.VERDANT_SPRUCE_SAPLING.get(),
                NORMAL_LEAVES_SAPLING_CHANCES
        ));

        dropSelf(VWBlocks.VERDANT_SPRUCE_SAPLING);
        dropPottedContents(VWBlocks.POTTED_VERDANT_SPRUCE_SAPLING.get());

        dropSelf(VWBlocks.VERDANT_SPRUCE_PLANKS);
        dropSelf(VWBlocks.VERDANT_SPRUCE_LOG);
        dropSelf(VWBlocks.VERDANT_SPRUCE_WOOD);
        dropSelf(VWBlocks.STRIPPED_VERDANT_SPRUCE_LOG);
        dropSelf(VWBlocks.STRIPPED_VERDANT_SPRUCE_WOOD);
        add(VWBlocks.VERDANT_SPRUCE_SLAB, createSlabItemTable(VWBlocks.VERDANT_SPRUCE_SLAB.get()));
        dropSelf(VWBlocks.VERDANT_SPRUCE_STAIRS);
        dropSelf(VWBlocks.VERDANT_SPRUCE_FENCE);
        dropSelf(VWBlocks.VERDANT_SPRUCE_FENCE_GATE);
        dropSelf(VWBlocks.VERDANT_SPRUCE_BUTTON);
        dropSelf(VWBlocks.VERDANT_SPRUCE_PRESSURE_PLATE);
        add(VWBlocks.VERDANT_SPRUCE_DOOR, createDoorTable(VWBlocks.VERDANT_SPRUCE_DOOR.get()));
        dropSelf(VWBlocks.VERDANT_SPRUCE_TRAPDOOR);
        dropSelf(VWBlocks.VERDANT_SPRUCE_SIGN);
        dropSelf(VWBlocks.VERDANT_SPRUCE_HANGING_SIGN);
        dropSelf(VWBlocks.VERDANT_SPRUCE_SHELF);
        dropSelf(VWBlocks.VERDANT_SPRUCE_STORAGE_BOX);

        dropSelf(VWBlocks.IRIDESCENT_GLASS);
        dropSelf(VWBlocks.IRIDESCENT_GLASS_PANE);
        dropSelf(VWBlocks.LODESTONE_WIND_CORE);
    }

    private void dropSelf(DeferredBlock<Block> block) {
        super.dropSelf(block.get());
    }

    private LootTable.Builder createOreDrop(DeferredBlock<Block> original, ItemLike drop) {
        return super.createOreDrop(original.get(), drop.asItem());
    }

    private void add(DeferredBlock<Block> original, LootTable.Builder oreDrop) {
        super.add(original.get(), oreDrop);
    }
}
