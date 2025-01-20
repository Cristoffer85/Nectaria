package com.cristoffer85;

import javax.swing.*;

import com.cristoffer85.Main.GamePanel;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("New proper 2D collision game");
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.setSize(600, 600); // Set window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}