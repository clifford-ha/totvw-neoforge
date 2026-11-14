package cliffordha.totvw.registry.attachments;

import cliffordha.totvw.registry.VWItems;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;
import cliffordha.totvw.util.ExtUtil;
import com.mojang.serialization.Codec;
import net.minecraft.util.Prediction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static cliffordha.totvw.util.ExtUtil.consumeItem;
import static cliffordha.totvw.util.ExtUtil.sendToChat;

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
    public Item getRunestoneItem(Wolf wolf) {
        return getStack(wolf.getData(WolfAttachment.RUNESTONE_TYPE)).getItem();
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
        return entity.getData(WolfAttachment.RUNESTONE_TYPE) == type;
    }
    public static Runestone getType(Wolf wolf) {
        return wolf.getData(WolfAttachment.RUNESTONE_TYPE);
    }
    public static void dropRunestone(Wolf wolf) {
        Runestone type = wolf.getData(WolfAttachment.RUNESTONE_TYPE);
        if (type.equals(Runestone.EMPTY)) return;
        wolf.drop(Runestone.getStack(type), false, Prediction.SERVER_ONLY);
        wolf.removeData(WolfAttachment.RUNESTONE_TYPE);
    }
    public static void dropRunestone(Wolf wolf, Player receiver) {
        Runestone type = wolf.getData(WolfAttachment.RUNESTONE_TYPE);
        if (type.equals(Runestone.EMPTY)) return;
        ExtUtil.addToInventory(receiver, type.getRunestoneItem(wolf));
        wolf.removeData(WolfAttachment.RUNESTONE_TYPE);
    }
    public static boolean hasRunestone(LivingEntity entity) {
        return !entity.getData(WolfAttachment.RUNESTONE_TYPE).equals(Runestone.EMPTY);
    }
    public static InteractionResult updateRunestone(Wolf wolf, Player player, Runestone type, ItemStack stack) {
        String name = wolf.getPlainTextName();
        Runestone current = wolf.getData(WolfAttachment.RUNESTONE_TYPE);

        if (current == Runestone.EMPTY) {
            wolf.setData(WolfAttachment.RUNESTONE_TYPE, type);
            consumeItem(player, stack);
            sendToChat(player, true, type.getName() + ": " + type.getBuff() +" buff is now activated for " + name + ".");
            return InteractionResult.SUCCESS;
        } else if (current == type) {
            sendToChat(player, true, name + " already uses this runestone type.");
            return InteractionResult.FAIL;
        } else {
            wolf.drop(Runestone.getStack(current), false, Prediction.SERVER_ONLY);
            wolf.setData(WolfAttachment.RUNESTONE_TYPE, type);
            consumeItem(player, stack);
            sendToChat(player, true, name + "'s Runestone buff has been changed to " + type.getBuff() + ".");
            return InteractionResult.SUCCESS;
        }
    }
}
