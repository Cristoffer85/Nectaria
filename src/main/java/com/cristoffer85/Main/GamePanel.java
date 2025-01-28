package com.cristoffer85.Main;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Tile.TileManager;
import com.cristoffer85.Entity.Player;
import com.cristoffer85.States.MainMenuState;
import com.cristoffer85.States.StatesResources.StatesDefinitions;
import com.cristoffer85.States.GameState;
import com.cristoffer85.States.PauseState;
import com.cristoffer85.Main.MainResources.SaveLoadReset;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private KeyHandler keyHandler;
    private StatesDefinitions statesDefinitions;
    private MainMenuState mainMenuState;
    private GameState gameState;
    private PauseState pauseState;
    private int baseWidth;
    private int baseHeight;
    private int scaleFactor;

    // Initialize player
    public GamePanel(int baseWidth, int baseHeight, int scaleFactor) {
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
        this.scaleFactor = scaleFactor;

        // Initialize player
        player = new Player(30, 30, 64, 6);

        // Initialize obstacles
        Obstacle.addObstacles();

        // Initialize key handler
        keyHandler = new KeyHandler(this);

        // Initialize tilesheet and map
        TileManager.loadTilesheet("/TileSheet.png", 64, 64);
        TileManager.tilesByMapSize("/MainWorld.txt");

        // Initialize different states, and add them to a "card" layout
        mainMenuState = new MainMenuState(this);
        gameState = new GameState(player, baseWidth, baseHeight, scaleFactor);
        pauseState = new PauseState(this);

        setLayout(new CardLayout());
        add(mainMenuState, StatesDefinitions.MAIN_MENU.name());
        add(gameState, StatesDefinitions.GAME.name());
        add(pauseState, StatesDefinitions.PAUSE_MENU.name());

        //----------------------------------------------

        // Main Game loop
        Timer timer = new Timer(16, e -> {
            if (statesDefinitions == StatesDefinitions.GAME) {
                // Update game state
                List<Rectangle> straightObstacles = Obstacle.getStraightObstacles();
                List<Line2D> diagonalObstacles = Obstacle.getDiagonalObstacles();
                player.move(keyHandler, straightObstacles, diagonalObstacles);
                gameState.repaint();
            }
        });
        timer.start();
    }

    // ## Helper Methods ##

    // Get current game state
    public StatesDefinitions getCurrentState() {
        return statesDefinitions;
    }

    // Change between different states.
    public void setGameState(StatesDefinitions newState) {
        if (newState == StatesDefinitions.PAUSE_MENU) {
            // Capture the current GameState image, in order to display it in the PauseState behind menu.
            BufferedImage gameStateImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = gameStateImage.createGraphics();
            gameState.paint(g2d);
            g2d.dispose();
            pauseState.setGameStateImage(gameStateImage);
        }
        //Creates the different states as cards, and can later switch between them.
        this.statesDefinitions = newState;
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, newState.name());
    }

    public void resetGame() {
        SaveLoadReset.resetGame(this, baseWidth, baseHeight, scaleFactor);
    }

    public void saveGame() {
        SaveLoadReset.saveGame(player);
    }

    public void loadGame() {
        SaveLoadReset.loadGame(player, this);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}