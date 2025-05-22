package com.tilldawn.Control;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.enums.monsterTypes;

import java.util.ArrayList;
import java.util.Iterator;

public class MonsterController {
    private ArrayList<Monster> tentacleMonsters = new ArrayList<>();
    private ArrayList<Monster> treeMonsters = new ArrayList<>();
    private float spawnTimer = 0;
    private boolean treesSpawned = false;

    public void update(Player player, SpriteBatch batch) {
        if (!treesSpawned) {
            spawnInitialTrees();
            treesSpawned = true;
        }

        // Draw and check TreeMonsters
        for (Monster m : treeMonsters) {
            if (!m.isDead()) {
                m.updateAndDraw(batch, player); // They don’t move, so no need for extra logic
            }
        }

        // TentacleMonster spawn logic
        spawnTimer += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (spawnTimer > 2f) {
            tentacleMonsters.add(new Monster(monsterTypes.TentacleMonster));
            spawnTimer = 0;
        }

        Iterator<Monster> iter = tentacleMonsters.iterator();
        while (iter.hasNext()) {
            Monster m = iter.next();

            if (m.isDead()) {
                player.addScore(10);
                iter.remove();
                continue;
            }
            m.updateAndDraw(batch, player);
        }
    }

    private void spawnInitialTrees() {
        for (int i = 0; i < 80; i++) {
            treeMonsters.add(new Monster(monsterTypes.TreeMonster));
        }
    }

    public ArrayList<Monster> getTentacleMonsters() {
        return tentacleMonsters;
    }

    public ArrayList<Monster> getTreeMonsters() {
        return treeMonsters;
    }
}
