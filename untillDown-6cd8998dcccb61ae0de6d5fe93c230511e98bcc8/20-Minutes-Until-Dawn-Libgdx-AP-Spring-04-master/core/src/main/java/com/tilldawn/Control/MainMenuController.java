package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.SaveData;
import com.tilldawn.Model.enums.monsterTypes;
import com.tilldawn.View.*;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view == null) return;

        if (view.getPlayButton().isChecked()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }

        if (view.getSettingsButton().isChecked()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new SettingsView(Main.getMain().getSettingsController(), GameAssetManager.getGameAssetManager().getSkin()));
        }

        if (view.getProfileButton().isChecked()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new ProfileView(new ProfileController(), GameAssetManager.getGameAssetManager().getSkin()));
        }

        if (view.getScoreboardButton().isChecked()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new ScoreboardView(new ScoreboardController(), GameAssetManager.getGameAssetManager().getSkin()));
        }

        if (view.getContinueButton().isChecked()) {
            view.getContinueButton().setChecked(false);
            loadGame();
        }

        if (view.getTalentButton().isChecked()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new TalentView(GameAssetManager.getGameAssetManager().getSkin()));
        }

        if (view.getLogoutButton().isChecked()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }

        if (view.getRegisterButton().isChecked()) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new RegisterMenuViews(new RegisterMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }

        if (view.getExitButton().isChecked()) {
            System.exit(0);
        }
    }

    private void loadGame() {
        String playerName = Main.currentUser.getUsername();
        FileHandle file = Gdx.files.local(playerName + ".json");

        try {
            SaveData data = new Gson().fromJson(file.readString(), SaveData.class);
            String fileName = data.NameOfFile + ".png";
            Texture playerTexture = new Texture(Gdx.files.internal(fileName));

            Player player = new Player(playerTexture);
            player.setCharacterName(data.playerName);
            player.setPosX(data.playerX);
            player.setPosY(data.playerY);
            player.setExp(data.exp);
            player.setLevel(data.level);
            player.setPlayerHealth(data.playerHealth);
            player.setGainAbilities(data.abilities);

            MonsterController monsterController = new MonsterController();
            monsterController.setTreesSpawned(true);

            GameController gameController = new GameController(data.duration, data.weapon);
            String newFileName = fileName.replaceAll(".png", "");

            PlayerController playerController = new PlayerController(player, newFileName, gameController);
            gameController.setPlayerController(playerController);

            if (data.treeMonsterCount > 0) {
                ArrayList<Monster> treeMonsters = new ArrayList<>(data.treeMonsterCount);
                for (int i = 0; i < data.treeMonsterCount; i++) {
                    Monster m = new Monster(monsterTypes.TreeMonster);
                    m.setPosX(data.treeMonsterPositionX.get(i));
                    m.setPosY(data.treeMonsterPositionY.get(i));
                    treeMonsters.add(m);
                }
                gameController.getMonsterController().setTreeMonsters(treeMonsters);
            }

            if (data.elderMonsterAppeared) {
                Monster elder = new Monster(monsterTypes.Elder);
                elder.setPosX(data.elderMonsterPositionX);
                elder.setPosY(data.elderMonsterPositionY);

                if (gameController.getMonsterController().getElderMonster() == null)
                    gameController.getMonsterController().setElderMonster(new ArrayList<>());

                gameController.getMonsterController().getElderMonster().add(elder);
            }

            long time = data.remainingTimeMillis;
            if (time <= 1000) {
                System.out.println("Saved game has no remaining time. Resetting to minimum value.");
                time = 12_000;
            }
            gameController.setRemainingTimeMillis(time);
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new GameView(gameController,
                GameAssetManager.getGameAssetManager().getSkin(), playerTexture, newFileName));
            gameController.getPlayerController().setPlayer(player);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
