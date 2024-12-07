package io.github.nofe1248.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu extends BaseGUI {
    public MainMenu(Viewport viewport, Stage stage) {
        super("gui/MainMenu/MainMenu.json", "gui/MainMenu/MainMenuLayout.json", viewport, stage);
    }
}
