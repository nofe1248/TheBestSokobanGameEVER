package io.github.nofe1248.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import io.github.nofe1248.preferences.GamePreferences;

import java.util.HashMap;

public class BackgroundMusicManager {
    HashMap<BackgroundMusicSelection, Music> backgroundMusicMap;
    BackgroundMusicSelection previousPlaying = null;

    public BackgroundMusicManager() {
        this.backgroundMusicMap = new HashMap<>();
        this.backgroundMusicMap.put(BackgroundMusicSelection.MAIN_MENU,
            Gdx.audio.newMusic(Gdx.files.internal("audio/StartMenu.ogg")));
        this.backgroundMusicMap.put(BackgroundMusicSelection.SETTINGS,
            Gdx.audio.newMusic(Gdx.files.internal("audio/Setting.ogg")));
        this.backgroundMusicMap.put(BackgroundMusicSelection.MULTIPLAYER,
            Gdx.audio.newMusic(Gdx.files.internal("audio/Game/Versus.ogg")));
        this.backgroundMusicMap.put(BackgroundMusicSelection.SINGLEPLAYER_MENU,
            Gdx.audio.newMusic(Gdx.files.internal("audio/Game/Challenge/ChallengeMenu.ogg")));
        this.backgroundMusicMap.put(BackgroundMusicSelection.SINGLEPLAYER_MONIKA,
            Gdx.audio.newMusic(Gdx.files.internal("audio/Game/Challenge/ChallengeMonika.ogg")));
        this.backgroundMusicMap.put(BackgroundMusicSelection.SINGLEPLAYER_NATSUKI,
            Gdx.audio.newMusic(Gdx.files.internal("audio/Game/Challenge/ChallengeNatsuki.ogg")));
        this.backgroundMusicMap.put(BackgroundMusicSelection.SINGLEPLAYER_SAYORI,
            Gdx.audio.newMusic(Gdx.files.internal("audio/Game/Challenge/ChallengeSayori.ogg")));
        this.backgroundMusicMap.put(BackgroundMusicSelection.SINGLEPLAYER_YURI,
            Gdx.audio.newMusic(Gdx.files.internal("audio/Game/Challenge/ChallengeYuri.ogg")));
    }

    public void playBackgroundMusic(BackgroundMusicSelection backgroundMusicSelection, boolean seamless_transition) {
        if (backgroundMusicSelection == this.previousPlaying) {
            return;
        }
        Music music = this.backgroundMusicMap.get(backgroundMusicSelection);
        music.setVolume(GamePreferences.getMasterVolume() * GamePreferences.getMusicVolume());
        music.setLooping(true);
        if (this.previousPlaying != null) {
            this.backgroundMusicMap.get(this.previousPlaying).stop();
        }
        if (seamless_transition) {
            if (this.previousPlaying != null) {
                Music previousMusic = this.backgroundMusicMap.get(this.previousPlaying);
                float previousPosition = previousMusic.getPosition();
                this.backgroundMusicMap.get(this.previousPlaying).stop();
                music.setPosition(previousPosition);
            }
        }
        music.play();
        this.previousPlaying = backgroundMusicSelection;
    }

    public void dispose() {
        for (Music music : this.backgroundMusicMap.values()) {
            music.dispose();
        }
    }
}
