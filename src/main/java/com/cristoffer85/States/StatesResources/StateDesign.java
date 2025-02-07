package com.cristoffer85.States.StatesResources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;

public class StateDesign extends JPanel {
    protected Image logoImage;
    private JLabel titleLabel;
    private JLabel logoLabel;
    private int originalLogoWidth;
    private int originalLogoHeight;

    protected static final Color BACKGROUND_COLOR = Color.ORANGE;
    protected static final int MIDDLE_PANEL_OFFSET = 36;
    protected static final Color BUTTON_COLOR = Color.ORANGE;
    protected Font MENUBUTTON_FONTANDSIZE = loadCustomFont("/Retro-pixelfont.ttf", 44f);
    protected static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);
    protected static final Font SWITCH_USER_FONTANDSIZE = new Font("Arial", Font.PLAIN, 12);
    protected static final int MENUBUTTON_VERTICAL_SPACING = 20;

    // --------------------------------- Scaling -----------------------------------
    private static final Map<Dimension, Double> LOGO_SCALE = new HashMap<>();
    private static final Map<Dimension, Float> MENUBUTTON_FONTSIZE = new HashMap<>();

    static {
        LOGO_SCALE.put(new Dimension(1920, 1080), 2.0);
        LOGO_SCALE.put(new Dimension(1280, 720), 1.0);
        LOGO_SCALE.put(new Dimension(960, 540), 0.7);

        MENUBUTTON_FONTSIZE.put(new Dimension(1920, 1080), 44f * 1.0f);
        MENUBUTTON_FONTSIZE.put(new Dimension(1280, 720), 44f * 0.5f);
        MENUBUTTON_FONTSIZE.put(new Dimension(960, 540), 44f * 0.3f);
    }
    // ------------------------------------------------------------------------------

    protected JPanel createVerticalPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }

    protected JPanel createCombinedVerticalAndHorizontalPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(BACKGROUND_COLOR);
        return panel;
    }

    protected JButton regularMenuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        button.setFont(MENUBUTTON_FONTANDSIZE);
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

    public Font loadCustomFont(String path, float size) {
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
            logoImage = logoIcon.getImage();

            originalLogoWidth = logoImage.getWidth(null);
            originalLogoHeight = logoImage.getHeight(null);

            logoLabel = new JLabel(new ImageIcon(logoImage));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            logoPanel.add(Box.createVerticalStrut(100));
            logoPanel.add(logoLabel);
            logoPanel.add(Box.createVerticalGlue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void addTitle(JPanel panel, String title) {
        panel.add(Box.createVerticalGlue());
        titleLabel = new JLabel(title);
        titleLabel.setFont(MENUBUTTON_FONTANDSIZE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(MIDDLE_PANEL_OFFSET));
        panel.add(Box.createVerticalGlue());
        panel.setBackground(BACKGROUND_COLOR);
    }

    public StateDesign() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustComponentSizes();
            }
        });
    }

    private void adjustComponentSizes() {
        Dimension size = getSize();
        float overallScaleFactor = Math.min(size.width / 1920f, size.height / 1080f);
    
        double logoScale = LOGO_SCALE.getOrDefault(new Dimension(size.width, size.height), 1.0);
        float buttonFontSize = MENUBUTTON_FONTSIZE.getOrDefault(new Dimension(size.width, size.height), 44f * overallScaleFactor);
    
        Font scaledFont = MENUBUTTON_FONTANDSIZE.deriveFont(buttonFontSize);
    
        for (Component component : getComponents()) {
            if (component instanceof JPanel) {
                for (Component subComponent : ((JPanel) component).getComponents()) {
                    if (subComponent instanceof JButton) {
                        JButton button = (JButton) subComponent;
                        button.setFont(button.getFont().equals(SWITCH_USER_FONTANDSIZE) ? SWITCH_USER_FONTANDSIZE : scaledFont);
                    } else if (subComponent instanceof JLabel) {
                        JLabel label = (JLabel) subComponent;
                        if (label.getIcon() != null && label == logoLabel) {
                            int width = (int) (originalLogoWidth * logoScale);
                            int height = (int) (originalLogoHeight * logoScale);
                            Image scaledImage = logoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                            label.setIcon(new ImageIcon(scaledImage));
                        } else {
                            label.setFont(scaledFont);
                        }
                    }
                }
            }
        }
        revalidate();
        repaint();
    }
}