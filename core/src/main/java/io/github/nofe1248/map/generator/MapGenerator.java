package io.github.nofe1248.map.generator;

import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.map.MapElement;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

public class MapGenerator {
    private int width = 0;
    private int height = 0;
    private int boxes = 0;
    private int seed = 114514;
    private double difficulty = 30;
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
        return this.random.nextInt(Map.MAX_WIDTH - Map.MIN_WIDTH + 1) + Map.MIN_WIDTH;
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
        return this.random.nextInt(Map.MAX_HEIGHT - Map.MIN_HEIGHT + 1) + Map.MIN_HEIGHT;
    }
    public int generateAndSetRandomHeight() {
        height = generateRandomHeight();
        this.setHeight(height);
        return height;
    }

    public int getBoxes() {
        return boxes;
    }
    public void updateBoxes() {
        if (this.width == 0 || this.height == 0) {
            this.boxes = 0;
            return;
        }
        int area = width * height;
        double m = (double) (Map.MAX_BOXES - Map.MIN_BOXES) / (Map.MAX_WIDTH * Map.MAX_HEIGHT - Map.MIN_WIDTH * Map.MIN_HEIGHT);
        double b = Map.MIN_BOXES - m * Map.MIN_WIDTH * Map.MIN_HEIGHT;
        this.boxes = (int) (m * area + b);
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

    public Point randomValidPosition() {
        return new Point(random.nextInt(1, width - 1), random.nextInt(1, height - 1));
    }

    public Map generateMap() {
        assert this.width >= Map.MIN_WIDTH && this.width <= Map.MAX_WIDTH;
        assert this.height >= Map.MIN_HEIGHT && this.height <= Map.MAX_HEIGHT;
        assert this.boxes >= Map.MIN_BOXES && this.boxes <= Map.MAX_BOXES;

        /*
         * The generator will initially create a puzzle with a random board size,
         * then the player and the boxes on goals will be randomly placed on the board.
         * The player will only be able to pull boxes from their positions during the generation of a puzzle,
         * breaking every wall on his way, so it is guaranteed that the puzzle will have a valid solution.
         * */

        Map map = null;
        boolean valid = false;

        while(!valid) {
            map = new Map(width, height);
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

            ReversePlayer reversePlayer = new ReversePlayer(map, playerPosition);
            long counter = Math.round(width * height * difficulty);
            while (reversePlayer.getStates().get(reversePlayer.getCurrentStateString()) <= 200 && counter > 0) {
                reversePlayer.update();
                counter--;
            }

            if (reversePlayer.getMap().outOfPlaceBoxesCount() >= (boxesCreated / 3) * 2) {
                valid = true;
            }
            else {
                this.seed++;
            }
        }
        map.update();
        map.setSeed(this.seed);
        MapOptimizer.optimizeMap(map);
        return map;
    }
}
