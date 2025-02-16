package com.cristoffer85.Map;

import com.cristoffer85.Entity.Obstacle;

import java.util.HashMap;
import java.util.Map;

/* MapHandler loads maps + also obstacles/objects for the game, so no calling for it is longer needed in either
   Gamepanel or SaveLoadReset. 
   It also clears previous map data when loading a new map so the old one wont be rendered extra to it,
   making it able to switch and have individual map sizes (whatever size you want really, the size of the map.txt file determines it)
 */

public class MapHandler {
    private final Map<String, String> mapPaths = new HashMap<>();
    private String currentMap;

    public MapHandler(String initialMap) {
        mapPaths.put("MainWorld", "/MainWorld.txt");
        mapPaths.put("SecondWorld", "/SecondWorld.txt");
        loadMap(initialMap);
    }

    public void loadMap(String mapName) {
        String path = mapPaths.get(mapName);
        if (path != null) {
            Tile.clearTilePositions(); // Clear previous tile positions
            Tile.tilesByMapSize(path);
            Obstacle.loadObstacles(mapName); // Load obstacles for the new map
            currentMap = mapName;
        } else {
            throw new IllegalArgumentException("Map not found: " + mapName);
        }
    }

    public String getCurrentMap() {
        return currentMap;
    }
}