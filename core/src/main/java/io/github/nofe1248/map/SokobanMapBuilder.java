package io.github.nofe1248.map;

import java.util.List;

public class SokobanMapBuilder {
    private int width;
    private int height;
    private List<AbstractMapElement> elementList;

    public SokobanMapBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public SokobanMapBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public SokobanMapBuilder setElementList(List<AbstractMapElement> elementList) {
        this.elementList = elementList;
        return this;
    }

    public SokobanMap createSokobanMap() {
        return new SokobanMap(width, height, elementList);
    }
}
