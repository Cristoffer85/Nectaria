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

        // Add components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(resumeButton, gbc);
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