package com.cristoffer85.States;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import com.cristoffer85.Main.GamePanel;

public class MainMenuState extends JPanel {
    private Image logoImage;

    public MainMenuState(GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Load logo image
        try {
            logoImage = new ImageIcon(getClass().getResource("/Logo.png")).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and style the "New Game" button
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> gamePanel.setGameState(StatesDefinitions.GAME));
        styleButton(newGameButton);

        // Add components to the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel(new ImageIcon(logoImage)), gbc);

        gbc.gridy = 1;
        add(newGameButton, gbc);
    }

    private void styleButton(JButton button) {
        try {
            Font retroFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Retro-pixelfont.ttf")).deriveFont(52f);
            button.setFont(retroFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}