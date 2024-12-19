package io.github.nofe1248.map.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import io.github.nofe1248.preferences.GamePreferences;

public enum MapElement {
    PLAYER,
    PLAYER_ON_GOAL,
    BOX,
    GOAL,
    BOX_ON_GOAL,
    WALL,
    EMPTY;

    static Texture playerMonikaTexture = new Texture("images/Character/Monika/Monika.png");
    static Texture playerNatsukiTexture = new Texture("images/Character/Natsuki/Natsuki.png");
    static Texture playerSayoriTexture = new Texture("images/Character/Sayori/Sayori.png");
    static Texture playerYuriTexture = new Texture("images/Character/Yuri/Yuri.png");
    static Texture boxTexture = new Texture("images/Block/Chest.png");
    static Texture goalTexture = new Texture("images/Block/Goal.png");
    static Texture wallTexture = new Texture("images/Block/Wall.png");
    static Texture emptyTexture = new Texture("images/Block/Ground.png");

    public boolean isAnyOf(MapElement... elements) {
        for (MapElement element : elements) {
            if (this == element) {
                return true;
            }
        }
        return false;
    }

    /*
        * - The player
        % - The player on a goal
        @ - A box
        X - A goal
        $ - A box on a goal
        + - A wall
        - - An empty position
    */
    static public MapElement fromCharRepresentation(char raw) {
        return switch (raw) {
            case '*' -> MapElement.PLAYER;
            case '%' -> MapElement.PLAYER_ON_GOAL;
            case '@' -> MapElement.BOX;
            case 'X' -> MapElement.GOAL;
            case '$' -> MapElement.BOX_ON_GOAL;
            case '+' -> MapElement.WALL;
            case '-' -> MapElement.EMPTY;
            default -> throw new IllegalArgumentException("Unexpected char representation of map element");
        };
    }

    public char toCharRepresentation() {
        return switch (this) {
            case PLAYER -> '*';
            case PLAYER_ON_GOAL -> '%';
            case BOX -> '@';
            case GOAL -> 'X';
            case BOX_ON_GOAL -> '$';
            case WALL -> '+';
            case EMPTY -> '-';
        };
    }

    public Actor getActor() {
        return switch (this) {
            case PLAYER -> {
                Stack stack = new Stack();
                stack.add(new Image(emptyTexture));
                stack.add(new Image(switch (GamePreferences.getCharacterSelection()) {
                    case MONIKA -> playerMonikaTexture;
                    case NATSUKI -> playerNatsukiTexture;
                    case SAYORI -> playerSayoriTexture;
                    case YURI -> playerYuriTexture;
                }));
                yield stack;
            }
            case PLAYER_ON_GOAL -> {
                Stack stack = new Stack();
                stack.add(new Image(emptyTexture));
                stack.add(new Image(goalTexture));
                stack.add(new Image(switch (GamePreferences.getCharacterSelection()) {
                    case MONIKA -> playerMonikaTexture;
                    case NATSUKI -> playerNatsukiTexture;
                    case SAYORI -> playerSayoriTexture;
                    case YURI -> playerYuriTexture;
                }));
                yield stack;
            }
            case BOX -> {
                Stack stack = new Stack();
                stack.add(new Image(emptyTexture));
                stack.add(new Image(boxTexture));
                yield stack;
            }
            case GOAL -> {
                Stack stack = new Stack();
                stack.add(new Image(emptyTexture));
                stack.add(new Image(goalTexture));
                yield stack;
            }
            case BOX_ON_GOAL -> {
                Stack stack = new Stack();
                stack.add(new Image(emptyTexture));
                stack.add(new Image(goalTexture));
                stack.add(new Image(boxTexture));
                yield stack;
            }
            case WALL -> new Image(wallTexture);
            case EMPTY -> new Image(emptyTexture);
        };
    }
}
