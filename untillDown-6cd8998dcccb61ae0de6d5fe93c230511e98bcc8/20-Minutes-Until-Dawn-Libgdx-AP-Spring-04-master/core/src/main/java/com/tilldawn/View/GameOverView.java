package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Control.GameOverController;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.RegisterMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameSaver;

public class GameOverView implements Screen {
    private Stage stage;
    private GameOverController gameOverController;
    private Skin skin;
    private BitmapFont largeFont;
    private Texture backgroundTexture;
    private SpriteBatch batch;
    private GameController gameController;

    public GameOverView(GameOverController gameOverController, Skin skin) {
        this.gameOverController = gameOverController;
        this.skin = skin;
        this.largeFont = new BitmapFont(); // default font
        this.largeFont.getData().setScale(3);
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("game_over_background.png"));
        batch = new SpriteBatch();

        BitmapFont smallFont = new BitmapFont();
        smallFont.getData().setScale(1.5f); // smaller than title
        Label.LabelStyle smallLabelStyle = new Label.LabelStyle(smallFont, null);

        // Game Over title
        Label gameOverLabel = new Label("GAME OVER!", skin.get("title", Label.LabelStyle.class));
        gameOverLabel.setPosition(
            Gdx.graphics.getWidth() / 2f - gameOverLabel.getWidth() / 2f,
            Gdx.graphics.getHeight() - 200
        );
        stage.addActor(gameOverLabel);

        // Username
        Label usernameLabel = new Label("Username: " + gameOverController.getUsername(), smallLabelStyle);
        usernameLabel.setPosition(Gdx.graphics.getWidth() / 2f - usernameLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 320);
        stage.addActor(usernameLabel);

        // Score
        Label scoreLabel = new Label("Score: " + gameOverController.getScore(), smallLabelStyle);
        scoreLabel.setPosition(Gdx.graphics.getWidth() / 2f - scoreLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 400);
        stage.addActor(scoreLabel);

        // Kills
        Label killsLabel = new Label("Kills: " + gameOverController.getKills(), smallLabelStyle);
        killsLabel.setPosition(Gdx.graphics.getWidth() / 2f - killsLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 480);
        stage.addActor(killsLabel);

        // Health
        Label healthLabel = new Label("Health: " + gameOverController.getHealth(), smallLabelStyle);
        healthLabel.setPosition(Gdx.graphics.getWidth() / 2f - healthLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 560);
        stage.addActor(healthLabel);

        // Use button style from skin (defined in your skin.json or skin file)
        TextButton mainMenuButton = new TextButton("Go Back to Main Menu", skin);
        mainMenuButton.getLabel().getStyle().fontColor = Color.RED;
        mainMenuButton.getLabel().setFontScale(0.6f);
        mainMenuButton.setColor(1, 0, 0, 1);
        mainMenuButton.setSize(400, 100);
        mainMenuButton.setPosition(Gdx.graphics.getWidth() / 2f - 200, 100);

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                if (Main.currentUser == null) {
                    Main.getMain().setScreen(new RegisterMenuViews(
                        new RegisterMenuController(),
                        GameAssetManager.getGameAssetManager().getSkin()
                    ));
                } else {
                    Main.getMain().setScreen(new MainMenuView(
                        new MainMenuController(),
                        GameAssetManager.getGameAssetManager().getSkin()
                    ));
                }
            }
        });
        stage.addActor(mainMenuButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        largeFont.dispose();
        backgroundTexture.dispose();
        batch.dispose();
    }
}
