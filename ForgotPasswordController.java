package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.View.ForgotPasswordView;
import com.tilldawn.View.LoginMenuView;


public class ForgotPasswordController {
    private ForgotPasswordView view;

    public void setView(ForgotPasswordView view) {
        this.view = view;
        addListeners();
    }

    private void addListeners() {
        view.getSubmitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = view.getUsernameField().getText().trim();
                User user = loadUserFromJson(username);

                if (user == null) {
                    view.setFeedbackText("Username not found.");
                    return;
                }

                String answer = view.getAnswerField().getText().trim();
                if (!user.getSecurityAnswer().equalsIgnoreCase(answer)) {
                    view.setFeedbackText("Incorrect answer to security question.");
                    return;
                }

                String newPassword = view.getNewPasswordField().getText().trim();
                if (newPassword.length() < 8) {
                    view.setFeedbackText("Password must be at least 4 characters.");
                    return;
                }

                user.setPassword(newPassword);
                updateUserFile(user);
                view.setFeedbackText("Password changed successfully!");

                view.setBackToLoginButtonVisible(true);
                view.getSubmitButton().setDisabled(true);

                view.getBackToLoginButton().addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Main.getMain().getScreen().dispose();
                        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                    }
                });
            }
        });
    }

    private void updateUserFile(User user) {
        Json json = new Json();
        FileHandle file = Gdx.files.local("users/" + user.getUsername() + ".json");
        file.writeString(json.prettyPrint(user), false); // false = overwrite
    }


    public void render(float delta) {
        view.getStage().act(delta);
        view.getStage().draw();
    }

    public void dispose() {
        view.getStage().dispose();
    }

    private User loadUserFromJson(String username) {
        FileHandle file = Gdx.files.local("users/" + username + ".json");

        if (!file.exists()) return null;

        Json json = new Json();
        return json.fromJson(User.class, file);
    }
}
