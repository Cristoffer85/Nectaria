package com.cristoffer85.States;
import com.cristoffer85.States.StatesResources.StateDesign;
import com.cristoffer85.States.StatesResources.StateDefinitions;
import com.cristoffer85.Main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsState extends StateDesign {

    private static final int BOTTOM_PANEL_OFFSET = 280;
    private static final String[] RESOLUTIONS = {"1920x1080", "1280x720", "960x540"};
    private static final Map<String, Double> SCALE_FACTORS_MAP = new HashMap<>();

    static {
        SCALE_FACTORS_MAP.put("SNES", 0.5);
        SCALE_FACTORS_MAP.put("Small", 1.0);
    }

    private GamePanel gamePanel;

    public SettingsState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setLayout(new BorderLayout());
        add(middlePanel(), BorderLayout.CENTER);
        add(bottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel middlePanel() {
        JPanel panel = createVerticalPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addTitle(panel, "SETTINGS");
        return panel;
    }

    private JPanel bottomPanel() {
        JPanel buttonPanel = createVerticalPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JPanel settingsPanel = createSettingsPanel();
        buttonPanel.add(settingsPanel);
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton backButton = regularMenuButton("Back", e -> gamePanel.changeGameState(StateDefinitions.MAIN_MENU));
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createVerticalStrut(BOTTOM_PANEL_OFFSET));

        return buttonPanel;
    }

    private JPanel createSettingsPanel() {
        JPanel settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = createGbc();

        addComponent(settingsPanel, createLabel("Fullscreen:"), gbc, 0, 0);
        addComponent(settingsPanel, createFullscreenCheckbox(), gbc, 1, 0);

        addComponent(settingsPanel, createLabel("Window size:"), gbc, 0, 1);
        addComponent(settingsPanel, createResolutionDropdown(), gbc, 1, 1);

        addComponent(settingsPanel, createLabel("Graphic scale:"), gbc, 0, 2);
        addComponent(settingsPanel, createScaleFactorDropdown(), gbc, 1, 2);

        return settingsPanel;
    }

    private GridBagConstraints createGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    private void addComponent(JPanel panel, JComponent component, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setBackground(BACKGROUND_COLOR);
        label.setOpaque(true);
        return label;
    }

    private JCheckBox createFullscreenCheckbox() {
        JCheckBox checkbox = new JCheckBox();
        checkbox.setSelected(true);
        checkbox.setBackground(BACKGROUND_COLOR); 
        checkbox.addActionListener(e -> setFullscreen(checkbox.isSelected()));
        return checkbox;
    }

    private JComboBox<String> createResolutionDropdown() {
        JComboBox<String> dropdown = new JComboBox<>(RESOLUTIONS);
        dropdown.setMaximumSize(new Dimension(200, 30));
        dropdown.addActionListener(e -> {
            String selectedResolution = (String) dropdown.getSelectedItem();
            if (selectedResolution != null) {
                String[] dimensions = selectedResolution.split("x");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);
                changeResolution(width, height);
            }
        });
        return dropdown;
    }

    private JComboBox<String> createScaleFactorDropdown() {
        String[] scaleFactorNames = SCALE_FACTORS_MAP.keySet().toArray(new String[0]);
        JComboBox<String> dropdown = new JComboBox<>(scaleFactorNames);
        dropdown.setMaximumSize(new Dimension(200, 30));
        dropdown.setSelectedItem("SNES");
        dropdown.addActionListener(e -> {
            String selectedScaleFactorName = (String) dropdown.getSelectedItem();
            if (selectedScaleFactorName != null) {
                double scaleFactor = SCALE_FACTORS_MAP.get(selectedScaleFactorName);
                gamePanel.setScaleFactor(scaleFactor);
            }
        });
        return dropdown;
    }

    public void changeResolution(int width, int height) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gamePanel);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        gamePanel.changeResolution(width, height);
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public void setFullscreen(boolean fullscreen) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gamePanel);
        frame.dispose();
        frame.setExtendedState(fullscreen ? JFrame.MAXIMIZED_BOTH : JFrame.NORMAL);
        frame.setUndecorated(fullscreen);
        frame.setVisible(true);
    }
}