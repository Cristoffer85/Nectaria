package com.cristoffer85;

import javax.swing.*;

import com.cristoffer85.Main.GamePanel;

public class Game {
    private static JFrame frame;
    private static GamePanel gamePanel;

    public static void main(String[] args) {
        frame = new JFrame("New proper 2D collision game");
        gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setSize(1920, 1080); // Set initial window size
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