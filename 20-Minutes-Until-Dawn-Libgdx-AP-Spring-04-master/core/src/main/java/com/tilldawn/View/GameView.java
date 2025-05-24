package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.DamagePopup;
import com.tilldawn.Model.GameAssetManager;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends InputAdapter implements Screen {
    private Stage stage;
    private Skin skin;
    private GameController controller;
    private Array<DamagePopup> damagePopups = new Array<>();
    private ImageButton settingsButton;
    private Animation<TextureRegion> treeAnimation;
    private Animation<TextureRegion> playerAnimation;
    private float stateTime;
    private GameAssetManager assetManager;
    private Label timerLabel;
    private Label healthLabel;
    private Label killsLabel;
    private Label scoreLabel;

    public GameView(GameController controller, Skin skin, Texture playerTexture, String name) {
        this.skin = skin;
        this.controller = controller;
        controller.setView(this, playerTexture, name);
        this.assetManager = GameAssetManager.getGameAssetManager();
        this.stateTime = 0f;

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        timerLabel = new Label("Time Left: 10:00", skin);
        timerLabel.setPosition(10, Gdx.graphics.getHeight() - 30);
        stage.addActor(timerLabel);

        healthLabel = new Label("HP: " + controller.getPlayerHealth(), skin);
        healthLabel.setPosition(10, Gdx.graphics.getHeight() - 60);
        stage.addActor(healthLabel);

        killsLabel = new Label("Kills: " + controller.getKillCount(), skin);
        killsLabel.setPosition(10, Gdx.graphics.getHeight() - 90);
        stage.addActor(killsLabel);

        scoreLabel = new Label("Score: " + controller.getPlayerController().getPlayer().getScore(), skin);
        scoreLabel.setPosition(10, Gdx.graphics.getHeight() - 120);
        stage.addActor(scoreLabel);


        settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new com.badlogic.gdx.graphics.Texture(Gdx.files.internal("gear.png")))));
        settingsButton.setPosition(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 60);
        settingsButton.setSize(50, 50);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToSettingsMenu();
            }
        });
        stage.addActor(settingsButton);


        Animation<com.badlogic.gdx.graphics.Texture> rawPlayerAnim = assetManager.getCharacter1_idle_animation();
        Array<TextureRegion> playerFrames = new Array<>();
        for (com.badlogic.gdx.graphics.Texture t : rawPlayerAnim.getKeyFrames()) {
            playerFrames.add(new TextureRegion(t));
        }
        playerAnimation = new Animation<>(rawPlayerAnim.getFrameDuration(), playerFrames, Animation.PlayMode.LOOP);

        InputMultiplexer multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stateTime += delta;
        Main.getBatch().begin();

        long elapsedMillis = System.currentTimeMillis() - controller.getStartTimeMillis();
        long remainingMillis = Math.max(0, controller.getGameDurationMillis() - elapsedMillis);
        long minutes = remainingMillis / 60000;
        long seconds = (remainingMillis % 60000) / 1000;
        timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));

        healthLabel.setText("HP: " + controller.getPlayerHealth());
        killsLabel.setText("Kills: " + controller.getKillCount());
        scoreLabel.setText("Score: " + controller.getPlayerController().getPlayer().getScore());


        controller.updateGame();

        for (int i = damagePopups.size - 1; i >= 0; i--) {
            DamagePopup popup = damagePopups.get(i);
            popup.update(delta);
            popup.draw(Main.getBatch());
            if (popup.isFinished()) {
                damagePopups.removeIndex(i);
            }
        }

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        Main.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.N) {
            controller.returnToMainMenu();
            return true;
        }
        if (keycode == Input.Keys.SPACE) {
            controller.toggleAutoAim();
            return true;
        }
        if (keycode == Input.Keys.K) {
            controller.killMonster();
            return true;
        }
        if(keycode == Input.Keys.E){
            controller.killEyeBat();
            return true;
        }
        if(keycode == Input.Keys.T){
            controller.addTime(60000);
            return true;
        }
        return false;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public void setScoreLabel(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        controller.getWeaponController().handleWeaponShoot(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    public void addDamagePopup(float x, float y, String text) {
        damagePopups.add(new DamagePopup(x, y, text));
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public GameController getController() {
        return controller;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public Array<DamagePopup> getDamagePopups() {
        return damagePopups;
    }

    public void setDamagePopups(Array<DamagePopup> damagePopups) {
        this.damagePopups = damagePopups;
    }

    public ImageButton getSettingsButton() {
        return settingsButton;
    }

    public void setSettingsButton(ImageButton settingsButton) {
        this.settingsButton = settingsButton;
    }

    public Animation<TextureRegion> getTreeAnimation() {
        return treeAnimation;
    }

    public void setTreeAnimation(Animation<TextureRegion> treeAnimation) {
        this.treeAnimation = treeAnimation;
    }

    public Animation<TextureRegion> getPlayerAnimation() {
        return playerAnimation;
    }

    public void setPlayerAnimation(Animation<TextureRegion> playerAnimation) {
        this.playerAnimation = playerAnimation;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public GameAssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(GameAssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }

    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    public Label getHealthLabel() {
        return healthLabel;
    }

    public void setHealthLabel(Label healthLabel) {
        this.healthLabel = healthLabel;
    }

    public Label getKillsLabel() {
        return killsLabel;
    }

    public void setKillsLabel(Label killsLabel) {
        this.killsLabel = killsLabel;
    }

    public float getStateTime() {
        return stateTime;
    }
}
