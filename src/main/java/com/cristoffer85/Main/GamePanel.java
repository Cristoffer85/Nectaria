package com.cristoffer85.Main;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Tile.Tile;
import com.cristoffer85.Entity.Player;
import com.cristoffer85.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private KeyHandler keyHandler;
    private BufferedImage gameImage;
    private int scaleFactor = 2; // Scale factor for pixel doubling entire game == change this value and up/downscale entire game (Couldnt figure out any other way to not have to manually draw 128x128px sprite and tiles)? 
                                 // Scale factor: 2 (with 64px sprites and tiles) makes the game viewable and playable on 1920x1080 resolution.

    public GamePanel() {
        // Initialization of player and obstacles
        player = new Player(30, 30, 64, 6, 0, 0); // Use 32x32 size for player
        Obstacle.addObstacles();

        // Keyhandling methods
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);

        // Load tilesheet
        Tile.loadTilesheet("/Overworld640x576-16pxtile.png", 16, 16); // Use 32x32 tiles

        // Initialize tiles from file
        Tile.initializeTiles("/MainWorld.txt", 16, 16); // Use 32x32 tiles

        // Create the game image with the lower resolution
        gameImage = new BufferedImage(960, 540, BufferedImage.TYPE_INT_ARGB); // Half of 1920x1080

        // Game loop
        Timer timer = new Timer(16, e -> updateGame());
        timer.start();

        // Add key listener for resolution change
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    Game.changeResolution(1280, 720); // Switch to 1280x720
                } else if (e.getKeyCode() == KeyEvent.VK_F2) {
                    Game.changeResolution(1920, 1080); // Switch to 1920x1080
                } else if (e.getKeyCode() == KeyEvent.VK_F3) {
                    Game.changeResolution(2560, 1440); // Switch to 2560x1440
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render the game to the BufferedImage
        Graphics2D g2d = gameImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, gameImage.getWidth(), gameImage.getHeight());

        // Render all tiles
        Tile.renderAll(g2d);

        // Render obstacles
        Obstacle.renderAll(g2d);

        // Render player on top of obstacles
        player.render(g2d);

        g2d.dispose();

        // Draw the BufferedImage scaled up to the panel size
        g.drawImage(gameImage, 0, 0, gameImage.getWidth() * scaleFactor, gameImage.getHeight() * scaleFactor, null);
    }

    private void updateGame() {
        List<Rectangle> straightObstacles = Obstacle.getStraightObstacles();
        List<Line2D> diagonalObstacles = Obstacle.getDiagonalObstacles();
        player.move(keyHandler, getBounds(), straightObstacles, diagonalObstacles);
        repaint();
    }
}