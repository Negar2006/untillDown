package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.GameView;
import com.tilldawn.View.PreGameMenuView;

public class PreGameMenuController {

    private PreGameMenuView view;
    private int selectedCharacter = -1;
    private static final String[] CHARACTER_NAMES = {"SHANA", "DASHER", "DIAMOND", "LILITH", "SCARLET"};

    public void setView(PreGameMenuView view) {
        this.view = view;
    }

    public void onCharacterSelected(int index) {
        selectedCharacter = index;
        System.out.println("Character selected: " + CHARACTER_NAMES[index]);
    }

    public void handlePreGameMenuButtons() {
        view.getPlayButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (selectedCharacter < 0) {
                    System.out.println("No character selected!");
                    return;
                }

                String fileName = CHARACTER_NAMES[selectedCharacter] + ".png";
                Texture playerTexture = new Texture(Gdx.files.internal(fileName));

                int duration = view.getSelectedGameDuration();

                String weapon = view.getSelectedWeapon();

                System.out.println("Starting game with duration: " + duration + " minutes and weapon: " + weapon);

                GameController gameController = new GameController(duration, weapon);

                Main.getMain().setScreen(new GameView(gameController,
                    GameAssetManager.getGameAssetManager().getSkin(),
                    playerTexture,
                    CHARACTER_NAMES[selectedCharacter]));
            }
        });
    }
}
