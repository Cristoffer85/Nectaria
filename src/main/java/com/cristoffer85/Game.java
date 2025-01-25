package com.cristoffer85;

import javax.swing.*;
import java.awt.Dimension;

import com.cristoffer85.Main.GamePanel;

public class Game {
    private static JFrame frame;
    private static GamePanel gamePanel;

    public static void main(String[] args) {
        // Change resolution here
        int baseWidth = 960;        // Base resolution width
        int baseHeight = 540;       // Base resolution height
        final int scaleFactor = 2;  // Scale factor

        frame = new JFrame("New proper 2D collision game");
        gamePanel = new GamePanel(baseWidth, baseHeight, scaleFactor);
        gamePanel.setPreferredSize(new Dimension(baseWidth * scaleFactor, baseHeight * scaleFactor));
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Base super simple and not very good or worked through method to change resolution, yet, change by clicking f1, f2 or f3, check KeyHandler.java and GamePanel.java to follow common KeyHandler logic
    public static void changeResolution(int width, int height) {
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}