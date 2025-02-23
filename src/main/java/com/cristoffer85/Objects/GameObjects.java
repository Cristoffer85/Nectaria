package com.cristoffer85.Objects;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface GameObjects {
    void draw(Graphics g, int x, int y);
    int getX();
    int getY();
    int getWidth();
    int getHeight();
    Rectangle getCollisionRectangle();
}