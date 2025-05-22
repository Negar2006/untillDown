package com.tilldawn.Control;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class MonsterController {
    private ArrayList<Monster> monsters = new ArrayList<>();
    private float spawnTimer = 0;

    public void update(Player player, SpriteBatch batch) {
        spawnTimer += com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        if (spawnTimer > 2f) {
            monsters.add(new Monster());
            spawnTimer = 0;
        }

        Iterator<Monster> iter = monsters.iterator();
        while (iter.hasNext()) {
            Monster m = iter.next();

            if (m.isDead()) {
                player.addScore(10);
                iter.remove();
                continue;
            }
            m.moveTowards(player.getPlayerSprite().getX(), player.getPlayerSprite().getY());
            m.updateAndDraw(batch);
        }
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
