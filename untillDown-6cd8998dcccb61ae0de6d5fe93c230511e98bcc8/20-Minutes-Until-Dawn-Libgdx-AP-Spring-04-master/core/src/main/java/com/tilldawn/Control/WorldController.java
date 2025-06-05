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
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private float shieldRadius;
    private float shrinkingRadius = -1f;
    private float shrinkSpeed = 100f;
    private boolean shieldActive = false;

    public WorldController(PlayerController playerController) {
        this.playerController = playerController;
        this.backgroundTexture = new Texture("background.png");
        this.radialGradientTexture = new Texture("radial_gradient11.png");

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer = new ShapeRenderer();
        shieldRadius = Math.max(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()) * 1.2f;
    }

    public void update() {
        Player player = playerController.getPlayer();

        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        SpriteBatch batch = Main.getBatch();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        drawBackground(batch, player);
        applyRadialGradient(batch, player);

    }

    private void drawBackground(SpriteBatch batch, Player player) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float backgroundWidth = Math.max(screenWidth, shieldRadius * 2);
        float backgroundHeight = Math.max(screenHeight, shieldRadius * 2);

        batch.draw(backgroundTexture,
            player.getPosX() - backgroundWidth / 2,
            player.getPosY() - backgroundHeight / 2,
            backgroundWidth,
            backgroundHeight);
    }

    private void applyRadialGradient(SpriteBatch batch, Player player) {
        float playerX = player.getPosX();
        float playerY = player.getPosY();
        float size = shieldRadius * 2;

        batch.draw(radialGradientTexture,
            playerX - shieldRadius,
            playerY - shieldRadius,
            size,
            size);
    }

    public void drawShieldCircleWithShape(Player player) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (shrinkingRadius == -1f) {
            shrinkingRadius = shieldRadius;
        }

        shrinkingRadius -= shrinkSpeed * deltaTime;
        if (shrinkingRadius < 0) shrinkingRadius = 0;

        float playerX = player.getPosX();
        float playerY = player.getPosY();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0.4f, 0.8f, 1f, 1f);
        shapeRenderer.circle(playerX, playerY, shrinkingRadius);
        shapeRenderer.end();
    }

    public boolean isShieldActive() {
        return shieldActive;
    }

    public void setShieldActive(boolean shieldActive) {
        this.shieldActive = shieldActive;
        if (shieldActive) {
            shrinkingRadius = -1f;
        }
    }

    public void checkShieldCollision(Player player) {
        if (!shieldActive) return;

        if (shrinkingRadius <= 50 && shrinkingRadius > 0) {
            player.takeDamage(5);
            System.out.println("Shield hit! Player health decreased by 5.");
        }
    }
}
