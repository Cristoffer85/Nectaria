package com.cristoffer85.Entity.Collision.CollisionResources;

import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.Map.Tile;
import com.cristoffer85.Map.TileManager;

import java.awt.*;
import java.util.Map;

public class TileCollision extends ProjectedCollision {

    public TileCollision(Player player) {
        super(player);
    }

    public int checkTileCollision(Rectangle projectedRect, int velocity, boolean isHorizontal) {
        int tileWidth = Tile.getTileWidth();
        int tileHeight = Tile.getTileHeight();
        int mapWidth = Tile.getMapWidth();
        int mapHeight = Tile.getMapHeight();
        Map<Point, Integer> tilePositions = Tile.getTilePositions();

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                TileManager tile = Tile.getTile(tilePositions.get(new Point(x, y)));
                if (tile != null && tile.isCollidable()) {
                    Rectangle tileRect = new Rectangle(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                    if (projectedRect.intersects(tileRect)) {
                        int collisionBoxSize = player.getCollisionBoxSize();
                        int collisionBoxOffsetX = player.getCollisionBoxOffsetX();
                        int collisionBoxOffsetY = player.getCollisionBoxOffsetY();

                        if (isHorizontal) {
                            return velocity > 0
                                ? tileRect.x - collisionBoxSize - collisionBoxOffsetX
                                : tileRect.x + tileRect.width - collisionBoxOffsetX;
                        } else {
                            return velocity > 0
                                ? tileRect.y - collisionBoxSize - collisionBoxOffsetY
                                : tileRect.y + tileRect.height - collisionBoxOffsetY;
                        }
                    }
                }
            }
        }
        return Integer.MIN_VALUE;
    }
}
