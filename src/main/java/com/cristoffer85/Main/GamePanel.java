package com.cristoffer85.Main;

import javax.swing.*;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Entity.Player;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private List<Obstacle> obstacles;
    private List<Line2D> diagonalObstacles;
    private KeyHandler keyHandler;

    public GamePanel() {
        // Initialization of player and obstacles
        player = new Player(50, 50, 20, 6, 0, 0);
        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(new Rectangle(200, 200, 10, 50)));
        obstacles.add(new Obstacle(new Rectangle(300, 300, 50, 50))); // Add more obstacles as needed

        // Initialization of diagonal obstacles
        diagonalObstacles = new ArrayList<>();
        diagonalObstacles.add(new Line2D.Float(100, 100, 150, 150)); // Add a diagonal line for testing

        // Keyhandling methods
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);

        // Game loop
        Timer timer = new Timer(16, e -> updateGame());
        timer.start();
    }

    private void updateGame() {
        List<Rectangle> obstacleRectangles = new ArrayList<>();
        for (Obstacle obstacle : obstacles) {
            obstacleRectangles.add(obstacle.getRectangle());
        }
        player.move(keyHandler, getBounds(), obstacleRectangles, diagonalObstacles);

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        player.render(g);
        for (Obstacle obstacle : obstacles) {
            obstacle.render(g);
        }

        // Render diagonal obstacles
        g.setColor(Color.BLUE);
        for (Line2D line : diagonalObstacles) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.draw(line);
        }
    }
}