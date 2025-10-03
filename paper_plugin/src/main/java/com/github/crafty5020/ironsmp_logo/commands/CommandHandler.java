package com.github.crafty5020.ironsmp_logo.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;

public class CommandHandler {
	public CommandHandler() {
		LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("iron-logo");
		root.then(Commands.literal("reload"));
		root.then(Commands.literal("help"));
		root.then(Commands.literal("version"));
		root.then(Commands.literal("give")
			    .then(Commands.argument("target", ArgumentTypes.players())
                        .then(Commands.literal("maps"))
                        .then(Commands.literal("item_frames"))
                )
			);
	}
}
