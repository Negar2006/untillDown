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
    private final TextButton registerButton;
    private final TextButton exitButton;
    private Image avatarImage;
    private String avatarPath;
    private final Label usernameLabel;
    private final Label numberOfKilling;
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
        this.registerButton = new TextButton("Register", skin);
        this.exitButton = new TextButton("Exit", skin);
        this.talentButton = new TextButton("Talent", skin);
        setAvatar(Main.currentUser.getSelectedCharacter());
        this.usernameLabel = new Label("Username: " + Main.currentUser.getUsername(), skin);
        this.scoreLabel = new Label("Score: " + Main.currentUser.getScore(), skin);
        this.numberOfKilling = new Label("Kills: " + Main.currentUser.getNumberOfKillingMonster(), skin);

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.top().pad(15);

        table.add(gameTitle).colspan(3).padBottom(30).row();

        if (avatarImage != null) {
            table.add(avatarImage).size(150).padBottom(20).colspan(3).row();
        }

        updateUserInfo();

        table.add(usernameLabel).colspan(3).padBottom(20).row();
        table.add(scoreLabel).padBottom(30).colspan(3).row();
        table.add(numberOfKilling).padBottom(20).colspan(3).row();

        table.add(playButton).width(300).height( 120).pad(15);
        table.add(settingsButton).width(300).height( 120).pad(15);
        table.add(profileButton).width(300).height( 120).pad(15).row();

        table.add(scoreboardButton).width(300).height( 120).pad(15);
        table.add(continueButton).width(300).height( 120).pad(15);
        table.add(logoutButton).width(300).height( 120).pad(15).row();

        table.add(talentButton).width(300).height( 120).pad(15);
        table.add(registerButton).width(300).height( 120).pad(15);
        table.add(exitButton).width(300).height( 120).pad(15).row();

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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Label getGameTitle() {
        return gameTitle;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public Image getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(Image avatarImage) {
        this.avatarImage = avatarImage;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public void setAvatar(String path) {
        this.avatarPath = path;
        Texture avatarTexture = new Texture(Gdx.files.internal(path));
        avatarImage = new Image(avatarTexture);
        avatarImage.setSize(100, 100);
    }

    public void updateUserInfo() {
        usernameLabel.setText("Username: " + Main.currentUser.getUsername());
        scoreLabel.setText("Score: " + Main.currentUser.getScore());
        numberOfKilling.setText("Kills: " + Main.currentUser.getNumberOfKillingMonster());
    }

}
