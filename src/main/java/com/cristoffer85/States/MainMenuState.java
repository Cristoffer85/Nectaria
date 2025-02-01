package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StatesDefinitions;

import javax.swing.*;
import java.io.IOException;
import java.awt.*;

public class MainMenuState extends JPanel {
    private Image logoImage;

    public MainMenuState(GamePanel gamePanel) {
        // ------ Switch user button, has its own JPanel with BorderLayout (BorderLayout.NORTH + FlowLayout.RIGHT), aligns it top-right
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        setLayout(new BorderLayout());
        topRightPanel.setBackground(Color.ORANGE);
        JButton switchUserButton = new JButton("Switch User");
        switchUserButton.addActionListener(e -> {
            gamePanel.changeGameState(StatesDefinitions.INITIAL_STATE);
        });
        styleSwitchUserButton(switchUserButton);
        topRightPanel.add(switchUserButton);
        add(topRightPanel, BorderLayout.NORTH);
        // ------------------------------------------------------------

        // ------ Center JPanel with GridBagLayout (gbc, which aligns components in a grid - x and y coordinates)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.ORANGE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load and scale logo image
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Logo.png"));
            logoImage = scaleImage(logoIcon.getImage(), 1.5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and style "Resume" button
        JButton resumeButton = new JButton("RESUME");
        resumeButton.addActionListener(e -> {
            gamePanel.loadGame(); // Load current, last saved game for the selected profile
        });
        styleRegularButton(resumeButton);

        // Create and style "Start New Game" button
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
                gamePanel.changeGameState(StatesDefinitions.GAME);
            }
        });
        styleRegularButton(newGameButton);

        // #### Add components to grid ####
        gbc.gridy = 0;
        centerPanel.add(Box.createVerticalStrut(20), gbc);

        // Add logo
        gbc.gridy = 1;
        centerPanel.add(new JLabel(new ImageIcon(logoImage)), gbc);

        gbc.gridy = 2;
        centerPanel.add(Box.createVerticalStrut(20), gbc);

        // Add buttons
        gbc.gridy = 3;
        centerPanel.add(resumeButton, gbc);

        gbc.gridy = 4;
        centerPanel.add(newGameButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
        // ------------------------------------------------------------
    }
    

    private Image scaleImage(Image image, double scale) {
        int width = (int) (image.getWidth(null) * scale);
        int height = (int) (image.getHeight(null) * scale);
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private void styleRegularButton(JButton button) {
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

    private void styleSwitchUserButton(JButton button) {
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