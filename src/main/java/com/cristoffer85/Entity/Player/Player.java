package com.cristoffer85.Entity.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.cristoffer85.Entity.Player.PlayerResources.PlayerMovement;
import com.cristoffer85.Entity.Player.PlayerResources.PlayerSprite;
import com.cristoffer85.Main.KeyHandler;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

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

    // ## Player's stats ##
    private int health;
    private int strength;
    private int dexterity;

    // ## Heart sprites ##
    private BufferedImage heartSheet;
    private BufferedImage fullHeart;
    private BufferedImage halfHeart;
    private BufferedImage emptyHeart;

    public Player(int x, int y, int size, int moveSpeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.moveSpeed = moveSpeed;

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

        // Initialize player stats
        this.health = 5;
        this.strength = 10;
        this.dexterity = 10;

        // Load heart sprites
        try {
            heartSheet = ImageIO.read(getClass().getResourceAsStream("/objects/PlayerHearts.png"));
            fullHeart = heartSheet.getSubimage(128, 0, 64, 64);
            halfHeart = heartSheet.getSubimage(64, 0, 64, 64);
            emptyHeart = heartSheet.getSubimage(0, 0, 64, 64);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public void paintHearts(Graphics g) {
        int maxHealth = 6;
        int heartsToDraw = maxHealth / 2;
        int currentHealth = health;

        for (int i = 0; i < heartsToDraw; i++) {
            if (currentHealth >= 2) {

                // First Integer value is X position on screen, second is Spacing between hearts, Third is Y position on screen
                g.drawImage(fullHeart, -10 + (i * 34), -10, null);
                currentHealth -= 2;
            } else if (currentHealth == 1) {
                g.drawImage(halfHeart, -10 + (i * 34), -10, null);
                currentHealth -= 1;
            } else {
                g.drawImage(emptyHeart, -10 + (i * 34), -10, null);
            }
        }
    }
}