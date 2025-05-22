package com.tilldawn.Control;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Model.Bullet;
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
    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }


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
        if (eyebatSpawnTimer > 10f) {
            eyebatMonsters.add(new Monster(monsterTypes.Eyebat));
            eyebatSpawnTimer = 0;
        }

        for (Monster m : eyebatMonsters) {
            if (!m.isDead()) {
                m.updateAndDraw(batch, player);
            }
        }

        Iter(player, batch, tentacleMonsters);
        Iter(player, batch, treeMonsters);
        Iter(player, batch, eyebatMonsters);
        handleEyebatBullets(player);
    }

    private void Iter(Player player, SpriteBatch batch, ArrayList<Monster> eyebatMonsters) {
        Iterator<Monster> iter2 = eyebatMonsters.iterator();
        while (iter2.hasNext()) {
            Monster m = iter2.next();

            if (m.isDead()) {
                player.addScore(10);
                iter2.remove();
                int newKill = gameController.getKillCount() + 1;
                gameController.setNumberOfKill(newKill);
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

    private void handleEyebatBullets(Player player) {
        for (Monster eyebat : eyebatMonsters) {
            if (eyebat.isDead()) continue;
            ArrayList<Bullet> bullets = eyebat.getBullets();
            Iterator<Bullet> iter = bullets.iterator();
            while (iter.hasNext()) {
                Bullet bullet = iter.next();
                if (bullet.getSprite().getBoundingRectangle().overlaps(player.getPlayerSprite().getBoundingRectangle())) {
                    player.takeDamage(bullet.getDamage());
                    iter.remove();
                }
            }
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
