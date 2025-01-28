package com.cristoffer85.Main;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Tile.TileManager;
import com.cristoffer85.Entity.Player;
import com.cristoffer85.States.StatesDefinitions;
import com.cristoffer85.States.MainMenuState;

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
    private StatesDefinitions statesDefinitions;
    private MainMenuState mainMenuState;
    private JPanel gameContentPanel;

    // Method to change between different game states. Creates the different states as a card, and can later switch between them 
    public void setGameState(StatesDefinitions newState) {
        this.statesDefinitions = newState;
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, newState.name());
    }

    public GamePanel(int baseWidth, int baseHeight, int scaleFactor) {
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.scaleFactor = scaleFactor;
        this.statesDefinitions = StatesDefinitions.MAIN_MENU;

        // Initialize player and obstacles
        player = new Player(30, 30, 64, 6, 0, 0); // Use 64x64 size for player, velocityY and velocityX are dummy values for now required for acceleration and deceleration
        Obstacle.addObstacles();

        // Keyhandling methods
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);

        // Load tilesheet
        TileManager.loadTilesheet("/TileSheet.png", 64, 64);  // sets the tile size to 64x64
        
        // Load map
        TileManager.tilesByMapSize("/MainWorld.txt");

        // Create the game image with the base resolution
        gameImage = new BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_ARGB);

        // Initialize Main menu
        mainMenuState = new MainMenuState(this);

        // Initialize game content
        gameContentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                GamePanel.this.paintGame(g);
            }
        };

        // Add main menu and game content panels to the card layout
        setLayout(new CardLayout());
        add(mainMenuState, StatesDefinitions.MAIN_MENU.name());
        add(gameContentPanel, StatesDefinitions.GAME.name());

        // Game loop
        Timer timer = new Timer(16, e -> {
            if (statesDefinitions == StatesDefinitions.GAME) {
                // Update game state
                List<Rectangle> straightObstacles = Obstacle.getStraightObstacles();
                List<Line2D> diagonalObstacles = Obstacle.getDiagonalObstacles();
                player.move(keyHandler, straightObstacles, diagonalObstacles);
                repaint();
            }
        });
        timer.start();
    }

    protected void paintGame(Graphics g) {
        if (statesDefinitions == StatesDefinitions.GAME) {
            // Calculate position for Camera following player, keeps player centered on map when not near map boundary
            int cameraX = player.getX() - baseWidth / 2 + player.getSize() / 2;
            int cameraY = player.getY() - baseHeight / 2 + player.getSize() / 2 + 24;

            // Clamp camera position to map boundaries, makes you know when you are near the map boundary, also neat huh? :)
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

            // Dispose of the Graphics2D object, in order to free up resources/save memory/optimization
            g2d.dispose();

            // Draw the BufferedImage scaled up to the panel size
            g.drawImage(gameImage, 0, 0, gameImage.getWidth() * scaleFactor, gameImage.getHeight() * scaleFactor, null);
        }
    }
}