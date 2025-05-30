package com.tilldawn.View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tilldawn.Control.ContinueController;

public class ContinueView implements Screen {
    private ContinueController controller;
    private Stage stage;
    private Skin skin;

    public ContinueView(ContinueController controller, Skin skin) {
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
