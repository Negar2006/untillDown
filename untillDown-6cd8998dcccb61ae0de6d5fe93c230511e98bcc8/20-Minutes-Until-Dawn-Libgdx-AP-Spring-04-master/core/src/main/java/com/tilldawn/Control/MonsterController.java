package com.tilldawn.Control;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.Monster;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.Seed;
import com.tilldawn.Model.enums.monsterTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MonsterController {
    private ArrayList<Monster> tentacleMonsters = new ArrayList<>();
    private ArrayList<Monster> treeMonsters = new ArrayList<>();
    private ArrayList<Monster> eyebatMonsters = new ArrayList<>();
    private ArrayList<Monster> ElderMonster = new ArrayList<>();
    private ArrayList<Seed> seeds = new ArrayList<>();

    private float tentacleSpawnTimer = 0;
    private float eyebatSpawnTimer = 0;
    private boolean treesSpawned = false;
    private GameController gameController;
    private boolean isElder = false;
    private final HashMap<Monster, Float> overlapTimers = new HashMap<>();

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

        seeds.removeIf(seed -> player.getPlayerSprite().getBoundingRectangle().overlaps(seed.getBounds()));

        Iter(player, batch, tentacleMonsters);
        Iter(player, batch, treeMonsters);
        Iter(player, batch, eyebatMonsters);
        Iter(player, batch, ElderMonster);
        handleEyebatBullets(player);

        for (Seed seed : seeds) {
            seed.draw(batch);
        }
    }

    private void Iter(Player player, SpriteBatch batch, ArrayList<Monster> monsters) {
        Iterator<Monster> iter = monsters.iterator();
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        while (iter.hasNext()) {
            Monster m = iter.next();

            if (m.isDead()) {
                iter.remove();
                overlapTimers.remove(m);  // clean up
                int newKill = gameController.getKillCount() + 1;
                gameController.setNumberOfKill(newKill);
                player.addScore(m.getType().getScore());
                seeds.add(new Seed(m.getType(), m.getX(), m.getY()));
                continue;
            }

            m.updateAndDraw(batch, player);

            if (player.getPlayerSprite().getBoundingRectangle().overlaps(m.getSprite().getBoundingRectangle())) {
                float time = overlapTimers.getOrDefault(m, 0f) + delta;
                if (time >= 1f) {
                    player.takeDamage(5);
                    time = 0f;
                }
                overlapTimers.put(m, time);
            } else {
                overlapTimers.put(m, 0f);
            }
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

    public void clearMonsters() {
        tentacleMonsters.clear();
        eyebatMonsters.clear();
        treeMonsters.clear();
        ElderMonster.clear();
    }

    public void addMonster(Monster monster) {
        switch (monster.getType()) {
            case TentacleMonster:
                tentacleMonsters.add(monster);
                break;
            case Eyebat:
                eyebatMonsters.add(monster);
                break;
            case TreeMonster:
                treeMonsters.add(monster);
                break;
            case Elder:
                ElderMonster.add(monster);
                break;
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

    public ArrayList<Seed> getSeeds() {
        return seeds;
    }
}

