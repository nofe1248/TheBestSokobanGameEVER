package io.github.nofe1248.map.map;

import javax.annotation.Nonnull;
import java.awt.*;

public enum MoveDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    @Nonnull
    public static MoveDirection getOpposite(MoveDirection direction) {
        return switch (direction) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

    public MoveDirection getOpposite() {
        return getOpposite(this);
    }

    @Nonnull
    public Point toMovementVector() {
        return switch (this) {
            case UP -> new Point(-1, 0);
            case DOWN -> new Point(1, 0);
            case LEFT -> new Point(0, -1);
            case RIGHT -> new Point(0, 1);
        };
    }

    public char toChar() {
        return switch (this) {
            case UP -> 'U';
            case DOWN -> 'D';
            case LEFT -> 'L';
            case RIGHT -> 'R';
        };
    }

    public static MoveDirection fromChar(char c) {
        return switch (c) {
            case 'U' -> UP;
            case 'D' -> DOWN;
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> null;
        };
    }
}
