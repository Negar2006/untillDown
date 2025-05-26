package com.tilldawn.Model;

import java.util.ArrayList;

public class SaveData {
    // Player info
    public String playerName;
    public float playerX;
    public float playerY;
    public float playerHealth;

    // Game settings
    public int duration;
    public String weapon;

    // Monster info
    public int treeMonsterCount;
    public ArrayList<Float> treeMonsterPositionX;
    public ArrayList<Float> treeMonsterPositionY;

    // Default constructor required for libGDX Json
    public SaveData() {
        this.treeMonsterPositionX = new ArrayList<>();
        this.treeMonsterPositionY = new ArrayList<>();
    }
}
