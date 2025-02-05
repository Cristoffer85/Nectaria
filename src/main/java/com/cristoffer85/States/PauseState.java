package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StateDefinitions;
import com.cristoffer85.States.StatesResources.StateMenuDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PauseState extends JPanel {
    private Image gameStateImage;
    private static final Font BUTTON_FONT = new StateMenuDesign().loadCustomFont("/Retro-pixelfont.ttf", 24f);

    public PauseState(GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.ORANGE);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        buttonPanel.setPreferredSize(new Dimension(300, 400));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create and style the "Resume Game" button
        JButton resumeButton = new JButton("RESUME");
        resumeButton.addActionListener(e -> gamePanel.changeGameState(StateDefinitions.GAME));
        styleButton(resumeButton);

        // Create and style the "Load Game" button
        JButton loadButton = new JButton("LOAD GAME");
        loadButton.addActionListener(e -> gamePanel.loadGame());
        styleButton(loadButton);

        // Create and style the "Quit Game" button
        JButton quitButton = new JButton("SAVE & QUIT");
        quitButton.addActionListener(e -> {
            gamePanel.saveGame();
            gamePanel.changeGameState(StateDefinitions.MAIN_MENU);
        });
        styleButton(quitButton);

        // Add buttons to the button panel
        buttonPanel.add(resumeButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(loadButton);
        buttonPanel.add(Box.createVerticalStrut(250));
        buttonPanel.add(quitButton);

        // Center buttons horizontally
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the button panel to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(buttonPanel, gbc);
    }

    public void freezeGameBackground(GamePanel gamePanel, GameState gameState) {
        BufferedImage gameStateImage = new BufferedImage(gamePanel.getWidth(), gamePanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = gameStateImage.createGraphics();
        gameState.paint(g2d);
        g2d.dispose();
        this.gameStateImage = gameStateImage;
    }

    private void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
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
        g.setColor(new Color(0, 0, 0, 150)); // Last value == changes opacity, of pause screen overlay
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}