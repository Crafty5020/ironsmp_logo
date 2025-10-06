package com.github.crafty5020.commands;

import com.github.crafty5020.IronSMPLogo;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.Optional;

public class Version {
    public static int showModVersion(CommandContext<ServerCommandSource> context) {
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer(IronSMPLogo.MOD_ID);

        if (container.isEmpty()) {
            return 0;
        }

        ModMetadata metadata = container.get().getMetadata();

        context.getSource().sendMessage(Text.literal("IronSMP-Logo Version: " + metadata.getVersion()));
        return Command.SINGLE_SUCCESS;
    }
}
