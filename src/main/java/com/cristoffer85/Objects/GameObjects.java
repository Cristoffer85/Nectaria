package com.cristoffer85.Objects;

import java.awt.Graphics;

public interface GameObjects {
    void draw(Graphics g, int x, int y);
    int getX();
    int getY();
    int getWidth();
    int getHeight();
}