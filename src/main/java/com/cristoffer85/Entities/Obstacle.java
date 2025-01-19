package com.cristoffer85.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@Getter
@AllArgsConstructor
public class Obstacle {
    private final Rectangle rectangle;

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}