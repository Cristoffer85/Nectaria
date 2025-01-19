package com.cristoffer85.Main;

import javax.swing.*;

import com.cristoffer85.Entities.Obstacle;
import com.cristoffer85.Entities.Player;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GamePanel extends JPanel {
    private final Player player;
    private final Obstacle obstacle;
    private final Set<Integer> keysPressed = new HashSet<>();

    public GamePanel() {
        player = new Player(50, 50, 20, 5);
        obstacle = new Obstacle(new Rectangle(200, 200, 50, 50));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keysPressed.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keysPressed.remove(e.getKeyCode());
            }
        });

        setFocusable(true);
        setPreferredSize(new Dimension(400, 400));

        Timer timer = new Timer(16, e -> updateGame());
        timer.start();
    }

    private void updateGame() {
        int deltaX = 0, deltaY = 0;

        if (keysPressed.contains(KeyEvent.VK_W)) deltaY -= player.getMoveSpeed();
        if (keysPressed.contains(KeyEvent.VK_S)) deltaY += player.getMoveSpeed();
        if (keysPressed.contains(KeyEvent.VK_A)) deltaX -= player.getMoveSpeed();
        if (keysPressed.contains(KeyEvent.VK_D)) deltaX += player.getMoveSpeed();

        player.move(deltaX, deltaY, getBounds(), obstacle.getRectangle());
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        player.draw(g);
        obstacle.draw(g);
    }
}