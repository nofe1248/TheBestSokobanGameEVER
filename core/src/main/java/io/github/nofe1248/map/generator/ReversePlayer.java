package io.github.nofe1248.map.generator;

import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.map.MapElement;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ReversePlayer {
    private Map map;
    private ArrayList<MapElement> currentState = new ArrayList<>();
    private DefaultHashMap<String, Integer> states = new DefaultHashMap<>(0);
    private Point previousMove;
    private int playerX;
    private int playerY;

    public ReversePlayer(Map map, Point playerPosition) {
        this.map = map;
        this.previousMove = new Point(0, 0);
        this.playerX = playerPosition.x;
        this.playerY = playerPosition.y;
    }

    public ReversePlayer(Map map, int playerX, int playerY) {
        this.map = map;
        this.previousMove = new Point(0, 0);
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public ArrayList<MapElement> getState() {
        ArrayList<MapElement> state = new ArrayList<>();
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                state.add(map.getMapElement(i, j));
            }
        }
        return state;
    }

    public String getCurrentStateString() {
        StringBuilder state = new StringBuilder();
        for (MapElement element : currentState) {
            state.append(element.toCharRepresentation());
        }
        return state.toString();
    }

    public void update() {
        int height = map.getHeight();
        int width = map.getWidth();
        HashMap<MapElement, MapElement> quickConversionMap = new HashMap<>();
        /*
        quick_chars = {
            '*': '-',
            '%': 'X',
            '+': '*',
            '-': '*',
            'X': '%',
            '@': '-',
            '$': 'X',
        }
        */
        quickConversionMap.put(MapElement.PLAYER, MapElement.EMPTY);
        quickConversionMap.put(MapElement.PLAYER_ON_GOAL, MapElement.GOAL);
        quickConversionMap.put(MapElement.WALL, MapElement.PLAYER);
        quickConversionMap.put(MapElement.EMPTY, MapElement.PLAYER);
        quickConversionMap.put(MapElement.GOAL, MapElement.PLAYER_ON_GOAL);
        quickConversionMap.put(MapElement.BOX, MapElement.EMPTY);
        quickConversionMap.put(MapElement.BOX_ON_GOAL, MapElement.GOAL);

        this.currentState = this.getCurrentState();

        Point move = this.selectRandomMove();
        this.states.put(this.getCurrentStateString(), this.states.get(this.getCurrentStateString()) + 1);

        int currentX = this.playerX;
        int currentY = this.playerY;
        int targetX = currentX + move.x;
        int targetY = currentY + move.y;
        int reverseTargetX = currentX - move.x;
        int reverseTargetY = currentY - move.y;

        System.out.printf("Current pos: %d, %d, moving to %d, %d\n", this.playerX, this.playerY, targetX, targetY);

        if (
            !this.map.isPositionValid(targetX, targetY) ||
            (this.map.isPositionValid(targetX, targetY)
                && this.map.getMapElement(targetX, targetY).isAnyOf(MapElement.BOX, MapElement.BOX_ON_GOAL))
        ) {
            this.previousMove = (Point) move.clone();
            return;
        }
        this.previousMove = new Point(-move.x, -move.y);

        this.map.setMapElement(currentX, currentY, quickConversionMap.get(this.map.getMapElement(currentX, currentY)));
        if (this.map.isPositionValid(targetX, targetY)) {
            this.map.setMapElement(targetX, targetY, quickConversionMap.get(this.map.getMapElement(targetX, targetY)));
        }
        if (this.map.isPositionValid(reverseTargetX, reverseTargetY) &&
            this.map.getMapElement(reverseTargetX, reverseTargetY).isAnyOf(MapElement.BOX, MapElement.BOX_ON_GOAL)) {
            this.map.setMapElement(reverseTargetX, reverseTargetY,
                quickConversionMap.get(this.map.getMapElement(reverseTargetX, reverseTargetY)));

            int boxTargetX = reverseTargetX + move.x;
            int boxTargetY = reverseTargetY + move.y;
            if (this.map.isPositionValid(reverseTargetX, reverseTargetY)) {
                if (this.map.getMapElement(reverseTargetX, reverseTargetY).isAnyOf(MapElement.BOX_ON_GOAL)) {
                    this.map.setMapElement(boxTargetX, boxTargetY, MapElement.GOAL);
                }
                else {
                    this.map.setMapElement(boxTargetX, boxTargetY, MapElement.EMPTY);
                }
            }
            if (this.map.isPositionValid(boxTargetX, boxTargetY)) {
                if (this.map.getMapElement(boxTargetX, boxTargetY).isAnyOf(MapElement.GOAL, MapElement.PLAYER_ON_GOAL)) {
                    this.map.setMapElement(boxTargetX, boxTargetY, MapElement.BOX_ON_GOAL);
                }
                else {
                    this.map.setMapElement(boxTargetX, boxTargetY, MapElement.BOX);
                }
            }
        }
        this.playerX = targetX;
        this.playerY = targetY;
    }

    public Point selectRandomMove() {
        ArrayList<Point> moves = new ArrayList<>(Arrays.asList(
            new Point(1, 0),
            new Point(-1, 0),
            new Point(0, -1),
            new Point(0, 1)
        ));

        ArrayList<Double> weights = new ArrayList<>();
        for (Point move : moves) {
            if (move.equals(this.previousMove)) {
                weights.add(0.1);
            } else {
                weights.add(1.0);
            }
        }

        return weightedRandomChoice(moves, weights);
    }

    private <T> T weightedRandomChoice(java.util.List<T> items, List<Double> weights) {
        if (items.size() != weights.size()) {
            throw new IllegalArgumentException("Items and weights must have the same size");
        }

        double totalWeight = 0.0;
        for (double weight : weights) {
            totalWeight += weight;
        }

        double randomValue = new Random().nextDouble() * totalWeight;
        for (int i = 0; i < items.size(); i++) {
            randomValue -= weights.get(i);
            if (randomValue <= 0.0) {
                return items.get(i);
            }
        }

        return items.get(items.size() - 1); // Fallback in case of rounding errors
    }

    public Map getMap() {
        return map;
    }
    public void setMap(Map map) {
        this.map = map;
    }

    public ArrayList<MapElement> getCurrentState() {
        return currentState;
    }

    public DefaultHashMap<String, Integer> getStates() {
        return states;
    }

    public Point getPreviousMove() {
        return previousMove;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
