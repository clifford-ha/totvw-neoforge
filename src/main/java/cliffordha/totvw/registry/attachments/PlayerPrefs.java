package cliffordha.totvw.registry.attachments;

import cliffordha.totvw.config.VWConfig;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

import static cliffordha.totvw.registry.attachments.AttachmentUtil.ATTACHMENTS;

public class PlayerPrefs {
    private static final String benediction = "benediction_";

    public static final Supplier<AttachmentType<Boolean>> SHOW_ATROCITY_COUNTER = registerBool("show_atrocity_counter", VWConfig.get().SYNCED_SHOW_ATROCITY_COUNTER);
    public static final Supplier<AttachmentType<Boolean>> ENABLE_NOTIFIERS = registerBool("enable_notifiers", VWConfig.get().SYNCED_ENABLE_NOTIFIERS);
    public static final Supplier<AttachmentType<Boolean>> SHOW_WOLF_EFFECT_INDICATORS = registerBool("show_wolf_effect_indicators", VWConfig.get().SYNCED_SHOW_WOLF_EFFECT_INDICATORS);

    public static final Supplier<AttachmentType<Integer>> BENEDICTION_HEALTH_THRESHOLD = registerInt(benediction + "health_threshold", VWConfig.get().SYNCED_BENEDICTION_HEALTH_THRESHOLD);
    public static final Supplier<AttachmentType<Boolean>> BENEDICTION_SHARE_STACK = registerBool(benediction + "share_stack", VWConfig.get().SYNCED_WOLF_SHARES_BENEDICTION_STACK);
    public static final Supplier<AttachmentType<Boolean>> BENEDICTION_ALWAYS_TRIGGER_BLESSING = registerBool(benediction + "always_trigger_blessing", VWConfig.get().SYNCED_ALWAYS_TRIGGER_BLESSING);
    public static final Supplier<AttachmentType<Boolean>> BENEDICTION_TELEPORT_AFTER_SAVE = registerBool(benediction + "teleport_after_save", VWConfig.get().SYNCED_TELEPORT_AFTER_SAVE);
    public static final Supplier<AttachmentType<Integer>> BENEDICTION_WOLF_TP_METHOD = registerInt(benediction + "wolf_tp_method", VWConfig.get().SYNCED_WOLF_TP_METHOD);
    public static final Supplier<AttachmentType<Integer>> BENEDICTION_PLAYER_TP_METHOD = registerInt(benediction + "player_tp_method", VWConfig.get().SYNCED_PLAYER_TP_METHOD);
    public static final Supplier<AttachmentType<Boolean>> BENEDICTION_WOLF_TP_ALL = registerBool(benediction + "wolf_tp_all", VWConfig.get().SYNCED_WOLF_TP_ALL);

    public static Supplier<AttachmentType<Integer>> registerInt(String name, Integer config) {
        return ATTACHMENTS.register("prefs_" + name, () -> {
            var builder = AttachmentType.builder(() -> config)
                    .serialize(Codec.INT.fieldOf("value"))
                    .sync(ByteBufCodecs.INT)
                    .copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<Boolean>> registerBool(String name, Boolean config) {
        return ATTACHMENTS.register("prefs_" + name, () -> {
            var builder = AttachmentType.builder(() -> config)
                    .serialize(Codec.BOOL.fieldOf("value"))
                    .sync(ByteBufCodecs.BOOL)
                    .copyOnDeath();

            return builder.build();
        });
    }
}
