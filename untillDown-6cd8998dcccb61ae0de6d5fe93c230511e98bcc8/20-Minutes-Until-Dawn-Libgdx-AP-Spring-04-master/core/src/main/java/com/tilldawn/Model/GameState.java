package com.tilldawn.Model;

import java.util.ArrayList;

public class GameState {
    public Player player;
    public ArrayList<Monster> monsters;

    public GameState(Player player, ArrayList<Monster> monsters) {
        this.player = player;
        this.monsters = monsters;
    }
}
