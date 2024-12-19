package io.github.nofe1248.map.test;

import io.github.nofe1248.map.generator.MapGenerator;
import io.github.nofe1248.map.map.Map;

import java.nio.file.Path;

public class MapGeneratorTest {
    public static void main(String[] args) {
        MapGenerator mapGenerator = new MapGenerator();

        mapGenerator.setWidth(15);
        mapGenerator.setHeight(15);
        Map map = mapGenerator.generateMap();
        System.out.println(map);
        map.saveMapImage(Path.of("map.png"));
    }
}
