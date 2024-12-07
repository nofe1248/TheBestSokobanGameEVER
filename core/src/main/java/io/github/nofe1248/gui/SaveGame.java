package io.github.nofe1248.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SaveGame extends BaseGUI {
    public SaveGame(Viewport viewport, Stage stage) {
        super("gui/SaveGame/SaveGame.json", "gui/SaveGame/SaveGameLayout.json", viewport, stage);
    }
}
