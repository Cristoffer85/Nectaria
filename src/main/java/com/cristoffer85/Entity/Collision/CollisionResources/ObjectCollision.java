package com.cristoffer85.Entity.Collision.CollisionResources;

import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.Objects.GameObjects;

import java.awt.*;
import java.util.List;

public class ObjectCollision extends PROJECTEDCollision {

    public ObjectCollision(Player player) {
        super(player);
    }

    public GameObjects checkObjectCollision(Rectangle projectedRect, List<GameObjects> gameObjects) {
        for (GameObjects gameObject : gameObjects) {
            if (projectedRect.intersects(gameObject.getCollisionRectangle())) {
                return gameObject;
            }
        }
        return null;
    }
}