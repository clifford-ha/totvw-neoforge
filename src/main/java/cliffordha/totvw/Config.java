package cliffordha.totvw;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue SERVER_WOLF_DMG_DISTRIBUTION = BUILDER.comment("Wolf damage distribution").define("serverWolfDamageDistribution", true);
    public static final ModConfigSpec.IntValue SERVER_WOLF_PLAYER_SCAN_DISTANCE = BUILDER.comment("Wolf player scan distance").defineInRange("serverWolfPlayerScanDistance", 16, 4, 1024);

    public static final ModConfigSpec.BooleanValue SERVER_SKILL_COOLDOWNS = BUILDER.comment("Skill cooldowns").define("serverSkillCooldowns", true);
    public static final ModConfigSpec.BooleanValue SERVER_ITEM_COOLDOWNS = BUILDER.comment("Item cooldowns").define("serverItemCooldowns", true);
    public static final ModConfigSpec.BooleanValue SERVER_OTHER_COOLDOWNS = BUILDER.comment("Other cooldowns").define("serverOtherCooldowns", true);

    static final ModConfigSpec SYNCED = BUILDER.build();

    public static void register() {
        List<?> CONFIG_LIST = List.of(
                Config.SERVER_WOLF_DMG_DISTRIBUTION,
                Config.SERVER_WOLF_PLAYER_SCAN_DISTANCE,

                Config.SERVER_SKILL_COOLDOWNS,
                Config.SERVER_ITEM_COOLDOWNS,
                Config.SERVER_OTHER_COOLDOWNS
        );
        TOTVW.sendClassRegisterLog("Configs (" + CONFIG_LIST.size() + ")");
    }
}
