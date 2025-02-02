package com.cristoffer85.States.StatesResources;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.ActionListener;

public class StateMenuDesign extends JPanel {
    protected static final Color BACKGROUND_COLOR = Color.ORANGE;
    protected static final int MIDDLE_PANEL_OFFSET = 36;
    protected static final Color BUTTON_COLOR = Color.ORANGE;
    protected final Font MENU_BUTTON_FONTANDSIZE = loadFont("/Retro-pixelfont.ttf", 44f);
    protected static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);
    protected static final Font SWITCH_USER_FONTANDSIZE = new Font("Arial", Font.PLAIN, 12);
    protected static final int MENUBUTTON_VERTICAL_SPACING = 20;

    protected Image logoImage;

    protected JPanel createSimpleVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }

    protected JPanel createBothVertAndHorizPanelWithFLowLayout(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }

    protected JButton menuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(MENU_BUTTON_FONTANDSIZE);
        button.setForeground(Color.BLACK);
        button.setBackground(BUTTON_COLOR);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMargin(new Insets(5, 5, 1, 2));
        return button;
    }

    protected JButton switchUserButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(SWITCH_USER_FONTANDSIZE);
        button.setForeground(Color.BLACK);
        button.setBackground(BUTTON_COLOR);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMargin(new Insets(5, 5, 1, 2));
        return button;
    }

    protected Font loadFont(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(path)).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return DEFAULT_FONT;
        }
    }

    protected void addLogo(JPanel logoPanel) {
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

    protected Image scaleImage(Image image, double scale) {
        int width = (int) (image.getWidth(null) * scale);
        int height = (int) (image.getHeight(null) * scale);
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}