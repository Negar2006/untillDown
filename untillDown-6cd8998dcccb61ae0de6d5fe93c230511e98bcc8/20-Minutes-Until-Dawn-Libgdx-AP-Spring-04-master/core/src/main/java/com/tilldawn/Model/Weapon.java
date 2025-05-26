package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.enums.weaponType;

public class Weapon {
    private final Texture smgTexture = new Texture(GameAssetManager.getGameAssetManager().getSmg());
    private Sprite smgSprite = new Sprite(smgTexture);
    private int ammo = 30;  // Default ammo for SMG
    private weaponType type;
    private long reloadStartTime = -1;

    public Weapon(weaponType type) {
        this.type = type;
        smgSprite.setX((float) Gdx.graphics.getWidth() / 2);
        smgSprite.setY((float) Gdx.graphics.getHeight() / 2);
        smgSprite.setSize(50, 50);
    }

    public Texture getSmgTexture() {
        return smgTexture;
    }

    public Sprite getSmgSprite() {
        return smgSprite;
    }

    public void setSmgSprite(Sprite smgSprite) {
        this.smgSprite = smgSprite;
    }

    public weaponType getType() {
        return type;
    }

    public void setType(weaponType type) {
        this.type = type;
    }

    public long getReloadStartTime() {
        return reloadStartTime;
    }

    public void setReloadStartTime(long reloadStartTime) {
        this.reloadStartTime = reloadStartTime;
    }

    public void reload() {
        if (reloadStartTime == -1) {
            reloadStartTime = System.currentTimeMillis(); // Start reload timer
        }
    }

    public boolean isReloading() {
        if (reloadStartTime == -1) return false;

        // Check if enough time has passed to finish the reload
        long elapsedTime = System.currentTimeMillis() - reloadStartTime;
        if (elapsedTime >= type.reloadTimeSeconds * 1000) {
            ammo = type.maxAmmo; // Reload ammo to max
            reloadStartTime = -1; // Reset reload timer
            return false; // Reload complete
        }
        return true; // Still reloading
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public weaponType getWeaponType() {
        return type;
    }

    public void setPosition(float x, float y) {
        smgSprite.setPosition(x, y);
    }
}
