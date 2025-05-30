// SettingsController.java
package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.audio.Music;

import java.util.HashMap;

public class SettingsController {
    private float musicVolume = 0.5f;
    private String currentMusic = "montero";
    private boolean sfxEnabled = true;
    private boolean autoReloadEnabled = false;
    private boolean grayscaleEnabled = false;

    private final HashMap<String, Music> musicTracks = new HashMap<>();
    private Music currentPlayingMusic;
    private ShaderProgram grayscaleShader;

    public SettingsController() {
        // load tracks (keys all lowercase for consistency)
        musicTracks.put("montero", Gdx.audio.newMusic(Gdx.files.internal("Montero.mp3")));
        musicTracks.put("rise_up", Gdx.audio.newMusic(Gdx.files.internal("rise_up.mp3")));
        musicTracks.put("walk_thru_fire", Gdx.audio.newMusic(Gdx.files.internal("walk_thru_fire.mp3")));

        setCurrentMusic(currentMusic);

        // compile shader
        String vert = Gdx.files.internal("shaders/grayscale.glsl").readString();
        String frag = Gdx.files.internal("shaders/grayscale.frag").readString();
        grayscaleShader = new ShaderProgram(vert, frag);
        if (!grayscaleShader.isCompiled()) {
            Gdx.app.error("Shader", "Compile error: " + grayscaleShader.getLog());
        }
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

    public void setSfxEnabled(boolean enabled) { sfxEnabled = enabled; }

    public void setAutoReloadEnabled(boolean enabled) { autoReloadEnabled = enabled; }

    public void setGrayscaleEnabled(boolean enabled) {
        grayscaleEnabled = enabled;
    }

    /** Called by the view before drawing the stage. */
    public void applyGrayscale() {
        if (grayscaleEnabled && grayscaleShader.isCompiled()) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glUseProgram(grayscaleShader.getHandle());
        } else {
            Gdx.gl.glUseProgram(0);
        }
    }

    // getters...
    public float getMusicVolume() { return musicVolume; }
    public String getCurrentMusic() { return currentMusic; }
    public boolean isSfxEnabled() { return sfxEnabled; }
    public boolean isAutoReloadEnabled() { return autoReloadEnabled; }
    public boolean isGrayscaleEnabled() { return grayscaleEnabled; }
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
}
