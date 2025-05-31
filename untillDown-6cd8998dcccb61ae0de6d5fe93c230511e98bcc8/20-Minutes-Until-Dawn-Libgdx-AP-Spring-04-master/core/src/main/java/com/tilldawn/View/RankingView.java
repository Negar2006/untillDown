package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tilldawn.Main;
import com.tilldawn.Model.User;
import com.tilldawn.Control.ScoreboardController;
import com.tilldawn.View.ScoreboardView;

import java.util.ArrayList;
import java.util.List;

public class RankingView implements Screen {

    private final Stage stage;
    private final Skin skin;
    private final Array<User> users;
    private final Table table;
    private final List<Texture> avatarTextures = new ArrayList<>();

    public RankingView(Array<User> users, Skin skin) {
        this.users = users;
        this.skin = skin;
        this.stage = new Stage();
        this.table = new Table(skin);
    }

    @Override
    public void show() {
        // Prepare stage and table
        stage.clear();
        Gdx.input.setInputProcessor(stage);

        table.clear();
        table.setFillParent(true);
        table.top().pad(50);

        // Header row
        table.add(new Label("Username", skin)).pad(10);
        table.add(new Label("Score", skin)).pad(10);
        table.add(new Label("Kills", skin)).pad(10);
        table.add(new Label("Time Alive", skin)).pad(10);
        table.add(new Label("Avatar", skin)).pad(10).row();

        // Populate rows
        int rankIndex = 0;
        for (User user : users) {
            Label nameLabel = new Label(user.getUsername(), skin);
            Label scoreLabel = new Label(String.valueOf(user.getScore()), skin);
            Label killsLabel = new Label(String.valueOf(user.getNumberOfKillingMonster()), skin);
            Label timeLabel = new Label(formatTime(user.getTimeAliveMillis()), skin);

            Texture avatarTexture = new Texture(Gdx.files.internal(user.getSelectedCharacter()));
            avatarTextures.add(avatarTexture);
            Image avatar = new Image(avatarTexture);

            table.add(nameLabel).pad(10);
            table.add(scoreLabel).pad(10);
            table.add(killsLabel).pad(10);
            table.add(timeLabel).pad(10);
            table.add(avatar).size(50, 50).pad(10).row();

            // Highlight top 3
            if (rankIndex == 0) {
                nameLabel.setColor(Color.MAGENTA);
            } else if (rankIndex == 1) {
                nameLabel.setColor(Color.PINK);
            } else if (rankIndex == 2) {
                nameLabel.setColor(Color.PURPLE);
            }
            // Highlight current user
            if (user.getUsername().equalsIgnoreCase(Main.currentUser.getUsername())) {
                nameLabel.setColor(Color.GREEN);
                scoreLabel.setColor(Color.GREEN);
                killsLabel.setColor(Color.GREEN);
                timeLabel.setColor(Color.GREEN);
            }
            rankIndex++;
        }

        // Back button
        TextButton backButton = new TextButton("Back to Scoreboard", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.postRunnable(() -> Main.getMain()
                    .setScreen(new ScoreboardView(new ScoreboardController(), skin)));
            }
        });

        Table backTable = new Table();
        backTable.top().padTop(10);
        backTable.setFillParent(true);
        backTable.add(backButton).expand().left().pad(20);

        stage.addActor(table);
        stage.addActor(backTable);
    }

    private String formatTime(long timeMillis) {
        long seconds = timeMillis / 1000;
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
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
        for (Texture texture : avatarTextures) {
            texture.dispose();
        }
        stage.dispose();
    }
}
