package io.github.nofe1248.map.map;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Map implements Cloneable {
    MapElement[][] underlyingMap;
    Point playerPosition = null;
    Set<Point> boxPositions = new HashSet<>();
    Set<Point> goalPositions = new HashSet<>();
    int seed = 0;
    Table renderTable = new Table();

    public Map(Map map) {
        this(map.getUnderlyingMap());
        this.seed = map.getSeed();
    }

    public Map(int width, int height) {
        assert width >= 6 && width <= 15;
        assert height >= 6 && height <= 15;

        underlyingMap = new MapElement[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                underlyingMap[i][j] = MapElement.WALL;
            }
        }

        createEmptyRenderTable();
    }

    public Map(MapElement[][] underlyingMap) {
        this.underlyingMap = new MapElement[underlyingMap.length][underlyingMap[0].length];
        for (int i = 0; i < underlyingMap.length; i++) {
            this.underlyingMap[i] = Arrays.copyOf(underlyingMap[i], underlyingMap[i].length);
        }
        update();
        assert this.boxPositions.size() == this.goalPositions.size();
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

    public void update() {
        updatePlayerPosition();
        updateBoxPositions();
        updateGoalPositions();
        updateRenderTable();
    }

    private void updatePlayerPosition() {
        int count = 0;
        for (int i = 0; i < underlyingMap.length; i++) {
            for (int j = 0; j < underlyingMap[i].length; j++) {
                if (underlyingMap[i][j].isAnyOf(MapElement.PLAYER, MapElement.PLAYER_ON_GOAL)) {
                    playerPosition = new Point(i, j);
                    count++;
                }
            }
        }
        if (count != 1) {
            playerPosition = null;
            assert false;
        }
    }

    private void updateBoxPositions() {
        boxPositions.clear();
        for (int i = 0; i < underlyingMap.length; i++) {
            for (int j = 0; j < underlyingMap[i].length; j++) {
                if (underlyingMap[i][j].isAnyOf(MapElement.BOX, MapElement.BOX_ON_GOAL)) {
                    boxPositions.add(new Point(i, j));
                }
            }
        }
    }

    private void updateGoalPositions() {
        goalPositions.clear();
        for (int i = 0; i < underlyingMap.length; i++) {
            for (int j = 0; j < underlyingMap[i].length; j++) {
                if (underlyingMap[i][j].isAnyOf(MapElement.GOAL, MapElement.BOX_ON_GOAL, MapElement.PLAYER_ON_GOAL)) {
                    goalPositions.add(new Point(i, j));
                }
            }
        }
    }

    private void updateRenderTable() {
        this.renderTable.clear();
        this.renderTable.setFillParent(true);
        for (MapElement[] mapElements : underlyingMap) {
            for (MapElement mapElement : mapElements) {
                this.renderTable.add(mapElement.getActor()).width(30).height(30);
            }
            this.renderTable.row();
        }
    }

    private void createEmptyRenderTable() {
        this.renderTable.clear();
        this.renderTable.setFillParent(true);
        for (MapElement[] mapElements : underlyingMap) {
            for (MapElement mapElement : mapElements) {
                this.renderTable.add(MapElement.EMPTY.getActor());
            }
            this.renderTable.row();
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

    public boolean isSolved() {
        return this.outOfPlaceBoxesCount() == 0;
    }

    public boolean isPositionValid(Point position) {
        return position.getX() >= 0 && position.getX() < getWidth() && position.getY() >= 0 && position.getY() < getHeight();
    }

    public boolean isPositionValid(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    public boolean isPositionOnBorder(Point position) {
        return position.getX() == 0 || position.getX() == getWidth() - 1 || position.getY() == 0 || position.getY() == getHeight() - 1;
    }

    public boolean isPositionOnBorder(int x, int y) {
        return x == 0 || x == getWidth() - 1 || y == 0 || y == getHeight() - 1;
    }

    public void fromRawMapString(String rawMap) {
        String[] lines = rawMap.split("\n");
        underlyingMap = new MapElement[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                underlyingMap[i][j] = MapElement.fromCharRepresentation(lines[i].charAt(j));
            }
        }
        update();
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
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

    public Table getRenderTable() {
        return renderTable;
    }

    private int convertPointToIndexInRenderTable(Point position) {
        return position.y + position.x * underlyingMap[0].length;
    }

    private int convertPointToIndexInRenderTable(int x, int y) {
        return y + x * underlyingMap[0].length;
    }

    public void setMapElement(int x, int y, MapElement element) {
        this.renderTable.getCells().get(convertPointToIndexInRenderTable(x, y)).getActor().remove();
        this.renderTable.getCells().get(convertPointToIndexInRenderTable(x, y)).setActor(element.getActor());
        if (underlyingMap[x][y] == element) {
            return ;
        }
        if (underlyingMap[x][y] == MapElement.PLAYER) {
            if (this.playerPosition != null && x == playerPosition.getX() && y == playerPosition.getY()) {
                playerPosition = null;
            }
        }
        else if (underlyingMap[x][y] == MapElement.BOX) {
            boxPositions.remove(new Point(x, y));
        }
        else if (underlyingMap[x][y] == MapElement.GOAL) {
            goalPositions.remove(new Point(x, y));
        }
        else if (underlyingMap[x][y] == MapElement.BOX_ON_GOAL) {
            boxPositions.remove(new Point(x, y));
            goalPositions.remove(new Point(x, y));
        }
        else if (underlyingMap[x][y] == MapElement.PLAYER_ON_GOAL) {
            if (this.playerPosition != null && x == playerPosition.getX() && y == playerPosition.getY()) {
                playerPosition = null;
            }
            goalPositions.remove(new Point(x, y));
        }

        if (element == MapElement.PLAYER) {
            playerPosition = new Point(x, y);
        }
        else if (element == MapElement.PLAYER_ON_GOAL) {
            playerPosition = new Point(x, y);
            goalPositions.add(new Point(x, y));
        }
        else if (element == MapElement.BOX_ON_GOAL) {
            boxPositions.add(new Point(x, y));
            goalPositions.add(new Point(x, y));
        }
        else if (element == MapElement.BOX) {
            boxPositions.add(new Point(x, y));
        }
        else if (element == MapElement.GOAL) {
            goalPositions.add(new Point(x, y));
        }

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

    public Point getPlayerPosition() {
        return playerPosition;
    }

    public Set<Point> getBoxPositions() {
        return boxPositions;
    }

    public Set<Point> getGoalPositions() {
        return goalPositions;
    }

    public int getBoxCount() {
        return boxPositions.size();
    }

    public int getGoalCount() {
        return goalPositions.size();
    }

    public String getStateString() {
        StringBuilder sb = new StringBuilder();
        for (MapElement[] row : underlyingMap) {
            for (MapElement element : row) {
                sb.append(element.toCharRepresentation());
            }
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return this.dump().hashCode();
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
        return String.format("Map(height: %d, width: %d, seed: %d):\n%s", underlyingMap.length, underlyingMap[0].length, this.seed, this.dump());
    }

    @Override
    public Map clone() {
        try {
            Map map = (Map) super.clone();
            for (int i = 0; i < underlyingMap.length; i++) {
                System.arraycopy(underlyingMap[i], 0, map.underlyingMap[i], 0, underlyingMap[i].length);
            }
            map.update();
            return map;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
