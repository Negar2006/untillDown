package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;

public enum Ability {
    VITALITY(GameAssetManager.getGameAssetManager().getVitalityTexture(), "Vitality"),
    DAMAGER(GameAssetManager.getGameAssetManager().getDamagerTexture(), "Damager"),
    PROCREASE(GameAssetManager.getGameAssetManager().getProCreaseTexture(), "Procrease"),
    AMOCREASE(GameAssetManager.getGameAssetManager().getAmoCreaseTexture(), "Amocrease"),
    SPEEDY(GameAssetManager.getGameAssetManager().getSpeedyTexture(), "Speedy");

    private final Texture texture;
    private String description;
    Ability(Texture texture, String description) {
        this.texture = texture;
        this.description = description;
    }
    public Texture getTexture() {
        return texture;
    }
    public String getDescription() {
        return description;
    }
}
