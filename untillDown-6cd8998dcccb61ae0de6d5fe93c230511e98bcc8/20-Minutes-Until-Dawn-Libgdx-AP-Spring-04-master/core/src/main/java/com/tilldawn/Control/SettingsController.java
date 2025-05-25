package com.tilldawn.Control;

public class SettingsController {
    private float musicVolume = 0.5f;
    private String currentMusic = "Track 1";
    private boolean sfxEnabled = true;
    private boolean autoReloadEnabled = false;
    private boolean grayscaleEnabled = false;

    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
        System.out.println("Music volume set to: " + volume);
    }

    public void setCurrentMusic(String music) {
        this.currentMusic = music;
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
}
