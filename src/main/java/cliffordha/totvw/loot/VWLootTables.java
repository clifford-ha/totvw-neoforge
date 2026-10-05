package cliffordha.totvw.loot;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.registry.VWBlocks;
import cliffordha.totvw.registry.VWEnchantments;
import cliffordha.totvw.registry.VWItems;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.registries.DeferredItem;

public class VWLootTables implements LootTableSubProvider {
    private static final Holder<ContextIntProvider> ONE_ROLL = ContextIntProviders.exactly(1);

    public static final ResourceKey<LootTable> PAGE_FROM_VERDANT_CAMPS = createFromPath("chests/verdant_camp_valuables");
    public static final ResourceKey<LootTable> SOUL_RUNESTONE_FRAGMENT_FROM_VERIXIUM_PILLARS = createFromPath("chests/verixium_pillar");
    public static final ResourceKey<LootTable> SOUL_RUNESTONE_FRAGMENT_FROM_VERDANT_WEAPONSMITH = createFromPath("chests/village/verdant/weaponsmith");

    public static final Identifier ID_PAGE_FROM_VERDANT_CAMPS = TOTVW.registerID("chests/verdant_camp_valuables");
    public static final Identifier ID_ANCIENT_VERIXIUM_PILLARS = TOTVW.registerID("chests/verixium_pillar");
    public static final Identifier ID_VERDANT_VILLAGE_WEAPONSMITH = TOTVW.registerID("chests/village/verdant/weaponsmith");

    public static final ResourceKey<LootTable> ANCIENT_CITY_LOOTS = createFromDefault("chests/ancient_city");
    public static final ResourceKey<LootTable> VERIXIUM_POWDER_TRIAL = createExtra("verixium_powder_trial");
    public static final ResourceKey<LootTable> VERIXIUM_UPGRADE_TEMPLATE_ARMORER = createExtra("verixium_upgrade_template_armorer");
    public static final ResourceKey<LootTable> ENCHANTS_TEMPLATE_TRIAL_OMINOUS = createExtra("enchants_template_trial_ominous");
    public static final ResourceKey<LootTable> WIND_CORE_FROM_TRIAL_CHAMBERS = createExtra("wind_core_from_trial_chambers");
    public static final ResourceKey<LootTable> PAGES_FROM_VILLAGE_ARMORER = createExtra("pages_from_village_armorer");
    public static final ResourceKey<LootTable> PAGES_FROM_PILLAGER_OUTPOST = createExtra("pages_from_pillager_outpost");
    public static final ResourceKey<LootTable> GENESIS_RUNESTONE_FROM_END_CITY = createExtra("genesis_runestone_from_end_city");
    public static final ResourceKey<LootTable> HAVOC_RUNESTONE_FROM_BASTION = createExtra("havoc_runestone_from_bastion");
    public static final ResourceKey<LootTable> EFFLORESCENCE_RUNESTONE_FROM_DESERT_PYRAMID = createExtra("efflorescence_runestone_from_desert_pyramid");

    private static LootPool.Builder addItemChance(DeferredItem<Item> item, int count, float chance) {
        return LootPool.lootPool()
                .setRolls(ONE_ROLL)
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(item))
                .apply(SetItemCountFunction
                        .setCount(ContextIntProviders
                                .exactly(count)));
    }
    private static LootPool.Builder addBlockChance(Block block, int count, float chance) {
        return LootPool.lootPool()
                .setRolls(ONE_ROLL)
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(block)
                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(count))));
    }
    private static LootPool.Builder addEnchantedBookChance(HolderGetter<Enchantment> provider, ResourceKey<Enchantment> ench, int lvl, float chance) {
        return LootPool.lootPool()
                .setRolls(ONE_ROLL)
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(Items.ENCHANTED_BOOK).apply(new SetEnchantmentsFunction.Builder()
                        .withEnchantment(provider.getOrThrow(ench), ContextIntProviders.exactly(lvl)))
                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))));
    }
    private static LootPool.Builder addEnchantedBookChance(HolderGetter<Enchantment> provider, ResourceKey<Enchantment> ench, int lvlA, int lvlB, float chance) {
        return LootPool.lootPool()
                .setRolls(ONE_ROLL)
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(Items.ENCHANTED_BOOK).apply(new SetEnchantmentsFunction.Builder()
                        .withEnchantment(provider.getOrThrow(ench), ContextIntProviders.between(lvlA, lvlB)))
                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))));
    }

    private static ResourceKey<LootTable> createFromPath(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(TOTVW.MOD_ID, path));
    }
    private static ResourceKey<LootTable> createFromDefault(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace(path));
    }
    private static ResourceKey<LootTable> createExtra(String path) {
        return ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace("extra/" + path));
    }

    private final LootTableSubProvider.Context context;

    public VWLootTables(LootTableSubProvider.Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        HolderGetter<Enchantment> provider = context.lookup(Registries.ENCHANTMENT);

        LootPool.Builder benedictionEnchantment = addEnchantedBookChance(provider, VWEnchantments.BENEDICTION_OF_THE_VERDANT_MOUNTAINS, 1, 0.05f);
        LootPool.Builder page1005 = addItemChance(VWItems.Pages.SP_ID_1005,1, 0.07f);
        LootPool.Builder verixiumTemplate = addItemChance(VWItems.VERIXIUM_ARMOR_UPGRADE_TEMPLATE,1, 0.07f);

        LootTable.Builder ANCIENT_CITY = LootTable.lootTable().withPool((benedictionEnchantment)).withPool((page1005)).withPool(verixiumTemplate);
        context.accept(ANCIENT_CITY_LOOTS, ANCIENT_CITY);

        context.accept(VERIXIUM_POWDER_TRIAL,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.VERIXIUM_POWDER, 3, 0.1f))
        );

        context.accept(VERIXIUM_UPGRADE_TEMPLATE_ARMORER,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.VERIXIUM_ARMOR_UPGRADE_TEMPLATE,1, 0.7f))
        );

        context.accept(ENCHANTS_TEMPLATE_TRIAL_OMINOUS,
                LootTable.lootTable().withPool(
                        addEnchantedBookChance(provider, VWEnchantments.WOLF_EFFECT_WITHERING, 1, 3, 0.1f))
                        .withPool(
                                addEnchantedBookChance(provider, VWEnchantments.WOLF_EFFECT_POISONING, 3, 5, 0.1f))
                        .withPool(
                                addEnchantedBookChance(provider, VWEnchantments.WOLF_EFFECT_MIGHT, 3, 5, 0.1f))
                        .withPool(
                                addItemChance(VWItems.VERIXIUM_ARMOR_UPGRADE_TEMPLATE,1, 0.6f)
                        )
        );

        context.accept(WIND_CORE_FROM_TRIAL_CHAMBERS,
                LootTable.lootTable().withPool(
                        addBlockChance(VWBlocks.LODESTONE_WIND_CORE.get(), 1, 0.12f))
        );

        context.accept(PAGES_FROM_VILLAGE_ARMORER,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.Pages.SP_ID_1003,1, 0.33f))
                        .withPool(
                                addItemChance(VWItems.Pages.SP_ID_1004,1, 0.33f))
        );

        context.accept(PAGES_FROM_PILLAGER_OUTPOST,
            LootTable.lootTable().withPool(
                    addItemChance(VWItems.Pages.SP_ID_1001,1, 0.33f))
                    .withPool(
                            addItemChance(VWItems.Pages.SP_ID_1002,1, 0.33f))
        );

        context.accept(GENESIS_RUNESTONE_FROM_END_CITY,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.GENESIS_RUNESTONE_PLATE,1, 0.07f)
                )
        );

        context.accept(HAVOC_RUNESTONE_FROM_BASTION,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.HAVOC_RUNESTONE_PLATE,1, 0.33f)
                )
        );

        context.accept(EFFLORESCENCE_RUNESTONE_FROM_DESERT_PYRAMID,
                LootTable.lootTable().withPool(
                    addItemChance(VWItems.EFFLORESCENCE_RUNESTONE_PLATE,1, 0.24f))
        );
        /*

        context.accept(SOUL_RUNESTONE_FRAGMENT_FROM_VERIXIUM_PILLARS,
            LootTable.lootTable().withPool(
                    addItemChance(VWItems.SOUL_RUNESTONE_FRAGMENT_1,1, 0.33f))
                    .withPool(
                            addItemChance(VWItems.SOUL_RUNESTONE_FRAGMENT_3,1, 0.33f))
        );

        context.accept(SOUL_RUNESTONE_FRAGMENT_FROM_VERDANT_WEAPONSMITH,
            LootTable.lootTable().withPool(
                    addItemChance(VWItems.SOUL_RUNESTONE_FRAGMENT_2,1, 0.33f))
                    .withPool(
                            addItemChance(VWItems.SOUL_RUNESTONE_FRAGMENT_4,1, 0.33f))
        );

        context.accept(PAGE_FROM_VERDANT_CAMPS,
            LootTable.lootTable().withPool(
                    addItemChance(VWItems.Pages.SP_ID_1006,1, 0.07f))
        );*/
    }
}
