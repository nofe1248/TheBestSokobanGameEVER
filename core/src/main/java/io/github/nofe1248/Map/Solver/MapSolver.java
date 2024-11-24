package io.github.nofe1248.Map.Solver;

import io.github.nofe1248.Map.InFlightMap;
import io.github.nofe1248.Map.SokobanMap;

import java.awt.Point;
import java.util.*;

final class BoxGoalHeuristic {
    public void score(BoardState state) {
        Set<Point> goals = state.getGoals();
        Set<Point> crates = state.getCrates();

        Set<Point> intersection = new HashSet<>(goals);
        intersection.retainAll(crates);

        state.setCost(goals.size() - intersection.size());
    }
}

public final class MapSolver {
    private BoardState currentState;
    private HashSet<BoardState> visited;
    private HashMap<BoardState, BoardState> backtrack;
    private Queue<BoardState> queue;
    private BoxGoalHeuristic heuristic;

    private long startTime;
    private long endTime;
    private int previouslySeen;

    public MapSolver(InFlightMap map) {
        this.currentState = new BoardState(map.getMap(), map.getPlayer().getPosition());
        this.visited = new HashSet<>();
        this.backtrack = new HashMap<>();
        this.queue = new PriorityQueue<BoardState>();
        this.heuristic = new BoxGoalHeuristic();
        this.startTime = this.endTime = -1;
        this.previouslySeen = 0;
    }

    public String search() throws NoSolutionException {
        searchStart();
        while (!this.queue.isEmpty()) {
            currentState = this.queue.poll();
            if (this.visited.contains(currentState)) {
                this.previouslySeen++;
            }
            this.visited.add(currentState);

            if (currentState.isSolved()) {
                System.out.println(currentState.toString());
                stopTimer();
                return backtrackMoves(currentState);
            }

            ArrayList<BoardState> validMoves = getValidMoves();
            searchFunction(validMoves);
        }
        throw new NoSolutionException();
    }

    private void searchStart() {
        this.queue.add(this.currentState);
    }

    private void searchFunction(ArrayList<BoardState> validMoves) {
        for (BoardState move : validMoves) {
            this.backtrack.put(move, this.currentState);
            this.heuristic.score(move);
            this.queue.add(move);
        }
    }

    private ArrayList<BoardState> getValidMoves() {
        ArrayList<BoardState> validMoves = new ArrayList<>(4);
        addIfValid(validMoves, Direction.UP);
        addIfValid(validMoves, Direction.DOWN);
        addIfValid(validMoves, Direction.LEFT);
        addIfValid(validMoves, Direction.RIGHT);
        return validMoves;
    }

    private void addIfValid(ArrayList<BoardState> moves, Point direction) {
        if (this.currentState.canMove(direction)) {
            BoardState newState = currentState.getMove(direction);
            if (!this.visited.contains(newState)) {
                moves.add(newState);
            }
        }
    }

    private String backtrackMoves(BoardState finalState) {
        LinkedList<Character> moveStack = new LinkedList<>();
        BoardState current = finalState;
        while (current.getDirectionTaken() != null) {
            char move = Direction.directionToChar(current.getDirectionTaken());
            moveStack.push(move);
            current = backtrack.get(current);
        }

        StringBuilder builder = new StringBuilder();
        builder.append(moveStack.stream().toList());
        return builder.toString();
    }

    private void startTimer() {
        this.startTime = System.currentTimeMillis();
    }

    private void stopTimer() {
        this.endTime = System.currentTimeMillis();
    }

    public long getElapsedTimeMillis() {
        return this.endTime - this.startTime;
    }

    public int getVisitedLength() {
        return this.visited.size();
    }

    public int getFringeLength() {
        return this.queue.size();
    }

    public int getPreviouslySeen() {
        return this.previouslySeen;
    }

    public int getNodesExplored() {
        return getPreviouslySeen() + getVisitedLength() + getFringeLength();
    }
}
