package cliffordha.totvw;

import cliffordha.totvw.datagen.*;
import cliffordha.totvw.loot.VWLootModifiers;
import cliffordha.totvw.tag.*;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = TOTVW.MOD_ID)
public class TOTVWDataGenerator {
	@SubscribeEvent
	public static void gatherClientData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		var provider = event.getReloadableLookupProvider();

		event.createWorldRegistryObjects(VWDatapackProvider.WORLD_BUILDER);
		event.createReloadableRegistryObjects(VWDatapackProvider.RELOADABLE_BUILDER);

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
	}

	@SubscribeEvent
	public static void gatherServerData(GatherDataEvent.Server event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		var provider = event.getReloadableLookupProvider();
	}
}