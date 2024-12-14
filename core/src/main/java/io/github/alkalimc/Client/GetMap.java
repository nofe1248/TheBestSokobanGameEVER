package io.github.alkalimc.Client;

import io.github.nofe1248.map.map.Map;

public class GetMap {
    public Map GetMap(Client client) {
        return client.sendData("GET_MAP");
    }
}
