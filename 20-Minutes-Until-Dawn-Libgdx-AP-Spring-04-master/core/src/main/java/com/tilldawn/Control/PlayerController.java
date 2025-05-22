package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Player;

public class PlayerController {
    private Player player;
    private float moveSpeed = 100f;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void update(float delta) {
        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += player.getSpeed() * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= player.getSpeed() * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= player.getSpeed() * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += player.getSpeed() * delta;

        player.updatePosition(dx, dy);

        player.getPlayerSprite().draw(Main.getBatch());
    }

    public void idleAnimation() {
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getCharacter1_idle_animation();

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
