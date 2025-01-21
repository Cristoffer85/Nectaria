package com.cristoffer85.Entities.Resources;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PlayerSprite {
    private BufferedImage spritesheet;
    private BufferedImage[][] sprites;

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

    public void render(Graphics g, int x, int y, int size, int direction, int frame, boolean isMoving) {
        if (isMoving) {
            g.drawImage(getSprite(direction, frame), x, y, size, size, null);
        } else {
            g.drawImage(getSprite(direction, 0), x, y, size, size, null); // Use the first sprite of the direction when not moving
        }
    }
}