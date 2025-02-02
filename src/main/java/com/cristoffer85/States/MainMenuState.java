package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StateDefinitions;
import com.cristoffer85.States.StatesResources.StateMenuDesign;

import javax.swing.*;
import java.awt.*;

public class MainMenuState extends StateMenuDesign {

    private static final int BOTTOM_PANEL_OFFSET = 280;

    public MainMenuState(GamePanel gamePanel) {
        setLayout(new BorderLayout());

        // Uses Borderlayout to place the panels in the correct vertical positions on the screen. A combination of BorderLayout and FlowLayout is later also used to alter contents position horizontally.
        add(topPanel(gamePanel), BorderLayout.NORTH);
        add(middlePanel(), BorderLayout.CENTER);
        add(bottomPanel(gamePanel), BorderLayout.SOUTH);
    }

    private JPanel topPanel(GamePanel gamePanel) {
        JPanel switchUserPanel = createBothVertAndHorizPanelWithFLowLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton switchUserButton = switchUserButton("Switch User", e -> gamePanel.changeGameState(StateDefinitions.INITIAL_STATE));
        switchUserPanel.add(switchUserButton);
        return switchUserPanel;
    }

    private JPanel middlePanel() {
        JPanel logoPanel = createSimpleVerticalPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        addLogo(logoPanel);
        return logoPanel;
    }

    private JPanel bottomPanel(GamePanel gamePanel) {
        JPanel buttonPanel = createSimpleVerticalPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(menuButton("RESUME", e -> gamePanel.loadGame()));
        buttonPanel.add(Box.createVerticalStrut(MENUBUTTON_VERTICAL_SPACING));
        buttonPanel.add(menuButton("START NEW GAME", e -> startNewGame(gamePanel)));
        buttonPanel.add(Box.createVerticalStrut(MENUBUTTON_VERTICAL_SPACING));
        buttonPanel.add(menuButton("TEST BUTTON", e -> System.out.println("Test Button Clicked")));
        buttonPanel.add(Box.createVerticalStrut(BOTTOM_PANEL_OFFSET));

        return buttonPanel;
    }

    // Helper methods
    private void startNewGame(GamePanel gamePanel) {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to start a new game? Your whole progress so far will be erased!",
                "Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (response == JOptionPane.YES_OPTION) {
            gamePanel.resetGame();
            gamePanel.saveGame();
            gamePanel.changeGameState(StateDefinitions.GAME);
        }
    }
}