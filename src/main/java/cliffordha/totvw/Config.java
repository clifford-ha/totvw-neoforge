package cliffordha.totvw;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue CLIENT_TRANSLATE_LANGUAGE = BUILDER.comment("Translate language").define("clientTranslateLanguage", false);
    public static final ModConfigSpec.BooleanValue CLIENT_ALLOW_EFFECT_OVERLAYS = BUILDER.comment("Allow effect overlays").define("clientAllowEffectOverlays", true);
    public static final ModConfigSpec.BooleanValue CLIENT_BLOODLUST_EFFECT_OVERLAY = BUILDER.comment("Bloodlust effect overlay").define("clientBloodlustEffectOverlay", false);
    public static final ModConfigSpec.BooleanValue CLIENT_ENABLE_NOTIFIERS = BUILDER.comment("Enable notifiers").define("clientEnableNotifiers", true);
    public static final ModConfigSpec.BooleanValue CLIENT_MOD_SOUNDS = BUILDER.comment("Enable mod sounds").define("clientModSounds", true);
    public static final ModConfigSpec.BooleanValue CLIENT_SHOW_ATROCITY_COUNTER = BUILDER.comment("Show atrocity counter").define("clientShowAtrocityCounter", false);
    public static final ModConfigSpec.BooleanValue CLIENT_ALLOW_LORE_SPOILERS = BUILDER.comment("Allow lore spoilers").define("clientAllowLoreSpoilers", false);

    public static final ModConfigSpec.BooleanValue SERVER_WOLF_DMG_DISTRIBUTION = BUILDER.comment("Wolf damage distribution").define("serverWolfDamageDistribution", true);
    public static final ModConfigSpec.IntValue SERVER_WOLF_PLAYER_SCAN_DISTANCE = BUILDER.comment("Wolf player scan distance").defineInRange("serverWolfPlayerScanDistance", 16, 4, 1024);
    public static final ModConfigSpec.IntValue SERVER_BENEDICTION_HEALTH_THRESHOLD = BUILDER.comment("Benediction health threshold").defineInRange("serverBenedictionHealthThreshold", 30, 15, 90);
    public static final ModConfigSpec.BooleanValue SERVER_ALWAYS_TRIGGER_BLESSING = BUILDER.comment("Always trigger blessing").define("serverAlwaysTriggerBlessing", false);
    public static final ModConfigSpec.BooleanValue SERVER_WOLF_SHARES_BENEDICTION_STACK = BUILDER.comment("Wolf shares Benediction stack").define("serverWolfSharesBenedictionStack", true);
    public static final ModConfigSpec.BooleanValue SERVER_TELEPORT_AFTER_SAVE = BUILDER.comment("Teleport after save").define("serverTeleportAfterSave", true);
    public static final ModConfigSpec.IntValue SERVER_WOLF_TP_METHOD = BUILDER.comment("Wolf TP method").defineInRange("serverWolfTPMethod", 0, 0, 1);
    public static final ModConfigSpec.IntValue SERVER_PLAYER_TP_METHOD = BUILDER.comment("Player TP method").defineInRange("serverPlayerTPMethod", 0, 0, 1);
    public static final ModConfigSpec.BooleanValue SERVER_WOLF_TP_ALL = BUILDER.comment("Wolf TP all").define("serverWolfTPAll", false);

    public static final ModConfigSpec.BooleanValue SERVER_SKILL_COOLDOWNS = BUILDER.comment("Skill cooldowns").define("serverSkillCooldowns", true);
    public static final ModConfigSpec.BooleanValue SERVER_ITEM_COOLDOWNS = BUILDER.comment("Item cooldowns").define("serverItemCooldowns", true);
    public static final ModConfigSpec.BooleanValue SERVER_OTHER_COOLDOWNS = BUILDER.comment("Other cooldowns").define("serverOtherCooldowns", true);

    public static final ModConfigSpec.BooleanValue LOG_ENCHANTMENT_SHOW_PLAYER_CD = BUILDER.comment("Show Player CD").define("logEnchantmentShowPlayerCD", false);
    public static final ModConfigSpec.BooleanValue LOG_ENCHANTMENT_SHOW_WOLF_CD = BUILDER.comment("Show Wolf CD").define("logEnchantmentShowWolfCD", false);
    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(Identifier.parse(itemName));
    }

    public static void register() {
        List<?> CONFIG_LIST = List.of(
                Config.CLIENT_TRANSLATE_LANGUAGE,
                Config.CLIENT_ALLOW_LORE_SPOILERS,
                Config.CLIENT_ALLOW_EFFECT_OVERLAYS,
                Config.CLIENT_BLOODLUST_EFFECT_OVERLAY,
                Config.CLIENT_MOD_SOUNDS,
                Config.CLIENT_ENABLE_NOTIFIERS,
                Config.CLIENT_SHOW_ATROCITY_COUNTER,
                Config.SERVER_WOLF_DMG_DISTRIBUTION,
                Config.SERVER_WOLF_PLAYER_SCAN_DISTANCE,
                Config.SERVER_BENEDICTION_HEALTH_THRESHOLD,
                Config.SERVER_ALWAYS_TRIGGER_BLESSING,
                Config.SERVER_WOLF_SHARES_BENEDICTION_STACK,
                Config.SERVER_TELEPORT_AFTER_SAVE,
                Config.SERVER_WOLF_TP_METHOD,
                Config. SERVER_PLAYER_TP_METHOD,
                Config.SERVER_WOLF_TP_ALL,

                Config.SERVER_SKILL_COOLDOWNS,
                Config.SERVER_ITEM_COOLDOWNS,
                Config.SERVER_OTHER_COOLDOWNS,

                Config.LOG_ENCHANTMENT_SHOW_PLAYER_CD,
                Config.LOG_ENCHANTMENT_SHOW_WOLF_CD
        );
        TOTVW.sendClassRegisterLog("Configs (" + CONFIG_LIST.size() + ")");
    }
}
