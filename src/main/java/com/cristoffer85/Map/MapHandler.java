package com.cristoffer85.Map;

import java.util.HashMap;
import java.util.Map;

public class MapHandler {
    private final Map<String, String> mapPaths = new HashMap<>();
    private String currentMap;

    public MapHandler(String initialMap) {
        // Add maps here
        mapPaths.put("MainWorld", "/MainWorld.txt");
        mapPaths.put("SecondWorld", "/SecondWorld.txt");
        loadMap(initialMap);
    }

    public void loadMap(String mapName) {
        String path = mapPaths.get(mapName);
        if (path != null) {
            Tile.clearTilePositions(); // Clear previous tile positions
            Tile.tilesByMapSize(path);
            currentMap = mapName;
        } else {
            throw new IllegalArgumentException("Map not found: " + mapName);
        }
    }

    public String getCurrentMap() {
        return currentMap;
    }
}