package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.LoginMenuController;

public class LoginMenuView implements Screen {

    private Stage stage;
    private final TextField usernameField;
    private final TextField passwordField;
    private final Label feedbackLabel;
    private final TextButton loginButton;
    private final TextButton forgotPasswordButton;
    private final Skin skin;
    private final Table table;

    public LoginMenuView(LoginMenuController controller, Skin skin) {
        this.skin = skin;
        stage = new Stage(new ScreenViewport());

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        usernameField = new TextField("", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        feedbackLabel = new Label("", skin);
        loginButton = new TextButton("Login", skin);
        forgotPasswordButton = new TextButton("Forgot Password?", skin);

        table.defaults().pad(10);
        table.add(new Label("Username:", skin)).left();
        table.add(usernameField).width(200).row();
        table.add(new Label("Password:", skin)).left();
        table.add(passwordField).width(200).row();
        table.add(loginButton).colspan(2).center().row();
        table.add(forgotPasswordButton).colspan(2).center().row();
        table.add(feedbackLabel).colspan(2).center();
        this.table = new Table();
        controller.setView(this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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

    public Stage getStage() {
        return stage;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextButton getLoginButton() {
        return loginButton;
    }

    public TextButton getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    public void setFeedbackText(String text) {
        feedbackLabel.setText(text);
    }
}
