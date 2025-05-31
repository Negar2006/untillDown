package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.User;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.PreGameMenuView;
import com.tilldawn.View.RegisterMenuViews;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static com.tilldawn.Main.usersDB;

public class RegisterMenuController {

    private RegisterMenuViews view;
    private boolean registrationSuccess = false;
    private boolean isGuest = false;
    private final String dbPath = Gdx.files.local("database/users.db").file().getAbsolutePath();

    public void setView(RegisterMenuViews view) {
        this.view = view;
        initializeDatabase();
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
                    User newUser = new User(username, password, securityAnswer, character, 0, 0, 0);
                    usersDB.put(username, newUser);
                    saveUserToJson(newUser);
                    saveUserToDatabase(newUser);
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
        if (!file.parent().exists()) {
            file.parent().mkdirs();
        }
        file.writeString(json.prettyPrint(json.toJson(user)), false);
    }

    private void initializeDatabase() {
        try {
            FileHandle dbDir = Gdx.files.local("database");
            if (!dbDir.exists()) {
                dbDir.mkdirs();
            }
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "username TEXT PRIMARY KEY," +
                "password TEXT NOT NULL," +
                "securityAnswer TEXT NOT NULL," +
                "character TEXT," +
                "score INTEGER)";

            stmt.execute(sql);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveUserToDatabase(User user) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             PreparedStatement pstmt = conn.prepareStatement(
                 "INSERT INTO users(username, password, securityAnswer, character, score) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getSecurityAnswer());
            pstmt.setString(4, user.getSelectedCharacter());
            pstmt.setInt(5, user.getScore());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
