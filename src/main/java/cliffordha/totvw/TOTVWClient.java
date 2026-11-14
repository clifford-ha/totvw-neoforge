package cliffordha.totvw;

import cliffordha.totvw.client.ClientScreens;
import cliffordha.totvw.client.EffectOverlay;
import cliffordha.totvw.client.VWKeymap;
import cliffordha.totvw.client.VWModelLayerProvider;
import cliffordha.totvw.client.screen.ConfigScreen;
import cliffordha.totvw.fluid.VWFluidTypes;
import cliffordha.totvw.item.pages.ScatteredPageItem;
import cliffordha.totvw.particle.*;
import cliffordha.totvw.registry.*;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;
import cliffordha.totvw.util.ColorizeText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.particle.SnowflakeParticle;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.ShelfRenderer;
import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.List;

import static cliffordha.totvw.registry.VWColors.shadeColor;

@Mod(value = TOTVW.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = TOTVW.MOD_ID, value = Dist.CLIENT)
public class TOTVWClient {
    public TOTVWClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (_, parent) -> new ConfigScreen(parent));
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(VWEntities.VERDANT_SPRUCE_BOAT.get(), context -> new BoatRenderer(context, VWModelLayerProvider.VERDANT_SPRUCE_BOAT));
        EntityRenderers.register(VWEntities.VERDANT_SPRUCE_CHEST_BOAT.get(), context -> new BoatRenderer(context, VWModelLayerProvider.VERDANT_SPRUCE_CHEST_BOAT));

        ColorizeText.register(VWColors.VERDANT_WIND, List.of(
                        VWEnchantments.KEY_BENEDICTION,

                        "item.minecraft.tipped_arrow.effect.sacred_verdant_potion",
                        "item.minecraft.potion.effect.sacred_verdant_potion",
                        "item.minecraft.splash_potion.effect.sacred_verdant_potion",
                        "item.minecraft.lingering_potion.effect.sacred_verdant_potion",

                        VWEffects.KEY_BLESSING_OF_THE_VERDANT_WIND,
                        VWEffects.KEY_WIND_VEIL
                )
        );
        ColorizeText.register(VWColors.ARCTIC_WINDS, VWEnchantments.KEY_WOLF_EFFECT_ARCTIC_WINDS);
        ColorizeText.register(shadeColor(VWColors.MIGHT, 0.1f), VWEnchantments.KEY_WOLF_EFFECT_MIGHT);
        ColorizeText.register(shadeColor(VWColors.BLOODLUST, 0.1f), VWEnchantments.KEY_WOLF_EFFECT_BLOODLUST);
        ColorizeText.register(VWColors.GNAWING, VWEnchantments.KEY_WOLF_EFFECT_GNAWING);
        ColorizeText.register(VWColors.WITHERING, VWEnchantments.KEY_WOLF_EFFECT_WITHERING);
        ColorizeText.register(VWColors.POISON, VWEnchantments.KEY_WOLF_EFFECT_POISONING);
        ColorizeText.register(VWColors.POISON, VWEnchantments.KEY_WOLF_EFFECT_POISONING);
        ColorizeText.register(VWColors.IGNITION, VWEnchantments.KEY_WOLF_EFFECT_IGNITION);

        ColorizeText.register(VWColors.BLOODLUST, VWEffects.KEY_BLOODLUST);
        ColorizeText.register(VWColors.HAVOC_PARTICLE, VWEffects.KEY_HAVOC);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(VWModelLayerProvider.VERDANT_SPRUCE_BOAT, BoatModel::createBoatModel);
        event.registerLayerDefinition(VWModelLayerProvider.VERDANT_SPRUCE_CHEST_BOAT, BoatModel::createChestBoatModel);
    }

    @SubscribeEvent
    public static void registerKeymapping(RegisterKeyMappingsEvent event) {
        event.register(VWKeymap.TOTVW_STAT);
        event.register(VWKeymap.TOTVW_CONFIG);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        Minecraft client = Minecraft.getInstance();
        if (VWKeymap.isConfigKeyDown()) {
            ConfigScreen.create();
            return;
        }
        if (VWKeymap.isStatusScreenKeyDown()) {
            if (client.crosshairPickEntity == null) {
                ScatteredPageItem.openStatScreen(client.player);
                return;
            }
            if (client.crosshairPickEntity instanceof Wolf wolf && wolf.getOwner() == client.player) {
                if (wolf.hasData(WolfAttachment.FRAGMENT_DATA)) {
                    ClientScreens.showLiveWolfStats(wolf, true);
                    return;
                }
                ClientScreens.showLiveWolfStats(wolf, false);
                return;
            }
            ScatteredPageItem.openStatScreen(client.player);
        }
    }

    @SubscribeEvent
    public static void registerHUD(RegisterGuiLayersEvent event) {
        EffectOverlay.register(event);
    }

    @SubscribeEvent
    public static void registerColoredBlocks(RegisterColorHandlersEvent.BlockTintSources event) {
    }

    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(VWParticles.BENEDICTION_TRIGGER.get(), BenedictionTriggerParticle.BenedictionParticleProvider::new);
        event.registerSpriteSet(VWParticles.VERDANT_BIOMES_AMBIENCE.get(), VerdantBiomesAmbience.VerdantBiomesAmbienceProvider::new);
        event.registerSpriteSet(VWParticles.VERIXIUM_POWDER_RAIN.get(), VerixiumPowderRainParticle.VerixiumPowderRainParticleProvider::new);
        event.registerSpriteSet(VWParticles.MIGHT_PARALYZE.get(), MightParalyzeParticle.MightParalyzeParticleProvider::new);
        event.registerSpriteSet(VWParticles.EXT_SNOWFLAKE.get(), SnowflakeParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerOnClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(VWFluidTypes.VERIXIUM_FLUID_EXTENSION, VWFluidTypes.VERIXIUM_FLUID_TYPE.get());
    }

    @SubscribeEvent
    public static void registerFluidModelsEvent(RegisterFluidModelsEvent event) {
        FluidModel.Unbaked verixiumFluid = new FluidModel.Unbaked(
                new Material(Identifier.withDefaultNamespace("block/water_still")),
                new Material(Identifier.withDefaultNamespace("block/water_flow")),
                new Material(Identifier.withDefaultNamespace("block/water_overlay")),
                BlockTintSources.constant(VWColors.setColor(0x13e1a8)));

        event.register(verixiumFluid, VWFluids.VERIXIUM_FLUID.get());
        event.register(verixiumFluid, VWFluids.FLOWING_VERIXIUM_FLUID.get());
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