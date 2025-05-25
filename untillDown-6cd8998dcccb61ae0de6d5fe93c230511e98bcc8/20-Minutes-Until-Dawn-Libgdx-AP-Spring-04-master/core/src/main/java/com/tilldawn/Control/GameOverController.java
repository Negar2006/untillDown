package com.tilldawn.Control;

public class GameOverController {
    private String username;
    private int score;
    private int kills;
    private float health;

    // Getters and setters
    public GameOverController(String username, int score, int kills, float health) {
        this.username = username;
        this.score = score;
        this.kills = kills;
        this.health = health;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getKills() {
        return kills;
    }

    public float getHealth() {
        return health;
    }
}
