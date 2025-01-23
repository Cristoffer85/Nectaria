package com.cristoffer85.Entity.EntityResources;

import com.cristoffer85.Entity.Player;
import com.cristoffer85.Main.KeyHandler;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

public class PlayerMovement {
    private final Player player;
    private final int acceleration;
    private final int deceleration;

    public PlayerMovement(Player player, int acceleration, int deceleration) {
        this.player = player;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
    }

    public void move(KeyHandler keyHandler, Rectangle boundary, List<Rectangle> straightObstacles, List<Line2D> diagonalObstacles) {
        handleHorizontalMovement(keyHandler);
        handleVerticalMovement(keyHandler);

        // Update player's position and check for collisions with all obstacles
        player.setX(processMovement(player.getX(), player.getVelocityX(), boundary.width, straightObstacles, diagonalObstacles, true));
        player.setY(processMovement(player.getY(), player.getVelocityY(), boundary.height, straightObstacles, diagonalObstacles, false));

        // Set isMoving flag based on both horizontal and vertical velocities
        player.setMoving(player.getVelocityX() != 0 || player.getVelocityY() != 0);
    }

    private void handleHorizontalMovement(KeyHandler keyHandler) {
        if (keyHandler.isKeyPressed("moveLeft")) {
            player.setVelocityX(Math.max(player.getVelocityX() - acceleration, -player.getMoveSpeed())); // Accelerate left
            player.setLastDirection(1); // Left
        } else if (keyHandler.isKeyPressed("moveRight")) {
            player.setVelocityX(Math.min(player.getVelocityX() + acceleration, player.getMoveSpeed()));  // Accelerate right
            player.setLastDirection(3); // Right
        } else {
            player.setVelocityX(applyDeceleration(player.getVelocityX()));                   // Apply deceleration when no key is pressed
        }
    }

    private void handleVerticalMovement(KeyHandler keyHandler) {
        if (keyHandler.isKeyPressed("moveUp")) {
            player.setVelocityY(Math.max(player.getVelocityY() - acceleration, -player.getMoveSpeed())); // Accelerate up
            player.setLastDirection(0); // Up
        } else if (keyHandler.isKeyPressed("moveDown")) {
            player.setVelocityY(Math.min(player.getVelocityY() + acceleration, player.getMoveSpeed()));  // Accelerate down
            player.setLastDirection(2); // Down
        } else {
            player.setVelocityY(applyDeceleration(player.getVelocityY()));                   // Apply deceleration when no key is pressed
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
        int scaledSize = player.getSize() * player.getScale();          // Use scaled size for collision detection
        
        if (projectedPosition < 0) {                                    // Check if the projected position exceeds the boundary
            return 0;                                                   // Snap to the start of the boundary if beyond limit
        }
        if (projectedPosition > boundaryLimit - scaledSize) {
            return boundaryLimit - scaledSize;                          // Snap to the end of the boundary if beyond limit
        }
        
        Rectangle projectedRect = isHorizontal                          // Create a rectangle representing the player's projected position for collision detection
            ? new Rectangle(projectedPosition, player.getY(), scaledSize, scaledSize)   // Horizontal movement
            : new Rectangle(player.getX(), projectedPosition, scaledSize, scaledSize);  // Vertical movement
    
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
                    player.setVelocityY((int) (velocity * sin));
                    return currentPosition; // Stop horizontal movement
                } else {
                    player.setVelocityX((int) (velocity * cos));
                    return currentPosition; // Stop vertical movement
                }
            }
        }
    
        return projectedPosition;                                       // If no collision, return the projected position
    }
}