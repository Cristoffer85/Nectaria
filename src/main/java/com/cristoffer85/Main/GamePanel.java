package com.cristoffer85.Main;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Tile.TileManager;
import com.cristoffer85.Entity.Player;
import com.cristoffer85.States.MainMenuState;
import com.cristoffer85.States.StatesResources.StatesDefinitions;
import com.cristoffer85.States.GameState;
import com.cristoffer85.States.InitialState;
import com.cristoffer85.States.PauseState;
import com.cristoffer85.Main.MainResources.CRUDProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private KeyHandler keyHandler;

    private MainMenuState mainMenuState;
    private GameState gameState;
    private PauseState pauseState;
    private InitialState initialState;

    private int baseWidth;
    private int baseHeight;
    private int scaleFactor;
    private String profileName;
    private StatesDefinitions currentState;

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

        // ---------- Initialize different states ----------
        initialState = new InitialState(this);
        mainMenuState = new MainMenuState(this);
        gameState = new GameState(player, baseWidth, baseHeight, scaleFactor);
        pauseState = new PauseState(this);

        // ..and add them to the "card" layout.
        setLayout(new CardLayout());
        add(initialState, StatesDefinitions.INITIAL_STATE.name());
        add(mainMenuState, StatesDefinitions.MAIN_MENU.name());
        add(gameState, StatesDefinitions.GAME.name());
        add(pauseState, StatesDefinitions.PAUSE_MENU.name());
        //---------------------------------------------------

        // Main Game loop
        Timer timer = new Timer(16, e -> {
                // IF GameState: Update game state
            if (currentState == StatesDefinitions.GAME) {
                List<Rectangle> straightObstacles = Obstacle.getStraightObstacles();
                List<Line2D> diagonalObstacles = Obstacle.getDiagonalObstacles();
                player.move(keyHandler, straightObstacles, diagonalObstacles);
                gameState.repaint();
            }
        });
        timer.start();
    }

    // ## Helper Methods ##
    public StatesDefinitions getCurrentState() {
        return currentState;
    }

    public void initializeGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void changeGameState(StatesDefinitions newState) {
        if (newState == StatesDefinitions.PAUSE_MENU) {
            pauseState.freezeGameBackground(this, gameState);
        }
        currentState = newState;
        ((CardLayout) getLayout()).show(this, newState.name());
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void saveGame() {
        CRUDProfile.saveGame(player, profileName);
    }

    public void loadGame() {
        CRUDProfile.loadGame(player, this, profileName);
    }

    public void resetGame() {
        CRUDProfile.resetGame(this, baseWidth, baseHeight, scaleFactor, profileName);
    }
}