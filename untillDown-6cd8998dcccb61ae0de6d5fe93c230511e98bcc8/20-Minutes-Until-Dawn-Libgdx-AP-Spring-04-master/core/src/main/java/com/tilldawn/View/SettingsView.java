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
import com.tilldawn.Model.enums.Language;

public class SettingsView implements Screen {
    private Stage stage;
    private final SettingsController ctrl;
    private final Skin skin;
    private final Table table;

    private Label volumeLabel, changeKeyLabel;
    private CheckBox sfxToggle, autoReloadToggle, grayToggle;
    private TextButton setKeyButton, backButton, pauseMusicButton, languageToggleButton;

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

        volumeLabel = new Label("Music Volume", skin);
        changeKeyLabel = new Label("Change Cheat Key:", skin);

        final Slider volumeSlider = new Slider(0, 1, 0.1f, false, skin);
        volumeSlider.setValue(ctrl.getMusicVolume());

        final SelectBox<String> musicSelectBox = new SelectBox<>(skin);
        musicSelectBox.setItems("Montero", "rise_up", "walk_thru_fire");
        musicSelectBox.setSelected(ctrl.getCurrentMusic());

        sfxToggle = new CheckBox("Enable Sound Effects", skin);
        sfxToggle.setChecked(ctrl.isSfxEnabled());

        autoReloadToggle = new CheckBox("Enable Auto Reload", skin);
        autoReloadToggle.setChecked(ctrl.isAutoReloadEnabled());

        grayToggle = new CheckBox("Enable Grayscale Mode", skin);
        grayToggle.setChecked(ctrl.isGrayscaleEnabled());

        final SelectBox<String> actionSelectBox = new SelectBox<>(skin);
        actionSelectBox.setItems("Return to Main Menu", "Kill Monster", "Kill EyeBat", "Add Time", "Level Up", "Reload");

        final TextField keyInput = new TextField("", skin);
        setKeyButton = new TextButton("Set Key", skin);
        setKeyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String input = keyInput.getText().toUpperCase();
                try {
                    int key = Input.Keys.valueOf(input);
                    String keyName = null;
                    String selected = actionSelectBox.getSelected();

                    if (selected.equals("Return to Main Menu")) {
                        keyName = "RETURN_MENU";
                    } else if (selected.equals("Kill Monster")) {
                        keyName = "KILL_MONSTER";
                    } else if (selected.equals("Kill EyeBat")) {
                        keyName = "KILL_EYEBAT";
                    } else if (selected.equals("Add Time")) {
                        keyName = "ADD_TIME";
                    } else if (selected.equals("Level Up")) {
                        keyName = "LEVEL_UP";
                    } else if (selected.equals("Reload")) {
                        keyName = "RELOAD";
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

        backButton = new TextButton("Back", skin);
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

        pauseMusicButton = new TextButton("Pause Music", skin);
        pauseMusicButton.addListener(event -> {
            if (!pauseMusicButton.isPressed()) return false;
            ctrl.playSfx(ctrl.getClickSound());
            if (ctrl.isMusicPlaying()) {
                ctrl.pauseMusic();
                pauseMusicButton.setText(ctrl.getCurrentLanguage() == Language.FRENCH ? "Reprendre la musique" : "Resume Music");
            } else {
                ctrl.resumeMusic();
                pauseMusicButton.setText(ctrl.getCurrentLanguage() == Language.FRENCH ? "Mettre en pause" : "Pause Music");
            }
            return true;
        });

        languageToggleButton = new TextButton("Switch to French", skin);
        languageToggleButton.addListener(event -> {
            ctrl.toggleLanguage();
            updateLanguageTexts();
            languageToggleButton.setText(ctrl.getCurrentLanguage() == Language.ENGLISH
                ? "Switch to French" : "Changer en anglais");
            return true;
        });

        volumeSlider.addListener(event -> {
            if (volumeSlider.isDragging()) return false;
            ctrl.setMusicVolume(volumeSlider.getValue());
            return true;
        });

        musicSelectBox.addListener(event -> {
            ctrl.setCurrentMusic(musicSelectBox.getSelected());
            return true;
        });

        ScrollPane scrollPane = new ScrollPane(table, skin);
        scrollPane.setFillParent(true);
        scrollPane.setScrollingDisabled(false, false);

        table.add(languageToggleButton).pad(10).row();
        table.add(volumeLabel).pad(10).row();
        table.add(volumeSlider).width(300).pad(10).row();
        table.add(musicSelectBox).width(300).pad(10).row();
        table.add(sfxToggle).pad(10).row();
        table.add(autoReloadToggle).pad(10).row();
        table.add(grayToggle).pad(10).row();

        table.add(changeKeyLabel).padTop(20).row();
        table.add(actionSelectBox).width(250).padBottom(5).row();
        table.add(keyInput).width(200).padBottom(5).row();
        table.add(setKeyButton).padBottom(20).row();

        table.add(pauseMusicButton).pad(10).row();
        table.add(backButton).pad(20).row();

        stage.addActor(scrollPane);

        updateLanguageTexts();
    }

    private void updateLanguageTexts() {
        Language lang = ctrl.getCurrentLanguage();

        volumeLabel.setText(lang == Language.FRENCH ? "Volume de la musique" : "Music Volume");
        sfxToggle.setText(lang == Language.FRENCH ? "Activer les effets sonores" : "Enable Sound Effects");
        autoReloadToggle.setText(lang == Language.FRENCH ? "Activer le rechargement automatique" : "Enable Auto Reload");
        grayToggle.setText(lang == Language.FRENCH ? "Activer le mode niveaux de gris" : "Enable Grayscale Mode");
        changeKeyLabel.setText(lang == Language.FRENCH ? "Changer la touche de triche :" : "Change Cheat Key:");
        setKeyButton.setText(lang == Language.FRENCH ? "Définir la touche" : "Set Key");
        backButton.setText(lang == Language.FRENCH ? "Retour" : "Back");
        pauseMusicButton.setText(ctrl.isMusicPlaying()
            ? (lang == Language.FRENCH ? "Mettre en pause" : "Pause Music")
            : (lang == Language.FRENCH ? "Reprendre la musique" : "Resume Music"));
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
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        ctrl.pauseMusic();
    }

    @Override
    public void resume() {
        ctrl.resumeMusic();
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
