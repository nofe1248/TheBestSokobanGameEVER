package io.github.nofe1248.map.solver;

import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.map.MapElement;
import io.github.nofe1248.map.map.MoveDirection;

import java.awt.*;
import java.util.HashSet;

public class SolverState implements Comparable<SolverState> {
    private final Map map;
    private String move = "";
    private HashSet<Point> deadlocks = new HashSet<>();

    public SolverState(Map map) {
        this.map = new Map(map);
    }

    public SolverState(Map map, HashSet<Point> deadlocks) {
        this.map = new Map(map);
        this.deadlocks = new HashSet<>(deadlocks);
    }

    public SolverState(SolverState state) {
        this.map = new Map(state.getMap());
        this.move = state.getMove();
        this.deadlocks = new HashSet<>(state.deadlocks);
    }

    public Map getMap() {
        return map;
    }

    public String getMove() {
        return move;
    }

    public boolean move(MoveDirection direction) {
        int playerX = map.getPlayerPosition().x, playerY = map.getPlayerPosition().y;
        int targetX = playerX + direction.toMovementVector().x, targetY = playerY + direction.toMovementVector().y;
        int boxTargetX = playerX + direction.toMovementVector().x * 2, boxTargetY = playerY + direction.toMovementVector().y * 2;

        if (map.isPositionValid(targetX, targetY)) {
            if (map.getMapElement(targetX, targetY).isAnyOf(MapElement.WALL)) {
                return false;
            }
            if (map.getMapElement(targetX, targetY).isAnyOf(MapElement.BOX, MapElement.BOX_ON_GOAL)) {
                if (map.isPositionValid(boxTargetX, boxTargetY)) {
                    if (map.getMapElement(boxTargetX, boxTargetY).isAnyOf(MapElement.WALL, MapElement.BOX, MapElement.BOX_ON_GOAL)) {
                        return false;
                    }
                    if (deadlocks.contains(new Point(boxTargetX, boxTargetY))) {
                        return false;
                    }
                    map.setMapElement(boxTargetX, boxTargetY, map.getMapElement(boxTargetX, boxTargetY).isAnyOf(MapElement.GOAL) ? MapElement.BOX_ON_GOAL : MapElement.BOX);
                    map.setMapElement(targetX, targetY, map.getMapElement(targetX, targetY).isAnyOf(MapElement.GOAL, MapElement.BOX_ON_GOAL) ? MapElement.PLAYER_ON_GOAL : MapElement.PLAYER);
                    map.setMapElement(playerX, playerY, map.getMapElement(playerX, playerY).isAnyOf(MapElement.PLAYER_ON_GOAL) ? MapElement.GOAL : MapElement.EMPTY);
                    this.move += direction.toChar();
                } else {
                    return false;
                }
            } else {
                map.setMapElement(targetX, targetY, map.getMapElement(targetX, targetY).isAnyOf(MapElement.GOAL) ? MapElement.PLAYER_ON_GOAL : MapElement.PLAYER);
                map.setMapElement(playerX, playerY, map.getMapElement(playerX, playerY).isAnyOf(MapElement.PLAYER_ON_GOAL) ? MapElement.GOAL : MapElement.EMPTY);
                this.move += direction.toChar();
            }
        } else {
            return false;
        }

        return true;
    }

    public int euclideanDistance() {
        int playerX = map.getPlayerPosition().x, playerY = map.getPlayerPosition().y;
        int playerToBox = 0;

        for (Point box : map.getBoxPositions()) {
            playerToBox += (int) Math.sqrt(Math.pow(playerX - box.x, 2) + Math.pow(playerY - box.y, 2));
        }

        int boxToGoal = 0;
        for (Point box : map.getBoxPositions()) {
            for (Point goal : map.getGoalPositions()) {
                boxToGoal += (int) Math.sqrt(Math.pow(box.x - goal.x, 2) + Math.pow(box.y - goal.y, 2));
            }
        }

        return playerToBox + boxToGoal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SolverState state) {
            return map.getBoxPositions().equals(state.getMap().getBoxPositions()) && map.getPlayerPosition().equals(state.getMap().getPlayerPosition());
        }
        return false;
    }

    @Override
    public int compareTo(SolverState state) {
        return Integer.compare(euclideanDistance(), state.euclideanDistance());
    }

    @Override
    public String toString() {
        return map.getStringRepresentation() + move;
    }

    @Override
    public int hashCode() {
        int boxHash = 0;
        for (Point box : map.getBoxPositions()) {
            boxHash += box.hashCode();
            boxHash *= 37;
        }
        return map.getPlayerPosition().x * 73 + map.getPlayerPosition().y + boxHash;
    }
}
