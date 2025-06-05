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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.GL20;
import com.tilldawn.Model.enums.Ability;

import java.util.ArrayList;


public class PauseMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private GameController controller;
    private TextButton resumeButton;
    private TextButton cheatCodesButton;
    private TextButton abilitiesButton;
    private TextButton giveUpButton;
    private TextButton settingsButton;
    private TextButton saveGameButton;
    private ShaderProgram grayscaleShader;
    private boolean grayscaleEnabled = false;
    private GameView gameView;

    public PauseMenuView(GameController controller, GameView gameView) {
        this.controller = controller;
        this.gameView = gameView;
        this.skin = GameAssetManager.getGameAssetManager().getSkin();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        String vertexShader = Gdx.files.internal("shaders/grayscale.glsl").readString();
        String fragmentShader = Gdx.files.internal("shaders/grayscale.frag").readString();
        grayscaleShader = new ShaderProgram(vertexShader, fragmentShader);
        if (!grayscaleShader.isCompiled()) {
            Gdx.app.error("Shader", "Shader compile error: " + grayscaleShader.getLog());
        }

        final float buttonWidth = 300;
        final float buttonHeight = 60;
        final float centerX = stage.getWidth() / 2 - buttonWidth / 2;
        float startY = stage.getHeight() - 100;

        resumeButton = new TextButton("Resume", skin);
        resumeButton.setSize(buttonWidth, buttonHeight);
        resumeButton.setPosition(centerX, startY);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.resumeGame();
            }
        });
        stage.addActor(resumeButton);

        cheatCodesButton = new TextButton("Cheat Codes", skin);
        cheatCodesButton.setSize(buttonWidth, buttonHeight);
        cheatCodesButton.setPosition(centerX, startY - (buttonHeight + 10));
        cheatCodesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StringBuilder cheatText = new StringBuilder("Cheat Keys:\n");
                cheatText.append("- N: Return to Main Menu\n");
                cheatText.append("- K: Kill current monster\n");
                cheatText.append("- E: Kill EyeBat\n");
                cheatText.append("- T: Add 60 seconds to timer\n");
                cheatText.append("- B: Gain EXP to level up\n");

                Label cheatLabel = new Label(cheatText.toString(), skin);
                cheatLabel.setPosition(stage.getWidth() / 2f - 200, stage.getHeight() / 2f - 120);
                stage.addActor(cheatLabel);

                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        cheatLabel.remove();
                        controller.resumeGame();
                    }
                }, 5);
            }
        });

        stage.addActor(cheatCodesButton);

        abilitiesButton = new TextButton("Abilities", skin);
        abilitiesButton.setSize(buttonWidth, buttonHeight);
        abilitiesButton.setPosition(centerX, startY - 2 * (buttonHeight + 10));
        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ArrayList<Ability> abilities = controller.getPlayerController().getPlayer().getGainAbilities();

                StringBuilder abilitiesText = new StringBuilder("Abilities:\n");
                for (Ability ability : abilities) {
                    abilitiesText.append("- ").append(ability.name()).append("\n");
                }

                Label abilityLabel = new Label(abilitiesText.toString(), skin);
                abilityLabel.setPosition(stage.getWidth() / 2f - 150, stage.getHeight() / 2f);
                stage.addActor(abilityLabel);

                Gdx.app.postRunnable(() -> {
                    Gdx.app.log("Abilities", "Returning to game in 5 seconds...");
                    com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                        @Override
                        public void run() {
                            controller.resumeGame();
                        }
                    }, 5);
                });
            }
        });

        stage.addActor(abilitiesButton);

        giveUpButton = new TextButton("Give Up", skin);
        giveUpButton.setSize(buttonWidth, buttonHeight);
        giveUpButton.setPosition(centerX, startY - 3 * (buttonHeight + 10));
        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.endGame();
            }
        });
        stage.addActor(giveUpButton);

        saveGameButton = new TextButton("Save/Leave", skin);
        saveGameButton.setSize(buttonWidth, buttonHeight);
        saveGameButton.setPosition(centerX, startY - 4 * (buttonHeight + 10));
        saveGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.saveGameState();
                controller.endGame();
            }
        });
        stage.addActor(saveGameButton);

        settingsButton = new TextButton("Color", skin);
        settingsButton.setSize(buttonWidth, buttonHeight);
        settingsButton.setPosition(centerX, startY - 5 * (buttonHeight + 10));
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameView.setGrayscale(true);
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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
