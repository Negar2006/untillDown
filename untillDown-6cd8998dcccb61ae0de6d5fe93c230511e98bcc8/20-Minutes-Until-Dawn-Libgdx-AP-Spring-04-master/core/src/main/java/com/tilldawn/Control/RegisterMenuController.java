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
                handleRegister();
            }
        });

        view.getGuestLoginButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleGuestLoginClick();
            }
        });

        view.getLoginButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleLoginRedirect();
            }
        });
    }

    private void handleRegister() {
        String username = view.getUsernameField().getText().trim();
        String password = view.getPasswordField().getText().trim();
        String securityAnswer = view.getSecurityQuestionField().getText().trim();
        String character = view.getSelectedCharacterName();

        String validationMessage = validateRegistrationFields(username, password, securityAnswer);
        if (validationMessage != null) {
            view.setFeedbackText(validationMessage);
            return;
        }

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

    private String validateRegistrationFields(String username, String password, String securityAnswer) {
        if (username.isEmpty() || password.isEmpty() || securityAnswer.isEmpty()) {
            return "All fields must be filled.";
        }
        if (Gdx.files.local("users/" + username + ".json").exists()) {
            return "Username already taken.";
        }
        if (password.length() < 8) {
            return "Password must be at least 8 characters.";
        }
        if (!password.matches(".*\\d.*")) {
            return "Password must include a number.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Password must include an uppercase letter.";
        }
        if (!password.matches(".*[()\\*$%@#].*")) {
            return "Password must include a special character: ()*$%@#";
        }
        return null;
    }

    private void handleGuestLoginClick() {
        Gdx.app.log("GuestLogin", "Guest user entered");
        isGuest = true;
        registrationSuccess = true;
        handleGuestLogin();
    }

    private void handleLoginRedirect() {
        Gdx.app.log("LoginButton", "User pressed Login button from RegisterMenu");
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }


    private void handleGuestLogin() {
        if (view != null && registrationSuccess) {
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(),
                GameAssetManager.getGameAssetManager().getSkin()));
            registrationSuccess = false;
        }
    }

    public void handleRegisterMenuButtons() {
        if (view != null && registrationSuccess) {
            Main.getMain().getScreen().dispose();
            if (isGuest)
                Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()));
            else
                Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()));
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
