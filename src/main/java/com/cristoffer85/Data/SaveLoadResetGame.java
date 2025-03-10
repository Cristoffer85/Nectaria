package com.cristoffer85.Data;

import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.States.GameState;
import com.cristoffer85.States.SettingsState;
import com.cristoffer85.States.StatesResources.StateDefinitions;
import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.Main.EventHandler;
import com.cristoffer85.Map.MapHandler;

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
    
    //--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//
    // Something is wrong with this method, it does not reset events or objects properly......
    public static void resetGame(GamePanel gamePanel, String profileName) {
        Player player = new Player(30, 30, 64, 6);
        MapHandler mapHandler = new MapHandler("MainWorld");
        gamePanel.loadMap("MainWorld");
        EventHandler eventHandler = new EventHandler(player, mapHandler);
        mapHandler.setEventHandler(eventHandler);
        GameState gameState = new GameState(player, gamePanel.getWidth(), gamePanel.getHeight(), eventHandler);
        gamePanel.add(gameState, StateDefinitions.GAME.name());
        gamePanel.setPlayer(player);
        gamePanel.initializeGameState(gameState);
        gamePanel.setScaleFactor(SettingsState.SCALE_FACTORS_MAP.get("SNES"));
    }
    //--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//--//
}