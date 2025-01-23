package com.cristoffer85.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private final BufferedImage image;
    private final int tileId;

    public Tile(BufferedImage image, int tileId) {
        this.image = image;
        this.tileId = tileId;
    }

    public int getTileId() {
        return tileId;
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
    }
}