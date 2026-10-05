package cliffordha.totvw.registry.attachments;

import cliffordha.totvw.Config;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

import static cliffordha.totvw.registry.attachments.AttachmentUtil.*;

public class PlayerPrefs {
    private static final String p = "benediction_";

    public static Supplier<AttachmentType<Boolean>> SHOW_ATROCITY_COUNTER = registerBool(
            "show_atrocity_counter", Config.CLIENT_SHOW_ATROCITY_COUNTER.get()
    );
    public static Supplier<AttachmentType<Boolean>> ENABLE_NOTIFIERS = registerBool(
            "enable_notifiers", Config.CLIENT_ENABLE_NOTIFIERS.get()
    );


    public static Supplier<AttachmentType<Integer>> BENEDICTION_HEALTH_THRESHOLD = registerInt(
            p + "health_threshold", Config.SERVER_BENEDICTION_HEALTH_THRESHOLD.get()
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_SHARE_STACK = registerBool(
            p + "share_stack", Config.SERVER_WOLF_SHARES_BENEDICTION_STACK.get()
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_ALWAYS_TRIGGER_BLESSING = registerBool(
            p + "always_trigger_blessing", Config.SERVER_ALWAYS_TRIGGER_BLESSING.get()
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_TELEPORT_AFTER_SAVE = registerBool(
            p + "teleport_after_save", Config.SERVER_TELEPORT_AFTER_SAVE.get()
    );
    public static Supplier<AttachmentType<Integer>> BENEDICTION_WOLF_TP_METHOD = registerInt(
            p + "wolf_tp_method", Config.SERVER_WOLF_TP_METHOD.get()
    );
    public static Supplier<AttachmentType<Integer>> BENEDICTION_PLAYER_TP_METHOD = registerInt(
            p + "player_tp_method", Config.SERVER_PLAYER_TP_METHOD.get()
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_WOLF_TP_ALL = registerBool(
            p + "wolf_tp_all", Config.SERVER_WOLF_TP_ALL.get()
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
