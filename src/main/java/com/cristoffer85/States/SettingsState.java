package com.cristoffer85.States;
import com.cristoffer85.States.StatesResources.StateMenuDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsState extends StateMenuDesign {
    private JPanel settingsPanel;

    public SettingsState() {
        settingsPanel = createSimpleVerticalPanel();
        initializeSettingsMenu();
    }

    private void initializeSettingsMenu() {
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(MENU_BUTTON_FONTANDSIZE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsPanel.add(titleLabel);
        settingsPanel.add(Box.createVerticalStrut(MIDDLE_PANEL_OFFSET));

        JButton backButton = menuButton("Back", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to return to the previous state
            }
        });
        settingsPanel.add(backButton);
        
        // Additional settings options can be added here
    }

    public JPanel getSettingsPanel() {
        return settingsPanel;
    }
}
