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
import com.tilldawn.View.GameView;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.MainMenuView;

public class LoginMenuController {
    private LoginMenuView view;

    public void setView(LoginMenuView view) {
        this.view = view;
        addListeners();
    }

    private void addListeners() {
        view.getLoginButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleLogin();
            }
        });

        view.getForgotPasswordButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleForgotPassword();
            }
        });
    }

    private void handleLogin() {
        String username = view.getUsernameField().getText().trim();
        String password = view.getPasswordField().getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            view.setFeedbackText("Please enter both fields.");
            return;
        }
        User user = loadUserFromJson(username);
        if (user == null) {
            view.setFeedbackText("Username not found.");
        } else if (!user.getPassword().equals(password)) {
            view.setFeedbackText("Incorrect password.");
        } else {
            view.setFeedbackText("Login successful!");
            Gdx.app.log("Login", "User logged in: " + username + ", Character: " + user.getSelectedCharacter());
            Main.getMain().getScreen().dispose();
            Main.currentUser = user;
            Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        }
    }

    private void handleForgotPassword() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ForgotPasswordView(new ForgotPasswordController()));
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
