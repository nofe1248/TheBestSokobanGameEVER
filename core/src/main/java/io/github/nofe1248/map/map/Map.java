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

    public int outOfPlaceBoxesCount() {
        int count = 0;
        for (int i = 0; i < underlyingMap.length; i++) {
            for (int j = 0; j < underlyingMap[i].length; j++) {
                if (underlyingMap[i][j] == MapElement.BOX) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isPositionValid(Point position) {
        return position.getX() >= 0 && position.getX() < getWidth() && position.getY() >= 0 && position.getY() < getHeight();
    }

    public boolean isPositionValid(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    public void fromRawMapString(String rawMap) {
        String[] lines = rawMap.split("\n");
        underlyingMap = new MapElement[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                underlyingMap[i][j] = MapElement.fromCharRepresentation(lines[i].charAt(j));
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

    public int getWidth() {
        return underlyingMap.length;
    }

    public int getHeight() {
        return underlyingMap[0].length;
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        for (MapElement[] row : underlyingMap) {
            for (MapElement element : row) {
                sb.append(element.toCharRepresentation());
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

    @Override
    public Map clone() {
        return new Map(underlyingMap);
    }
}
