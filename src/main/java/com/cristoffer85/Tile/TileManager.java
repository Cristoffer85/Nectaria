package com.cristoffer85.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TileManager {
    private static BufferedImage tilesheet;
    private static int tileWidth;
    private static int tileHeight;
    private static final Map<Integer, Tile> tileMap = new HashMap<>();
    private static final Map<Point, Integer> tilePositions = new HashMap<>();
    private static int mapWidth;
    private static int mapHeight;

    public static void loadTilesheet(String path, int tileWidth, int tileHeight) {
        try {
            
            // ImageIO() is a method that reads an image from a specified file
            tilesheet = ImageIO.read(TileManager.class.getResource(path));
            TileManager.tileWidth = tileWidth;
            TileManager.tileHeight = tileHeight;

            int rows = tilesheet.getHeight() / tileHeight;
            int cols = tilesheet.getWidth() / tileWidth;

            int tileId = 0;

            // Outer loop reads each row of the tilesheet
            for (int row = 0; row < rows; row++) {

                // Inner loop reads each column of the tilesheet
                for (int col = 0; col < cols; col++) {
                    BufferedImage tileImage = tilesheet.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
                    
                    // Check if the tile ID is in the collidable set
                    boolean collidable = Tile.getCollidableTileIds().contains(tileId); 
                    Tile tile = new Tile(tileImage, tileId, collidable);
                    tileMap.put(tileId, tile);
                    tileId++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initializeTiles(String path, int tileWidth, int tileHeight) {

        // BufferedReader() is a method that reads text from a specified file
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Tile.class.getResourceAsStream(path)))) {
            String line;
            int y = 0;

                // Outer loop reads each line of the file
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");

                // Inner loop reads each value on the line
                for (int x = 0; x < tokens.length; x++) {
                    int tileId = Integer.parseInt(tokens[x]);
                    tilePositions.put(new Point(x, y), tileId);
                }
                y++;
            }
            // Calculate map dimensions based on tile positions in map (MainWorld.txt)
            mapWidth = tilePositions.keySet().stream().mapToInt(point -> (int) point.getX()).max().orElse(0) + 1;
            mapHeight = tilePositions.keySet().stream().mapToInt(point -> (int) point.getY()).max().orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Tile getTile(int tileId) {
        return tileMap.get(tileId);
    }

    public static Map<Point, Integer> getTilePositions() {
        return tilePositions;
    }

    public static void renderAll(Graphics2D g2d, int cameraX, int cameraY) {
        
        // Render all tiles with camera offset
        for (Map.Entry<Point, Integer> entry : tilePositions.entrySet()) {
            Point point = entry.getKey();
            int tileId = entry.getValue();
            Tile tile = getTile(tileId);
            
            if (tile != null) {
                // Camera offset calculation for each tile
                int x = (int) point.getX() * tileWidth - cameraX;
                int y = (int) point.getY() * tileHeight - cameraY;
                tile.render(g2d, x, y);
            }
        }
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
