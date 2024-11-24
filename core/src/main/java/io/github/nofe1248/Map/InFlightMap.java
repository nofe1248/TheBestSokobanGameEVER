package io.github.nofe1248.Map;

public class InFlightMap {
    private SokobanMap map;
    private Player player;

    public InFlightMap(SokobanMap map, Player player) {
        this.map = map;
        this.player = player;
    }

    public SokobanMap getMap() {
        return map;
    }

    public void setMap(SokobanMap map) {
        this.map = map;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
