package cliffordha.totvw.registry.attachments;

import cliffordha.totvw.config.VWConfig;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

import static cliffordha.totvw.registry.attachments.AttachmentUtil.*;

public class PlayerPrefs {
    private static final String p = "benediction_";

    public static Supplier<AttachmentType<Boolean>> SHOW_ATROCITY_COUNTER = registerBool(
            "show_atrocity_counter", VWConfig.get().CLIENT_SHOW_ATROCITY_COUNTER
    );
    public static Supplier<AttachmentType<Boolean>> ENABLE_NOTIFIERS = registerBool(
            "enable_notifiers", VWConfig.get().CLIENT_ENABLE_NOTIFIERS
    );


    public static Supplier<AttachmentType<Integer>> BENEDICTION_HEALTH_THRESHOLD = registerInt(
            p + "health_threshold", VWConfig.get().SERVER_BENEDICTION_HEALTH_THRESHOLD
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_SHARE_STACK = registerBool(
            p + "share_stack", VWConfig.get().SERVER_WOLF_SHARES_BENEDICTION_STACK
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_ALWAYS_TRIGGER_BLESSING = registerBool(
            p + "always_trigger_blessing", VWConfig.get().SERVER_ALWAYS_TRIGGER_BLESSING
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_TELEPORT_AFTER_SAVE = registerBool(
            p + "teleport_after_save", VWConfig.get().SERVER_TELEPORT_AFTER_SAVE
    );
    public static Supplier<AttachmentType<Integer>> BENEDICTION_WOLF_TP_METHOD = registerInt(
            p + "wolf_tp_method", VWConfig.get().SERVER_WOLF_TP_METHOD
    );
    public static Supplier<AttachmentType<Integer>> BENEDICTION_PLAYER_TP_METHOD = registerInt(
            p + "player_tp_method", VWConfig.get().SERVER_PLAYER_TP_METHOD
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_WOLF_TP_ALL = registerBool(
            p + "wolf_tp_all", VWConfig.get().SERVER_WOLF_TP_ALL
    );

    public static Supplier<AttachmentType<Integer>> registerInt(String name, int config) {
        return ATTACHMENTS.register("prefs_" + name, () -> {
            var builder = AttachmentType.builder(() -> config)
                    .serialize(Codec.INT.fieldOf("value"))
                    .sync(ByteBufCodecs.INT)
                    .copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<Boolean>> registerBool(String name, boolean config) {
        return ATTACHMENTS.register("prefs_" + name, () -> {
            var builder = AttachmentType.builder(() -> config)
                    .serialize(Codec.BOOL.fieldOf("value"))
                    .sync(ByteBufCodecs.BOOL)
                    .copyOnDeath();

            return builder.build();
        });
    }
}
