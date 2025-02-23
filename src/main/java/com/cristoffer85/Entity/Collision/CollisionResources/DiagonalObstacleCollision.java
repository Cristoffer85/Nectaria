package com.cristoffer85.Entity.Collision.CollisionResources;

import com.cristoffer85.Entity.Player.Player;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

/* Right now only class using vector math collision to calculate player's new position, in difference to the other classes.
   
   This class needs to be studied harder to more thouroughly understand how it works.
   The class is used with the Line2D class, combined with both the projectedRect from ProjectedCollision, 
   and also the Line2D itself, from Obstacle.java have had its rectangles in its line split to smaller rectangles. Thats what make it works  for now.
   With that said as the tip study this specific vector class collision to understand it better.
 */

public class DiagonalObstacleCollision extends PROJECTEDCollision {

    public DiagonalObstacleCollision(Player player) {
        super(player);
    }

    public int checkDiagonalObstacleCollision(Rectangle projectedRect, List<Line2D> diagonalObstacles, int velocity, boolean isHorizontal) {
        for (Line2D diagonalObstacle : diagonalObstacles) {
            if (projectedRect.intersectsLine(diagonalObstacle)) {
                double dx = diagonalObstacle.getX2() - diagonalObstacle.getX1();
                double dy = diagonalObstacle.getY2() - diagonalObstacle.getY1();
                double length = Math.sqrt(dx * dx + dy * dy);
                double normalX = -dy / length;
                double normalY = dx / length;

                double dotProduct = (player.getVelocityX() * normalX + player.getVelocityY() * normalY);
                int reflectedX = (int) (player.getVelocityX() - 2 * dotProduct * normalX);
                int reflectedY = (int) (player.getVelocityY() - 2 * dotProduct * normalY);

                if (isHorizontal) {
                    player.setVelocityY(reflectedY);
                    if (velocity > 0) {
                        double leftEdge = Math.min(diagonalObstacle.getX1(), diagonalObstacle.getX2());
                        return (int) (leftEdge - player.getCollisionBoxSize() - player.getCollisionBoxOffsetX());
                    } else {
                        double rightEdge = Math.max(diagonalObstacle.getX1(), diagonalObstacle.getX2());
                        return (int) (rightEdge + player.getCollisionBoxSize() - 62 + player.getCollisionBoxOffsetX());
                    }
                } else {
                    player.setVelocityX(reflectedX);
                    if (velocity > 0) {
                        double topEdge = Math.min(diagonalObstacle.getY1(), diagonalObstacle.getY2());
                        return (int) (topEdge - player.getCollisionBoxSize() - player.getCollisionBoxOffsetY());
                    } else {
                        double bottomEdge = Math.max(diagonalObstacle.getY1(), diagonalObstacle.getY2());
                        return (int) (bottomEdge + player.getCollisionBoxSize() - 98 + player.getCollisionBoxOffsetY());
                    }
                }
            }
        }
        return Integer.MIN_VALUE;
    }
}