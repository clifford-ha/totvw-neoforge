package cliffordha.totvw.datagen;

import cliffordha.totvw.Config;
import cliffordha.totvw.TOTVW;
import cliffordha.totvw.keymapping.VWKeymap;
import cliffordha.totvw.registry.*;
import cliffordha.totvw.registry.VWBlocks;
import cliffordha.totvw.registry.VWItems.Pages;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import static cliffordha.totvw.item.scatteredpages.ScatteredPageTitle.*;

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
        add(VWBlocks.VERDANT_SPRUCE_SHELF, "Verdant Spruce Shelf");
        add(VWBlocks.VERDANT_SPRUCE_STORAGE_BOX, "Verdant Spruce Storage Box");

        add(VWBlocks.VERIXIUM_FLUID.get(), "Verixium Fluid");
        add(VWBlocks.LODESTONE_WIND_CORE, "Lodestone Wind Core");

        
        // MISC BLOCKS
        add(VWBlocks.FARMLAND_PLACER, "Farmland Placer");
        
        
        // ENTITIES
        add(VWEntities.VERDANT_SPRUCE_BOAT.get(), "Verdant Spruce Boat");
        add(VWEntities.VERDANT_SPRUCE_CHEST_BOAT.get(), "Verdant Spruce Chest Boat");

        add(VWItems.VERDANT_SPRUCE_BOAT, "Verdant Spruce Boat");
        add(VWItems.VERDANT_SPRUCE_CHEST_BOAT, "Verdant Spruce Chest Boat");
        add(VWItems.VERDANT_SPRUCE_SIGN, "Verdant Spruce Sign");
        add(VWItems.VERDANT_SPRUCE_HANGING_SIGN, "Verdant Spruce Hanging Sign");
        
        
        add(VWItems.VERIXIUM_FLUID_BUCKET, "Verixium Fluid Bucket");
        add("verixium_fluid", "Verixium Fluid");
        add("flowing_verixium_fluid", "Flowing Verixium Fluid");


        add(VWItems.VERIXIUM_CHUNK, "Verixium Chunk");
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

        add(VWItems.VERIXIUM_SPEAR, "Verixium Spear");
        add(VWItems.VERIXIUM_SWORD, "Verixium Sword");
        add(VWItems.VERIXIUM_AXE, "Verixium Axe");
        add(VWItems.VERIXIUM_PICKAXE, "Verixium Pickaxe");
        add(VWItems.VERIXIUM_SHOVEL, "Verixium Shovel");
        add(VWItems.VERIXIUM_HOE, "Verixium Hoe");


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

        add(Pages.SP_ID_3000, SP_3000.getTitle());
        add(Pages.SP_ID_3001, SP_3001.getTitle());
        add(Pages.SP_ID_3002, SP_3002.getTitle());

        add(Pages.LODESTONE_WIND_CORE_MANUAL, LODESTONE_WIND_CORE_MANUAL.getTitle());


        // ENCHANTMENTS
        add(enchant(VWEnchantments.WOLF_EFFECT_IGNITION), "Wolf ATK Effect: §vIgnition");
        add(enchant(VWEnchantments.WOLF_EFFECT_POISONING), "Wolf ATK Effect: §cPoison");
        add(enchant(VWEnchantments.WOLF_EFFECT_WITHERING), "Wolf ATK Effect: §cWithering");
        add(enchant(VWEnchantments.WOLF_EFFECT_LIFTING), "Wolf ATK Effect: Lifting");
        add(enchant(VWEnchantments.WOLF_EFFECT_BLOODLUST), "Wolf ATK Effect: §cBloodlust");
        add(enchant(VWEnchantments.WOLF_EFFECT_MIGHT), "Wolf ATK Effect: §dMight");
        add(enchant(VWEnchantments.WOLF_EFFECT_OOZING), "Wolf ATK Effect: §aOozing");
        add(enchant(VWEnchantments.WOLF_EFFECT_GNAWING), "Wolf ATK Effect: §dGnawing");
        add(enchant(VWEnchantments.WOLF_ARMOR_ENHANCEMENT_KIT), "Wolf Armor Enhancement Kit");
        add(enchant(VWEnchantments.BENEDICTION_OF_THE_VERDANT_MOUNTAINS),"Benediction of the Verdant Mountains");


        // POTIONS
        add("effect.tales-of-the-verdant-wind.bloodlust.description", "Gives massive attack buff in exchange for constant damage while the effect is active");

        add("item.minecraft.potion.effect.sacred_verdant_potion", "Sacred Verdant Potion");
        add("item.minecraft.splash_potion.effect.sacred_verdant_potion", "Sacred Verdant Splash Potion");
        add("item.minecraft.lingering_potion.effect.sacred_verdant_potion", "Sacred Verdant Lingering Potion");

        add("item.minecraft.potion.effect.might_amplifier_potion", "Amplified Might Potion");
        add("item.minecraft.splash_potion.effect.might_amplifier_potion", "Amplified Might Splash Potion");
        add("item.minecraft.lingering_potion.effect.might_amplifier_potion", "Amplified Might Lingering Potion");

        add("item.minecraft.potion.effect.baleful_strength_potion", "Baleful Strength Potion");
        add("item.minecraft.splash_potion.effect.baleful_strength_potion", "Baleful Strength Splash Potion");
        add("item.minecraft.lingering_potion.effect.baleful_strength_potion", "Baleful Strength Lingering Potion");

        add("item.minecraft.tipped_arrow.effect.sacred_verdant_potion", "Arrow infused with Verdant Wind");
        add("item.minecraft.tipped_arrow.effect.might_amplifier_potion", "Arrow of Amplified Might");
        add("item.minecraft.tipped_arrow.effect.baleful_strength_potion", "Arrow of Baleful Strength");


        // DEATH DIALOGUE
        add("death.attack.bloodlust", "%1$s died from the agonizing effects of §cBloodlust§r");
        add("death.attack.bloodlust.player", "%1$s died from the agonizing effects of §cBloodlust§r while fighting %2$s");

        add("death.attack.scorching_heat", "%1$s died from scorching heat");
        add("death.attack.scorching_heat.player", "%1$s died from scorching heat while fighting %2$s");

        add("death.attack.bleeding", "%1$s bled to death");
        add("death.attack.bleeding.player", "%1$s bled to death while fighting %2$s");

        add("death.attack.wind_core_pulse", "%1$s got incapacitated by the Wind Core's pulse");
        add("death.attack.wind_core_pulse.player", "%1$s got incapacitated by the Wind Core's pulse while fighting %2$s");

        add("death.attack.havoc", "%1$s got their life drained by the effects of Havoc");
        add("death.attack.havoc.player", "%1$s got their life drained by the effects of Havoc while fighting %2$s");

        add("death.attack.tether_proxy", "%1$s got killed via proxy");
        add("death.attack.tether_proxy.player", "%1$s got killed via proxy while fighting %2$s");


        // BIOMES
        add(biomeKey("verdant_mountains"), "Verdant Mountains");
        add(biomeKey("verdant_forest"), "Verdant Forest");


        // EFFECTS
        add(effectKey("blessing_of_the_verdant_wind"), "Blessing of the Verdant Wind");
        add(effectKey("amplified_might"), "Amplified Might");
        add(effectKey("bloodlust"), "Bloodlust");
        add(effectKey("paralyze"), "Paralyzed");
        add(effectKey("wind_veil"), "Wind Veil");
        add(effectKey("havoc"), "Havoc");


        // SOUNDS
        add(VWSounds.WOLF_HOWL_A.toString(), "Distant wolf howls");
        add(VWSounds.WOLF_HOWL_B1.toString(), "Distant wolf howls");
        add(VWSounds.WOLF_HOWL_B2.toString(), "Distant wolf howls");
        add(VWSounds.WOLF_HOWL_B3.toString(), "Distant wolf howls");

        add(VWSounds.NOTIFY.toString(), "Notification popped");
        add(VWSounds.WOLF_SKILL_PARALYZE.toString(), "%1$s got paralyzed");

        add(VWSounds.LODESTONE_WIND_CORE_AMBIENT.toString(), "Wind Core whooshes");


        // KEYMAPPINGS
        add(VWKeymap.WOLF_CONFIG_KEY, "Wolf Config Screen");

        //MOD NAME
        add(TOTVW.MOD_ID, "Tales of the Verdant Wind");

        // MOD CONFIG
        add(config("clientTranslateLanguage"), "Client Translate Language");
        add(config("clientEnableNotifiers"), "Client Enable Notifiers");
        add(config("clientShowAtrocityCounter"), "Show Atrocity Counter");
        add(config("clientModSounds"), "Client Mod Sounds");
        add(config("clientAllowLoreSpoilers"), "Client Allow Lore Spoilers");
        add(config("clientAllowEffectOverlays"), "Client Allow Effect Overlays");
        add(config("clientBloodlustEffectOverlay"), "Client Bloodlust Effect Overlay");

        add(config("serverWolfDamageDistribution"), "Server Wolf Damage Distribution");

        add(config("serverBenedictionHealthThreshold"), "Server Benediction Health Threshold");
        add(config("serverWolfPlayerScanDistance"), "Server Wolf Player Scan Distance");
        add(config("serverAlwaysTriggerBlessing"), "Server Always Trigger Blessing");
        add(config("serverWolfSharesBenedictionStack"), "Server Wolf Shares Benediction Stack");
        add(config("serverTeleportAfterSave"), "Server Teleport After Save");
        add(config("serverPlayerTPMethod"), "Server Player TP Method");
        add(config("serverWolfTPMethod"), "Server Wolf TP Method");
        add(config("serverWolfTPAll"), "Server Wolf TP All");

        add(config("serverSkillCooldowns"), "Server Skill Cooldowns");
        add(config("serverItemCooldowns"), "Server Item Cooldowns");
        add(config("serverOtherCooldowns"), "Server Other Cooldowns");

        add(config("logEnchantmentShowWolfCD"), "Show Wolf CD");
        add(config("logEnchantmentShowPlayerCD"), "Show Player CD");

        //DEV
        if (TOTVW.IN_DEVELOPMENT) {
            add(VWItems.DevItems.ATTACHMENTS_REMOVER, "Attachments Remover");
            addConfigValue(Config.CLIENT_TRANSLATE_LANGUAGE, "Translate Language");
        }
    }
    
    private static String enchant(ResourceKey<Enchantment> value) {
        Identifier ofValue = value.identifier();
        String name = ofValue.toString().replace(":", ".");
        return "enchantment." + name;
    }
    private static String config(String value) {
        return TOTVW.MOD_ID + ".configuration." + value;
    }
    private static String biomeKey(String name) {
        return "biome." + TOTVW.MOD_ID + "." + name;
    }
    private static String effectKey(String name) {
        return "effect." + TOTVW.MOD_ID + "." + name;
    }
}