package cliffordha.totvw.tag;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.registry.VWEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class VWEnchantmentTags extends EnchantmentTagsProvider {
    public VWEnchantmentTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TOTVW.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {
        getOrCreateRawBuilder(WOLF_ENCHANTMENTS)
                .addOptionalElement(VWEnchantments.BENEDICTION_OF_THE_VERDANT_MOUNTAINS.identifier())
                .addOptionalElement(VWEnchantments.WOLF_ARMOR_ENHANCEMENT_KIT.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_IGNITION.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_POISONING.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_WITHERING.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_LIFTING.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_BLOODLUST.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_MIGHT.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_OOZING.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_GNAWING.identifier());

        getOrCreateRawBuilder(CONTINUOUS_DAMAGE)
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_IGNITION.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_POISONING.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_WITHERING.identifier());

        getOrCreateRawBuilder(IMPAIRING_DAMAGE)
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_OOZING.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_LIFTING.identifier());

        getOrCreateRawBuilder(EnchantmentTags.IN_ENCHANTING_TABLE)
                .addTag(WOLF_ENCHANTMENTS.location());

        getOrCreateRawBuilder(EnchantmentTags.ON_RANDOM_LOOT)
                .addOptionalElement(VWEnchantments.WOLF_ARMOR_ENHANCEMENT_KIT.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_IGNITION.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_MIGHT.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_OOZING.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_LIFTING.identifier());

        getOrCreateRawBuilder(EnchantmentTags.TREASURE)
                .addOptionalElement(VWEnchantments.BENEDICTION_OF_THE_VERDANT_MOUNTAINS.identifier())
                .addOptionalElement(VWEnchantments.WOLF_ARMOR_ENHANCEMENT_KIT.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_POISONING.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_WITHERING.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_BLOODLUST.identifier())
                .addOptionalElement(VWEnchantments.WOLF_EFFECT_GNAWING.identifier());

    }
    public static final TagKey<Enchantment> WOLF_ENCHANTMENTS = create("wolf_enchantments");
    public static final TagKey<Enchantment> CONTINUOUS_DAMAGE = create("continuous_damage");
    public static final TagKey<Enchantment> IMPAIRING_DAMAGE = create("impairing_damage");

    private static TagKey<Enchantment> create(String name) {
        return TagKey.create(Registries.ENCHANTMENT, TOTVW.registerID(name));
    }
}
