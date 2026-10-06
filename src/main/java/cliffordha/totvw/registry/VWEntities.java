package cliffordha.totvw.registry;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.entity.VWGlobalEntityBehaviors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static cliffordha.totvw.TOTVW.sendClassRegisterLog;

public class VWEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.createEntities(TOTVW.MOD_ID);

    public static final ResourceKey<EntityType<?>> VERDANT_SPRUCE_BOAT_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            TOTVW.registerID( "verdant_spruce_boat"));
    public static final ResourceKey<EntityType<?>> VERDANT_SPRUCE_CHEST_BOAT_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            TOTVW.registerID("verdant_spruce_chest_boat"));

    public static final Supplier<EntityType<Boat>> VERDANT_SPRUCE_BOAT = ENTITY_TYPES.register("verdant_spruce_boat",
            () -> EntityType.Builder.<Boat>of((entityType, level) -> new Boat(entityType, level, VWItems.VERDANT_SPRUCE_BOAT),
                            MobCategory.MISC).sized(1.375f, 0.5625f)
                    .clientTrackingRange(10).build(VERDANT_SPRUCE_BOAT_KEY));

    public static final Supplier<EntityType<ChestBoat>> VERDANT_SPRUCE_CHEST_BOAT = ENTITY_TYPES.register("verdant_spruce_chest_boat",
            () -> EntityType.Builder.<ChestBoat>of((entityType, level) -> new ChestBoat(entityType, level, VWItems.VERDANT_SPRUCE_CHEST_BOAT),
                            MobCategory.MISC).sized(1.375f, 0.5625f)
                    .clientTrackingRange(10).build(VERDANT_SPRUCE_CHEST_BOAT_KEY));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
        VWGlobalEntityBehaviors.register();
        sendClassRegisterLog("Entities");
    }
}
