package cliffordha.totvw.loot;

import cliffordha.totvw.TOTVW;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class VWLootModifiers extends GlobalLootModifierProvider {
    public VWLootModifiers(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, TOTVW.MOD_ID);
    }

    public static Identifier ANCIENT_CITY_CHEST = Identifier.withDefaultNamespace("chests/ancient_city");
    public static Identifier BASTION_TREASURE_CHEST = Identifier.withDefaultNamespace("chests/bastion_treasure");
    public static Identifier TRIAL_CHAMBERS_OMINOUS_RARE = Identifier.withDefaultNamespace("chests/trial_chambers/reward_ominous_rare");
    public static Identifier TRIAL_CHAMBERS_OMINOUS_UNIQUE = Identifier.withDefaultNamespace("chests/trial_chambers/reward_ominous_unique");
    public static Identifier TRIAL_CHAMBERS_RARE = Identifier.withDefaultNamespace("chests/trial_chambers/reward_rare");
    public static Identifier VILLAGER_ARMORER = Identifier.withDefaultNamespace("chests/village/village_armorer");
    public static Identifier END_CITY_TREASURE_CHEST = Identifier.withDefaultNamespace("chests/end_city_treasure");
    public static Identifier DESERT_PYRAMID_CHEST = Identifier.withDefaultNamespace("chests/desert_pyramid");
    public static Identifier PILLAGER_OUTPOST_CHEST = Identifier.withDefaultNamespace("chests/pillager_outpost");

    @Override
    protected void start() {
        this.add("ancient_city_additional_loot",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(ANCIENT_CITY_CHEST).build())),
                        10, VWLootTables.ANCIENT_CITY_LOOTS));

        this.add("verixium_powder_from_trial_chambers",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(TRIAL_CHAMBERS_RARE).build())),
                        10, VWLootTables.VERIXIUM_POWDER_TRIAL));

        this.add("verixium_upgrade_template_from_village_armorer",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(VILLAGER_ARMORER).build())),
                        10, VWLootTables.VERIXIUM_UPGRADE_TEMPLATE_ARMORER));

        this.add("enchants_template_from_ominous_trials",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(TRIAL_CHAMBERS_OMINOUS_RARE).build())),
                        10, VWLootTables.ENCHANTS_TEMPLATE_TRIAL_OMINOUS
                ));

        this.add("wind_core_from_trial_chambers",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(TRIAL_CHAMBERS_OMINOUS_UNIQUE).build())),
                        10, VWLootTables.WIND_CORE_FROM_TRIAL_CHAMBERS
                ));

        this.add("pages_from_village_armorer",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(VILLAGER_ARMORER).build())),
                        10, VWLootTables.PAGES_FROM_VILLAGE_ARMORER
                ));

        this.add("pages_from_pillager_outpost",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(PILLAGER_OUTPOST_CHEST).build())),
                        10, VWLootTables.PAGES_FROM_PILLAGER_OUTPOST
                ));

        this.add("genesis_runestone_from_end_city",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(END_CITY_TREASURE_CHEST).build())),
                        10, VWLootTables.GENESIS_RUNESTONE_FROM_END_CITY
                ));

        this.add("havoc_runestone_from_bastion",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(BASTION_TREASURE_CHEST).build())),
                        10, VWLootTables.HAVOC_RUNESTONE_FROM_BASTION
                ));

        this.add("efflorescence_runestone_from_desert_pyramid",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(DESERT_PYRAMID_CHEST).build())),
                        10, VWLootTables.EFFLORESCENCE_RUNESTONE_FROM_DESERT_PYRAMID
                ));

        this.add("soul_runestone_fragment_from_ancient_pillars",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(VWLootTables.ID_ANCIENT_VERIXIUM_PILLARS).build())),
                        10, VWLootTables.SOUL_RUNESTONE_FRAGMENT_FROM_VERIXIUM_PILLARS
                ));

        this.add("soul_runestone_fragment_from_verdant_weaponsmith",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(VWLootTables.ID_VERDANT_VILLAGE_WEAPONSMITH).build())),
                        10, VWLootTables.SOUL_RUNESTONE_FRAGMENT_FROM_VERDANT_WEAPONSMITH
                ));

        this.add("page_from_verdant_camps",
                new AddTableLootModifier(Optional.of(Holder.direct(
                        new LootTableIdCondition.Builder(VWLootTables.ID_PAGE_FROM_VERDANT_CAMPS).build())),
                        10, VWLootTables.PAGE_FROM_VERDANT_CAMPS
                ));
    }
}
