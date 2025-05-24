package com.tilldawn.Model.enums;

public enum weaponType {
    REVOLVER(6, 1, 1, 20),
    SHOTGUN(2, 1, 4, 10),
    SMG_DUAL(24, 2, 1, 8);

    public final int maxAmmo;
    public final int reloadTimeSeconds;
    public final int projectileCount;
    public final int damage;

    weaponType(int maxAmmo, int reloadTimeSeconds, int projectileCount, int damage) {
        this.maxAmmo = maxAmmo;
        this.reloadTimeSeconds = reloadTimeSeconds;
        this.projectileCount = projectileCount;
        this.damage = damage;
    }
}
