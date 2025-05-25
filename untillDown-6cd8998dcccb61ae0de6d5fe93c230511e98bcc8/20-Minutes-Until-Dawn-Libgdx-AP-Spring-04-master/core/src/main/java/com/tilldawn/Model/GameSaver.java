package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class GameSaver {
    private static final String SAVE_FILE = "game_save.json"; // File name for the save

    public static void saveGame(Player player, ArrayList<Monster> monsters) {
        // Create a GameState object
        GameState gameState = new GameState(player, monsters);

        // Create a Json object to serialize
        Json json = new Json();

        // Convert the game state to JSON
        String jsonData = json.toJson(gameState);

        // Write to a file
        FileHandle file = Gdx.files.local(SAVE_FILE);
        file.writeString(jsonData, false);
    }
}
