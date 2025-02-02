package com.cristoffer85.States;

import com.cristoffer85.States.StatesResources.StatesDefinitions;
import com.cristoffer85.Main.GamePanel;

import javax.swing.*;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.*;

public class MainMenuState extends JPanel {
    private static final Color BACKGROUND_COLOR = Color.ORANGE;
    private static final Color BUTTON_COLOR = Color.ORANGE;
    private static final Font SWITCH_USER_FONTANDSIZE = new Font("Arial", Font.PLAIN, 12);
    private final Font MENU_BUTTON_FONTANDSIZE = loadFont("/Retro-pixelfont.ttf", 44f);
    private static final int BOTTOM_PANEL_OFFSET = 280;
    private static final int MENUBUTTON_VERTICAL_SPACING = 20;

    private Image logoImage;

    public MainMenuState(GamePanel gamePanel) {
        setLayout(new BorderLayout());
        
        // Uses Borderlayout to place the panels in the correct vertical positions on the screen. A combination of BorderLayout and FlowLayout is later also used to alter contents position horizontally.
        add(topPanel(gamePanel), BorderLayout.NORTH);
        add(middlePanel(), BorderLayout.CENTER);
        add(bottomPanel(gamePanel), BorderLayout.SOUTH);
    }

    private JPanel topPanel(GamePanel gamePanel) {
        JPanel switchUserPanel = createBothVertAndHorizPanelWithFLowLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton switchUserButton = switchUserButton("Switch User", e -> gamePanel.changeGameState(StatesDefinitions.INITIAL_STATE));
        switchUserPanel.add(switchUserButton);
        return switchUserPanel;
    }

    private JPanel middlePanel() {
        JPanel logoPanel = createSimpleVerticalPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        addLogo(logoPanel);
        return logoPanel;
    }

    private JPanel bottomPanel(GamePanel gamePanel) {
        JPanel buttonPanel = createSimpleVerticalPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(menuButton("RESUME", e -> gamePanel.loadGame()));
        buttonPanel.add(Box.createVerticalStrut(MENUBUTTON_VERTICAL_SPACING));
        buttonPanel.add(menuButton("START NEW GAME", e -> startNewGame(gamePanel)));
        buttonPanel.add(Box.createVerticalStrut(MENUBUTTON_VERTICAL_SPACING));
        buttonPanel.add(menuButton("TEST BUTTON", e -> System.out.println("Test Button Clicked")));
        buttonPanel.add(Box.createVerticalStrut(BOTTOM_PANEL_OFFSET));

        return buttonPanel;
    }

    // Helper methods
    private JPanel createSimpleVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }

    private JPanel createBothVertAndHorizPanelWithFLowLayout(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }

    private JButton menuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(MENU_BUTTON_FONTANDSIZE);
        button.setForeground(Color.BLACK);
        button.setBackground(BUTTON_COLOR);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMargin(new Insets(5, 5, 1, 2));
        return button;
    }

    private JButton switchUserButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(SWITCH_USER_FONTANDSIZE);
        button.setForeground(Color.BLACK);
        button.setBackground(BUTTON_COLOR);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMargin(new Insets(5, 5, 1, 2));
        return button;
    }

    private Font loadFont(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(path)).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return SWITCH_USER_FONTANDSIZE;
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
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}