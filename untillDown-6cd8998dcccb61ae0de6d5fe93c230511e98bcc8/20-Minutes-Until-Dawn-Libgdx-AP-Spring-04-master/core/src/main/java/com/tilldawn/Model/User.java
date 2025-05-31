package com.tilldawn.Model;

public class User {
    private String username;
    private String password;
    private String securityAnswer;
    private String selectedCharacter;
    private int Score;
    private long timeAliveMillis;
    private int numberOfKillingMonster;

    public User() {}

    public User(String username, String password, String securityAnswer, String selectedCharacter, int Score, int numberOfKillingMonster, long timeAliveMillis) {
        this.username = username;
        this.password = password;
        this.securityAnswer = securityAnswer;
        this.selectedCharacter = selectedCharacter;
        this.Score = Score;
        this.numberOfKillingMonster = numberOfKillingMonster;
        this.timeAliveMillis = timeAliveMillis;
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

    public void addNumberOfKilling(int numberOfKillingMonster) {
        this.numberOfKillingMonster += numberOfKillingMonster;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public void setNumberOfKillingMonster(int numberOfKillingMonster) {
        this.numberOfKillingMonster = numberOfKillingMonster;
    }

    public void addScore(int score) {
        Score += score;
    }

    public int getNumberOfKillingMonster() {
        return numberOfKillingMonster;
    }

    public long getTimeAliveMillis() {
        return timeAliveMillis;
    }

    public String getAvatar() {
        return securityAnswer;
    }

    public void setTimeAliveMillis(long timeAliveMillis) {
        this.timeAliveMillis = timeAliveMillis;
    }

    @Override
    public String toString() {
        return "User{" +
            "username='" + username + '\'' +
            ", selectedCharacter='" + selectedCharacter + '\'' +
            '}';
    }
}

