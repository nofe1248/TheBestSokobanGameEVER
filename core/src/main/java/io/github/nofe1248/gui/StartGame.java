package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.nofe1248.map.MapManager;
import io.github.nofe1248.sound.BackgroundMusicSelection;

public class StartGame extends BaseSaveGUI {
    Texture placeholder = new Texture("images/Menu/frame_Modified.png");

    public StartGame() {
        super("gui/StartGame/StartGame.json", "gui/StartGame/StartGameLayout.json");
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

        updateMapTitleOnPageChange();
    }

    @Override
    protected void updateMapTitleOnPageChange() {
        super.updateMapTitleOnPageChange();
        Image map1Image = MapManager.getMapImage(this.currentPage * 4 - 3);
        Image map2Image = MapManager.getMapImage(this.currentPage * 4 - 2);
        Image map3Image = MapManager.getMapImage(this.currentPage * 4 - 1);
        Image map4Image = MapManager.getMapImage(this.currentPage * 4);
        if (map1Image != null) {
            var imageCell = this.map1Button.getImageCell();
            if (imageCell == null) {
                this.map1Button.clearChildren();
                imageCell = this.map1Button.add(map1Image).width(320).height(180);
            }
            imageCell.width(320);
            imageCell.height(180);
            imageCell.setActor(map1Image);
            this.map1Exists = true;
        } else {
            this.map1Button.clearChildren();
            this.map1Button.add(new Image(placeholder)).width(320).height(180);
            this.map1Exists = false;
        }
        if (map2Image != null) {
            var imageCell = this.map2Button.getImageCell();
            if (imageCell == null) {
                this.map2Button.clearChildren();
                imageCell = this.map2Button.add(map2Image).width(320).height(180);
            }
            imageCell.width(320);
            imageCell.height(180);
            imageCell.setActor(map2Image);
            this.map2Exists = true;
        } else {
            this.map2Button.clearChildren();
            this.map2Button.add(new Image(placeholder)).width(320).height(180);
            this.map2Exists = false;
        }
        if (map3Image != null) {
            var imageCell = this.map3Button.getImageCell();
            if (imageCell == null) {
                this.map3Button.clearChildren();
                imageCell = this.map3Button.add(map3Image).width(320).height(180);
            }
            imageCell.width(320);
            imageCell.height(180);
            imageCell.setActor(map3Image);
            this.map3Exists = true;
        } else {
            this.map3Button.clearChildren();
            this.map3Button.add(new Image(placeholder)).width(320).height(180);
            this.map3Exists = false;
        }
        if (map4Image != null) {
            var imageCell = this.map4Button.getImageCell();
            if (imageCell == null) {
                this.map4Button.clearChildren();
                imageCell = this.map4Button.add(map4Image).width(320).height(180);
            }
            imageCell.width(320);
            imageCell.height(180);
            imageCell.setActor(map4Image);
            this.map4Exists = true;
        } else {
            this.map4Button.clearChildren();
            this.map4Button.add(new Image(placeholder)).width(320).height(180);
            this.map4Exists = false;
        }
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
