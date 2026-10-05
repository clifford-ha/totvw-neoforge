package cliffordha.totvw.registry;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.registry.VWItems.Pages;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VWCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TOTVW.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TOTVW_ITEMS_TAB_KEY = CREATIVE_MODE_TABS.register("items_tab", () -> CreativeModeTab.builder()
            .title(Component.literal(TOTVW.MOD_NAME_LONG).withColor(VWColors.VERDANT_WIND))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> VWItems.VERIXIUM_CHUNK.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(VWBlocks.VERIXIUM_STONE_ORE);
                output.accept(VWBlocks.VERIXIUM_DEEPSLATE_ORE);
                output.accept(VWBlocks.VERIXIUM_POWDER_BLOCK);
                output.accept(VWBlocks.VERDANT_MOSS_BLOCK);
                output.accept(VWBlocks.VERDANT_SPRUCE_LEAVES);
                output.accept(VWBlocks.VERDANT_SPRUCE_SAPLING);

                output.accept(VWBlocks.VERDANT_SPRUCE_LOG);
                output.accept(VWBlocks.VERDANT_SPRUCE_WOOD);
                output.accept(VWBlocks.STRIPPED_VERDANT_SPRUCE_LOG);
                output.accept(VWBlocks.STRIPPED_VERDANT_SPRUCE_WOOD);
                output.accept(VWBlocks.VERDANT_SPRUCE_PLANKS);
                output.accept(VWBlocks.VERDANT_SPRUCE_SHELF);
                output.accept(VWBlocks.VERDANT_SPRUCE_STORAGE_BOX);
                output.accept(VWBlocks.LODESTONE_WIND_CORE);
                output.accept(VWBlocks.VERDANT_SPRUCE_TRAPDOOR);
                output.accept(VWBlocks.VERDANT_SPRUCE_DOOR);
                output.accept(VWBlocks.VERDANT_SPRUCE_SLAB);
                output.accept(VWBlocks.VERDANT_SPRUCE_STAIRS);
                output.accept(VWBlocks.VERDANT_SPRUCE_FENCE);
                output.accept(VWBlocks.VERDANT_SPRUCE_FENCE_GATE);
                output.accept(VWItems.VERDANT_SPRUCE_SIGN);
                output.accept(VWItems.VERDANT_SPRUCE_HANGING_SIGN);
                output.accept(VWBlocks.VERDANT_SPRUCE_BUTTON);
                output.accept(VWBlocks.VERDANT_SPRUCE_PRESSURE_PLATE);
                output.accept(VWItems.VERDANT_SPRUCE_BOAT);
                output.accept(VWItems.VERDANT_SPRUCE_CHEST_BOAT);

                output.accept(VWBlocks.IRIDESCENT_GLASS);
                output.accept(VWBlocks.IRIDESCENT_GLASS_PANE);

                output.accept(VWItems.VERIXIUM_CHUNK);
                output.accept(VWItems.CONDENSED_VERIXIUM);
                output.accept(VWItems.VERIXIUM_SHARD);
                output.accept(VWItems.VERIXIUM_POWDER);
                output.accept(VWItems.VERIXIUM_INGOT);
                output.accept(VWItems.VERIXIUM_FLUID_BUCKET);
                output.accept(VWItems.VERIXIUM_PAPER);
                output.accept(VWItems.VERIXIUM_ARMOR_UPGRADE_TEMPLATE);

                output.accept(VWItems.GENESIS_RUNESTONE_PLATE);
                output.accept(VWItems.EFFLORESCENCE_RUNESTONE_PLATE);
                output.accept(VWItems.SOUL_RUNESTONE_PLATE);
                output.accept(VWItems.TETHER_RUNESTONE_PLATE);
                output.accept(VWItems.HAVOC_RUNESTONE_PLATE);

                output.accept(VWItems.VERIXIUM_HELMET);
                output.accept(VWItems.VERIXIUM_CHESTPLATE);
                output.accept(VWItems.VERIXIUM_LEGGINGS);
                output.accept(VWItems.VERIXIUM_BOOTS);

                output.accept(VWItems.VERIXIUM_WOLF_ARMOR);
                output.accept(VWItems.VERIXIUM_HORSE_ARMOR);

                output.accept(VWItems.VERIXIUM_SPEAR);
                output.accept(VWItems.VERIXIUM_SWORD);
                output.accept(VWItems.VERIXIUM_AXE);
                output.accept(VWItems.VERIXIUM_PICKAXE);
                output.accept(VWItems.VERIXIUM_HOE);
                output.accept(VWItems.VERIXIUM_SHOVEL);

                output.accept(VWItems.SOUL_RUNESTONE_FRAGMENT_1);
                output.accept(VWItems.SOUL_RUNESTONE_FRAGMENT_2);
                output.accept(VWItems.SOUL_RUNESTONE_FRAGMENT_3);
                output.accept(VWItems.SOUL_RUNESTONE_FRAGMENT_4);
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TOTVW_SCATTERED_PAGES_TAB_KEY = CREATIVE_MODE_TABS.register("scattered_pages", () -> CreativeModeTab.builder()
            .title(Component.literal("Scattered Pages"))
            .withTabsBefore(TOTVW_ITEMS_TAB_KEY.getId())
            .icon(() -> Pages.OLD_SCATTERED_PAGE.get().getDefaultInstance())
            .displayItems(((parameters, output) -> {
                if (TOTVW.IN_DEVELOPMENT) {
                    output.accept(Pages.SCATTERED_PAGE);
                    output.accept(Pages.OLD_SCATTERED_PAGE);

                    output.accept(Pages.PLAYER_STATS);
                    output.accept(Pages.SP_ID_TEST);
                    output.accept(Pages.SP_ID_1000);
                }
                output.accept(Pages.ENCHANTMENTS_HANDBOOK);
                output.accept(Pages.EFFECTS_HANDBOOK);
                output.accept(Pages.ITEMS_HANDBOOK);
                output.accept(Pages.FEATURES_HANDBOOK);

                output.accept(Pages.SP_ID_3000);
                output.accept(Pages.SP_ID_3001);
                output.accept(Pages.SP_ID_3002);

                output.accept(Pages.SP_ID_1001);
                output.accept(Pages.SP_ID_1002);
                output.accept(Pages.SP_ID_1003);
                output.accept(Pages.SP_ID_1004);
                output.accept(Pages.SP_ID_1005);
                output.accept(Pages.SP_ID_1006);

                output.accept(Pages.LODESTONE_WIND_CORE_MANUAL);
            })).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
