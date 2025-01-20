package com.cristoffer85.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.cristoffer85.Main.KeyHandler;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private int x, y;
    private final int size;
    private final int moveSpeed;

    public void move(KeyHandler keyHandler, Rectangle boundary, Rectangle obstacle) {
        int moveX = 0;
        int moveY = 0;

        // ############### HORIZONTAL MOVEMENT ###############
        if (keyHandler.isKeyPressed("moveLeft")) moveX -= moveSpeed;
        if (keyHandler.isKeyPressed("moveRight")) moveX += moveSpeed;

        if (moveX != 0) {
            int newX = x + moveX;
            if (newX >= 0 && newX <= boundary.width - size) {
                Rectangle newRectX = new Rectangle(newX, y, size, size);
                // Collision detection, intersect method
                if (!newRectX.intersects(obstacle)) {
                    x = newX;
                }
            }
        }

        // ############### VERTICAL MOVEMENT ###############
        if (keyHandler.isKeyPressed("moveUp")) moveY -= moveSpeed;
        if (keyHandler.isKeyPressed("moveDown")) moveY += moveSpeed;

        if (moveY != 0) {
            int newY = y + moveY;
            if (newY >= 0 && newY <= boundary.height - size) {
                Rectangle newRectY = new Rectangle(x, newY, size, size);
                // Collision detection, intersect method
                if (!newRectY.intersects(obstacle)) {
                    y = newY;
                }
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);
    }
}