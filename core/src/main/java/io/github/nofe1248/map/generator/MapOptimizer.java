package io.github.nofe1248.map.generator;

import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.map.MapElement;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class MapOptimizer {
    static void optimizeUnusableBoxes(Map map) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (map.getMapElement(i, j).isAnyOf(MapElement.BOX_ON_GOAL)) {
                    boolean left = (map.isPositionValid(i - 1, j) && map.getMapElement(i - 1, j).isAnyOf(MapElement.WALL));
                    boolean right = (map.isPositionValid(i + 1, j) && map.getMapElement(i + 1, j).isAnyOf(MapElement.WALL));
                    boolean down = (map.isPositionValid(i, j - 1) && map.getMapElement(i, j - 1).isAnyOf(MapElement.WALL));
                    boolean up = (map.isPositionValid(i, j + 1) && map.getMapElement(i, j + 1).isAnyOf(MapElement.WALL));

                    if (left && right && down && up) {
                        map.setMapElement(i, j, MapElement.WALL);
                    }
                    else if ((left && up) || (left && down) || (right && up) || (right && down)) {
                        map.setMapElement(i, j, MapElement.WALL);
                    }
                }
            }
        }
    }

    // Remove walls that are not necessary
    static void reduceWall(Map map) {
        boolean[][] shouldRemove = new boolean[map.getWidth()][map.getHeight()];
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                shouldRemove[i][j] = true;
            }
        }
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (!map.getMapElement(i, j).isAnyOf(MapElement.WALL)) {
                    shouldRemove[i][j] = false;
                    if (map.isPositionValid(i - 1, j)) {
                        shouldRemove[i - 1][j] = false;
                    }
                    if (map.isPositionValid(i + 1, j)) {
                        shouldRemove[i + 1][j] = false;
                    }
                    if (map.isPositionValid(i, j - 1)) {
                        shouldRemove[i][j - 1] = false;
                    }
                    if (map.isPositionValid(i, j + 1)) {
                        shouldRemove[i][j + 1] = false;
                    }
                    if (map.isPositionValid(i - 1, j - 1)) {
                        shouldRemove[i - 1][j - 1] = false;
                    }
                    if (map.isPositionValid(i + 1, j - 1)) {
                        shouldRemove[i + 1][j - 1] = false;
                    }
                    if (map.isPositionValid(i - 1, j + 1)) {
                        shouldRemove[i - 1][j + 1] = false;
                    }
                    if (map.isPositionValid(i + 1, j + 1)) {
                        shouldRemove[i + 1][j + 1] = false;
                    }
                }
            }
        }
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (shouldRemove[i][j]) {
                    map.setMapElement(i, j, MapElement.EMPTY);
                }
            }
        }
    }

    static void optimizeMap(Map map) {
        optimizeUnusableBoxes(map);
        reduceWall(map);
    }
}
