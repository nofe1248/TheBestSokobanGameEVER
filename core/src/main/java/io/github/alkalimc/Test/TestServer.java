package io.github.alkalimc.Test;

import io.github.alkalimc.Server.Server;
import io.github.alkalimc.Server.SetMap;
import io.github.nofe1248.map.generator.MapGenerator;
import io.github.nofe1248.map.map.Map;
import java.util.Scanner;

public class TestServer {
    public static void main(String[] args) {
        MapGenerator mapGenerator = new MapGenerator();
        mapGenerator.setWidth(15);
        mapGenerator.setHeight(15);
        Map map = mapGenerator.generateMap();
        System.out.println(map);
        System.out.println("Map Generate Finished");

        Server server = new Server();
        test(server);

        System.out.println("Press Enter to execute testMap...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        testMap(server, map);
    }

    public static void test(Server server) {
        if (server.connect()) {
            System.out.println("Server Start Successfully");
        } else {
            System.out.println("Server Start Failed");
        }
    }

    public static void testMap(Server server, Map map) {
        if (SetMap.setMap(map)) {
            System.out.println("Set Map Finished");
        } else {
            System.out.println("Set Map Failed");
        }
    }
}
