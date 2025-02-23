package com.cristoffer85.States;

import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.States.StatesResources.StateDesign;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class CharacterState extends StateDesign {
    private final GameState gameState;
    private final Player player;
    private int selectedSlotX = 0;
    private int selectedSlotY = 0;

    public CharacterState(GamePanel gamePanel, GameState gameState, Player player) {
        this.gameState = gameState;
        this.player = player;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render GameState as background
        gameState.PaintGameState(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the two CHARACTER_STATE boxes
        drawCharacterStatsBox(g2d);
        drawInventoryBox(g2d);
    }

    private void drawCharacterStatsBox(Graphics2D g2d) {
        int windowWidth = getWidth();
        int windowHeight = getHeight();

        int boxWidth = windowWidth / 4;
        int boxHeight = (int) (windowHeight / 1.5);

        // Set the position of the box to the top left corner
        int boxX = 20; // Fixed x-coordinate
        int boxY = 40; // Fixed y-coordinate

        // Set the color to semi-transparent black
        g2d.setColor(new Color(0, 0, 0, 128));
        // Draw a rounded rectangle with semi-transparent black background
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        // Set the color to white
        g2d.setColor(Color.WHITE);
        // Draw the border of the rounded rectangle
        g2d.setStroke(new java.awt.BasicStroke(5));
        g2d.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        // Calculate the font size and vertical spacing based on the window size
        int fontSize = Math.min(windowWidth, windowHeight) / 40;
        int verticalSpacing = fontSize + 10;

        // Set the font and draw text inside the rectangle
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
        g2d.drawString("Character Stats", boxX + 25, boxY + verticalSpacing);

        // Add an extra vertical spacing for the gap
        int extraSpacing = verticalSpacing - 15;

        // Set the font to plain and draw player stats
        g2d.setFont(new Font("Arial", Font.PLAIN, fontSize));
        g2d.drawString("Health: " + player.getHealth(), boxX + 25, boxY + verticalSpacing * 2 + extraSpacing);
        g2d.drawString("Strength: " + player.getStrength(), boxX + 25, boxY + verticalSpacing * 3 + extraSpacing);
        g2d.drawString("Dexterity: " + player.getDexterity(), boxX + 25, boxY + verticalSpacing * 4 + extraSpacing);
    }

    private void drawInventoryBox(Graphics2D g2d) {
        int windowWidth = getWidth();
        int windowHeight = getHeight();

        // Calculate the dimensions of the box based on the window size
        int boxWidth = windowWidth / 4;
        int boxHeight = (int) (windowHeight / 1.5);

        // Set the position of the box to the top right corner
        int boxX = windowWidth - boxWidth - 20; // Fixed x-coordinate
        int boxY = 40; // Fixed y-coordinate

        // Set the color to semi-transparent black
        g2d.setColor(new Color(0, 0, 0, 128));
        // Draw a rounded rectangle with semi-transparent black background
        g2d.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        // Set the color to white
        g2d.setColor(Color.WHITE);
        // Draw the border of the rounded rectangle
        g2d.setStroke(new java.awt.BasicStroke(5));
        g2d.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        // Calculate the font size and vertical spacing based on the window size
        int fontSize = Math.min(windowWidth, windowHeight) / 40;
        int verticalSpacing = fontSize + 10;

        // Set the font and draw text inside the rectangle
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
        g2d.drawString("Inventory", boxX + 25, boxY + verticalSpacing);

        // Draw the inventory slots
        int slotSize = boxWidth / 5;
        int slotPadding = 10;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                int slotX = boxX + 25 + (slotSize + slotPadding) * j;
                int slotY = boxY + verticalSpacing * 2 + (slotSize + slotPadding) * i;
                g2d.drawRect(slotX, slotY, slotSize, slotSize);

                // Highlight the selected slot
                if (i == selectedSlotY && j == selectedSlotX) {
                    g2d.setColor(Color.YELLOW);
                    g2d.drawRect(slotX - 2, slotY - 2, slotSize + 4, slotSize + 4);
                    g2d.setColor(Color.WHITE);
                }
            }
        }
    }

    public void moveSelection(int dx, int dy) {
        selectedSlotX = Math.max(0, Math.min(3, selectedSlotX + dx));
        selectedSlotY = Math.max(0, Math.min(5, selectedSlotY + dy));
        repaint();
    }
}