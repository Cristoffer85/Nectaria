package com.cristoffer85.Entity.Collision;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

import com.cristoffer85.Entity.Collision.CollisionResources.BoundaryCollision;
import com.cristoffer85.Entity.Collision.CollisionResources.DiagonalObstacleCollision;
import com.cristoffer85.Entity.Collision.CollisionResources.StraightObstacleCollision;
import com.cristoffer85.Entity.Collision.CollisionResources.TileCollision;
import com.cristoffer85.Entity.Collision.CollisionResources.ObjectCollision;
import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.Objects.GameObjects;

public class CollisionChecker {
    private final BoundaryCollision boundaryCollision;
    private final StraightObstacleCollision straightObstacleCollision;
    private final DiagonalObstacleCollision diagonalObstacleCollision;
    private final TileCollision tileCollision;
    private final ObjectCollision objectCollision;

    public CollisionChecker(Player player) {
        this.boundaryCollision = new BoundaryCollision(player);
        this.straightObstacleCollision = new StraightObstacleCollision(player);
        this.diagonalObstacleCollision = new DiagonalObstacleCollision(player);
        this.tileCollision = new TileCollision(player);
        this.objectCollision = new ObjectCollision(player);
    }

    public int playerCollision(int currentPosition, int velocity, int boundaryLimit, List<Rectangle> straightObstacles, List<Line2D> diagonalObstacles, List<GameObjects> gameObjects, boolean isHorizontal) {
        int projectedPosition = currentPosition + velocity;
        Rectangle projectedRect = boundaryCollision.calculateProjectedPosition(projectedPosition, isHorizontal);

        int boundaryCollisionResult = boundaryCollision.checkBoundaryCollision(projectedPosition, boundaryLimit, isHorizontal);
        if (boundaryCollisionResult != Integer.MIN_VALUE) {
            return boundaryCollisionResult;
        }

        int straightCollisionResult = straightObstacleCollision.checkStraightObstacleCollision(projectedRect, straightObstacles, velocity, isHorizontal);
        if (straightCollisionResult != Integer.MIN_VALUE) {
            return straightCollisionResult;
        }

        int diagonalCollisionResult = diagonalObstacleCollision.checkDiagonalObstacleCollision(projectedRect, diagonalObstacles, velocity, isHorizontal);
        if (diagonalCollisionResult != Integer.MIN_VALUE) {
            return diagonalCollisionResult;
        }

        int tileCollisionResult = tileCollision.checkTileCollision(projectedRect, velocity, isHorizontal);
        if (tileCollisionResult != Integer.MIN_VALUE) {
            return tileCollisionResult;
        }

        int objectCollisionResult = objectCollision.checkObjectCollision(projectedRect, velocity, gameObjects, isHorizontal);
        if (objectCollisionResult != Integer.MIN_VALUE) {
            return objectCollisionResult;
        }

        return projectedPosition;
    }
}