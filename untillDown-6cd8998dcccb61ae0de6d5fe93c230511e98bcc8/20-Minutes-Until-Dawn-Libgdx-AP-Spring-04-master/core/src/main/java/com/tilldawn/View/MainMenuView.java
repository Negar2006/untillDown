package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.SettingsController;
import com.tilldawn.Main;
import com.tilldawn.Model.enums.Language;

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
        this.playButton = new TextButton("", skin);
        this.gameTitle = new Label("", skin);
        this.field = new TextField("", skin);
        this.table = new Table();
        this.settingsButton = new TextButton("", skin);
        this.profileButton = new TextButton("", skin);
        this.scoreboardButton = new TextButton("", skin);
        this.continueButton = new TextButton("", skin);
        this.logoutButton = new TextButton("", skin);
        this.registerButton = new TextButton("", skin);
        this.exitButton = new TextButton("", skin);
        this.talentButton = new TextButton("", skin);
        setAvatar(Main.currentUser.getSelectedCharacter());
        this.usernameLabel = new Label("", skin);
        this.scoreLabel = new Label("", skin);
        this.numberOfKilling = new Label("", skin);

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/new.fnt"));

        gameTitle.setStyle(new Label.LabelStyle(font, gameTitle.getStyle().fontColor));
        usernameLabel.setStyle(new Label.LabelStyle(font, usernameLabel.getStyle().fontColor));
        scoreLabel.setStyle(new Label.LabelStyle(font, scoreLabel.getStyle().fontColor));
        numberOfKilling.setStyle(new Label.LabelStyle(font, numberOfKilling.getStyle().fontColor));

        TextButton[] buttons = {
            playButton, settingsButton, profileButton,
            scoreboardButton, continueButton, logoutButton,
            talentButton, registerButton, exitButton
        };
        for (TextButton btn : buttons) {
            TextButton.TextButtonStyle style = btn.getStyle();
            style.font = font;
            btn.setStyle(style);
        }

        TextField.TextFieldStyle fieldStyle = field.getStyle();
        fieldStyle.font = font;
        field.setStyle(fieldStyle);

        table.setFillParent(true);
        table.top().pad(15);

        updateLanguageTexts(); // اینجا متن‌ها رو بر اساس زبان فعلی تنظیم می‌کنیم
        updateUserInfo();

        table.clear(); // جدول رو پاک می‌کنیم که از رندر قبلی چیزی نمونده باشه

        table.add(gameTitle).colspan(3).padBottom(30).row();

        if (avatarImage != null) {
            table.add(avatarImage).size(150).padBottom(20).colspan(3).row();
        }

        table.add(usernameLabel).colspan(3).padBottom(20).row();
        table.add(scoreLabel).padBottom(30).colspan(3).row();
        table.add(numberOfKilling).padBottom(20).colspan(3).row();

        table.add(playButton).width(300).height(120).pad(15);
        table.add(settingsButton).width(300).height(120).pad(15);
        table.add(profileButton).width(300).height(120).pad(15).row();

        table.add(scoreboardButton).width(300).height(120).pad(15);
        table.add(continueButton).width(300).height(120).pad(15);
        table.add(logoutButton).width(300).height(120).pad(15).row();

        table.add(talentButton).width(300).height(120).pad(15);
        table.add(registerButton).width(300).height(120).pad(15);
        table.add(exitButton).width(300).height(120).pad(15).row();

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
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

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
        usernameLabel.setText(
            "username : " + Main.currentUser.getUsername()
        );
        scoreLabel.setText(
            "score : " + Main.currentUser.getScore()
        );
        numberOfKilling.setText(
            "kills : " + Main.currentUser.getNumberOfKillingMonster()
        );
    }

    public void updateLanguageTexts() {
        Language lang =Main.getMain().getSettingsController().getCurrentLanguage();

        if (lang == Language.FRENCH) {
            gameTitle.setText("Titre du Jeu");
            playButton.setText("Jouer");
            settingsButton.setText("Paramètres");
            profileButton.setText("Profil");
            scoreboardButton.setText("Classement");
            continueButton.setText("Continuer");
            logoutButton.setText("Déconnexion");
            talentButton.setText("Talent");
            registerButton.setText("S'inscrire");
            exitButton.setText("Quitter");

            updateUserInfo();
        } else {
            gameTitle.setText("Game Title");
            playButton.setText("Play");
            settingsButton.setText("Settings");
            profileButton.setText("Profile");
            scoreboardButton.setText("Scoreboard");
            continueButton.setText("Continue");
            logoutButton.setText("Logout");
            talentButton.setText("Talent");
            registerButton.setText("Register");
            exitButton.setText("Exit");

            updateUserInfo();
        }
    }
    public void refreshView() {
        updateLanguageTexts();
        updateUserInfo();
    }
}
