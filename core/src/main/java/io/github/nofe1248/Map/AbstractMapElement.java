package io.github.nofe1248.Map;

public abstract class AbstractMapElement {
    private int x;
    private int y;
    private String mapIcon;

    public AbstractMapElement(int x, int y, String mapIcon) {
        this.x = x;
        this.y = y;
        this.mapIcon = mapIcon;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public String getMapIcon() {
        return mapIcon;
    }
    public void setMapIcon(String mapIcon) {
        this.mapIcon = mapIcon;
    }

    public void render() {

    }
}
