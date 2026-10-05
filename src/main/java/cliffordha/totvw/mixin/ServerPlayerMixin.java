package cliffordha.totvw.mixin;

import cliffordha.totvw.registry.VWColors;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cliffordha.totvw.util.VWUtil.sendToChat;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        ServerLevel level = serverPlayer.level();
        for (var serverLevel : level.getServer().getAllLevels()) {
            serverLevel.getEntities(EntityTypes.PLAYER, _ -> true).forEach(player -> {
                if (!player.entityTags().contains(player.getStringUUID() + "-reminderStamp")) {
                    sendToChat(player, VWColors.VERDANT_WIND, false, "TOTVW mod version is a development build.");
                    player.entityTags().add(player.getStringUUID() + "-reminderStamp");
                }
                if (!player.getData(PlayerAttachment.IS_DEV_MODE)) {
                    player.setData(PlayerAttachment.IS_DEV_MODE, true);
                }
            });
        }
    }
}
