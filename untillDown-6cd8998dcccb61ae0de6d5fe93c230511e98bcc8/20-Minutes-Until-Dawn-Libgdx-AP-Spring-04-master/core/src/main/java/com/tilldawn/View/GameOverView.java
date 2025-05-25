package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameOverController;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;

public class GameOverView implements Screen {
    private Stage stage;
    private GameOverController gameOverController;
    private Skin skin;

    // Use default LibGDX font
    private BitmapFont largeFont;
    private BitmapFont mediumFont;

    public GameOverView(GameOverController gameOverController, Skin skin) {
        this.gameOverController = gameOverController;
        this.skin = skin;

        // Use default font provided by LibGDX
        this.largeFont = new BitmapFont(); // Default LibGDX font
        this.mediumFont = new BitmapFont(); // Default LibGDX font
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        // Label style with default font
        Label.LabelStyle titleStyle = new Label.LabelStyle(largeFont, null); // White text
        Label.LabelStyle textStyle = new Label.LabelStyle(mediumFont, null); // Yellow text

        // Center the title and make it large
        Label gameOverLabel = new Label("Game Over!", titleStyle);
        gameOverLabel.setPosition(Gdx.graphics.getWidth() / 2f - gameOverLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 150);
        stage.addActor(gameOverLabel);

        // Username Label
        Label usernameLabel = new Label("Username: " + gameOverController.getUsername(), textStyle);
        usernameLabel.setPosition(Gdx.graphics.getWidth() / 2f - usernameLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 250);
        stage.addActor(usernameLabel);

        // Score Label
        Label scoreLabel = new Label("Score: " + gameOverController.getScore(), textStyle);
        scoreLabel.setPosition(Gdx.graphics.getWidth() / 2f - scoreLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 300);
        stage.addActor(scoreLabel);

        // Kills Label
        Label killsLabel = new Label("Kills: " + gameOverController.getKills(), textStyle);
        killsLabel.setPosition(Gdx.graphics.getWidth() / 2f - killsLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 350);
        stage.addActor(killsLabel);

        // Health Label
        Label healthLabel = new Label("Health: " + gameOverController.getHealth(), textStyle);
        healthLabel.setPosition(Gdx.graphics.getWidth() / 2f - healthLabel.getWidth() / 2f, Gdx.graphics.getHeight() - 400);
        stage.addActor(healthLabel);

        // Main Menu Button with larger size
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = mediumFont; // Use medium font for the button text
        buttonStyle.fontColor = null; // Default font color (white)

        TextButton mainMenuButton = new TextButton("Go to Main Menu", buttonStyle);
        mainMenuButton.setSize(300, 70); // Bigger button size
        mainMenuButton.setPosition(Gdx.graphics.getWidth() / 2f - mainMenuButton.getWidth() / 2f, 150);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                // Navigate to main menu screen
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        stage.addActor(mainMenuButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Set background color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        mediumFont.dispose();
    }
}
