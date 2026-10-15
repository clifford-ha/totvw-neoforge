package cliffordha.totvw;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfig {
    private ClientConfig() {}
    private static final String DESC_TRANSLATE_LANGUAGE =
            """
                    When circumstances are met, certain item
                    tooltips will show untranslated version of the text""";

    private static final String DESC_EFFECT_OVERLAYS =
            "When enabled, certain mob effects will affect the screen.";

    private static final String DESC_BLOODLUST_EFFECT_OVERLAY =
            """
                    When enabled, if a player has the §cBloodlust Effect§r,
                    an overlay will be displayed on the whole screen.
                    The strength of the overlay depends on the player's health.
                    
                    §8§oDisabled by default for safety purposes.
                    Will also be disabled when Allow Effect Overlays is disabled.""";

    private static final String DESC_NOTIFIERS =
            """
                    When enabled, notifications will
                    be sent to chat or overlay.""";

    private static final String DESC_ATROCITY_COUNTER =
            """
                    When enabled, if a player hits a wolf or villager,
                    it will display a counter on the screen.
                
                    You can also know this by using the command:
                    /totvw get_atrocity_count""";

    private static final String DESC_LORE_SPOILERS =
            """
                    When enabled, show texts on pages that
                    contain lore regardless if the player
                    is not in survival mode.""";

    private static final String DESC_B_HEALTH_THRESHOLD =
            """
                    Grant §bBlessing of the Verdant Wind§r when
                    wolf/owner health threshold (in %) is met""";

    private static final String DESC_B_ALWAYS_TRIGGER =
            """
                    When enabled, when all of the
                    following conditions are met:
                    • §bShare Benediction§r is enabled,
                    • wolf has more than 1 Benediction stack
                    • owner's health goes below the set threshold percent§r,
                    wolf will grant §bBlessing of the Verdant Wind§r
                    to owner regardless if wolf are
                    able to revive them.""";

    private static final String DESC_B_SHARE_STACK =
            """
                    When enabled, if wolf has more than
                    1 Benediction Stack and their owner
                    enters dying state within specified chunk range,
                    wolf will consume §b1§r stack to revive owner.""";

    private static final String DESC_B_TP_AFTER_SAVE =
            """
                    When enabled, teleport to the nearest wolf
                    or owner after revival through Blessing.
                    For wolves: if player is inaccessible,
                    teleport to the saved spawn if valid.
                    
                    Only works if both are in the same dimension.""";

    private static final String DESC_B_WOLF_TP_METHOD =
            """
                    If 0, wolf will be teleport to
                    player. Otherwise, do reverse.
                    
                    Enable Teleport After Revival
                    to work.""";

    private static final String DESC_B_PLAYER_TP_METHOD =
            """
                    If 0, player will be teleport to
                    wolf. Otherwise, do reverse.
                    
                    Enable Teleport After Revival
                    to work.""";

    private static final String DESC_TP_ALL_WOLF =
            """
                    When enabled, teleport ALL tamed
                    wolves to player's location if one
                    of the wolves is able to revive them.
                    This option only affects the Player
                    TP Method. Have fun with this :3
                    
                    Enable Teleport After Revival
                    to work.""";


    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue ALLOW_EFFECT_OVERLAYS = BUILDER
            .comment(DESC_EFFECT_OVERLAYS).define("allowEffectOverlays", true);

    public static final ModConfigSpec.BooleanValue ALLOW_BLOODLUST_EFFECT_OVERLAY = BUILDER
            .comment(DESC_BLOODLUST_EFFECT_OVERLAY).define("allowBloodlustEffectOverlay", false);

    public static final ModConfigSpec.BooleanValue ENABLE_NOTIFIERS = BUILDER
            .comment(DESC_NOTIFIERS).define("enableNotifiers", true);

    public static final ModConfigSpec.BooleanValue SHOW_ATROCITY_COUNTER = BUILDER
            .comment(DESC_ATROCITY_COUNTER).define("showAtrocityCounter", false);

    public static final ModConfigSpec.BooleanValue ALLOW_LORE_SPOILERS = BUILDER
            .comment(DESC_LORE_SPOILERS).define("allowLoreSpoilers", false);


    public static final ModConfigSpec.IntValue BENEDICTION_HEALTH_THRESHOLD = BUILDER
            .comment(DESC_B_HEALTH_THRESHOLD).defineInRange("benedictionHealthThreshold", 30, 15, 90);

    public static final ModConfigSpec.BooleanValue WOLF_SHARES_BENEDICTION_STACK = BUILDER
            .comment(DESC_B_SHARE_STACK).define("wolfSharesBenedictionStack", true);

    public static final ModConfigSpec.BooleanValue ALWAYS_TRIGGER_BLESSING = BUILDER
            .comment(DESC_B_ALWAYS_TRIGGER).define("alwaysTriggerBlessing", false);

    public static final ModConfigSpec.BooleanValue TELEPORT_AFTER_SAVE = BUILDER
            .comment(DESC_B_TP_AFTER_SAVE).define("teleportAfterSave", true);

    public static final ModConfigSpec.IntValue WOLF_TP_METHOD = BUILDER
            .comment(DESC_B_WOLF_TP_METHOD).defineInRange("wolfTPMethod", 0, 0, 1);

    public static final ModConfigSpec.IntValue PLAYER_TP_METHOD = BUILDER
            .comment(DESC_B_PLAYER_TP_METHOD).defineInRange("playerTPMethod", 0, 0, 1);

    public static final ModConfigSpec.BooleanValue WOLF_TP_ALL = BUILDER
            .comment(DESC_TP_ALL_WOLF).define("wolfTPAll", false);


    public static final ModConfigSpec.BooleanValue LOG_ENCHANTMENT_SHOW_PLAYER_CD = BUILDER.define("logEnchantmentShowPlayerCD", false);
    public static final ModConfigSpec.BooleanValue LOG_ENCHANTMENT_SHOW_WOLF_CD = BUILDER.define("logEnchantmentShowWolfCD", false);

    public static final ModConfigSpec CLIENT = BUILDER.build();

    public static ClientConfig getInstance() {
        return new ClientConfig();
    }
}
