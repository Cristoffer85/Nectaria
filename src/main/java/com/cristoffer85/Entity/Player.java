package com.cristoffer85.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.cristoffer85.Main.KeyHandler;
import com.cristoffer85.Entity.EntityResources.PlayerSprite;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private int x, y;
    private final int size;
    private final int moveSpeed;
    private final int acceleration = 1;
    private final int deceleration = 1;

    private int velocityX = 0;
    private int velocityY = 0;

    private PlayerSprite playerSprite;

    private boolean isMoving = false;
    private int lastDirection = 0;

    private final int scale = 1; // Scale factor for rendering (note: if changing, adjust render method (offset) accordingly)

    public Player(int x, int y, int size, int moveSpeed, int velocityX, int velocityY) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.moveSpeed = moveSpeed;
        this.velocityX = velocityX;
        this.velocityY = velocityY;

        initializePlayerSprite();
    }

    private void initializePlayerSprite() {
        playerSprite = new PlayerSprite("/TestCharx9.png", 64, 64, 4, 9);
    }

    public void move(KeyHandler keyHandler, Rectangle boundary, List<Rectangle> straightObstacles, List<Line2D> diagonalObstacles) {
        handleHorizontalMovement(keyHandler);
        handleVerticalMovement(keyHandler);

        // Update player's position and check for collisions with all obstacles
        x = processMovement(x, velocityX, boundary.width, straightObstacles, diagonalObstacles, true);
        y = processMovement(y, velocityY, boundary.height, straightObstacles, diagonalObstacles, false);

        // Set isMoving flag based on both horizontal and vertical velocities
        isMoving = (velocityX != 0 || velocityY != 0);
    }

    private void handleHorizontalMovement(KeyHandler keyHandler) {
        if (keyHandler.isKeyPressed("moveLeft")) {
            velocityX = Math.max(velocityX - acceleration, -moveSpeed); // Accelerate left
            lastDirection = 2; // Left
        } else if (keyHandler.isKeyPressed("moveRight")) {
            velocityX = Math.min(velocityX + acceleration, moveSpeed);  // Accelerate right
            lastDirection = 3; // Right
        } else {
            velocityX = applyDeceleration(velocityX);                   // Apply deceleration when no key is pressed
        }
    }

    private void handleVerticalMovement(KeyHandler keyHandler) {
        if (keyHandler.isKeyPressed("moveUp")) {
            velocityY = Math.max(velocityY - acceleration, -moveSpeed); // Accelerate up
            lastDirection = 1; // Up
        } else if (keyHandler.isKeyPressed("moveDown")) {
            velocityY = Math.min(velocityY + acceleration, moveSpeed);  // Accelerate down
            lastDirection = 0; // Down
        } else {
            velocityY = applyDeceleration(velocityY);                   // Apply deceleration when no key is pressed
        }
    }

    private int applyDeceleration(int velocity) {
        if (velocity > 0) {                                             // If moving in positive direction, gradually slow down to zero
            return Math.max(velocity - deceleration, 0);
        }
        if (velocity < 0) {                                             // If moving in negative direction, gradually slow down to zero
            return Math.min(velocity + deceleration, 0);
        }
        return 0;                                                       // No movement, return zero
    }

    private int processMovement(int currentPosition, int velocity, int boundaryLimit, List<Rectangle> straightObstacles, List<Line2D> diagonalObstacles, boolean isHorizontal) {
        int projectedPosition = currentPosition + velocity;             // Calculate the projected position based on velocity
        int scaledSize = size * scale;                                  // Use scaled size for collision detection
        
        if (projectedPosition < 0) {                                    // Check if the projected position exceeds the boundary
            return 0;                                                   // Snap to the start of the boundary if beyond limit
        }
        if (projectedPosition > boundaryLimit - scaledSize) {
            return boundaryLimit - scaledSize;                          // Snap to the end of the boundary if beyond limit
        }
        
        Rectangle projectedRect = isHorizontal                          // Create a rectangle representing the player's projected position for collision detection
            ? new Rectangle(projectedPosition, y, scaledSize, scaledSize) // Horizontal movement
            : new Rectangle(x, projectedPosition, scaledSize, scaledSize); // Vertical movement
    
        // ## Collision straight obstacles ##
        for (Rectangle straightObstacle : straightObstacles) {
            if (projectedRect.intersects(straightObstacle)) {           // Check for collision with each obstacle
                return isHorizontal                                     // Snap the position to the obstacle boundaries
                    ? (velocity > 0 ? straightObstacle.x - scaledSize : straightObstacle.x + straightObstacle.width) // Snap to left or right of obstacle
                    : (velocity > 0 ? straightObstacle.y - scaledSize : straightObstacle.y + straightObstacle.height); // Snap to top or bottom of obstacle
            }
        }
    
        // ## Collision diagonal obstacles ##
        for (Line2D diagonalObstacle : diagonalObstacles) {
            if (projectedRect.intersectsLine(diagonalObstacle)) {       // Check for collision with each diagonal obstacle
                // Calculate the angle of the diagonal line
                double angle = Math.atan2(diagonalObstacle.getY2() - diagonalObstacle.getY1(), diagonalObstacle.getX2() - diagonalObstacle.getX1());
                double sin = Math.sin(angle);
                double cos = Math.cos(angle);

                // Adjust the player's velocity based on the angle of the line
                if (isHorizontal) {
                    velocityY = (int) (velocity * sin);
                    return currentPosition; // Stop horizontal movement
                } else {
                    velocityX = (int) (velocity * cos);
                    return currentPosition; // Stop vertical movement
                }
            }
        }
    
        return projectedPosition;                                       // If no collision, return the projected position
    }

    public void render(Graphics g) {
        // Determine the scaled size of the sprite
        int scaledSize = size * scale;
    
        // Draw out collision box/Debug
        g.setColor(Color.RED);
        g.drawRect(x - (scaledSize - size) / 2, y - (scaledSize - size) / 2, size, size);   
    
        // OFFSET: Adjust for scaling to center the sprite (num value is the offset)
        int renderX = x - scaledSize - 1;
        int renderY = y - scaledSize - 16;
    
        // Determine direction of movement and render the sprite
        int direction = playerSprite.determineDirection(velocityX, velocityY, lastDirection);
        playerSprite.render(g, renderX, renderY, scaledSize, direction, isMoving, scale);
    }
}