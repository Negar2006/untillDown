package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DamagePopup {
    private BitmapFont font;
    private float x, y;
    private String text;
    private float alpha = 1f;
    private float elapsed = 0f;

    public DamagePopup(float x, float y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = new BitmapFont();
        font.setColor(Color.RED);
    }

    public void update(float delta) {
        elapsed += delta;
        y += 30 * delta;
        alpha = 1 - (elapsed / 1f);
        font.setColor(1, 0, 0, alpha);
    }

    public void draw(SpriteBatch batch) {
        font.draw(batch, text, x, y);
    }

    public boolean isFinished() {
        return elapsed > 1f;
    }
}
