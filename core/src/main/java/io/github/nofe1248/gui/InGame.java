package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.nofe1248.map.map.InFlightMap;

public class InGame extends BaseGUI {
    InFlightMap activeMap;
    Table mapRenderTable;

    public InGame() {
        super("gui/Playing/Playing.json", "gui/Playing/PlayingLayout.json");
    }

    @Override
    public void create() {
        super.create();

        ImageButton upButton = this.stage.getRoot().findActor("up");
        assert upButton != null;
        upButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                activeMap.movePlayerUp();
            }
        });

        ImageButton downButton = this.stage.getRoot().findActor("down");
        assert downButton != null;
        downButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                activeMap.movePlayerDown();
            }
        });

        ImageButton leftButton = this.stage.getRoot().findActor("left");
        assert leftButton != null;
        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                activeMap.movePlayerLeft();
            }
        });

        ImageButton rightButton = this.stage.getRoot().findActor("right");
        assert rightButton != null;
        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                activeMap.movePlayerRight();
            }
        });
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
        mapRenderTable = this.activeMap.getMap().getRenderTable();
        mapRenderTable.padBottom(100);
        this.stage.addActor(mapRenderTable);
    }

    @Override
    public void onHide() {

    }

    @Override
    public void input() {
        super.input();

        if (this.activeMap != null) {
            mapRenderTable.remove();
            //need to be adjusted to prevent very fast moving when holding the key
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                activeMap.movePlayerUp();
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                activeMap.movePlayerDown();
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                activeMap.movePlayerLeft();
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                activeMap.movePlayerRight();
            }
            mapRenderTable = this.activeMap.getMap().getRenderTable();
            mapRenderTable.padBottom(100);
            this.stage.addActor(mapRenderTable);
        }
    }
}
