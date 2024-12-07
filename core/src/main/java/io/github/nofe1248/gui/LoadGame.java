package io.github.nofe1248.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadGame extends BaseGUI {
    public LoadGame(Viewport viewport, Stage stage) {
        super("gui/LoadGame/LoadGame.json", "gui/LoadGame/LoadGameLayout.json", viewport, stage);
    }
}
