package io.github.nofe1248.map.solver;

import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.map.MapElement;
import io.github.nofe1248.map.map.MoveDirection;

import java.awt.*;
import java.util.HashSet;

public class DeadlockDetector {
    public static HashSet<Point> findDeadlocks(Map map) {
        HashSet<Point> deadlocks = new HashSet<>();

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if (map.getMapElement(x, y).isAnyOf(MapElement.WALL, MapElement.PLAYER_ON_GOAL, MapElement.BOX_ON_GOAL, MapElement.GOAL)) {
                    continue;
                }

                if (cornerTest(map, x, y) || boundaryTest(map, x, y)) {
                    deadlocks.add(new Point(x, y));
                }
            }
        }

        return deadlocks;
    }

    private static boolean cornerTest(Map map, int x, int y) {
        return (map.isPositionValid(x - 1, y) && map.getMapElement(x - 1, y).isAnyOf(MapElement.WALL)) ||
            (map.isPositionValid(x, y - 1) && map.getMapElement(x, y - 1).isAnyOf(MapElement.WALL)) ||
            (map.isPositionValid(x + 1, y) && map.getMapElement(x + 1, y).isAnyOf(MapElement.WALL)) ||
            (map.isPositionValid(x, y + 1) && map.getMapElement(x, y + 1).isAnyOf(MapElement.WALL));
    }

    private static boolean boundaryTest(Map map, int x, int y) {
        //left boundary
        if (map.isPositionValid(x, y - 1) && map.getMapElement(x, y - 1).isAnyOf(MapElement.WALL)) {
            Point upbound = findNearestWallTowardsDirection(map, x, y, MoveDirection.UP);
            Point downbound = findNearestWallTowardsDirection(map, x, y, MoveDirection.DOWN);

            if (upbound == null || downbound == null) {
                return false;
            } else {
                for (int m = upbound.x + 1; m < downbound.x; m++) {
                    if (map.isPositionValid(m, y - 1) && !map.getMapElement(m, y - 1).isAnyOf(MapElement.WALL)) {
                        return false;
                    }
                }
            }

            return true;
        }

        //right boundary
        if (map.isPositionValid(x, y + 1) && map.getMapElement(x, y + 1).isAnyOf(MapElement.WALL)) {
            Point upbound = findNearestWallTowardsDirection(map, x, y, MoveDirection.UP);
            Point downbound = findNearestWallTowardsDirection(map, x, y, MoveDirection.DOWN);

            if (upbound == null || downbound == null) {
                return false;
            } else {
                for (int m = upbound.x + 1; m < downbound.x; m++) {
                    if (map.isPositionValid(m, y + 1) && !map.getMapElement(m, y + 1).isAnyOf(MapElement.WALL)) {
                        return false;
                    }
                }
            }

            return true;
        }

        //up boundary
        if (map.isPositionValid(x - 1, y) && map.getMapElement(x - 1, y).isAnyOf(MapElement.WALL)) {
            Point leftbound = findNearestWallTowardsDirection(map, x, y, MoveDirection.LEFT);
            Point rightbound = findNearestWallTowardsDirection(map, x, y, MoveDirection.RIGHT);

            if (leftbound == null || rightbound == null) {
                return false;
            } else {
                for (int m = leftbound.y + 1; m < rightbound.y; m++) {
                    if (map.isPositionValid(x - 1, m) && !map.getMapElement(x - 1, m).isAnyOf(MapElement.WALL)) {
                        return false;
                    }
                }
            }

            return true;
        }

        //down boundary
        if (map.isPositionValid(x + 1, y) && map.getMapElement(x + 1, y).isAnyOf(MapElement.WALL)) {
            Point leftbound = findNearestWallTowardsDirection(map, x, y, MoveDirection.LEFT);
            Point rightbound = findNearestWallTowardsDirection(map, x, y, MoveDirection.RIGHT);

            if (leftbound == null || rightbound == null) {
                return false;
            } else {
                for (int m = leftbound.y + 1; m < rightbound.y; m++) {
                    if (map.isPositionValid(x + 1, m) && !map.getMapElement(x + 1, m).isAnyOf(MapElement.WALL)) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    private static Point findNearestWallTowardsDirection(Map map, int x, int y, MoveDirection direction) {
        int dx = direction.toMovementVector().x;
        int dy = direction.toMovementVector().y;
        int newX = x + dx;
        int newY = y + dy;

        while (map.isPositionValid(newX, newY)) {
            if (map.getMapElement(newX, newY).isAnyOf(MapElement.WALL)) {
                return new Point(newX, newY);
            } else if (map.getMapElement(newX, newY).isAnyOf(MapElement.BOX, MapElement.BOX_ON_GOAL)) {
                return null;
            }
            newX += dx;
            newY += dy;
        }

        return null;
    }
}
