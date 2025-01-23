package com.cristoffer85.Main;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Tile.Tile;
import com.cristoffer85.Entity.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.geom.Line2D;

public class GamePanel extends JPanel {
    private Player player;
    private KeyHandler keyHandler;

    public GamePanel() {
        // Initialize player
        player = new Player(50, 50, 20, 6, 0, 0);
        
        // Initialize obstacles
        Obstacle.addObstacles();

        // Keyhandling methods
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);

        // Load tilesheet
        Tile.loadTilesheet("/Overworld640x576-16pxtile.png", 16, 16);
        // Initialize tiles
        Tile.initializeTiles("/MainWorld.txt", 16, 16);

        // Game loop
        Timer timer = new Timer(16, e -> updateGame());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Render tiles
        Tile.renderAll(g);
        // Render obstacles
        Obstacle.renderAll(g);

        // Render player on top of obstacles
        player.render(g);
    }

    private void updateGame() {
        List<Rectangle> straightObstacles = Obstacle.getStraightObstacles();
        List<Line2D> diagonalObstacles = Obstacle.getDiagonalObstacles();
        player.move(keyHandler, getBounds(), straightObstacles, diagonalObstacles);
        repaint();
    }
}