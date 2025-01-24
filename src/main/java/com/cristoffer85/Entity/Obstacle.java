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

    public void render(Graphics g) {
        g.setColor(color);
        if (rectangle != null) {
            g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        } else if (diagonalLine != null) {
            ((Graphics2D) g).draw(diagonalLine);
        }
    }

    public static void addObstacles() {
        // Add obstacles
        obstacles.add(new Obstacle(new Rectangle(200, 200, 10, 50), Color.RED));
        obstacles.add(new Obstacle(new Rectangle(300, 300, 50, 50), Color.RED));    // Add more obstacles as needed
        obstacles.add(new Obstacle(new Line2D.Float(100, 100, 150, 150), Color.BLUE));   // Add a diagonal line for testing

        // Separate obstacles into straight and diagonal lists
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getRectangle() != null) {
                straightObstacles.add(obstacle.getRectangle());
            } else if (obstacle.getDiagonalLine() != null) {
                diagonalObstacles.add(obstacle.getDiagonalLine());
            }
        }
    }

    public static List<Obstacle> getObstacles() {
        return obstacles;
    }

    public static List<Rectangle> getStraightObstacles() {
        return straightObstacles;
    }

    public static List<Line2D> getDiagonalObstacles() {
        return diagonalObstacles;
    }

    public static void renderAll(Graphics2D g2d, int cameraX, int cameraY) {
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
}