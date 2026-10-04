package cliffordha.totvw.registry;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.fluid.VWFluidTypes;
import cliffordha.totvw.fluid.VerixiumFluid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VWFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, TOTVW.MOD_ID);

    public static final Supplier<FlowingFluid> FLOWING_VERIXIUM_FLUID = FLUIDS.register(
            "flowing_verixium_fluid", () -> new BaseFlowingFluid.Flowing(VWFluids.VERIXIUM_FLUID_PROPERTIES));
    public static final Supplier<FlowingFluid> VERIXIUM_FLUID = FLUIDS.register(
            "verixium_fluid", () -> new BaseFlowingFluid.Source(VWFluids.VERIXIUM_FLUID_PROPERTIES));

    private static final BaseFlowingFluid.Properties VERIXIUM_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            VWFluidTypes.VERIXIUM_FLUID_TYPE, VERIXIUM_FLUID, FLOWING_VERIXIUM_FLUID)
            .slopeFindDistance(2).levelDecreasePerBlock(1)
            .block(VWBlocks.VERIXIUM_FLUID).bucket(VWItems.VERIXIUM_FLUID_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
        TOTVW.sendClassRegisterLog("Fluids");
    }
}