package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.alkalimc.User.UserDataManager;
import io.github.nofe1248.map.SaveManager;
import io.github.nofe1248.map.map.InFlightMap;
import io.github.nofe1248.sound.BackgroundMusicSelection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveGame extends BaseSaveGUI {
    private TextButton continueButton;

    public SaveGame() {
        super("gui/SaveGame/SaveGame.json", "gui/SaveGame/SaveGameLayout.json");
        this.setPrefix("Save ");
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

        TextButton map1TitleButton = this.stage.getRoot().findActor("map1_title");
        assert map1TitleButton != null;
        map1TitleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        TextButton map2TitleButton = this.stage.getRoot().findActor("map2_title");
        assert map2TitleButton != null;
        map2TitleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        TextButton map3TitleButton = this.stage.getRoot().findActor("map3_title");
        assert map3TitleButton != null;
        map3TitleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        TextButton map4TitleButton = this.stage.getRoot().findActor("map4_title");
        assert map4TitleButton != null;
        map4TitleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        ImageButton map1Button = this.stage.getRoot().findActor("map1");
        assert map1Button != null;
        map1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        ImageButton map2Button = this.stage.getRoot().findActor("map2");
        assert map2Button != null;
        map2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        ImageButton map3Button = this.stage.getRoot().findActor("map3");
        assert map3Button != null;
        map3Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        ImageButton map4Button = this.stage.getRoot().findActor("map4");
        assert map4Button != null;
        map4Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        TextButton frontPageButton = this.stage.getRoot().findActor("left_left");
        assert frontPageButton != null;
        frontPageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        TextButton previousPageButton = this.stage.getRoot().findActor("left");
        assert previousPageButton != null;
        previousPageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        TextButton nextPageButton = this.stage.getRoot().findActor("right");
        assert nextPageButton != null;
        nextPageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
            }
        });

        TextButton lastPageButton = this.stage.getRoot().findActor("right_right");
        assert lastPageButton != null;
        lastPageButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
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
    protected void mapButtonCallback(int mapNumber, boolean exists) {
        //save current active map to /save/save-number.json and also save the map image to /save/save-number.png
        //if there already exists a save file, just overwrite it
        //after save update the map image to the new one
        InGame inGame = (InGame) GUIManager.getManager().getGUI(GUISelection.IN_GAME);
        assert inGame != null;
        InFlightMap activeMap = inGame.getActiveMap();
        if (activeMap != null) {
            String jsonString = activeMap.toJSONString();
            try {
                //create the save folder if it does not exist
                if (!Files.exists(Paths.get("save", UserDataManager.getUser().getAccount()))) {
                    Files.createDirectories(Paths.get("save", UserDataManager.getUser().getAccount()));
                }
                Files.writeString(Paths.get("save", UserDataManager.getUser().getAccount(), "save" + mapNumber + ".json"), jsonString);
                activeMap.getMap().saveMapImage(Paths.get("save", UserDataManager.getUser().getAccount(), "save" + mapNumber + ".png"));
                SaveManager.updateSaveList();
                updateMapTitleOnPageChange();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void updateMapTitleOnPageChange() {
        super.updateMapTitleOnPageChange();

        Image map1Image = SaveManager.getMapImage(this.currentPage * 4 - 3);
        Image map2Image = SaveManager.getMapImage(this.currentPage * 4 - 2);
        Image map3Image = SaveManager.getMapImage(this.currentPage * 4 - 1);
        Image map4Image = SaveManager.getMapImage(this.currentPage * 4);
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
        SaveManager.updateSaveList();
        GUIManager manager = GUIManager.getManager();
        manager
            .getBackgroundMusicManager()
            .playBackgroundMusic(BackgroundMusicSelection.MAIN_MENU, false);
        if (manager.getGUI(GUISelection.IN_GAME) instanceof InGame inGame && inGame.getActiveMap() != null && !inGame.getActiveMap().getMap().isSolved()) {
            continueButton.setVisible(true);
        } else {
            continueButton.setVisible(false);
        }
    }

    @Override
    public void onHide() {

    }
}
