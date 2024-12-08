package io.github.nofe1248.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SoundEffectManager {
    HashMap<SoundEffectSelection, Sound> soundEffectMap;

    public SoundEffectManager() {
        this.soundEffectMap = new HashMap<>();
        this.soundEffectMap.put(SoundEffectSelection.BUTTON_CLICK, Gdx.audio.newSound(Gdx.files.internal("audio/Action/Keyboard/click.ogg")));
    }

    public void playSoundEffect(SoundEffectSelection soundEffectSelection) {
        this.soundEffectMap.get(soundEffectSelection).play(1.0f);
    }

    public void playClick(){
        this.playSoundEffect(SoundEffectSelection.BUTTON_CLICK);
    }

    public void dispose() {
        for (Sound sound : this.soundEffectMap.values()) {
            sound.dispose();
        }
    }
}