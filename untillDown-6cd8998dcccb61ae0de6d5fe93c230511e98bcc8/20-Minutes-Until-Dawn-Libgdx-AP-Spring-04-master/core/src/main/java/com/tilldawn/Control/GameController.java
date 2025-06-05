package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.Model.enums.Ability;
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
    private long pauseStartTime = 0;
    private String fileName;
    private String gameResult = "Game Over";

    public GameController(int gameDurationMinutes, String weapon) {
        this.GAME_DURATION_MILLIS = gameDurationMinutes * 60 * 1000L;
        this.remainingTimeMillis = GAME_DURATION_MILLIS;
        this.startTimeMillis = System.currentTimeMillis();
        this.monsterController = new MonsterController();
        switch (weapon) {
            case "SMG_DUAL":
            case "SMGs Dual":
                this.weaponType = com.tilldawn.Model.enums.weaponType.SMG_DUAL;
                break;
            case "SHOTGUN":
            case "shotgun":
                this.weaponType = com.tilldawn.Model.enums.weaponType.SHOTGUN;
                break;
            case "REVOLVER":
            case "Revolver":
                this.weaponType = com.tilldawn.Model.enums.weaponType.REVOLVER;
                break;
            default:
                break;
        }
    }

    public void setGAME_DURATION_MILLIS(long GAME_DURATION_MILLIS) {
        this.GAME_DURATION_MILLIS = GAME_DURATION_MILLIS;
    }

    public void setView(GameView view, Texture playerTexture, String characterName) {
        this.view = view;
        this.fileName = characterName;
        playerController = new PlayerController(new Player(playerTexture), characterName, this);
        weaponController = new WeaponController(new Weapon(weaponType));
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
            weaponController.update();
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

    public void returnToMainMenu() {
        Main.getMain().setScreen(new RegisterMenuViews(new RegisterMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void killMonster() {
        Player player = playerController.getPlayer();
        float playerX = player.getPlayerSprite().getX();
        float playerY = player.getPlayerSprite().getY();
        float radius = 200f;

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

    public void setNumberOfKill(int numberOfKill) {
        this.numberOfKill = numberOfKill;
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

    public long getStartTimeMillis() {
        return startTimeMillis;
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
        if (Main.currentUser != null) username = Main.currentUser.getUsername();
        else username = "guest";

        int score = (int) ((System.currentTimeMillis() - startTimeMillis) / 1000L * numberOfKill);
        int kills = numberOfKill;
        long time = System.currentTimeMillis() - startTimeMillis;
        float health = playerController.getPlayer().getPlayerHealth();

        if (health <= 0) gameResult = "Game Over";
        else if (remainingTimeMillis <= 0) gameResult = "Time's Up you win";
        else gameResult = "Give UP";

        GameOverController gameOverController = new GameOverController(username, score, kills, health, time, gameResult);

        if (Main.currentUser != null) {
            Main.currentUser.addNumberOfKilling(kills);
            Main.currentUser.addScore(score);
            Main.currentUser.setTimeAliveMillis(time);
        }

        saveUserToJson(Main.currentUser);

        Main.getMain().setScreen(new GameOverView(gameOverController, GameAssetManager.getGameAssetManager().getSkin()));
    }

    private void saveUserToJson(User user) {
        Json json = new Json();
        FileHandle file = Gdx.files.local("users/" + user.getUsername() + ".json");

        if (!file.parent().exists()) file.parent().mkdirs();
        file.writeString(json.prettyPrint(json.toJson(user)), false);
    }

    public void pauseGame() {
        if (!isPaused) {
            isPaused = true;
            pauseStartTime = System.currentTimeMillis();
            Main.getMain().setScreen(new PauseMenuView(this, view));
        }
    }

    public void resumeGame() {
        if (isPaused) {
            isPaused = false;

            startTimeMillis += (System.currentTimeMillis() - pauseStartTime);
            Main.getMain().setScreen(view);
        }
    }

    public void saveGameState() {
        Player player = playerController.getPlayer();
        SaveData data = new SaveData();
        data.playerName = player.getCharacterName();
        data.playerX = player.getPosX();
        data.playerY = player.getPosY();
        data.playerHealth = player.getPlayerHealth();
        data.exp = player.getExp();
        data.level = player.getLevel();
        for (Ability ability : player.getGainAbilities()) {
            data.abilities.add(ability.name());
        }

        String nameOfFile = player.getPlayerTexture().toString().replaceAll(".png", "");
        data.NameOfFile = nameOfFile;

        System.out.println(data.NameOfFile);

        data.remainingTimeMillis = remainingTimeMillis;
        data.weapon = this.weaponType.name();

        data.treeMonsterPositionX = new ArrayList<>();
        data.treeMonsterPositionY = new ArrayList<>();
        for (Monster m : monsterController.getTreeMonsters()) {
            data.treeMonsterPositionX.add(m.getX());
            data.treeMonsterPositionY.add(m.getY());
        }
        data.treeMonsterCount = data.treeMonsterPositionX.size();

        data.elderMonsterAppeared = !monsterController.getElderMonster().isEmpty();
        if (data.elderMonsterAppeared) {
            Monster elder = monsterController.getElderMonster().get(0);
            data.elderMonsterPositionX = elder.getX();
            data.elderMonsterPositionY = elder.getY();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOut = gson.toJson(data);

        FileHandle file = Gdx.files.local(data.playerName + ".json");
        file.writeString(jsonOut, false);
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public MonsterController getMonsterController() {
        return monsterController;
    }

    public boolean isReloadAutoEnabled() {
        return autoAimEnabled;
    }

    public void setRemainingTimeMillis(long time) {
        this.remainingTimeMillis = time;
    }

    public WorldController getWorldController() {
        return this.worldController;
    }
}
