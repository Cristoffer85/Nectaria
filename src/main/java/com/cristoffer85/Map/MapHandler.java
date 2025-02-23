package com.cristoffer85.Map;

import com.cristoffer85.Entity.Obstacle;
import com.cristoffer85.Main.AssetSetter;
import com.cristoffer85.Main.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class MapHandler {
    private final Map<String, String> mapPaths = new HashMap<>();
    private String currentMap;
    private EventHandler eventHandler;
    private AssetSetter assetSetter;

    public MapHandler(String initialMap) {
        mapPaths.put("MainWorld", "/maps/MainWorld.txt");
        mapPaths.put("Dungeon1", "/maps/Dungeon1.txt");
        loadMap(initialMap);
    }

    public void loadMap(String mapName) {
        String path = mapPaths.get(mapName);
        if (path != null) {
            Tile.clearTilePositions();
            Tile.tilesByMapSize(path);
            Obstacle.loadObstacles(mapName);
            currentMap = mapName;
            if (eventHandler != null) {
                eventHandler.setupEventRectangles(mapName);
            }
            if (assetSetter != null) {
                assetSetter.loadObjectsForMap(mapName);
            }
            System.out.println("Map loaded: " + mapName);
        } else {
            throw new IllegalArgumentException("Map not found: " + mapName);
        }
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void setAssetSetter(AssetSetter assetSetter) {
        this.assetSetter = assetSetter;
    }
}