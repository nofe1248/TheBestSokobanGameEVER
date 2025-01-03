package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.alkalimc.User.Login;
import io.github.alkalimc.User.UserDataManager;
import io.github.nofe1248.map.SaveManager;
import io.github.nofe1248.sound.BackgroundMusicSelection;

public class MainMenu extends BaseGUI {
    private TextButton continueButton;
    private TextButton loadGameButton;

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
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                Login.logout();
                Gdx.app.exit();
            }
        });

        TextButton settingsButton = this.stage.getRoot().findActor("settings");
        assert settingsButton != null;
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.SETTINGS);
            }
        });

        TextButton multiplayerButton = this.stage.getRoot().findActor("multiplayer");
        assert multiplayerButton != null;
        multiplayerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.MULTIPLAYER);
            }
        });

        loadGameButton = this.stage.getRoot().findActor("load_game");
        assert loadGameButton != null;
        loadGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.LOAD_GAME);
            }
        });

        TextButton startGameButton = this.stage.getRoot().findActor("start_game");
        assert startGameButton != null;
        startGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.START_GAME);
            }
        });

        continueButton = this.stage.getRoot().findActor("continue");
        assert continueButton != null;
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                if (manager.getGUI(GUISelection.IN_GAME) instanceof InGame inGame && inGame.getActiveMap() != null && !inGame.getActiveMap().getMap().isSolved()) {
                    manager.setCurrentGUI(GUISelection.IN_GAME);
                } else if (SaveManager.getQuickSaveMap() != null) {
                    InGame gui = (InGame) manager.getGUI(GUISelection.IN_GAME);
                    gui.setActiveMap(SaveManager.getQuickSaveMap());
                    manager.setCurrentGUI(GUISelection.IN_GAME);
                }
            }
        });
    }

    @Override
    public void onShow() {
        GUIManager manager = GUIManager.getManager();
        manager
            .getBackgroundMusicManager()
            .playBackgroundMusic(BackgroundMusicSelection.MAIN_MENU, false);
        if (manager.getGUI(GUISelection.IN_GAME) instanceof InGame inGame
                && inGame.getActiveMap() != null && !inGame.getActiveMap().getMap().isSolved()) {
            continueButton.setVisible(true);
        } else {
            continueButton.setVisible(false);
        }

        SaveManager.loadQuickSave();

        if (SaveManager.getQuickSaveMap() != null) {
            continueButton.setVisible(true);
        }

        if (UserDataManager.getGuest()) {
            loadGameButton.setDisabled(true);
        } else {
            loadGameButton.setDisabled(false);
        }
    }

    @Override
    public void onHide() {

    }
}
