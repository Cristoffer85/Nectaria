package com.cristoffer85.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Tile {
    private final BufferedImage image;
    private final int tileId;
    private static Map<Integer, Tile> tileMap = new HashMap<>();

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

    public static void loadTilesheet(String path, int tileWidth, int tileHeight) {
        try {
            BufferedImage tilesheet = ImageIO.read(Tile.class.getResource(path));
            int rows = tilesheet.getHeight() / tileWidth;
            int cols = tilesheet.getWidth() / tileHeight;

            int tileId = 0;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    BufferedImage tileImage = tilesheet.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
                    Tile tile = new Tile(tileImage, tileId);
                    tileMap.put(tileId, tile);
                    tileId++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Tile getTileById(int tileId) {
        return tileMap.get(tileId);
    }
}