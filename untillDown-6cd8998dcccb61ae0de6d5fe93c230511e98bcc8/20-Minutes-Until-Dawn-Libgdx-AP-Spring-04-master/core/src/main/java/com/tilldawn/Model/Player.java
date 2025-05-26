package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Control.GameController;
import com.tilldawn.Control.GameOverController;
import com.tilldawn.Control.PlayerController;
import com.tilldawn.Control.PreGameMenuController;
import com.tilldawn.Main;
import com.tilldawn.View.GameOverView;
import com.tilldawn.View.PreGameMenuView;

import java.util.ArrayList;

public class Player {
    private Texture playerTexture;
    private Sprite playerSprite;
    private String name;
    private float posX;
    private float posY;
    private float playerHealth = 100;
    private CollisionRect rect;
    private float time = 0;
    private float speed = 100f;
    private int score = 0;
    private boolean isPlayerIdle = true;
    private PlayerController playerController;
    private int Level;
    private int exp;

    public Player(Texture playerTexture) {
        if(Main.currentUser!= null) name = Main.currentUser.getUsername();
        else name = "guest";
        this.playerTexture = playerTexture;
        this.playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);

        posX = playerSprite.getX();
        posY = playerSprite.getY();

        centerPlayerOnScreen();
        rect = new CollisionRect(posX, posY, playerSprite.getWidth(), playerSprite.getHeight());
        this.Level = 1;
        this.exp = 0;
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

    public PlayerController getPlayerController() {
        return playerController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
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

    public void takeDamage(int damage) {
        // Subtract damage from player's health
        playerHealth -= damage;

        // Check if health is less than or equal to 0
        if (playerHealth <= 0) {
            playerHealth = 0; // Ensure health doesn't go below zero

            // Create a GameOverController instance with the player's stats
            GameOverController gameOverController;
            if (Main.currentUser == null) {
                gameOverController = new GameOverController("guest", score, playerController.getGameController().getKillCount(), playerHealth);
            } else {
                gameOverController = new GameOverController(Main.currentUser.getUsername(), score, playerController.getGameController().getKillCount(), playerHealth);
            }
            Main.getMain().setScreen(new GameOverView(gameOverController, GameAssetManager.getGameAssetManager().getSkin()));
        }
    }

    public String getCharacterName() {
        return name;
    }

    public void setHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public void setCharacterName(String playerName) {
        this.name = playerName;
    }

    public int getExp() {
        return exp;
    }

    public void addEXP(int exp) {
        this.exp += exp;
        if(this.exp >= 20 * Level){
            Level += 1;
            this.exp = 0;
        }
    }

    public int getLevel() {
        return this.Level;
    }
}
