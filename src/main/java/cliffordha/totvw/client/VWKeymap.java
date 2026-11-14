package cliffordha.totvw.client;

import cliffordha.totvw.TOTVW;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class VWKeymap {
    private static final String PREFIX = "key.tales_of_the_verdant_wind.";
    public static final String TOTVW_CONFIG_SCREEN = registerKey("wolf_config");
    public static final String TOTVW_STAT_SCREEN = registerKey("status_screen");

    public static final KeyMapping TOTVW_CONFIG = new KeyMapping(TOTVW_CONFIG_SCREEN, InputConstants.KEY_LBRACKET, KeyMapping.Category.GAMEPLAY);

    public static final KeyMapping TOTVW_STAT = new KeyMapping(TOTVW_STAT_SCREEN, InputConstants.UNKNOWN.getValue(), KeyMapping.Category.GAMEPLAY);


    public static boolean isConfigKeyDown() {
        return !TOTVW_CONFIG.isUnbound() && TOTVW_CONFIG.isDown();
    }
    public static boolean isStatusScreenKeyDown() {
        return !TOTVW_STAT.isUnbound() && TOTVW_STAT.isDown();
    }

    public static void register() {
        TOTVW.sendClassRegisterLog("KeyMappings");
    }
    private static String registerKey(String key) {
        return PREFIX + key;
    }
}
