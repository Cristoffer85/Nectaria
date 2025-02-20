package com.cristoffer85.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Tile {
    private static BufferedImage tilesheet;
    private static int tileWidth;
    private static int tileHeight;
    private static final Map<Integer, TileManager> tileMap = new HashMap<>();
    private static final Map<Point, Integer> tilePositions = new HashMap<>();
    private static int mapWidth;
    private static int mapHeight;

    // Load tilesheet and define tiles from it
    public static void loadTilesheet(String path, int tileWidth, int tileHeight) {
        try {
            tilesheet = readTilesheetImage(path);
            Tile.tileWidth = tileWidth;
            Tile.tileHeight = tileHeight;

            int rows = tilesheet.getHeight() / tileHeight;
            int cols = tilesheet.getWidth() / tileWidth;

            int tileId = 0;

            // Outer loop for rows
            for (int row = 0; row < rows; row++) {
                // Inner loop for columns
                for (int col = 0; col < cols; col++) {
                    tileDefiner(tileId, col, row);
                    tileId++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tileDefiner(int tileId, int col, int row) {
        BufferedImage tileImage = tilesheet.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
        boolean collidable = TileManager.getCollidableTileIds().contains(tileId + 1); // Also sets the collidable id:s value to start from 1 instead
        TileManager tile = new TileManager(tileImage, tileId, collidable);
        tileMap.put(tileId, tile);
    }

    // Clear tile positions before loading a new map
    public static void clearTilePositions() {
        tilePositions.clear();
    }

    // Read map file, and then define tiles automagically by map size defined in "MainWorld.txt" for example
    public static void tilesByMapSize(String path) {
        clearTilePositions(); // Clear previous tile positions
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Tile.class.getResourceAsStream(path)))) {
            readMapRow(br);
            totalMapSize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readMapRow(BufferedReader br) throws IOException {
        String line;
        int y = 0;

        // Read each line of the map file
        while ((line = br.readLine()) != null) {
            readMapColumn(line, y);
            y++;
        }
    }

    private static void readMapColumn(String line, int y) {
        String[] tokens = line.split(",");           // Also, changed this from space to comma, because of current map editor

        // Read each tile id on row
        for (int x = 0; x < tokens.length; x++) {
            int tileId = Integer.parseInt(tokens[x]) -1;  // Array starts from 1 now, crazy huh! (Because of current map editor, and how the map numbers are saved apparently..)
            tilePositions.put(new Point(x, y), tileId);
        }
    }

    private static void totalMapSize() {
        mapWidth = tilePositions.keySet().stream().mapToInt(point -> (int) point.getX()).max().orElse(0) + 1;
        mapHeight = tilePositions.keySet().stream().mapToInt(point -> (int) point.getY()).max().orElse(0) + 1;
    }

    // Render all tiles
    public static void paintTiles(Graphics2D g2d, int cameraX, int cameraY) {
        for (Map.Entry<Point, Integer> entry : tilePositions.entrySet()) {
            Point point = entry.getKey();
            Integer tileId = entry.getValue();
            TileManager tile = getTile(tileId);

            if (tile != null) {
                int x = (int) point.getX() * tileWidth - cameraX;
                int y = (int) point.getY() * tileHeight - cameraY;
                tile.render(g2d, x, y);
            }
        }
    }

    private static BufferedImage readTilesheetImage(String path) throws IOException {
        return ImageIO.read(Tile.class.getResource(path));
    }

    // Helper methods for other classes be able to access the tile data
    public static TileManager getTile(Integer tileId) {
        return tileMap.get(tileId);
    }

    public static Map<Point, Integer> getTilePositions() {
        return tilePositions;
    }

    public static int getMapWidth() {
        return mapWidth;
    }

    public static int getMapHeight() {
        return mapHeight;
    }

    public static int getTileWidth() {
        return tileWidth;
    }

    public static int getTileHeight() {
        return tileHeight;
    }
}