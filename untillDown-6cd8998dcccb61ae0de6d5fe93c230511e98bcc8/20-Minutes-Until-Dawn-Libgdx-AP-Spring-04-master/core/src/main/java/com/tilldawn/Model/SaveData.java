package com.tilldawn.Model;

import java.util.ArrayList;

public class SaveData {
    public String playerName;
    public float playerX;
    public float playerY;
    public float playerHealth;
    public int exp;
    public int level;
    public ArrayList<String> abilities;

    public long currentTimeMillis;
    public long remainingTimeMillis;
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
}
