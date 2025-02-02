package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StateDefinitions;
import com.cristoffer85.Main.MainResources.CRUDProfile;
import com.cristoffer85.States.StatesResources.StateMenuDesign;

import javax.swing.*;
import java.awt.*;

public class InitialState extends StateMenuDesign {

    private static final int BOTTOM_PANEL_OFFSET = 280;

    public InitialState(GamePanel gamePanel) {
        setLayout(new BorderLayout());

        // Uses borderlayout to place the panels in the correct position on the screen
        add(middlePanel(), BorderLayout.CENTER);
        add(bottomPanel(gamePanel), BorderLayout.SOUTH);
    }

    private JPanel middlePanel() {
        JPanel logoPanel = createSimpleVerticalPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.add(Box.createVerticalStrut(MIDDLE_PANEL_OFFSET));
        addLogo(logoPanel);
        return logoPanel;
    }

    private JPanel bottomPanel(GamePanel gamePanel) {
        JPanel buttonPanel = createSimpleVerticalPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(menuButton("CREATE NEW PROFILE", e -> createNewProfile(gamePanel)));
        buttonPanel.add(Box.createVerticalStrut(MENUBUTTON_VERTICAL_SPACING));
        buttonPanel.add(menuButton("SELECT EXISTING PROFILE", e -> selectProfile(gamePanel)));
        buttonPanel.add(Box.createVerticalStrut(BOTTOM_PANEL_OFFSET));

        return buttonPanel;
    }

    // Helper methods
    private void createNewProfile(GamePanel gamePanel) {
        String profileName = JOptionPane.showInputDialog("Enter profile name:");
        if (profileName != null && !profileName.trim().isEmpty()) {
            CRUDProfile.createProfile(profileName);
            gamePanel.setProfileName(profileName);
            gamePanel.resetGame();
            gamePanel.changeGameState(StateDefinitions.GAME);
        }
    }

    private void selectProfile(GamePanel gamePanel) {
        String[] profiles = CRUDProfile.getCurrentProfiles();
        if (profiles.length > 0) {
            String selectedProfile = (String) JOptionPane.showInputDialog(
                    this,
                    "Select a profile:",
                    "Select Profile",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    profiles,
                    profiles[0]
            );
            if (selectedProfile != null) {
                gamePanel.setProfileName(selectedProfile);
                gamePanel.loadGame();
                gamePanel.changeGameState(StateDefinitions.MAIN_MENU);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No profiles found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}