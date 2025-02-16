package com.cristoffer85.Main;

import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.Map.MapHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    private final List<Rectangle> eventRectangles = new ArrayList<>();
    private final Player player;
    private final MapHandler mapHandler;

    public EventHandler(Player player, MapHandler mapHandler) {
        this.player = player;
        this.mapHandler = mapHandler;
        setupEventRectangles(mapHandler.getCurrentMap());
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
        // Transfer player between worlds based on the event rectangle
        if (mapHandler.getCurrentMap().equals("MainWorld") && eventRect.equals(new Rectangle(100, 100, 32, 32))) {
            mapHandler.loadMap("SecondWorld");
            player.setX(50);
            player.setY(50);
            setupEventRectangles("SecondWorld");
        } else if (mapHandler.getCurrentMap().equals("SecondWorld") && eventRect.equals(new Rectangle(50, 50, 32, 32))) {
            mapHandler.loadMap("MainWorld");
            player.setX(100);
            player.setY(100);
            setupEventRectangles("MainWorld");
        }

        /* DEBUG event rectangle trigger and hit
        Print out the coordinates of the player's collision box and the event rectangle
        System.out.println("Event triggered at: " + eventRect);
        System.out.println("Player collision box: " + playerCollisionBox);
        System.out.println("Event rectangle: " + eventRect);
        */
    }

    public void setupEventRectangles(String mapName) {
        eventRectangles.clear();
        if (mapName.equals("MainWorld")) {
            eventRectangles.add(new Rectangle(100, 100, 32, 32)); // Example event rectangle in MainWorld
        } else if (mapName.equals("SecondWorld")) {
            eventRectangles.add(new Rectangle(50, 50, 32, 32)); // Example event rectangle in SecondWorld
        }
    }

    public void drawEventRectangles(Graphics2D g2d, int cameraX, int cameraY) {
        g2d.setColor(Color.RED);
        for (Rectangle eventRect : eventRectangles) {
            g2d.drawRect(eventRect.x - cameraX, eventRect.y - cameraY, eventRect.width, eventRect.height);
        }
    }
}