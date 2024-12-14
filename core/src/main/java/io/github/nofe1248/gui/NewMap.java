package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.nofe1248.sound.BackgroundMusicSelection;

public class NewMap extends BaseGUI{
    public NewMap() {
        super("gui/NewMap/NewMap.json", "gui/NewMap/NewMapLayout.json");
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

        TextButton loadGameButton = this.stage.getRoot().findActor("load_game");
        assert loadGameButton != null;
        loadGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.setCurrentGUI(GUISelection.LOAD_GAME);
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
