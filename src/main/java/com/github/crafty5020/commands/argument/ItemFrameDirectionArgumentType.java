package com.github.crafty5020.commands.argument;

import com.github.crafty5020.items.data.MapItemFrameDirection;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ItemFrameDirectionArgumentType implements ArgumentType<MapItemFrameDirection> {
    private final Collection<String> EXAMPLES = Arrays.asList("up", "left", "down", "right");


    public static ItemFrameDirectionArgumentType itemDirection() {
        return new ItemFrameDirectionArgumentType();
    }

    @Override
    public MapItemFrameDirection parse(StringReader reader) throws CommandSyntaxException {
        try {
            String argument = reader.readString();

            MapItemFrameDirection.Direction direction = MapItemFrameDirection.Direction.valueOf(argument);

            return new MapItemFrameDirection(direction);
        } catch (Exception e) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException().create("Invalid item frame direction format. Expected enum of ITEM_FRAME_DIRECTION");
        }
    }

    public static MapItemFrameDirection getDirection(final CommandContext<?> context, String name) {
        return context.getArgument(name, MapItemFrameDirection.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        if ("up".startsWith(builder.getRemaining())) {
            builder.suggest("up");
        }
        if ("left".startsWith(builder.getRemaining())) {
            builder.suggest("left");
        }
        if ("down".startsWith(builder.getRemaining())) {
            builder.suggest("down");
        }
        if ("right".startsWith(builder.getRemaining())) {
            builder.suggest("right");
        }
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
