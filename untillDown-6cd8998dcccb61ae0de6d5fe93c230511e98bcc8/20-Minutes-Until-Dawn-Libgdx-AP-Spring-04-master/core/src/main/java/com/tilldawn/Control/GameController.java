package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.Weapon;
import com.tilldawn.Model.enums.weaponType;
import com.tilldawn.View.*;

import java.util.ArrayList;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    private boolean autoAimEnabled = false;
    private long startTimeMillis;
    private long remainingTimeMillis;
    private long GAME_DURATION_MILLIS;
    private int numberOfKill = 0;
    private weaponType weaponType;
    private boolean isPaused = false;
    private long pausedTimeMillis = 0;
    private long totalElapsedTime = 0;
    private long pauseStartTime = 0;

    public GameController(int gameDurationMinutes, String weapon) {
        this.GAME_DURATION_MILLIS = gameDurationMinutes * 60 * 1000L;
        this.remainingTimeMillis = GAME_DURATION_MILLIS;
        this.startTimeMillis = System.currentTimeMillis();
        switch (weapon) {
            case "SMGs Dual":
                this.weaponType = com.tilldawn.Model.enums.weaponType.SMG_DUAL;
                break;
            case "shotgun":
                this.weaponType = com.tilldawn.Model.enums.weaponType.SHOTGUN;
                break;
            case "Revolver":
                this.weaponType = com.tilldawn.Model.enums.weaponType.REVOLVER;
                break;
            default:
                break;
        }
    }

    public void setView(GameView view, Texture playerTexture, String characterName) {
        this.view = view;
        playerController = new PlayerController(new Player(playerTexture), characterName, this);
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
            weaponController.update(monsterController.getElderMonster());
            playerController.update(delta);

            float totalGameTime = (currentTime - startTimeMillis) / 1000f;
            float holeTimeOfGame = GAME_DURATION_MILLIS / 1000f;
            monsterController.update(playerController.getPlayer(), Main.getBatch(), totalGameTime, holeTimeOfGame);

        }
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

        for (Monster monster : monsterController.getElderMonster()) {
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

    public void killEyeBat() {
        for (Monster monster : monsterController.getEyebatMonsters()) {
            monster.setHealth(-1);
        }
    }

    public void addTime(long millis) {
        startTimeMillis -= millis;
    }

    public long getRemainingTimeMillis() {
        long elapsed = System.currentTimeMillis() - startTimeMillis;
        return Math.max(0, remainingTimeMillis - elapsed);
    }

    public void endGame() {
        String username;
        if (Main.currentUser != null) {
            username = Main.currentUser.getUsername();
        } else {
            username = "guest";
        }

        int score = playerController.getPlayer().getScore(); // Get player score
        int kills = numberOfKill; // Number of kills
        float health = playerController.getPlayer().getPlayerHealth(); // Player health

        GameOverController gameOverController = new GameOverController(username, score, kills, health);
        Main.getMain().setScreen(new GameOverView(gameOverController, GameAssetManager.getGameAssetManager().getSkin()));
    }

    public ArrayList<Monster> allMonsters() {
        ArrayList<Monster> monsters = new ArrayList<>();
        for (Monster monster : monsterController.getTentacleMonsters()) {
            monsters.add(monster);
        }

        for (Monster monster : monsterController.getEyebatMonsters()) {
            monsters.add(monster);
        }

        for (Monster monster : monsterController.getTreeMonsters()) {
            monsters.add(monster);
        }

        for (Monster monster : monsterController.getElderMonster()) {
            monsters.add(monster);
        }
        return monsters;
    }

    public void startGame() {
        this.startTimeMillis = System.currentTimeMillis();
    }

    // Call when pausing the game
    public void pauseGame() {
        if (!isPaused) {
            isPaused = true;
            pauseStartTime = System.currentTimeMillis();
            Main.getMain().setScreen(new PauseMenuView(this));
        }
    }

    public void resumeGame() {
        if (isPaused) {
            isPaused = false;

            startTimeMillis += (System.currentTimeMillis() - pauseStartTime);
            Main.getMain().setScreen(view);
        }
    }

    public long getElapsedTime() {
        if (isPaused) {
            return totalElapsedTime;
        } else {
            return totalElapsedTime + (System.currentTimeMillis() - startTimeMillis);
        }
    }
}

