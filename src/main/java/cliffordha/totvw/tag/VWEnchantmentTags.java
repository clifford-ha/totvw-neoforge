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

import static cliffordha.totvw.tag.VWTagHelpers.enchantment;

public class VWEnchantmentTags extends EnchantmentTagsProvider {
    public VWEnchantmentTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, TOTVW.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {
        getOrCreateRawBuilder(WOLF_ENCHANTMENTS)
                .add(enchantment(VWEnchantments.BENEDICTION_OF_THE_VERDANT_MOUNTAINS))
                .add(enchantment(VWEnchantments.WOLF_ARMOR_ENHANCEMENT_KIT))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_IGNITION))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_POISONING))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_WITHERING))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_LIFTING))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_BLOODLUST))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_MIGHT))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_OOZING))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_GNAWING));

        getOrCreateRawBuilder(CONTINUOUS_DAMAGE)
                .add(enchantment(VWEnchantments.WOLF_EFFECT_IGNITION))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_POISONING))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_WITHERING));

        getOrCreateRawBuilder(IMPAIRING_DAMAGE)
                .add(enchantment(VWEnchantments.WOLF_EFFECT_OOZING))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_LIFTING));

        getOrCreateRawBuilder(EnchantmentTags.IN_ENCHANTING_TABLE)
                .addTag(WOLF_ENCHANTMENTS.location());

        getOrCreateRawBuilder(EnchantmentTags.ON_RANDOM_LOOT)
                .add(enchantment(VWEnchantments.WOLF_ARMOR_ENHANCEMENT_KIT))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_IGNITION))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_MIGHT))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_OOZING))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_LIFTING));

        getOrCreateRawBuilder(EnchantmentTags.TREASURE)
                .add(enchantment(VWEnchantments.BENEDICTION_OF_THE_VERDANT_MOUNTAINS))
                .add(enchantment(VWEnchantments.WOLF_ARMOR_ENHANCEMENT_KIT))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_POISONING))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_WITHERING))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_BLOODLUST))
                .add(enchantment(VWEnchantments.WOLF_EFFECT_GNAWING));

    }
    public static final TagKey<Enchantment> WOLF_ENCHANTMENTS = create("wolf_enchantments");
    public static final TagKey<Enchantment> CONTINUOUS_DAMAGE = create("continuous_damage");
    public static final TagKey<Enchantment> IMPAIRING_DAMAGE = create("impairing_damage");

    private static TagKey<Enchantment> create(String name) {
        return TagKey.create(Registries.ENCHANTMENT, TOTVW.registerID(name)); }

}
