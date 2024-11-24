package io.github.nofe1248.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public final class SokobanMap {
    private final Map<Point, AbstractMapElement> underlyingMap;
    private int width;
    private int height;

    public SokobanMap() {
        this.underlyingMap = new HashMap<>();
    }

    public SokobanMap(int width, int height, List<AbstractMapElement> elementList) {
        this.underlyingMap = new HashMap<>(width * height);
        this.width = width;
        this.height = height;
        if (elementList != null) {
            assert elementList.size() == width * height;
            for (int i = 0; i < elementList.size(); i++) {
                var element = elementList.get(i);
                this.underlyingMap.put(new Point(i % width, i / width), element);
            }
        }
    }

    public SokobanMap(AbstractMapElement[][] element2dMap) {
        assert element2dMap.length > 0;
        assert element2dMap[0].length > 0;
        this.underlyingMap = new HashMap<>(element2dMap.length * element2dMap[0].length);
        this.width = element2dMap[0].length;
        this.height = element2dMap.length;
        for (int x = 0; x < element2dMap.length; x++) {
            for (int y = 0; y < element2dMap[x].length; y++) {
                this.underlyingMap.put(new Point(x, y), element2dMap[x][y]);
            }
        }
    }

    public AbstractMapElement getMapElement(int x, int y) {
        return underlyingMap.get(new Point(x, y));
    }

    public ArrayList<AbstractMapElement> toArrayList() {
        return new ArrayList<>(underlyingMap.values());
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
