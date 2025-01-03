package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.alkalimc.User.UserDataManager;
import io.github.nofe1248.sound.BackgroundMusicSelection;

public class FinishPage extends BaseGUI {
    Label mapTitle;
    Label steps;
    Label time;
    Label score;

    TextButton saveGameButton;
    TextButton loadGameButton;

    public FinishPage() {
        super("gui/FinishPage/FinishPage.json", "gui/FinishPage/FinishPageLayout.json");
    }

    @Override
    public void create() {
        super.create();

        this.mapTitle = this.stage.getRoot().findActor("map");
        this.steps = this.stage.getRoot().findActor("steps");
        this.time = this.stage.getRoot().findActor("time");
        this.score = this.stage.getRoot().findActor("score");
        assert this.mapTitle != null;
        assert this.steps != null;
        assert this.time != null;
        assert this.score != null;

        TextButton retryButton = this.stage.getRoot().findActor("retry");
        assert retryButton != null;
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                ((InGame) manager.getGUI(GUISelection.IN_GAME)).retry();
                manager.setCurrentGUI(GUISelection.IN_GAME);
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

        saveGameButton = this.stage.getRoot().findActor("save_game");
        assert saveGameButton != null;
        saveGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.SAVE_GAME);
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

        TextButton mainMenuButton = this.stage.getRoot().findActor("main_menu");
        assert mainMenuButton != null;
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.MAIN_MENU);
            }
        });

        TextButton mainMenuBottomButton = this.stage.getRoot().findActor("main_menu_bottom");
        assert mainMenuBottomButton != null;
        mainMenuBottomButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.MAIN_MENU);
            }
        });

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

        TextButton returnButton = this.stage.getRoot().findActor("return");
        assert returnButton != null;
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.backToPreviousGUI();
            }
        });
    }

    @Override
    public void onShow() {
        mapTitle.setText("Map: " + ((InGame) GUIManager.getManager().getGUI(GUISelection.IN_GAME)).getActiveMap().getMapNumber());
        steps.setText(((InGame) GUIManager.getManager().getGUI(GUISelection.IN_GAME)).getActiveMap().getSteps());
        time.setText(((InGame) GUIManager.getManager().getGUI(GUISelection.IN_GAME)).getActiveMap().getElapsedTime() / 1000 + "s");
        score.setText(((InGame) GUIManager.getManager().getGUI(GUISelection.IN_GAME)).getActiveMap().getScore());

        if (!UserDataManager.getGuest()) {
            UserDataManager.getUser().newScore(((InGame) GUIManager.getManager().getGUI(GUISelection.IN_GAME)).getActiveMap().getScore());
        }

        GUIManager manager = GUIManager.getManager();
        manager
            .getBackgroundMusicManager()
            .playBackgroundMusic(BackgroundMusicSelection.MAIN_MENU, false);

        if (UserDataManager.getGuest()) {
            loadGameButton.setDisabled(true);
            saveGameButton.setDisabled(true);
        } else {
            loadGameButton.setDisabled(false);
        }
    }

    @Override
    public void onHide() {

    }
}
