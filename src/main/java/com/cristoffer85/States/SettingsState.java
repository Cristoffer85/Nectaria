package com.cristoffer85.States;
import com.cristoffer85.States.StatesResources.StateDesign;
import com.cristoffer85.States.StatesResources.StateDefinitions;
import com.cristoffer85.Main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SettingsState extends StateDesign {

    private static final int BOTTOM_PANEL_OFFSET = 280;

    private GamePanel gamePanel;

    public SettingsState(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setLayout(new BorderLayout());

        // Uses Borderlayout to place the panels in the correct vertical positions on the screen.
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

        // Create a panel with GridBagLayout for the labels and dropdowns
        JPanel settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setBackground(buttonPanel.getBackground());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Resolution dropdown
        JLabel resolutionLabel = new JLabel("Window size:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        settingsPanel.add(resolutionLabel, gbc);

        String[] resolutions = {"960x540", "1280x720", "1920x1080"};
        JComboBox<String> resolutionDropdown = new JComboBox<>(resolutions);
        resolutionDropdown.setMaximumSize(new Dimension(200, 30));
        gbc.gridx = 1;
        settingsPanel.add(resolutionDropdown, gbc);
        resolutionDropdown.addActionListener(e -> {
            String selectedResolution = (String) resolutionDropdown.getSelectedItem();
            if (selectedResolution != null) {
                String[] dimensions = selectedResolution.split("x");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);
                changeResolution(width, height);
            }
        });

        // Scale factor dropdown
        JLabel scaleFactorLabel = new JLabel("Graphic scale:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        settingsPanel.add(scaleFactorLabel, gbc);

        Map<String, Double> scaleFactorsMap = new HashMap<>();
        scaleFactorsMap.put("SNES", 0.5);
        scaleFactorsMap.put("Small", 1.0);

        String[] scaleFactorNames = scaleFactorsMap.keySet().toArray(new String[0]);
        JComboBox<String> scaleFactorDropdown = new JComboBox<>(scaleFactorNames);
        scaleFactorDropdown.setMaximumSize(new Dimension(200, 30));
        gbc.gridx = 1;
        settingsPanel.add(scaleFactorDropdown, gbc);
        scaleFactorDropdown.addActionListener(e -> {
            String selectedScaleFactorName = (String) scaleFactorDropdown.getSelectedItem();
            if (selectedScaleFactorName != null) {
                double scaleFactor = scaleFactorsMap.get(selectedScaleFactorName);
                gamePanel.setScaleFactor(scaleFactor);
            }
        });

        buttonPanel.add(settingsPanel);
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton backButton = regularMenuButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.changeGameState(StateDefinitions.MAIN_MENU);
            }
        });
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createVerticalStrut(BOTTOM_PANEL_OFFSET));

        return buttonPanel;
    }

    public void changeResolution(int width, int height) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gamePanel);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        gamePanel.changeResolution(width, height);
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}