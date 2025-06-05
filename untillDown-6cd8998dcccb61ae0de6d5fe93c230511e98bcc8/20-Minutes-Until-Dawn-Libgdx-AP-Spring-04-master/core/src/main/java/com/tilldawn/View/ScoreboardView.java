package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tilldawn.Control.ScoreboardController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;

public class ScoreboardView implements Screen {

    private Stage stage;
    private final Skin skin;
    private final ScoreboardController controller;

    public ScoreboardView(ScoreboardController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage();
    }

    @Override
    public void show() {
        final Array<User> users = loadUsersFromJson("users/");

        TextButton sortByScoreButton = new TextButton("Sort by Score", skin);
        TextButton sortByKillsButton = new TextButton("Sort by Kills", skin);
        TextButton sortByTimeAliveButton = new TextButton("Sort by Time Alive", skin);
        TextButton sortByUsernameButton = new TextButton("Sort by Username", skin);
        TextButton backToMainMenuButton = new TextButton("Back to Main Menu", skin);

        Table table = new Table();
        table.setFillParent(true);
        table.center().padTop(80);

        float buttonWidth = 600f;
        float buttonHeight = 150f;

        table.add(sortByScoreButton).width(buttonWidth).height(buttonHeight).pad(10).row();
        table.add(sortByKillsButton).width(buttonWidth).height(buttonHeight).pad(10).row();
        table.add(sortByTimeAliveButton).width(buttonWidth).height(buttonHeight).pad(10).row();
        table.add(sortByUsernameButton).width(buttonWidth).height(buttonHeight).pad(10).row();
        table.add(backToMainMenuButton).width(buttonWidth).height(buttonHeight).padTop(40).row();

        stage.clear();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        sortByScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setScreen(new RankingView(controller.sortUsersByScore(users), skin));
            }
        });

        sortByKillsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setScreen(new RankingView(controller.sortUsersByKills(users), skin));
            }
        });

        sortByTimeAliveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setScreen(new RankingView(controller.sortUsersByTimeAlive(users), skin));
            }
        });

        sortByUsernameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setScreen(new RankingView(controller.sortUsersByUsername(users), skin));
            }
        });

        backToMainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.getMain().setScreen(new MainMenuView(new com.tilldawn.Control.MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
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
}
