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
    private ArrayList<Monster> ElderMonster = new ArrayList<>();

    private float tentacleSpawnTimer = 0;
    private float eyebatSpawnTimer = 0;
    private boolean treesSpawned = false;
    private GameController gameController;
    private boolean isElder = false;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void update(Player player, SpriteBatch batch, float totalGameTime, float totalGame) {
        if (!treesSpawned) {
            spawnInitialTrees();
            treesSpawned = true;
        }

        tentacleSpawnTimer += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (tentacleSpawnTimer >= 3f) {
            tentacleSpawnTimer = 0f;

            int numberToSpawn = Math.max((int) (totalGameTime / 30f), 0);
            for (int i = 0; i < numberToSpawn; i++) {
                tentacleMonsters.add(new Monster(monsterTypes.TentacleMonster));
            }
        }

        eyebatSpawnTimer += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (totalGameTime >= (totalGame / 4)) {
            if (eyebatSpawnTimer > 10f) {
                eyebatSpawnTimer = 0;

                int numberToSpawn = Math.max((int) ((4 * totalGameTime - totalGame + 30) / 30f), 0);
                for (int i = 0; i < numberToSpawn; i++) {
                    eyebatMonsters.add(new Monster(monsterTypes.Eyebat));
                }
            }
        }

        if (totalGameTime >= (totalGame / 2) && !isElder) {
            ElderMonster.add(new Monster(monsterTypes.Elder));
            isElder = true;
        }

        Iter(player, batch, tentacleMonsters);
        Iter(player, batch, treeMonsters);
        Iter(player, batch, eyebatMonsters);
        Iter(player, batch, ElderMonster);
        handleEyebatBullets(player);
    }

    private void Iter(Player player, SpriteBatch batch, ArrayList<Monster> monsters) {
        Iterator<Monster> iter = monsters.iterator();

        while (iter.hasNext()) {
            Monster m = iter.next();

            if (m.isDead()) {
                iter.remove();
                int newKill = gameController.getKillCount() + 1;
                gameController.setNumberOfKill(newKill);
                player.addScore(m.getType().getScore());
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

    public ArrayList<Monster> getElderMonster() {
        return ElderMonster;
    }
}
