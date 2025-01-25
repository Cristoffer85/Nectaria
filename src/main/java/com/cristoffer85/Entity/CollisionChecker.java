package com.cristoffer85.Entity;

import com.cristoffer85.Tile.Tile;
import com.cristoffer85.Tile.TileManager;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Map;

public class CollisionChecker {
    private final Player player;

    public CollisionChecker(Player player) {
        this.player = player;
    }

    public int playerCollision(int currentPosition, int velocity, int boundaryLimit, List<Rectangle> straightObstacles, List<Line2D> diagonalObstacles, boolean isHorizontal) {
        int projectedPosition = currentPosition + velocity;             // Calculate the projected position based on velocity
        int collisionBoxSize = player.getCollisionBoxSize();            // Use player's collision box size for collision detection
        int collisionBoxOffsetX = player.getCollisionBoxOffsetX();      // Get the collision box offset X
        int collisionBoxOffsetY = player.getCollisionBoxOffsetY();      // Get the collision box offset Y
        
        // ## Boundary collision ##
        if (isHorizontal) {
            if (projectedPosition + collisionBoxOffsetX < 0) {          // Check if the projected position exceeds the boundary
                return -collisionBoxOffsetX;                            // Snap to the start of the boundary if beyond limit
            }
            if (projectedPosition + collisionBoxOffsetX > boundaryLimit - collisionBoxSize) {
                return boundaryLimit - collisionBoxSize - collisionBoxOffsetX; // Snap to the end of the boundary if beyond limit
            }
        } else {
            if (projectedPosition + collisionBoxOffsetY < 0) {          // Check if the projected position exceeds the boundary
                return -collisionBoxOffsetY;                            // Snap to the start of the boundary if beyond limit
            }
            if (projectedPosition + collisionBoxOffsetY > boundaryLimit - collisionBoxSize) {
                return boundaryLimit - collisionBoxSize - collisionBoxOffsetY; // Snap to the end of the boundary if beyond limit
            }
        }
        
        // ## Extra 'Snap'-collision method created to calculate the players projected position, and then "snap" player against obstacles, removing the "fault"-parameter of not having a full on intersect with obstacle/no extra gap. ##
        Rectangle projectedRect = isHorizontal
            ? new Rectangle(projectedPosition + collisionBoxOffsetX, player.getY() + collisionBoxOffsetY, collisionBoxSize, collisionBoxSize)   // Horizontal movement
            : new Rectangle(player.getX() + collisionBoxOffsetX, projectedPosition + collisionBoxOffsetY, collisionBoxSize, collisionBoxSize);  // Vertical movement
        //

        // ## Collision straight obstacles ##
        for (Rectangle straightObstacle : straightObstacles) {
            if (projectedRect.intersects(straightObstacle)) {           // Check for collision with each obstacle
                return isHorizontal                                     // Snap the position to the obstacle boundaries
                    ? (velocity > 0 ? straightObstacle.x - collisionBoxSize - collisionBoxOffsetX : straightObstacle.x + straightObstacle.width - collisionBoxOffsetX)   // Snap to left or right of obstacle
                    : (velocity > 0 ? straightObstacle.y - collisionBoxSize - collisionBoxOffsetY : straightObstacle.y + straightObstacle.height - collisionBoxOffsetY); // Snap to top or bottom of obstacle
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

        // ## Collision collidable tiles ##
        int tileWidth = TileManager.getTileWidth();
        int tileHeight = TileManager.getTileHeight();
        int mapWidth = TileManager.getMapWidth();
        int mapHeight = TileManager.getMapHeight();
        Map<Point, Integer> tilePositions = TileManager.getTilePositions();

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                Tile tile = TileManager.getTile(tilePositions.get(new Point(x, y)));
                if (tile != null && tile.isCollidable()) {
                    Rectangle tileRect = new Rectangle(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                    if (projectedRect.intersects(tileRect)) {
                        return isHorizontal
                            ? (velocity > 0 ? tileRect.x - collisionBoxSize - collisionBoxOffsetX : tileRect.x + tileRect.width - collisionBoxOffsetX)   // Snap to left or right of tile
                            : (velocity > 0 ? tileRect.y - collisionBoxSize - collisionBoxOffsetY : tileRect.y + tileRect.height - collisionBoxOffsetY); // Snap to top or bottom of tile
                    }
                }
            }
        }
    
        return projectedPosition;                                       // If no collision, return the projected position
    }
}