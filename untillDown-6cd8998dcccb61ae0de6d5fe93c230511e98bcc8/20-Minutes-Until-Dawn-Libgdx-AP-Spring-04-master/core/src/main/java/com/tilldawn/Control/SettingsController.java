// SettingsController.java
package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.tilldawn.Model.enums.Language;

import java.util.HashMap;

public class SettingsController {
    private float musicVolume = 0.5f;
    private String currentMusic = "montero";
    private boolean sfxEnabled = true;
    private boolean autoReloadEnabled;
    private boolean grayscaleEnabled = false;
    private final HashMap<String, Music> musicTracks = new HashMap<>();
    private Music currentPlayingMusic;
    private ShaderProgram grayscaleShader;
    private final Sound clickSound;
    private final HashMap<String, Integer> cheatKeys = new HashMap<>();
    private Language currentLanguage = Language.ENGLISH;

    public SettingsController() {
        autoReloadEnabled = true;
        musicTracks.put("montero", Gdx.audio.newMusic(Gdx.files.internal("Montero.mp3")));
        musicTracks.put("rise_up", Gdx.audio.newMusic(Gdx.files.internal("rise_up.mp3")));
        musicTracks.put("walk_thru_fire", Gdx.audio.newMusic(Gdx.files.internal("walk_thru_fire.mp3")));

        setCurrentMusic(currentMusic);

        String vert = Gdx.files.internal("shaders/grayscale.glsl").readString();
        String frag = Gdx.files.internal("shaders/grayscale.frag").readString();
        grayscaleShader = new ShaderProgram(vert, frag);
        if (!grayscaleShader.isCompiled()) {
            Gdx.app.error("Shader", "Compile error: " + grayscaleShader.getLog());
        }

        clickSound = Gdx.audio.newSound(Gdx.files.internal("sfx/gunShot.mp3"));
        setDefaultCheatKeys();
    }

    public void setMusicVolume(float volume) {
        musicVolume = Math.min(1f, Math.max(0f, volume));
        if (currentPlayingMusic != null) {
            currentPlayingMusic.setVolume(musicVolume);
        }
    }

    public void setCurrentMusic(String key) {
        key = key.toLowerCase();
        Music next = musicTracks.get(key);
        if (next == null) return;

        if (currentPlayingMusic != null) currentPlayingMusic.stop();
        currentMusic = key;
        currentPlayingMusic = next;
        currentPlayingMusic.setLooping(true);
        currentPlayingMusic.setVolume(musicVolume);
        currentPlayingMusic.play();
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public String getCurrentMusic() {
        return currentMusic;
    }

    public boolean isSfxEnabled() {
        return sfxEnabled;
    }

    public boolean isAutoReloadEnabled() {
        return autoReloadEnabled;
    }

    public boolean isGrayscaleEnabled() {
        return grayscaleEnabled;
    }

    public boolean isMusicPlaying() {
        return currentPlayingMusic != null && currentPlayingMusic.isPlaying();
    }

    public void pauseMusic() {
        if (isMusicPlaying()) currentPlayingMusic.pause();
    }

    public void resumeMusic() {
        if (currentPlayingMusic != null && !currentPlayingMusic.isPlaying()) {
            currentPlayingMusic.play();
        }
    }

    public void dispose() {
        for (Music m : musicTracks.values()) m.dispose();
        if (grayscaleShader != null) grayscaleShader.dispose();
    }

    public ShaderProgram getGrayscaleShader() {
        return grayscaleShader;
    }

    public void playSfx(Sound sound) {
        if (sfxEnabled && sound != null) {
            sound.play();
        }
    }

    public Sound getClickSound() {
        return clickSound;
    }

    public void setDefaultCheatKeys() {
        cheatKeys.put("RETURN_MENU", Input.Keys.N);
        cheatKeys.put("KILL_MONSTER", Input.Keys.K);
        cheatKeys.put("KILL_EYEBAT", Input.Keys.E);
        cheatKeys.put("ADD_TIME", Input.Keys.T);
        cheatKeys.put("LEVEL_UP", Input.Keys.B);
        cheatKeys.put("RELOAD", Input.Keys.R);
    }

    public int getCheatKey(String name) {
        return cheatKeys.getOrDefault(name, Input.Keys.UNKNOWN);
    }

    public void setCheatKey(String name, int key) {
        cheatKeys.put(name, key);
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public void toggleLanguage() {
        currentLanguage = (currentLanguage == Language.ENGLISH) ? Language.FRENCH : Language.ENGLISH;
    }

}
