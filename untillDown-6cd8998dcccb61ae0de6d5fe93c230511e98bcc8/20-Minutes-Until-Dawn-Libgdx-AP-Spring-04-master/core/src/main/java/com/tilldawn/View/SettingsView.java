package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tilldawn.Control.SettingsController;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;

public class SettingsView implements Screen {
    private Stage stage;
    private final SettingsController ctrl;
    private final Skin skin;
    private final Table table;

    public SettingsView(SettingsController controller, Skin skin) {
        this.ctrl = controller;
        this.skin = skin;
        this.table = new Table();
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.table.setFillParent(true);

        Label volumeLabel = new Label("Music Volume", skin);
        final Slider volumeSlider = new Slider(0, 1, 0.1f, false, skin);
        volumeSlider.setValue(ctrl.getMusicVolume());

        Label musicLabel = new Label("Background Music", skin);
        final SelectBox<String> musicSelectBox = new SelectBox<>(skin);
        musicSelectBox.setItems("Montero", "rise_up", "walk_thru_fire");
        musicSelectBox.setSelected(ctrl.getCurrentMusic());

        final CheckBox sfxToggle = new CheckBox("Enable Sound Effects", skin);
        sfxToggle.setChecked(ctrl.isSfxEnabled());
        sfxToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ctrl.setSfxEnabled(sfxToggle.isChecked());
                if (sfxToggle.isChecked()) {
                    ctrl.playSfx(ctrl.getClickSound());
                }
            }
        });

        final CheckBox autoReloadToggle = new CheckBox("Enable Auto Reload", skin);
        autoReloadToggle.setChecked(ctrl.isAutoReloadEnabled());
        autoReloadToggle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ctrl.setAutoReloadEnabled(autoReloadToggle.isChecked());
//                ctrl.setAutoReloadEnabled(true);
            }
        });


        final CheckBox gray = new CheckBox("Enable Grayscale Mode", skin);
        gray.setChecked(ctrl.isGrayscaleEnabled());
        gray.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                ctrl.setGrayscaleEnabled(gray.isChecked());
            }
        });

        // Cheat Key Customization
        Label changeKeyLabel = new Label("Change Cheat Key:", skin);
        final SelectBox<String> actionSelectBox = new SelectBox<>(skin);
        actionSelectBox.setItems(
            "Return to Main Menu",
            "Kill Monster",
            "Kill EyeBat",
            "Add Time",
            "Level Up",
            "Reload"
        );

        final TextField keyInput = new TextField("", skin);
        TextButton setKeyButton = new TextButton("Set Key", skin);

        setKeyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String input = keyInput.getText().toUpperCase();
                try {
                    int key = Input.Keys.valueOf(input);
                    String keyName = null;
                    switch (actionSelectBox.getSelected()) {
                        case "Return to Main Menu":
                            keyName = "RETURN_MENU";
                            break;
                        case "Kill Monster":
                            keyName = "KILL_MONSTER";
                            break;
                        case "Kill EyeBat":
                            keyName = "KILL_EYEBAT";
                            break;
                        case "Add Time":
                            keyName = "ADD_TIME";
                            break;
                        case "Level Up":
                            keyName = "LEVEL_UP";
                            break;
                        case "Reload":
                            keyName = "RELOAD";
                            break;
                    }

                    if (keyName != null) {
                        ctrl.setCheatKey(keyName, key);
                        ctrl.playSfx(ctrl.getClickSound());
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid key name");
                }
            }
        });

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(event -> {
            if (backButton.isPressed()) {
                ctrl.playSfx(ctrl.getClickSound());
                Main.getMain().setScreen(new MainMenuView(
                    new MainMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()
                ));
            }
            return true;
        });

        final TextButton pauseMusicButton = getTextButton();

        volumeSlider.addListener(event -> {
            if (volumeSlider.isDragging()) return false;
            ctrl.setMusicVolume(volumeSlider.getValue());
            return true;
        });

        musicSelectBox.addListener(event -> {
            ctrl.setCurrentMusic(musicSelectBox.getSelected());
            return true;
        });

        table.add(volumeLabel).pad(10).row();
        table.add(volumeSlider).width(300).pad(10).row();
        table.add(musicLabel).pad(10).row();
        table.add(musicSelectBox).width(300).pad(10).row();
        table.add(sfxToggle).pad(10).row();
        table.add(autoReloadToggle).pad(10).row();
        table.add(gray).pad(10).row();

        table.add(changeKeyLabel).padTop(20).row();
        table.add(actionSelectBox).width(250).padBottom(5).row();
        table.add(keyInput).width(200).padBottom(5).row();
        table.add(setKeyButton).padBottom(20).row();

        table.add(pauseMusicButton).pad(10).row();
        table.add(backButton).pad(20).row();

        stage.addActor(table);
    }

    private TextButton getTextButton() {
        final TextButton pauseMusicButton = new TextButton("Pause Music", skin);
        pauseMusicButton.addListener(event -> {
            if (!pauseMusicButton.isPressed()) return false;

            ctrl.playSfx(ctrl.getClickSound());

            if (ctrl.isMusicPlaying()) {
                ctrl.pauseMusic();
                pauseMusicButton.setText("Resume Music");
            } else {
                ctrl.resumeMusic();
                pauseMusicButton.setText("Pause Music");
            }

            return true;
        });
        return pauseMusicButton;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        SpriteBatch batch = (SpriteBatch) stage.getBatch();

        if (ctrl.isGrayscaleEnabled() && ctrl.getGrayscaleShader().isCompiled()) {
            batch.setShader(ctrl.getGrayscaleShader());
        } else {
            batch.setShader(null);
        }

        stage.act(delta);
        stage.draw();
        batch.setShader(null);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() { ctrl.pauseMusic(); }
    @Override public void resume() { ctrl.resumeMusic(); }
    @Override public void hide() { dispose(); }
    @Override public void dispose() { stage.dispose(); }
}
