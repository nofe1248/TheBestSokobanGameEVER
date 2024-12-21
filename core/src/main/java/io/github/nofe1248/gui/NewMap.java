package io.github.nofe1248.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;
import io.github.nofe1248.map.MapManager;
import io.github.nofe1248.map.generator.MapGenerator;
import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.sound.BackgroundMusicSelection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NewMap extends BaseGUI {
    private TextField mapDirectory;
    private ImageButton browseButton;
    private TextButton importMapButton;
    private TextButton generateMapButton;
    private TextField mapSeed;
    private TextField difficulty;
    private TextField row;
    private TextField col;
    private TextButton continueButton;

    private int mapIndex;

    private FileChooser fileChooser;

    public NewMap() {
        super("gui/NewMap/NewMap.json", "gui/NewMap/NewMapLayout.json");
    }

    public void setMapIndex(int mapIndex) {
        this.mapIndex = mapIndex;
    }

    @Override
    public void create() {
        super.create();

        this.mapDirectory = this.stage.getRoot().findActor("map_directory");
        this.browseButton = this.stage.getRoot().findActor("dir");
        this.importMapButton = this.stage.getRoot().findActor("import_map");
        this.generateMapButton = this.stage.getRoot().findActor("generate_map");
        this.mapSeed = this.stage.getRoot().findActor("map_seed");
        this.difficulty = this.stage.getRoot().findActor("difficulty");
        this.row = this.stage.getRoot().findActor("row");
        this.col = this.stage.getRoot().findActor("col");

        assert this.mapDirectory != null;
        assert this.browseButton != null;
        assert this.importMapButton != null;
        assert this.generateMapButton != null;
        assert this.mapSeed != null;
        assert this.difficulty != null;
        assert this.row != null;
        assert this.col != null;

        FileChooser.setDefaultPrefsName("io.github.nofe1248.filechooser");

        this.fileChooser = new FileChooser(FileChooser.Mode.OPEN);
        this.fileChooser.setSelectionMode(FileChooser.SelectionMode.FILES);
        this.fileChooser.setMultiSelectionEnabled(false);
        FileTypeFilter filter = new FileTypeFilter(true);
        filter.addRule("Sokoban Map JSON files", "json");
        this.fileChooser.setFileTypeFilter(filter);
        this.fileChooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected(Array<FileHandle> file) {
                mapDirectory.setText(file.first().path());
            }
        });

        this.browseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                manager.getStage().addActor(fileChooser.fadeIn());
            }
        });

        this.importMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();
                //copy the selected map json file to /map/ directory and rename it to map<mapIndex>.json
                //and generate the map image
                assert mapDirectory.getText() != null;

                try {
                    Files.copy(Path.of(mapDirectory.getText()), Path.of("map/map" + mapIndex + ".json"));
                    String json = Files.readString(Path.of("map/map" + mapIndex + ".json"));
                    Map map = new Map(json);
                    map.saveMapImage(Path.of("map/map" + mapIndex + ".png"));
                    MapManager.updateMapList();
                    manager.setCurrentGUI(GUISelection.START_GAME);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        this.generateMapButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GUIManager manager = GUIManager.getManager();
                manager.getSoundEffectManager().playClick();

                int seed;
                if (!mapSeed.getText().isEmpty()) {
                    seed = Integer.parseInt(mapSeed.getText());
                } else {
                    seed = (int) (Math.random() * Integer.MAX_VALUE);
                }
                double difficulty = Double.parseDouble(NewMap.this.difficulty.getText()) * 2 + 20;
                int row = Integer.parseInt(NewMap.this.row.getText());
                int col = Integer.parseInt(NewMap.this.col.getText());

                if (row < Map.MIN_HEIGHT || row > Map.MAX_HEIGHT) {
                    return;
                }
                if (col < Map.MIN_WIDTH || col > Map.MAX_WIDTH) {
                    return;
                }
                if (difficulty < Map.MIN_DIFFICULTY || difficulty > Map.MAX_DIFFICULTY) {
                    return;
                }
                if (seed < 0) {
                    return;
                }

                //generate a new map with the given seed, difficulty, row, and col
                MapGenerator generator = new MapGenerator();
                generator.setSeed(seed);
                generator.setDifficulty(difficulty);
                generator.setHeight(row);
                generator.setWidth(col);

                Map map = new Map(generator.generateMap());

                try {
                    map.saveMapImage(Path.of("map/map" + mapIndex + ".png"));
                    Files.writeString(Path.of("map/map" + mapIndex + ".json"), map.toJSONString());
                    MapManager.updateMapList();
                    manager.setCurrentGUI(GUISelection.START_GAME);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
    }

    @Override
    public void onShow() {
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
