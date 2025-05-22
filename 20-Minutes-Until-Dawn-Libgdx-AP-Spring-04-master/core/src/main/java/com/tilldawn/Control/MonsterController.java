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
    private ArrayList<Monster> eyebatMonsters = new ArrayList<>();

    private float spawnTimer = 0;
    private float eyebatSpawnTimer = 0;
    private boolean treesSpawned = false;

    public void update(Player player, SpriteBatch batch) {
        if (!treesSpawned) {
            spawnInitialTrees();
            treesSpawned = true;
        }

        for (Monster m : treeMonsters) {
            if (!m.isDead()) {
                m.updateAndDraw(batch, player);
            }
        }

        spawnTimer += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (spawnTimer > 2f) {
            tentacleMonsters.add(new Monster(monsterTypes.TentacleMonster));
            spawnTimer = 0;
        }

        eyebatSpawnTimer += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (eyebatSpawnTimer > 2f) {
            eyebatMonsters.add(new Monster(monsterTypes.Eyebat));
            eyebatSpawnTimer = 0;
        }

        for (Monster m : eyebatMonsters) {
            if (!m.isDead()) {
                m.updateAndDraw(batch, player);
            }
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
        for (int i = 0; i < 50; i++) {
            treeMonsters.add(new Monster(monsterTypes.TreeMonster));
        }
    }

    public ArrayList<Monster> getEyebatMonsters() {
        return eyebatMonsters;
    }

    public ArrayList<Monster> getTentacleMonsters() {
        return tentacleMonsters;
    }

    public ArrayList<Monster> getTreeMonsters() {
        return treeMonsters;
    }
}
