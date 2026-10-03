package cliffordha.totvw;

import cliffordha.totvw.entity.VWGlobalEntityBehaviors;
import cliffordha.totvw.networking.VWNetworking;
import cliffordha.totvw.registry.VWBlocks;
import cliffordha.totvw.registry.VWItems;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(TOTVW.MOD_ID)
public class TalesOfTheVerdantWind {
    public static final boolean IN_DEVELOPMENT = true;

    public TalesOfTheVerdantWind(IEventBus modEventBus, ModContainer modContainer) {
        VWBlocks.register(modEventBus);
        VWItems.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(VWNetworking::onPlayerJoinEvents);
        modEventBus.addListener(VWNetworking::registerPayloads);

        modContainer.registerConfig(ModConfig.Type.LOCAL, Config.SPEC);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        TOTVW.sendInfo("HELLO from server starting");
    }
}
