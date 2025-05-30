package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.ProfileController;
import com.tilldawn.Control.RegisterMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;

public class ProfileView implements Screen {

    private final Stage stage;
    private final Skin skin;
    private final ProfileController controller;

    public ProfileView(ProfileController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.pad(20);
        stage.addActor(table);

        TextButton changeUsernameButton = new TextButton("Change Username", skin);
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showUsernameChangeDialog();
            }
        });
        table.add(changeUsernameButton).width(600).pad(10).center().row();

        TextButton changePasswordButton = new TextButton("Change Password", skin);
        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showPasswordChangeDialog();
            }
        });
        table.add(changePasswordButton).width(600).pad(10).center().row();

        TextButton changeAvatarButton = new TextButton("Change Avatar", skin);
        changeAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showAvatarSelectionDialog();
            }
        });
        table.add(changeAvatarButton).width(600).pad(10).center().row();

        TextButton randomPCAvatarButton = new TextButton("Random Avatar from PC", skin);
        randomPCAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    java.io.File downloadsDir = new java.io.File("C:/Users/Negar/Downloads");

                    if (!downloadsDir.exists() || !downloadsDir.isDirectory()) {
                        controller.showError("Downloads folder not found.", stage, skin);
                        return;
                    }

                    java.io.File[] imageFiles = downloadsDir.listFiles((dir, name) -> {
                        String lower = name.toLowerCase();
                        return lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg");
                    });

                    if (imageFiles == null || imageFiles.length == 0) {
                        controller.showError("No image files found in Downloads.", stage, skin);
                        return;
                    }

                    int index = (int) (Math.random() * imageFiles.length);
                    String randomPath = imageFiles[index].getAbsolutePath();

                    controller.changeAvatar(randomPath);
                    controller.showInfo("Random avatar selected:\n" + randomPath, stage, skin);
                } catch (Exception e) {
                    controller.showError("Error selecting random avatar.", stage, skin);
                }
            }
        });
        table.add(randomPCAvatarButton).width(600).pad(10).center().row();

        TextButton selectFromPCButton = new TextButton("Select Avatar from PC", skin);
        selectFromPCButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    java.awt.FileDialog dialog = new java.awt.FileDialog((java.awt.Frame) null, "Choose Avatar");
                    dialog.setMode(java.awt.FileDialog.LOAD);
                    dialog.setVisible(true);

                    String file = dialog.getFile();
                    String dir = dialog.getDirectory();

                    if (file != null && dir != null) {
                        String fullPath = dir + file;
                        if (fullPath.endsWith(".png") || fullPath.endsWith(".jpg") || fullPath.endsWith(".jpeg")) {
                            controller.changeAvatar(fullPath);
                            controller.showInfo("Avatar set to:\n" + fullPath, stage, skin);
                        } else {
                            controller.showError("Please select a valid image file.", stage, skin);
                        }
                    }
                } catch (Exception e) {
                    controller.showError("File selection failed.", stage, skin);
                }
            }
        });
        table.add(selectFromPCButton).width(600).pad(10).center().row();

        TextButton deleteAccountButton = new TextButton("Delete Account", skin);
        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDeleteAccountDialog();
            }
        });
        table.add(deleteAccountButton).width(600).pad(10).center().row();

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                dispose();
            }
        });
        table.add(backButton).width(600).pad(10).center().row();
    }

    private void showUsernameChangeDialog() {
        final TextField usernameField = new TextField("", skin);

        Dialog dialog = new Dialog("Change Username", skin) {
            @Override
            protected void result(Object obj) {
                if ((boolean) obj) {
                    String newUsername = usernameField.getText().trim();
                    if (newUsername.isEmpty()) {
                        controller.showError("Username cannot be empty.", stage, skin);
                    } else if (controller.isUsernameDuplicate(newUsername)) {
                        controller.showError("Username already exists.", stage, skin);
                    } else {
                        controller.changeUsername(newUsername);
                        controller.showInfo("Username changed to " + newUsername, stage, skin);
                        this.hide();
                    }
                }
            }
        };

        dialog.getContentTable().add(new Label("New Username:", skin)).pad(10);
        dialog.getContentTable().row();
        dialog.getContentTable().add(usernameField).width(200).pad(10);
        dialog.button("Confirm", true);
        dialog.button("Cancel", false);
        dialog.show(stage);
    }

    private void showPasswordChangeDialog() {
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        Dialog dialog = new Dialog("Change Password", skin) {
            @Override
            protected void result(Object obj) {
                if ((boolean) obj) {
                    String newPassword = passwordField.getText().trim();
                    if (newPassword.isEmpty()) {
                        controller.showError("Password cannot be empty.", stage, skin);
                    } else {
                        controller.changePassword(newPassword);
                        controller.showInfo("Password changed successfully.", stage, skin);
                        this.hide();
                    }
                }
            }
        };

        dialog.getContentTable().add(new Label("New Password:", skin)).pad(10);
        dialog.getContentTable().row();
        dialog.getContentTable().add(passwordField).width(200).pad(10);
        dialog.button("Confirm", true);
        dialog.button("Cancel", false);
        dialog.show(stage);
    }

    private void showDeleteAccountDialog() {
        Dialog dialog = new Dialog("Delete Account", skin) {
            @Override
            protected void result(Object obj) {
                if ((boolean) obj) {
                    controller.deleteCurrentUser();
                    controller.showInfo("Account deleted.", stage, skin);
                    Main.getMain().setScreen(new RegisterMenuViews(new RegisterMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
                    dispose();
                }
            }
        };
        dialog.text("Are you sure you want to delete your account?");
        dialog.button("Yes", true);
        dialog.button("No", false);
        dialog.show(stage);
    }

    private void showAvatarSelectionDialog() {
        Dialog dialog = new Dialog("Choose Avatar", skin);
        Table avatarTable = new Table();

        String[] imagePaths = {
            "2/Abby.png", "2/Dasher.png", "2/Diamond.png", "2/Hastur.png",
            "2/Hina.png", "2/Lilith.png", "2/Luna.png", "2/Raven.png",
            "2/Scarlett.png", "2/Shana.png", "2/Spark.png", "2/Yuki.png"
        };

        for (int i = 0; i < imagePaths.length; i++) {
            final String path = imagePaths[i];
            final Texture texture = new Texture(Gdx.files.internal(path));
            final Image avatar = new Image(texture);
            avatar.setSize(64, 64);

            avatar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.changeAvatar(path);
                    controller.showInfo("Avatar changed to " + path, stage, skin);
                    dialog.hide();
                    texture.dispose();
                }
            });

            avatarTable.add(avatar).pad(5);
            if ((i + 1) % 4 == 0) avatarTable.row();
        }

        dialog.getContentTable().add(avatarTable).pad(10);
        dialog.button("Cancel", false);
        dialog.show(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }
}
