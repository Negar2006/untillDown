package com.tilldawn.Control;

import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.User;

public class ScoreboardController {

    public Array<User> sortUsersByScore(Array<User> users) {
        users.sort(this::compareByScore);
        return users;
    }

    public Array<User> sortUsersByKills(Array<User> users) {
        users.sort(this::compareByKills);
        return users;
    }

    public Array<User> sortUsersByTimeAlive(Array<User> users) {
        users.sort(this::compareByTimeAlive);
        return users;
    }

    public Array<User> sortUsersByUsername(Array<User> users) {
        users.sort(this::compareByUsername);
        return users;
    }

    private int compareByScore(User user1, User user2) {
        if (user1 == null || user2 == null) return 0;
        return Integer.compare(user2.getScore(), user1.getScore());
    }

    private int compareByKills(User user1, User user2) {
        if (user1 == null || user2 == null) return 0;
        return Integer.compare(user2.getNumberOfKillingMonster(), user1.getNumberOfKillingMonster());
    }

    private int compareByTimeAlive(User user1, User user2) {
        if (user1 == null || user2 == null) return 0;
        return Long.compare(user2.getTimeAliveMillis(), user1.getTimeAliveMillis());
    }

    private int compareByUsername(User user1, User user2) {
        if (user1 == null || user2 == null) return 0;
        return user1.getUsername().compareTo(user2.getUsername());
    }
}
