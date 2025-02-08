package com.cristoffer85.States.StatesResources;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MenuScaling {
    private static final Map<Dimension, Double> SCALE_LOGO = new HashMap<>();
    private static final Map<Dimension, Float> SCALE_MENUBUTTON = new HashMap<>();

    static {
        SCALE_LOGO.put(new Dimension(1920, 1080), 2.0);
        SCALE_LOGO.put(new Dimension(1280, 720), 1.2);
        SCALE_LOGO.put(new Dimension(960, 540), 0.7);

        SCALE_MENUBUTTON.put(new Dimension(1920, 1080), 44f * 1.0f);
        SCALE_MENUBUTTON.put(new Dimension(1280, 720), 44f * 0.5f);
        SCALE_MENUBUTTON.put(new Dimension(960, 540), 44f * 0.3f);
    }

    public static void adjustComponentSizes(JPanel panel, Font menuButtonFontAndSize, Font switchUserFontAndSize, Image logoImage, JLabel logoLabel, int originalLogoWidth, int originalLogoHeight) {
        Dimension size = panel.getSize();
        float overallScaleFactor = Math.min(size.width / 1920f, size.height / 1080f);

        double logoScale = SCALE_LOGO.getOrDefault(new Dimension(size.width, size.height), 1.0);
        float buttonFontSize = SCALE_MENUBUTTON.getOrDefault(new Dimension(size.width, size.height), 44f * overallScaleFactor);

        Font scaledFont = menuButtonFontAndSize.deriveFont(buttonFontSize);

        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel) {
                adjustPanelComponents((JPanel) component, scaledFont, logoScale, switchUserFontAndSize, logoImage, logoLabel, originalLogoWidth, originalLogoHeight);
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    private static void adjustPanelComponents(JPanel panel, Font scaledFont, double logoScale, Font switchUserFontAndSize, Image logoImage, JLabel logoLabel, int originalLogoWidth, int originalLogoHeight) {
        for (Component subComponent : panel.getComponents()) {
            if (subComponent instanceof JButton) {
                adjustSwitchUserButton((JButton) subComponent, scaledFont, switchUserFontAndSize);
            } else if (subComponent instanceof JLabel) {
                adjustLabel((JLabel) subComponent, scaledFont, logoScale, logoImage, logoLabel, originalLogoWidth, originalLogoHeight);
            }
        }
    }

    private static void adjustSwitchUserButton(JButton button, Font scaledFont, Font switchUserFontAndSize) {
        button.setFont(button.getFont().equals(switchUserFontAndSize) ? switchUserFontAndSize : scaledFont);
    }

    private static void adjustLabel(JLabel label, Font scaledFont, double logoScale, Image logoImage, JLabel logoLabel, int originalLogoWidth, int originalLogoHeight) {
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
