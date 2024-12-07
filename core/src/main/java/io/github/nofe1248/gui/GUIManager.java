package io.github.nofe1248.gui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;

public class GUIManager extends ApplicationAdapter {
    HashMap<GUISelection, BaseGUI> guiMap;
    GUISelection currentGui = GUISelection.LOGIN_PANEL;

    public BaseGUI getCurrentGUI() {
        return this.guiMap.get(this.currentGui);
    }

    public GUISelection getCurrentGUISelection() {
        return this.currentGui;
    }

    @Override
    public void create() {
        guiMap = new HashMap<>();
        guiMap.put(GUISelection.LOAD_GAME, new LoadGame());
        guiMap.put(GUISelection.LOGIN_PANEL, new LoginPanel());
        guiMap.put(GUISelection.MAIN_MENU, new MainMenu());
        guiMap.put(GUISelection.MULTIPLAYER, new Multiplayer());
        guiMap.put(GUISelection.SAVE_GAME, new SaveGame());
        guiMap.put(GUISelection.SETTINGS, new Settings());
        guiMap.put(GUISelection.START_GAME, new StartGame());

        for (BaseGUI gui : guiMap.values()) {
            gui.create();
        }
    }

    @Override
    public void resize(int width, int height) {
        this.getCurrentGUI().resize(width, height);
    }

    @Override
    public void render() {
        this.getCurrentGUI().render();
    }

    @Override
    public void dispose() {
        for (BaseGUI gui : guiMap.values()) {
            gui.dispose();
        }
    }
}
