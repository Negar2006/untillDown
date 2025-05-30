package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;

public class TalentView implements Screen {
    private static final float LABEL_PADDING = 5f;
    private static final float BUTTON_PADDING_TOP = 30f;
    private static final float TABLE_PADDING_TOP = 20f;

    private final Stage stage;
    private final Skin skin;
    private final Table mainTable;

    public TalentView(Skin skin) {
        if (skin == null) {
            throw new IllegalArgumentException("Skin cannot be null");
        }
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.mainTable = new Table(skin);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        mainTable.clear();
        mainTable.setFillParent(true);
        mainTable.top().pad(TABLE_PADDING_TOP);

        // نمایش دکمه‌های اصلی
        addMainMenuButton("Hero Stats", this::showHeroStatsPage);
        addMainMenuButton("Key Bindings", this::showKeyBindingsPage);
        addMainMenuButton("Abilities", this::showAbilitiesPage);

        stage.addActor(mainTable);
    }

    // دکمه‌های منو برای نمایش هر بخش
    private void addMainMenuButton(String sectionTitle, Runnable action) {
        TextButton button = new TextButton(sectionTitle, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                action.run();
            }
        });
        mainTable.add(button).expandX().padTop(20f).row();
    }

    private void showHeroStatsPage() {
        Table heroStatsTable = new Table(skin);
        heroStatsTable.clear();
        addTableTitle(heroStatsTable, "Hero Stats:");

        addTableRow(heroStatsTable, "SHANA", "Health: 40, Speed: 80", "Shana is a balanced hero.");
        addTableRow(heroStatsTable, "SCARLET", "Health: 30, Speed: 100", "Scarlet is a fast attacker.");
        addTableRow(heroStatsTable, "LILITH", "Health: 50, Speed: 60", "Lilith is a powerful hero.");
        addTableRow(heroStatsTable, "DASHER", "Health: 20, Speed: 200", "Dasher is extremely fast.");
        addTableRow(heroStatsTable, "DIAMOND", "Health: 70, Speed: 20", "Diamond is a tank with high health.");

        addBackToMainMenuButton(heroStatsTable);

        mainTable.clearChildren();
        mainTable.add(heroStatsTable).expandX().top().padTop(TABLE_PADDING_TOP).row();
        stage.addActor(mainTable);
    }

    private void showKeyBindingsPage() {
        Table keyBindingsTable = new Table(skin);
        keyBindingsTable.clear();
        addTableTitle(keyBindingsTable, "Key Bindings:");

        addTableRow(keyBindingsTable, "N", "Return to Main Menu", "Returns to the main menu.");
        addTableRow(keyBindingsTable, "SPACE", "Toggle Auto Aim", "Activates or deactivates auto aim.");
        addTableRow(keyBindingsTable, "K", "Kill Monster", "Kills the monster in the vicinity.");
        addTableRow(keyBindingsTable, "E", "Kill Eye Bat", "Kills the eye bat enemy.");
        addTableRow(keyBindingsTable, "T", "change time", "Moves the time forward one minute.");
        addTableRow(keyBindingsTable, "B", "add ability", "change ability one level");
        addBackToMainMenuButton(keyBindingsTable);

        mainTable.clearChildren();
        mainTable.add(keyBindingsTable).expandX().top().padTop(TABLE_PADDING_TOP).row();
        stage.addActor(mainTable);
    }

    private void showAbilitiesPage() {
        Table abilitiesTable = new Table(skin);
        abilitiesTable.clear();
        addTableTitle(abilitiesTable, "Abilities Explained:");

        addTableRow(abilitiesTable, "VITALITY", "Restores +20 HP", "Restores 20 health points.");
        addTableRow(abilitiesTable, "SPEEDY", "Doubles player's speed", "Increases the player's speed for 10 seconds.");
        addTableRow(abilitiesTable, "AMOCREASE", "Adds 5 extra ammo", "Adds 5 extra ammo to your weapon.");
        addTableRow(abilitiesTable, "PROCREASE", "Increases projectile count", "Adds one extra projectile to your weapon.");
        addTableRow(abilitiesTable, "DAMAGER", "Increases weapon damage", "Increases weapon damage by 25%.");

        addBackToMainMenuButton(abilitiesTable);

        mainTable.clearChildren();
        mainTable.add(abilitiesTable).expandX().top().padTop(TABLE_PADDING_TOP).row();
        stage.addActor(mainTable);
    }

    private void addTableTitle(Table table, String title) {
        table.add(new Label(title, skin)).colspan(2).padBottom(LABEL_PADDING).row();
    }

    private void addTableRow(Table table, String name, String info, String description) {
        TextButton rowButton = new TextButton(name + ": " + info, skin);
        rowButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showInfoDialog(name, description);
            }
        });
        table.add(rowButton).expandX().padTop(15f).row();
    }

    private void showInfoDialog(String name, String description) {
        Dialog dialog = new Dialog(name, skin);
        dialog.text(description);
        TextButton closeButton = new TextButton("Back to Main Menu", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });
        dialog.button(closeButton);
        dialog.show(stage);
    }

    // اضافه کردن دکمه "بازگشت به منوی اصلی"
    private void addBackToMainMenuButton(Table table) {
        TextButton backToMainMenuButton = new TextButton("Back to Main Menu", skin);
        backToMainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });
        table.add(backToMainMenuButton).colspan(2).padTop(BUTTON_PADDING_TOP).row();
    }

    @Override
    public void render(float delta) {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
