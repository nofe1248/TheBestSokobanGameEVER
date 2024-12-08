package io.github.nofe1248.map.generator;

import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.map.MapElement;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

public class MapGenerator {
    public final int MIN_WIDTH = 6;
    public final int MIN_HEIGHT = 6;
    public final int MAX_WIDTH = 20;
    public final int MAX_HEIGHT = 20;
    public final int MIN_BOXES = 4;
    public final int MAX_BOXES = 20;

    private int width = 0;
    private int height = 0;
    private int boxes = 0;
    private int seed = 114514;
    private Random random;

    {
        random = new Random(seed);
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
        this.updateBoxes();
    }
    public int generateRandomWidth() {
        return this.random.nextInt(MAX_WIDTH - MIN_WIDTH + 1) + MIN_WIDTH;
    }
    public int generateAndSetRandomWidth() {
        width = generateRandomWidth();
        this.setWidth(width);
        return width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
        this.updateBoxes();
    }
    public int generateRandomHeight() {
        return this.random.nextInt(MAX_HEIGHT - MIN_HEIGHT + 1) + MIN_HEIGHT;
    }
    public int generateAndSetRandomHeight() {
        height = generateRandomHeight();
        this.setHeight(height);
        return height;
    }

    public int getBoxes() {
        return boxes;
    }
    public int updateBoxes() {
        if (this.width == 0 || this.height == 0) {
            this.boxes = 0;
            return 0;
        }
        int area = width * height;
        int m = (MAX_BOXES - MIN_BOXES) / (MAX_WIDTH * MAX_HEIGHT - MIN_WIDTH * MIN_HEIGHT);
        int b = MIN_BOXES - m * MIN_WIDTH * MIN_HEIGHT;
        this.boxes = m * area + b;
        return this.boxes;
    }

    public int getSeed() {
        return seed;
    }
    public void setSeed(int seed) {
        this.seed = seed;
    }

    public Point randomValidPosition() {
        return new Point(random.nextInt(1, width), random.nextInt(1, height));
    }

    public Map generateMap() {
        assert this.width >= MIN_WIDTH && this.width <= MAX_WIDTH;
        assert this.height >= MIN_HEIGHT && this.height <= MAX_HEIGHT;
        assert this.boxes >= MIN_BOXES && this.boxes <= MAX_BOXES;
        Map map = new Map(width, height);
        boolean valid = false;
        while(!valid) {
            HashSet<Point> boxesSeen = new HashSet<>();
            Point playerPosition = randomValidPosition();
            int boxesCreated = 0;
            map.setMapElement(playerPosition, MapElement.PLAYER);
            while(boxesCreated < this.boxes) {
                Point boxPosition = randomValidPosition();
                if (map.getMapElement(boxPosition).isAnyOf(MapElement.WALL)) {
                    map.setMapElement(boxPosition, MapElement.BOX_ON_GOAL);
                    boxesSeen.add(boxPosition);
                    boxesCreated++;
                }
            }
            valid = true;
        }
        return map;
    }
}
