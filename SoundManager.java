package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private static Sound deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/death.wav"));

    public static void playDeathSound() {
        deathSound.play(1.0f); // 1.0f = volume
    }
}
