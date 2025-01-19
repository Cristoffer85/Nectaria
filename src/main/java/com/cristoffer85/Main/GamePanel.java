package com.cristoffer85.Main;

import javax.swing.*;
import java.awt.*;
import com.cristoffer85.Entities.Player;
import java.util.HashSet;
import java.util.Set;
import com.cristoffer85.Entities.Obstacle;

public class GamePanel extends JPanel {
    private final Player player;
    private final Obstacle obstacle;
    private final Set<Integer> keysPressed = new HashSet<>();
    private final KeyHandler keyHandler;

    public GamePanel() {
        player = new Player(50, 50, 20, 5);
        obstacle = new Obstacle(new Rectangle(200, 200, 50, 50));

        keyHandler = new KeyHandler(keysPressed);
        addKeyListener(keyHandler);

        setFocusable(true);
        setPreferredSize(new Dimension(400, 400));

        Timer timer = new Timer(16, e -> updateGame());
        timer.start();
    }

    private void updateGame() {
        int playerMoveX = keyHandler.getPlayerMoveX() * player.getMoveSpeed();
        int playerMoveY = keyHandler.getPlayerMoveY() * player.getMoveSpeed();

        player.move(playerMoveX, playerMoveY, getBounds(), obstacle.getRectangle());
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