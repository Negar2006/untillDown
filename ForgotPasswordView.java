package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.ForgotPasswordController;
import com.tilldawn.Model.GameAssetManager;

public class ForgotPasswordView extends ScreenAdapter {
    private final Stage stage;
    private final ForgotPasswordController controller;

    private final TextField usernameField;
    private final Label securityQuestionLabel;
    private final TextField answerField;
    private final TextField newPasswordField;
    private final TextButton submitButton;
    private final Label feedbackLabel;
    private final TextButton backToLoginButton;

    public ForgotPasswordView(ForgotPasswordController controller) {
        this.controller = controller;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = GameAssetManager.getGameAssetManager().getSkin();

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10);

        // Username input
        usernameField = new TextField("", skin);
        table.add(new Label("Username:", skin));
        table.add(usernameField).row();

        // Security question
        securityQuestionLabel = new Label("What is your favorite character?", skin);
        table.add(new Label("Question:", skin));
        table.add(securityQuestionLabel).row();

        // Answer input
        answerField = new TextField("", skin);
        table.add(new Label("Answer:", skin));
        table.add(answerField).row();

        // New password input
        newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordCharacter('*');
        newPasswordField.setPasswordMode(true);
        table.add(new Label("New Password:", skin));
        table.add(newPasswordField).row();

        backToLoginButton = new TextButton("Back to Login", skin);
        backToLoginButton.setVisible(false);

        // Submit button
        submitButton = new TextButton("Submit", skin);
        table.add(submitButton).colspan(2).center().row();

        // Feedback label
        feedbackLabel = new Label("", skin);
        table.add(feedbackLabel).colspan(2).center();

        table.add(backToLoginButton).colspan(2).center().row();

        stage.addActor(table);
        controller.setView(this); // Let controller hook up
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        controller.render(delta);
    }

    @Override
    public void dispose() {
        controller.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public Label getSecurityQuestionLabel() {
        return securityQuestionLabel;
    }

    public TextField getAnswerField() {
        return answerField;
    }

    public TextField getNewPasswordField() {
        return newPasswordField;
    }

    public TextButton getSubmitButton() {
        return submitButton;
    }

    public void setFeedbackText(String text) {
        feedbackLabel.setText(text);
    }

    public void setSecurityQuestion(String question) {
        securityQuestionLabel.setText(question);
    }

    public TextButton getBackToLoginButton() {
        return backToLoginButton;
    }

    public void setBackToLoginButtonVisible(boolean visible) {
        backToLoginButton.setVisible(visible);
    }
}
