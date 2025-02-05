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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the frame to fullscreen mode
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);

        // Ensure the game panel is focused to receive key events
        gamePanel.requestFocusInWindow();
    }
}