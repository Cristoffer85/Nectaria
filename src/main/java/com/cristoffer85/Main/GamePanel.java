package com.cristoffer85.Main;

import com.cristoffer85.Data.SaveLoadReset;
import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.States.MainMenuState;
import com.cristoffer85.States.StatesResources.StateDefinitions;
import com.cristoffer85.States.CharacterState;
import com.cristoffer85.States.GameState;
import com.cristoffer85.States.InitialState;
import com.cristoffer85.States.PauseState;
import com.cristoffer85.States.SettingsState;
import com.cristoffer85.Map.MapHandler;
import com.cristoffer85.Map.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

public class GamePanel extends JPanel {
    private String profileName;
    private Player player;
    private KeyHandler keyHandler;
    private MapHandler mapHandler;
    private EventHandler eventHandler;

    private StateDefinitions currentState;
    private MainMenuState mainMenuState;
    private GameState gameState;
    private PauseState pauseState;
    private InitialState initialState;
    private SettingsState settingsState;
    private CharacterState characterState;

    public GamePanel() {

        // --------- Initializization of diverse game components. NOTE: Do NOT change order of below initializations. It will mess up events among one thing. ---------
        // Player
        player = new Player(30, 30, 64, 6);                            // Set player starting x, starting y, sprite size, moving speed
        // Keyhandler
        keyHandler = new KeyHandler(this);
        // Tilesheet
        Tile.loadTilesheet("/TileSheet.png", 64, 64);          // Load TileSheet.png from file, set tile width and tile height. Map rendering will adjust to these values.
        // Load initial map
        mapHandler = new MapHandler("MainWorld");                             // Load map.txt from file. Set whatever size you want for the map in the text file. Mainworld right now = 128x128 tiles.
        // Event handler
        eventHandler = new EventHandler(player, mapHandler);
        // Sets up events per individual map and player
        mapHandler.setEventHandler(eventHandler);
        // -----------------------------------------------------------------------------------------------------------------------------------------------------------

        // --------- Initialize different states ---------------------------
        initialState = new InitialState(this);
        mainMenuState = new MainMenuState(this);
        gameState = new GameState(player, 1920, 1080, eventHandler);              // Default values for 1920x1080 resolution
        gameState.setScaleFactor(0.5);                                       // Default start scale factor .5 for 'SNES' style
        pauseState = new PauseState(this);
        settingsState = new SettingsState(this); 
        characterState = new CharacterState(this, gameState, player);

        // ..and add them to the "card" layout...
        setLayout(new CardLayout());
        add(initialState, StateDefinitions.INITIAL_STATE.name());
        add(mainMenuState, StateDefinitions.MAIN_MENU.name());
        add(gameState, StateDefinitions.GAME.name());
        add(pauseState, StateDefinitions.PAUSE_MENU.name());
        add(settingsState, StateDefinitions.SETTINGS_MENU.name());
        add(characterState, StateDefinitions.CHARACTER_STATE.name());
        //------------------------------------------------------------------

        // --------- Main Game loop ----------------------------------------
        Timer timer = new Timer(16, e -> {

                // Every timer tick (In GAME-state) Check if: Player move - Collide - Key press - Repaint 
                if (currentState == StateDefinitions.GAME) {
                    List<Rectangle> straightObstacles = Obstacle.getStraightObstacles();
                    List<Line2D> diagonalObstacles = Obstacle.getDiagonalObstacles();
                    player.move(keyHandler, straightObstacles, diagonalObstacles);
                    eventHandler.checkEvents();
                    gameState.repaint();
                }
            });
        timer.start();
        //------------------------------------------------------------------
    }

    // ## Helper Methods ##
    public StateDefinitions getCurrentState() {
        return currentState;
    }

    public void initializeGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void changeGameState(StateDefinitions newState) {
        if (newState == StateDefinitions.PAUSE_MENU) {
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
        if (eventHandler != null) {
            eventHandler.setPlayer(player);
        }
    }

    public void saveGame() {
        SaveLoadReset.saveGame(player, profileName, mapHandler.getCurrentMap());
    }

    public void loadGame() {
        SaveLoadReset.loadGame(player, this, profileName);
    }

    public void resetGame() {
        SaveLoadReset.resetGame(this, profileName);
    }

    public void changeResolution(int width, int height) {
        gameState.updateResolution(width, height);
    }

    public void setScaleFactor(double scaleFactor) {
        gameState.setScaleFactor(scaleFactor);
    }

    public void loadMap(String mapName) {
        mapHandler.loadMap(mapName);
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }
}