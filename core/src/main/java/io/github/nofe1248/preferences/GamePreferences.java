package io.github.nofe1248.preferences;

public class GamePreferences {
    private static float masterVolume = 1.0f;
    private static float soundVolume = 1.0f;
    private static float musicVolume = 1.0f;

    public static float getMasterVolume() {
        return masterVolume;
    }

    public static void setMasterVolume(float masterVolume) {
        GamePreferences.masterVolume = masterVolume;
    }

    public static float getSoundVolume() {
        return soundVolume;
    }

    public static void setSoundVolume(float soundVolume) {
        GamePreferences.soundVolume = soundVolume;
    }

    public static float getMusicVolume() {
        return musicVolume;
    }

    public static void setMusicVolume(float musicVolume) {
        GamePreferences.musicVolume = musicVolume;
    }
}
