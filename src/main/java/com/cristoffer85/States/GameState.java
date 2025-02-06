package com.cristoffer85.States;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Entity.Player;
import com.cristoffer85.Tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameState extends JPanel {
    private final Player player;
    private BufferedImage gameImage;
    private int baseWidth;
    private int baseHeight;
    private double scaleFactor = 1.0; // Default scale

    public GameState(Player player, int baseWidth, int baseHeight) {
        this.player = player;
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.gameImage = new BufferedImage((int) (baseWidth * scaleFactor), (int) (baseHeight * scaleFactor), BufferedImage.TYPE_INT_ARGB);
    }

    public void updateResolution(int width, int height) {
        this.baseWidth = width;
        this.baseHeight = height;
        this.gameImage = new BufferedImage((int) (baseWidth * scaleFactor), (int) (baseHeight * scaleFactor), BufferedImage.TYPE_INT_ARGB);
        revalidate();
        repaint();
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
        this.gameImage = new BufferedImage((int) (baseWidth * scaleFactor), (int) (baseHeight * scaleFactor), BufferedImage.TYPE_INT_ARGB);
        revalidate();
        repaint();
    }

    private void paintGame(Graphics g) {
        // Calculate position for Camera following player, keeps player centered on map when not near map boundary
        int cameraX = player.getX() - (int) (baseWidth * scaleFactor) / 2 + player.getSize() / 2;
        int cameraY = player.getY() - (int) (baseHeight * scaleFactor) / 2 + player.getSize() / 2 + 24;

        // Clamp camera position to map boundaries, when player is near map boundary
        int maxCameraX = Tile.getMapWidth() * Tile.getTileWidth() - (int) (baseWidth * scaleFactor);
        int maxCameraY = Tile.getMapHeight() * Tile.getTileHeight() - (int) (baseHeight * scaleFactor);
        cameraX = Math.max(0, Math.min(cameraX, maxCameraX));
        cameraY = Math.max(0, Math.min(cameraY, maxCameraY));

        // Render the game to the BufferedImage
        Graphics2D g2d = gameImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, gameImage.getWidth(), gameImage.getHeight());

        // Render all tiles with camera offset
        Tile.paintTiles(g2d, cameraX, cameraY);

        // Render all obstacles with camera offset
        Obstacle.paintObstacles(g2d, cameraX, cameraY);

        // Render player on top of obstacles
        player.paintPlayer(g2d, cameraX, cameraY);

        // Dispose of the Graphics2D object
        g2d.dispose();

        // Draw the BufferedImage scaled up to the panel size
        g.drawImage(gameImage, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGame(g);
    }
}