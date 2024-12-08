package io.github.nofe1248.map.map;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Map {
    MapElement[][] underlyingMap;

    public Map(int width, int height) {
        underlyingMap = new MapElement[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                underlyingMap[i][j] = MapElement.WALL;
            }
        }
    }

    public Map(MapElement[][] underlyingMap) {
        this.underlyingMap = underlyingMap.clone();
    }

    public Map(String rawMap) {
        this.fromRawMapString(rawMap);
    }

    //load map from file
    public Map(Path path) {
        try {
            String rawMap = Files.readString(path);
            this.fromRawMapString(rawMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        * - The player
        % - The player on a goal
        @ - A box
        X - A goal
        $ - A box on a goal
        + - A wall
        - - An empty position
    */
    public void fromRawMapString(String rawMap) {
        String[] lines = rawMap.split("\n");
        underlyingMap = new MapElement[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                switch (lines[i].charAt(j)) {
                    case '-':
                        underlyingMap[i][j] = MapElement.EMPTY;
                        break;
                    case '+':
                        underlyingMap[i][j] = MapElement.WALL;
                        break;
                    case 'X':
                        underlyingMap[i][j] = MapElement.GOAL;
                        break;
                    case '@':
                        underlyingMap[i][j] = MapElement.BOX;
                        break;
                    case '$':
                        underlyingMap[i][j] = MapElement.BOX_ON_GOAL;
                        break;
                    case '%':
                        underlyingMap[i][j] = MapElement.PLAYER_ON_GOAL;
                        break;
                    case '*':
                        underlyingMap[i][j] = MapElement.PLAYER;
                        break;
                }
            }
        }
    }

    public MapElement[][] getUnderlyingMap() {
        return underlyingMap;
    }

    public void setUnderlyingMap(MapElement[][] underlyingMap) {
        this.underlyingMap = underlyingMap.clone();
    }

    public MapElement getMapElement(int x, int y) {
        return underlyingMap[x][y];
    }

    public MapElement getMapElement(Point position) {
        return underlyingMap[(int)position.getX()][(int)position.getY()];
    }

    public void setMapElement(int x, int y, MapElement element) {
        underlyingMap[x][y] = element;
    }

    public void setMapElement(Point position, MapElement element) {
        underlyingMap[(int)position.getX()][(int)position.getY()] = element;
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        for (MapElement[] row : underlyingMap) {
            for (MapElement element : row) {
                switch (element) {
                    case EMPTY:
                        sb.append('-');
                        break;
                    case WALL:
                        sb.append('+');
                        break;
                    case GOAL:
                        sb.append('X');
                        break;
                    case BOX:
                        sb.append('@');
                        break;
                    case BOX_ON_GOAL:
                        sb.append('$');
                        break;
                    case PLAYER_ON_GOAL:
                        sb.append('%');
                        break;
                    case PLAYER:
                        sb.append('*');
                        break;
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public void dumpMap(Path dumpFilePath) {
        try {
            Files.writeString(dumpFilePath, this.dump());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("Map(%d, %d):\n%s", underlyingMap.length, underlyingMap[0].length, this.dump());
    }
}
