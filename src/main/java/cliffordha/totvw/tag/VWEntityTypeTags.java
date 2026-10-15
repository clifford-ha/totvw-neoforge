package cliffordha.totvw.tag;

import cliffordha.totvw.TOTVW;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;
import static cliffordha.totvw.tag.VWTagHelpers.entity;

public class VWEntityTypeTags extends EntityTypeTagsProvider {
    public VWEntityTypeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TOTVW.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateRawBuilder(IGNORES_STRONG_WIND_CORE_PULSE)
                .add(entity(EntityType.WOLF))
                .add(entity(EntityType.ARMOR_STAND))
                .add(entity(EntityType.PAINTING))
                .add(entity(EntityType.ITEM_FRAME));

        getOrCreateRawBuilder(CAN_WEAR_RUNESTONES)
                .add(entity(EntityType.WOLF));
    }

    public static final TagKey<EntityType<?>> IGNORES_STRONG_WIND_CORE_PULSE = create("ignores_strong_wind_core_pulse");
    public static final TagKey<EntityType<?>> CAN_WEAR_RUNESTONES = create("can_wear_runestones");

    private static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, TOTVW.registerID(name)); }
}
