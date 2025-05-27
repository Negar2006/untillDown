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

        // Adjust camera position based on the player's position
        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        // Set the camera projection matrix to adjust for camera position and zoom
        Main.getBatch().setProjectionMatrix(camera.combined);

        // Draw background texture (background shrinks with radial)
        drawBackground(player);

        // Apply the radial gradient effect around the player
        applyRadialGradient(player);
    }

    private void drawBackground(Player player) {
        SpriteBatch batch = Main.getBatch();

        // Calculate the size of the background based on the shieldRadius (or player's position)
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // The background shrinks with the radial size (to match the player's position)
        float backgroundWidth = Math.max(screenWidth, shieldRadius * 2);
        float backgroundHeight = Math.max(screenHeight, shieldRadius * 2);

        // Adjust the background to fit the player and radial size
        batch.setColor(1, 1, 1, 1); // Make sure the texture is fully opaque

        // Draw the background around the player's position
        batch.draw(backgroundTexture,
            player.getPosX() - backgroundWidth / 2,
            player.getPosY() - backgroundHeight / 2,
            backgroundWidth,
            backgroundHeight);
    }

    private void applyRadialGradient(Player player) {
        SpriteBatch batch = Main.getBatch();

        // Get the player's position (this will be the center of the gradient)
        float playerX = player.getPosX();
        float playerY = player.getPosY();

        // Calculate the size of the radial gradient texture
        float size = shieldRadius * 2; // Make it cover the screen

        // Set the position of the radial gradient texture based on the player's position
        batch.setColor(1, 1, 1, 1); // Make sure the texture is fully opaque

        // Draw the radial gradient around the player, centered at their position
        batch.draw(radialGradientTexture, playerX - shieldRadius, playerY - shieldRadius, size, size);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
