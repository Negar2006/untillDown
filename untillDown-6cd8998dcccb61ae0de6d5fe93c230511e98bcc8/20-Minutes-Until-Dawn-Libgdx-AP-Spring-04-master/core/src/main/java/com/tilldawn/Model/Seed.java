package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.tilldawn.Model.enums.monsterTypes;

public class Seed {
    private Texture texture;
    private Rectangle bounds;
    private monsterTypes type;

    public Seed(monsterTypes type, float x, float y) {
        this.type = type;
        switch (type) {
            case TreeMonster:
                texture = new Texture("Tree_EM.png");
                break;
            case TentacleMonster:
                texture = new Texture("Tentacle_EM.png");
                break;
            case Eyebat:
                texture = new Texture("EyeBat_EM.png");
                break;
            case Elder:
                texture = new Texture("Elder_EM.png");
                break;
        }
        this.bounds = new Rectangle(x, y, texture.getWidth() * 5f, texture.getHeight() * 5f);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        texture.dispose();
    }
}
