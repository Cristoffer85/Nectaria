package com.cristoffer85.States;

import com.cristoffer85.States.StatesResources.StatesDefinitions;
import com.cristoffer85.Main.GamePanel;

import javax.swing.*;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.*;

public class MainMenuState extends JPanel {
    private Image logoImage;

    public MainMenuState(GamePanel gamePanel) {
        // ------------ Top-right panel for the "Switch User" button ------------
        JPanel topRightPanel = createPanel(new FlowLayout(FlowLayout.RIGHT), Color.ORANGE);
        setLayout(new BorderLayout());
        JButton switchUserButton = createButton("Switch User", e -> gamePanel.changeGameState(StatesDefinitions.INITIAL_STATE), new Font("Arial", Font.PLAIN, 12));
        topRightPanel.add(switchUserButton);
        add(topRightPanel, BorderLayout.NORTH);

        // ------------ Logo panel ------------
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(Color.ORANGE);
        addLogo(logoPanel);
        add(logoPanel, BorderLayout.CENTER);

        // ------------ Bottom panel for buttons ------------
        JPanel containerPanel = createPanel(new BorderLayout(), Color.ORANGE);
            // Adds a button panel - inside container panel - to more easily position the buttons vertically and horizontally within its "South" border layout
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setBackground(Color.ORANGE);

            buttonPanel.add(createButton("RESUME", e -> gamePanel.loadGame(), loadFont("/Retro-pixelfont.ttf", 44f)));
            buttonPanel.add(Box.createVerticalStrut(20));
            buttonPanel.add(createButton("START NEW GAME", e -> startNewGame(gamePanel), loadFont("/Retro-pixelfont.ttf", 44f)));
            buttonPanel.add(Box.createVerticalStrut(20));
            buttonPanel.add(createButton("TEST BUTTON", e -> System.out.println("Test Button Clicked"), loadFont("/Retro-pixelfont.ttf", 44f)));
            buttonPanel.add(Box.createVerticalStrut(300));

            containerPanel.add(buttonPanel, BorderLayout.CENTER);
        add(containerPanel, BorderLayout.SOUTH);
    }

    private JPanel createPanel(LayoutManager layout, Color background) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(background);
        return panel;
    }

    private JButton createButton(String text, ActionListener action, Font font) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(font);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.ORANGE);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMargin(new Insets(5, 5, 1, 2));
        return button;
    }

    private Font loadFont(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(path)).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, 12);
        }
    }

    private void addLogo(JPanel logoPanel) {
        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/Logo.png"));
            logoImage = scaleImage(logoIcon.getImage(), 1.5);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            logoPanel.add(Box.createVerticalStrut(100));
            logoPanel.add(logoLabel);
            logoPanel.add(Box.createVerticalGlue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startNewGame(GamePanel gamePanel) {
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
    }

    private Image scaleImage(Image image, double scale) {
        int width = (int) (image.getWidth(null) * scale);
        int height = (int) (image.getHeight(null) * scale);
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}