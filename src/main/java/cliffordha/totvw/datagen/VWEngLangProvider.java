package cliffordha.totvw.datagen;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.client.VWKeymap;
import cliffordha.totvw.registry.*;
import cliffordha.totvw.registry.VWBlocks;
import cliffordha.totvw.registry.VWItems.Pages;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import static cliffordha.totvw.item.pages.ScatteredPageTitle.*;

public class VWEngLangProvider extends LanguageProvider {
    public VWEngLangProvider(PackOutput output) {
        super(output, TOTVW.MOD_ID, "en_us");
    }

    public void add(DeferredItem<Item> key, String name) {
        super.add(key.get(), name);
    }
    public void add(DeferredBlock<Block> key, String name) {
        super.add(key.get(), name);
    }

    @Override
    protected void addTranslations() {
        add(VWBlocks.VERIXIUM_DEEPSLATE_ORE, "Verixium Deepslate Ore");
        add(VWBlocks.VERIXIUM_STONE_ORE, "Verixium Stone Ore");
        add(VWBlocks.VERIXIUM_POWDER_BLOCK, "Verixium Powder Block");
        add(VWBlocks.VERDANT_MOSS_BLOCK, "Verdant Moss Block");
        add(VWBlocks.VERDANT_SPRUCE_LEAVES, "Verdant Spruce Leaves");
        add(VWBlocks.VERDANT_SPRUCE_SAPLING, "Verdant Spruce Sapling");
        add(VWBlocks.POTTED_VERDANT_SPRUCE_SAPLING, "Potted Verdant Spruce Sapling");

        add(VWBlocks.IRIDESCENT_GLASS, "Iridescent Glass");
        add(VWBlocks.IRIDESCENT_GLASS_PANE, "Iridescent Glass Pane");

        add(VWBlocks.VERDANT_SPRUCE_LOG, "Verdant Spruce Log");
        add(VWBlocks.VERDANT_SPRUCE_WOOD, "Verdant Spruce Wood");
        add(VWBlocks.STRIPPED_VERDANT_SPRUCE_LOG, "Stripped Verdant Spruce Log");
        add(VWBlocks.STRIPPED_VERDANT_SPRUCE_WOOD, "Stripped Verdant Spruce Wood");
        add(VWBlocks.VERDANT_SPRUCE_PLANKS, "Verdant Spruce Planks");
        add(VWBlocks.VERDANT_SPRUCE_SLAB, "Verdant Spruce Slab");
        add(VWBlocks.VERDANT_SPRUCE_STAIRS, "Verdant Spruce Stairs");
        add(VWBlocks.VERDANT_SPRUCE_FENCE, "Verdant Spruce Fence");
        add(VWBlocks.VERDANT_SPRUCE_FENCE_GATE, "Verdant Spruce Fence Gate");
        add(VWBlocks.VERDANT_SPRUCE_BUTTON, "Verdant Spruce Button");
        add(VWBlocks.VERDANT_SPRUCE_PRESSURE_PLATE, "Verdant Spruce Pressure Plate");
        add(VWBlocks.VERDANT_SPRUCE_DOOR, "Verdant Spruce Door");
        add(VWBlocks.VERDANT_SPRUCE_TRAPDOOR, "Verdant Spruce Trapdoor");
        add(VWItems.VERDANT_SPRUCE_SIGN, "Verdant Spruce Sign");
        add(VWItems.VERDANT_SPRUCE_HANGING_SIGN, "Verdant Spruce Hanging Sign");
        add(VWBlocks.VERDANT_SPRUCE_SHELF, "Verdant Spruce Shelf");
        add(VWBlocks.VERDANT_SPRUCE_STORAGE_BOX, "Verdant Spruce Storage Box");

        add(VWBlocks.VERIXIUM_FLUID.get(), "Verixium Fluid");

        add("container.tales-of-the-verdant-wind.storage_box", "Storage Box");
        add(VWBlocks.LODESTONE_WIND_CORE, "Lodestone Wind Core");

        add(VWItems.VERDANT_SPRUCE_BOAT, "Verdant Spruce Boat");
        add(VWItems.VERDANT_SPRUCE_CHEST_BOAT, "Verdant Spruce Chest Boat");

        add(VWEntities.VERDANT_SPRUCE_BOAT.get(), "Verdant Spruce Boat");
        add(VWEntities.VERDANT_SPRUCE_CHEST_BOAT.get(), "Verdant Spruce Chest Boat");


        add(VWItems.VERIXIUM_FLUID_BUCKET, "Verixium Fluid Bucket");
        add("verixium_fluid", "Verixium Fluid");
        add("flowing_verixium_fluid", "Flowing Verixium Fluid");


        add(VWItems.VERIXIUM_CHUNK, "Verixium Chunk");
        add(VWItems.VERIXIUM_SEDIMENT, "Verixium Sediment");
        add(VWItems.CONDENSED_VERIXIUM, "Condensed Verixium");
        add(VWItems.VERIXIUM_SHARD, "Verixium Shard");
        add(VWItems.VERIXIUM_POWDER, "Verixium Powder");
        add(VWItems.VERIXIUM_INGOT, "Verixium Ingot");

        add(VWItems.VERIXIUM_PAPER, "Verixium Paper");


        add(VWItems.VERIXIUM_HELMET, "Verixium Helmet");
        add(VWItems.VERIXIUM_CHESTPLATE, "Verixium Chestplate");
        add(VWItems.VERIXIUM_LEGGINGS, "Verixium Leggings");
        add(VWItems.VERIXIUM_BOOTS, "Verixium Boots");

        add(VWItems.VERIXIUM_WOLF_ARMOR, "Verixium Wolf Armor");
        add(VWItems.VERIXIUM_ARMOR_UPGRADE_TEMPLATE, "Verixium Armor Upgrade Template");

        add(VWItems.VERIXIUM_HORSE_ARMOR, "Verixium Horse Armor");

        add(VWItems.SOUL_RUNESTONE_PLATE, "Soul Runestone Plate");
        add(VWItems.SOUL_RUNESTONE_FRAGMENT_1, "Soul Runestone Fragment (TL)");
        add(VWItems.SOUL_RUNESTONE_FRAGMENT_2, "Soul Runestone Fragment (TR)");
        add(VWItems.SOUL_RUNESTONE_FRAGMENT_3, "Soul Runestone Fragment (BL)");
        add(VWItems.SOUL_RUNESTONE_FRAGMENT_4, "Soul Runestone Fragment (BR)");
        add(VWItems.TETHER_RUNESTONE_PLATE, "Tether Runestone Plate");
        add(VWItems.GENESIS_RUNESTONE_PLATE, "Genesis Runestone Plate");
        add(VWItems.HAVOC_RUNESTONE_PLATE, "Havoc Runestone Plate");
        add(VWItems.EFFLORESCENCE_RUNESTONE_PLATE, "Efflorescence Runestone Plate");

        // MISC BLOCKS
        add(VWBlocks.FARMLAND_PLACER, "Farmland Placer");


        String SCATTERED_PAGE = "Scattered Page";
        add(Pages.SCATTERED_PAGE, SCATTERED_PAGE);

        String OLD_SCATTERED_PAGE = "Old Scattered Page";
        add(Pages.OLD_SCATTERED_PAGE, OLD_SCATTERED_PAGE);

        add(Pages.ENCHANTMENTS_HANDBOOK, "Enchantments Handbook");
        add(Pages.EFFECTS_HANDBOOK, "Effects Handbook");
        add(Pages.ITEMS_HANDBOOK, "Items Handbook");
        add(Pages.FEATURES_HANDBOOK, "Features Handbook");

        add(Pages.PLAYER_STATS, "Player Stats");
        add(Pages.SP_ID_TEST, "Test Page");
        add(Pages.SP_ID_1000, SP_1000.getTitle());

        add(Pages.SP_ID_1001, SP_1001.getTitle());
        add(Pages.SP_ID_1002, SP_1002.getTitle());
        add(Pages.SP_ID_1003, SP_1003.getTitle());
        add(Pages.SP_ID_1004, SP_1004.getTitle());

        add(Pages.SP_ID_1005, SP_1005.getTitle());

        add(Pages.SP_ID_1006, SP_1006.getTitle());

        add(Pages.SP_ID_1007, SP_1007.getTitle());
        add(Pages.SP_ID_1008, SP_1008.getTitle());

        add(Pages.SP_ID_3000, SP_3000.getTitle());
        add(Pages.SP_ID_3001, SP_3001.getTitle());
        add(Pages.SP_ID_3002, SP_3002.getTitle());

        add(Pages.LODESTONE_WIND_CORE_MANUAL, LODESTONE_WIND_CORE_MANUAL.getTitle());



        add(VWEnchantments.KEY_WOLF_EFFECT_IGNITION, "Wolf Effect: Ignition");
        add(VWEnchantments.KEY_WOLF_EFFECT_POISONING, "Wolf Effect: Poison");
        add(VWEnchantments.KEY_WOLF_EFFECT_WITHERING, "Wolf Effect: Withering");
        add(VWEnchantments.KEY_WOLF_EFFECT_LIFTING, "Wolf Effect: Lifting");
        add(VWEnchantments.KEY_WOLF_EFFECT_BLOODLUST, "Wolf Effect: Bloodlust");
        add(VWEnchantments.KEY_WOLF_EFFECT_MIGHT, "Wolf Effect: Might");
        add(VWEnchantments.KEY_WOLF_EFFECT_OOZING, "Wolf Effect: Oozing");
        add(VWEnchantments.KEY_WOLF_EFFECT_GNAWING, "Wolf Effect: Gnawing");
        add(VWEnchantments.KEY_WOLF_EFFECT_ARCTIC_WINDS, "Wolf Effect: Arctic Winds");
        add(VWEnchantments.KEY_WOLF_ARMOR_ENHANCEMENT_KIT, "Wolf Armor Enhancement Kit");
        add(VWEnchantments.KEY_BENEDICTION,"Benediction of the Verdant Mountains");


        add(effectKey("blessing_of_the_verdant_wind"), "Blessing of the Verdant Wind");
        add(effectKey("amplified_might"), "Amplified Might");
        add(effectKey("bloodlust"), "Bloodlust");
        add(effectKey("paralyze"), "Paralyzed");
        add(effectKey("wind_veil"), "Wind Veil");
        add(effectKey("havoc"), "Havoc");

        add("effect.tales-of-the-verdant-wind.bloodlust.description", "Gives massive attack buff in exchange for constant damage while the effect is active");

        add(potionKey("sacred_verdant_potion"), "Sacred Verdant Potion");
        add(potionSplashKey("sacred_verdant_potion"), "Sacred Verdant Splash Potion");
        add(potionLingeringKey("sacred_verdant_potion"), "Sacred Verdant Lingering Potion");
        add(arrowKey("sacred_verdant_potion"), "Arrow infused with Verdant Wind");

        HashMap<String, String> potionTranslations = new HashMap<>();
        potionTranslations.put("might_amplifier_potion", "Amplified Might");
        potionTranslations.put("baleful_strength_potion", "Baleful Strength");

        potionTranslations(potionTranslations);

        HashMap<String, List<String>> deathTranslations = new HashMap<>();
        deathTranslations.put("bloodlust", List.of(
                "%1$s died from the agonizing effects of §cBloodlust§r",
                "%1$s died from the agonizing effects of §cBloodlust§r while fighting %2$s"
        ));
        deathTranslations.put("scorching_heat", List.of(
                "%1$s died from scorching heat",
                "%1$s died from scorching heat while fighting %2$s"
        ));
        deathTranslations.put("bleeding", List.of(
                "%1$s bled to death",
                "%1$s bled to death while fighting %2$s"
        ));
        deathTranslations.put("wind_core_pulse", List.of(
                "%1$s got incapacitated by the Wind Core's pulse",
                "%1$s got incapacitated by the Wind Core's pulse while fighting %2$s"
        ));
        deathTranslations.put("havoc", List.of(
                "%1$s got their life drained by the effects of Havoc",
                "%1$s got their life drained by the effects of Havoc while fighting %2$s"
        ));
        deathTranslations.put("tether_proxy", List.of(
                "%1$s got killed via proxy",
                "%1$s got killed via proxy while fighting %2$s"
        ));
        deathTranslations.put("thermal_burn", List.of(
                "%1$s got thermally burnt",
                "%1$s got thermally burnt while fighting %2$s"
        ));
        deathTranslations.put("winterbreeze_shock", List.of(
                "%1$s chilled to death from the effects of Winterbreeze Shock",
                "%1$s chilled to death from the effects of Winterbreeze Shock while fighting %2$s"
        ));

        deathTranslationsBuilder(deathTranslations);


        add(VWItems.VERIXIUM_SPEAR, "Verixium Spear");
        add(VWItems.VERIXIUM_SWORD, "Verixium Sword");
        add(VWItems.VERIXIUM_AXE, "Verixium Axe");
        add(VWItems.VERIXIUM_PICKAXE, "Verixium Pickaxe");
        add(VWItems.VERIXIUM_SHOVEL, "Verixium Shovel");
        add(VWItems.VERIXIUM_HOE, "Verixium Hoe");


        add(biomeKey("verdant_mountains"), "Verdant Mountains");
        add(biomeKey("verdant_forest"), "Verdant Forest");


        // SOUNDS
        addSound(VWSounds.WOLF_HOWL_A, "Distant wolf howls");
        addSound(VWSounds.WOLF_HOWL_B1, "Distant wolf howls");
        addSound(VWSounds.WOLF_HOWL_B2, "Distant wolf howls");
        addSound(VWSounds.WOLF_HOWL_B3, "Distant wolf howls");

        addSound(VWSounds.NOTIFY, "Notification popped");
        addSound(VWSounds.WOLF_SKILL_PARALYZE, "%1$s got paralyzed");

        addSound(VWSounds.LODESTONE_WIND_CORE_AMBIENT, "Wind Core whooshes");



        // KEYMAPPINGS
        add(VWKeymap.TOTVW_CONFIG_SCREEN, "TOTVW Config Screen");
        add(VWKeymap.TOTVW_STAT_SCREEN, "TOTVW Stats Screen");

        add(TOTVW.MOD_ID, "Tales of the Verdant Wind");
        add(VWItems.EMPTY, "");

        //DEV
        if (TOTVW.IN_DEVELOPMENT) {
            add(VWItems.DevItems.ATTACHMENTS_REMOVER, "Attachments Remover");
        }
    }

    private static String biomeKey(String name) {
        return "biome." + TOTVW.MOD_ID + "." + name;
    }
    private static String effectKey(String name) {
        return "effect." + TOTVW.MOD_ID + "." + name;
    }
    private static String deathKey(String name) {
        return "death.attack." + name;
    }
    private static String deathPlayerKey(String name) {
        return "death.attack." + name + ".player";
    }
    private void deathTranslationsBuilder(HashMap<String, List<String>> deaths) {
        for (String death : deaths.keySet()) {
            super.add(deathKey(death), deaths.get(death).getFirst());
            super.add(deathPlayerKey(death), deaths.get(death).get(1));
        }
    }

    private static String potionKey(String name) {
        return "item.minecraft.potion.effect." + name;
    }
    private static String potionSplashKey(String name) {
        return "item.minecraft.splash_potion.effect." + name;
    }
    private static String potionLingeringKey(String name) {
        return "item.minecraft.lingering_potion.effect." + name;
    }
    private static String arrowKey(String name) {
        return "item.minecraft.tipped_arrow.effect." + name;
    }
    private void potionTranslations(HashMap<String, String> potions) {
        for (String potion : potions.keySet()) {
            super.add(potionKey(potion), potions.get(potion) + " Potion");
            super.add(potionSplashKey(potion), potions.get(potion) + " Splash Potion");
            super.add(potionLingeringKey(potion), potions.get(potion) + " Lingering Potion");
            super.add(arrowKey(potion), "Arrow of " + potions.get(potion));
        }
    }
    private void addSound(Supplier<SoundEvent> sound, String name) {
        String key = BuiltInRegistries.SOUND_EVENT.getKey(sound.get()).toLanguageKey().replace("/", ".");
        super.add(key, name);
    }
}