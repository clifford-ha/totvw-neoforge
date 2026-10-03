package cliffordha.totvw.datagen;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.block.custom.LodestoneWindCoreBlock;
import cliffordha.totvw.block.custom.StorageBlock;
import cliffordha.totvw.registry.VWItems;
import cliffordha.totvw.registry.VWItems.Pages;
import cliffordha.totvw.registry.VWBlocks;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

public class VWModelProvider extends ModelProvider {
    public VWModelProvider(PackOutput output) {
        super(output, TOTVW.MOD_ID);
    }

    private static TextureMapping storageBoxTextureMapping(Block block, String topSuffix) {
        return new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side"))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block, "_bottom"))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, topSuffix));
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        generateBlockStateModels(blockModels);
        generateItemModels(itemModels);
    }

    public void generateBlockStateModels(BlockModelGenerators block) {
        block.createTrivialCube(VWBlocks.VERIXIUM_STONE_ORE.get());
        block.createTrivialCube(VWBlocks.VERIXIUM_DEEPSLATE_ORE.get());
        block.createColoredBlockWithRandomRotations(TexturedModel.CUBE, List.of(
                VWBlocks.VERDANT_MOSS_BLOCK.get(),
                VWBlocks.VERIXIUM_POWDER_BLOCK.get()
        ));

        block.createTrivialCube(VWBlocks.AIR_PLACEHOLDER.get());
        block.copyModel(VWBlocks.VERDANT_MOSS_BLOCK.get(), VWBlocks.FARMLAND_PLACER.get());

        block.createGlassBlocks(VWBlocks.IRIDESCENT_GLASS.get(), VWBlocks.IRIDESCENT_GLASS_PANE.get());

        block.createTrivialBlock(VWBlocks.VERDANT_SPRUCE_LEAVES.get(), TexturedModel.LEAVES);
        block.createPlantWithDefaultItem(VWBlocks.VERDANT_SPRUCE_SAPLING.get(), VWBlocks.POTTED_VERDANT_SPRUCE_SAPLING.get(), BlockModelGenerators.PlantType.TINTED);

        block.family(VWBlocks.VERDANT_SPRUCE_LOG.get())
                .pressurePlate(VWBlocks.VERDANT_SPRUCE_PRESSURE_PLATE.get())
                .slab(VWBlocks.VERDANT_SPRUCE_SLAB.get())
                .sign(VWBlocks.VERDANT_SPRUCE_SIGN.get())
                .hangingSign(VWBlocks.VERDANT_SPRUCE_HANGING_SIGN.get())
                .fence(VWBlocks.VERDANT_SPRUCE_FENCE.get())
                .fenceGate(VWBlocks.VERDANT_SPRUCE_FENCE_GATE.get())
                .button(VWBlocks.VERDANT_SPRUCE_BUTTON.get())
                .door(VWBlocks.VERDANT_SPRUCE_DOOR.get())
                .trapdoor(VWBlocks.VERDANT_SPRUCE_TRAPDOOR.get());

        block.createShelf(VWBlocks.VERDANT_SPRUCE_SHELF.get(), VWBlocks.STRIPPED_VERDANT_SPRUCE_LOG.get());
        block.woodProvider(VWBlocks.VERDANT_SPRUCE_LOG.get()).log(VWBlocks.VERDANT_SPRUCE_LOG.get()).wood(VWBlocks.VERDANT_SPRUCE_WOOD.get());
        block.woodProvider(VWBlocks.STRIPPED_VERDANT_SPRUCE_LOG.get()).log(VWBlocks.STRIPPED_VERDANT_SPRUCE_LOG.get()).wood(VWBlocks.STRIPPED_VERDANT_SPRUCE_WOOD.get());

        MultiVariant OPEN = BlockModelGenerators.plainVariant(
                ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(VWBlocks.VERDANT_SPRUCE_STORAGE_BOX.get(), "_open",
                        storageBoxTextureMapping(VWBlocks.VERDANT_SPRUCE_STORAGE_BOX.get(), "_top_open"), block.modelOutput)
        );
        MultiVariant CLOSED = BlockModelGenerators.plainVariant(
                ModelTemplates.CUBE_BOTTOM_TOP.create(VWBlocks.VERDANT_SPRUCE_STORAGE_BOX.get(),
                        storageBoxTextureMapping(VWBlocks.VERDANT_SPRUCE_STORAGE_BOX.get(), "_top"), block.modelOutput)
        );

        MultiVariant LODESTONE_WIND_CORE_ACTIVE = BlockModelGenerators.plainVariant(
                Identifier.fromNamespaceAndPath(TOTVW.MOD_ID, "block/lodestone_wind_core_active")
        );
        MultiVariant LODESTONE_WIND_CORE = BlockModelGenerators.plainVariant(
                Identifier.fromNamespaceAndPath(TOTVW.MOD_ID, "block/lodestone_wind_core")
        );

        block.blockStateOutput.accept(MultiVariantGenerator.dispatch(VWBlocks.LODESTONE_WIND_CORE.get())
                .with(BlockModelGenerators.createBooleanModelDispatch(LodestoneWindCoreBlock.ACTIVE, LODESTONE_WIND_CORE_ACTIVE, LODESTONE_WIND_CORE))
                .with(PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
                        .select(Direction.NORTH, BlockModelGenerators.NOP)
                        .select(Direction.SOUTH, BlockModelGenerators.Y_ROT_180)
                        .select(Direction.WEST,  BlockModelGenerators.Y_ROT_270)
                        .select(Direction.EAST,  BlockModelGenerators.Y_ROT_90)
                ));

        block.blockStateOutput.accept(MultiVariantGenerator.dispatch(VWBlocks.VERDANT_SPRUCE_STORAGE_BOX.get())
                .with(BlockModelGenerators.createBooleanModelDispatch(StorageBlock.OPEN, OPEN, CLOSED))
                .with(PropertyDispatch.modify(BlockStateProperties.FACING)
                        .select(Direction.DOWN,  BlockModelGenerators.X_ROT_180)
                        .select(Direction.UP,    BlockModelGenerators.NOP)
                        .select(Direction.NORTH, BlockModelGenerators.X_ROT_90)
                        .select(Direction.SOUTH, BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_180))
                        .select(Direction.WEST,  BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_270))
                        .select(Direction.EAST,  BlockModelGenerators.X_ROT_90.then(BlockModelGenerators.Y_ROT_90))
                ));
    }

    private static void add(ItemModelGenerators item, ModelTemplate template, List<DeferredItem<Item>> v) {
        for (DeferredItem<Item> items : v) {
            item.generateFlatItem(items.get(), template);
        }
    }
    private static void addCopy(ItemModelGenerators item, DeferredItem<Item> donor, List<DeferredItem<Item>> v) {
        for (DeferredItem<Item> items : v) {
            item.generateFlatItem(items.get(), donor.get(), ModelTemplates.FLAT_ITEM);
        }
    }
    private static void addCopy(ItemModelGenerators item, Item donor, List<DeferredItem<Item>> v) {
        for (DeferredItem<Item> items : v) {
            item.generateFlatItem(items.get(), donor, ModelTemplates.FLAT_ITEM);
        }
    }

    public void generateItemModels(ItemModelGenerators item) {
        add(item, ModelTemplates.FLAT_ITEM, List.of(
                VWItems.VERIXIUM_CHUNK,
                VWItems.CONDENSED_VERIXIUM,
                VWItems.VERIXIUM_SHARD,
                VWItems.VERIXIUM_POWDER,
                VWItems.VERIXIUM_INGOT,
                VWItems.VERIXIUM_PAPER,
                VWItems.VERIXIUM_ARMOR_UPGRADE_TEMPLATE,

                VWItems.VERIXIUM_HELMET,
                VWItems.VERIXIUM_CHESTPLATE,
                VWItems.VERIXIUM_LEGGINGS,
                VWItems.VERIXIUM_BOOTS,

                VWItems.VERIXIUM_WOLF_ARMOR,
                VWItems.VERIXIUM_HORSE_ARMOR,

                VWItems.VERIXIUM_FLUID_BUCKET,
                VWItems.VERDANT_SPRUCE_BOAT,
                VWItems.VERDANT_SPRUCE_CHEST_BOAT,

                VWItems.SOUL_RUNESTONE_PLATE,
                VWItems.SOUL_RUNESTONE_FRAGMENT_1,
                VWItems.SOUL_RUNESTONE_FRAGMENT_2,
                VWItems.SOUL_RUNESTONE_FRAGMENT_3,
                VWItems.SOUL_RUNESTONE_FRAGMENT_4,

                VWItems.TETHER_RUNESTONE_PLATE,
                VWItems.GENESIS_RUNESTONE_PLATE,
                VWItems.HAVOC_RUNESTONE_PLATE,
                VWItems.EFFLORESCENCE_RUNESTONE_PLATE
        ));

        add(item, ModelTemplates.FLAT_HANDHELD_ITEM, List.of(
                VWItems.VERIXIUM_SWORD,
                VWItems.VERIXIUM_AXE,
                VWItems.VERIXIUM_PICKAXE,
                VWItems.VERIXIUM_SHOVEL,
                VWItems.VERIXIUM_HOE
        ));
        item.generateSpear(VWItems.VERIXIUM_SPEAR.get());

        item.generateFlatItem(Pages.SCATTERED_PAGE.get(), ModelTemplates.FLAT_ITEM);
        item.generateFlatItem(Pages.OLD_SCATTERED_PAGE.get(), ModelTemplates.FLAT_ITEM);

        item.generateFlatItem(Pages.ENCHANTMENTS_HANDBOOK.get(), ModelTemplates.FLAT_ITEM);
        addCopy(item, Pages.ENCHANTMENTS_HANDBOOK, List.of(
                Pages.EFFECTS_HANDBOOK,
                Pages.ITEMS_HANDBOOK,
                Pages.FEATURES_HANDBOOK
        ));

        addCopy(item, Items.BOOK, List.of(
                Pages.SP_ID_3000,
                Pages.SP_ID_3001,
                Pages.SP_ID_3002
        ));

        addCopy(item, Pages.SCATTERED_PAGE, List.of(
                Pages.PLAYER_STATS,
                Pages.LODESTONE_WIND_CORE_MANUAL,

                Pages.SP_ID_1001,
                Pages.SP_ID_1002,
                Pages.SP_ID_1003,
                Pages.SP_ID_1004,

                Pages.SP_ID_1005

        ));

        addCopy(item, Pages.OLD_SCATTERED_PAGE, List.of(
                Pages.SP_ID_1000,

                Pages.SP_ID_1006,

                Pages.SP_ID_1007,
                Pages.SP_ID_1008,
                Pages.SP_ID_1009,

                Pages.SP_ID_TEST
        ));

        if (TOTVW.IN_DEVELOPMENT) {
            item.generateFlatItem(VWItems.DevItems.ATTACHMENTS_REMOVER.get(), Pages.SCATTERED_PAGE.get(), ModelTemplates.FLAT_ITEM);
        }
    }

    @Override
    public String getName() {
        return "VWModelProvider";
    }
}