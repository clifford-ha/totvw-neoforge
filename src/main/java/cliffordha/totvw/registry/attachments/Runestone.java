package cliffordha.totvw.registry.attachments;

import cliffordha.totvw.registry.VWItems;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;
import com.mojang.serialization.Codec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.item.ItemStack;

public enum Runestone {
    EMPTY("EMPTY", "None"),

    GENESIS("Genesis Runestone", "None"),
    SOUL("Soul Runestone", "None"),
    TETHER("Tether Runestone", "Link"),
    HAVOC("Havoc Runestone", "Havoc"),
    EFFLORESCENCE("Efflorescence Runestone", "Verdant Bloom"),

    // fix
    FLOURISHING_FLORA(EFFLORESCENCE.name, EFFLORESCENCE.buff),
    ;

    private final String name;
    private final String buff;
    public static final Codec<Runestone> CODEC = Codec.STRING.xmap(Runestone::valueOf, Runestone::name);

    Runestone(String name, String buff) {
        this.name = name;
        this.buff = buff;
    }

    public String getName() {
        return name;
    }
    public String getBuff() {
        return buff;
    }
    public static String getNameByStack(ItemStack stack) {
        if (stack.is(VWItems.GENESIS_RUNESTONE_PLATE)) {
            return GENESIS.name;
        } else if (stack.is(VWItems.SOUL_RUNESTONE_PLATE)) {
            return SOUL.name;
        } else if (stack.is(VWItems.TETHER_RUNESTONE_PLATE)) {
            return TETHER.name;
        } else if (stack.is(VWItems.HAVOC_RUNESTONE_PLATE)) {
            return HAVOC.name;
        } else if (stack.is(VWItems.EFFLORESCENCE_RUNESTONE_PLATE)) {
            return EFFLORESCENCE.name;
        }


        else {
            return "Invalid Runestone";
        }
    }
    public static ItemStack getStack(Runestone type) {
        ItemStack stack;
        final ItemStack DEFAULT = new ItemStack(VWItems.GENESIS_RUNESTONE_PLATE.get());
        final ItemStack GENESIS = new ItemStack(VWItems.GENESIS_RUNESTONE_PLATE.get());
        final ItemStack SOUL = new ItemStack(VWItems.SOUL_RUNESTONE_PLATE.get());
        final ItemStack TETHER = new ItemStack(VWItems.TETHER_RUNESTONE_PLATE.get());
        final ItemStack HAVOC = new ItemStack(VWItems.HAVOC_RUNESTONE_PLATE.get());
        final ItemStack EFFLORESCENCE = new ItemStack(VWItems.EFFLORESCENCE_RUNESTONE_PLATE.get());
        switch (type) {
            case GENESIS -> stack = GENESIS;
            case SOUL -> stack = SOUL;
            case TETHER -> stack = TETHER;
            case HAVOC -> stack = HAVOC;
            case EFFLORESCENCE -> stack = EFFLORESCENCE;
            default -> stack = DEFAULT;
        }
        return stack;
    }

    public static boolean hasGenesis(Wolf wolf) {
        return hasRunestone(wolf, Runestone.GENESIS);
    }
    public static boolean hasSoul(Wolf wolf) {
        return hasRunestone(wolf, Runestone.SOUL);
    }
    public static boolean hasTether(Wolf wolf) {
        return hasRunestone(wolf, Runestone.TETHER);
    }
    public static boolean hasHavoc(Wolf wolf) {
        return hasRunestone(wolf, Runestone.HAVOC);
    }
    public static boolean hasEfflorescence(Wolf wolf) {
        return hasRunestone(wolf, Runestone.EFFLORESCENCE);
    }

    private static boolean hasRunestone(LivingEntity entity, Runestone type) {
        if (type == Runestone.EMPTY) return false;
        return entity.getData(WolfAttachment.RUNESTONE_TYPE) == type;
    }
}
