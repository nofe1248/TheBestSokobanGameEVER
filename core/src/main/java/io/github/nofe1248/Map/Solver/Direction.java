package io.github.nofe1248.Map.Solver;

import java.awt.Point;

public class Direction {
    public static final Point UP = new Point(-1, 0);
    public static final Point DOWN = new Point(1, 0);
    public static final Point LEFT = new Point(0, -1);
    public static final Point RIGHT = new Point(0, 1);

    private Direction() {}

    public static char directionToChar(Point direction) {
        if (direction.equals(UP)) return 'U';
        if (direction.equals(DOWN)) return 'D';
        if (direction.equals(LEFT)) return 'L';
        if (direction.equals(RIGHT)) return 'R';
        throw new IllegalArgumentException("Illegal direction: " + direction);
    }
}
