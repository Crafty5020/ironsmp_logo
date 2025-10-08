package com.github.crafty5020.commands;

import com.github.crafty5020.commands.argument.ItemFrameDirectionArgumentType;
import com.github.crafty5020.properties.PropertiesManager;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class CommandHandler {
    public static LiteralArgumentBuilder<ServerCommandSource> getCommands() {
        return CommandManager.literal("iron_logo")
                .then(CommandManager.literal("help")
                    .executes(Help::showHelpMessage)
                )
                .then(CommandManager.literal("version")
                    .executes(Version::showModVersion)
                )
                .then(CommandManager.literal("change_map_amount")
                    .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                            .executes(PropertiesManager::changeMapsAmount)
                    )
                    .requires(source -> source.hasPermissionLevel(3))
                )
                .then(CommandManager.literal("change_maps_spawned_alr")
                    .then(CommandManager.argument("maps_spawned_alr", IntegerArgumentType.integer(0))
                            .executes(PropertiesManager::changeMapsSpawnedAlr)
                    )
                    .requires(source -> source.hasPermissionLevel(3))
                )
                .then(CommandManager.literal("give")
                    .requires(source -> source.hasPermissionLevel(1))
                    .then(CommandManager.argument("player/s", EntityArgumentType.players())
                        .then(CommandManager.literal("item_frames")
                            .then(CommandManager.argument("direction", ItemFrameDirectionArgumentType.itemDirection())
                                .then(CommandManager.argument("glow", BoolArgumentType.bool())
                                    .then(CommandManager.argument("invisible", BoolArgumentType.bool())
                                        .then(CommandManager.argument("fixed", BoolArgumentType.bool())
                                            .executes(ItemFrames::giveItemFrames)
                                        )
                                        .executes(ItemFrames::giveItemFrames)
                                    )
                                    .executes(ItemFrames::giveItemFrames)
                                )
                                .executes(ItemFrames::giveItemFrames)
                            )
                            .executes(ItemFrames::giveItemFrames)
                        )
                        .then(CommandManager.literal("maps")
                            .executes(Maps::giveMaps)
                        )
                    )

                )
                .then(CommandManager.literal("github")
                    .executes(Github::githubLink)
                );



    }
}
