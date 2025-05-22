package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.View.GameView;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.PreGameMenuView;
import com.tilldawn.View.RegisterMenuViews;

import java.util.HashMap;

import static com.tilldawn.Main.usersDB;

public class RegisterMenuController {

    private RegisterMenuViews view;
    private boolean registrationSuccess = false;
    private boolean isGuest = false;

    public void setView(RegisterMenuViews view) {
        this.view = view;
        addListeners();
    }

    private void addListeners() {
        view.getRegisterButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = view.getUsernameField().getText().trim();
                String password = view.getPasswordField().getText().trim();
                String securityAnswer = view.getSecurityQuestionField().getText().trim();
                String character = view.getSelectedCharacterName();

                if (username.isEmpty() || password.isEmpty() || securityAnswer.isEmpty()) {
                    view.setFeedbackText("All fields must be filled.");
                } else if (Gdx.files.local("users/" + username + ".json").exists()) {
                    view.setFeedbackText("Username already taken.");
                } else if (password.length() < 8) {
                    view.setFeedbackText("Password must be at least 8 characters.");
                } else if (!password.matches(".*\\d.*")) {
                    view.setFeedbackText("Password must include a number.");
                } else if (!password.matches(".*[A-Z].*")) {
                    view.setFeedbackText("Password must include an uppercase letter.");
                } else if (!password.matches(".*[()\\*$%@#].*")) {
                    view.setFeedbackText("Password must include a special character: ()*$%@#");
                } else {
                    User newUser = new User(username, password, securityAnswer, character);
                    usersDB.put(username, newUser);
                    saveUserToJson(newUser); // Save to JSON
                    Gdx.app.log("Register", "Registered: " + newUser);
                    view.setFeedbackText("Registration successful!");
                    registrationSuccess = true;
                    isGuest = false;
                    handleRegisterMenuButtons();
                }
            }
        });

        view.getGuestLoginButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("GuestLogin", "Guest user entered");
                isGuest = true;
                registrationSuccess = true;
                handleGuestLogin();
            }
        });

        view.getLoginButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("LoginButton", "User pressed Login button from RegisterMenu");
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        });

    }

    private void handleGuestLogin() {
        if (view != null && registrationSuccess) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            registrationSuccess = false;
        }
    }

    public void handleRegisterMenuButtons() {
        if (view != null && registrationSuccess) {
            Main.getMain().getScreen().dispose();

            if (isGuest)
                Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            else
                Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            registrationSuccess = false;
        }
    }

    private void saveUserToJson(User user) {
        Json json = new Json();
        FileHandle file = Gdx.files.local("users/" + user.getUsername() + ".json");

        // Make sure the directory exists
        if (!file.parent().exists()) {
            file.parent().mkdirs();
        }

        file.writeString(json.prettyPrint(user), false);
    }
}
