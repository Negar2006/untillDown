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
import com.tilldawn.Model.enums.weaponType;

import java.util.ArrayList;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {
            if (view.getPlayButton().isChecked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }

            if (view.getSettingsButton().isChecked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new SettingsView(new SettingsController(), GameAssetManager.getGameAssetManager().getSkin()));
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
                view.getContinueButton().setChecked(false);  // Prevent multiple clicks

                String playerName = Main.currentUser.getUsername();
                FileHandle file = Gdx.files.local(playerName + ".json");

                if (!file.exists()) {
                    System.out.println("Error: No saved game found!");
                    return;
                }

                try {
                    SaveData data = new Gson().fromJson(file.readString(), SaveData.class);

                    String fileName = data.NameOfFile + ".png";
                    Texture playerTexture = new Texture(Gdx.files.internal(fileName));

                    Player player = new Player(playerTexture);
                    player.setCharacterName(data.playerName);
                    player.setPosX(data.playerX);
                    player.setPosY(data.playerY);
                    player.setPlayerHealth(data.playerHealth);

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

                        if (gameController.getMonsterController().getElderMonster() == null) {
                            gameController.getMonsterController().setElderMonster(new ArrayList<>());
                        }

                        gameController.getMonsterController().getElderMonster().add(elder);
                    }

                    // Switch to GameView
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new GameView(gameController,
                        GameAssetManager.getGameAssetManager().getSkin(),
                        playerTexture,
                        newFileName));
                    gameController.getPlayerController().setPlayer(player);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error loading save data!");
                }
            }

            if (view.getTalentButton().isChecked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new TalentView(new TalentController(), GameAssetManager.getGameAssetManager().getSkin()));
            }

            if (view.getLogoutButton().isChecked()) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        }
    }

}
