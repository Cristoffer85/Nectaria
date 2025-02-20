package com.cristoffer85.Map;

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
        collidableTileIds.add(3);   // Water base
        collidableTileIds.add(4);   // Water waves
        collidableTileIds.add(5);   
        collidableTileIds.add(6);   
        collidableTileIds.add(7);   
        collidableTileIds.add(8);  
        collidableTileIds.add(9);  
        collidableTileIds.add(10);  
        collidableTileIds.add(11);  
        collidableTileIds.add(12);  
        collidableTileIds.add(13);  
        collidableTileIds.add(14);  
        collidableTileIds.add(15);  
        collidableTileIds.add(16);  
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