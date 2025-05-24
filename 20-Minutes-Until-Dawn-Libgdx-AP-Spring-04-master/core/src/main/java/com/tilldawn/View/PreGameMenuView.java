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
    private final Label timeLabel;
    private final ButtonGroup<TextButton> timeButtonGroup;
    private final Table timeTable;
    private final Label weaponLabel;
    private final ButtonGroup<TextButton> weaponButtonGroup;
    private final Table weaponTable;

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

        timeLabel = new Label("Select Game Duration (minutes):", skin);

        timeButtonGroup = new ButtonGroup<>();

        timeTable = new Table();

        int[] times = {2, 5, 10, 20};
        for (int time : times) {
            TextButton btn = new TextButton(time + " min", skin, "toggle");
            btn.setUserObject(time);
            timeButtonGroup.add(btn);
            timeTable.add(btn).pad(5);
        }
        timeButtonGroup.setMaxCheckCount(1);
        timeButtonGroup.setMinCheckCount(1);
        timeButtonGroup.setChecked("5 min");

        weaponLabel = new Label("Select Your Weapon:", skin);

        weaponButtonGroup = new ButtonGroup<>();

        weaponTable = new Table();

        String[] weapons = {"Sword", "Bow", "Magic"};
        for (String weapon : weapons) {
            TextButton btn = new TextButton(weapon, skin, "toggle");
            btn.setUserObject(weapon);
            weaponButtonGroup.add(btn);
            weaponTable.add(btn).pad(5);
        }
        weaponButtonGroup.setMaxCheckCount(1);
        weaponButtonGroup.setMinCheckCount(1);
        weaponButtonGroup.setChecked("Sword");

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

        float imageWidth = 400f;
        float imageHeight = 400f;

        for (int i = 0; i < characterTextures.length; i++) {
            final int index = i;
            Image img = new Image(characterTextures[i]);
            img.setScaling(Scaling.fit); // حفظ نسبت

            img.setSize(imageWidth, imageHeight); // سایز بزرگ‌تر
            img.setTouchable(Touchable.enabled);

            img.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedCharacterIndex = index;
                    playButton.setDisabled(false);
                    controller.onCharacterSelected(selectedCharacterIndex);
                }
            });

            characterImages[i] = img;
            imageTable.add(img).width(imageWidth).height(imageHeight).pad(15); // کنترل دقیق سایز
        }

        mainTable.add(imageTable).colspan(characterTextures.length);
        mainTable.row();

        mainTable.add(playButton)
            .colspan(characterTextures.length)
            .center()
            .padTop(30);

        mainTable.row();
        mainTable.add(timeLabel).colspan(characterTextures.length).padTop(30);
        mainTable.row();
        mainTable.add(timeTable).colspan(characterTextures.length);

        mainTable.row();
        mainTable.add(weaponLabel).colspan(characterTextures.length).padTop(30);
        mainTable.row();
        mainTable.add(weaponTable).colspan(characterTextures.length);


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

    public int getSelectedGameDuration() {
        if (timeButtonGroup.getChecked() != null) {
            return (int) timeButtonGroup.getChecked().getUserObject();
        }
        return 5;
    }

    public String getSelectedWeapon() {
        if (weaponButtonGroup.getChecked() != null) {
            return (String) weaponButtonGroup.getChecked().getUserObject();
        }
        return "Sword";
    }

}
