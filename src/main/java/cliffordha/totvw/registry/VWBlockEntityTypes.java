package cliffordha.totvw.registry;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.block.entity.VWHangingSignBlockEntity;
import cliffordha.totvw.block.entity.VWShelfBlockEntity;
import cliffordha.totvw.block.entity.VWSignBlockEntity;
import cliffordha.totvw.block.entity.custom.StorageBlockEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class VWBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, TOTVW.MOD_ID);

    public static final Supplier<BlockEntityType<VWSignBlockEntity>> SIGN =
            BLOCK_ENTITIES.register("sign", () -> new BlockEntityType<>(
                    VWSignBlockEntity::new, Set.of(VWBlocks.VERDANT_SPRUCE_SIGN.get(), VWBlocks.VERDANT_SPRUCE_WALL_SIGN.get())));

    public static final Supplier<BlockEntityType<VWHangingSignBlockEntity>> HANGING_SIGN =
            BLOCK_ENTITIES.register("hanging_sign", () -> new BlockEntityType<>(
                    VWHangingSignBlockEntity::new, Set.of(VWBlocks.VERDANT_SPRUCE_HANGING_SIGN.get(), VWBlocks.VERDANT_SPRUCE_WALL_HANGING_SIGN.get())));

    public static final Supplier<BlockEntityType<VWShelfBlockEntity>> SHELF =
            BLOCK_ENTITIES.register("shelf", () -> new BlockEntityType<>(
                    VWShelfBlockEntity::new, Set.of(VWBlocks.VERDANT_SPRUCE_SHELF.get())));

    public static final Supplier<BlockEntityType<StorageBlockEntity>> STORAGE_BOX =
            BLOCK_ENTITIES.register("storage_box", () -> new BlockEntityType<>(
                    StorageBlockEntity::new, Set.of(VWBlocks.VERDANT_SPRUCE_STORAGE_BOX.get())));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}