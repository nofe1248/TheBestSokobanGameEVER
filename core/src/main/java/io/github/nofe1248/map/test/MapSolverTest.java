package io.github.nofe1248.map.test;

import io.github.nofe1248.map.generator.MapGenerator;
import io.github.nofe1248.map.map.Map;
import io.github.nofe1248.map.solver.AStarSolver;

public class MapSolverTest {
    public static void main(String[] args) {
        MapGenerator mapGenerator = new MapGenerator();

        mapGenerator.setWidth(6);
        mapGenerator.setHeight(6);
        Map map = mapGenerator.generateMap();
        System.out.println(map);
        System.out.println("Solution: " + AStarSolver.solve(map, 10));
    }
}
