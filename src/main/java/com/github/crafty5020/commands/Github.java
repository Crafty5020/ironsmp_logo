package com.github.crafty5020.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.net.URI;

public class Github {
    public static int githubLink(CommandContext<ServerCommandSource> context) {
        URI githubUrl = URI.create("https://github.com/Crafty5020/ironsmp_logo");

        Text linkText = Text.literal("https://github.com/Crafty5020/ironsmp_logo");

        Style linkStyle = Style.EMPTY.withClickEvent(new ClickEvent.OpenUrl(githubUrl));

        linkStyle = linkStyle.withColor(0xF0C108).withUnderline(true);

        linkText = linkText.copy().setStyle(linkStyle);

        context.getSource().sendMessage(Text.literal("IronSMP-Logo github:  ").append(linkText));
        return Command.SINGLE_SUCCESS;
    }
}
