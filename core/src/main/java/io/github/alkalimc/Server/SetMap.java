package io.github.alkalimc.Server;

import io.github.nofe1248.map.map.Map;

public class SetMap {
    public boolean setMap(Map map) {
        if (Server.sendObject(map)) {
            return true;
        }
        return false;
    }
}
