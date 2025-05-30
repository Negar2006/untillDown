package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
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

        final CheckBox gray = new CheckBox("Enable Grayscale Mode", skin);
        gray.setChecked(ctrl.isGrayscaleEnabled());
        gray.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent event, Actor actor) {
                ctrl.setGrayscaleEnabled(gray.isChecked());
            }
        });

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

        final TextButton pauseMusicButton = getTextButton();
        // Listeners
        volumeSlider.addListener(event -> {
            if (volumeSlider.isDragging()) return false;
            ctrl.setMusicVolume(volumeSlider.getValue());
            return true;
        });

        musicSelectBox.addListener(event -> {
            ctrl.setCurrentMusic(musicSelectBox.getSelected());
            return true;
        });

        sfxToggle.addListener(event -> {
            ctrl.setSfxEnabled(sfxToggle.isChecked());
            return true;
        });

        autoReloadToggle.addListener(event -> {
            ctrl.setAutoReloadEnabled(autoReloadToggle.isChecked());
            return true;
        });


        // Layout
        table.add(volumeLabel).pad(10).row();
        table.add(volumeSlider).width(300).pad(10).row();
        table.add(musicLabel).pad(10).row();
        table.add(musicSelectBox).width(300).pad(10).row();
        table.add(sfxToggle).pad(10).row();
        table.add(autoReloadToggle).pad(10).row();
        table.add(gray).pad(10).row();
        table.add(pauseMusicButton).pad(10).row();
        table.add(backButton).pad(20);

        stage.addActor(table);
    }

    private TextButton getTextButton() {
        final TextButton pauseMusicButton = new TextButton("Pause Music", skin);
        pauseMusicButton.addListener(event -> {
            if (!pauseMusicButton.isPressed()) return false;

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

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {
        ctrl.pauseMusic(); // optional
    }

    @Override
    public void resume() {
        ctrl.resumeMusic(); // optional
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
