package io.github.nofe1248.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.map.MapElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class MapManager {
    static HashMap<Integer, Map> mapList = new HashMap<>();

    static {
        updateMapList();
    }

    public static Map getMap(int mapNumber) {
        //return the map object with the given map number, if the map does not exist, return null
        return mapList.get(mapNumber);
    }

    public static Image getMapImage(int mapNumber) {
        //load the map image file from the disk, if the file does not exist, create it
        //the map image should be named as map1.png, map2.png, map3.png, etc.
        //return the map image as an Image object
        Path mapImagePath = Paths.get("map", "map" + mapNumber + ".png");
        if (!Files.exists(mapImagePath)) {
            if (mapList.containsKey(mapNumber)) {
                mapList.get(mapNumber).saveMapImage(mapImagePath);
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

    public static Map saveAllMaps() {
        //save all the maps in mapList to the disk
        //the map JSON should be named as map1.json, map2.json, map3.json, etc.
        //save all the map image files to the disk
        //the map image should be named as map1.png, map2.png, map3.png, etc.
        mapList.forEach((key, value) -> {

        });
        return null;
    }

    public static void updateMapList() {
        //scan all the map JSON files under /map/ folder, if the folder does not exist, create it
        //load all the map JSON files into mapList
        //the map JSON should be named as map1.json, map2.json, map3.json, etc.

        Path mapFolderPath = Paths.get("map");
        if (!Files.exists(mapFolderPath)) {
            try {
                Files.createDirectories(mapFolderPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (Stream<Path> paths = Files.walk(mapFolderPath)) {
            paths.filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".json"))
                .forEach(path -> {
                    String fileName = path.getFileName().toString();
                    if (fileName.matches("map\\d+\\.json")) {
                        int mapNumber = Integer.parseInt(fileName.replaceAll("\\D", ""));
                        try {
                            String jsonContent = Files.readString(path);
                            Map map = new Map(jsonContent);
                            map.fromJSONString(jsonContent);
                            mapList.put(mapNumber, map);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //the first 5 maps is builtin, if the map JSON file does not exist, create it
        Map map1 = new Map(new MapElement[][]{
            {MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL},
            {MapElement.WALL, MapElement.PLAYER, MapElement.EMPTY, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.BOX, MapElement.GOAL, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.GOAL, MapElement.BOX, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL}
        });
        map1.setDifficulty(20);
        mapList.put(1, map1);

        Map map2 = new Map(new MapElement[][]{
            {MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.EMPTY},
            {MapElement.WALL, MapElement.PLAYER, MapElement.EMPTY, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.BOX, MapElement.BOX, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.WALL, MapElement.GOAL, MapElement.EMPTY, MapElement.GOAL, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.EMPTY, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL}
        });
        map2.setDifficulty(20);
        mapList.put(2, map2);

        Map map3 = new Map(new MapElement[][]{
            {MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.EMPTY},
            {MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL, MapElement.EMPTY},
            {MapElement.WALL, MapElement.PLAYER, MapElement.EMPTY, MapElement.GOAL, MapElement.BOX, MapElement.WALL, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.EMPTY, MapElement.BOX, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.WALL, MapElement.GOAL, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL}
        });
        map3.setDifficulty(20);
        mapList.put(3, map3);

        Map map4 = new Map(new MapElement[][]{
            {MapElement.EMPTY, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.EMPTY},
            {MapElement.WALL, MapElement.WALL, MapElement.PLAYER, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.BOX, MapElement.BOX_ON_GOAL, MapElement.BOX, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.GOAL, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.GOAL, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL}
        });
        map4.setDifficulty(20);
        mapList.put(4, map4);

        Map map5 = new Map(new MapElement[][]{
            {MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY},
            {MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL, MapElement.WALL, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.EMPTY, MapElement.GOAL, MapElement.GOAL, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.BOX, MapElement.BOX, MapElement.BOX, MapElement.PLAYER, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.EMPTY, MapElement.EMPTY, MapElement.WALL, MapElement.EMPTY, MapElement.GOAL, MapElement.EMPTY, MapElement.WALL},
            {MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL, MapElement.WALL}
        });
        map5.setDifficulty(20);
        mapList.put(5, map5);

        saveAllMaps();
    }
}
