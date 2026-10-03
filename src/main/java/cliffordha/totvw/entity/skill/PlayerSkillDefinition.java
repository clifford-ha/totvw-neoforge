package cliffordha.totvw.entity.skill;

import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

public record PlayerSkillDefinition(
        Supplier<AttachmentType<Integer>> cooldown,
        Supplier<AttachmentType<Integer>> notifier,
        int notifierColor,
        String skillName
)
{}