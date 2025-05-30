package com.tilldawn.Control;

import com.badlogic.gdx.utils.Array;
import com.tilldawn.Model.User;

public class ScoreboardController {

    // Sort by score (highest first)
    public Array<User> sortUsersByScore(Array<User> users) {
        users.sort(this::compareByScore);  // Using a method reference for clarity
        return users;
    }

    // Sort by number of kills (highest first)
    public Array<User> sortUsersByKills(Array<User> users) {
        users.sort(this::compareByKills);  // Using a method reference for clarity
        return users;
    }

    // Sort by time alive (longer time first)
    public Array<User> sortUsersByTimeAlive(Array<User> users) {
        users.sort(this::compareByTimeAlive);  // Using a method reference for clarity
        return users;
    }

    // Sort by username (alphabetical order)
    public Array<User> sortUsersByUsername(Array<User> users) {
        users.sort(this::compareByUsername);  // Using a method reference for clarity
        return users;
    }

    // Helper method for comparing by score
    private int compareByScore(User user1, User user2) {
        if (user1 == null || user2 == null) return 0;  // Handle nulls safely
        return Integer.compare(user2.getScore(), user1.getScore());
    }

    // Helper method for comparing by number of kills
    private int compareByKills(User user1, User user2) {
        if (user1 == null || user2 == null) return 0;  // Handle nulls safely
        return Integer.compare(user2.getNumberOfKillingMonster(), user1.getNumberOfKillingMonster());
    }

    // Helper method for comparing by time alive
    private int compareByTimeAlive(User user1, User user2) {
        if (user1 == null || user2 == null) return 0;  // Handle nulls safely
        return Long.compare(user2.getTimeAliveMillis(), user1.getTimeAliveMillis());
    }

    // Helper method for comparing by username
    private int compareByUsername(User user1, User user2) {
        if (user1 == null || user2 == null) return 0;  // Handle nulls safely
        return user1.getUsername().compareTo(user2.getUsername());
    }
}
