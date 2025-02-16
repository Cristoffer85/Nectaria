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

    public static void clearObstacles() {
        obstacles.clear();
        straightObstacles.clear();
        diagonalObstacles.clear();
    }

    public static void loadObstacles(String mapName) {
        clearObstacles();
        if (mapName.equals("MainWorld")) {
            addMainWorldObstacles();
        } else if (mapName.equals("SecondWorld")) {
            addSecondWorldObstacles();
        }
        categorizeObstacles();
    }

    private static void addMainWorldObstacles() {
        // Add obstacles for MainWorld
        obstacles.add(new Obstacle(new Rectangle(300, 300, 50, 64), Color.RED));
        obstacles.add(new Obstacle(new Rectangle(192, 192, 1, 64), Color.RED));
        obstacles.add(new Obstacle(new Rectangle(600, 100, 64, 1), Color.RED));
        obstacles.add(new Obstacle(new Line2D.Float(194, 193, 258, 257), Color.BLUE));
        obstacles.add(new Obstacle(new Line2D.Float(594, 193, 658, 257), Color.BLUE));
        obstacles.add(new Obstacle(new Line2D.Float(458, 193, 394, 257), Color.BLUE));
    }

    private static void addSecondWorldObstacles() {
        /* Add obstacles for SecondWorld
        obstacles.add(new Obstacle(new Rectangle(100, 100, 50, 64), Color.GREEN));
        obstacles.add(new Obstacle(new Rectangle(200, 200, 1, 64), Color.GREEN));
        obstacles.add(new Obstacle(new Rectangle(300, 300, 64, 1), Color.GREEN));
        obstacles.add(new Obstacle(new Line2D.Float(100, 100, 150, 150), Color.YELLOW));
        obstacles.add(new Obstacle(new Line2D.Float(200, 200, 250, 250), Color.YELLOW));
        obstacles.add(new Obstacle(new Line2D.Float(300, 300, 350, 350), Color.YELLOW));
        */
    }

    private static void categorizeObstacles() {
        straightObstacles.clear();
        diagonalObstacles.clear();
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getRectangle() != null) {
                straightObstacles.add(obstacle.getRectangle());
            } else if (obstacle.getDiagonalLine() != null) {
                subdivideLine(obstacle.getDiagonalLine());
            }
        }
    }

    private static void subdivideLine(Line2D line) {
        final double segmentLength = 0.5; // Smaller segment length for higher precision
        double x1 = line.getX1();
        double y1 = line.getY1();
        double x2 = line.getX2();
        double y2 = line.getY2();
        double length = line.getP1().distance(line.getP2());
        int numSegments = (int) (length / segmentLength);
        double dx = (x2 - x1) / numSegments;
        double dy = (y2 - y1) / numSegments;

        for (int i = 0; i < numSegments; i++) {
            double startX = x1 + i * dx;
            double startY = y1 + i * dy;
            double endX = x1 + (i + 1) * dx;
            double endY = y1 + (i + 1) * dy;
            diagonalObstacles.add(new Line2D.Double(startX, startY, endX, endY));
        }
    }

    public static void paintObstacles(Graphics2D g2d, int cameraX, int cameraY) {
        g2d.setColor(Color.RED);
        for (Rectangle obstacle : straightObstacles) {
            g2d.drawRect(obstacle.x - cameraX, obstacle.y - cameraY, obstacle.width, obstacle.height);
        }

        g2d.setColor(Color.BLUE);
        for (Line2D obstacle : diagonalObstacles) {
            g2d.drawLine((int) obstacle.getX1() - cameraX, (int) obstacle.getY1() - cameraY, (int) obstacle.getX2() - cameraX, (int) obstacle.getY2() - cameraY);
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
}