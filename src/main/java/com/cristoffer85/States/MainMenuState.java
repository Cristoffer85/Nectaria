package com.cristoffer85.States;

import javax.swing.*;
import java.awt.*;

import com.cristoffer85.Main.GamePanel;

public class MainMenuState extends JPanel {
    public MainMenuState(GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> gamePanel.setGameState(StatesDefinitions.GAME));

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(newGameButton, gbc);
    }
}