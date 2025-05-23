package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.tilldawn.Main;
import com.tilldawn.Model.Player;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private float shieldRadius;
    private long lastShrinkTime;

    public WorldController(PlayerController playerController) {
        this.playerController = playerController;
        this.backgroundTexture = new Texture("background.png");

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer = new ShapeRenderer();
        shieldRadius = Math.max(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()) * 1.2f;
        lastShrinkTime = TimeUtils.millis();
    }

    public void update() {
        Player player = playerController.getPlayer();

        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        Main.getBatch().setProjectionMatrix(camera.combined);

        Main.getBatch().draw(backgroundTexture, 0, 0);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
