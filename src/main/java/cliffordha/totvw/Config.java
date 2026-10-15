package cliffordha.totvw;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final String DESC_SERVER_WOLF_DMG_DISTRIBUTION =
            """
                    When enabled, allow distribution
                    of damage to both the armor and wolf.
                    
                    Recommended as it is being utilized
                    by certain feature-triggers.""";

    private static final String DESC_WOLF_PLAYER_SCAN_DISTANCE =
            """
                    Max chunk scan distance to check for
                    wolf or player for enchantment
                    effects to trigger. Note that
                    this will still be limited by
                    your current game render distance.
                    
                    §8§oHigher values may affect performance!""";

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SERVER_WOLF_DMG_DISTRIBUTION = BUILDER
            .comment(DESC_SERVER_WOLF_DMG_DISTRIBUTION).define("wolfDamageDistribution", true);

    public static final ModConfigSpec.IntValue SERVER_WOLF_PLAYER_SCAN_DISTANCE = BUILDER
            .comment(DESC_WOLF_PLAYER_SCAN_DISTANCE).defineInRange("wolfPlayerScanDistance", 16, 10, 1024);

    public static final ModConfigSpec.BooleanValue SERVER_SKILL_COOLDOWNS = BUILDER
            .comment("Allow skill cooldowns. If disabled, active cooldowns will reset. For test purposes.").define("serverSkillCooldowns", true);

    public static final ModConfigSpec.BooleanValue SERVER_ITEM_COOLDOWNS = BUILDER
            .comment("Allow item-specific cooldowns. For test purposes.").define("serverItemCooldowns", true);

    public static final ModConfigSpec.BooleanValue SERVER_OTHER_COOLDOWNS = BUILDER
            .comment("Allow other time-based cooldowns. If disabled, active cooldowns will reset. For test purposes.").define("serverOtherCooldowns", true);

    static final ModConfigSpec SYNCED = BUILDER.build();
}
