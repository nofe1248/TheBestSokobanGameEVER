package io.github.nofe1248.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.alkalimc.User.User;
import io.github.alkalimc.User.UserDataManager;
import io.github.nofe1248.gui.GUIManager;
import io.github.nofe1248.gui.GUISelection;
import io.github.nofe1248.gui.InGame;
import io.github.nofe1248.map.map.InFlightMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class SaveManager {
    static HashMap<Integer, InFlightMap> saveList = new HashMap<>();
    static InFlightMap quickSaveMap = null;

    public static InFlightMap getSave(int saveNumber) {
        //return the save object with the given save number, if the save does not exist, return null
        return saveList.get(saveNumber);
    }

    public static void loadQuickSave() {
        if (UserDataManager.getGuest()) {
            return;
        }
        try {
            String json = Files.readString(Path.of("save", UserDataManager.getUser().getAccount(), "quicksave.json"));
            quickSaveMap = new InFlightMap(json);
        } catch (IOException e) {
            return ;
        }
    }

    public static InFlightMap getQuickSaveMap() {
        return quickSaveMap;
    }

    public static void saveQuickSave() {
        if (UserDataManager.getGuest()) {
            return;
        }
        InGame ingameUI = (InGame) GUIManager.getManager().getGUI(GUISelection.IN_GAME);
        if (ingameUI.getActiveMap() != null) {
            String json = ingameUI.getActiveMap().toJSONString();
            try {
                Files.writeString(Path.of("save", UserDataManager.getUser().getAccount(), "quicksave.json"), json);
            } catch (IOException e) {
                return ;
            }
        }
    }

    public static Image getMapImage(int mapNumber) {
        //load the map image file from the disk, if the file does not exist, create it
        //the map image should be named as map1.png, map2.png, map3.png, etc.
        //return the map image as an Image object

        if (UserDataManager.getGuest()) {
            return null;
        }

        Path saveFolderPath = Paths.get("save/" + UserDataManager.getUser().getAccount());
        if (!Files.exists(saveFolderPath)) {
            try {
                Files.createDirectories(saveFolderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path mapImagePath = Paths.get("save", UserDataManager.getUser().getAccount(), "save" + mapNumber + ".png");
        if (!Files.exists(mapImagePath)) {
            if (saveList.containsKey(mapNumber)) {
                saveList.get(mapNumber).getMap().saveMapImage(mapImagePath);
            }
            else {
                return null;
            }
        }
        Image mapImage = new Image(new Texture(mapImagePath.toString()));
        mapImage.setHeight(180);
        mapImage.setWidth(320);
        return mapImage;
    }

    public static void updateSaveList() {
        //scan all the save JSON files under /save/user folder, if the folder does not exist, create it
        //load all the save JSON files into saveList
        //the save JSON should be named as save1.json, save2.json, save3.json, etc.

        if (UserDataManager.getGuest()) {
            return ;
        }

        Path saveFolderPath = Paths.get("save/" + UserDataManager.getUser().getAccount());
        if (!Files.exists(saveFolderPath)) {
            try {
                Files.createDirectories(saveFolderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (Stream<Path> paths = Files.walk(saveFolderPath)) {
            paths.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".json"))
                .forEach(path -> {
                    String fileName = path.getFileName().toString();
                    if (fileName.matches("save\\d+\\.json")) {
                        int saveNumber = Integer.parseInt(fileName.replaceAll("\\D", ""));
                        try {
                            String jsonContent = Files.readString(path);
                            InFlightMap save = new InFlightMap(jsonContent);
                            saveList.put(saveNumber, save);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
