package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.GameView;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Iterator;

public class WeaponController {
    private final Weapon weapon;
    private final ArrayList<Bullet> bullets = new ArrayList<>();
    private GameView gameView;
    private GameController controller;

    public void setGameController(GameController controller) {
        this.controller = controller;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }


    public WeaponController(Weapon weapon) {
        this.weapon = weapon;
    }

    public void update() {

        ArrayList<Monster> allMonsters = new ArrayList<>();
        allMonsters.addAll(controller.getMonsterController().getTentacleMonsters());
        allMonsters.addAll(controller.getMonsterController().getTreeMonsters());
        allMonsters.addAll(controller.getMonsterController().getEyebatMonsters());
        allMonsters.addAll(controller.getMonsterController().getElderMonster());

        if (controller != null && controller.isAutoAimEnabled()) {
            Monster closest = findClosestMonster(allMonsters);
            if (closest != null) {
                autoAimAt(closest);
            }
        }

        if (controller != null && controller.isAutoAimEnabled()) {
            Monster closest = findClosestMonster(allMonsters);
            if (closest != null) {
                autoAimAt(closest);
            }
        }

        Player player = controller.getPlayerController().getPlayer();
        Sprite playerSprite = player.getPlayerSprite();

        float weaponX = playerSprite.getX() + playerSprite.getWidth() / 2;
        float weaponY = playerSprite.getY() + playerSprite.getHeight() / 3;

        weapon.setPosition(weaponX, weaponY);
        weapon.getSmgSprite().draw(Main.getBatch());
        weapon.reload(Gdx.graphics.getDeltaTime());
        updateBullets(allMonsters);
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSmgSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int x, int y) {
        Main.getMain().getSettingsController().playSfx(Main.getMain().getSettingsController().getClickSound());
        if (weapon.isReloading()) return;

        if (weapon.getAmmo() <= 0) {
            if (controller.isReloadAutoEnabled()) {
                weapon.startReload();
            }
            return;
        }

        Player player = controller.getPlayerController().getPlayer();
        Sprite playerSprite = player.getPlayerSprite();
        float playerX = playerSprite.getX() + playerSprite.getWidth() / 2;
        float playerY = playerSprite.getY() + playerSprite.getHeight() / 3;
        float angleDeg = weapon.getSmgSprite().getRotation();
        float fixedAngle = angleDeg + 360;

        for (int i = 0; i < weapon.getProjectileCount(); i++) {
            float spread = MathUtils.random(-5f, 5f); // randomize for shotgun
            float finalAngle = fixedAngle + spread;
            float targetX = playerX + MathUtils.cosDeg(finalAngle) * 1000;
            float targetY = playerY + MathUtils.sinDeg(finalAngle) * 1000;

            bullets.add(new Bullet(targetX, targetY, playerX, playerY, weapon.getDamage()));
        }

        weapon.setAmmo(weapon.getAmmo() - 1);
    }


    public void updateBullets(ArrayList<Monster> monsters) {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet b = bulletIterator.next();
            b.getSprite().draw(Main.getBatch());

            // move in stored direction
            Vector2 dir = b.getDirection();
            b.getSprite().translate(dir.x * 5, dir.y * 5);

            for (Monster m : monsters) {
                if (b.getSprite().getBoundingRectangle().overlaps(m.getSprite().getBoundingRectangle())) {
                    m.takeDamage(b.getDamage());
                    m.applyKnockback(b.getSprite().getX(), b.getSprite().getY(), 10f);

                    if (gameView != null) {
                        float popupX = m.getSprite().getX() + m.getSprite().getWidth() / 2;
                        float popupY = m.getSprite().getY() + m.getSprite().getHeight();
                        gameView.addDamagePopup(popupX, popupY, "-5");
                    }

                    bulletIterator.remove();
                    break;
                }
            }
        }
    }

    private Monster findClosestMonster(ArrayList<Monster> monsters) {
        float minDistance = Float.MAX_VALUE;
        Monster closest = null;

        Sprite playerSprite = controller.getPlayerController().getPlayer().getPlayerSprite();
        float playerX = playerSprite.getX() + playerSprite.getWidth() / 2f;
        float playerY = playerSprite.getY() + playerSprite.getHeight() / 2f;

        for (Monster monster : monsters) {
            Sprite monsterSprite = monster.getSprite();
            float dx = (monsterSprite.getX() + monsterSprite.getWidth() / 2f) - playerX;
            float dy = (monsterSprite.getY() + monsterSprite.getHeight() / 2f) - playerY;
            float distance = dx * dx + dy * dy;
            if (distance < minDistance) {
                minDistance = distance;
                closest = monster;
            }
        }

        return closest;
    }

    private void autoAimAt(Monster target) {
        float targetX = target.getSprite().getX() + target.getSprite().getWidth() / 2;
        float targetY = target.getSprite().getY() + target.getSprite().getHeight() / 2;

        handleWeaponRotation((int) targetX, (int) targetY);

        try {
            Robot robot = new Robot();
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

            int windowX = gd.getDefaultConfiguration().getBounds().x;
            int windowY = gd.getDefaultConfiguration().getBounds().y;

            int screenX = windowX + (int) targetX;
            int screenY = windowY + Gdx.graphics.getHeight() - (int) targetY;

            robot.mouseMove(screenX, screenY);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void addProjectile(int i) {
    }
}
