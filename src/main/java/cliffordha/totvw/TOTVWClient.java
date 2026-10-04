package cliffordha.totvw;

import cliffordha.totvw.client.VWModelLayerProvider;
import cliffordha.totvw.client.VWTooltips;
import cliffordha.totvw.config.VWConfig;
import cliffordha.totvw.config.VWConfigScreen;
import cliffordha.totvw.fluid.VWFluidTypes;
import cliffordha.totvw.keymapping.VWKeymap;
import cliffordha.totvw.particle.BenedictionTriggerParticle;
import cliffordha.totvw.particle.VerdantBiomesEnvironmentAmbiance;
import cliffordha.totvw.particle.VerixiumPowderRainParticle;
import cliffordha.totvw.registry.*;
import cliffordha.totvw.util.VWEffectOverlays;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.ShelfRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.sprite.Material;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = TOTVW.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = TOTVW.MOD_ID, value = Dist.CLIENT)
public class TOTVWClient {
    public TOTVWClient(ModContainer container) {
        //container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) -> VWConfigScreen.create(parent));

        NeoForge.EVENT_BUS.addListener(VWTooltips::register);
        //NeoForge.EVENT_BUS.addListener(VWEffectOverlays::register);

        VWKeymap.register();
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(VWEntities.VERDANT_SPRUCE_BOAT.get(), context -> new BoatRenderer(context, VWModelLayerProvider.VERDANT_SPRUCE_BOAT));
        EntityRenderers.register(VWEntities.VERDANT_SPRUCE_CHEST_BOAT.get(), context -> new BoatRenderer(context, VWModelLayerProvider.VERDANT_SPRUCE_CHEST_BOAT));
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(VWModelLayerProvider.VERDANT_SPRUCE_BOAT, BoatModel::createBoatModel);
        event.registerLayerDefinition(VWModelLayerProvider.VERDANT_SPRUCE_CHEST_BOAT, BoatModel::createChestBoatModel);
    }

    @SubscribeEvent
    public static void registerKeymapping(RegisterKeyMappingsEvent event) {
        event.register(VWKeymap.WOLF_CONFIG_PRESSED.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (VWKeymap.WOLF_CONFIG_PRESSED.get().consumeClick()) {
            // IN HERE: WE ARE ON THE CLIENT!
            //Minecraft.getInstance().player.sendSystemMessage(Component.literal("I have " + Minecraft.getInstance().player.getData(ModAttachmentTypes.MANA) + " Mana"));
            //ClientPacketDistributor.sendToServer(new TestPacketC2S("Kaupenjoe", 67));
        }
    }

    @SubscribeEvent
    public static void registerHUD(RegisterGuiLayersEvent event) {
    }

    @SubscribeEvent
    public static void registerColoredBlocks(RegisterColorHandlersEvent.BlockTintSources event) {
        //event.register(List.of(BlockTintSources.foliage()), ModBlocks.COLORED_LEAVES.get());
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(VWParticles.BENEDICTION_TRIGGER_PARTICLE.get(), BenedictionTriggerParticle.BenedictionParticleProvider::new);
        event.registerSpriteSet(VWParticles.VERDANT_BIOMES_ENVIRONMENT_AMBIANCE.get(), VerdantBiomesEnvironmentAmbiance.VerdantBiomesEnvironmentAmbianceProvider::new);
        event.registerSpriteSet(VWParticles.VERIXIUM_POWDER_RAIN_PARTICLE.get(), VerixiumPowderRainParticle.VerixiumPowderRainParticleProvider::new);
        event.registerSpriteSet(VWParticles.MIGHT_PARALYZE_PARTICLE.get(), VerixiumPowderRainParticle.VerixiumPowderRainParticleProvider::new);
    }

    @SubscribeEvent
    public static void registerOnClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(VWFluidTypes.VERIXIUM_FLUID_EXTENSION, VWFluidTypes.VERIXIUM_FLUID_TYPE.get());
    }

    @SubscribeEvent
    public static void registerFluidModelsEvent(RegisterFluidModelsEvent event) {
        FluidModel.Unbaked zirconWaterModel = new FluidModel.Unbaked(
                new Material(TOTVW.registerID("block/water_still")),
                new Material(TOTVW.registerID("block/water_flow")),
                new Material(TOTVW.registerID("block/water_overlay")),
                BlockTintSources.constant(VWColors.setColor(0x13e1a8)));

        event.register(zirconWaterModel, VWFluids.VERIXIUM_FLUID.get());
        event.register(zirconWaterModel, VWFluids.FLOWING_VERIXIUM_FLUID.get());
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(VWBlockEntityTypes.SIGN.get(), StandingSignRenderer::new);
        event.registerBlockEntityRenderer(VWBlockEntityTypes.HANGING_SIGN.get(), HangingSignRenderer::new);
        event.registerBlockEntityRenderer(VWBlockEntityTypes.SHELF.get(), ShelfRenderer::new);
    }
}