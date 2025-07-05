package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Json;
import com.tilldawn.Main;
import com.tilldawn.Model.User;

import java.util.HashSet;
import java.util.Set;

public class ProfileController {
    private static Set<String> existingUsernames = new HashSet<>();

    public ProfileController() {

        if (Main.currentUser != null) {
            existingUsernames.add(Main.currentUser.getUsername());
        }
    }

    public boolean isUsernameDuplicate(String username) {
        return existingUsernames.contains(username);
    }

    public void changeUsername(String newUsername, Stage stage, Skin skin) {
        if (Main.currentUser == null) return;

        if (Gdx.files.local("users/" + newUsername + ".json").exists()) {
            showError("Username already exists. Please choose another one.", stage, skin);
            return;
        }
        String oldUsername = Main.currentUser.getUsername();

        User user = loadUserFromJson(oldUsername);
        if (user == null) return;

        existingUsernames.remove(oldUsername);

        user.setUsername(newUsername);
        Main.currentUser.setUsername(newUsername);
        existingUsernames.add(newUsername);

        FileHandle oldFile = Gdx.files.local("users/" + oldUsername + ".json");
        if (oldFile.exists()) oldFile.delete();

        updateUserFile(user);
        showInfo("Username changed to "+ newUsername+" successfully!", stage, skin);
    }

    public void showError(String message, Stage stage, Skin skin) {
        Dialog dialog = new Dialog("Error", skin);
        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
    }

    public void showInfo(String message, Stage stage, Skin skin) {
        Dialog dialog = new Dialog("Info", skin);
        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
    }

    public void changeAvatar(String avatarPath) {
        if (Main.currentUser != null) {
            Main.currentUser.setSelectedCharacter(avatarPath);

            User user = loadUserFromJson(Main.currentUser.getUsername());

            user.setSelectedCharacter(avatarPath);
            updateUserFile(user);
        }
    }

    public void deleteCurrentUser() {
        if (Main.currentUser == null) return;

        String username = Main.currentUser.getUsername();
        java.io.File userFile = new java.io.File("users/" + username + ".json");

        if (userFile.exists()) {
            userFile.delete();
        }

        existingUsernames.remove(username);
        Main.currentUser = null;
    }

    public void changePassword(String newPassword) {
        if (Main.currentUser == null) return;

        Main.currentUser.setPassword(newPassword);
        User user = loadUserFromJson(Main.currentUser.getUsername());

        user.setPassword(newPassword);
        updateUserFile(user);
    }

    private void updateUserFile(User user) {
        Json json = new Json();
        FileHandle file = Gdx.files.local("users/" + user.getUsername() + ".json");
        file.writeString(json.prettyPrint(user), false); // false = overwrite
    }

    private User loadUserFromJson(String username) {
        FileHandle file = Gdx.files.local("users/" + username + ".json");

        if (!file.exists()) return null;

        Json json = new Json();
        return json.fromJson(User.class, file);
    }

}
