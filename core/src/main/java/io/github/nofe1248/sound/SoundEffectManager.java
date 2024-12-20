package io.github.nofe1248.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import io.github.nofe1248.preferences.GamePreferences;

import java.util.HashMap;

public class SoundEffectManager {
    HashMap<SoundEffectSelection, Sound> soundEffectMap;

    public SoundEffectManager() {
        this.soundEffectMap = new HashMap<>();
        this.soundEffectMap.put(SoundEffectSelection.BUTTON_CLICK, Gdx.audio.newSound(Gdx.files.internal("audio/Action/Keyboard/click.ogg")));
        this.soundEffectMap.put(SoundEffectSelection.PLAYER_MOVE, Gdx.audio.newSound(Gdx.files.internal("audio/Action/Keyboard/move.wav")));
        this.soundEffectMap.put(SoundEffectSelection.PLAYER_MOVE_FAIL, Gdx.audio.newSound(Gdx.files.internal("audio/Action/Keyboard/move_failed.wav")));
    }

    public void playSoundEffect(SoundEffectSelection soundEffectSelection) {
        this.soundEffectMap.get(soundEffectSelection).play(GamePreferences.getMasterVolume() * GamePreferences.getSoundVolume());
    }

    public void playClick(){
        this.playSoundEffect(SoundEffectSelection.BUTTON_CLICK);
    }

    public void playPlayerMove(){
        this.playSoundEffect(SoundEffectSelection.PLAYER_MOVE);
    }

    public void playPlayerMoveFail(){
        this.playSoundEffect(SoundEffectSelection.PLAYER_MOVE_FAIL);
    }

    public void dispose() {
        for (Sound sound : this.soundEffectMap.values()) {
            sound.dispose();
        }
    }
}
