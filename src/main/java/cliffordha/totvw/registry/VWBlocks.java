package cliffordha.totvw.registry;

import cliffordha.totvw.block.*;
import cliffordha.totvw.block.custom.*;
import cliffordha.totvw.TOTVW;
import cliffordha.totvw.world.tree.VWTreeGrowers;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootTable;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static cliffordha.totvw.registry.VWBlocks.Util.*;

public class VWBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TOTVW.MOD_ID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(TOTVW.MOD_ID);

    public static final BlockSetType VERDANT_SPRUCE_SET =
            BlockSetType.register(new BlockSetType(TOTVW.MOD_ID + ":verdant_spruce"));
    public static final WoodType VERDANT_SPRUCE_WOOD_TYPE =
            WoodType.register(new WoodType(TOTVW.MOD_ID + ":verdant_spruce", VERDANT_SPRUCE_SET));

    public static final DeferredBlock<Block> VERIXIUM_DEEPSLATE_ORE = registerBlock("verixium_deepslate_ore",
            properties -> new VerixiumOreBlock(properties
                    .sound(SoundType.DEEPSLATE)
                    .mapColor(MapColor.DEEPSLATE)
                    .requiresCorrectToolForDrops()
                    .lightLevel(_ -> 9)
                    .strength(3.5F, 60F)),
            BlockBehaviour.Properties.of(),
            true
    );
    public static final DeferredBlock<Block> VERIXIUM_STONE_ORE = registerBlock("verixium_stone_ore",
            properties -> new VerixiumOreBlock(properties
                    .sound(SoundType.STONE)
                    .mapColor(MapColor.STONE)
                    .requiresCorrectToolForDrops()
                    .lightLevel(_ -> 9)
                    .strength(3.0F, 30F)),
            BlockBehaviour.Properties.of(),
            true
    );
    public static final DeferredBlock<Block> VERIXIUM_POWDER_BLOCK = registerBlock("verixium_powder_block",
            properties -> new VerixiumPowderBlock(new ColorRGBA(VWColors.VERDANT_WIND), properties
                    .sound(SoundType.SAND)
                    .mapColor(MapColor.DIAMOND)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .lightLevel(_ -> 15)
                    .pushReaction(PushReaction.POPPED)
                    .strength(1.5F, 90F)),
            BlockBehaviour.Properties.of(),
            true
    );
    public static final DeferredBlock<LiquidBlock> VERIXIUM_FLUID = BLOCKS.registerBlock(
            "verixium_fluid", properties -> new LiquidBlock(VWFluids.FLOWING_VERIXIUM_FLUID.get(),
                    properties
                            .noCollision()
                            .strength(100.0F)
                            .pushReaction(PushReaction.POPPED)
                            .noLootTable()
                            .liquid()
                            .replaceable()
                            .mapColor(MapColor.WARPED_NYLIUM)
                            .sound(SoundType.EMPTY)
            )
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_LEAVES = registerBlock("verdant_spruce_leaves",
            properties -> new VerdantSpruceLeavesBlock(0.00f, ParticleTypes.ASH, properties
                    .mapColor(MapColor.WARPED_WART_BLOCK)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_SAPLING = registerBlock("verdant_spruce_sapling",
            properties -> new VWSaplingBlock(VWTreeGrowers.VERDANT, properties
                    .mapColor(MapColor.WARPED_WART_BLOCK)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING),
            true
    );
    public static final DeferredBlock<Block> POTTED_VERDANT_SPRUCE_SAPLING = registerBlock("potted_verdant_spruce_sapling",
            properties -> new FlowerPotBlock(VERDANT_SPRUCE_SAPLING.get(), properties
                    .mapColor(MapColor.WARPED_WART_BLOCK)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_SPRUCE_SAPLING),
            false
    );
    public static final DeferredBlock<Block> VERDANT_MOSS_BLOCK = registerBlock("verdant_moss_block",
            properties -> new GrassBlock(properties
                    .mapColor(MapColor.WARPED_WART_BLOCK)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_PLANKS = registerBlock("verdant_spruce_planks",
            properties -> new Block(properties
                    .mapColor(MapColor.WARPED_STEM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_SLAB = registerBlock("verdant_spruce_slab",
            properties -> new SlabBlock(properties
                    .mapColor(MapColor.WARPED_STEM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_STAIRS = registerBlock("verdant_spruce_stairs",
            properties -> new StairBlock(VERDANT_SPRUCE_PLANKS.get().defaultBlockState(), properties
                    .mapColor(MapColor.WARPED_STEM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_STAIRS),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_BUTTON = registerBlock("verdant_spruce_button",
            properties -> new ButtonBlock(VERDANT_SPRUCE_SET, 10, properties
                    .mapColor(MapColor.WARPED_STEM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_BUTTON),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_PRESSURE_PLATE = registerBlock("verdant_spruce_pressure_plate",
            properties -> new PressurePlateBlock(VERDANT_SPRUCE_SET, properties
                    .mapColor(MapColor.WARPED_STEM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PRESSURE_PLATE),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_FENCE = registerBlock("verdant_spruce_fence",
            properties -> new FenceBlock(properties
                    .mapColor(MapColor.WARPED_STEM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_FENCE),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_FENCE_GATE = registerBlock("verdant_spruce_fence_gate",
            properties -> new FenceGateBlock(VERDANT_SPRUCE_WOOD_TYPE, properties
                    .mapColor(MapColor.WARPED_STEM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_FENCE_GATE),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_TRAPDOOR = registerBlock("verdant_spruce_trapdoor",
            properties -> new TrapDoorBlock(VERDANT_SPRUCE_SET, properties
                    .mapColor(MapColor.WARPED_NYLIUM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_TRAPDOOR),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_DOOR = registerBlock("verdant_spruce_door",
            properties -> new DoorBlock(VERDANT_SPRUCE_SET, properties
                    .mapColor(MapColor.WARPED_NYLIUM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_DOOR),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_LOG = registerBlock("verdant_spruce_log",
            properties -> new RotatedPillarBlock(properties
                    .mapColor(MapColor.WARPED_NYLIUM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_WOOD = registerBlock("verdant_spruce_wood",
            properties -> new RotatedPillarBlock(properties
                    .mapColor(MapColor.WARPED_NYLIUM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD),
            true
    );
    public static final DeferredBlock<Block> STRIPPED_VERDANT_SPRUCE_LOG = registerBlock("stripped_verdant_spruce_log",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG).mapColor(MapColor.WARPED_NYLIUM),
            true
    );
    public static final DeferredBlock<Block> STRIPPED_VERDANT_SPRUCE_WOOD = registerBlock("stripped_verdant_spruce_wood",
            RotatedPillarBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD).mapColor(MapColor.WARPED_NYLIUM),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_SIGN = registerBlock("verdant_spruce_sign",
            properties -> new VWStandingSignBlock(VERDANT_SPRUCE_WOOD_TYPE, properties
                    .mapColor(MapColor.WARPED_STEM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SIGN),
            false
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_HANGING_SIGN = registerBlock("verdant_spruce_hanging_sign",
            properties -> new VWCeilingHangingSignBlock(VERDANT_SPRUCE_WOOD_TYPE, properties
                    .mapColor(MapColor.WARPED_STEM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_HANGING_SIGN),
            false
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_WALL_SIGN = registerBlock("verdant_spruce_wall_sign",
            properties -> new VWWallSignBlock(VERDANT_SPRUCE_WOOD_TYPE, properties),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WALL_SIGN)
                    .overrideDescription(descriptionId("verdant_spruce_sign"))
                    .overrideLootTable(lootTable("verdant_spruce_sign")),
            false
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_WALL_HANGING_SIGN = registerBlock("verdant_spruce_wall_hanging_sign",
            properties -> new VWWallHangingSignBlock(VERDANT_SPRUCE_WOOD_TYPE, properties),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WALL_HANGING_SIGN)
                    .overrideDescription(descriptionId("verdant_spruce_hanging_sign"))
                    .overrideLootTable(lootTable("verdant_spruce_hanging_sign")),
            false
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_SHELF = registerBlock("verdant_spruce_shelf",
            properties -> new VWShelfBlock(properties
                    .mapColor(MapColor.WARPED_STEM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SHELF),
            true
    );
    public static final DeferredBlock<Block> VERDANT_SPRUCE_STORAGE_BOX = registerBlock("verdant_spruce_storage_box",
            properties -> new StorageBlock(properties
                    .mapColor(MapColor.WARPED_NYLIUM)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL),
            true
    );

    public static final DeferredBlock<Block> IRIDESCENT_GLASS = registerBlock("iridescent_glass",
            properties -> new TransparentBlock(properties
                    .lightLevel(_ -> 9)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS),
            true
    );
    public static final DeferredBlock<Block> IRIDESCENT_GLASS_PANE = registerBlock("iridescent_glass_pane",
            properties -> new StainedGlassPaneBlock(DyeColor.CYAN, properties
                    .lightLevel(_ -> 9)
            ),
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE),
            true
    );
    public static final DeferredBlock<Block> LODESTONE_WIND_CORE = registerBlock("lodestone_wind_core",
            properties -> new LodestoneWindCoreBlock(properties
                    .strength(50.0f, 100.0f)
                    .sound(SoundType.STONE)
                    .mapColor(MapColor.STONE)
                    .pushReaction(PushReaction.IMMOVEABLE)
                    .lightLevel((state) -> state.getValue(LodestoneWindCoreBlock.ACTIVE) ? 15 : 0)
            ),
            BlockBehaviour.Properties.of(),
            true
    );

    public static final DeferredBlock<Block> AIR_PLACEHOLDER = registerBlock("air_placeholder",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.AIR),
            true
    );
    public static final DeferredBlock<Block> FARMLAND_PLACER = registerBlock("farmland_placer",
            properties -> new FarmlandBlockPlacer(properties
                    .sound(SoundType.GRASS)
                    .mapColor(MapColor.DIRT)
                    .strength(1.0f, 1.0f)
            ),
            BlockBehaviour.Properties.of(),
            true
    );



    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        BLOCK_ITEMS.register(modEventBus);
        TOTVW.sendClassRegisterLog("Blocks");
    }

    public static class Util {
        public static DeferredBlock<Block> registerBlock(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean registerItem, Component... tooltips) {
            DeferredBlock<Block> block = BLOCKS.registerBlock(name, blockFactory, () -> settings);
            if (registerItem) {
                BLOCK_ITEMS.registerItem(name, properties -> new BlockItem(block.get(), properties.useBlockDescriptionPrefix()) {
                    @Override
                    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
                        for (var component : tooltips) {
                            builder.accept(component);
                        }
                        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
                    }
                });
            }
            return block;
        }

        static String descriptionId(String blockName) {
            return "block." + TOTVW.MOD_ID + "." + blockName;
        }

        static Optional<ResourceKey<LootTable>> lootTable(String blockName) {
            return Optional.of(ResourceKey.create(Registries.LOOT_TABLE, TOTVW.registerID("blocks/" + blockName)));
        }
    }
}