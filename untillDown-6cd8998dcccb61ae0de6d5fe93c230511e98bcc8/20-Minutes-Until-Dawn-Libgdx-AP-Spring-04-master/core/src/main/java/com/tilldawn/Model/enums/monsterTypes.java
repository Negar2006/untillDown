package com.tilldawn.Model.enums;

public enum monsterTypes {
    TreeMonster(5, 10),
    TentacleMonster(10, 25),
    Eyebat(20, 50),
    Elder(50, 400);

    private final int score;
    private final int health;

    monsterTypes(int score, int health) {
        this.score = score;
        this.health = health;
    }

    public int getScore() {
        return score;
    }

    public int getHealth() {
        return health;
    }
}
