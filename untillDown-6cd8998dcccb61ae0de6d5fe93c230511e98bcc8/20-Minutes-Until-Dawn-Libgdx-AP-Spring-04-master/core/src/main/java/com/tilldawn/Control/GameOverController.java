package com.tilldawn.Control;

public class GameOverController {
    private final String username;
    private final int score;
    private final int kills;
    private final float health;
    private final String gameResult;
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

    public String getGameResult() {
        return gameResult;
    }
}
