package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
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
    private boolean hasShield = false;
    public boolean shieldActive = false;
    private float shieldRadius;
    private float shieldShrinkSpeed = 20f; // Shrinking speed for the shield
    private Circle shieldCircle;
    private ShapeRenderer shapeRenderer;

    public Monster(monsterTypes type) {
        shapeRenderer = new ShapeRenderer();
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
                this.hasShield = true;
                this.shieldActive = true;
                this.shieldRadius = 400f;
                this.shieldCircle = new Circle(0, 0, shieldRadius);
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

    public void setType(monsterTypes type) {
        this.type = type;
    }

    public float getSpeed() {
        return speed;
    }

    public monsterTypes getType() {
        return type;
    }

    public void updateAndDraw(SpriteBatch batch, Player player) {
        float delta = Gdx.graphics.getDeltaTime();
        time += delta;

        // Movement for TentacleMonster and Eyebat
        if (type == monsterTypes.TentacleMonster || type == monsterTypes.Eyebat) {
            moveTowards(player.getPlayerSprite().getX(), player.getPlayerSprite().getY());
        }

        // Eyebat shooting behavior
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

            // Shrink shield and handle collision
            if (shieldActive) {
                shieldRadius -= shieldShrinkSpeed * delta;
                if (shieldRadius < 0) shieldRadius = 0;

                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(Color.BLUE); // Shield color
                shapeRenderer.circle(this.getX(), this.getY(), shieldRadius);
                shapeRenderer.end();

                shieldCircle.set(this.getX(), this.getY(), shieldRadius);
                if (shieldCircle.contains(player.getPosX(), player.getPosY())) {
                    player.takeDamage(1); // Adjust damage as necessary
                }
            }
        }

        // Update the sprite and animation
        sprite.setRegion(animation.getKeyFrame(time, true));
        sprite.draw(batch);

        // Handle bullets for Eyebat
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

    public boolean isPlayerCollidingWithShield(Player player) {
        return shieldCircle.contains(player.getPosX(), player.getPosY());
    }

    public float getShieldRadius() {
        return shieldRadius;
    }


    public Sprite getSprite() {
        return sprite;
    }

    public float getHealth() {
        return health;
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

}
