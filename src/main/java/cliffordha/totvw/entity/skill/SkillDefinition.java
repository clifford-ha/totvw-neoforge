package cliffordha.totvw.entity.skill;

import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

public record SkillDefinition(
        Supplier<AttachmentType<Integer>> cooldown,
        Supplier<AttachmentType<Integer>> notifier,
        String skillName
){}
