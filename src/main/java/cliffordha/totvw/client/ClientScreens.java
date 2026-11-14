package cliffordha.totvw.client;

import cliffordha.totvw.client.screen.*;
import cliffordha.totvw.entity.status.WolfStats;
import cliffordha.totvw.item.pages.EntityStats;
import cliffordha.totvw.networking.packets.ConfirmAlliance;
import cliffordha.totvw.networking.packets.OpenTetherManagerPayload;
import cliffordha.totvw.networking.packets.ProcessAlliancePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

import static cliffordha.totvw.item.pages.FTextStyle.BOLD;
import static cliffordha.totvw.item.pages.ScatteredPageItem.invalidRef;
import static cliffordha.totvw.util.TextUtil.fText;

public class ClientScreens {
    public static void openPage(String title, Supplier<String> pages) {
        Minecraft.getInstance().setScreenAndShow(new PageScreen(title, pages));
    }
    public static void openTetherManagerScreen(OpenTetherManagerPayload payload) {
        Minecraft.getInstance().setScreenAndShow(new TetherScreen(payload.entityId(), payload.tetherData(), null));
    }
    public static void openAllianceScreen(ConfirmAlliance payload, IPayloadContext context) {
        Player other = context.player().level().getPlayerByUUID(payload.uuid());
        if (other != null) {
            Minecraft.getInstance().setScreenAndShow(new AllianceConfirmationScreen(null, Component.literal(other.getPlainTextName()), other.getUUID(), false));
        }
    }

    public static void showLiveWolfStats(Wolf wolf, boolean isWither) {
        int id = wolf.getId();
        WolfStats stats = WolfStats.valueOf(wolf);

        if (isWither) {
            Minecraft.getInstance().setScreenAndShow(new WolfFragmentScreen(
                    () -> {
                        Minecraft mc = Minecraft.getInstance();
                        return mc.level != null && mc.level.getEntity(id) instanceof Wolf
                                ? EntityStats.witherwolfStats(wolf, stats)
                                : invalidRef;
                    },
                    () -> wolf
            ));
            return;
        }
        Minecraft.getInstance().setScreenAndShow(new WolfPageScreen(
                fText(BOLD, stats.name() + "'s Stats"),
                () -> {
                    Minecraft mc = Minecraft.getInstance();
                    return mc.level != null && mc.level.getEntity(id) instanceof Wolf
                            ? EntityStats.wolfStats(wolf, stats)
                            : invalidRef;
                },
                () -> wolf
        ));
    }
    public static void setScreen(String title, Supplier<String> pages) {
        Minecraft.getInstance().setScreenAndShow(new PageScreen(title, pages));
    }
    public static void setScreen(Player player, String title, Supplier<String> pages) {
        Minecraft.getInstance().setScreenAndShow(new PlayerPageScreen(player, title, pages));
    }
}
