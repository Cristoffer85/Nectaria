package com.cristoffer85.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Tile {
    private static BufferedImage tilesheet;
    private static int tileWidth;
    private static int tileHeight;
    private final BufferedImage image;
    private final int tileId;
    private static Map<Integer, Tile> tileMap = new HashMap<>();
    private static Map<Point, Integer> tilePositions = new HashMap<>();
    private static int mapWidth;
    private static int mapHeight;

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
            tilesheet = ImageIO.read(Tile.class.getResource(path));
            Tile.tileWidth = tileWidth;
            Tile.tileHeight = tileHeight;

            int rows = tilesheet.getHeight() / tileHeight;
            int cols = tilesheet.getWidth() / tileWidth;

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

    public static void addTile(int tileId, int x, int y) {
        tilePositions.put(new Point(x, y), tileId);
    }

    public static void initializeTiles(String filePath, int tileWidth, int tileHeight) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Tile.class.getResourceAsStream(filePath)))) {
            String line;
            int height = 0;
            int width = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                width = parts.length; // Update width for each line
                for (int x = 0; x < parts.length; x++) {
                    int tileId = Integer.parseInt(parts[x]);
                    addTile(tileId, x, height); // Store tile positions in tile coordinates
                }
                height++;
            }
            mapWidth = width;
            mapHeight = height;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void renderAll(Graphics2D g2d, int cameraX, int cameraY) {
        for (Map.Entry<Point, Integer> entry : tilePositions.entrySet()) {
            Point position = entry.getKey();
            int tileId = entry.getValue();
            Tile tile = getTileById(tileId);
            if (tile != null) {
                int renderX = position.x * tileWidth - cameraX;
                int renderY = position.y * tileHeight - cameraY;
                tile.render(g2d, renderX, renderY);
            }
        }
    }

    public static int getTileWidth() {
        return tileWidth;
    }

    public static int getTileHeight() {
        return tileHeight;
    }

    public static int getMapWidth() {
        return mapWidth;
    }

    public static int getMapHeight() {
        return mapHeight;
    }
}