package io.github.test;

import io.github.nofe1248.map.generator.MapGenerator;

public class MapGeneratorTest {
    public static void main(String[] args) {
        MapGenerator mapGenerator = new MapGenerator();
        mapGenerator.generateAndSetRandomHeight();
        mapGenerator.generateAndSetRandomWidth();
        System.out.println(mapGenerator.generateMap());
    }
}
