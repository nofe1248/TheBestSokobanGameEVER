package io.github.nofe1248.gui;

import com.badlogic.gdx.ApplicationAdapter;
import io.github.nofe1248.sound.SoundEffectManager;

import java.util.HashMap;
import java.util.Stack;

public class GUIManager extends ApplicationAdapter {
    HashMap<GUISelection, BaseGUI> guiMap;
    Stack<GUISelection> guiSelectionStack;

    public SoundEffectManager getSoundEffectManager() {
        return soundEffectManager;
    }

    SoundEffectManager soundEffectManager;

    public BaseGUI getCurrentGUI() {
        return this.guiMap.get(this.guiSelectionStack.getLast());
    }

    public GUISelection getCurrentGUISelection() {
        return this.guiSelectionStack.getLast();
    }

    public void setCurrentGUI(GUISelection gui) {
        this.guiSelectionStack.push(gui);
        if (this.guiSelectionStack.size() > 256) {
            this.guiSelectionStack.removeFirst();
        }
    }

    public void backToPreviousGUI() {
        if (this.guiSelectionStack.size() > 1) {
            this.guiSelectionStack.removeLast();
        }
    }

    @Override
    public void create() {
        this.guiSelectionStack = new Stack<>();
        this.guiSelectionStack.push(GUISelection.LOGIN_PANEL);

        this.guiMap = new HashMap<>();
        this.guiMap.put(GUISelection.LOAD_GAME, new LoadGame());
        this.guiMap.put(GUISelection.LOGIN_PANEL, new LoginPanel());
        this.guiMap.put(GUISelection.MAIN_MENU, new MainMenu());
        this.guiMap.put(GUISelection.MULTIPLAYER, new Multiplayer());
        this.guiMap.put(GUISelection.SAVE_GAME, new SaveGame());
        this.guiMap.put(GUISelection.SETTINGS, new Settings());
        this.guiMap.put(GUISelection.START_GAME, new StartGame());

        for (BaseGUI gui : this.guiMap.values()) {
            gui.create();
        }

        this.soundEffectManager = new SoundEffectManager();
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

        this.soundEffectManager.dispose();
    }
}
