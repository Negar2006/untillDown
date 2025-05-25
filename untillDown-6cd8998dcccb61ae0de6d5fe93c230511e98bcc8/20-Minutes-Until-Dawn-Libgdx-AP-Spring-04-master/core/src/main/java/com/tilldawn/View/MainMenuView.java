package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Main;

public class MainMenuView implements Screen {
    private Stage stage;
    private final TextButton playButton;
    private final Label gameTitle;
    private final TextField field;
    public Table table;
    private final TextButton settingsButton;
    private final TextButton profileButton;
    private final TextButton scoreboardButton;
    private final TextButton continueButton;
    private final TextButton logoutButton;
    private final TextButton talentButton;
    private Image avatarImage;
    private String avatarPath;
    private final Label usernameLabel;
    private final Label scoreLabel;
    private final MainMenuController controller;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.controller = controller;
        this.playButton = new TextButton("Play", skin);
        this.gameTitle = new Label("Game Title", skin);
        this.field = new TextField("Player Name", skin);
        this.table = new Table();
        this.settingsButton = new TextButton("Settings", skin);
        this.profileButton = new TextButton("Profile", skin);
        this.scoreboardButton = new TextButton("Scoreboard", skin);
        this.continueButton = new TextButton("Continue", skin);
        this.logoutButton = new TextButton("Logout", skin);
        this.talentButton = new TextButton("Talent", skin);
        setAvatar(Main.currentUser.getSelectedCharacter());
        this.usernameLabel = new Label("Username: Guest", skin);
        this.scoreLabel = new Label("Score: 0", skin);


        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.top().pad(15);
        table.add(gameTitle).colspan(2).padBottom(20).row();
        if (avatarImage != null) {
            table.add(avatarImage).size(100).padBottom(10).row();
        }
        table.add(usernameLabel);
        table.row();
        table.add(scoreLabel).padBottom(20);
        table.row();
        table.add(playButton).width(300).pad(5);
        table.row();
        table.add(continueButton).width(300).pad(5);
        table.row();
        table.add(talentButton).width(300).pad(5);
        table.row();
        table.add(settingsButton).width(300).pad(5);
        table.row();
        table.add(profileButton).width(300).pad(5);
        table.row();
        table.add(scoreboardButton).width(300).pad(5);
        table.row();
        table.add(logoutButton).width(300).pad(5);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleMainMenuButtons();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public TextButton getPlayButton() {
        return playButton;
    }

    public TextField getField() {
        return field;
    }

    public MainMenuController getController() {
        return controller;
    }

    public TextButton getSettingsButton() {
        return settingsButton;
    }

    public TextButton getProfileButton() {
        return profileButton;
    }

    public TextButton getScoreboardButton() {
        return scoreboardButton;
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public TextButton getLogoutButton() {
        return logoutButton;
    }

    public TextButton getTalentButton() {
        return talentButton;
    }

    public void setAvatar(String path) {
        this.avatarPath = path;
        Texture avatarTexture = new Texture(Gdx.files.internal(path));
        avatarImage = new Image(avatarTexture);
        avatarImage.setSize(100, 100);
    }
}
