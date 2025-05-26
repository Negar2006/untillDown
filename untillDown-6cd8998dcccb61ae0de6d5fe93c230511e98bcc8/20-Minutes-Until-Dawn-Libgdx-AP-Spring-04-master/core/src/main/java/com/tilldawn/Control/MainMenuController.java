package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.SaveData;
import com.tilldawn.Model.enums.monsterTypes;
import com.tilldawn.View.*;

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
                Main.getMain().getScreen().dispose();

                String playerName = Main.currentUser.getUsername();
                FileHandle file = Gdx.files.local(playerName + ".json");

                if (file.exists()) {
                    String jsonContent = file.readString();
                    Json json = new Json();
                    SaveData data = json.fromJson(SaveData.class, jsonContent);

                    Texture playerTexture = new Texture(Gdx.files.internal("DIAMOND.png"));
                    Player player = new Player(playerTexture);
                    player.setCharacterName(data.playerName);
                    player.setPosX(data.playerX);
                    player.setPosY(data.playerY);
                    player.setPlayerHealth(data.playerHealth);

                    GameController gameController = new GameController(data.duration, data.weapon);
                    PlayerController playerController = new PlayerController(player, playerName, gameController);
                    gameController.setPlayerController(playerController);

                    ArrayList<Monster> treeMonsters = new ArrayList<>();
                    for (int i = 0; i < data.treeMonsterCount; i++) {
                        float x = data.treeMonsterPositionX.get(i);
                        float y = data.treeMonsterPositionY.get(i);
                        Monster monster = new Monster(monsterTypes.TreeMonster);
                        monster.setPosX(x);
                        monster.setPosY(y);
                        treeMonsters.add(monster);
                    }
                    gameController.getMonsterController().setTreeMonsters(treeMonsters);

                    Main.getMain().setScreen(new GameView(new GameController(10, "Sward"), GameAssetManager.getGameAssetManager().getSkin(), playerTexture, playerName));

                } else {
                    System.out.println("Error: No saved game found!");
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
