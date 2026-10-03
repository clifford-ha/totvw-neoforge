package cliffordha.totvw.registry;

import cliffordha.totvw.TOTVW;
import cliffordha.totvw.registry.attachments.entity.PlayerAttachment;
import cliffordha.totvw.registry.attachments.entity.WolfAttachment;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import oshi.util.tuples.Pair;

import java.util.*;

import static cliffordha.totvw.util.VWUtil.sendToChat;

public class VWCommands {
    private static final AttachmentType<List<Pair<String, UUID>>> TRUST_DATA = PlayerAttachment.TRUSTED_PLAYERS.get();
    private static final AttachmentType<List<Pair<String, UUID>>> WOLF_TRUST_DATA = WolfAttachment.TRUSTED_PLAYERS.get();

    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        
        dispatcher.register(Commands.literal("totvw")
                .then(Commands.literal("trust_data")
                .then(Commands.literal("query")
                .executes(context -> {
                    ServerPlayer player = getPlayer(context);
                    if (player == null) {
                        failed(context);
                        return 0;
                    }

                    List<Pair<String, UUID>> data = player.getData(TRUST_DATA);
                    if (!data.isEmpty()) {
                        String firstPart = data.size() > 1 ? "These are currently " : "There is currently ";
                        sendSuccess(context, false, firstPart + data.size() + " player" + (data.size() > 1 ? "s" : "") + " you trust.");

                        sendToChat(player, false, "Index  |  Name");
                        for (Pair<String, UUID> pair : player.getData(TRUST_DATA)) {
                            sendToChat(player, false, data.indexOf(pair) + 1 + " " + pair.getA());
                        }

                        return data.size();
                    } else {
                        sendFail(context, VWColors.GRAY, "You don't have any trusted players in your list.");
                        return 0;
                    }
                }
                ))));

        dispatcher.register(Commands.literal("totvw")
                .then(Commands.literal("trust_data")
                .then(Commands.literal("remove")
                .then(Commands.argument("entity", IntegerArgumentType.integer(1))
                .executes(context -> {
                    ServerPlayer player = getPlayer(context);
                    if (player == null) {
                        failed(context);
                        return 0;
                    }

                    int index = context.getArgument("entity", Integer.class);
                    List<Pair<String, UUID>> data = new ArrayList<>(player.getData(TRUST_DATA));
                    
                    if (!data.isEmpty()) {
                        if (index < 1 || index > data.size()) {
                            sendFail(context, "The index you provided is out of range. (1-" + data.size() + ")");
                            return 0;
                        }

                        Pair<String, UUID> removed = data.get(index - 1);
                        sendSuccess(context, false, "You removed " + removed.getA() + " from your list of trusted players.");
                        data.remove(index - 1);
                        player.setData(TRUST_DATA, data);
                        return data.size();
                    } else {
                        sendFail(context, "No trust data to remove.");
                        return 0;
                    }
                }
                )))));
        
        dispatcher.register(Commands.literal("totvw")
                .then(Commands.literal("trust_data")
                .then(Commands.literal("remove")
                .then(Commands.literal("all")
                .executes(context -> {
                    ServerPlayer player = getPlayer(context);
                    if (player == null) {
                        failed(context);
                        return 0;
                    }
                    
                    int data = player.getData(TRUST_DATA).size();
                    if (data > 0) {
                        player.removeData(TRUST_DATA);
                        sendSuccess(context, false, "You removed your list of trusted players (" + data + ").");
                        return data;
                    } else {
                        sendFail(context, "No trust data to remove.");
                        return 0;
                    }
                }
                )))));

        dispatcher.register(Commands.literal("totvw")
                .then(Commands.literal("trust_data")
                .then(Commands.literal("wolf")
                .then(Commands.literal("removeAggressorList")
                .executes(context -> {
                    ServerPlayer player = getPlayer(context);
                    if (player == null) {
                        failed(context);
                        return 0;
                    }

                    ServerLevel level = player.level();
                    List<Wolf> wolves = level.getEntities(
                            EntityTypes.WOLF,
                            player.getBoundingBox().inflate(16),
                            t -> t.getOwner() == player);
                    
                    if (wolves.isEmpty()) {
                        sendFail(context, "No nearby tamed wolves to update!");
                        return 0;
                    }
                    
                    for (Wolf wolf : wolves) {
                        wolf.removeData(WolfAttachment.AGGRESSOR_LIST);
                    }

                    String w = wolves.size() > 1 ? "nearby wolves" : wolves.getFirst().getPlainTextName();
                    sendSuccess(context, false, "Removed the aggressor list of " + w + ".");
                    return wolves.size();
                }
                )))));

            dispatcher.register(Commands.literal("totvw")
                    .then(Commands.literal("trust_data")
                    .then(Commands.literal("wolf")
                    .then(Commands.literal("sync")
                    .executes(context -> {
                        ServerPlayer player = getPlayer(context);
                        if (player == null) {
                            failed(context);
                            return 0;
                        }
                        ServerLevel level = player.level();

                        List<Wolf> wolves = level.getEntities(
                                EntityTypes.WOLF,
                                player.getBoundingBox().inflate(16),
                                t -> t.getOwner() == player);

                        if (wolves.isEmpty()) {
                            sendFail(context, "No nearby wolves to sync to!");
                            return 0;
                        }

                        int aggressors = 0;
                        List<Pair<String, UUID>> trustData = player.getData(TRUST_DATA);
                        for (Wolf wolf : wolves) {
                            List<Pair<String, UUID>> aggressorList = new ArrayList<>(wolf.getData(WolfAttachment.AGGRESSOR_LIST));

                            if (!trustData.isEmpty()) {
                                wolf.setData(WOLF_TRUST_DATA, trustData);

                                if (!aggressorList.isEmpty()) {
                                    for (Pair<String, UUID> data : aggressorList) {
                                        if (aggressorList.contains(data)) {
                                            aggressors++;
                                            aggressorList.remove(data);
                                            wolf.setData(WolfAttachment.AGGRESSOR_LIST, aggressorList);
                                        }
                                    }
                                }

                            } else {
                                wolf.removeData(WOLF_TRUST_DATA);
                            }
                        }

                        String wolfCount = wolves.size() > 1 ? "nearby wolves" : wolves.getFirst().getPlainTextName();
                        String removedAggressors = aggressors > 0 ? " and removed " + aggressors + " aggressor" + (aggressors > 1 ? "s" : "") + "." : ".";
                        String count = "Synced " + trustData.size() + " trusted players to " + wolfCount + removedAggressors;

                        String finalize = trustData.isEmpty() ? "Removed old data from " + wolfCount : count;

                        sendSuccess(context, false, finalize);
                        return wolves.size();
                    }
                    )))));

            dispatcher.register(Commands.literal("totvw")
                    .then(Commands.literal("enchantments_handbook").executes(context -> {
                        ServerPlayer player = getPlayer(context);
                        if (player == null) {
                            failed(context);
                            return 0;
                        }
                        int getStat = player.getData(PlayerAttachment.RECEIVED_ENCHANTMENTS_HANDBOOK);
                        if (getStat < 1) {
                            giveOrDropHandbook(player, 0);
                        } else {
                            if (player.isCreative() || player.isSpectator()) {
                                giveOrDropHandbook(player, 0);
                            } else {
                                handbookCopy(context);
                            }
                        }
                        return getStat;
                    }
                    )));

            dispatcher.register(Commands.literal("totvw")
                    .then(Commands.literal("effects_handbook").executes(context -> {
                        ServerPlayer player = getPlayer(context);
                        if (player == null) {
                            failed(context);
                            return 0;
                        }
                        int getStat = player.getData(PlayerAttachment.RECEIVED_EFFECTS_HANDBOOK);
                        if (getStat < 1) {
                            giveOrDropHandbook(player, 1);
                        } else {
                            if (player.isCreative() || player.isSpectator()) {
                                giveOrDropHandbook(player, 1);
                            } else {
                                handbookCopy(context);
                            }
                        }
                        return getStat;
                    }
                    )));

            dispatcher.register(Commands.literal("totvw")
                    .then(Commands.literal("items_handbook").executes(context -> {
                        ServerPlayer player = getPlayer(context);
                        if (player == null) {
                            failed(context);
                            return 0;
                        }

                        int getStat = player.getData(PlayerAttachment.RECEIVED_ITEMS_HANDBOOK);
                        if (getStat < 1) {
                            giveOrDropHandbook(player, 2);
                        } else {
                            if (player.isCreative() || player.isSpectator()) {
                                giveOrDropHandbook(player, 2);
                            } else {
                                handbookCopy(context);
                            }
                        }
                        return getStat;
                    }
                    )));

            dispatcher.register(Commands.literal("totvw")
                    .then(Commands.literal("features_handbook").executes(context -> {
                        ServerPlayer player = getPlayer(context);
                        if (player == null) {
                            failed(context);
                            return 0;
                        }

                        int getStat = player.getData(PlayerAttachment.RECEIVED_FEATURES_HANDBOOK);
                        if (getStat < 1) {
                            giveOrDropHandbook(player, 3);
                        } else {
                            if (player.isCreative() || player.isSpectator()) {
                                giveOrDropHandbook(player, 3);
                            } else {
                                handbookCopy(context);
                            }
                        }
                        return getStat;
                    }
                    )));

            dispatcher.register(Commands.literal("totvw")
                    .then(Commands.literal("get_atrocity_count").executes(context -> {
                                ServerPlayer player = getPlayer(context);
                                if (player == null) {
                                    failed(context);
                                    return 0;
                                }

                                int villager = player.getData(PlayerAttachment.VILLAGER_ATROCITY_COUNT);
                                int wolf = player.getData(PlayerAttachment.WOLF_ATROCITY_COUNT);
                                if ((villager + wolf) < 1) {
                                    context.getSource().sendSystemMessage(Component.literal("You don't have any atrocity count."));
                                } else {
                                    context.getSource().sendSuccess(() -> Component.literal("Wolf: " + wolf + "  |  Villager: " + villager), true);
                                }
                                return 1;
                            }
                    )));



        if (!TOTVW.IN_DEVELOPMENT) return;
        dispatcher.register(Commands.literal("totvw")
                .then(Commands.literal("tame_nearby_wolves").executes(context -> {
                    ServerPlayer player = getPlayer(context);
                    if (player == null) {
                        failed(context);
                        return 0;
                    }

                    ServerLevel level = player.level();
                    List<Wolf> wolves = level.getEntities(EntityTypes.WOLF,
                            player.getBoundingBox().inflate(32),
                            wolf -> wolf.isTame() && wolf.getUUID() != player.getUUID());

                    if (wolves.isEmpty()) {
                        context.getSource().sendFailure(Component.literal("No nearby wolves to tame!"));
                        return 0;
                    }

                    for (Wolf wolf : wolves) {
                        wolf.setOwner(player);
                        wolf.setTame(true, true);
                    }
                    context.getSource().sendSuccess(() -> Component.literal("Tamed " + wolves.size() + " nearby wolves."), true);
                    return wolves.size();
                }
                )));

        dispatcher.register(Commands.literal("totvw")
                .then(Commands.literal("release_tamed_wolves").executes(context -> {
                    ServerPlayer player = getPlayer(context);
                    if (player == null) {
                        failed(context);
                        return 0;
                    }

                    ServerLevel level = player.level();
                    List<Wolf> wolves = level.getEntities(EntityTypes.WOLF,
                            player.getBoundingBox().inflate(32),
                            wolf -> wolf.isTame() && wolf.getOwner() == player);

                    if (wolves.isEmpty()) {
                        context.getSource().sendFailure(Component.literal("No nearby wolves to un-tame!"));
                        return 0;
                    }

                    for (Wolf wolf : wolves) {
                        wolf.setOwner(null);
                        wolf.setTame(false, true);
                    }
                    context.getSource().sendSuccess(() -> Component.literal("Un-tamed " + wolves.size() + " nearby wolves."), true);
                    return wolves.size();
                }
                )));
    }

    private static void giveOrDropHandbook(ServerPlayer player, int toGive) {
        ItemStack mainHand = player.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack handbook;
        AttachmentType<Integer> handbookType;
        String handbookName;

        switch (toGive) {
            case 0 -> {
                handbook = new ItemStack(VWItems.Pages.ENCHANTMENTS_HANDBOOK.get());
                handbookType = PlayerAttachment.RECEIVED_ENCHANTMENTS_HANDBOOK.get();
                handbookName = "Enchantments";
            }
            case 1 -> {
                handbook = new ItemStack(VWItems.Pages.EFFECTS_HANDBOOK.get());
                handbookType = PlayerAttachment.RECEIVED_EFFECTS_HANDBOOK.get();
                handbookName = "Effects";
            }
            case 2 -> {
                handbook = new ItemStack(VWItems.Pages.ITEMS_HANDBOOK.get());
                handbookType = PlayerAttachment.RECEIVED_ITEMS_HANDBOOK.get();
                handbookName = "Items";
            }
            default -> {
                handbook = new ItemStack(VWItems.Pages.FEATURES_HANDBOOK.get());
                handbookType = PlayerAttachment.RECEIVED_FEATURES_HANDBOOK.get();
                handbookName = "Features";
            }
        }

        Inventory inv = player.getInventory();
        boolean hasItem = inv.contains(handbook);
        if (hasItem) {
            sendToChat(player, false, "You already have the " + handbookName + " Handbook!");
        } else if (mainHand.isEmpty()) {
            player.setItemSlot(EquipmentSlot.MAINHAND, handbook);
        } else {
            int slot = inv.getFreeSlot();
            if (slot < 1) {
                player.spawnAtLocation(player.level(), handbook);
            } else {
                inv.add(slot, handbook);
            }
        }

        player.setData(handbookType, 1);
    }

    private static ServerPlayer getPlayer(CommandContext<CommandSourceStack> context) {
        ServerPlayer player;
        try {
            player = context.getSource().getPlayerOrException();
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("This command must be run by a player."));
            return null;
        }
        return player;
    }
    private static void handbookCopy(CommandContext<CommandSourceStack> c) {
        sendSuccess(c, false, "You can request another copy after your next respawn.");
    }
    private static void sendSuccess(CommandContext<CommandSourceStack> c, boolean broadcast, String msg) {
        c.getSource().sendSuccess(() -> Component.literal(msg), broadcast);
    }
    private static void sendSuccess(CommandSourceStack s, boolean broadcast, String msg) {
        s.sendSuccess(() -> Component.literal(msg), broadcast);
    }
    private static void sendFail(CommandContext<CommandSourceStack> c, String msg) {
        c.getSource().sendFailure(Component.literal(msg));
    }
    private static void sendFail(CommandContext<CommandSourceStack> c, int color, String msg) {
        c.getSource().sendFailure(Component.literal(msg).withColor(color));
    }
    private static void failed(CommandContext<CommandSourceStack> c) {
        failed(c.getSource());
    }
    private static void failed(CommandSourceStack c) {
        c.sendFailure(Component.literal("Failed to execute command."));
    }
}