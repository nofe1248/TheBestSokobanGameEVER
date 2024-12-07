package io.github.nofe1248.map.Solver;

import io.github.nofe1248.map.*;

import java.awt.*;
import java.util.*;

public class BoardState implements Comparable<BoardState> {
    public static final byte PLAYER = 1;
    public static final byte WALL = 1 << 1;
    public static final byte CRATE = 1 << 2;
    public static final byte GOAL = 1 << 3;

    private static HashMap<Character, Byte> charToField;
    private static HashMap<Byte, Character> fieldToChar;

    static {
        charToField = new HashMap<>();
        charToField.put('W', WALL);
        charToField.put('C', CRATE);
        charToField.put('G', GOAL);
        charToField.put('P', PLAYER);
        charToField.put('+', (byte) (PLAYER | GOAL));
        charToField.put('-', (byte) (CRATE | GOAL));
        charToField.put(' ', (byte) 0);

        fieldToChar = new HashMap<>();
        for (Map.Entry<Character, Byte> entry : charToField.entrySet()) {
            fieldToChar.put(entry.getValue(), entry.getKey());
        }
    }

    private byte[][] board;
    private Point player;
    private Set<Point> goals;
    private Set<Point> crates;
    private Point directionTaken;
    private int cost;

    public BoardState(SokobanMap map, Point player) {
        this.player = player;
        this.board = new byte[map.getWidth()][map.getHeight()];
        this.goals = new HashSet<>();
        this.crates = new HashSet<>();
        this.directionTaken = new Point(0, 0);
        this.cost = 0;
        for (AbstractMapElement element : map.toArrayList()) {
            int x = element.getX();
            int y = element.getY();
            if (element instanceof MapElementCrate) {
                this.board[x][y] = CRATE;
                this.crates.add(new Point(x, y));
            }
            else if (element instanceof MapElementFloor) {
                this.board[x][y] = 0;
            }
            else if (element instanceof MapElementGoal) {
                this.board[x][y] = GOAL;
                this.goals.add(new Point(x, y));
            }
            else if (element instanceof MapElementWall) {
                this.board[x][y] = WALL;
            }
        }
        this.board[player.x][player.y] = PLAYER;
    }

    public BoardState(byte[][] board, Point player, Set<Point> goals, Set<Point> crates, Point directionTaken) {
        this.board = board;
        this.player = player;
        this.goals = goals;
        this.crates = crates;
        this.directionTaken = directionTaken;
    }

    public byte[][] getBoard() {
        return board;
    }

    public Point getDirectionTaken() {
        return directionTaken;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Set<Point> getGoals() {
        return goals;
    }

    public Set<Point> getCrates() {
        return crates;
    }

    public boolean isSolved() {
        for (Point point : this.goals) {
            if (!(pointHas(point, GOAL) && pointHas(point, CRATE))) {
                return false;
            }
        }
        return true;
    }

    public boolean canMove(Point direction) {
        Point newPos = new Point(this.player.x + direction.x, this.player.y + direction.y);
        Point oneOutPos = new Point(newPos.x + direction.x, newPos.y + direction.y);
        if (pointHas(newPos, CRATE)) {
            return !pointHas(oneOutPos, WALL) && !pointHas(oneOutPos, CRATE);
        }
        else if (pointHas(newPos, WALL)) {
            return false;
        }
        return true;
    }

    public BoardState getMove(Point direction) {
        Point newPos = new Point(this.player.x + direction.x, this.player.y + direction.y);
        Point oneOutPos = new Point(newPos.x + direction.x, newPos.y + direction.y);
        Set<Point> newCrates = this.crates;

        byte[][] newBoard = new byte[this.board.length][this.board[0].length];
        for (int i = 0; i < newBoard.length; i++) {
            newBoard[i] = this.board[i].clone();
        }

        byte playerBitField = newBoard[player.x][player.y];
        newBoard[player.x][player.y] = toggleField(playerBitField, PLAYER);

        byte newPlayerBitField = newBoard[newPos.x][newPos.y];
        newBoard[newPos.x][newPos.y] = toggleField(newPlayerBitField, PLAYER);

        if (pointHas(newPos, CRATE)) {
            byte oldBoxBitField = newBoard[newPos.x][newPos.y];
            byte newBoxBitField = newBoard[oneOutPos.x][oneOutPos.y];
            newBoard[newPos.x][newPos.y] = toggleField(oldBoxBitField, CRATE);
            newBoard[oneOutPos.x][oneOutPos.y] = toggleField(newBoxBitField, CRATE);
            newCrates = new HashSet<>(this.crates);
            newCrates.remove(newPos);
            newCrates.add(oneOutPos);
        }

        return new BoardState(newBoard, newPos, goals, newCrates, direction);
    }

    public boolean nextMoveHas(byte field, Point direction) {
        return pointHas(new Point(player.x + direction.x, player.y + direction.y), field);
    }

    private boolean pointHas(int x, int y, byte field) {
        return (board[x][y] & field) == field;
    }

    private boolean pointHas(Point point, byte field) {
        return pointHas(point.x, point.y, field);
    }

    private byte toggleField(byte bitfield, byte field) {
        return (byte) (bitfield ^ field);
    }

    @Override
    public int compareTo(BoardState that) {
        return Integer.compare(this.cost, that.cost);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < this.board.length; x++) {
            for (int y = 0; y < this.board[x].length; y++) {
                builder.append(fieldToChar.get(this.board[x][y]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(board);
        result = prime * result + ((goals == null) ? 0 : goals.hashCode());
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BoardState)) {
            return false;
        }
        BoardState other = (BoardState) obj;
        if (!Arrays.deepEquals(board, other.board)) {
            return false;
        }
        if (goals == null) {
            if (other.goals != null) {
                return false;
            }
        }
        else if (!goals.equals(other.goals)) {
            return false;
        }
        if (player == null) {
            if (other.player != null) {
                return false;
            }
        }
        else if (!player.equals(other.player)) {
            return false;
        }
        return true;
    }
}
