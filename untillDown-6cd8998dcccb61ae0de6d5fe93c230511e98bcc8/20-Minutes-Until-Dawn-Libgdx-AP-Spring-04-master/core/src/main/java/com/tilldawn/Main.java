package com.tilldawn;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Control.RegisterMenuController;
import com.tilldawn.Control.SettingsController;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.View.RegisterMenuViews;

import java.util.HashMap;

public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;
    public static HashMap<String, User> usersDB = new HashMap<>();
    public static User currentUser = null;
    private static SettingsController settings;

    @Override
    public void create() {
        setCustomCursor();
        main = this;
        settings = new SettingsController();
        batch = new SpriteBatch();
        getMain().setScreen(new RegisterMenuViews(new RegisterMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    public SettingsController getSettingsController() {
        return settings;
    }

    private void setCustomCursor() {
        Pixmap original = new Pixmap(Gdx.files.internal("shooting.png"));

        int scale = 8;
        int newWidth = Math.max(1, original.getWidth() / scale);
        int newHeight = Math.max(1, original.getHeight() / scale);

        Pixmap scaled = new Pixmap(newWidth, newHeight, original.getFormat());
        scaled.drawPixmap(
            original,
            0, 0, original.getWidth(), original.getHeight(),
            0, 0, newWidth, newHeight
        );

        Cursor cursor = Gdx.graphics.newCursor(scaled, newWidth / 2, newHeight / 2);
        Gdx.graphics.setCursor(cursor);

        original.dispose();
        scaled.dispose();
    }

}
