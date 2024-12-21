package io.github.nofe1248.map.map;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import io.github.nofe1248.gui.GUIManager;
import io.github.nofe1248.sound.SoundEffectManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InFlightMap {
    private Map map;
    private List<Map> previousMaps = new ArrayList<>();
    private int steps = 0;
    private long elapsedTime = 0;
    private long startTime = System.currentTimeMillis();

    public InFlightMap(Map map) {
        this.map = new Map(map);
    }

    public InFlightMap(JSONObject json) {
        fromJSON(json.toJSONString());
    }

    public InFlightMap(String jsonContent) {
        fromJSON(jsonContent);
    }

    public Map getMap() {
        return map;
    }

    public List<Map> getPreviousMaps() {
        return previousMaps;
    }

    public int getSteps() {
        return steps;
    }

    public long getElapsedTime() {
        updateElapsedTime();
        return elapsedTime;
    }

    public void clearElapsedTime() {
        elapsedTime = 0;
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public void suspendTimer() {
        elapsedTime += System.currentTimeMillis() - startTime;
    }

    public void updateElapsedTime() {
        elapsedTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
    }

    public boolean revertLastMove() {
        if (previousMaps.isEmpty()) {
            return false;
        }
        map = previousMaps.removeLast();
        steps--;
        return true;
    }

    public boolean movePlayer(MoveDirection direction) {
        Point movementVector = direction.toMovementVector();
        Point playerPosition = map.getPlayerPosition();
        Point newPosition = new Point(playerPosition.x + movementVector.x, playerPosition.y + movementVector.y);
        Point boxTargetPosition = new Point(playerPosition.x + movementVector.x * 2, playerPosition.y + movementVector.y * 2);
        Map oldMap = new Map(map);

        SoundEffectManager soundEffectManager = GUIManager.getManager().getSoundEffectManager();

        if (!map.isPositionValid(newPosition)) {
            return false;
        }

        if (map.getMapElement(newPosition).isAnyOf(MapElement.WALL)) {
            return false;
        }

        if (map.getMapElement(newPosition).isAnyOf(MapElement.BOX, MapElement.BOX_ON_GOAL)) {
            if (!map.isPositionValid(boxTargetPosition)) {
                return false;
            }
            if (map.getMapElement(boxTargetPosition).isAnyOf(MapElement.WALL, MapElement.BOX, MapElement.BOX_ON_GOAL)) {
                return false;
            }
            map.setMapElement(boxTargetPosition, map.getMapElement(boxTargetPosition).isAnyOf(MapElement.GOAL) ? MapElement.BOX_ON_GOAL : MapElement.BOX);
            map.setMapElement(newPosition, map.getMapElement(newPosition).isAnyOf(MapElement.GOAL, MapElement.BOX_ON_GOAL) ? MapElement.PLAYER_ON_GOAL : MapElement.PLAYER);
            map.setMapElement(playerPosition, map.getMapElement(playerPosition).isAnyOf(MapElement.PLAYER_ON_GOAL) ? MapElement.GOAL : MapElement.EMPTY);
        }
        else {
            map.setMapElement(newPosition, map.getMapElement(newPosition).isAnyOf(MapElement.GOAL) ? MapElement.PLAYER_ON_GOAL : MapElement.PLAYER);
            map.setMapElement(playerPosition, map.getMapElement(playerPosition).isAnyOf(MapElement.PLAYER_ON_GOAL) ? MapElement.GOAL : MapElement.EMPTY);
        }

        steps++;
        previousMaps.add(oldMap);

        return true;
    }

    public boolean movePlayerUp() {
        return movePlayer(MoveDirection.UP);
    }

    public boolean movePlayerDown() {
        return movePlayer(MoveDirection.DOWN);
    }

    public boolean movePlayerLeft() {
        return movePlayer(MoveDirection.LEFT);
    }

    public boolean movePlayerRight() {
        return movePlayer(MoveDirection.RIGHT);
    }

    public void fromJSON(String jsonContent) {
        JSONObject json = JSONObject.parseObject(jsonContent);

        assert json.containsKey("map");
        assert json.containsKey("previousMaps");
        assert json.containsKey("steps");
        assert json.containsKey("elapsedTime");

        map = new Map(json.getJSONObject("map"));
        previousMaps.clear();
        JSONArray previousMapsJson = json.getJSONArray("previousMaps");
        for (Object previousMapJson : previousMapsJson) {
            previousMaps.add(new Map((JSONObject) previousMapJson));
        }
        steps = json.getIntValue("steps");
        elapsedTime = json.getLongValue("elapsedTime");
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("map", map.toJSON());
        JSONArray previousMapsJson = new JSONArray();
        for (Map previousMap : previousMaps) {
            previousMapsJson.add(previousMap.toJSON());
        }
        json.put("previousMaps", previousMapsJson);
        json.put("steps", steps);
        updateElapsedTime();
        this.startTime = System.currentTimeMillis();
        json.put("elapsedTime", elapsedTime);
        return json;
    }

    public String toJSONString() {
        return toJSON().toJSONString(JSONWriter.Feature.PrettyFormat);
    }

    //calculate the score based on the steps, time, and difficult of the map
    //for the steps and the time, the less the better
    //for the difficulty, the more the better
    //the range is 0-1000
    public int getScore() {
        return 0;
    }
}
