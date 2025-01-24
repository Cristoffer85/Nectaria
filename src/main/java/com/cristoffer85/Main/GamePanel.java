package com.cristoffer85.Main;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Tile.Tile;
import com.cristoffer85.Entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private KeyHandler keyHandler;
    private BufferedImage gameImage;
    private int scaleFactor;
    private final int baseWidth;
    private final int baseHeight;

    public GamePanel(int baseWidth, int baseHeight, int scaleFactor) {
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.scaleFactor = scaleFactor;

        // Initialization of player and obstacles
        player = new Player(30, 30, 64, 6, 0, 0); // Use 64x64 size for player
        Obstacle.addObstacles();

        // Keyhandling methods
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);

        // Load tilesheet
        Tile.loadTilesheet("/TileSheet.png", 64, 64);           // Use 64x64 tiles

        // Initialize tiles from file
        Tile.initializeTiles("/MainWorld.txt", 64, 64);     // Use 64x64 tiles

        // Create the game image with the base resolution
        gameImage = new BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_ARGB);

        // Game loop
        Timer timer = new Timer(16, e -> {
            // Update game state
            List<Rectangle> straightObstacles = Obstacle.getStraightObstacles();
            List<Line2D> diagonalObstacles = Obstacle.getDiagonalObstacles();
            player.move(keyHandler, getBounds(), straightObstacles, diagonalObstacles);
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calculate camera position
        int cameraX = player.getX() - baseWidth / 2;
        int cameraY = player.getY() - baseHeight / 2;

        // Clamp camera position to map boundaries
        int maxCameraX = Tile.getMapWidth() * Tile.getTileWidth() - baseWidth;
        int maxCameraY = Tile.getMapHeight() * Tile.getTileHeight() - baseHeight;
        cameraX = Math.max(0, Math.min(cameraX, maxCameraX));
        cameraY = Math.max(0, Math.min(cameraY, maxCameraY));

        // Render the game to the BufferedImage
        Graphics2D g2d = gameImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, gameImage.getWidth(), gameImage.getHeight());

        // Render all tiles with camera offset
        Tile.renderAll(g2d, cameraX, cameraY);

        // Render all obstacles with camera offset
        Obstacle.renderAll(g2d, cameraX, cameraY);

        // Render player on top of obstacles
        player.render(g2d, cameraX, cameraY);

        // Dispose of the Graphics2D object
        g2d.dispose();

        // Draw the BufferedImage scaled up to the panel size
        g.drawImage(gameImage, 0, 0, gameImage.getWidth() * scaleFactor, gameImage.getHeight() * scaleFactor, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(baseWidth * scaleFactor, baseHeight * scaleFactor);
    }
}