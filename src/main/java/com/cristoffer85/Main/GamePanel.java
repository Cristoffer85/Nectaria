package com.cristoffer85.Main;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Tile.TileManager;
import com.cristoffer85.Entity.Player;
import com.cristoffer85.States.MainMenuState;
import com.cristoffer85.States.StatesResources.StatesDefinitions;
import com.cristoffer85.States.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private KeyHandler keyHandler;
    private StatesDefinitions statesDefinitions;
    private MainMenuState mainMenuState;
    private GameState gameState;

    public GamePanel(int baseWidth, int baseHeight, int scaleFactor) {
        this.statesDefinitions = StatesDefinitions.MAIN_MENU;

        // Initialize player
        player = new Player(30, 30, 64, 6); // Startposition X and Y - size of playersprite - movespeed
        
        // Initialize obstacles
        Obstacle.addObstacles();

        // Keyhandling methods
        keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);

        // Load tilesheet and map
        TileManager.loadTilesheet("/TileSheet.png", 64, 64);  // Sets tile size to 64 Height and 64 Width
        TileManager.tilesByMapSize("/MainWorld.txt");

        // #### Initialize different states, and add them to a "card" layout ####
        mainMenuState = new MainMenuState(this);
        gameState = new GameState(player, baseWidth, baseHeight, scaleFactor);

        setLayout(new CardLayout());
        add(mainMenuState, StatesDefinitions.MAIN_MENU.name());
        add(gameState, StatesDefinitions.GAME.name());
        //----------------------------------------------------------

        // Game loop
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

      // Helper Method to change between different game states. Creates the different states as a card, and can later switch between them 
      public void setGameState(StatesDefinitions newState) {
        this.statesDefinitions = newState;
        CardLayout cl = (CardLayout) getLayout();
        cl.show(this, newState.name());
    }
}