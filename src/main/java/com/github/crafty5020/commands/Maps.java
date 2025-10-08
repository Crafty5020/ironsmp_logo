package com.github.crafty5020.commands;

import com.github.crafty5020.properties.PropertiesManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

public class Maps {
    public static int giveMaps(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "player/s");
        int amountOfMaps = PropertiesManager.getAmountOfMaps();
        int mapsAlrExisting = PropertiesManager.getMapsSpawned();

        ItemStack baseMap = new ItemStack(Items.FILLED_MAP);


        for (ServerPlayerEntity player : players) {
            for (int mapId = 0; mapId < amountOfMaps; mapId++) {
                ItemStack map = baseMap.copy();

                map.set(DataComponentTypes.MAP_ID, new MapIdComponent(mapId + mapsAlrExisting));
                map.set(DataComponentTypes.ITEM_NAME, Text.literal("Logo-" + mapId));

                if (!player.getInventory().insertStack(map)) {
                    player.dropItem(map, true,true);
                }
            }
            player.sendMessageToClient(Text.literal("Gave item frames with logo art to ").append(player.getName()), false);
        }

        context.getSource().sendFeedback(() -> Text.literal("executed command by ").append(context.getSource().getName()).append(Text.literal(": gave maps with logo art")), false);

        return Command.SINGLE_SUCCESS;
    }
}
