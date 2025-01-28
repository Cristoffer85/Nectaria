package com.cristoffer85.Main.MainResources;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Entity.Player;
import com.cristoffer85.States.GameState;
import com.cristoffer85.States.StatesResources.StatesDefinitions;
import com.cristoffer85.Main.GamePanel;
import com.cristoffer85.Tile.TileManager;

import java.io.*;

public class SaveLoadReset implements Serializable {
    private static final long serialVersionUID = 1L;

    private int playerX;
    private int playerY;

    public SaveLoadReset(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public static void saveGame(Player player) {
        SaveLoadReset saveData = new SaveLoadReset(player.getX(), player.getY());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.dat"))) {
            oos.writeObject(saveData);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGame(Player player, GamePanel gamePanel) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            SaveLoadReset saveData = (SaveLoadReset) ois.readObject();
            player.setX(saveData.getPlayerX());
            player.setY(saveData.getPlayerY());
            // Update other game state fields if needed
            System.out.println("Game loaded successfully.");
            gamePanel.setGameState(StatesDefinitions.GAME); // Switch to the game state
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void resetGame(GamePanel gamePanel, int baseWidth, int baseHeight, int scaleFactor) {
        // Reset player position, score, and other game-related states
        Player player = new Player(30, 30, 64, 6);
        Obstacle.addObstacles();
        TileManager.tilesByMapSize("/MainWorld.txt");
        GameState gameState = new GameState(player, baseWidth, baseHeight, scaleFactor);
        gamePanel.add(gameState, StatesDefinitions.GAME.name());
        gamePanel.setPlayer(player);
        gamePanel.setGameState(gameState);
    }
}