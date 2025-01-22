package com.cristoffer85.Entity.EntityResources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerSprite {
    private BufferedImage spritesheet;
    private BufferedImage[][] sprites;
    private int currentFrame = 0;
    private int frameDelay = 5;                                                     // Adjust animation speed, lower = faster and reverse
    private int frameCounter = 0;

    public PlayerSprite(String spritePath, int spriteWidth, int spriteHeight, int rows, int cols) {
        try {
            spritesheet = ImageIO.read(getClass().getResource(spritePath));
            sprites = new BufferedImage[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    sprites[row][col] = spritesheet.getSubimage(col * spriteWidth, row * spriteHeight, spriteWidth, spriteHeight);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(int direction, int frame) {
        return sprites[direction][frame];
    }

    public void updateFrame(boolean isMoving) {
        if (isMoving) {
            frameCounter++;
            if (frameCounter >= frameDelay) {
                frameCounter = 0;
                currentFrame = (currentFrame + 1) % 4;                              // Assuming: 4 frames per direction
            }
        } else {
            currentFrame = 0;                                                       // Reset to the first frame when not moving
        }
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public int determineDirection(int velocityX, int velocityY, int lastDirection) { // Determines direction for which sprite to render
        return (velocityY < 0) ? 1 :      // Up
               (velocityY > 0) ? 0 :      // Down
               (velocityX < 0) ? 2 :      // Left
               (velocityX > 0) ? 3 :      // Right
               lastDirection;
    }

    public void render(Graphics g, int x, int y, int size, int direction, int currentFrame, boolean isMoving) {
        updateFrame(isMoving);
        g.drawImage(getSprite(direction, currentFrame), x, y, size, size, null);
    }
}