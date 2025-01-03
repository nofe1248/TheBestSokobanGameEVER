package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.nofe1248.sound.BackgroundMusicSelection;
import io.github.alkalimc.User.*;

public class LoginPanel extends BaseGUI {
    public LoginPanel() {
        super("gui/Login/Login.json", "gui/Login/LoginLayout.json");
    }

    @Override
    public void create() {
        super.create();

        TextField usernameField = this.stage.getRoot().findActor("username");
        assert usernameField != null;
        TextField passwordField = this.stage.getRoot().findActor("password");
        assert passwordField != null;

        TextButton quitButton = this.stage.getRoot().findActor("quit");
        assert quitButton != null;
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                Gdx.app.exit();
            }
        });

        TextButton loginButton = this.stage.getRoot().findActor("login");
        assert loginButton != null;
        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();

                String username = usernameField.getText();
                String password = passwordField.getText();

                if (Login.login(username, password)) {
                    manager.setCurrentGUI(GUISelection.MAIN_MENU);
                }
            }
        });

        TextButton registerButton = this.stage.getRoot().findActor("register");
        assert registerButton != null;
        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();

                String username = usernameField.getText();
                String password = passwordField.getText();

                if (Login.register(username, password)) {
                    manager.setCurrentGUI(GUISelection.MAIN_MENU);
                }
            }
        });

        TextButton guestButton = this.stage.getRoot().findActor("guest");
        assert guestButton != null;
        guestButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();

                UserDataManager.setGuest(true);
                manager.setCurrentGUI(GUISelection.MAIN_MENU);
            }
        });
    }

    @Override
    public void onShow() {
        GUIManager
            .getManager()
            .getBackgroundMusicManager()
            .playBackgroundMusic(BackgroundMusicSelection.MAIN_MENU, false);
    }

    @Override
    public void onHide() {

    }
}
