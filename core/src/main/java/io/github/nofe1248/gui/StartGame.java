package io.github.nofe1248.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.alkalimc.User.UserDataManager;
import io.github.nofe1248.map.MapManager;
import io.github.nofe1248.map.map.InFlightMap;
import io.github.nofe1248.preferences.GamePreferences;
import io.github.nofe1248.sound.BackgroundMusicSelection;

public class StartGame extends BaseSaveGUI {
    private TextButton continueButton;
    private TextButton loadGameButton;

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

        continueButton = this.stage.getRoot().findActor("continue");
        assert continueButton != null;
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                if (manager.getGUI(GUISelection.IN_GAME) instanceof InGame inGame && inGame.getActiveMap() != null && !inGame.getActiveMap().getMap().isSolved()) {
                    manager.setCurrentGUI(GUISelection.IN_GAME);
                }
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
        updateMapTitleOnPageChange();
        GUIManager manager = GUIManager.getManager();
        manager
            .getBackgroundMusicManager()
            .playBackgroundMusic(BackgroundMusicSelection.MAIN_MENU, false);
        if (manager.getGUI(GUISelection.IN_GAME) instanceof InGame inGame && inGame.getActiveMap() != null && !inGame.getActiveMap().getMap().isSolved()) {
            continueButton.setVisible(true);
        } else {
            continueButton.setVisible(false);
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

    @Override
    protected void mapButtonCallback(int mapIndex, boolean mapExists) {
        GUIManager manager = GUIManager.getManager();
        if (mapExists) {
            manager.getSoundEffectManager().playClick();
            if (!UserDataManager.getGuest()) {
                UserDataManager.getUser().newAttempt();
            }
            ((InGame) manager.getGUI(GUISelection.IN_GAME)).setActiveMap(new InFlightMap(MapManager.getMap(mapIndex), mapIndex));
            manager.setCurrentGUI(GUISelection.IN_GAME);
        }
        else {
            ((NewMap) manager.getGUI(GUISelection.NEW_MAP)).setMapIndex(mapIndex);
            manager.setCurrentGUI(GUISelection.NEW_MAP);
        }
    }
}
