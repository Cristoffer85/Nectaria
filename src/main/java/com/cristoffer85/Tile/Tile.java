package com.cristoffer85.Tile;

import lombok.Getter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

@Getter
public class Tile {
    private final BufferedImage image;
    private final int tileId;
    private final boolean collidable;
    private static final Set<Integer> collidableTileIds = new HashSet<>();

    // COLLIDABLE TILES values go here (from array value from tilesheet)
    static {
        collidableTileIds.add(3);
    }

    public Tile(BufferedImage image, int tileId, boolean collidable) {
        this.image = image;
        this.tileId = tileId;
        this.collidable = collidable;
    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    public static Set<Integer> getCollidableTileIds() {
        return collidableTileIds;
    }
}