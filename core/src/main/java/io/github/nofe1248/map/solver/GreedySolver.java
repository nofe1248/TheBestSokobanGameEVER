package io.github.nofe1248.map.solver;

import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.map.MoveDirection;

import java.awt.*;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

public class GreedySolver {
    public static String greedy(Map map) {
        Queue<SolverState> queue = new PriorityQueue<>();
        HashSet<SolverState> visited = new HashSet<>();
        HashSet<Point> deadlocks = DeadlockDetector.findDeadlocks(map);
        queue.add(new SolverState(map, deadlocks));

        while (!queue.isEmpty()) {
            SolverState currentState = queue.poll();
            visited.add(currentState);

            if (currentState.getMap().isSolved()) {
                return currentState.getMove();
            } else {
                for (MoveDirection direction : MoveDirection.values()) {
                    SolverState newState = new SolverState(currentState);
                    if (newState.move(direction)) {
                        if (!visited.contains(newState)) {
                            queue.add(newState);
                        }
                    }
                }
            }
        }

        return null;
    }
}
