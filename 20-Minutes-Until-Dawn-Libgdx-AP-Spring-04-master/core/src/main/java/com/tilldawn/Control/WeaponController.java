package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.View.GameView;

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

    public void update(ArrayList<Monster> monsters) {
        if (controller != null && controller.isAutoAimEnabled()) {
            Monster closest = findClosestMonster(monsters);
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
        updateBullets(monsters);
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSmgSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int x, int y) {
        bullets.add(new Bullet(x, y));
        weapon.setAmmo(weapon.getAmmo() - 1);
    }

    public void updateBullets(ArrayList<Monster> monsters) {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet b = bulletIterator.next();
            b.getSprite().draw(Main.getBatch());

            Vector2 direction = new Vector2(
                Gdx.graphics.getWidth() / 2f - b.getX(),
                Gdx.graphics.getHeight() / 2f - b.getY()
            ).nor();

            b.getSprite().setX(b.getSprite().getX() - direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 5);

            for (Monster m : monsters) {
                if (b.getSprite().getBoundingRectangle().overlaps(m.getSprite().getBoundingRectangle())) {
                    m.takeDamage(b.getDamage());

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
    }

}
