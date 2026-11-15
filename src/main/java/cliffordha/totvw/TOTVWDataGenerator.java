package cliffordha.totvw;

import cliffordha.totvw.datagen.*;
import cliffordha.totvw.datagen.villager.VWVillagerTrades;
import cliffordha.totvw.loot.VWBlockLootTableProvider;
import cliffordha.totvw.loot.VWLootModifiers;
import cliffordha.totvw.loot.VWLootTables;
import cliffordha.totvw.registry.VWEnchantments;
import cliffordha.totvw.tag.*;
import cliffordha.totvw.world.VWBiomeModifications;
import cliffordha.totvw.world.VWBiomes;
import cliffordha.totvw.worldgen.VWConfiguredFeatures;
import cliffordha.totvw.worldgen.VWPlacedFeatures;
import cliffordha.totvw.worldgen.dimension.VWDimensions;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = TOTVW.MOD_ID)
public class TOTVWDataGenerator {

	@SubscribeEvent
	public static void gatherClientData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		var provider = event.getLookupProvider();

		generator.addProvider(true, new VWModelProvider(output));

		generator.addProvider(true, new VWBlockTags(output, provider));
		generator.addProvider(true, new VWItemTags(output, provider));
		generator.addProvider(true, new VWFluidTags(output, provider));
		generator.addProvider(true, new VWEntityTypeTags(output, provider));
		generator.addProvider(true, new VWEnchantmentTags(output, provider));
		generator.addProvider(true, new VWVillagerTradeTags(output, provider));
		generator.addProvider(true, new VWBiomeTags(output, provider));
		generator.addProvider(true, new VWDamageTypeTags(output, provider));

		generator.addProvider(true, new VWSoundsProvider(output));
		generator.addProvider(true, new VWEngLangProvider(output));
		generator.addProvider(true, new VWLootModifiers(output, provider));
		generator.addProvider(true, new VWEquipmentAssetProvider(output));
		generator.addProvider(true, new VWDatamapProvider(output, provider));
		generator.addProvider(true, new VWAdvancements(output, provider));

		var datapackProvider = generator.addProvider(true, new VWDatapackProvider(output, provider));
		var datapackLookup = datapackProvider.getRegistryProvider();

		generator.addProvider(true, new LootTableProvider(output, Collections.emptySet(),
				List.of(
						new LootTableProvider.SubProviderEntry(VWBlockLootTableProvider::new, LootContextParamSets.BLOCK),
						new LootTableProvider.SubProviderEntry(VWLootTables::new, LootContextParamSets.ALL_PARAMS)
				), datapackLookup));
	}

	@SubscribeEvent
	public static void gatherServerData(GatherDataEvent.Server event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		var provider = event.getLookupProvider();
	}
}