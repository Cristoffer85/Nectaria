package com.cristoffer85;

import javax.swing.*;

import com.cristoffer85.Main.GamePanel;

public class Game {
    private static JFrame frame;
    private static GamePanel gamePanel;

    public static void main(String[] args) {
        int baseWidth = 960;  // Base resolution width
        int baseHeight = 540; // Base resolution height
        int scaleFactor = 2;  // Scale factor

        frame = new JFrame("New proper 2D collision game");
        gamePanel = new GamePanel(baseWidth, baseHeight, scaleFactor);
        frame.add(gamePanel);
        frame.pack(); // Set the window size based on the GamePanel's preferred size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void changeResolution(int width, int height) {
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // Center the window
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}