package com.tilldawn.Model;

import java.util.Map;

public class GameState {
    private String playerName;
    private float playerX;
    private float playerY;
    private float playerHealth;
    private String currentHour;
    private String weaponType;
    private int treeMonsterCount;
    private Map<String, Float> treeMonsterPositions; // Use String for the map keys
    private boolean elderMonsterAppeared;

    // Getters and Setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public float getPlayerX() {
        return playerX;
    }

    public void setPlayerX(float playerX) {
        this.playerX = playerX;
    }

    public float getPlayerY() {
        return playerY;
    }

    public void setPlayerY(float playerY) {
        this.playerY = playerY;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public String getCurrentHour() {
        return currentHour;
    }

    public void setCurrentHour(String currentHour) {
        this.currentHour = currentHour;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType = weaponType;
    }

    public int getTreeMonsterCount() {
        return treeMonsterCount;
    }

    public void setTreeMonsterCount(int treeMonsterCount) {
        this.treeMonsterCount = treeMonsterCount;
    }

    public boolean isElderMonsterAppeared() {
        return elderMonsterAppeared;
    }

    public void setElderMonsterAppeared(boolean elderMonsterAppeared) {
        this.elderMonsterAppeared = elderMonsterAppeared;
    }

    public Map<String, Float> getTreeMonsterPositions() {
        return treeMonsterPositions;
    }

    public void setTreeMonsterPositions(Map<String, Float> treeMonsterPositions) {
        this.treeMonsterPositions = treeMonsterPositions;
    }
}
