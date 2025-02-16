package com.cristoffer85.Entity.Collision.CollisionResources;

import com.cristoffer85.Entity.Player.Player;

import java.awt.*;
import java.util.List;

public class StraightObstacleCollision extends ProjectedCollision {

    public StraightObstacleCollision(Player player) {
        super(player);
    }

    public int checkStraightObstacleCollision(Rectangle projectedRect, List<Rectangle> straightObstacles, int velocity, boolean isHorizontal) {
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
}
