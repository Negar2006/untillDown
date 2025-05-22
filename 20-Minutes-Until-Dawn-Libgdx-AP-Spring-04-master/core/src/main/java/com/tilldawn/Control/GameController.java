package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.Weapon;
import com.tilldawn.View.*;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    private boolean autoAimEnabled = false;
    private long startTimeMillis;
    private final long GAME_DURATION_MILLIS = 10 * 60 * 1000;
    private int numberOfKill = 0;

    public void setView(GameView view, Texture playerTexture, String characterName) {
        this.view = view;
        playerController = new PlayerController(new Player(playerTexture), characterName);
        weaponController = new WeaponController(new Weapon());
        worldController = new WorldController(playerController);
        monsterController = new MonsterController();
        monsterController.setGameController(this);

        weaponController.setGameView(view);
        weaponController.setGameController(this);
        startTimeMillis = System.currentTimeMillis();
    }

    public void updateGame() {
        if (view != null) {
            long currentTime = System.currentTimeMillis();
            long elapsed = currentTime - startTimeMillis;

            if (elapsed >= GAME_DURATION_MILLIS) {
                endGame();
                return;
            }
            worldController.update();
            float delta = Gdx.graphics.getDeltaTime();
            weaponController.update(monsterController.getTentacleMonsters());
            weaponController.update(monsterController.getTreeMonsters());
            weaponController.update(monsterController.getEyebatMonsters());
            playerController.update(delta);
            monsterController.update(playerController.getPlayer(), Main.getBatch());
        }
    }

    private void endGame() {
        System.out.println("⏰ Game time finished! Going to end screen...");
        // Example: go to a hypothetical GameOverView screen (you should implement this)
        Main.getMain().setScreen(new GameOverView(new GameOverController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public boolean isAutoAimEnabled() {
        return autoAimEnabled;
    }

    public void toggleAutoAim() {
        autoAimEnabled = !autoAimEnabled;
    }


    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    private void checkIfPlayerOutOfScreen() {
        Player player = playerController.getPlayer();

        float worldX = player.getPosX();
        float worldY = player.getPosY();

        float WORLD_WIDTH = 1920;
        float WORLD_HEIGHT = 1080;

        if (worldX < 0 || worldX > WORLD_WIDTH || worldY < 0 || worldY > WORLD_HEIGHT) {
            System.out.println("💀 You can't go out of bounds! YOU LOST! 💀");
            Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }
    }

    public void returnToMainMenu() {
        Main.getMain().setScreen(new RegisterMenuViews(new RegisterMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToSettingsMenu() {
        Main.getMain().setScreen(new SettingsView(new SettingsController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void killMonster() {
        Player player = playerController.getPlayer();
        float playerX = player.getPlayerSprite().getX();
        float playerY = player.getPlayerSprite().getY();
        float radius = 50f;

        for (Monster monster : monsterController.getTentacleMonsters()) {
            killingAllMonster(playerX, playerY, radius, monster);
        }

        for (Monster monster : monsterController.getEyebatMonsters()) {
            killingAllMonster(playerX, playerY, radius, monster);
        }

        for (Monster monster : monsterController.getTreeMonsters()) {
            killingAllMonster(playerX, playerY, radius, monster);
        }
    }

    private void killingAllMonster(float playerX, float playerY, float radius, Monster monster) {
        if (!monster.isDead()) {
            float dx = monster.getSprite().getX() - playerX;
            float dy = monster.getSprite().getY() - playerY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance <= radius) {
                monster.setHealth(-1);
                System.out.println("💀 Monster slain at (" + monster.getX() + ", " + monster.getY() + ")");
            }
        }
    }

    public int getNumberOfKill() {
        return numberOfKill;
    }

    public void setNumberOfKill(int numberOfKill) {
        this.numberOfKill = numberOfKill;
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public float getPlayerHealth() {
        return playerController.getPlayer().getPlayerHealth();
    }

    public int getKillCount() {
        return this.numberOfKill;
    }
}
