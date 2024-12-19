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
        for (int i = 1; i < map.getWidth() - 1; i++) {
            for (int j = 1; j < map.getHeight() - 1; j++) {
                if (shouldRemove[i][j]) {
                    map.setMapElement(i, j, MapElement.EMPTY);
                }
            }
        }
    }

    //break all empty bubbles in the wall that are not connected to the player by removing a wall that form a bubble
    //the algorithm is to visit all empty cells start from the player position, and mark all visited cells
    static void breakBubbles(Map map) {
        Point playerPosition = map.getPlayerPosition();
        boolean[][] visited = new boolean[map.getWidth()][map.getHeight()];
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                visited[i][j] = false;
            }
        }
        Set<Point> current = new HashSet();
        current.add(playerPosition);
        while (!current.isEmpty()) {
            Set<Point> toRemove = new HashSet();
            Set<Point> toAdd = new HashSet();
            for (Point p : current) {
                if (map.isPositionValid(p.x - 1, p.y) && !visited[p.x - 1][p.y] && !map.getMapElement(p.x - 1, p.y).isAnyOf(MapElement.WALL)) {
                    toAdd.add(new Point(p.x - 1, p.y));
                    visited[p.x - 1][p.y] = true;
                }
                if (map.isPositionValid(p.x + 1, p.y) && !visited[p.x + 1][p.y] && !map.getMapElement(p.x + 1, p.y).isAnyOf(MapElement.WALL)) {
                    toAdd.add(new Point(p.x + 1, p.y));
                    visited[p.x + 1][p.y] = true;
                }
                if (map.isPositionValid(p.x, p.y - 1) && !visited[p.x][p.y - 1] && !map.getMapElement(p.x, p.y - 1).isAnyOf(MapElement.WALL)) {
                    toAdd.add(new Point(p.x, p.y - 1));
                    visited[p.x][p.y - 1] = true;
                }
                if (map.isPositionValid(p.x, p.y + 1) && !visited[p.x][p.y + 1] && !map.getMapElement(p.x, p.y + 1).isAnyOf(MapElement.WALL)) {
                    toAdd.add(new Point(p.x, p.y + 1));
                    visited[p.x][p.y + 1] = true;
                }
                toRemove.add(p);
            }
            for (Point p : toRemove) {
                current.remove(p);
            }
            current.addAll(toAdd);
        }
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (!visited[i][j] && map.getMapElement(i, j).isAnyOf(MapElement.EMPTY)) {
                    if (map.isPositionValid(i - 1, j) && !map.isPositionOnBorder(i - 1, j) && map.getMapElement(i - 1, j).isAnyOf(MapElement.WALL)) {
                        map.setMapElement(i - 1, j, MapElement.EMPTY);
                    }
                    else if (map.isPositionValid(i + 1, j) && !map.isPositionOnBorder(i + 1, j) && map.getMapElement(i + 1, j).isAnyOf(MapElement.WALL)) {
                        map.setMapElement(i + 1, j, MapElement.EMPTY);
                    }
                    else if (map.isPositionValid(i, j - 1) && !map.isPositionOnBorder(i, j - 1) && map.getMapElement(i, j - 1).isAnyOf(MapElement.WALL)) {
                        map.setMapElement(i, j - 1, MapElement.EMPTY);
                    }
                    else if (map.isPositionValid(i, j + 1) && !map.isPositionOnBorder(i, j + 1) && map.getMapElement(i, j + 1).isAnyOf(MapElement.WALL)) {
                        map.setMapElement(i, j + 1, MapElement.EMPTY);
                    }
                }
            }
        }
    }

    static void optimizeMap(Map map) {
        optimizeUnusableBoxes(map);
        reduceWall(map);
        breakBubbles(map);
    }
}
