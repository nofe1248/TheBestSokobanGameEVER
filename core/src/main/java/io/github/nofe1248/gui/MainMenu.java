package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenu extends BaseGUI {
    public MainMenu() {
        super("gui/MainMenu/MainMenu.json", "gui/MainMenu/MainMenuLayout.json");
    }

    @Override
    public void create() {
        super.create();

        TextButton quitButton = this.stage.getRoot().findActor("quit");
        assert quitButton != null;
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        TextButton settingsButton = this.stage.getRoot().findActor("settings");
        assert settingsButton != null;
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.setCurrentGUI(GUISelection.SETTINGS);
            }
        });

        TextButton multiplayerButton = this.stage.getRoot().findActor("multiplayer");
        assert multiplayerButton != null;
        multiplayerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.setCurrentGUI(GUISelection.MULTIPLAYER);
            }
        });

        TextButton loadGameButton = this.stage.getRoot().findActor("load_game");
        assert loadGameButton != null;
        loadGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.setCurrentGUI(GUISelection.LOAD_GAME);
            }
        });

        TextButton startGameButton = this.stage.getRoot().findActor("start_game");
        assert startGameButton != null;
        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.setCurrentGUI(GUISelection.START_GAME);
            }
        });
    }
}
