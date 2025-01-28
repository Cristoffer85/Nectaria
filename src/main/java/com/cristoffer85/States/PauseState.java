package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StatesDefinitions;

import javax.swing.*;
import java.awt.*;

public class PauseState extends JPanel {
    private Image gameStateImage;

    public PauseState(GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create and style the "Resume Game" button
        JButton resumeButton = new JButton("RESUME GAME");
        resumeButton.addActionListener(e -> gamePanel.setGameState(StatesDefinitions.GAME));
        styleButton(resumeButton);

        // Create and style the "Save Game" button
        JButton saveButton = new JButton("SAVE GAME");
        saveButton.addActionListener(e -> gamePanel.saveGame());
        styleButton(saveButton);

        // Create and style the "Load Game" button
        JButton loadButton = new JButton("LOAD GAME");
        loadButton.addActionListener(e -> gamePanel.loadGame());
        styleButton(loadButton);

        // Create and style the "Quit Game" button
        JButton quitButton = new JButton("QUIT GAME");
        quitButton.addActionListener(e -> gamePanel.setGameState(StatesDefinitions.MAIN_MENU));
        styleButton(quitButton);

        // Add components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(resumeButton, gbc);

        gbc.gridy = 1;
        add(saveButton, gbc);

        gbc.gridy = 2;
        add(loadButton, gbc);

        gbc.gridy = 3;
        add(quitButton, gbc);
    }

    public void setGameStateImage(Image gameStateImage) {
        this.gameStateImage = gameStateImage;
    }

    private void styleButton(JButton button) {
        button.setForeground(Color.BLACK);
        button.setBackground(Color.ORANGE);
        button.setMargin(new Insets(5, 5, 1, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameStateImage != null) {
            g.drawImage(gameStateImage, 0, 0, getWidth(), getHeight(), this);
        }
        g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent overlay
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}