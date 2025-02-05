package com.cristoffer85.States;
import com.cristoffer85.States.StatesResources.StateMenuDesign;
import com.cristoffer85.States.StatesResources.StateDefinitions;
import com.cristoffer85.Main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsState extends StateMenuDesign {

    private static final int BOTTOM_PANEL_OFFSET = 280;
    private int baseWidth = 960;        // Default base resolution width
    private int baseHeight = 540;       // Default base resolution height
    private int scaleFactor = 2;        // Default scale factor

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

        // Resolution dropdown
        String[] resolutions = {"960x540", "1280x720", "1920x1080"};
        JComboBox<String> resolutionDropdown = new JComboBox<>(resolutions);
        resolutionDropdown.setMaximumSize(new Dimension(200, 30));
        resolutionDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        resolutionDropdown.addActionListener(e -> {
            String selectedResolution = (String) resolutionDropdown.getSelectedItem();
            if (selectedResolution != null) {
                String[] dimensions = selectedResolution.split("x");
                baseWidth = Integer.parseInt(dimensions[0]);
                baseHeight = Integer.parseInt(dimensions[1]);
                changeResolution();
            }
        });
        panel.add(resolutionDropdown);
        panel.add(Box.createVerticalStrut(20));

        // Scale factor dropdown
        String[] scaleFactors = {"1", "2", "3"};
        JComboBox<String> scaleFactorDropdown = new JComboBox<>(scaleFactors);
        scaleFactorDropdown.setMaximumSize(new Dimension(200, 30));
        scaleFactorDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        scaleFactorDropdown.addActionListener(e -> {
            scaleFactor = Integer.parseInt((String) scaleFactorDropdown.getSelectedItem());
            changeResolution();
        });
        panel.add(scaleFactorDropdown);
        panel.add(Box.createVerticalStrut(20));

        return panel;
    }

    private JPanel bottomPanel() {
        JPanel buttonPanel = createVerticalPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

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

    private void changeResolution() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gamePanel);
        frame.setSize(baseWidth * scaleFactor, baseHeight * scaleFactor);
        frame.setLocationRelativeTo(null);
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    public void changeResolution(int width, int height) {
        this.baseWidth = width;
        this.baseHeight = height;
        changeResolution();
    }
}