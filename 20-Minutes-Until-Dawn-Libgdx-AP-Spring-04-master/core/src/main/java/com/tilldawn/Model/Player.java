package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private Texture playerTexture = new Texture(GameAssetManager.getGameAssetManager().getCharacter1_idle0());
    private Sprite playerSprite = new Sprite(playerTexture);
    private float playerHealth = 100;
    private CollisionRect rect;
    private float time = 0;
    private float speed = 100f;
    private int score = 0;

    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;

    public Player() {
        float spriteWidth = playerTexture.getWidth() * 3;
        float spriteHeight = playerTexture.getHeight() * 3;

        // Position at screen center
        float startX = (Gdx.graphics.getWidth() - spriteWidth) / 2f;
        float startY = (Gdx.graphics.getHeight() - spriteHeight) / 2f;

        playerSprite.setSize(spriteWidth, spriteHeight);
        playerSprite.setPosition(startX, startY);

        rect = new CollisionRect(startX, startY, spriteWidth, spriteHeight);
    }

    // Position getters/setters use sprite's position to keep consistent
    public float getPosX() {
        return playerSprite.getX();
    }

    public void setPosX(float posX) {
        playerSprite.setX(posX);
        rect.setPosition(posX, getPosY());
    }

    public float getPosY() {
        return playerSprite.getY();
    }

    public void setPosY(float posY) {
        playerSprite.setY(posY);
        rect.setPosition(getPosX(), posY);
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getSpeed() {
        return speed;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int amount) {
        this.score += amount;
    }
}
