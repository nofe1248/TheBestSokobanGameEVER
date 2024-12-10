package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Settings extends BaseGUI {
    public Settings() {
        super("gui/Settings/Settings.json", "gui/Settings/SettingsLayout.json");
    }

    @Override
    public void create() {
        super.create();

        Table settingsTable = this.stage.getRoot().findActor("Info");
        assert settingsTable != null;
        Slider masterVolumeSlider = new Slider(0, 1, 0.01f, false, this.skin);
        Slider musicVolumeSlider = new Slider(0, 1, 0.01f, false, this.skin);
        Slider soundEffectVolumeSlider = new Slider(0, 1, 0.01f, false, this.skin);

        var masterVolumeCell = settingsTable.getCells().get(4);
        var musicVolumeCell = settingsTable.getCells().get(5);
        var soundEffectVolumeCell = settingsTable.getCells().get(6);

        assert masterVolumeCell != null;
        assert musicVolumeCell != null;
        assert soundEffectVolumeCell != null;

        masterVolumeCell.padTop(12.8f).setActor(masterVolumeSlider);
        musicVolumeCell.padTop(12.8f).setActor(musicVolumeSlider);
        soundEffectVolumeCell.padTop(12.8f).setActor(soundEffectVolumeSlider);

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

        TextButton returnButton = this.stage.getRoot().findActor("return");
        assert returnButton != null;
        returnButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.getSoundEffectManager().playClick();
                manager.backToPreviousGUI();
            }
        });

        TextButton logoutButton = this.stage.getRoot().findActor("log_out");
        assert logoutButton != null;
        logoutButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.LOGIN_PANEL);
            }
        });

        TextButton mainMenuButton = this.stage.getRoot().findActor("main_menu");
        assert mainMenuButton != null;
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.MAIN_MENU);
            }
        });

        TextButton loadGameButton = this.stage.getRoot().findActor("load_game");
        assert loadGameButton != null;
        loadGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.LOAD_GAME);
            }
        });

        TextButton saveGameButton = this.stage.getRoot().findActor("save_game");
        assert saveGameButton != null;
        saveGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = (GUIManager) Gdx.app.getApplicationListener();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.SAVE_GAME);
            }
        });
    }
}
