package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Control.GameOverController;
import com.tilldawn.Control.PlayerController;
import com.tilldawn.Main;
import com.tilldawn.Model.enums.Ability;
import com.tilldawn.View.GameOverView;
import com.tilldawn.View.GameView;

import java.util.ArrayList;

public class Player {
    private Texture playerTexture;
    private Sprite playerSprite;
    private String name;
    private float posX;
    private float posY;
    private float playerHealth;
    private CollisionRect rect;
    private float speed = 100f;
    private int score = 0;
    private boolean isPlayerIdle = true;
    private PlayerController playerController;
    private int Level;
    private int exp;
    private ArrayList<Ability> gainAbilities = new ArrayList<>();
    private boolean isInvincible = false;
    private long invincibleStartTime = 0;
    private final long INVINCIBLE_DURATION = 10000;
    private boolean isDead = false;
    private Texture deadTexture;
    private Sprite deadSprite;

    public Player(Texture playerTexture) {
        if (Main.currentUser != null) name = Main.currentUser.getUsername();
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

        deadTexture = new Texture(Gdx.files.internal("dead.png"));
        deadSprite = new Sprite(deadTexture);
        deadSprite.setSize(playerSprite.getWidth(), playerSprite.getHeight());

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

        if (isInvincible) {
            playerSprite.setColor(1, 1, 1, 0.5f);
        } else {
            playerSprite.setColor(1, 1, 1, 1);
        }
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

    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void takeDamage(int damage) {
        playerHealth -= damage;

        if (Main.getMain().getScreen() instanceof GameView) {
            GameView view = (GameView) Main.getMain().getScreen();

            float popupX = playerSprite.getX() + playerSprite.getWidth() / 2;
            float popupY = playerSprite.getY() + playerSprite.getHeight();

            view.addDamagePopup(popupX, popupY, "-" + damage);
        }
        if (playerHealth <= 0) {
            playerHealth = 0;
            isDead = true;

            deadSprite.setPosition(playerSprite.getX(), playerSprite.getY());
            playerSprite.setAlpha(0);

            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                GameOverController gameOverController;
                if (Main.currentUser == null) {
                    gameOverController = new GameOverController("guest", score,
                        playerController.getGameController().getKillCount(), playerHealth, 0, "Game Over");
                } else {
                    long time = System.currentTimeMillis() - getPlayerController().getGameController().getStartTimeMillis();
                    gameOverController = new GameOverController(Main.currentUser.getUsername(), score,
                        playerController.getGameController().getKillCount(), playerHealth, time, "Game Over");
                }

                Gdx.app.postRunnable(() -> Main.getMain().setScreen(
                    new GameOverView(gameOverController, GameAssetManager.getGameAssetManager().getSkin())));
            }).start();
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
        if (this.exp >= 20 * Level) {
            Level += 1;
            this.exp = 0;

            if (Main.getMain().getScreen() instanceof GameView) {
                ((GameView) Main.getMain().getScreen()).showAbilitySelectionDialog();
            }
        }
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return this.Level;
    }

    public ArrayList<Ability> getGainAbilities() {
        return gainAbilities;
    }

    public void addAbility(Ability ability) {
        gainAbilities.add(ability);
    }

    public boolean hasAbility(Ability ability) {
        return gainAbilities.contains(ability);
    }

    public void removeAbility(Ability ability) {
        for (Ability ability1 : gainAbilities) {
            if (ability.equals(ability1)) {
                gainAbilities.remove(ability);
                break;
            }
        }
    }

    public void activateInvincibility() {
        isInvincible = true;
        invincibleStartTime = System.currentTimeMillis();
    }

    public void updateInvincibility() {
        if (isInvincible && (System.currentTimeMillis() - invincibleStartTime > INVINCIBLE_DURATION)) {
            isInvincible = false;
        }
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean isDead() {
        return isDead;
    }

    public Sprite getDeadSprite() {
        return deadSprite;
    }

    public void setGainAbilities(ArrayList<String> gainAbilities) {
        ArrayList<Ability> abilityList = new ArrayList<>();
        for (String string : gainAbilities) {
            Ability newAbility = Ability.getByName(string);
            abilityList.add(newAbility);
        }
        this.gainAbilities = abilityList;
    }

    public void setLevel(int level) {
        Level = level;
    }
}
