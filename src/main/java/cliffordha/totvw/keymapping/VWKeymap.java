package cliffordha.totvw.keymapping;

import cliffordha.totvw.TOTVW;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.common.util.Lazy;

public class VWKeymap {
    private static final String PREFIX = "key.tales_of_the_verdant_wind.";
    public static final String WOLF_CONFIG_KEY = registerKey("wolf_config");

    public static final KeyMapping WOLF_CONFIG = new KeyMapping(WOLF_CONFIG_KEY, InputConstants.KEY_TAB, KeyMapping.Category.GAMEPLAY);
    public static final Lazy<KeyMapping> WOLF_CONFIG_PRESSED = Lazy.of(() -> WOLF_CONFIG);


    public static boolean isWolfConfigKeyDown() {
        return !WOLF_CONFIG.isUnbound() && WOLF_CONFIG.isDown();
    }

    public static void register() {
        TOTVW.sendClassRegisterLog("KeyMappings");
    }
    private static String registerKey(String key) {
        return PREFIX + key;
    }
}
