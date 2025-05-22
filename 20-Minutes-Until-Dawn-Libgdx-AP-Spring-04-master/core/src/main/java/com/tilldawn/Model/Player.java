package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private Texture playerTexture = new Texture(GameAssetManager.getGameAssetManager().getCharacter1_idle0());
    private Sprite playerSprite = new Sprite(playerTexture);
    private float posX;
    private float posY;
    private float playerHealth = 100;
    private CollisionRect rect;
    private float time = 0;
    private float speed = 100f;
    private int score = 0;

    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;

    public Player() {
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);

        posX = playerSprite.getX();
        posY = playerSprite.getY();

        centerPlayerOnScreen();

        rect = new CollisionRect(posX, posY, playerSprite.getWidth(), playerSprite.getHeight());
    }

    private void centerPlayerOnScreen() {
        float centerX = (Gdx.graphics.getWidth() - playerSprite.getWidth()) / 2f;
        float centerY = (Gdx.graphics.getHeight() - playerSprite.getHeight()) / 2f;

        posX = centerX;
        posY = centerY;

        playerSprite.setPosition(posX, posY);
    }

    public void updatePosition(float deltaX, float deltaY) {
        posX += deltaX;
        posY += deltaY;
        playerSprite.setPosition(posX, posY);
        rect.move(posX, posY);
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
        playerSprite.setX(posX);
        rect.setPosition(posX, posY);
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
        playerSprite.setY(posY);
        rect.setPosition(posX, posY);
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public int getScore() {
        return score;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    public void reduceHealth(int i) {
        this.playerHealth -= i;
    }
}
