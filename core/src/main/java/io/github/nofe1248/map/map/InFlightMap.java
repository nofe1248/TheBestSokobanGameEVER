package io.github.nofe1248.map.map;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class InFlightMap {
    Map map;

    public InFlightMap(Map map) {
        this.map = map;
    }

    public Table getRenderTable() {
        Table table = new Table();
        return table;
    }
}
