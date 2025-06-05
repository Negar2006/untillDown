package com.tilldawn.Model;

import java.util.ArrayList;

public class SaveData {
    public String playerName;
    public float playerX;
    public float playerY;
    public float playerHealth;
    public ArrayList<String> abilities;

    public long currentTimeMillis;
    public int duration;
    public String weapon;

    public int treeMonsterCount;
    public ArrayList<Float> treeMonsterPositionX;
    public ArrayList<Float> treeMonsterPositionY;

    public boolean elderMonsterAppeared;
    public float elderMonsterPositionX;
    public float elderMonsterPositionY;
    public String NameOfFile;

    public SaveData() {
        this.treeMonsterPositionX = new ArrayList<>();
        this.treeMonsterPositionY = new ArrayList<>();
        this.abilities = new ArrayList<>();
    }

    public void addAbility(String abilityName) {
        if (!abilities.contains(abilityName)) {
            this.abilities.add(abilityName);
        }
    }

    public void removeAbility(String abilityName) {
        this.abilities.remove(abilityName);
    }

    public void clearAbilities() {
        this.abilities.clear();
    }

    public ArrayList<String> getAbilities() {
        return new ArrayList<>(abilities); // برگشت کپی برای حفظ انسجام داده‌ها
    }
}
