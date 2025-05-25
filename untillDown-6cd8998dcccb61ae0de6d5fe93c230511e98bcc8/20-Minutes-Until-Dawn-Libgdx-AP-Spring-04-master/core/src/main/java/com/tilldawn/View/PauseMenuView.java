package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;

public class PauseMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private GameController controller;
    private TextButton resumeButton;
    private TextButton cheatCodesButton;
    private TextButton abilitiesButton;
    private TextButton giveUpButton;
    private TextButton settingsButton;

    public PauseMenuView(GameController controller) {
        this.controller = controller;
        this.skin = GameAssetManager.getGameAssetManager().getSkin();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Resume Button
        resumeButton = new TextButton("Resume", skin);
        resumeButton.setPosition(200, 300);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.resumeGame();
            }
        });
        stage.addActor(resumeButton);

        // Cheat Codes Button
        cheatCodesButton = new TextButton("Cheat Codes", skin);
        cheatCodesButton.setPosition(200, 240);
        cheatCodesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Show cheat codes here
            }
        });
        stage.addActor(cheatCodesButton);

        // Abilities Button
        abilitiesButton = new TextButton("Abilities", skin);
        abilitiesButton.setPosition(200, 180);
        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Show abilities here
            }
        });
        stage.addActor(abilitiesButton);

        // Give Up Button
        giveUpButton = new TextButton("Give Up", skin);
        giveUpButton.setPosition(200, 120);
        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.endGame(); // End the game
            }
        });
        stage.addActor(giveUpButton);

        // Settings Button
        settingsButton = new TextButton("Settings", skin);
        settingsButton.setPosition(200, 60);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToSettingsMenu(); // Open settings menu
            }
        });
        stage.addActor(settingsButton);
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        // Clean up resources
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
