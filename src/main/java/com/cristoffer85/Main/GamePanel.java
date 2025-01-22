package com.cristoffer85.Main;

import com.cristoffer85.Entity.Player;
import com.cristoffer85.Entity.Obstacle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private List<Obstacle> obstacles;
    private List<Rectangle> straightObstacles;
    private List<Line2D> diagonalObstacles;
    private KeyHandler keyHandler;

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

        // Game loop
        Timer timer = new Timer(16, e -> updateGame());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Render obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.render(g);
        }

        // Render player on top of obstacles
        player.render(g);
    }

    private void updateGame() {
        player.move(keyHandler, getBounds(), straightObstacles, diagonalObstacles);
        repaint();
    }
}