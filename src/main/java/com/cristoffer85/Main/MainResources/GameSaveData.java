package com.cristoffer85.Main.MainResources;

import java.io.Serializable;

public class GameSaveData implements Serializable {
    private static final long serialVersionUID = 1L;

    private int playerX;
    private int playerY;
  

    public GameSaveData(int playerX, int playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}