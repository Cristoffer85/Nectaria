package com.cristoffer85.Entity.Collision.CollisionResources;

import com.cristoffer85.Entity.Player.Player;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

public class DiagonalObstacleCollision extends ProjectedCollision {

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