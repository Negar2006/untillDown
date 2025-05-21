package com.tilldawn.Model;

import java.awt.*;

public class Tree {
    public float x, y;
    private int health = 100;

    public Tree(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void damage(int amount) {
        health -= amount;
        if (health < 0) health = 0;
    }
    public boolean isAlive() {
        return health > 0;
    }
    public int getHealth() {
        return health;
    }

//    public Rectangle getBounds() {
//        return new Rectangle(x, y, width, height);
//    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
