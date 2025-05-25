package com.tilldawn.Model;

public class User {
    private String username;
    private String password;
    private String securityAnswer;
    private String selectedCharacter;
    private int Score;

    public User() {}

    public User(String username, String password, String securityAnswer, String selectedCharacter) {
        this.username = username;
        this.password = password;
        this.securityAnswer = securityAnswer;
        this.selectedCharacter = selectedCharacter;
        Score = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public String getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public void setSelectedCharacter(String selectedCharacter) {
        this.selectedCharacter = selectedCharacter;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        this.Score = score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
            "username='" + username + '\'' +
            ", selectedCharacter='" + selectedCharacter + '\'' +
            '}';
    }
}

