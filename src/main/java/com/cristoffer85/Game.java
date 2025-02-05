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
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}