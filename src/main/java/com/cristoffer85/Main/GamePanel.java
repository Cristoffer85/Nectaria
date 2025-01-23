package com.cristoffer85.Main;

import com.cristoffer85.Entity.Player;
import com.cristoffer85.Tile.Tile;
import com.cristoffer85.Entity.Obstacle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePanel extends JPanel {
    private Player player;

    private List<Obstacle> obstacles;
    private List<Rectangle> straightObstacles;
    private List<Line2D> diagonalObstacles;

    private KeyHandler keyHandler;

    private List<Tile> tiles;
    private Map<Integer, Tile> tileMap;

    public GamePanel() {
        // Initialization of player and obstacles
        player = new Player(50, 50, 20, 6, 0, 0);
        obstacles = new ArrayList<>();
        straightObstacles = new ArrayList<>();
        diagonalObstacles = new ArrayList<>();
    
        // Add obstacles
        obstacles.add(new Obstacle(new Rectangle(200, 200, 10, 50), Color.RED));
        obstacles.add(new Obstacle(new Rectangle(300, 300, 50, 50), Color.RED));    // Add more obstacles as needed
        obstacles.add(new Obstacle(new Line2D.Float(100, 100, 150, 150), Color.BLUE)); // Add a diagonal line for testing
    
        // Separate obstacles into straight and diagonal lists
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getRectangle() != null) {
                straightObstacles.add(obstacle.getRectangle());
            } else if (obstacle.getDiagonalLine() != null) {
                diagonalObstacles.add(obstacle.getDiagonalLine());
            }
        }
    
        // Keyhandling methods
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);
    
        // Load tilesheet
        loadTilesheet("/TestTileSheet.png", 32, 32);
    
        // Game loop
        Timer timer = new Timer(16, e -> updateGame());
        timer.start();
    }

    private void loadTilesheet(String path, int tileWidth, int tileHeight) {
        try {
            BufferedImage tilesheet = ImageIO.read(getClass().getResource(path));
            int rows = tilesheet.getHeight() / tileWidth;
            int cols = tilesheet.getWidth() / tileHeight;
            tiles = new ArrayList<>();
            tileMap = new HashMap<>();

            int tileId = 0;
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    BufferedImage tileImage = tilesheet.getSubimage(col * tileWidth, row * tileHeight, tileWidth, tileHeight);
                    Tile tile = new Tile(tileImage, tileId);
                    tiles.add(tile);
                    tileMap.put(tileId, tile);
                    tileId++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawTile(Graphics g, int tileId, int x, int y) {
        Tile tile = tileMap.get(tileId);
        if (tile != null) {
            tile.render(g, x, y);
        }
    }

    private void updateGame() {
        player.move(keyHandler, getBounds(), straightObstacles, diagonalObstacles);
        repaint();
    }

        @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Example: Draw specific tiles at specified positions
        drawTile(g, 1, 50, 50); // Draw tile with tileId 0 at (50, 50)
        drawTile(g, 1, 100, 50); // Draw tile with tileId 1 at (100, 50)
        drawTile(g, 2, 150, 50); // Draw tile with tileId 2 at (150, 50)
        drawTile(g, 2, 150, 50); // Draw tile with tileId 2 at (150, 50)
        drawTile(g, 45, 250, 50); // Draw tile with tileId 2 at (150, 50)
        drawTile(g, 45, 350, 50); // Draw tile with tileId 2 at (150, 50)
        drawTile(g, 2, 150, 50); // Draw tile with tileId 2 at (150, 50)
        drawTile(g, 2, 150, 50); // Draw tile with tileId 2 at (150, 50)
        drawTile(g, 2, 150, 50); // Draw tile with tileId 2 at (150, 50)

        // Render obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.render(g);
        }

        // Render player on top of obstacles
        player.render(g);
    }
}