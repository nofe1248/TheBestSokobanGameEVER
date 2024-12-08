package io.github.nofe1248.map.map;

public enum MapElement {
    PLAYER,
    PLAYER_ON_GOAL,
    BOX,
    GOAL,
    BOX_ON_GOAL,
    WALL,
    EMPTY;

    public boolean isAnyOf(MapElement... elements) {
        for (MapElement element : elements) {
            if (this == element) {
                return true;
            }
        }
        return false;
    }

    /*
        * - The player
        % - The player on a goal
        @ - A box
        X - A goal
        $ - A box on a goal
        + - A wall
        - - An empty position
    */
    static public MapElement fromCharRepresentation(char raw) {
        return switch (raw) {
            case '*' -> MapElement.PLAYER;
            case '%' -> MapElement.PLAYER_ON_GOAL;
            case '@' -> MapElement.BOX;
            case 'X' -> MapElement.GOAL;
            case '$' -> MapElement.BOX_ON_GOAL;
            case '+' -> MapElement.WALL;
            case '-' -> MapElement.EMPTY;
            default -> throw new IllegalArgumentException("Unexpected char representation of map element");
        };
    }

    public char toCharRepresentation() {
        return switch (this) {
            case PLAYER -> '*';
            case PLAYER_ON_GOAL -> '%';
            case BOX -> '@';
            case GOAL -> 'X';
            case BOX_ON_GOAL -> '$';
            case WALL -> '+';
            case EMPTY -> '-';
        };
    }
}
