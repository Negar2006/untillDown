package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class GameLoader {
    private static final String SAVE_FILE = "game_save.json";

    public static GameState loadGame() {
        // Create a Json object to deserialize
        Json json = new Json();

        // Check if the save file exists
        FileHandle file = Gdx.files.local(SAVE_FILE);
        if (!file.exists()) {
            System.out.println("No save file found!");
            return null; // No save file, return null
        }

        // Read the file and convert it into GameState object
        String jsonData = file.readString();
        GameState gameState = json.fromJson(GameState.class, jsonData);

        return gameState;
    }
}
