package io.github.nofe1248.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Settings extends BaseGUI {
    public Settings(Viewport viewport, Stage stage) {
        super("gui/Settings/Settings.json", "gui/Settings/SettingsLayout.json", viewport, stage);
    }
}
