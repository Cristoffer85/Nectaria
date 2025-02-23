package com.cristoffer85.Entity.Collision.CollisionResources;

import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.Objects.GameObjects;

import java.awt.*;
import java.util.List;

public class ObjectCollision extends ProjectedCollision {

    public ObjectCollision(Player player) {
        super(player);
    }

    public int checkObjectCollision(Rectangle projectedRect, int velocity, List<GameObjects> gameObjects, boolean isHorizontal) {
        for (GameObjects gameObject : gameObjects) {
            if (projectedRect.intersects(gameObject.getCollisionRectangle())) {
                int collisionBoxSize = player.getCollisionBoxSize();
                int collisionBoxOffsetX = player.getCollisionBoxOffsetX();
                int collisionBoxOffsetY = player.getCollisionBoxOffsetY();

                Rectangle objectRect = gameObject.getCollisionRectangle();

                if (isHorizontal) {
                    return velocity > 0
                        ? objectRect.x - collisionBoxSize - collisionBoxOffsetX
                        : objectRect.x + objectRect.width - collisionBoxOffsetX;
                } else {
                    return velocity > 0
                        ? objectRect.y - collisionBoxSize - collisionBoxOffsetY
                        : objectRect.y + objectRect.height - collisionBoxOffsetY;
                }
            }
        }
        return Integer.MIN_VALUE;
    }
}