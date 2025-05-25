package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;
import com.tilldawn.Main;
import com.tilldawn.Model.Player;

public class WorldController {
    private PlayerController playerController;
    private Texture backgroundTexture;
    private Texture radialGradientTexture;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private float shieldRadius;
    private long lastShrinkTime;

    public WorldController(PlayerController playerController) {
        this.playerController = playerController;
        this.backgroundTexture = new Texture("background.png");

        // Load radial gradient texture (this needs to be created beforehand as a radial gradient)
        this.radialGradientTexture = new Texture("radial_gradient11.png");

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

        // Draw background texture
        Main.getBatch().draw(backgroundTexture, 0, 0);

        // Apply the radial gradient effect around the player
        applyRadialGradient(player);
    }

    private void applyRadialGradient(Player player) {
        SpriteBatch batch = Main.getBatch();

        // Calculate the size of the radial gradient texture to match the shield radius
        float size = shieldRadius * 2; // Since the texture will cover the diameter of the circle

        // Set the position of the radial gradient texture to the player's position
        batch.setColor(1, 1, 1, 1); // Make sure the texture is fully opaque

        // Draw the radial gradient texture around the player
        batch.draw(radialGradientTexture, player.getPosX() - shieldRadius, player.getPosY() - shieldRadius, size, size);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
