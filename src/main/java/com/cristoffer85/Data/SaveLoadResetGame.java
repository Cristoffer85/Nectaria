package com.cristoffer85.Data;

import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.States.GameState;
import com.cristoffer85.States.SettingsState;
import com.cristoffer85.States.StatesResources.StateDefinitions;
import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.Main.AssetSetter;
import com.cristoffer85.Main.EventHandler;
import com.cristoffer85.Map.MapHandler;
import java.awt.Component;

import java.io.*;

public class SaveLoadResetGame {

    public static void saveGame(Player player, String profileName, String currentMap) {
        ProfileData saveData = new ProfileData(player.getX(), player.getY(), currentMap);
        String filePath = "profiles/" + profileName + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(saveData);
            System.out.println("Game saved successfully to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGame(Player player, GamePanel gamePanel, String profileName) {
        String filePath = "profiles/" + profileName + ".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            ProfileData saveData = (ProfileData) ois.readObject();
            player.setX(saveData.getPlayerX());
            player.setY(saveData.getPlayerY());
            gamePanel.loadMap(saveData.getCurrentMap());
            if(gamePanel.getEventHandler() != null) {
                gamePanel.getEventHandler().setPlayer(player);
            }
            System.out.println("Game loaded successfully from " + filePath);
            gamePanel.changeGameState(StateDefinitions.GAME);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public static void resetGame(GamePanel gamePanel, String profileName) {
    // Create new game objects
    Player newPlayer = new Player(30, 30, 64, 6);
    MapHandler newMapHandler = new MapHandler("MainWorld");
    newMapHandler.loadMap("MainWorld");
    
    // Create a new event handler and update references
    EventHandler newEventHandler = new EventHandler(newPlayer, newMapHandler);
    newMapHandler.setEventHandler(newEventHandler);
    gamePanel.setEventHandler(newEventHandler);
    
    // Create a new game state with the new player and event handler
    GameState newGameState = new GameState(newPlayer, gamePanel.getWidth(), gamePanel.getHeight(), newEventHandler);
    
    // Initialize the asset setter for the new game state and map handler
    AssetSetter newAssetSetter = new AssetSetter(newGameState);
    newMapHandler.setAssetSetter(newAssetSetter);
    
    // Remove any existing GameState component from the CardLayout to avoid duplicates
    for (Component comp : gamePanel.getComponents()) {
        if (comp instanceof GameState) {
            gamePanel.remove(comp);
        }
    }
    
    // Add the new game state to the panel under the proper name
    gamePanel.add(newGameState, StateDefinitions.GAME.name());
    gamePanel.setPlayer(newPlayer);
    gamePanel.initializeGameState(newGameState);
    
    // Reset any additional parameters, such as scale factor
    gamePanel.setScaleFactor(SettingsState.SCALE_FACTORS_MAP.get("SNES"));
    
    // Switch to the new game state view
    gamePanel.changeGameState(StateDefinitions.GAME);
    }
}