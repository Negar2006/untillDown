package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
import com.tilldawn.Model.Tree;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends InputAdapter implements Screen {
    private Stage stage;
    private GameController controller;
    private Array<DamagePopup> damagePopups = new Array<>();
    private ImageButton settingsButton;
    private OrthographicCamera camera;

    private ArrayList<Tree> treeInstances;  // لیست درخت‌ها
    private Animation<TextureRegion> treeAnimation; // انیمیشن درخت
    private float stateTime;

    private GameAssetManager assetManager;

    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.assetManager = GameAssetManager.getGameAssetManager();
        this.treeInstances = new ArrayList<>();
        this.stateTime = 0f;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // تنظیم دکمه تنظیمات
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

        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            float x = rand.nextInt(Gdx.graphics.getWidth() - 100) + 50;
            float y = rand.nextInt(Gdx.graphics.getHeight() - 200) + 100;
            treeInstances.add(new Tree(x, y));
        }

        Animation<com.badlogic.gdx.graphics.Texture> rawTreeAnim = assetManager.getTree_idle_animation();

        Array<TextureRegion> frames = new Array<>();
        for (com.badlogic.gdx.graphics.Texture t : rawTreeAnim.getKeyFrames()) {
            frames.add(new TextureRegion(t));
        }
        treeAnimation = new Animation<>(rawTreeAnim.getFrameDuration(), frames, Animation.PlayMode.LOOP);

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

        // بروزرسانی بازی از کنترلر
        controller.updateGame();

//        for (Tree tree : treeInstances) {
//            if (tree.isAlive()) {
//                TextureRegion currentFrame = treeAnimation.getKeyFrame(stateTime);
//                Main.getBatch().draw(currentFrame, tree.getX(), tree.getY());
//            }
//        }

        for (int i = damagePopups.size - 1; i >= 0; i--) {
            DamagePopup popup = damagePopups.get(i);
            popup.update(delta);
            popup.draw(Main.getBatch());
            if (popup.isFinished()) {
                damagePopups.removeIndex(i);
            }
        }

        Main.getBatch().end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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

    public void damageTreeAt(float x, float y, int damage) {
        for (Tree tree : treeInstances) {
            if (tree.isAlive() && x >= tree.getX() && x <= tree.getX() + 64 && y >= tree.getY() && y <= tree.getY() + 64) {
                tree.damage(damage);
                addDamagePopup(tree.getX(), tree.getY() + 70, "-" + damage);
                break;
            }
        }
    }

    public void addDamagePopup(float x, float y, String text) {
        damagePopups.add(new DamagePopup(x, y, text));
    }
}
