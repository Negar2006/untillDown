package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

public class GameView extends InputAdapter implements Screen {
    private Stage stage;
    private GameController controller;
    Array<DamagePopup> damagePopups = new Array<>();
    private ImageButton settingsButton;
    private OrthographicCamera camera;
    private Texture gearTexture;

    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        gearTexture = new Texture(Gdx.files.internal("gear.png"));
        TextureRegion gearRegion = new TextureRegion(gearTexture);
        TextureRegionDrawable gearDrawable = new TextureRegionDrawable(gearRegion);
        settingsButton = new ImageButton(gearDrawable);

        settingsButton.setPosition(Gdx.graphics.getWidth() - 60, Gdx.graphics.getHeight() - 60);
        settingsButton.setSize(50, 50);

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToSettingsMenu();
            }
        });

        stage.addActor(settingsButton);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();

        controller.updateGame();

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
        gearTexture.dispose();
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

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void addDamagePopup(float x, float y, String text) {
        damagePopups.add(new DamagePopup(x, y, text));
    }
}
