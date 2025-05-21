package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.PreGameMenuController;
import com.tilldawn.Main;

public class PreGameMenuView implements Screen {

    private Stage stage;
    private final Label gameTitle;
    private final TextButton playButton;
    private final SelectBox<String> selectHero;
    private final Table table;
    private PreGameMenuController controller;

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.controller = controller;
        this.gameTitle = new Label("Pregame Menu", skin);
        this.selectHero = new SelectBox<>(skin);
        this.playButton = new TextButton("Play", skin);
        this.table = new Table();
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Array<String> heroes = new Array<>();
        heroes.add("Hero 1");
        heroes.add("Hero 2");
        heroes.add("Hero 3");

        selectHero.setItems(heroes);

        table.setFillParent(true);
        table.center();
        table.pad(20);

        table.add(gameTitle).pad(10);
        table.row();
        table.add(selectHero).width(200).pad(10);
        table.row();
        table.add(playButton).width(200).pad(10);

        stage.addActor(table);

        controller.handlePreGameMenuButtons();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }

    // getter برای دکمه Play برای استفاده کنترلر
    public TextButton getPlayButton() {
        return playButton;
    }

    // getter برای انتخاب هیرو
    public String getSelectedHero() {
        return selectHero.getSelected();
    }
}
