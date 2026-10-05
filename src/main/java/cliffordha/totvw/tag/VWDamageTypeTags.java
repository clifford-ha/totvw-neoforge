package cliffordha.totvw.tag;

import cliffordha.totvw.TOTVW;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;

import java.util.concurrent.CompletableFuture;

import static cliffordha.totvw.datagen.VWDamageTypes.*;

public class VWDamageTypeTags extends DamageTypeTagsProvider {


    public VWDamageTypeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TOTVW.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {

        getOrCreateRawBuilder(DamageTypeTags.NO_KNOCKBACK)
                .addOptionalElement(BLOODLUST.identifier())
                .addOptionalElement(BLEEDING.identifier());

        getOrCreateRawBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .addOptionalElement(BLOODLUST.identifier())
                .addOptionalElement(BLEEDING.identifier())
                .addOptionalElement(HAVOC.identifier())
                .addOptionalElement(LODESTONE_WIND_CORE_PULSE.identifier())
                .addOptionalElement(SCORCHING_HEAT.identifier());

        getOrCreateRawBuilder(DamageTypeTags.BYPASSES_WOLF_ARMOR)
                .addOptionalElement(BLOODLUST.identifier())
                .addOptionalElement(BLEEDING.identifier());

        getOrCreateRawBuilder(DamageTypeTags.ALWAYS_HURTS_ENDER_DRAGONS)
                .addOptionalElement(LODESTONE_WIND_CORE_PULSE.identifier())
                .addOptionalElement(SCORCHING_HEAT.identifier());

        getOrCreateRawBuilder(DamageTypeTags.BYPASSES_INVULNERABILITY)
                .addOptionalElement(HAVOC.identifier())
                .addOptionalElement(LODESTONE_WIND_CORE_PULSE.identifier());

        getOrCreateRawBuilder(DamageTypeTags.BYPASSES_RESISTANCE)
                .addOptionalElement(HAVOC.identifier())
                .addOptionalElement(BLEEDING.identifier())
                .addOptionalElement(LODESTONE_WIND_CORE_PULSE.identifier())
                .addOptionalElement(SCORCHING_HEAT.identifier())
                .addOptionalElement(BLOODLUST.identifier());

        getOrCreateRawBuilder(DamageTypeTags.BYPASSES_SHIELD)
                .addOptionalElement(HAVOC.identifier())
                .addOptionalElement(LODESTONE_WIND_CORE_PULSE.identifier())
                .addOptionalElement(SCORCHING_HEAT.identifier());

        getOrCreateRawBuilder(DamageTypeTags.BYPASSES_EFFECTS)
                .addOptionalElement(LODESTONE_WIND_CORE_PULSE.identifier())
                .addOptionalElement(BLEEDING.identifier())
                .addOptionalElement(HAVOC.identifier())
                .addOptionalElement(SCORCHING_HEAT.identifier());

        getOrCreateRawBuilder(BENEDICTION_CAN_REDUCE_HIGH_DAMAGE)
                .addOptionalElement(DamageTypes.SONIC_BOOM.identifier())
                .addOptionalElement(DamageTypes.WITHER_SKULL.identifier())
                .addOptionalElement(DamageTypes.DRAGON_BREATH.identifier());
    }

    public static final TagKey<DamageType> BENEDICTION_CAN_REDUCE_HIGH_DAMAGE = create("benediction_can_reduce_high_damage");
    private static TagKey<DamageType> create(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, TOTVW.registerID(name)); }
}
