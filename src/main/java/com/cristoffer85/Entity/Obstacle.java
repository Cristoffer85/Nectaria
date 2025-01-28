package com.cristoffer85.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Obstacle {
    private Rectangle rectangle;
    private Line2D diagonalLine;
    private Color color;

    private static List<Obstacle> obstacles = new ArrayList<>();
    private static List<Rectangle> straightObstacles = new ArrayList<>();
    private static List<Line2D> diagonalObstacles = new ArrayList<>();

    public Obstacle(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    public Obstacle(Line2D diagonalLine, Color color) {
        this.diagonalLine = diagonalLine;
        this.color = color;
    }

    public static void addObstacles() {
        // Add obstacles
        obstacles.add(new Obstacle(new Rectangle(300, 300, 50, 64), Color.RED));
        obstacles.add(new Obstacle(new Rectangle(192, 192, 1, 64), Color.RED));
        obstacles.add(new Obstacle(new Line2D.Float(194, 193, 258, 257), Color.BLUE));
        obstacles.add(new Obstacle(new Rectangle(400, 200, 64, 1), Color.RED));

        // Separate obstacles into straight and diagonal lists
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getRectangle() != null) {
                straightObstacles.add(obstacle.getRectangle());
            } else if (obstacle.getDiagonalLine() != null) {
                diagonalObstacles.add(obstacle.getDiagonalLine());
            }
        }
    }

    public static void paintObstacles(Graphics2D g2d, int cameraX, int cameraY) {
        // Render all straight obstacles with camera offset
        g2d.setColor(Color.RED);
        for (Rectangle obstacle : straightObstacles) {
            g2d.drawRect(obstacle.x - cameraX, obstacle.y - cameraY, obstacle.width, obstacle.height);
        }

        // Render all diagonal obstacles with camera offset
        g2d.setColor(Color.BLUE);
        for (Line2D obstacle : diagonalObstacles) {
            g2d.drawLine((int) obstacle.getX1() - cameraX, (int) obstacle.getY1() - cameraY, (int) obstacle.getX2() - cameraX, (int) obstacle.getY2() - cameraY);
        }
    }

    // Helper methods for other classes to access the obstacle data
    public static List<Obstacle> getObstacles() {
        return obstacles;
    }

    public static List<Rectangle> getStraightObstacles() {
        return straightObstacles;
    }

    public static List<Line2D> getDiagonalObstacles() {
        return diagonalObstacles;
    }
}