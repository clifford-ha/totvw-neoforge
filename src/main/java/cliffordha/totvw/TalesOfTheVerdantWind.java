package cliffordha.totvw;

import cliffordha.totvw.config.VWConfig;
import cliffordha.totvw.fluid.VWFluidTypes;
import cliffordha.totvw.networking.VWNetworking;
import cliffordha.totvw.registry.VWBlockEntityTypes;
import cliffordha.totvw.registry.VWBlocks;
import cliffordha.totvw.registry.VWCommands;
import cliffordha.totvw.registry.VWEffects;
import cliffordha.totvw.registry.VWEnchantments;
import cliffordha.totvw.registry.VWEntities;
import cliffordha.totvw.registry.VWFluids;
import cliffordha.totvw.registry.VWItems;
import cliffordha.totvw.registry.VWParticles;
import cliffordha.totvw.registry.VWPotionBrewing;
import cliffordha.totvw.registry.VWPotions;
import cliffordha.totvw.registry.VWSounds;
import cliffordha.totvw.registry.attachments.AttachmentUtil;
import cliffordha.totvw.registry.attachments.VWAttachments;
import cliffordha.totvw.world.VWBiomes;
import cliffordha.totvw.world.tree.VWRootPlacerTypes;
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
        VWConfig.load();
        VWBlocks.register(modEventBus);
        VWItems.register(modEventBus);

        VWEntities.register(modEventBus);
        VWBlockEntityTypes.register(modEventBus);

        VWFluids.register(modEventBus);
        VWFluidTypes.register(modEventBus);
        VWEffects.register(modEventBus);
        VWPotions.register(modEventBus);

        VWEnchantments.register();
        VWRootPlacerTypes.register(modEventBus);

        VWSounds.register(modEventBus);
        VWParticles.register(modEventBus);




        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(VWNetworking::onPlayerJoinEvents);
        NeoForge.EVENT_BUS.addListener(VWCommands::register);

        modEventBus.addListener(VWNetworking::registerPayloads);
        modEventBus.addListener(VWBiomes::registerBiomes);

        modContainer.registerConfig(ModConfig.Type.LOCAL, Config.SPEC);

        VWPotionBrewing.register();

        AttachmentUtil.registerAttachments(modEventBus);
        VWAttachments.register();

        VWConfig.save();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        TOTVW.sendInfo("HELLO from server starting");
    }
}