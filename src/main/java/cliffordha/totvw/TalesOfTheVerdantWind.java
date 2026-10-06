package cliffordha.totvw;

import cliffordha.totvw.datagen.VWDamageTypes;
import cliffordha.totvw.fluid.VWFluidTypes;
import cliffordha.totvw.registry.*;
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
    public static final boolean IN_DEVELOPMENT = false;

    public TalesOfTheVerdantWind(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT);
        modContainer.registerConfig(ModConfig.Type.LOCAL, Config.SYNCED);

        VWBlocks.register(modEventBus);
        VWItems.register(modEventBus);
        VWCreativeTabs.register(modEventBus);

        VWEntities.register(modEventBus);
        VWBlockEntityTypes.register(modEventBus);

        VWFluids.register(modEventBus);
        VWFluidTypes.register(modEventBus);
        VWEffects.register(modEventBus);
        VWPotions.register(modEventBus);

        VWDamageTypes.register();
        VWEnchantments.register();

        VWRootPlacerTypes.register(modEventBus);

        VWSounds.register(modEventBus);
        VWParticles.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.addListener(VWCommands::register);

        modEventBus.addListener(VWBiomes::registerBiomes);

        VWPotionBrewing.register();

        AttachmentUtil.registerAttachments(modEventBus);
        VWAttachments.register();

        ClientConfig.register();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        TOTVW.sendInfo("HELLO from server starting");
    }
}