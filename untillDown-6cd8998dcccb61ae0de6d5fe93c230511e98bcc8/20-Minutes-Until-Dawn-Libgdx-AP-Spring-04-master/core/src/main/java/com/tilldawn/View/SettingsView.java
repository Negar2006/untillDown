package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.SettingsController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;

public class SettingsView implements Screen {
    private Stage stage;
    private final SettingsController controller;
    private final Skin skin;
    private final Table table;

    public SettingsView(SettingsController controller, Skin skin) {
        this.controller = controller;
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
        volumeSlider.setValue(controller.getMusicVolume());

        Label musicLabel = new Label("Background Music", skin);
        final SelectBox<String> musicSelectBox = new SelectBox<>(skin);
        musicSelectBox.setItems("Montero", "rise_up", "walk_thru_fire");
        musicSelectBox.setSelected(controller.getCurrentMusic());

        final CheckBox sfxToggle = new CheckBox("Enable Sound Effects", skin);
        sfxToggle.setChecked(controller.isSfxEnabled());

        final CheckBox autoReloadToggle = new CheckBox("Enable Auto Reload", skin);
        autoReloadToggle.setChecked(controller.isAutoReloadEnabled());

        final CheckBox grayscaleToggle = new CheckBox("Enable Grayscale Mode", skin);
        grayscaleToggle.setChecked(controller.isGrayscaleEnabled());
        // Back button to go to main menu
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(event -> {
            if (backButton.isPressed()) {
                Main.getMain().setScreen(new MainMenuView(
                    new MainMenuController(),
                    GameAssetManager.getGameAssetManager().getSkin()
                ));
            }
            return true;
        });

        final TextButton pauseMusicButton = new TextButton("Pause Music", skin);
        pauseMusicButton.addListener(event -> {
            if (!pauseMusicButton.isPressed()) return false;

            if (controller.isMusicPlaying()) {
                controller.pauseMusic();
                pauseMusicButton.setText("Resume Music");
            } else {
                controller.resumeMusic();
                pauseMusicButton.setText("Pause Music");
            }

            return true;
        });
        // Listeners
        volumeSlider.addListener(event -> {
            if (volumeSlider.isDragging()) return false;
            controller.setMusicVolume(volumeSlider.getValue());
            return true;
        });

        musicSelectBox.addListener(event -> {
            controller.setCurrentMusic(musicSelectBox.getSelected());
            return true;
        });

        sfxToggle.addListener(event -> {
            controller.setSfxEnabled(sfxToggle.isChecked());
            return true;
        });

        autoReloadToggle.addListener(event -> {
            controller.setAutoReloadEnabled(autoReloadToggle.isChecked());
            return true;
        });

        grayscaleToggle.addListener(event -> {
            controller.setGrayscaleEnabled(grayscaleToggle.isChecked());
            return true;
        });

        // Layout
        table.add(volumeLabel).pad(10).row();
        table.add(volumeSlider).width(300).pad(10).row();
        table.add(musicLabel).pad(10).row();
        table.add(musicSelectBox).width(300).pad(10).row();
        table.add(sfxToggle).pad(10).row();
        table.add(autoReloadToggle).pad(10).row();
        table.add(grayscaleToggle).pad(10).row();
        table.add(pauseMusicButton).pad(10).row();
        table.add(backButton).pad(20); // back button

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {
        controller.pauseMusic(); // optional
    }

    @Override
    public void resume() {
        controller.resumeMusic(); // optional
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
