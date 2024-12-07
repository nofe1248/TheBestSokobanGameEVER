package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoginPanel extends BaseGUI {
    public LoginPanel() {
        super("gui/Login/Login.json", "gui/Login/LoginLayout.json");
    }

    @Override
    public void create() {
        super.create();

        TextButton quitButton = this.stage.getRoot().findActor("quit");
        assert quitButton != null;
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.getSoundEffectManager().playClick();
                Gdx.app.exit();
            }
        });

        TextButton loginButton = this.stage.getRoot().findActor("login");
        assert loginButton != null;
        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.MAIN_MENU);
            }
        });

        TextButton registerButton = this.stage.getRoot().findActor("register");
        assert registerButton != null;
        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.MAIN_MENU);
            }
        });
    }
}
