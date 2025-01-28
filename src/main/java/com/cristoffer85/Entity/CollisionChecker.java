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
        int projectedPosition = currentPosition + velocity;
        Rectangle projectedRect = calculateProjectedPosition(projectedPosition, isHorizontal);

        int boundaryCollision = checkBoundaryCollision(projectedPosition, boundaryLimit, isHorizontal);
        if (boundaryCollision != Integer.MIN_VALUE) {
            return boundaryCollision;
        }

        int straightCollision = checkStraightObstacleCollision(projectedRect, straightObstacles, velocity, isHorizontal);
        if (straightCollision != Integer.MIN_VALUE) {
            return straightCollision;
        }

        int diagonalCollision = checkDiagonalObstacleCollision(projectedRect, diagonalObstacles, velocity, isHorizontal);
        if (diagonalCollision != Integer.MIN_VALUE) {
            return diagonalCollision;
        }

        int tileCollision = checkTileCollision(projectedRect, velocity, isHorizontal);
        if (tileCollision != Integer.MIN_VALUE) {
            return tileCollision;
        }

        return projectedPosition;
    }

    // Method where the boundaries are located in the map, and player is stopped from moving outside them
    private int checkBoundaryCollision(int projectedPosition, int boundaryLimit, boolean isHorizontal) {
        int collisionBoxSize = player.getCollisionBoxSize();
        int collisionBoxOffset = isHorizontal ? player.getCollisionBoxOffsetX() : player.getCollisionBoxOffsetY();

        if (projectedPosition + collisionBoxOffset < 0) {
            return -collisionBoxOffset;
        }

        if (projectedPosition + collisionBoxOffset > boundaryLimit - collisionBoxSize) {
            return boundaryLimit - collisionBoxSize - collisionBoxOffset;
        }

        return Integer.MIN_VALUE;
    }

    // Method to check straight obstacle collision
    private int checkStraightObstacleCollision(Rectangle projectedRect, List<Rectangle> straightObstacles, int velocity, boolean isHorizontal) {
        for (Rectangle straightObstacle : straightObstacles) {
            if (projectedRect.intersects(straightObstacle)) {
                int collisionBoxSize = player.getCollisionBoxSize();
                int collisionBoxOffsetX = player.getCollisionBoxOffsetX();
                int collisionBoxOffsetY = player.getCollisionBoxOffsetY();

                if (isHorizontal) {
                    return velocity > 0
                        ? straightObstacle.x - collisionBoxSize - collisionBoxOffsetX
                        : straightObstacle.x + straightObstacle.width - collisionBoxOffsetX;
                } else {
                    return velocity > 0
                        ? straightObstacle.y - collisionBoxSize - collisionBoxOffsetY
                        : straightObstacle.y + straightObstacle.height - collisionBoxOffsetY;
                }
            }
        }
        return Integer.MIN_VALUE;
    }

    // Method to check diagonal obstacle collision
    private int checkDiagonalObstacleCollision(Rectangle projectedRect, List<Line2D> diagonalObstacles, int velocity, boolean isHorizontal) {
        for (Line2D diagonalObstacle : diagonalObstacles) {
            if (projectedRect.intersectsLine(diagonalObstacle)) {
                double angle = Math.atan2(diagonalObstacle.getY2() - diagonalObstacle.getY1(), diagonalObstacle.getX2() - diagonalObstacle.getX1());
                double sin = Math.sin(angle);
                double cos = Math.cos(angle);

                if (isHorizontal) {
                    player.setVelocityY((int) (velocity * sin));
                } else {
                    player.setVelocityX((int) (velocity * cos));
                }

                return isHorizontal ? player.getX() : player.getY();
            }
        }
        return Integer.MIN_VALUE;
    }

    // Method to check tile collision, calculates the entire map and checks for collision with each tile
    private int checkTileCollision(Rectangle projectedRect, int velocity, boolean isHorizontal) {
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
                        int collisionBoxSize = player.getCollisionBoxSize();
                        int collisionBoxOffsetX = player.getCollisionBoxOffsetX();
                        int collisionBoxOffsetY = player.getCollisionBoxOffsetY();

                        if (isHorizontal) {
                            return velocity > 0
                                ? tileRect.x - collisionBoxSize - collisionBoxOffsetX
                                : tileRect.x + tileRect.width - collisionBoxOffsetX;
                        } else {
                            return velocity > 0
                                ? tileRect.y - collisionBoxSize - collisionBoxOffsetY
                                : tileRect.y + tileRect.height - collisionBoxOffsetY;
                        }
                    }
                }
            }
        }
        return Integer.MIN_VALUE;
    }

    // Method to calculate the projected position of the player (Snap (to grid)) to provide more accurate collision detection by the intersect method etc 
    private Rectangle calculateProjectedPosition(int projectedPosition, boolean isHorizontal) {
        int collisionBoxSize = player.getCollisionBoxSize();
        int collisionBoxOffsetX = player.getCollisionBoxOffsetX();
        int collisionBoxOffsetY = player.getCollisionBoxOffsetY();

        return isHorizontal
            ? new Rectangle(projectedPosition + collisionBoxOffsetX, player.getY() + collisionBoxOffsetY, collisionBoxSize, collisionBoxSize)
            : new Rectangle(player.getX() + collisionBoxOffsetX, projectedPosition + collisionBoxOffsetY, collisionBoxSize, collisionBoxSize);
    }
}
