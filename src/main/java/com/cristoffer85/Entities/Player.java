package com.cristoffer85.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private int x, y;
    private final int size;
    private final int moveSpeed;

    public void move(int deltaX, int deltaY, Rectangle boundary, Rectangle obstacle) {
        // Handle horizontal movement - 1st: Checks horizontal boundary, 2nd: Checks horizontal collision, allows it to move vertically
        if (deltaX != 0) {
            int newX = x + deltaX;
            // Check horizontal boundary
            if (newX >= 0 && newX <= boundary.width - size) {
                Rectangle newRectX = new Rectangle(newX, y, size, size);
                // Check horizontal collision
                if (!newRectX.intersects(obstacle)) {
                    x = newX;
                }
            }
        }

        // Handle vertical movement - 1st: Checks vertical boundary, 2nd: Checks vertical collision, allows it to move horizontally
        if (deltaY != 0) {
            int newY = y + deltaY;
            // Check vertical boundary
            if (newY >= 0 && newY <= boundary.height - size) {
                Rectangle newRectY = new Rectangle(x, newY, size, size);
                // Check vertical collision
                if (!newRectY.intersects(obstacle)) {
                    y = newY;
                }
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);
    }
}