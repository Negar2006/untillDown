package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.RegisterMenuController;
import com.tilldawn.Model.GameAssetManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisterMenuViews implements Screen {

    private Stage stage;
    private final Skin skin;
    private final RegisterMenuController controller;

    private final Label gameTitle;
    private final TextField usernameField;
    private final TextField passwordField;
    private final TextField securityQuestionField;
    private final TextButton registerButton;
    private final TextButton guestLoginButton;
    private final TextButton loginButton;
    private final Label feedbackLabel;
    private final Table table;
    private final Label securityQuestionTitle;

    private String selectedCharacterName;
    private Texture selectedTexture;
    private final List<Texture> loadedTextures = new ArrayList<>();

    private final String[] characterImagePaths = {
        "2/Abby.png",
        "2/Dasher.png",
        "2/Diamond.png",
        "2/Hastur.png",
        "2/Hina.png",
        "2/Lilith.png",
        "2/Luna.png",
        "2/Raven.png",
        "2/Scarlett.png",
        "2/Shana.png",
        "2/Spark.png",
        "2/Yuki.png"
    };

    public RegisterMenuViews(RegisterMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.gameTitle = new Label("Register Menu", skin);
        this.gameTitle.setFontScale(2f);
        this.securityQuestionTitle = new Label("What is your favorite character?", skin);
        this.securityQuestionTitle.setColor(1f, 1f, 0f, 1f);
        this.usernameField = new TextField("", skin);
        this.securityQuestionField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.passwordField.setPasswordMode(true);
        this.passwordField.setPasswordCharacter('*');
        this.registerButton = new TextButton("Register", skin);
        this.guestLoginButton = new TextButton("Login as Guest", skin);
        this.loginButton = new TextButton("Login", skin);

        this.feedbackLabel = new Label("", skin);
        this.table = new Table();
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center();
        table.defaults().pad(10);

        table.add(gameTitle).colspan(2).row();
        table.add(new Label("Username:", skin));
        table.add(usernameField).width(200).row();
        table.add(new Label("Password:", skin));
        table.add(passwordField).width(200).row();
        table.add(securityQuestionTitle).colspan(2).row();
        table.add(new Label("Answer:", skin));
        table.add(securityQuestionField).width(200);

        Random random = new Random();
        int index = random.nextInt(characterImagePaths.length);
        selectedCharacterName = characterImagePaths[index];
        selectedTexture = new Texture(Gdx.files.internal(selectedCharacterName));
        loadedTextures.add(selectedTexture);

        Image selectedImage = new Image(new TextureRegionDrawable(new TextureRegion(selectedTexture)));
        selectedImage.setSize(100, 100);
        String characterNameWithoutExtension = selectedCharacterName.replace(".png", "").substring(selectedCharacterName.lastIndexOf("/") + 1);
        Label selectedLabel = new Label("Selected Character: " + characterNameWithoutExtension, skin);

        Table heroTable = new Table();
        table.add(heroTable).colspan(2).row();
        table.add(selectedImage).colspan(2).padTop(10).row();
        table.add(selectedLabel).colspan(2).row();
        float buttonWidth = 400f;
        float buttonHeight = 100f;
        Table buttonRow = new Table();
        buttonRow.add(registerButton).width(buttonWidth).height(buttonHeight).padRight(20);
        buttonRow.add(guestLoginButton).width(buttonWidth).height(buttonHeight);
        buttonRow.add(loginButton).width(buttonWidth).height(buttonHeight).padLeft(20);

        table.add(buttonRow).colspan(2).center().row();


        table.add(feedbackLabel).colspan(2);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleRegisterMenuButtons();
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
        for (Texture tex : loadedTextures) {
            if (tex != null) tex.dispose();
        }
    }

    public TextButton getRegisterButton() {
        return registerButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public void setFeedbackText(String text) {
        feedbackLabel.setText(text);
    }

    public TextButton getGuestLoginButton() {
        return guestLoginButton;
    }

    public String getSelectedCharacterName() {
        return selectedCharacterName;
    }

    public TextField getSecurityQuestionField() {
        return securityQuestionField;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }
}
