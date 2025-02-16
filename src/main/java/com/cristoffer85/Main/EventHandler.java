package com.cristoffer85.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.Map.MapHandler;

public class EventHandler {
    private final List<Rectangle> eventRectangles = new ArrayList<>();
    private final Player player;
    private final MapHandler mapHandler;

    public EventHandler(Player player, MapHandler mapHandler) {
        this.player = player;
        this.mapHandler = mapHandler;
        // Add test event rectangle
        eventRectangles.add(new Rectangle(100, 100, 32, 32)); // Example event rectangle
    }

    public void checkEvents() {
        Rectangle playerCollisionBox = new Rectangle(player.getX() + player.getCollisionBoxOffsetX(), player.getY() + player.getCollisionBoxOffsetY(), player.getCollisionBoxSize(), player.getCollisionBoxSize());
        for (Rectangle eventRect : eventRectangles) {
            if (playerCollisionBox.intersects(eventRect)) {
                triggerEvent(playerCollisionBox, eventRect);
            }
        }
    }

    private void triggerEvent(Rectangle playerCollisionBox, Rectangle eventRect) {
        
        // Transfer player from MainWorld to SecondWorld
        if (eventRect.equals(new Rectangle(100, 100, 32, 32))) {
            mapHandler.loadMap("SecondWorld");
            player.setX(50); 
            player.setY(50);
        }










        /* **** DEBUG - Print out the coordinates of the player's collision box and the event rectangle
        System.out.println("Event triggered at: " + eventRect);
        System.out.println("Player collision box: " + playerCollisionBox);
        System.out.println("Event rectangle: " + eventRect);
        */
    }

    public void drawEventRectangles(Graphics2D g2d, int cameraX, int cameraY) {
        g2d.setColor(Color.RED);
        for (Rectangle eventRect : eventRectangles) {
            g2d.drawRect(eventRect.x - cameraX, eventRect.y - cameraY, eventRect.width, eventRect.height);
        }
    }
}