package cliffordha.totvw.registry.attachments;

import cliffordha.totvw.ClientConfig;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.List;
import java.util.function.Supplier;

import static cliffordha.totvw.registry.attachments.AttachmentUtil.*;

public class ClientPref {
    private static final String p = "benediction_";

    public static Supplier<AttachmentType<Boolean>> SHOW_ATROCITY_COUNTER = registerBool(
            "show_atrocity_counter", ClientConfig.CLIENT_SHOW_ATROCITY_COUNTER
    );
    public static Supplier<AttachmentType<Boolean>> ENABLE_NOTIFIERS = registerBool(
            "enable_notifiers", ClientConfig.CLIENT_ENABLE_NOTIFIERS
    );


    public static Supplier<AttachmentType<Integer>> BENEDICTION_HEALTH_THRESHOLD = registerInt(
            p + "health_threshold", ClientConfig.SERVER_BENEDICTION_HEALTH_THRESHOLD
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_SHARE_STACK = registerBool(
            p + "share_stack", ClientConfig.SERVER_WOLF_SHARES_BENEDICTION_STACK
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_ALWAYS_TRIGGER_BLESSING = registerBool(
            p + "always_trigger_blessing", ClientConfig.SERVER_ALWAYS_TRIGGER_BLESSING
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_TELEPORT_AFTER_SAVE = registerBool(
            p + "teleport_after_save", ClientConfig.SERVER_TELEPORT_AFTER_SAVE
    );
    public static Supplier<AttachmentType<Integer>> BENEDICTION_WOLF_TP_METHOD = registerInt(
            p + "wolf_tp_method", ClientConfig.SERVER_WOLF_TP_METHOD
    );
    public static Supplier<AttachmentType<Integer>> BENEDICTION_PLAYER_TP_METHOD = registerInt(
            p + "player_tp_method", ClientConfig.SERVER_PLAYER_TP_METHOD
    );
    public static Supplier<AttachmentType<Boolean>> BENEDICTION_WOLF_TP_ALL = registerBool(
            p + "wolf_tp_all", ClientConfig.SERVER_WOLF_TP_ALL
    );


    public static Supplier<AttachmentType<Boolean>> SHOW_WOLF_LOG = registerBool(
            "client_show_wolf_log", ClientConfig.LOG_ENCHANTMENT_SHOW_WOLF_CD
    );
    public static Supplier<AttachmentType<Boolean>> SHOW_PLAYER_LOG = registerBool(
            "client_show_player_log", ClientConfig.LOG_ENCHANTMENT_SHOW_PLAYER_CD
    );


    public static boolean showAtrocityCounter(Player player) {
        return player.getData(SHOW_ATROCITY_COUNTER.get());
    }
    public static boolean enableNotifiers(Player player) {
        return player.getData(ENABLE_NOTIFIERS.get());
    }
    public static int benedictionHealthThreshold(Player player) {
        return player.getData(BENEDICTION_HEALTH_THRESHOLD.get());
    }
    public static boolean benedictionShareStack(Player player) {
        return player.getData(BENEDICTION_SHARE_STACK.get());
    }
    public static boolean benedictionAlwaysTriggerBlessing(Player player) {
        return player.getData(BENEDICTION_ALWAYS_TRIGGER_BLESSING.get());
    }
    public static boolean benedictionTeleportAfterSave(Player player) {
        return player.getData(BENEDICTION_TELEPORT_AFTER_SAVE.get());
    }
    public static int benedictionWolfTPMethod(Player player) {
        return player.getData(BENEDICTION_WOLF_TP_METHOD.get());
    }
    public static int benedictionPlayerTPMethod(Player player) {
        return player.getData(BENEDICTION_PLAYER_TP_METHOD.get());
    }
    public static boolean benedictionWolfTPAll(Player player) {
        return player.getData(BENEDICTION_WOLF_TP_ALL.get());
    }
    public static boolean showWolfLog(Wolf wolf) {
        if ((!(wolf.getOwner() instanceof Player player))) return false;
        return player.getData(SHOW_WOLF_LOG.get());
    }
    public static boolean showPlayerLog(Player player) {
        return player.getData(SHOW_PLAYER_LOG.get());
    }



    public static Supplier<AttachmentType<Integer>> registerInt(String name, Supplier<Integer> config) {
        return ATTACHMENTS.register("prefs_" + name, () -> {
            var builder = AttachmentType.builder(config)
                    .serialize(Codec.INT.fieldOf("value"))
                    .sync(ByteBufCodecs.INT)
                    .copyOnDeath();

            return builder.build();
        });
    }
    public static Supplier<AttachmentType<Boolean>> registerBool(String name, Supplier<Boolean> config) {
        return ATTACHMENTS.register("prefs_" + name, () -> {
            var builder = AttachmentType.builder(config)
                    .serialize(Codec.BOOL.fieldOf("value"))
                    .sync(ByteBufCodecs.BOOL)
                    .copyOnDeath();

            return builder.build();
        });
    }

    public static List<?> getAttachmentTypes(Player player) {
        return List.of(
                showAtrocityCounter(player),
                enableNotifiers(player),
                benedictionHealthThreshold(player),
                benedictionShareStack(player),
                benedictionAlwaysTriggerBlessing(player),
                benedictionTeleportAfterSave(player),
                benedictionWolfTPMethod(player),
                benedictionPlayerTPMethod(player),
                benedictionWolfTPAll(player),

                showPlayerLog(player)
        );
    }
}
