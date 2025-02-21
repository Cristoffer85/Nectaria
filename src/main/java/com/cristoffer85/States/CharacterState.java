package com.cristoffer85.States;

import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StateDesign;

import java.awt.Color;
import java.awt.Graphics;

public class CharacterState extends StateDesign {

    public CharacterState(GamePanel gamePanel) {
        // Initialize the character state UI components here
        // For example, you can add buttons, labels, etc.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Set the color to white
        g.setColor(Color.WHITE);
        // Draw a 100x100px white box at position (50, 50)
        g.fillRect(50, 50, 100, 100);
    }
}