package com.tilldawn.View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Control.ScoreboardController;
import com.tilldawn.Control.SettingsController;

public class ScoreboardView implements Screen {
    private Stage stage;
    private Skin skin;
    private ScoreboardController controller;

    public ScoreboardView(ScoreboardController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        stage = new Stage();
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
