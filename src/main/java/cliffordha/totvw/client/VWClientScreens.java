package cliffordha.totvw.client;

import cliffordha.totvw.client.screen.ScatteredPageScreen;
import cliffordha.totvw.client.screen.TetherBlacklistScreen;
import net.minecraft.client.Minecraft;

public class VWClientScreens {
    private VWClientScreens() {}

    public static void openScatteredPage(String title, String[] pages) {
        Minecraft.getInstance().setScreenAndShow(new ScatteredPageScreen(title, pages));
    }

    public static void openTetherBlacklist(int wolfId) {
        Minecraft.getInstance().setScreenAndShow(new TetherBlacklistScreen(wolfId));
    }
}
