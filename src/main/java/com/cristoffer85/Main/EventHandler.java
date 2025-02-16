package com.cristoffer85.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.cristoffer85.Entity.Player.Player;

public class EventHandler {
    private final List<Rectangle> eventRectangles = new ArrayList<>();
    private final Player player;

    public EventHandler(Player player) {
        this.player = player;
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
        // v v v -- Add event logic below here -- v v v
        


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