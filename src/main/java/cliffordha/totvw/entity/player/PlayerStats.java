package cliffordha.totvw.entity.player;

import cliffordha.totvw.registry.attachments.AttachmentUtil;
import cliffordha.totvw.registry.attachments.ClientPref;
import cliffordha.totvw.registry.attachments.HavocType;
import cliffordha.totvw.registry.attachments.VWAttachments;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.UUID;

public record PlayerStats(
        String name,
        String UUID,
        String sharedUUID,

        int wolfSouls,
        int wolfAtrocityCount,
        int villagerAtrocityCount,

        int genesisRunestoneAcquisitionCount,

        HavocType havocType,
        int havocUsageCount,

        List<Pair<String, UUID>> trustedPlayers,

        boolean hasReceivedEnchantmentsHandbook,
        boolean hasReceivedItemsHandbook,
        boolean hasReceivedFeaturesHandbook,
        boolean hasReceivedEffectsHandbook,

        List<?> listType
) {
    public static PlayerStats valueOf(Player player) {
        var sharedID = player.getData(VWAttachments.WOLF_PLAYER_SHARED_ID);
        String SHARED_UUID = sharedID != AttachmentUtil.EMPTY_UUID ? String.valueOf(sharedID) : "None";
        List<CompoundTag> souls = player.getData(PlayerAttachment.WOLF_SOULS);

        boolean enchantmentHandbook = player.getData(PlayerAttachment.RECEIVED_ENCHANTMENTS_HANDBOOK) > 0;
        boolean itemHandbook = player.getData(PlayerAttachment.RECEIVED_ITEMS_HANDBOOK) > 0;
        boolean featureHandbook = player.getData(PlayerAttachment.RECEIVED_FEATURES_HANDBOOK) > 0;
        boolean effectHandbook = player.getData(PlayerAttachment.RECEIVED_EFFECTS_HANDBOOK) > 0;

        List<?> listType = ClientPref.getAttachmentTypes(player);

        return new PlayerStats(
                player.getPlainTextName(),
                player.getStringUUID(),
                SHARED_UUID,

                souls.size(),
                player.getData(PlayerAttachment.WOLF_ATROCITY_COUNT),
                player.getData(PlayerAttachment.VILLAGER_ATROCITY_COUNT),

                player.getData(PlayerAttachment.GENESIS_RUNESTONE_ACQUISITION_COUNT),

                HavocType.getType(player),
                HavocType.getUsage(player),

                PlayerAttachment.getTrustedPlayers(player),

                enchantmentHandbook,
                itemHandbook,
                featureHandbook,
                effectHandbook,

                listType
        );
    }
}
