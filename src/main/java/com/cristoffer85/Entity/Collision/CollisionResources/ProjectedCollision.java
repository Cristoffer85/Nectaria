package com.cristoffer85.Entity.Collision.CollisionResources;

import com.cristoffer85.Entity.Player.Player;

import java.awt.*;

public abstract class ProjectedCollision {
    protected final Player player;

    public ProjectedCollision(Player player) {
        this.player = player;
    }

    /* Abstract collision check class == that holds the groundspecific logic for projected collision against rectangular obstacles and boundary. 
    If this not implemented == the collision will be very goofy/player will (sometimes) stop ca 1 pixel away from the obstacle sometimes. 
    Dont know why exactly but this class fixed that.

    This is an abstract class so the rectangle and boundary collision subclasses can use it.
    */

    public Rectangle calculateProjectedPosition(int projectedPosition, boolean isHorizontal) {
        int collisionBoxSize = player.getCollisionBoxSize();
        int collisionBoxOffsetX = player.getCollisionBoxOffsetX();
        int collisionBoxOffsetY = player.getCollisionBoxOffsetY();

        return isHorizontal
            ? new Rectangle(projectedPosition + collisionBoxOffsetX, player.getY() + collisionBoxOffsetY, collisionBoxSize, collisionBoxSize)
            : new Rectangle(player.getX() + collisionBoxOffsetX, projectedPosition + collisionBoxOffsetY, collisionBoxSize, collisionBoxSize);
    }
}