package io.github.nofe1248.Map.Generator;

import io.github.nofe1248.Map.*;

import java.awt.*;
import java.util.ArrayList;

public class Level {
    public static final byte PLAYER = 1;
    public static final byte WALL = 1 << 1;
    public static final byte CRATE = 1 << 2;
    public static final byte GOAL = 1 << 3;

    private int xDimension;
    private int yDimension;
    private int numCrates;
    private ArrayList<Point> crates;
    private ArrayList<Point> ghostCrates;
    private ArrayList<Point> goals;
    private int solveCounter;
    private ArrayList<Point> savedPositions;
    private ArrayList<Point> allowedSpots;
    private byte[][] map;

    public Level(int xDimension, int yDimension, int numCrates) {
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.numCrates = numCrates;
        this.solveCounter = 0;
        this.crates = new ArrayList<>(this.numCrates);
        this.ghostCrates = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.savedPositions = new ArrayList<>();
        this.allowedSpots = new ArrayList<>();
        this.map = new byte[this.xDimension][this.yDimension];
        for (int x = 0; x < this.xDimension; x++) {
            for (int y = 0; y < this.yDimension; y++) {
                this.map[x][y] = 0;
            }
        }
    }

    private void randomSpot() {
        if (!this.allowedSpots.isEmpty()) {
            int rand = (int) (Math.random() * this.allowedSpots.size());
            int x = this.allowedSpots.get(rand).x;
            int y = this.allowedSpots.get(rand).y;
        }
    }

    public InFlightMap generateSokobanMap() {
        AbstractMapElement[][] raw_map = new AbstractMapElement[this.xDimension][this.yDimension];
        Player player = null;
        for (int x = 0; x < this.xDimension; x++) {
            for (int y = 0; y < this.yDimension; y++) {
                raw_map[x][y] = switch (this.map[x][y]) {
                    case WALL -> new MapElementWall(x, y);
                    case CRATE -> new MapElementCrate(x, y);
                    case GOAL -> new MapElementGoal(x, y);
                    case PLAYER -> {
                        player = new Player(new Point(x, y));
                        yield new MapElementFloor(x, y);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + this.map[x][y]);
                };
            }
        }
        SokobanMap map = new SokobanMap(raw_map);
        return new InFlightMap(map, player);
    }
}
