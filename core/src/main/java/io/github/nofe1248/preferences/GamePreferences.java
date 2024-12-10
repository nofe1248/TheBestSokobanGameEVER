package io.github.nofe1248.preferences;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

public class GamePreferences {
    private static float masterVolume = 1.0f;
    private static float soundVolume = 1.0f;
    private static float musicVolume = 1.0f;
    private static CharacterSelection characterSelection = CharacterSelection.MONIKA;

    static {
        File file = new File("./config.json");
        Path configPath = Path.of("./config.json");
        if (file.exists()) {
            try {
                String jsonString = Files.readString(configPath);
                JSONObject obj = JSON.parseObject(jsonString);
                if (obj.containsKey("masterVolume")) {
                    masterVolume = obj.getFloatValue("masterVolume");
                    if (masterVolume < 0 || masterVolume > 1) {
                        masterVolume = 1.0f;
                    }
                }
                if (obj.containsKey("soundVolume")) {
                    soundVolume = obj.getFloatValue("soundVolume");
                    if (soundVolume < 0 || soundVolume > 1) {
                        soundVolume = 1.0f;
                    }
                }
                if (obj.containsKey("musicVolume")) {
                    musicVolume = obj.getFloatValue("musicVolume");
                    if (musicVolume < 0 || musicVolume > 1) {
                        musicVolume = 1.0f;
                    }
                }
                if (obj.containsKey("characterSelection")) {
                    characterSelection = CharacterSelection.valueOf(obj.getString("characterSelection"));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            save();
        }
    }

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

    public static void save() {
        JSONObject obj = new JSONObject();
        obj.put("masterVolume", masterVolume);
        obj.put("soundVolume", soundVolume);
        obj.put("musicVolume", musicVolume);
        obj.put("characterSelection", characterSelection.name());
        try {
            Files.writeString(Path.of("./config.json"), obj.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
