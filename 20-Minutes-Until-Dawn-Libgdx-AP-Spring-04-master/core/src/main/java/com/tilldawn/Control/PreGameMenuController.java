package com.tilldawn.Control;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.View.GameView;
import com.tilldawn.View.PreGameMenuView;

public class PreGameMenuController {

    private PreGameMenuView view;

    public void setView(PreGameMenuView view) {
        this.view = view;
    }

    public void handlePreGameMenuButtons() {
        view.getPlayButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String hero = view.getSelectedHero();
                System.out.println("Selected Hero: " + hero);

                Main.getMain().setScreen(new GameView(new GameController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
    }
}
