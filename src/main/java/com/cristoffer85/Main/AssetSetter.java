package com.cristoffer85.Main;

import com.cristoffer85.Objects.GameObject;
import com.cristoffer85.Objects.LesserAxe;
import com.cristoffer85.States.GameState;

import java.util.ArrayList;
import java.util.List;

public class AssetSetter {
    private final GameState gameState;
    private final List<GameObject> objects;

    public AssetSetter(GameState gameState) {
        this.gameState = gameState;
        this.objects = new ArrayList<>();
        placeObjects();
    }

    private void placeObjects() {
        placeLesserAxe(100, 100);
        placeLesserAxe(200, 200);
    }

    private void placeLesserAxe(int x, int y) {
        LesserAxe lesserAxe = new LesserAxe(x, y);
        objects.add(lesserAxe);
        gameState.addObject(lesserAxe);
    }

    public List<GameObject> getObjects() {
        return objects;
    }
}