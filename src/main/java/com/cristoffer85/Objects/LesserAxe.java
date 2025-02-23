package com.cristoffer85.Objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class LesserAxe implements GameObjects {
    private int x, y;
    private int width, height;
    private BufferedImage image;
    private Rectangle collisionRectangle;

    public LesserAxe(int x, int y) {
        this.x = x;
        this.y = y;
        loadImage();
        initializeCollisionRectangle();
    }

    private void loadImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/LesserAxe.png"));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeCollisionRectangle() {
        int collisionWidth = width / 2;
        int collisionHeight = height / 2;
        int collisionOffsetX = width / 4;
        int collisionOffsetY = height / 4;
        collisionRectangle = new Rectangle(x + collisionOffsetX, y + collisionOffsetY, collisionWidth, collisionHeight);
    }

    @Override
    public void draw(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
        
        // Draw collision rectangle for debugging
        g.drawRect(collisionRectangle.x - this.x + x, collisionRectangle.y - this.y + y, collisionRectangle.width, collisionRectangle.height);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }
}