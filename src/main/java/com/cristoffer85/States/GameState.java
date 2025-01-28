package com.cristoffer85.States;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Entity.Player;
import com.cristoffer85.Tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameState extends JPanel {
    private final Player player;
    private final BufferedImage gameImage;
    private final int baseWidth;
    private final int baseHeight;
    private final int scaleFactor;

    public GameState(Player player, int baseWidth, int baseHeight, int scaleFactor) {
        this.player = player;
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.scaleFactor = scaleFactor;
        this.gameImage = new BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_ARGB);
    }

    private void paintGame(Graphics g) {
        // Calculate position for Camera following player, keeps player centered on map when not near map boundary
        int cameraX = player.getX() - baseWidth / 2 + player.getSize() / 2;
        int cameraY = player.getY() - baseHeight / 2 + player.getSize() / 2 + 24;

        // Clamp camera position to map boundaries, when player is near map boundary
        int maxCameraX = TileManager.getMapWidth() * TileManager.getTileWidth() - baseWidth;
        int maxCameraY = TileManager.getMapHeight() * TileManager.getTileHeight() - baseHeight;
        cameraX = Math.max(0, Math.min(cameraX, maxCameraX));
        cameraY = Math.max(0, Math.min(cameraY, maxCameraY));

        // Render the game to the BufferedImage
        Graphics2D g2d = gameImage.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, gameImage.getWidth(), gameImage.getHeight());

        // Render all tiles with camera offset
        TileManager.paintTiles(g2d, cameraX, cameraY);

        // Render all obstacles with camera offset
        Obstacle.paintObstacles(g2d, cameraX, cameraY);

        // Render player on top of obstacles
        player.paintPlayer(g2d, cameraX, cameraY);

        // Dispose of the Graphics2D object
        g2d.dispose();

        // Draw the BufferedImage scaled up to the panel size
        g.drawImage(gameImage, 0, 0, gameImage.getWidth() * scaleFactor, gameImage.getHeight() * scaleFactor, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGame(g);
    }
}
