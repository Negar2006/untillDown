package com.tilldawn.Control;

public class GameOverController {
    private String username;
    private int score;
    private int kills;
    private float health;
    private String gameResult;  // new field for game result
    private long timeAliveMillis;

    public GameOverController(String username, int score, int kills, float health, long timeAliveMillis, String gameResult) {
        this.username = username;
        this.score = score;
        this.kills = kills;
        this.health = health;
        this.timeAliveMillis = timeAliveMillis;
        this.gameResult = gameResult;
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

    public void calculateScore() {
        this.score = (int) (timeAliveMillis / 1000 * kills);
    }

    public String getGameResult() {
        return gameResult;
    }
}
