package com.cristoffer85.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.cristoffer85.Main.KeyHandler;
import com.cristoffer85.Entity.EntityResources.PlayerSprite;
import com.cristoffer85.Entity.EntityResources.PlayerMovement;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    // ## Player's position and movement properties ##
    private int x, y;
    private final int size;
    private final int moveSpeed;
    private int acceleration;
    private int deceleration;
    // Values needed for acceleration and deceleration calculations
    private int velocityX = 0;
    private int velocityY = 0;
    
    private boolean isMoving = false;
    private int lastDirection = 0;
    private PlayerMovement playerMovement;

    // ## Player's sprite and collision properties ##
    private PlayerSprite playerSprite;
    private int collisionBoxSize;
    private int collisionBoxOffsetX;
    private int collisionBoxOffsetY;

    public Player(int x, int y, int size, int moveSpeed, int velocityX, int velocityY) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.moveSpeed = moveSpeed;
        this.velocityX = velocityX;
        this.velocityY = velocityY;

        initializePlayer();
        playerMovement = new PlayerMovement(this, acceleration, deceleration);
    }

    private void initializePlayer() {
        // Set Collision box size and offsets
        this.collisionBoxSize = size / 3;
        this.collisionBoxOffsetX = 21;
        this.collisionBoxOffsetY = 40;

        // Set acceleration and deceleration values
        this.acceleration = 1;
        this.deceleration = 1;

        // Initialize player sprite: /Path - Width - Height - Rows - Columns - Frames per Direction update - Frame Delay
        playerSprite = new PlayerSprite("/TestCharx9.png", 64, 64, 4, 9, 9, 1);
    }

    public void move(KeyHandler keyHandler, List<Rectangle> straightObstacles, List<Line2D> diagonalObstacles) {
        playerMovement.move(keyHandler, straightObstacles, diagonalObstacles);
    }

    public void paintPlayer(Graphics g, int cameraX, int cameraY) {
        // Calculate the render position based on the camera offset
        int renderX = x - cameraX;
        int renderY = y - cameraY;
    
        // Draw out collision box/Debug
        g.setColor(Color.RED);
        g.drawRect(renderX + collisionBoxOffsetX, renderY + collisionBoxOffsetY, collisionBoxSize, collisionBoxSize);   
    
        // Determine direction of movement and render the sprite
        int direction = playerSprite.determineDirection(velocityX, velocityY, lastDirection);
        playerSprite.render(g, renderX, renderY, size, direction, isMoving);
    }
}