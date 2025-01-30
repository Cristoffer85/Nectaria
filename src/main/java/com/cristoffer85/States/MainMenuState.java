package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;

import javax.swing.*;
import java.io.IOException;
import java.awt.*;

public class MainMenuState extends JPanel {
    private Image logoImage;

    public MainMenuState(GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load and scale logo image
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Logo.png"));
            logoImage = scaleImage(logoIcon.getImage(), 1.5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and style the "New Game" button
        JButton newGameButton = new JButton("NEW GAME");
        newGameButton.addActionListener(e -> {
            gamePanel.loadGame(); // Load the game for the selected profile
        });
        styleButton(newGameButton);

        // Add components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel(new ImageIcon(logoImage)), gbc);

        gbc.gridy = 1;
        add(newGameButton, gbc);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}