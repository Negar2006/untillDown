package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Main;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.Player;

import java.util.ArrayList;

public class WorldController {
    private PlayerController playerController;
    private MonsterController monsterController; // separate controller
    private Texture backgroundTexture;
    private OrthographicCamera camera;

    public WorldController(PlayerController playerController) {
        this.playerController = playerController;
        this.backgroundTexture = new Texture("background.png");

        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void update() {
        // Move camera to follow the player
        Player player = playerController.getPlayer();
        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        // Apply the camera's projection to the batch
        Main.getBatch().setProjectionMatrix(camera.combined);

        // Draw background (anchored at 0,0 in world coordinates)
        Main.getBatch().draw(backgroundTexture, 0, 0);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
