package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
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
    private Texture circleTexture;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private float shieldRadius;
    private long lastShrinkTime;
    private float shrinkingRadius = -1f;
    private float shrinkSpeed = 1000f;
    private boolean shieldActive = false;

    public WorldController(PlayerController playerController) {
        this.playerController = playerController;
        this.backgroundTexture = new Texture("background.png");
        this.circleTexture = new Texture("circle_shield.png");
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
        drawBackground(player);

        if (shieldActive) {
            drawShieldCircleWithShape(player);
        }
        applyRadialGradient(player);
    }

    private void drawShieldCircleWithShape(Player player) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (shrinkingRadius == -1f) {
            shrinkingRadius = shieldRadius;
        }

        shrinkingRadius -= shrinkSpeed * deltaTime;
        if (shrinkingRadius < 0) shrinkingRadius = 0;

        float playerX = player.getPosX();
        float playerY = player.getPosY();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.4f, 0.8f, 1f, 1f);
        shapeRenderer.circle(playerX, playerY, shrinkingRadius);
        shapeRenderer.end();
    }

    private void drawBackground(Player player) {
        SpriteBatch batch = Main.getBatch();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float backgroundWidth = Math.max(screenWidth, shieldRadius * 2);
        float backgroundHeight = Math.max(screenHeight, shieldRadius * 2);

        batch.setColor(1, 1, 1, 1);
        batch.draw(backgroundTexture,
            player.getPosX() - backgroundWidth / 2,
            player.getPosY() - backgroundHeight / 2,
            backgroundWidth,
            backgroundHeight);
    }

    private void applyRadialGradient(Player player) {
        SpriteBatch batch = Main.getBatch();

        float playerX = player.getPosX();
        float playerY = player.getPosY();

        float size = shieldRadius * 2;

        batch.setColor(1, 1, 1, 1);
        batch.draw(radialGradientTexture, playerX - shieldRadius, playerY - shieldRadius, size, size);
    }

    public void setShieldActive(boolean shieldActive) {
        this.shieldActive = shieldActive;
        if (shieldActive) {
            shrinkingRadius = -1f;
        }
    }

}
