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

    public SettingsState(GamePanel gamePanel) {
        setLayout(new BorderLayout());

        // Uses Borderlayout to place the panels in the correct vertical positions on the screen.
        add(middlePanel(), BorderLayout.CENTER);
        add(bottomPanel(gamePanel), BorderLayout.SOUTH);
    }

    private JPanel middlePanel() {
        JPanel panel = createVerticalPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addTitle(panel, "Settings");
        return panel;
    }

    private JPanel bottomPanel(GamePanel gamePanel) {
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
}