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

    public PlayerController(Player player){
        this.player = player;
    }

    public void update(float delta) {
        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += moveSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= moveSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= moveSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += moveSpeed * delta;

        Player player = getPlayer();
        player.setPosX(player.getPosX() + dx);
        player.setPosY(player.getPosY() + dy);
    }
    public void handlePlayerInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            player.setPosY(player.getPosY() - player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            player.setPosX(player.getPosX() - player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            player.setPosY(player.getPosY() + player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            player.setPosX(player.getPosX() + player.getSpeed());
            player.getPlayerSprite().flip(true, false);
        }
    }

    public void idleAnimation(){
        Animation<Texture> animation = GameAssetManager.getGameAssetManager().getCharacter1_idle_animation();

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
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
