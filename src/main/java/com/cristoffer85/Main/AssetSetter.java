package com.cristoffer85.Main;

import com.cristoffer85.Objects.GameObject;
import com.cristoffer85.Objects.LesserAxe;
import com.cristoffer85.States.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetSetter {
    private final GameState gameState;
    private final Map<String, List<GameObject>> objectsByMap;

    public AssetSetter(GameState gameState) {
        this.gameState = gameState;
        this.objectsByMap = new HashMap<>();
        placeObjects();
    }

    private void placeObjects() {
        placeObjectsForMainWorld();
        placeObjectsForSecondWorld();
    }

    private void placeObjectsForMainWorld() {
        List<GameObject> mainWorldObjects = new ArrayList<>();

        mainWorldObjects.add(new LesserAxe(100, 100));
        mainWorldObjects.add(new LesserAxe(200, 200));

        // Place more objects here



        objectsByMap.put("MainWorld", mainWorldObjects);
    }

    private void placeObjectsForSecondWorld() {
        List<GameObject> secondWorldObjects = new ArrayList<>();

        secondWorldObjects.add(new LesserAxe(350, 300));
        secondWorldObjects.add(new LesserAxe(400, 300));

        // Place more objects here




        objectsByMap.put("SecondWorld", secondWorldObjects);
    }

    public void loadObjectsForMap(String mapName) {
        gameState.clearObjects();
        List<GameObject> objects = objectsByMap.get(mapName);
        if (objects != null) {
            for (GameObject object : objects) {
                gameState.addObject(object);
            }
        }
    }
}