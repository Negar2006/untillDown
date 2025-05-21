package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.Weapon;
import com.tilldawn.View.GameView;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.RegisterMenuViews;
import com.tilldawn.View.SettingsView;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private MonsterController monsterController;
    private boolean autoAimEnabled = false;


    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(new Player());
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(new Weapon());
        weaponController.setGameView(view);
        weaponController.setGameController(this);
        monsterController = new MonsterController();
    }

    public void updateGame() {
        if (view != null) {
            worldController.update();
            float delta = Gdx.graphics.getDeltaTime();
            playerController.update(delta);
            weaponController.update(monsterController.getMonsters());
            monsterController.update(playerController.getPlayer(), Main.getBatch());
            checkIfPlayerOutOfScreen();
        }
    }

    public MonsterController getMonsterController() {
        return monsterController;
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

        float screenX = player.getPlayerSprite().getX();
        float screenY = player.getPlayerSprite().getY();
        float width = player.getPlayerSprite().getWidth();
        float height = player.getPlayerSprite().getHeight();

        if (screenX < 0 || screenX + width < 0 || screenX > Gdx.graphics.getWidth()
            || screenY + height < 0 || screenY > Gdx.graphics.getHeight()) {
            System.out.println("💀you can't go out of bounds! YOU LOST!💀");
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

        for (Monster monster : monsterController.getMonsters()) {
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
    }

    public WorldController getWorldController() {
        return worldController;
    }
}
