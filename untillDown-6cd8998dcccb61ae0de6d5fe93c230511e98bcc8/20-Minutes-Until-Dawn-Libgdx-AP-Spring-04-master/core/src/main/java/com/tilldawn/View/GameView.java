package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.DamagePopup;
import com.tilldawn.Model.GameAssetManager;

public class GameView extends InputAdapter implements Screen {
    private Stage stage;
    private Skin skin;
    private GameController controller;
    private Array<DamagePopup> damagePopups = new Array<>();
    private ImageButton pauseButton;
    private Animation<TextureRegion> treeAnimation;
    private Animation<TextureRegion> playerAnimation;
    private float stateTime;
    private GameAssetManager assetManager;
    private Label timerLabel;
    private Label healthLabel;
    private Label killsLabel;
    private Label scoreLabel;
    private String nameOfTexture;
    private Label levelLabel;
    private Label expLable;
    private ProgressBar expProgressBar;

    public GameView(GameController controller, Skin skin, Texture playerTexture, String name) {
        this.skin = skin;
        this.controller = controller;
        controller.setView(this, playerTexture, name);
        this.assetManager = GameAssetManager.getGameAssetManager();
        this.stateTime = 0f;
        this.nameOfTexture = name;
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

        // Create Level label
        levelLabel = new Label("Level: " + controller.getPlayerController().getPlayer().getLevel(), skin);
        levelLabel.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 40);
        stage.addActor(levelLabel);

        expLable = new Label("exp: " + controller.getPlayerController().getPlayer().getExp(), skin);
        expLable.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 80);
        stage.addActor(expLable);

        expProgressBar = new ProgressBar(0, 100, 1, false, skin.get("default-horizontal", ProgressBar.ProgressBarStyle.class));
        expProgressBar.setValue(0);
        expProgressBar.setAnimateDuration(0.25f);
        expProgressBar.setSize(300, 10);
        expProgressBar.setPosition(stage.getWidth() - 320, stage.getHeight() - 120);

        // Change the color of the progress bar when full
        expProgressBar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (expProgressBar.getValue() == expProgressBar.getMaxValue()) {
                    expProgressBar.setColor(Color.RED);
                }
            }
        });

        stage.addActor(expProgressBar);

        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new com.badlogic.gdx.graphics.Texture(Gdx.files.internal("pause_icon.png")))));
        pauseButton.setPosition(Gdx.graphics.getWidth() - 60, 10);  // Positioned at bottom-right
        pauseButton.setSize(50, 50);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.pauseGame();
            }
        });
        stage.addActor(pauseButton);

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

        long remainingMillis = controller.getRemainingTimeMillis();
        long minutes = remainingMillis / 60000;
        long seconds = (remainingMillis % 60000) / 1000;
        timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));

        healthLabel.setText("HP: " + controller.getPlayerHealth());
        killsLabel.setText("Kills: " + controller.getKillCount());
        scoreLabel.setText("Score: " + controller.getPlayerController().getPlayer().getScore());
        levelLabel.setText("Level: " + controller.getPlayerController().getPlayer().getLevel());
        expLable.setText("exp: " + controller.getPlayerController().getPlayer().getExp());

        expProgressBar.setRange(0, controller.getPlayerController().getPlayer().getLevel() * 20);
        expProgressBar.setValue(controller.getPlayerController().getPlayer().getExp());

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
        if (keycode == Input.Keys.E) {
            controller.killEyeBat();
            return true;
        }
        if (keycode == Input.Keys.T) {
            controller.addTime(60000);
            return true;
        }
        return false;
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
}
