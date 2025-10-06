package com.github.crafty5020.commands;

import com.github.crafty5020.properties.PropertiesManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.TypedEntityData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.Objects;

public class ItemFrames {

    public static int giveItemFrames(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "player/s");
        boolean glow;
        boolean invis;
        boolean fixed;
        int amountOfMaps = PropertiesManager.getAmountOfMaps();
        int mapsAlrExisting = PropertiesManager.getMapsSpawned();

        try {
            glow = BoolArgumentType.getBool(context, "glow");
        } catch (IllegalArgumentException e) {
            glow = false;
        }
        try {
            invis = BoolArgumentType.getBool(context, "invisible");
        } catch (IllegalArgumentException e) {
            invis = false;
        }
        try {
            fixed = BoolArgumentType.getBool(context, "fixed");
        } catch (IllegalArgumentException e) {
            fixed = false;
        }

        ItemStack baseFrame;

        if (glow) {
            baseFrame = new ItemStack(Items.GLOW_ITEM_FRAME);
        } else {
            baseFrame = new ItemStack(Items.ITEM_FRAME);
        }



        for (ServerPlayerEntity player : players) {
            for (int mapId = 0; mapId < amountOfMaps; mapId++) {
                ItemStack frame = baseFrame.copy();

                frame.set(DataComponentTypes.ITEM_NAME, Text.literal("Logo-" + mapId));

                NbtCompound frameNBT = new NbtCompound();

                if (glow) {
                    frameNBT.putString("id", "minecraft:glow_item_frame");
                } else {
                    frameNBT.putString("id", "minecraft:item_frame");
                }

                frameNBT.putBoolean("Invisible", invis);
                frameNBT.putBoolean("Fixed", fixed);

                NbtCompound frameItemNBT = new NbtCompound();

                frameItemNBT.putString("id", "minecraft:filled_map");

                NbtCompound frameItemComponentsNBT = new NbtCompound();

                frameItemComponentsNBT.putInt("map_id", mapId + mapsAlrExisting);

                frameItemNBT.put("components", frameItemComponentsNBT);
                frameNBT.put("Item", frameItemNBT);

                if (glow) {
                    frame.set(DataComponentTypes.ENTITY_DATA, TypedEntityData.create(EntityType.GLOW_ITEM_FRAME, frameNBT));
                } else {
                    frame.set(DataComponentTypes.ENTITY_DATA, TypedEntityData.create(EntityType.ITEM_FRAME, frameNBT));
                }
                if (!player.getInventory().insertStack(frame)) {
                    player.dropItem(frame, true,true);
                }


            }
        }

        context.getSource().sendFeedback(() -> Text.literal("Gave maps with logo art to ").append(Objects.requireNonNull(context.getSource().getPlayer()).getName()), false);

        return Command.SINGLE_SUCCESS;
    }
}
