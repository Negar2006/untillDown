package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tilldawn.Main;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.enums.monsterTypes;

public class WorldController {
    private PlayerController playerController;
    private GameController gameController;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    public WorldController(PlayerController playerController, GameController gameController) {
        this.gameController = gameController;
        this.playerController = playerController;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer = new ShapeRenderer();
    }

    public void update() {
        Player player = playerController.getPlayer();

        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        Main.getBatch().setProjectionMatrix(camera.combined);

        Main.getBatch().draw(new Texture("background.png"), 0, 0);

        // Draw the Elder monster's shield if active
        for (Monster monster : gameController.getMonsterController().getElderMonster()) {
            if (monster.getType() == monsterTypes.Elder && !monster.isDead()) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.CYAN); // Shield color
                shapeRenderer.circle(monster.getX(), monster.getY(), monster.getShieldRadius());
                shapeRenderer.end();
            }
        }
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
