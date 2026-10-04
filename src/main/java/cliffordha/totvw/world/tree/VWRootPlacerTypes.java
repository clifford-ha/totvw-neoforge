package cliffordha.totvw.world.tree;

import cliffordha.totvw.TOTVW;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VWRootPlacerTypes {
    public static final DeferredRegister<RootPlacerType<?>> ROOT_PLACER_TYPES = DeferredRegister.create(BuiltInRegistries.ROOT_PLACER_TYPE, TOTVW.MOD_ID);

    public static final Supplier<RootPlacerType<RootPlacer>> DEFAULT = ROOT_PLACER_TYPES.register("default_root_placer",
            () -> new RootPlacerType<>(DefaultRootPlacer.CODEC)
    );

    public static void register(IEventBus eventBus) {
        ROOT_PLACER_TYPES.register(eventBus);
    }
}
