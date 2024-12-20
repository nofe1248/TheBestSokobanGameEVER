package io.github.nofe1248.map.map;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Map implements Cloneable {
    public static final int MIN_WIDTH = 5;
    public static final int MIN_HEIGHT = 5;
    public static final int MAX_WIDTH = 15;
    public static final int MAX_HEIGHT = 15;
    public static final int MIN_BOXES = 2;
    public static final int MAX_BOXES = 15;
    public static final double MIN_DIFFICULTY = 20;
    public static final double MAX_DIFFICULTY = 40;

    MapElement[][] underlyingMap;
    Point playerPosition = null;
    Set<Point> boxPositions = new HashSet<>();
    Set<Point> goalPositions = new HashSet<>();
    int seed = 0;
    double difficulty = 0;
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
        this.fromJSONString(rawMap);
    }

    //load map from file
    public Map(Path path) {
        try {
            String rawMap = Files.readString(path);
            this.fromJSONString(rawMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map(JSONObject obj) {
        this.fromJSON(obj);
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
                //the smaller the map, the larger the cell size, the 15 * 15 map will have a cell size of 30
                //and the 5 * 5 map will have a cell size of 220
                int width = 30 + (15 - getWidth()) * 4;
                int height = 30 + (15 - getHeight()) * 4;
                this.renderTable.add(mapElement.getActor()).width(width).height(height);
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

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
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
        var cell = this.renderTable.getCells().get(convertPointToIndexInRenderTable(x, y));
        assert cell != null;
        cell.clearActor();
        cell.setActor(element.getActor());
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
        setMapElement(position.x, position.y, element);
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

    public String getStringRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (MapElement[] row : underlyingMap) {
            for (MapElement element : row) {
                sb.append(element.toCharRepresentation());
            }
            sb.append("\n");
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
        return String.format("Map(height: %d, width: %d, seed: %d):\n%s", underlyingMap.length, underlyingMap[0].length, this.seed, this.getStringRepresentation());
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

    public void fromJSON(JSONObject obj) {
        assert obj.containsKey("width");
        assert obj.containsKey("height");
        assert obj.containsKey("seed");
        assert obj.containsKey("difficulty");
        assert obj.containsKey("map");

        assert obj.getInteger("width") >= MIN_WIDTH && obj.getInteger("width") <= MAX_WIDTH;
        assert obj.getInteger("height") >= MIN_HEIGHT && obj.getInteger("height") <= MAX_HEIGHT;
        assert obj.getInteger("seed") >= 0;
        assert obj.getDouble("difficulty") >= MIN_DIFFICULTY && obj.getDouble("difficulty") <= MAX_DIFFICULTY;

        this.seed = obj.getInteger("seed");
        this.difficulty = obj.getDouble("difficulty");
        this.underlyingMap = new MapElement[obj.getInteger("width")][obj.getInteger("height")];
        String map = obj.getString("map");
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                underlyingMap[i][j] = MapElement.fromCharRepresentation(map.charAt(i * getHeight() + j));
            }
        }
        update();
    }

    public void fromJSONString(String json) {
        JSONObject obj = JSONObject.parseObject(json);
        fromJSON(obj);
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("width", getWidth());
        obj.put("height", getHeight());
        obj.put("seed", seed);
        obj.put("difficulty", difficulty);
        obj.put("map", dump());
        return obj;
    }

    public String toJSONString() {
        return toJSON().toJSONString(JSONWriter.Feature.PrettyFormat);
    }

    public void saveMapImage(Path path) {
        //generate an image of the map and save it to the path, each cell is 30x30
        //we use pure color to represent the map element
        //background: 230 180 180
        //wall: 230 230 230
        //goal: 65 243 132
        //box: 243 139 170
        //player: CYAN

        int cellSize = 30;
        int width = getWidth() * cellSize;
        int height = getHeight() * cellSize;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        // Draw the background
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);

        // Draw the map elements
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                MapElement element = getMapElement(i, j);
                switch (element) {
                    case WALL:
                        g.setColor(new Color(230, 230, 230));
                        break;
                    case GOAL:
                        g.setColor(new Color(65, 243, 132));
                        break;
                    case BOX, BOX_ON_GOAL:
                        g.setColor(new Color(243, 139, 170));
                        break;
                    case PLAYER, PLAYER_ON_GOAL:
                        g.setColor(Color.CYAN);
                        break;
                    case EMPTY:
                        g.setColor(new Color(230, 180, 180));
                        break;
                    default:
                        g.setColor(Color.GRAY);
                        break;
                }
                g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }

        g.dispose();

        // Rotate the image 90 degrees counter-clockwise
        BufferedImage rotatedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                rotatedImage.setRGB(i, j, image.getRGB(j, height - 1 - i));
            }
        }

        // Mirror the image horizontally
        BufferedImage mirroredImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mirroredImage.setRGB(i, j, rotatedImage.getRGB(height - 1 - i, j));
            }
        }

        // Pad the image with white (75 alpha) to have a 16:9 aspect ratio, and draw a slim line around the image
        int paddedWidth = Math.max(mirroredImage.getWidth(), mirroredImage.getHeight() * 16 / 9);
        int paddedHeight = Math.max(mirroredImage.getHeight(), mirroredImage.getWidth() * 9 / 16);
        BufferedImage paddedImage = new BufferedImage(paddedWidth, paddedHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = paddedImage.createGraphics();
        g2.setColor(new Color(230, 180, 180, 70));
        g2.fillRect(0, 0, paddedWidth, paddedHeight);
        g2.drawImage(mirroredImage, (paddedWidth - mirroredImage.getWidth()) / 2, (paddedHeight - mirroredImage.getHeight()) / 2, null);
        g2.setColor(Color.GRAY);
        g2.drawRect(0, 0, paddedWidth - 1, paddedHeight - 1);
        g2.dispose();

        try {
            ImageIO.write(paddedImage, "png", new File(path.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
