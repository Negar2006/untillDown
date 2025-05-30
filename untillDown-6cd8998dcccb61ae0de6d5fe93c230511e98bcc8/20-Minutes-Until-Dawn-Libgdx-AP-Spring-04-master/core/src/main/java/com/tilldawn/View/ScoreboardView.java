package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tilldawn.Control.ScoreboardController;
import com.tilldawn.Model.User;

public class ScoreboardView implements Screen {

    private Stage stage;
    private final Skin skin;
    private final ScoreboardController controller;
    private Table table;
    private TextButton sortByScoreButton;
    private TextButton sortByKillsButton;
    private TextButton sortByTimeAliveButton;
    private TextButton sortByUsernameButton;

    public ScoreboardView(ScoreboardController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage();
        this.table = new Table();
        this.table.setSkin(skin);  // Set the Skin for the Table here
    }

    @Override
    public void show() {
        final Array<User> users = loadUsersFromJson("users/");

        sortByScoreButton = new TextButton("Sort by Score", skin);
        sortByKillsButton = new TextButton("Sort by Kills", skin);
        sortByTimeAliveButton = new TextButton("Sort by Time Alive", skin);
        sortByUsernameButton = new TextButton("Sort by Username", skin);

        Table buttonTable = new Table();
        buttonTable.top().padTop(10).padLeft(10);
        buttonTable.add(sortByScoreButton).pad(10).colspan(5).fillX().row();
        buttonTable.add(sortByKillsButton).pad(10).colspan(5).fillX().row();
        buttonTable.add(sortByTimeAliveButton).pad(10).colspan(5).fillX().row();
        buttonTable.add(sortByUsernameButton).pad(10).colspan(5).fillX().row();

        stage.addActor(buttonTable);

        sortByScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<User> sortedUsers = controller.sortUsersByScore(users);
                updateTable(sortedUsers);
            }
        });

        sortByKillsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<User> sortedUsers = controller.sortUsersByKills(users);
                updateTable(sortedUsers);
            }
        });

        sortByTimeAliveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<User> sortedUsers = controller.sortUsersByTimeAlive(users);
                updateTable(sortedUsers);
            }
        });

        sortByUsernameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Array<User> sortedUsers = controller.sortUsersByUsername(users);
                updateTable(sortedUsers);
            }
        });

        table.setFillParent(true);
        stage.addActor(table);
        table.top().pad(60);

        updateTable(users);

        Gdx.input.setInputProcessor(stage);
    }


    private void updateTable(Array<User> users) {
        table.clear();

        table.add("Username").pad(10);
        table.add("Score").pad(10);
        table.add("Kills").pad(10);
        table.add("Time Alive").pad(10);
        table.add("Avatar").pad(10).row();

        for (int i = 0; i < users.size; i++) {
            User user = users.get(i);
            table.add(user.getUsername()).pad(10);
            table.add(String.valueOf(user.getScore())).pad(10);
            table.add(String.valueOf(user.getNumberOfKillingMonster())).pad(10);
            table.add(formatTime(user.getTimeAliveMillis())).pad(10);

            Texture avatarTexture = new Texture(user.getSelectedCharacter());
            Image avatar = new Image(avatarTexture);
            table.add(avatar).size(50, 50).pad(10).row();
        }
    }

    private String formatTime(long timeMillis) {
        long seconds = timeMillis / 1000;
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    private Array<User> loadUsersFromJson(String folderPath) {
        Json json = new Json();
        Array<User> users = new Array<>();

        FileHandle[] files = Gdx.files.local(folderPath).list();
        for (FileHandle file : files) {
            if (file.extension().equals("json")) {
                try {
                    User user = json.fromJson(User.class, file.reader());
                    users.add(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
