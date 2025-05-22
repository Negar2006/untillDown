package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.PreGameMenuController;
import com.tilldawn.Model.GameAssetManager;

public class PreGameMenuView implements Screen {

    private Stage stage;
    private final Label gameTitle;
    private final TextButton playButton;
    private final Table mainTable;
    private final PreGameMenuController controller;
    private final Skin skin;

    private Animation<?>[] characterAnimations;
    private int selectedCharacterIndex = -1; // هیچکدوم انتخاب نشده

    private float stateTime = 0f;

    public PreGameMenuView(PreGameMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.gameTitle = new Label("Select Your Hero", skin);
        this.playButton = new TextButton("Play", skin);
        this.mainTable = new Table();
        controller.setView(this);

        GameAssetManager assetManager = GameAssetManager.getGameAssetManager();
        characterAnimations = new Animation[]{
            assetManager.getCharacter1_idle_frames(),
            assetManager.getCharacter2_idle_frames(),
            assetManager.getCharacter3_idle_frames(),
            assetManager.getCharacter4_idle_frames(),
            assetManager.getCharacter5_idle_frames()
        };

        playButton.setDisabled(true);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        mainTable.setFillParent(true);
        mainTable.center().pad(20);

        mainTable.add(gameTitle).colspan(characterAnimations.length).padBottom(20);
        mainTable.row();

        Table animationTable = new Table();

        for (int i = 0; i < characterAnimations.length; i++) {
            final int index = i;
            Image animImage = new Image() {
                @Override
                public void draw(Batch batch, float parentAlpha) {
                    stateTime += Gdx.graphics.getDeltaTime();
                    Animation<?> anim = characterAnimations[index];
                    TextureRegion frame = (TextureRegion) anim.getKeyFrame(stateTime, true);
                    batch.setColor(getColor());
                    batch.draw(frame, getX(), getY(), getWidth(), getHeight());
                }
            };
            animImage.setSize(120, 120);
            animImage.setScaling(Scaling.fit);

            // Box or border for selection
            animImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedCharacterIndex = index;
                    playButton.setDisabled(false);
                    controller.onCharacterSelected(index);
                }
            });

            animationTable.add(animImage).pad(10);
        }

        mainTable.add(animationTable).colspan(characterAnimations.length);
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
            Batch batch = stage.getBatch();
            batch.begin();

            Table animationTable = (Table) mainTable.getChildren().get(1);
            SnapshotArray<Actor> animationActors = animationTable.getChildren();
            Actor selectedActor = animationActors.get(selectedCharacterIndex);

            float x = selectedActor.getX() + animationTable.getX();
            float y = selectedActor.getY() + animationTable.getY();
            float width = selectedActor.getWidth();
            float height = selectedActor.getHeight();

            batch.end();

            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.rect(x, y, width, height);
            shapeRenderer.end();
            shapeRenderer.dispose();

            batch.begin();
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
    }

    public TextButton getPlayButton() {
        return playButton;
    }

    public int getSelectedCharacterIndex() {
        return selectedCharacterIndex;
    }
}
