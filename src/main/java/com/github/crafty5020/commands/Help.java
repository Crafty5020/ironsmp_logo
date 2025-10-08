package com.github.crafty5020.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class Help {
    public static int showHelpMessage(CommandContext<ServerCommandSource> context) {
        if (context.getSource().hasPermissionLevel(3)) {
            context.getSource().sendMessage(Text.literal(
                    """
                    Help Message for IronSMP-Logo
                        1. help: This help message
                        2. version: Shows mod version
                        3. give <player/s> <item_frames/maps> <direction> <glow>
                            <invisible> <fixed>: Gives player maps with logo art
                        4. change_maps_spawned_alr <maps_spawned_alr>: Changes
                            the maps spawned alr variable (More info in github)
                        5. change_map_amount <amount>: Changes the map count
                            variable (More info in github)
                    """));
        } else if (context.getSource().hasPermissionLevel(1)) {
            context.getSource().sendMessage(Text.literal(
                    """
                    Help Message for IronSMP-Logo
                        1. help: This help message
                        2. version: Shows mod version
                        3. give <player/s> <item_frames/maps> <direction> <glow>
                            <invisible> <fixed>: Gives player maps with logo art
                    """));
        } else {
            context.getSource().sendMessage(Text.literal(
                    """
                            Help Message for IronSMP-Logo
                                1. help: This help message
                                2. version: Shows mod version
                            """));
        }
        return Command.SINGLE_SUCCESS;
    }
}
