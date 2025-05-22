package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.enums.monsterTypes;

import java.util.Random;

public class Monster {
    private float health;
    private monsterTypes type;
    private float posX, posY;
    private Sprite sprite;
    private float speed = 0.5f;
    private float time = 0f;
    private Animation<Texture> animation;

    public Monster(monsterTypes type) {

        this.type = type;
        switch (type) {
            case TreeMonster:
                this.health = 10f;
                this.animation = GameAssetManager.getGameAssetManager().getTree_idle_animation();
                break;
            case TentacleMonster:
                this.health = 25f;
                this.animation = GameAssetManager.getGameAssetManager().getMonsterIdleAnimation();
                break;
            case Eyebat:
                this.health = 50f;
                this.animation = GameAssetManager.getGameAssetManager().getEyeBat_idle_animation();
            default:
                this.health = 100f;
                this.animation = GameAssetManager.getGameAssetManager().getMonsterIdleAnimation();
                break;
        }

        if (type == monsterTypes.TreeMonster) {
            this.animation = GameAssetManager.getGameAssetManager().getTree_idle_animation();
        } else if (type == monsterTypes.TentacleMonster) {
            this.animation = GameAssetManager.getGameAssetManager().getMonsterIdleAnimation();
        } else
            this.animation = GameAssetManager.getGameAssetManager().getEyeBat_idle_animation();

        Texture firstFrame = animation.getKeyFrame(0);
        sprite = new Sprite(firstFrame);
        if (type == monsterTypes.TentacleMonster || type == monsterTypes.TreeMonster)
            sprite.setSize(firstFrame.getWidth() * 2, firstFrame.getHeight() * 2);
        else
            sprite.setSize(firstFrame.getWidth(), firstFrame.getHeight());
        setRandomStartPosition();
    }


    private void setRandomStartPosition() {
        Random rand = new Random();

        // Define your big world size here
        float worldWidth = 3500f;  // set your actual world width
        float worldHeight = 2500f; // set your actual world height

        if (type == monsterTypes.TreeMonster) {
            // Spawn TreeMonster anywhere inside the big world bounds
            float x = rand.nextFloat() * worldWidth;
            float y = rand.nextFloat() * worldHeight;
            sprite.setPosition(x, y);

        } else {
            // For other monsters, spawn outside the visible screen edges
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();
            int edge = rand.nextInt(4);
            float x = 0, y = 0;

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

        if (type == monsterTypes.TentacleMonster || type == monsterTypes.Eyebat) {
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
