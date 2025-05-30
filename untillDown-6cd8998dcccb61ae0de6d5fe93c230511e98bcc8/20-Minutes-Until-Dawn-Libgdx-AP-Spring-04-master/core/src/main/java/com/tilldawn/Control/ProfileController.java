package com.tilldawn.Control;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.tilldawn.Main;

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

    public void changeUsername(String newUsername) {
        if (Main.currentUser == null) return;

        existingUsernames.remove(Main.currentUser.getUsername());

        Main.currentUser.setUsername(newUsername);

        existingUsernames.add(newUsername);

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

//        Main.currentUser.saveToJson();
    }

}
