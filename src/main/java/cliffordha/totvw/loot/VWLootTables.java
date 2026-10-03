package cliffordha.totvw.loot;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.registry.VWBlocks;
import cliffordha.totvw.registry.VWEnchantments;
import cliffordha.totvw.registry.VWItems;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
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

    public static final ResourceKey<LootTable> VERDANT_CAMP_VALUABLES = createFromPath("chests/verdant_camp_valuables");
    public static final ResourceKey<LootTable> VERIXIUM_PILLAR = createFromPath("chests/verixium_pillar");
    public static final ResourceKey<LootTable> VERDANT_VILLAGE_WEAPONSMITH = createFromPath("chests/village/verdant/weaponsmith");

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
    private static LootPool.Builder addEnchantedBookChance(HolderLookup.Provider provider, ResourceKey<Enchantment> ench, int lvl, float chance) {
        return LootPool.lootPool()
                .setRolls(ONE_ROLL)
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(Items.ENCHANTED_BOOK).apply(new SetEnchantmentsFunction.Builder()
                        .withEnchantment(provider.getOrThrow(ench), ContextIntProviders.exactly(lvl)))
                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1))));
    }
    private static LootPool.Builder addEnchantedBookChance(HolderLookup.Provider provider, ResourceKey<Enchantment> ench, int lvlA, int lvlB, float chance) {
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

    private final LootTableSubProvider.Context context;

    public VWLootTables(LootTableSubProvider.Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        HolderLookup.Provider provider = (HolderLookup.Provider) context.holderLookup(Registries.LOOT_TABLE).orElseThrow();

        LootPool.Builder benedictionEnchantment = addEnchantedBookChance(provider, VWEnchantments.BENEDICTION_OF_THE_VERDANT_MOUNTAINS, 1, 0.05f);
        LootPool.Builder page1005 = addItemChance(VWItems.Pages.SP_ID_1005,1, 0.07f);
        LootPool.Builder verixiumTemplate = addItemChance(VWItems.VERIXIUM_ARMOR_UPGRADE_TEMPLATE,1, 0.07f);

        LootTable.Builder ANCIENT_CITY = LootTable.lootTable().withPool((benedictionEnchantment)).withPool((page1005)).withPool(verixiumTemplate);
        context.accept(BuiltInLootTables.ANCIENT_CITY, ANCIENT_CITY);


        context.accept(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_RARE,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.VERIXIUM_ARMOR_UPGRADE_TEMPLATE,1, 0.6f))
        );

        context.accept(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_RARE,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.VERIXIUM_POWDER, 3, 0.1f))
        );

        context.accept(BuiltInLootTables.ARMORER_GIFT,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.VERIXIUM_ARMOR_UPGRADE_TEMPLATE,1, 0.7f))
        );

        context.accept(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_RARE,
                LootTable.lootTable().withPool(
                        addEnchantedBookChance(provider, VWEnchantments.WOLF_EFFECT_WITHERING, 1, 3, 0.1f))
                        .withPool(
                                addEnchantedBookChance(provider, VWEnchantments.WOLF_EFFECT_POISONING, 3, 5, 0.1f))
                        .withPool(
                                addEnchantedBookChance(provider, VWEnchantments.WOLF_EFFECT_MIGHT, 3, 5, 0.1f))
        );

        context.accept(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS_UNIQUE,
                LootTable.lootTable().withPool(
                        addBlockChance(VWBlocks.LODESTONE_WIND_CORE.get(), 1, 0.12f))
        );

        context.accept(BuiltInLootTables.VILLAGE_ARMORER,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.Pages.SP_ID_1003,1, 0.33f))
                        .withPool(
                                addItemChance(VWItems.Pages.SP_ID_1004,1, 0.33f))
        );

        context.accept(BuiltInLootTables.PILLAGER_OUTPOST,
            LootTable.lootTable().withPool(
                    addItemChance(VWItems.Pages.SP_ID_1001,1, 0.33f))
                    .withPool(
                            addItemChance(VWItems.Pages.SP_ID_1002,1, 0.33f))
        );

        context.accept(BuiltInLootTables.END_CITY_TREASURE,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.GENESIS_RUNESTONE_PLATE,1, 0.07f)
                )
        );

        context.accept(BuiltInLootTables.BASTION_TREASURE,
                LootTable.lootTable().withPool(
                        addItemChance(VWItems.HAVOC_RUNESTONE_PLATE,1, 0.33f)
                )
        );

        context.accept(BuiltInLootTables.DESERT_PYRAMID,
                LootTable.lootTable().withPool(
                    addItemChance(VWItems.EFFLORESCENCE_RUNESTONE_PLATE,1, 0.24f))
        );


        context.accept(VERIXIUM_PILLAR,
            LootTable.lootTable().withPool(
                    addItemChance(VWItems.SOUL_RUNESTONE_FRAGMENT_1,1, 0.33f))
                    .withPool(
                            addItemChance(VWItems.SOUL_RUNESTONE_FRAGMENT_3,1, 0.33f))
        );

        context.accept(VERDANT_VILLAGE_WEAPONSMITH,
            LootTable.lootTable().withPool(
                    addItemChance(VWItems.SOUL_RUNESTONE_FRAGMENT_2,1, 0.33f))
                    .withPool(
                            addItemChance(VWItems.SOUL_RUNESTONE_FRAGMENT_4,1, 0.33f))
        );

        context.accept(VERDANT_CAMP_VALUABLES,
            LootTable.lootTable().withPool(
                    addItemChance(VWItems.Pages.SP_ID_1006,1, 0.07f))
        );
    }
}
