package com.cristoffer85.States;
import com.cristoffer85.States.StatesResources.StateDesign;
import com.cristoffer85.States.StatesResources.StateDefinitions;
import com.cristoffer85.Main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        // Resolution dropdown
        String[] resolutions = {"960x540", "1280x720", "1920x1080"};
        JComboBox<String> resolutionDropdown = new JComboBox<>(resolutions);
        resolutionDropdown.setMaximumSize(new Dimension(200, 30));
        resolutionDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        resolutionDropdown.addActionListener(e -> {
            String selectedResolution = (String) resolutionDropdown.getSelectedItem();
            if (selectedResolution != null) {
                String[] dimensions = selectedResolution.split("x");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);
                changeResolution(width, height);
            }
        });
        panel.add(resolutionDropdown);
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

    public void changeResolution(int width, int height) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(gamePanel);
        frame.setSize(width * 2, height * 2); // Use the default scale factor of 2
        frame.setLocationRelativeTo(null);
        gamePanel.revalidate();
        gamePanel.repaint();
    }
}