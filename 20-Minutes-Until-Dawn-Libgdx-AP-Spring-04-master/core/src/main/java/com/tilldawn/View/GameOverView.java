package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameOverController;

public class GameOverView implements Screen {
    private Stage stage;
    private GameOverController gameOverController;
    private Skin skin;

    public GameOverView(GameOverController gameOverController, Skin skin) {
        this.gameOverController = gameOverController;
        this.skin = skin;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Label gameOverLabel = new Label("Game Over! Time's up!", skin);
        gameOverLabel.setPosition(Gdx.graphics.getWidth() / 2f - gameOverLabel.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f);
        stage.addActor(gameOverLabel);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }
}
