package com.cristoffer85.Main;

import com.cristoffer85.Entities.Player;
import com.cristoffer85.Entities.Obstacle;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private List<Obstacle> obstacles;
    private KeyHandler keyHandler;

    public GamePanel() {
        // Initialization of player and obstacles
        player = new Player(50, 50, 20, 6, 0, 0);
        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(new Rectangle(200, 200, 10, 50)));
        obstacles.add(new Obstacle(new Rectangle(300, 300, 50, 50))); // Add more obstacles as needed

        // Keyhandling methods
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);

        // Game loop
        Timer timer = new Timer(16, e -> updateGame());
        timer.start();
    }

    private void updateGame() {
        // Update player movement once
        List<Rectangle> obstacleRectangles = new ArrayList<>();
        for (Obstacle obstacle : obstacles) {
            obstacleRectangles.add(obstacle.getRectangle());
        }
        player.move(keyHandler, getBounds(), obstacleRectangles);

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
    }
}