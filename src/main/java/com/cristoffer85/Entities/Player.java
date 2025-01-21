package com.cristoffer85.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.cristoffer85.Main.KeyHandler;
import com.cristoffer85.Entities.Resources.PlayerSprite;

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

    private int currentFrame = 0;
    private int frameDelay = 5; // Adjust as needed for animation speed
    private int frameCounter = 0;
    private boolean isMoving = false; // Flag to indicate whether the player is moving
    private int lastDirection = 0; // Track the last direction of movement

    public Player(int x, int y, int size, int moveSpeed, int velocityX, int velocityY) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.moveSpeed = moveSpeed;
        this.velocityX = velocityX;
        this.velocityY = velocityY;

        playerSprite = new PlayerSprite("/Playermove.jpg", 183, 275, 4, 4);
    }

    public void move(KeyHandler keyHandler, Rectangle boundary, List<Rectangle> obstacles, List<Line2D> diagonalObstacles) {
        handleHorizontalMovement(keyHandler);
        handleVerticalMovement(keyHandler);

        // Update the player's position and check for collisions with all obstacles
        x = processMovement(x, velocityX, boundary.width, obstacles, diagonalObstacles, true);
        y = processMovement(y, velocityY, boundary.height, obstacles, diagonalObstacles, false);

        // Update animation frame if moving
        if (isMoving) {
            frameCounter++;
            if (frameCounter >= frameDelay) {
                frameCounter = 0;
                currentFrame = (currentFrame + 1) % 4; // Assuming 4 frames per direction
            }
        } else {
            currentFrame = 0; // Reset to the first frame when not moving
        }
    }

    private void handleHorizontalMovement(KeyHandler keyHandler) {
        if (keyHandler.isKeyPressed("moveLeft")) {
            velocityX = Math.max(velocityX - acceleration, -moveSpeed); // Accelerate left
            isMoving = true;
            lastDirection = 2; // Left
        } else if (keyHandler.isKeyPressed("moveRight")) {
            velocityX = Math.min(velocityX + acceleration, moveSpeed);  // Accelerate right
            isMoving = true;
            lastDirection = 3; // Right
        } else {
            velocityX = applyDeceleration(velocityX);                   // Apply deceleration when no key is pressed
            isMoving = false;
        }
    }

    private void handleVerticalMovement(KeyHandler keyHandler) {
        if (keyHandler.isKeyPressed("moveUp")) {
            velocityY = Math.max(velocityY - acceleration, -moveSpeed); // Accelerate up
            isMoving = true;
            lastDirection = 1; // Up
        } else if (keyHandler.isKeyPressed("moveDown")) {
            velocityY = Math.min(velocityY + acceleration, moveSpeed);  // Accelerate down
            isMoving = true;
            lastDirection = 0; // Down
        } else {
            velocityY = applyDeceleration(velocityY);                   // Apply deceleration when no key is pressed
            isMoving = false;
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

    private int processMovement(int currentPosition, int velocity, int boundaryLimit, List<Rectangle> obstacles, List<Line2D> diagonalObstacles, boolean isHorizontal) {
        int projectedPosition = currentPosition + velocity;             // Calculate the projected position based on velocity
    
        if (projectedPosition < 0) {                                    // Check if the projected position exceeds the boundary
            return 0;                                                   // Snap to the start of the boundary if beyond limit
        }
        if (projectedPosition > boundaryLimit - size) {
            return boundaryLimit - size;                                // Snap to the end of the boundary if beyond limit
        }
    
        Rectangle projectedRect = isHorizontal                          // Create a rectangle representing the player's projected position for collision detection
            ? new Rectangle(projectedPosition, y, size, size)           // Horizontal movement
            : new Rectangle(x, projectedPosition, size, size);          // Vertical movement
    
        // ###### COLLISION HORIZONTAL/VERTICAL OBSTACLES ######
        for (Rectangle obstacle : obstacles) {
            if (projectedRect.intersects(obstacle)) {                   // Check for collision with each obstacle
                return isHorizontal                                     // Snap the position to the obstacle boundaries
                    ? (velocity > 0 ? obstacle.x - size : obstacle.x + obstacle.width)      // Snap to left or right of obstacle
                    : (velocity > 0 ? obstacle.y - size : obstacle.y + obstacle.height);    // Snap to top or bottom of obstacle
            }
        }
    
        // ###### COLLISION DIAGONAL OBSTACLES ######
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
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);

        // Determine the direction of movement
        int direction = lastDirection; // Default to last direction
        if (velocityY < 0) {
            direction = 1; // Up
        } else if (velocityY > 0) {
            direction = 0; // Down
        } else if (velocityX < 0) {
            direction = 2; // Left
        } else if (velocityX > 0) {
            direction = 3; // Right
        }

        // Render the appropriate sprite
        playerSprite.render(g, x, y, size, direction, currentFrame, isMoving);
    }
}