package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tilldawn.Control.ScoreboardController;

import java.io.FileReader;
import java.io.IOException;

public class ScoreboardView implements Screen {
    private Stage stage;
    private Skin skin;
    private ScoreboardController controller;
    private Table table;

    public ScoreboardView(ScoreboardController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage();
        this.table = new Table();
    }

    @Override
    public void show() {
        Array<Player> players = loadPlayersFromJson("players.json"); // Example JSON file

        table.setFillParent(true);
        stage.addActor(table);
        table.top().pad(15);

        // Add headers
        table.add("Username").pad(10);
        table.add("Score").pad(10);
        table.add("Kills").pad(10);
        table.add("Avatar").pad(10).row();

        // Add player data to table
        for (Player player : players) {
            table.add(player.name).pad(10);
            table.add(String.valueOf(player.score)).pad(10);
            table.add(String.valueOf(player.kills)).pad(10);

            // Load and display the player image
            Texture texture = new Texture(Gdx.files.internal(player.image));
            Image avatar = new Image(texture);
            table.add(avatar).size(50, 50).pad(10).row();
        }

        Gdx.input.setInputProcessor(stage);
    }

    private Array<Player> loadPlayersFromJson(String filePath) {
        Json json = new Json();
        Array<Player> players = new Array<>();

        try {
            // Read the JSON file
            FileReader reader = new FileReader(filePath);
            Player[] playerArray = json.fromJson(Player[].class, reader);
            for (Player player : playerArray) {
                players.add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return players;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    // Player class representing each player object
    public static class Player {
        public String name;
        public int score;
        public int kills;
        public String image;
    }
}
