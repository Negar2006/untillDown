package com.tilldawn.Control;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.GameView;
import com.tilldawn.View.PreGameMenuView;

public class PreGameMenuController {

    private PreGameMenuView view;
    private int selectedCharacter = -1;

    public void setView(PreGameMenuView view) {
        this.view = view;
    }

    public void onCharacterSelected(int index) {
        selectedCharacter = index;
        System.out.println("Character selected: " + selectedCharacter);
    }

    public void handlePreGameMenuButtons() {
        view.getPlayButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedCharacter == -1) {
                    System.out.println("Please select a character first!");
                    return;
                }
                System.out.println("Starting game with character " + selectedCharacter);
                Main.getMain().setScreen(new GameView(selectedCharacter, new GameController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
    }
}
