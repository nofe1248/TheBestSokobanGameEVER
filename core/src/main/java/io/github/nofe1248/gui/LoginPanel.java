package io.github.nofe1248.gui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoginPanel extends BaseGUI {
    public LoginPanel(Viewport viewport, Stage stage) {
        super("gui/Login/Login.json", "gui/Login/LoginLayout.json", viewport, stage);
    }
}
