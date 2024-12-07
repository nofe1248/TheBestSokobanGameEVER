package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Multiplayer extends BaseGUI {
    public Multiplayer() {
        super("gui/Multiplayer/Multiplayer.json", "gui/Multiplayer/MultiplayerLayout.json");
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
                manager.setCurrentGUI(GUISelection.SETTINGS);
                manager.playClick();
                Gdx.app.exit();
            }
        });

        TextButton settingsButton = this.stage.getRoot().findActor("settings");
        assert settingsButton != null;
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.playClick();
                manager.setCurrentGUI(GUISelection.SETTINGS);
            }
        });

        TextButton returnButton = this.stage.getRoot().findActor("return");
        assert returnButton != null;
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.playClick();
                manager.backToPreviousGUI();
            }
        });

        TextButton loadGameButton = this.stage.getRoot().findActor("load_game");
        assert loadGameButton != null;
        loadGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.playClick();
                manager.setCurrentGUI(GUISelection.LOAD_GAME);
            }
        });

        TextButton mainMenuButton = this.stage.getRoot().findActor("main_menu");
        assert mainMenuButton != null;
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.playClick();
                manager.setCurrentGUI(GUISelection.MAIN_MENU);
            }
        });

        TextButton hostGameButton = this.stage.getRoot().findActor("host_game");
        assert hostGameButton != null;
        hostGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.playClick();
            }
        });

        TextButton joinGameButton = this.stage.getRoot().findActor("join_game");
        assert joinGameButton != null;
        joinGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.playClick();
            }
        });
    }
}
