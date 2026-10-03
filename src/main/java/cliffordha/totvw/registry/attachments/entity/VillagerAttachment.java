package cliffordha.totvw.registry.attachments.entity;

import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

import static cliffordha.totvw.registry.attachments.AttachmentUtil.*;

public class VillagerAttachment {
    private static final String V = "villager_";
    public static final Supplier<AttachmentType<Boolean>> IS_VERDANT_TYPE = registerBool(V + "is_verdant_type", true);

    public static final Supplier<AttachmentType<Integer>> CD_HEAL_OTHERS = registerInt(V + "cd_heal_others", false);
    public static final Supplier<AttachmentType<Integer>> CD_HEAL_WOLF = registerInt(V + "cd_heal_wolf", false);
    public static final Supplier<AttachmentType<Integer>> CD_HEAL_IRON_GOLEM = registerInt(V + "cd_heal_iron_golem", false);
    public static final Supplier<AttachmentType<Integer>> CD_DISCOUNT_REROLL = registerInt(V + "cd_discount_reroll", false);

    public static final Supplier<AttachmentType<Float>> DISCOUNT_MODIFIER = registerFloat(V + "discount_modifier", false);
}
