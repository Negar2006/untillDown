package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import java.util.HashMap;

public class SettingsController {
    private float musicVolume = 0.5f;
    private String currentMusic = "Montero";
    private boolean sfxEnabled = true;
    private boolean autoReloadEnabled = false;
    private boolean grayscaleEnabled = false;

    private final HashMap<String, Music> musicTracks = new HashMap<>();
    private Music currentPlayingMusic;

    public SettingsController() {
        musicTracks.put("Montero", Gdx.audio.newMusic(Gdx.files.internal("Montero.mp3")));
        musicTracks.put("rise_up", Gdx.audio.newMusic(Gdx.files.internal("rise_up.mp3")));
        musicTracks.put("walk_thru_fire", Gdx.audio.newMusic(Gdx.files.internal("walk_thru_fire.mp3")));

        setCurrentMusic(currentMusic); // play default
    }

    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
        if (currentPlayingMusic != null) {
            currentPlayingMusic.setVolume(volume);
        }
        System.out.println("Music volume set to: " + volume);
    }

    public void setCurrentMusic(String music) {
        if (!musicTracks.containsKey(music)) return;

        if (currentPlayingMusic != null) {
            currentPlayingMusic.stop();
        }

        currentMusic = music;
        currentPlayingMusic = musicTracks.get(music);
        currentPlayingMusic.setLooping(true);
        currentPlayingMusic.setVolume(musicVolume);
        currentPlayingMusic.play();

        System.out.println("Music changed to: " + music);
    }

    public void setSfxEnabled(boolean enabled) {
        this.sfxEnabled = enabled;
        System.out.println("SFX enabled: " + enabled);
    }

    public void setAutoReloadEnabled(boolean enabled) {
        this.autoReloadEnabled = enabled;
        System.out.println("Auto-reload: " + enabled);
    }

    public void setGrayscaleEnabled(boolean enabled) {
        this.grayscaleEnabled = enabled;
        System.out.println("Grayscale mode: " + enabled);
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

    public void dispose() {
        for (Music music : musicTracks.values()) {
            music.dispose();
        }
    }

    public void pauseMusic() {
        if (currentPlayingMusic != null && currentPlayingMusic.isPlaying()) {
            currentPlayingMusic.pause();
        }
    }

    public void resumeMusic() {
        if (currentPlayingMusic != null && !currentPlayingMusic.isPlaying()) {
            currentPlayingMusic.play();
        }
    }

    public boolean isMusicPlaying() {
        return currentPlayingMusic != null && currentPlayingMusic.isPlaying();
    }

}
