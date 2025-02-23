package com.cristoffer85.Main;

import com.cristoffer85.Objects.GameObjects;
import com.cristoffer85.Objects.LesserAxe;
import com.cristoffer85.States.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetSetter {
    private final GameState gameState;
    private final Map<String, List<GameObjects>> objectsByMap;

    public AssetSetter(GameState gameState) {
        this.gameState = gameState;
        this.objectsByMap = new HashMap<>();
        placeObjects();
    }

    private void placeObjects() {
        placeObjectsForMainWorld();
        placeObjectsForDungeon1();
    }

    private void placeObjectsForMainWorld() {
        List<GameObjects> mainWorldObjects = new ArrayList<>();

        mainWorldObjects.add(new LesserAxe(800, 200));

        // Place more objects here



        objectsByMap.put("MainWorld", mainWorldObjects);
    }

    private void placeObjectsForDungeon1() {
        List<GameObjects> dungeon1Objects = new ArrayList<>();

        dungeon1Objects.add(new LesserAxe(350, 300));
        dungeon1Objects.add(new LesserAxe(400, 300));

        // Place more objects here




        objectsByMap.put("Dungeon1", dungeon1Objects);
    }

    public void loadObjectsForMap(String mapName) {
        gameState.clearObjects();
        List<GameObjects> objects = objectsByMap.get(mapName);
        if (objects != null) {
            for (GameObjects object : objects) {
                gameState.addObject(object);
            }
        }
    }
}