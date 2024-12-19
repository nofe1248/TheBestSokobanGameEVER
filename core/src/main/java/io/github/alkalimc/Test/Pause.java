package io.github.alkalimc.Test;

public class Pause {
    public static void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
