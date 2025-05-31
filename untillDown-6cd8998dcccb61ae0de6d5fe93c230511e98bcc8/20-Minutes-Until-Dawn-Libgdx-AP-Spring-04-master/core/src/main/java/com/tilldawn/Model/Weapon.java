package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.enums.weaponType;

public class Weapon {
    private final Texture smgTexture = new Texture(GameAssetManager.getGameAssetManager().getSmg());
    private Sprite smgSprite = new Sprite(smgTexture);

    private weaponType type;
    private int ammo;
    private int damage;
    private boolean isReloading = false;
    private float reloadTimer = 0f;

    public Weapon(weaponType type) {
        this.damage = type.damage;
        this.type = type;
        this.ammo = type.maxAmmo;
        smgSprite.setSize(50, 50);
        smgSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
    }

    public weaponType getType() {
        return type;
    }

    public Sprite getSmgSprite() {
        return smgSprite;
    }

    public void setPosition(float x, float y) {
        smgSprite.setPosition(x, y);
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo){
        this.ammo = ammo;
    }

    public int getProjectileCount() {
        return type.projectileCount;
    }

    public int getMaxAmmo() {
        return type.maxAmmo;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public void reload(float deltaTime) {
        if (!isReloading) return;
        reloadTimer += deltaTime;
        if (reloadTimer >= type.reloadTimeSeconds) {
            ammo = type.maxAmmo;
            isReloading = false;
            reloadTimer = 0f;
        }
    }

    public void startReload() {
        isReloading = true;
        reloadTimer = 0f;
    }

    public void addAmmo(int i) {
        this.ammo += i;
    }

    public void addDamage(float v) {
        this.damage += v;
    }

    public int getDamage() {
        return damage;
    }
}
