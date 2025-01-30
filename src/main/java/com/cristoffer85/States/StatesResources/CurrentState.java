package com.cristoffer85.States.StatesResources;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.GameState;
import com.cristoffer85.States.PauseState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CurrentState {
    private StatesDefinitions statesDefinitions;

    public StatesDefinitions getCurrentState() {
        return statesDefinitions;
    }

    public void setGameState(GamePanel gamePanel, StatesDefinitions newState, GameState gameState, PauseState pauseState) {
        // Pause_State
        if (newState == StatesDefinitions.PAUSE_MENU) {
            // Capture the current GameState image, in order to display it in the PauseState behind menu.
            BufferedImage gameStateImage = new BufferedImage(gamePanel.getWidth(), gamePanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = gameStateImage.createGraphics();
            gameState.paint(g2d);
            g2d.dispose();
            pauseState.setGameStateImage(gameStateImage);
        }
        // Game_State
        if (newState == StatesDefinitions.GAME) {
            // Reset the game state, in order to start a new game.
        }






        
        // Creates the different states as cards, and can later switch between them.
        this.statesDefinitions = newState;
        CardLayout cl = (CardLayout) gamePanel.getLayout();
        cl.show(gamePanel, newState.name());
    }
}