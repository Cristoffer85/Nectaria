package com.cristoffer85.Entities;

import java.awt.*;

public class Obstacle {
    private Rectangle rectangle;

    public Obstacle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}