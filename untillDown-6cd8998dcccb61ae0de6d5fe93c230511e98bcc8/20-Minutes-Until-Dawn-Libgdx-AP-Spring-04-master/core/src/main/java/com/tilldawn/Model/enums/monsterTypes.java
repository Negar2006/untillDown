package com.tilldawn.Model.enums;

public enum monsterTypes {
    TreeMonster(5),
    TentacleMonster(10),
    Eyebat(20),
    Elder(50);

    private final int score;

    monsterTypes(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
