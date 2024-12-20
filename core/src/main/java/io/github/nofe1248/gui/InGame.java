package io.github.nofe1248.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.nofe1248.map.map.InFlightMap;

public class InGame extends BaseGUI {
    InFlightMap activeMap;

    public InGame() {
        super("gui/Playing/Playing.json", "gui/Playing/PlayingLayout.json");
    }

    @Override
    public void create() {
        super.create();

    }

    public void setActiveMap(InFlightMap activeMap) {
        this.activeMap = activeMap;
    }

    public InFlightMap getActiveMap() {
        return activeMap;
    }

    @Override
    public void onShow() {
        assert activeMap != null;
        Table mapRenderTable = this.activeMap.getMap().getRenderTable();
        mapRenderTable.padBottom(100);
        mapRenderTable.setScale(2, 2);
        this.stage.addActor(mapRenderTable);
    }

    @Override
    public void onHide() {

    }
}
