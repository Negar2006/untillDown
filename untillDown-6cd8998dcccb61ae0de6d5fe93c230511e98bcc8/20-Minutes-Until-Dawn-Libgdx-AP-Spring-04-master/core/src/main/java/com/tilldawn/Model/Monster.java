package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Model.enums.monsterTypes;

import java.util.ArrayList;
import java.util.Random;

public class Monster {
    private float health;
    private monsterTypes type;
    private float posX, posY;
    private Sprite sprite;
    private float speed = 0.5f;
    private float time = 0f;
    private Animation<Texture> animation;
    private float bulletTimer = 0f;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private float elderDashTimer = 0f;
    private boolean isDashing = false;
    private float dashSpeed = 10f;
    private float dashDuration = 0.3f;
    private float dashTimePassed = 0f;
    private boolean dying = false;
    private boolean deathAnimFinished = false;
    private Animation<Texture> deathAnimation;
    private float deathStateTime = 0f;
    private float knockbackX = 0f;
    private float knockbackY = 0f;
    private float knockbackDecay = 0.9f;


    public Monster(monsterTypes type) {
        this.deathAnimation = GameAssetManager.getGameAssetManager().getDeathAnimation();
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
                break;
            case Elder:
                this.health = 400f;
                this.animation = GameAssetManager.getGameAssetManager().getBoss_idle_animation();
                break;
            default:
                this.health = 100f;
                this.animation = GameAssetManager.getGameAssetManager().getMonsterIdleAnimation();
                break;
        }
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
        float worldWidth = 3500f;
        float worldHeight = 2500f;

        if (type == monsterTypes.TreeMonster) {
            float x = rand.nextFloat() * worldWidth;
            float y = rand.nextFloat() * worldHeight;
            sprite.setPosition(x, y);

        } else {
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();
            int edge = rand.nextInt(4);
            float x = 0, y = 0;

            switch (edge) {
                case 0:
                    x = rand.nextFloat() * screenWidth;
                    y = screenHeight + 50;
                    break;
                case 1:
                    x = screenWidth + 50;
                    y = rand.nextFloat() * screenHeight;
                    break;
                case 2:
                    x = rand.nextFloat() * screenWidth;
                    y = -50;
                    break;
                case 3:
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
        leangth(dx, dy, speed);
    }

    private void leangth(float dx, float dy, float speed) {
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            dx /= length;
            dy /= length;
        }
        sprite.setPosition(sprite.getX() + dx * speed, sprite.getY() + dy * speed);
    }

    public monsterTypes getType() {
        return type;
    }

    public void updateAndDraw(SpriteBatch batch, Player player) {
        float delta = Gdx.graphics.getDeltaTime();

        if (dying) {
            deathStateTime += delta;
            Texture currentFrame = deathAnimation.getKeyFrame(deathStateTime, false);
            sprite.setRegion(currentFrame);
            sprite.draw(batch);

            if (deathStateTime >= 1f) deathAnimFinished = true;
            return;
        }

        time += delta;

        if (Math.abs(knockbackX) > 0.01f || Math.abs(knockbackY) > 0.01f) {
            sprite.translate(knockbackX, knockbackY);
            knockbackX *= knockbackDecay;
            knockbackY *= knockbackDecay;
        }

        if (type == monsterTypes.TentacleMonster || type == monsterTypes.Eyebat) {
            moveTowards(player.getPlayerSprite().getX(), player.getPlayerSprite().getY());
        }

        if (type == monsterTypes.Eyebat) {
            bulletTimer += delta;
            if (bulletTimer >= 3f) {
                shootBullet(player.getPosX(), player.getPosY());
                bulletTimer = 0f;
            }
        }

        if (type == monsterTypes.Elder) {
            elderDashTimer += delta;

            if (!isDashing && elderDashTimer >= 5f) {
                isDashing = true;
                dashTimePassed = 0f;
                elderDashTimer = 0f;
            }

            if (isDashing) {
                dashTimePassed += delta;
                float dx = player.getPlayerSprite().getX() - sprite.getX();
                float dy = player.getPlayerSprite().getY() - sprite.getY();
                leangth(dx, dy, dashSpeed);

                if (dashTimePassed >= dashDuration) {
                    isDashing = false;
                }
            } else moveTowards(player.getPlayerSprite().getX(), player.getPlayerSprite().getY());
        }

        sprite.setRegion(animation.getKeyFrame(time, true));
        sprite.draw(batch);

        for (Bullet b : bullets) {
            b.getSprite().translate(b.getDirection().x * 5f, b.getDirection().y * 5f);
            b.getSprite().draw(batch);
        }
    }

    private void shootBullet(float targetX, float targetY) {
        float startX = getX();
        float startY = getY();
        bullets.add(new Bullet(targetX, targetY, startX, startY, 5));
    }


    public Sprite getSprite() {
        return sprite;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
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

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void playDeathAnimation() {
        this.dying = true;
        this.deathStateTime = 0f;
    }

    public boolean isDeathAnimationFinished() {
        return deathAnimFinished;
    }

    public boolean isDying() {
        return dying;
    }

    public void applyKnockback(float fromX, float fromY, float strength) {
        if (type == monsterTypes.TreeMonster) return;

        float dx = sprite.getX() - fromX;
        float dy = sprite.getY() - fromY;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            dx /= length;
            dy /= length;
        }
        knockbackX = dx * strength;
        knockbackY = dy * strength;
    }
}

