package io.github.alkalimc.Test;

import io.github.alkalimc.Client.Client;
import io.github.alkalimc.Server.Server;
import io.github.alkalimc.Server.SetMap;
import io.github.nofe1248.map.generator.MapGenerator;
import io.github.nofe1248.map.map.Map;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static void main(String[] args) {
        //生成地图准备
        MapGenerator mapGenerator = new MapGenerator();
        mapGenerator.setWidth(15);
        mapGenerator.setHeight(15);
        Map map = mapGenerator.generateMap();
        System.out.println(map);
        System.out.println("Map Generate Finished");
        Server server = new Server();
        TestServer testServer = new TestServer();
        executorService.submit(() -> testServer.test(server));
        Pause.pause();
        Client client = new Client();
        TestClient testClient = new TestClient();
        executorService.submit(() -> testClient.test(client));
        Pause.pause();
        testClient.testConnect(client);
        Pause.pause();
        SetMap setMap = new SetMap();
        if (SetMap.setMap(map)) {
            System.out.println("Set Map Finished");
        }
        else {
            System.out.println("Set Map Failed");
        }
    }
}
