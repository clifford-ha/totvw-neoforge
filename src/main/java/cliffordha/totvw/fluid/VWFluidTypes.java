package cliffordha.totvw.fluid;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.registry.VWColors;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector4f;

import java.util.function.Supplier;

public class VWFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, TOTVW.MOD_ID);

    public static final Supplier<FluidType> VERIXIUM_FLUID_TYPE = FLUID_TYPES.register("verixium_fluid_type",
            () -> new FluidType(FluidType.Properties.create().isWaterLike(false)));

    public static IClientFluidTypeExtensions VERIXIUM_FLUID_EXTENSION = new IClientFluidTypeExtensions() {
        @Override
        public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
            fluidFogColor.set(ARGB.vector4fFromARGB32(VWColors.VERDANT_WIND));
            IClientFluidTypeExtensions.super.modifyFogColor(camera, partialTick, level, renderDistance, darkenWorldAmount, fluidFogColor);
        }
    };


    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
