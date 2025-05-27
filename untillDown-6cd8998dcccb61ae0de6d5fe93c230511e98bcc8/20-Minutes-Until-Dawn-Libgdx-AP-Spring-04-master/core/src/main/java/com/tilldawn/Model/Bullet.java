package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

    private Texture texture = new Texture(GameAssetManager.getGameAssetManager().getBullet());
    private Sprite sprite = new Sprite(texture);
    private int damage = 5;
    private Vector2 direction;

    public Bullet(float targetX, float targetY, float playerX, float playerY, int damage) {
        sprite.setSize(20, 20);
        sprite.setX(playerX);
        sprite.setY(playerY);

        // direction from player to target, normalized
        direction = new Vector2(targetX - playerX, targetY - playerY).nor();
        this.damage = damage;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public Vector2 getDirection() {
        return direction;
    }
}
