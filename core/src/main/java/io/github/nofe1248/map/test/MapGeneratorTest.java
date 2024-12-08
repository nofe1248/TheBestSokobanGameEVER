package io.github.nofe1248.map.test;

import io.github.nofe1248.map.generator.MapGenerator;
import java.util.Random;

public class MapGeneratorTest {
    public static void main(String[] args) {
        MapGenerator mapGenerator = new MapGenerator();

        mapGenerator.setWidth(6);
        mapGenerator.setHeight(6);
        mapGenerator.setSeed(new Random().nextInt());
        System.out.println(mapGenerator.generateMap());
    }
}
