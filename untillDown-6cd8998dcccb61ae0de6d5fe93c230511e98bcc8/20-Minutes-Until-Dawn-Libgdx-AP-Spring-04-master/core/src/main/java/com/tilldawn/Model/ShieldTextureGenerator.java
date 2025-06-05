package com.tilldawn.Model;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PixmapIO;

public class ShieldTextureGenerator {

    public static void createCircleShieldTexture(int diameter, String filePath) {
        Pixmap pixmap = new Pixmap(diameter, diameter, Format.RGBA8888);

        // Clear background to transparent
        pixmap.setColor(0, 0, 0, 0);
        pixmap.fill();

        // Draw white circle (shield)
        pixmap.setColor(Color.WHITE);

        int radius = diameter / 2;

        int thickness = 5;
        for (int r = radius - thickness; r <= radius; r++) {
            drawCircle(pixmap, radius, radius, r);
        }

        // Save pixmap as PNG file
        FileHandle file = new FileHandle(filePath);
        PixmapIO.writePNG(file, pixmap);

        pixmap.dispose();
    }

    private static void drawCircle(Pixmap pixmap, int centerX, int centerY, int radius) {
        int x = radius;
        int y = 0;
        int err = 0;

        while (x >= y) {
            plotCirclePoints(pixmap, centerX, centerY, x, y);
            y += 1;
            if (err <= 0) {
                err += 2 * y + 1;
            }
            if (err > 0) {
                x -= 1;
                err -= 2 * x + 1;
            }
        }
    }

    private static void plotCirclePoints(Pixmap pixmap, int cx, int cy, int x, int y) {
        pixmap.drawPixel(cx + x, cy + y);
        pixmap.drawPixel(cx + y, cy + x);
        pixmap.drawPixel(cx - y, cy + x);
        pixmap.drawPixel(cx - x, cy + y);
        pixmap.drawPixel(cx - x, cy - y);
        pixmap.drawPixel(cx - y, cy - x);
        pixmap.drawPixel(cx + y, cy - x);
        pixmap.drawPixel(cx + x, cy - y);
    }
}
