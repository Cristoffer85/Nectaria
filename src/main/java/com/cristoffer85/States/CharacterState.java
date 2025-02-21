package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StateDesign;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class CharacterState extends StateDesign {
    private final GameState gameState;

    public CharacterState(GamePanel gamePanel, GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Render the GameState as the background
        gameState.PaintGameState(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the color to semi-transparent black
        g2d.setColor(new Color(0, 0, 0, 128));
        // Draw a rounded rectangle with semi-transparent black background
        g2d.fillRoundRect(50, 50, 400, 450, 20, 20);

        // Set the color to white
        g2d.setColor(Color.WHITE);
        // Draw the border of the rounded rectangle
        g2d.setStroke(new java.awt.BasicStroke(5));
        g2d.drawRoundRect(50, 50, 400, 450, 20, 20);

        // Set the font and draw text inside the rectangle
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Character Stats", 75, 90);
    }
}