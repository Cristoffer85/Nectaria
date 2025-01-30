package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StatesDefinitions;

import javax.swing.*;
import java.io.IOException;
import java.awt.*;

public class MainMenuState extends JPanel {
    private Image logoImage;

    public MainMenuState(GamePanel gamePanel) {
        setLayout(new BorderLayout());

        // Create a panel for the top right corner button
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.setBackground(Color.ORANGE); // Set background color
        JButton switchUserButton = new JButton("Switch User");
        switchUserButton.addActionListener(e -> {
            gamePanel.setGameState(StatesDefinitions.INITIAL_STATE); // Switch to initial state
        });
        styleMinimalButton(switchUserButton);
        topRightPanel.add(switchUserButton);
        add(topRightPanel, BorderLayout.NORTH);

        // Create a panel for the centered content
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.ORANGE); // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load and scale logo image
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Logo.png"));
            logoImage = scaleImage(logoIcon.getImage(), 1.5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and style the "Resume" button
        JButton resumeButton = new JButton("RESUME");
        resumeButton.addActionListener(e -> {
            gamePanel.loadGame(); // Load the game for the selected profile
        });
        styleButton(resumeButton);

        // Create and style the "Start New Game" button
        JButton newGameButton = new JButton("START NEW GAME");
        newGameButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to start a new game? Your whole progress so far will be erased!",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (response == JOptionPane.YES_OPTION) {
                gamePanel.resetGame();
                gamePanel.saveGame();
                gamePanel.setGameState(StatesDefinitions.GAME);
            }
        });
        styleButton(newGameButton);

        // Add components to the center panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel(new ImageIcon(logoImage)), gbc);

        gbc.gridy = 1;
        centerPanel.add(resumeButton, gbc);

        gbc.gridy = 2;
        centerPanel.add(newGameButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    private Image scaleImage(Image image, double scale) {
        int width = (int) (image.getWidth(null) * scale);
        int height = (int) (image.getHeight(null) * scale);
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private void styleButton(JButton button) {
        try {
            Font retroFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Retro-pixelfont.ttf")).deriveFont(44f);
            button.setFont(retroFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        button.setForeground(Color.BLACK);
        button.setBackground(Color.ORANGE);
        button.setMargin(new Insets(5, 5, 1, 2));
    }

    private void styleMinimalButton(JButton button) {
        button.setForeground(Color.BLACK);
        button.setBackground(Color.ORANGE);
        button.setMargin(new Insets(2, 2, 2, 2));
        button.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}