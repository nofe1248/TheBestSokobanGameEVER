package io.github.nofe1248.map.solver;

import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.map.MapElement;
import org.checkerframework.dataflow.qual.Pure;

import java.awt.*;
import java.util.*;
import java.util.List;

class SolverUtils {
    static int manhattanSum(Map map) {
        int width = map.getWidth(), height = map.getHeight();
        int playerX = map.getPlayerPosition().x, playerY = map.getPlayerPosition().y;
        Set<Point> boxes = map.getBoxPositions();
        Set<Point> goals = map.getGoalPositions();
        int boxes_cost = boxes.size() * width * height;
        int player_cost = Integer.MAX_VALUE;

        assert boxes.size() == goals.size();

        for (Point box : boxes) {
            int min = Integer.MAX_VALUE;
            for (Point goal : goals) {
                min = Math.min(Math.abs(box.x - goal.x) + Math.abs(box.y - goal.y), min);
            }
            boxes_cost += min;
        }

        for (Point box : boxes) {
            player_cost = Math.min(Math.abs(playerX - box.x) + Math.abs(playerY - box.y), player_cost);
        }

        if (player_cost == Integer.MAX_VALUE || boxes_cost == Integer.MAX_VALUE) {
            return -1;
        }
        return boxes_cost + player_cost;
    }

    record CanMoveResult(boolean canMove, int moveCost, Map newMap) {
    }

    @Pure
    static CanMoveResult canMove(Map map, Point move) {
        Map newMap = new Map(map);
        int playerX = map.getPlayerPosition().x, playerY = map.getPlayerPosition().y;
        int move_cost = -1;
        int targetX = playerX + move.x, targetY = playerY + move.y;
        int boxTargetX = playerX + move.x + move.x, boxTargetY = playerY + move.y + move.y;

        if (!newMap.isPositionValid(targetX, targetY)) {
            return new CanMoveResult(false, 0, newMap);
        } else if (newMap.getMapElement(targetX, targetY) == MapElement.WALL) {
            return new CanMoveResult(false, 0, newMap);
        } else if (newMap.getMapElement(targetX, targetY).isAnyOf(MapElement.EMPTY, MapElement.GOAL)) {
            if (newMap.getMapElement(playerX, playerY) == MapElement.PLAYER) {
                newMap.setMapElement(playerX, playerY, MapElement.EMPTY);
            } else {
                newMap.setMapElement(playerX, playerY, MapElement.GOAL);
            }
            if (newMap.getMapElement(targetX, targetY) == MapElement.GOAL) {
                newMap.setMapElement(targetX, targetY, MapElement.PLAYER_ON_GOAL);
            } else {
                newMap.setMapElement(targetX, targetY, MapElement.PLAYER);
            }
            move_cost = 3;
        } else if (newMap.getMapElement(targetX, targetY).isAnyOf(MapElement.BOX, MapElement.BOX_ON_GOAL)) {
            if (!newMap.isPositionValid(boxTargetX, boxTargetY)) {
                return new CanMoveResult(false, 0, newMap);
            } else if (newMap.getMapElement(boxTargetX, boxTargetY).isAnyOf(MapElement.GOAL, MapElement.BOX_ON_GOAL, MapElement.BOX)) {
                return new CanMoveResult(false, 0, newMap);
            } else if (newMap.getMapElement(boxTargetX, boxTargetY).isAnyOf(MapElement.EMPTY, MapElement.GOAL)) {
                if (newMap.getMapElement(boxTargetX, boxTargetY) == MapElement.EMPTY) {
                    newMap.setMapElement(boxTargetX, boxTargetY, MapElement.BOX);
                } else {
                    newMap.setMapElement(boxTargetX, boxTargetY, MapElement.BOX_ON_GOAL);
                }
                if (newMap.getMapElement(targetX, targetY) == MapElement.BOX) {
                    newMap.setMapElement(targetX, targetY, MapElement.PLAYER);
                } else {
                    newMap.setMapElement(targetX, targetY, MapElement.PLAYER_ON_GOAL);
                }
                if (newMap.getMapElement(playerX, playerY) == MapElement.PLAYER) {
                    newMap.setMapElement(playerX, playerY, MapElement.EMPTY);
                } else {
                    newMap.setMapElement(playerX, playerY, MapElement.GOAL);
                }
                if (newMap.getMapElement(boxTargetX, boxTargetY) == MapElement.BOX_ON_GOAL) {
                    move_cost = 0;
                } else {
                    move_cost = 2;
                }
            }
        }
        assert move_cost != -1;
        return new CanMoveResult(true, move_cost, newMap);
    }

    static boolean isDeadlock(Map map) {
        Set<Point> boxes = map.getBoxPositions();

        //corner deadlock
        for (Point boxPosition : boxes) {
            int x = boxPosition.x, y = boxPosition.y;
            if ((map.isPositionValid(x - 1, y) && map.isPositionValid(x, y - 1) &&
                    map.getMapElement(x - 1, y) == MapElement.WALL && map.getMapElement(x, y - 1) == MapElement.WALL) ||
                (map.isPositionValid(x - 1, y) && map.isPositionValid(x, y + 1) &&
                    map.getMapElement(x - 1, y) == MapElement.WALL && map.getMapElement(x, y + 1) == MapElement.WALL) ||
                (map.isPositionValid(x + 1, y) && map.isPositionValid(x, y - 1) &&
                    map.getMapElement(x + 1, y) == MapElement.WALL && map.getMapElement(x, y - 1) == MapElement.WALL) ||
                (map.isPositionValid(x + 1, y) && map.isPositionValid(x, y + 1) &&
                    map.getMapElement(x + 1, y) == MapElement.WALL && map.getMapElement(x, y + 1) == MapElement.WALL)) {
                return true;
            }
        }

        //double boxes deadlock
        ArrayList<ArrayList<Point>> doubleBoxPositions = new ArrayList<>();
        doubleBoxPositions.add(new ArrayList<>(
            List.of(new Point(0, 0), new Point(-1, 0), new Point(0, -1), new Point(-1, -1))));
        doubleBoxPositions.add(new ArrayList<>(
            List.of(new Point(0, 0), new Point(1, 0), new Point(0, -1), new Point(1, -1))));
        doubleBoxPositions.add(new ArrayList<>(
            List.of(new Point(0, 0), new Point(-1, 0), new Point(-1, 1), new Point(0, 1))));
        doubleBoxPositions.add(new ArrayList<>(
            List.of(new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1))));

        for (Point boxPosition : boxes) {
            for (ArrayList<Point> doubleBoxPositionList : doubleBoxPositions) {
                Set<MapElement> positionSet = new HashSet<>();
                for (Point doubleBoxPosition : doubleBoxPositionList) {
                    if (map.isPositionValid(boxPosition.x + doubleBoxPosition.x, boxPosition.y + doubleBoxPosition.y)) {
                        positionSet.add(map.getMapElement(boxPosition.x + doubleBoxPosition.x, boxPosition.y + doubleBoxPosition.y));
                    }
                }
                if (positionSet.contains(MapElement.WALL) && positionSet.contains(MapElement.BOX) && positionSet.size() == 2) {
                    return true;
                }
                if (positionSet.contains(MapElement.BOX) && positionSet.size() == 1) {
                    return true;
                }
                if (positionSet.contains(MapElement.BOX_ON_GOAL)
                    && positionSet.contains(MapElement.BOX) && positionSet.size() == 2) {
                    return true;
                }
                if (positionSet.contains(MapElement.BOX_ON_GOAL) && positionSet.contains(MapElement.BOX)
                    && positionSet.contains(MapElement.WALL) && positionSet.size() == 3) {
                    return true;
                }
            }
        }

        //no enough goal deadlock
        return map.getBoxCount() > map.getGoalCount();
    }
}

public class AStarSolver {
    static class State implements Comparable<State> {
        int totalCost;
        int currentCost;
        Map map;
        int currentDepth;
        String currentAnswer;

        public State(int totalCost, int currentCost, Map map, int currentDepth, String currentAnswer) {
            this.totalCost = totalCost;
            this.currentCost = currentCost;
            this.map = map;
            this.currentDepth = currentDepth;
            this.currentAnswer = currentAnswer;
        }

        @Override
        public int compareTo(State o) {
            return this.totalCost - o.totalCost;
        }
    }

    public static String solve(Map map, int depthLimit) {
        int initialCost = 0, currentDepth = 0;
        int currentCost = SolverUtils.manhattanSum(map);
        Set<Integer> seenState = new HashSet<>();
        PriorityQueue<State> queue = new PriorityQueue<>();
        HashMap<Point, String> direction = new HashMap<>();
        direction.put(new Point(0, -1), "L");
        direction.put(new Point(0, 1), "R");
        direction.put(new Point(-1, 0), "U");
        direction.put(new Point(1, 0), "D");

        queue.add(new State(initialCost, currentCost, map, currentDepth, ""));

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            if (currentState.currentDepth >= depthLimit) {
                continue;
            }
            System.out.println("Current Solution : " + currentState.currentAnswer);
            System.out.println("Current Map : " + currentState.map);
            System.out.println("Current Cost : " + currentState.currentCost);
            System.out.println("Current Depth : " + currentState.currentDepth);
            System.out.println("Current Total Cost : " + currentState.totalCost);
            seenState.add(currentState.map.hashCode());

            for (var move : direction.entrySet()) {
                assert currentState.map.getPlayerPosition() != null;
                int newX = currentState.map.getPlayerPosition().x + move.getKey().x,
                    newY = currentState.map.getPlayerPosition().y + move.getKey().y;

                if (!currentState.map.isPositionValid(newX, newY)) {
                    continue;
                }

                SolverUtils.CanMoveResult canMoveResult = SolverUtils.canMove(currentState.map, move.getKey());
                if (!canMoveResult.canMove()) {
                    continue;
                }

                boolean isDeadlock = SolverUtils.isDeadlock(canMoveResult.newMap());
                if (seenState.contains(canMoveResult.newMap().hashCode()) || isDeadlock) {
                    continue;
                }
                int newCost = SolverUtils.manhattanSum(canMoveResult.newMap());
                if (newCost == -1) {
                    continue;
                }

                State newState = new State(
                    canMoveResult.moveCost() + currentState.currentCost,
                    newCost,
                    canMoveResult.newMap(),
                    currentState.currentDepth + 1,
                    currentState.currentAnswer + move.getValue()
                );
                queue.add(newState);

                if (canMoveResult.newMap().isSolved()) {
                    return newState.currentAnswer;
                }
            }
        }
        return "";
    }
}
