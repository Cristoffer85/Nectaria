package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StateDesign;

import java.awt.Color;
import java.awt.Graphics;

public class CharacterState extends StateDesign {
    private final GameState gameState;

    public CharacterState(GamePanel gamePanel, GameState gameState) {
        this.gameState = gameState;
        // Initialize the character state UI components here
        // For example, you can add buttons, labels, etc.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Render the GameState as the background
        gameState.PaintGameState(g);
        // Set the color to white
        g.setColor(Color.WHITE);
        // Draw a 100x100px white box at position (50, 50)
        g.fillRect(50, 50, 400, 400);
    }
}