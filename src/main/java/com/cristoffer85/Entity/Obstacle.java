package com.cristoffer85.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Line2D;

@Getter
@Setter
@AllArgsConstructor
public class Obstacle {
    private Rectangle rectangle;
    private Line2D diagonalLine;
    private Color color;

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
}