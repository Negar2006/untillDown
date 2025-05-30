package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tilldawn.Model.User;
import com.tilldawn.Control.ScoreboardController;

public class RankingView implements Screen {

    private final Stage stage;
    private final Skin skin;
    private final Array<User> users;
    private final Table table;

    public RankingView(Array<User> users, Skin skin) {
        this.users = users;
        this.skin = skin;
        this.stage = new Stage();
        this.table = new Table(skin);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.top().pad(50);

        // سرستون‌ها
        table.add("Username").pad(10);
        table.add("Score").pad(10);
        table.add("Kills").pad(10);
        table.add("Time Alive").pad(10);
        table.add("Avatar").pad(10).row();

        for (User user : users) {
            table.add(user.getUsername()).pad(10);
            table.add(String.valueOf(user.getScore())).pad(10);
            table.add(String.valueOf(user.getNumberOfKillingMonster())).pad(10);
            table.add(formatTime(user.getTimeAliveMillis())).pad(10);
            Texture avatarTexture = new Texture(user.getSelectedCharacter());
            Image avatar = new Image(avatarTexture);
            table.add(avatar).size(50, 50).pad(10).row();
        }

        TextButton backButton = new TextButton("Back to Scoreboard", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.postRunnable(() -> {
                    com.tilldawn.Main.getMain().setScreen(new ScoreboardView(new ScoreboardController(), skin));
                });
            }
        });

        Table backTable = new Table();
        backTable.top().padTop(10);
        backTable.setFillParent(true);
        backTable.add(backButton).expand().left().pad(20);

        stage.clear();
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

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
