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
        Tile.loadTilesheet("/Overworld640x576-16pxtile.png", 16, 16); // Use 16x16 tiles (shall be 64x64)

        // Initialize tiles from file
        Tile.initializeTiles("/MainWorld.txt", 16, 16); // Use 16x16 tiles (shall be 64x64)

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

    // ## Overriding the JavaX Swing paintComponent method to render the game ##
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render the game to the BufferedImage
        Graphics2D g2d = gameImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, gameImage.getWidth(), gameImage.getHeight());

        // Render all tiles
        Tile.renderAll(g2d);

        // Render all obstacles
        Obstacle.renderAll(g2d);

        // Render player on top of obstacles
        player.render(g2d);

        // Dispose of the Graphics2D object == cleanup of resources
        g2d.dispose();

        // Draw the BufferedImage scaled up to the panel size
        g.drawImage(gameImage, 0, 0, gameImage.getWidth() * scaleFactor, gameImage.getHeight() * scaleFactor, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(baseWidth * scaleFactor, baseHeight * scaleFactor);
    }
}