package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.enums.monsterTypes;

import java.util.Random;

public class Monster {
    private float health = 100;
    private monsterTypes type;
    private float posX, posY;
    private Sprite sprite;
    private float speed = 0.5f;
    private float time = 0f;
    private Animation<Texture> animation;

    public Monster(monsterTypes type) {
        this.type = type;

        if (type == monsterTypes.TreeMonster) {
            this.animation = GameAssetManager.getGameAssetManager().getTree_idle_animation();
        } else {
            this.animation = GameAssetManager.getGameAssetManager().getMonsterIdleAnimation();
        }

        Texture firstFrame = animation.getKeyFrame(0);
        sprite = new Sprite(firstFrame);
        sprite.setSize(firstFrame.getWidth() * 3, firstFrame.getHeight() * 3);
        setRandomStartPosition();
    }


    private void setRandomStartPosition() {
        Random rand = new Random();
        int edge = rand.nextInt(4);
        float x = 0, y = 0;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        switch (edge) {
            case 0: // top
                x = rand.nextFloat() * screenWidth;
                y = screenHeight + 50;
                break;
            case 1: // right
                x = screenWidth + 50;
                y = rand.nextFloat() * screenHeight;
                break;
            case 2: // bottom
                x = rand.nextFloat() * screenWidth;
                y = -50;
                break;
            case 3: // left
                x = -50;
                y = rand.nextFloat() * screenHeight;
                break;
        }

        sprite.setPosition(x, y);
    }

    public void moveTowards(float targetX, float targetY) {
        float dx = targetX - sprite.getX();
        float dy = targetY - sprite.getY();
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            dx /= length;
            dy /= length;
        }
        sprite.setPosition(sprite.getX() + dx * speed, sprite.getY() + dy * speed);
    }

    public void updateAndDraw(com.badlogic.gdx.graphics.g2d.SpriteBatch batch, Player player) {
        time += Gdx.graphics.getDeltaTime();

        if (type == monsterTypes.TentacleMonster) {
            moveTowards(player.getPlayerSprite().getX(), player.getPlayerSprite().getY());
        }

        sprite.setRegion(animation.getKeyFrame(time, true));
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void takeDamage(float damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public float getX() {
        return sprite.getX() + sprite.getWidth() / 2f;
    }

    public float getY() {
        return sprite.getY() + sprite.getHeight() / 2f;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

}
