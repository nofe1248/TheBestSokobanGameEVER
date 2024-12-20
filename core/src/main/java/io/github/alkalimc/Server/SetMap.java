package io.github.alkalimc.Server;

import io.github.nofe1248.map.map.Map;

public class SetMap {
    public SetMap(Map map) {
        Server server = new Server(map);
        server.sendObject(map);
    }
}
