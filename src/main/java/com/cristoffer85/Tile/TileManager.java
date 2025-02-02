package com.cristoffer85.Tile;

import lombok.Getter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

@Getter
public class TileManager {
    private final BufferedImage image;
    private final int tileId;
    private final boolean collidable;
    private static final Set<Integer> collidableTileIds = new HashSet<>();

    // COLLIDABLE TILES values go here (from array value from tilesheet)
    static {
        collidableTileIds.add(1);   // Grass water top left corner
        collidableTileIds.add(2);   // Grass water top center
        collidableTileIds.add(3);   // Grass water top right corner
        collidableTileIds.add(4);   // Grass water top left outer corner
        collidableTileIds.add(5);   // Grass water top right outer corner
        collidableTileIds.add(21);  // Grass water left center
        collidableTileIds.add(23);  // Grass water right center
        collidableTileIds.add(24);  // Grass water bottom left outer corner
        collidableTileIds.add(25);  // Grass water bottom right outer corner
        collidableTileIds.add(40);  // Water base
        collidableTileIds.add(41);  // Grass water bottom left corner
        collidableTileIds.add(42);  // Grass water bottom center
        collidableTileIds.add(43);  // Grass water bottom right corner
        collidableTileIds.add(60);  // Water waves
    }

    public TileManager(BufferedImage image, int tileId, boolean collidable) {
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