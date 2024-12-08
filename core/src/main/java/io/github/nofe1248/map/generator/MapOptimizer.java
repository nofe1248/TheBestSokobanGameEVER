package io.github.nofe1248.map.generator;

import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.map.MapElement;

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
}
