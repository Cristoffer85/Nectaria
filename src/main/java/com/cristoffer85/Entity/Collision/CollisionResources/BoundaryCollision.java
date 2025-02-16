package com.cristoffer85.Entity.Collision.CollisionResources;

import com.cristoffer85.Entity.Player.Player;

public class BoundaryCollision extends ProjectedCollision {

    public BoundaryCollision(Player player) {
        super(player);
    }

    public int checkBoundaryCollision(int projectedPosition, int boundaryLimit, boolean isHorizontal) {
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
}