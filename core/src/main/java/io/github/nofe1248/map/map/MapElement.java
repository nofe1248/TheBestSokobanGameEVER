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
}
