package cliffordha.totvw;

import cliffordha.totvw.datagen.*;
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
		var lookupProvider = event.getReloadableLookupProvider();

		generator.addProvider(true, new VWModelProvider(output));
		generator.addProvider(true, new VWSoundsProvider(output));
		generator.addProvider(true, new VWEngLangProvider(output));

		event.createWorldRegistryObjects(VWDatapackProvider.WORLD_BUILDER);
		event.createReloadableRegistryObjects(VWDatapackProvider.RELOADABLE_BUILDER);

		generator.addProvider(true, new VWItemTags(output, lookupProvider));
		generator.addProvider(true, new VWBlockTags(output, lookupProvider));
		generator.addProvider(true, new VWBiomeTags(output, lookupProvider));
		generator.addProvider(true, new VWFluidTags(output, lookupProvider));
		generator.addProvider(true, new VWEnchantmentTags(output, lookupProvider));
		generator.addProvider(true, new VWDamageTypeTags(output, lookupProvider));
		generator.addProvider(true, new VWEntityTypeTags(output, lookupProvider));
		generator.addProvider(true, new VWVillagerTradeTags(output, lookupProvider));
	}

	@SubscribeEvent
	public static void gatherServerData(GatherDataEvent.Server event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		var lookupProvider = event.getReloadableLookupProvider();



	}
}