package com.cristoffer85.Main;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Tile.Tile;
import com.cristoffer85.Entity.Player;
import com.cristoffer85.States.MainMenuState;
import com.cristoffer85.States.StatesDefinitions;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private KeyHandler keyHandler;
    private BufferedImage gameImage;
    private int scaleFactor;
    private final int baseWidth;
    private final int baseHeight;
    private StatesDefinitions gameState;
    private MainMenuState mainMenu;

    public GamePanel(int baseWidth, int baseHeight, int scaleFactor) {
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.scaleFactor = scaleFactor;
        this.gameState = StatesDefinitions.MAIN_MENU;

        // Initialization of player and obstacles
        player = new Player(30, 30, 64, 6, 0, 0); // Use 64x64 size for player
        Obstacle.addObstacles();

        // Keyhandling methods
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);

        // Load tilesheet
        Tile.loadTilesheet("/TileSheet.png", 64, 64);           // Use 64x64 tiles

        // Initialize tiles from file
        Tile.initializeTiles("/MainWorld.txt", 64, 64);     // Use 64x64 tiles

        // Create the game image with the base resolution
        gameImage = new BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_ARGB);

        // Initialize main menu panel
        mainMenu = new MainMenuState(this);
        setLayout(new CardLayout());
        add(mainMenu, StatesDefinitions.MAIN_MENU.name());
        add(new Game(), StatesDefinitions.GAME.name());

        // Game loop
        Timer timer = new Timer(16, e -> {
            if (gameState == StatesDefinitions.GAME) {
                // Update game state
                List<Rectangle> straightObstacles = Obstacle.getStraightObstacles();
                List<Line2D> diagonalObstacles = Obstacle.getDiagonalObstacles();
                player.move(keyHandler, straightObstacles, diagonalObstacles);
                repaint();
            }
        });
        timer.start();
    }

    public void setGameState(StatesDefinitions gameState) {
        this.gameState = gameState;
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, gameState.name());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameState == StatesDefinitions.GAME) {
            // Calculate position for Camera following player, keeps player centered on map when not near map boundary
            int cameraX = player.getX() - baseWidth / 2 + player.getSize() / 2;
            int cameraY = player.getY() - baseHeight / 2 + player.getSize() / 2 + 24;

            // Clamp camera position to map boundaries, makes you know when you are near the map boundary, also neat huh? :)
            int maxCameraX = Tile.getMapWidth() * Tile.getTileWidth() - baseWidth;
            int maxCameraY = Tile.getMapHeight() * Tile.getTileHeight() - baseHeight;
            cameraX = Math.max(0, Math.min(cameraX, maxCameraX));
            cameraY = Math.max(0, Math.min(cameraY, maxCameraY));

            // Render the game to the BufferedImage
            Graphics2D g2d = gameImage.createGraphics();
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, gameImage.getWidth(), gameImage.getHeight());

            // Render all tiles with camera offset
            Tile.renderAll(g2d, cameraX, cameraY);

            // Render all obstacles with camera offset
            Obstacle.renderAll(g2d, cameraX, cameraY);

            // Render player on top of obstacles
            player.render(g2d, cameraX, cameraY);

            // Dispose of the Graphics2D object, in order to free up resources/save memory/optimization
            g2d.dispose();

            // Draw the BufferedImage scaled up to the panel size
            g.drawImage(gameImage, 0, 0, gameImage.getWidth() * scaleFactor, gameImage.getHeight() * scaleFactor, null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(baseWidth * scaleFactor, baseHeight * scaleFactor);
    }

    private class Game extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            GamePanel.this.paintComponent(g);
        }
    }
}