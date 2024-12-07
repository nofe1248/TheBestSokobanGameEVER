package io.github.nofe1248.gui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;
import java.util.Stack;

public class GUIManager extends ApplicationAdapter {
    HashMap<GUISelection, BaseGUI> guiMap;
    Stack<GUISelection> guiSelectionStack;
    HashMap<SoundEffectSelection, Sound> soundEffectMap;

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

        this.soundEffectMap = new HashMap<>();
        this.soundEffectMap.put(SoundEffectSelection.BUTTON_CLICK, Gdx.audio.newSound(Gdx.files.internal("audio/Action/Keyboard/click.ogg")));

        for (BaseGUI gui : this.guiMap.values()) {
            gui.create();
        }
    }

    public void playSoundEffect(SoundEffectSelection soundEffectSelection) {
        this.soundEffectMap.get(soundEffectSelection).play(1.0f);
    }

    public void playClick(){
        this.playSoundEffect(SoundEffectSelection.BUTTON_CLICK);
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

        for (Sound sound : soundEffectMap.values()) {
            sound.dispose();
        }
    }
}
