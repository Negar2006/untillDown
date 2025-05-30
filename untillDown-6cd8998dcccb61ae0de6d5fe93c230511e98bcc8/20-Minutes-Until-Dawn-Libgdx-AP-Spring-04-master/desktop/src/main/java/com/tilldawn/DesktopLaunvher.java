package com.tilldawn;

import com.badlogic.gdx.backends.lwjgl3.*;
import com.badlogic.gdx.files.FileHandle;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Until Dawn");
        config.setWindowedMode(1280, 720);
        config.useVsync(true);
        config.setForegroundFPS(60);

        config.setWindowListener(new Lwjgl3WindowAdapter() {
            @Override
            public void filesDropped(String[] files) {
                for (String path : files) {
                    if (path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".jpeg")) {
                        Main.setDroppedFilePath(path); // متد در Main.java
                        break;
                    }
                }
            }
        });

        new Lwjgl3Application(new Main(), config);
    }
}
