package com.cristoffer85.Main;

import com.cristoffer85.Entity.Player.Player;
import com.cristoffer85.Map.MapHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventHandler {
    private final List<Rectangle> eventRectangles = new ArrayList<>();
    private Player player;
    private final MapHandler mapHandler;

    private static final Map<String, Rectangle> EVENT_RECTANGLES = new HashMap<>();

    static {
        EVENT_RECTANGLES.put("MainWorld", new Rectangle(100, 100, 32, 32));
        EVENT_RECTANGLES.put("SecondWorld", new Rectangle(50, 50, 32, 32));
    }

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
        if (mapHandler.getCurrentMap().equals("MainWorld") && eventRect.equals(EVENT_RECTANGLES.get("MainWorld")))
            switchMap("SecondWorld", 50, 50);

        if (mapHandler.getCurrentMap().equals("SecondWorld") && eventRect.equals(EVENT_RECTANGLES.get("SecondWorld")))
            switchMap("MainWorld", 100, 100);

        

        /* DEBUG event rectangle trigger and hit
        Print out the coordinates of the player's collision box and the event rectangle
        System.out.println("Event triggered at: " + eventRect);
        System.out.println("Player collision box: " + playerCollisionBox);
        System.out.println("Event rectangle: " + eventRect);
        */
    }

    public void setupEventRectangles(String mapName) {
        eventRectangles.clear();
        if (EVENT_RECTANGLES.containsKey(mapName)) {
            eventRectangles.add(EVENT_RECTANGLES.get(mapName));
        }
    }

    private void switchMap(String mapName, int playerX, int playerY) {
        mapHandler.loadMap(mapName);
        player.setX(playerX);
        player.setY(playerY);
        setupEventRectangles(mapName);
    }

    public void setPlayer(Player newPlayer) {
        this.player = newPlayer;
    }

    public void drawEventRectangles(Graphics2D g2d, int cameraX, int cameraY) {
        g2d.setColor(Color.RED);
        for (Rectangle eventRect : eventRectangles) {
            g2d.drawRect(eventRect.x - cameraX, eventRect.y - cameraY, eventRect.width, eventRect.height);
        }
    }
}