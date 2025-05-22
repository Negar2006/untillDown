package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.PreGameMenuController;

public class PreGameMenuView implements Screen {

    private Stage stage;
    private final Label gameTitle;
    private final TextButton playButton;
    private final Table mainTable;
    private final PreGameMenuController controller;
    private final Skin skin;

    private final Texture[] characterTextures;
    private final Image[] characterImages;
    private int selectedCharacterIndex = -1;

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.gameTitle = new Label("Select Your Hero", skin);
        this.playButton = new TextButton("Play", skin);
        this.mainTable = new Table();
        controller.setView(this);

        characterTextures = new Texture[]{
            new Texture(Gdx.files.internal("SHANA.png")),
            new Texture(Gdx.files.internal("DASHER.png")),
            new Texture(Gdx.files.internal("DIAMOND.png")),
            new Texture(Gdx.files.internal("LILITH.png")),
            new Texture(Gdx.files.internal("SCARLET.png"))
        };

        characterImages = new Image[characterTextures.length];

        playButton.setDisabled(true);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        mainTable.setFillParent(true);
        mainTable.center().pad(20);

        mainTable.add(gameTitle).colspan(characterTextures.length).padBottom(20);
        mainTable.row();

        Table imageTable = new Table();

        for (int i = 0; i < characterTextures.length; i++) {
            final int index = i;
            Image img = new Image(characterTextures[i]);
            img.setSize(120, 120);
            img.setScaling(Scaling.fit);

            img.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedCharacterIndex = index ;
                    playButton.setDisabled(false);
                    controller.onCharacterSelected(selectedCharacterIndex);
                }
            });

            characterImages[i] = img;
            imageTable.add(img).pad(10);
        }

        mainTable.add(imageTable).colspan(characterTextures.length);
        mainTable.row();

        mainTable.add(playButton).width(200).padTop(30);

        stage.addActor(mainTable);

        controller.handlePreGameMenuButtons();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();

        if (selectedCharacterIndex >= 0) {
            Actor selectedActor = characterImages[selectedCharacterIndex];

            Vector2 coords = selectedActor.localToStageCoordinates(new Vector2(0, 0));

            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(coords.x, coords.y, selectedActor.getWidth(), selectedActor.getHeight());
            shapeRenderer.end();
            shapeRenderer.dispose();
        }
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
        for (Texture texture : characterTextures) {
            texture.dispose();
        }
    }

    public TextButton getPlayButton() {
        return playButton;
    }

    public int getSelectedCharacterIndex() {
        return selectedCharacterIndex;
    }
}
