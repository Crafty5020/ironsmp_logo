package com.github.crafty5020.items.data;


public record MapItemFrameDirection(Direction itemDirection) {
    public enum Direction {
        up,
        left,
        down,
        right,
    }

    public int itemDirectionValue() {
        return itemDirection.ordinal();
    }
}
