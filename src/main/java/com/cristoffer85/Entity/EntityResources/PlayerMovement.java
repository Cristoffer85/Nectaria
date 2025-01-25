package com.cristoffer85.Entity.EntityResources;

import com.cristoffer85.Entity.Player;
import com.cristoffer85.Main.KeyHandler;
import com.cristoffer85.Tile.Tile;

import java.awt.*;
import java.awt.geom.Line2D;
import com.cristoffer85.Entity.CollisionChecker;
import java.util.List;

public class PlayerMovement {
    private final Player player;
    private final int acceleration;
    private final int deceleration;
    private final CollisionChecker collisionChecker;

    public PlayerMovement(Player player, int acceleration, int deceleration) {
        this.player = player;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.collisionChecker = new CollisionChecker(player);
    }

    public void move(KeyHandler keyHandler, List<Rectangle> straightObstacles, List<Line2D> diagonalObstacles) {
        handleHorizontalMovement(keyHandler);
        handleVerticalMovement(keyHandler);

        // Get map dimensions automatically, based on tile size and map size (look in Tile.java, for reference)
        int mapWidth = Tile.getMapWidth() * Tile.getTileWidth();
        int mapHeight = Tile.getMapHeight() * Tile.getTileHeight();

        // Update player's position and check for collisions with all obstacles
        player.setX(collisionChecker.playerCollision(player.getX(), player.getVelocityX(), mapWidth, straightObstacles, diagonalObstacles, true));
        player.setY(collisionChecker.playerCollision(player.getY(), player.getVelocityY(), mapHeight, straightObstacles, diagonalObstacles, false));

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
            player.setVelocityX(applyDeceleration(player.getVelocityX()));                               // Apply deceleration when no key is pressed
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
            player.setVelocityY(applyDeceleration(player.getVelocityY()));                               // Apply deceleration when no key is pressed
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
}