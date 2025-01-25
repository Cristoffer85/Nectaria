package com.cristoffer85.States;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import com.cristoffer85.Main.GamePanel;

public class MainMenuState extends JPanel {
    private Image logoImage;

    public MainMenuState(GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        // Sets a layout manager/Grid to position the components, GridBagLayout() is used to position the components in a grid of rows and columns
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load and scale logo image
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Logo.png"));
            logoImage = scaleImage(logoIcon.getImage(), 1.5); // Adjust the scaling factor as needed
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and style the "New Game" button
        JButton newGameButton = new JButton("NEW GAME");
        newGameButton.addActionListener(e -> gamePanel.setGameState(StatesDefinitions.GAME));
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
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 0));
        button.setMargin(new Insets(5, 10, 5, 10)); // Adjust the padding around the text
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}