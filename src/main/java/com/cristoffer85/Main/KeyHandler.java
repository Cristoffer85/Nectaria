package com.cristoffer85.Main;

import com.cristoffer85.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KeyHandler extends KeyAdapter {
    private final Set<Integer> keysPressed = new HashSet<>();
    private final Map<String, Integer> keyBindings;

    public KeyHandler() {
        keyBindings = Map.of(
            "moveLeft", KeyEvent.VK_A,
            "moveRight", KeyEvent.VK_D,
            "moveUp", KeyEvent.VK_W,
            "moveDown", KeyEvent.VK_S,
            "resolution720p", KeyEvent.VK_F1,
            "resolution1080p", KeyEvent.VK_F2,
            "resolution1440p", KeyEvent.VK_F3
        );
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
        handleResolutionChange(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    public boolean isKeyPressed(String action) {
        return keysPressed.contains(keyBindings.get(action));
    }

    private void handleResolutionChange(KeyEvent e) {
        if (e.getKeyCode() == keyBindings.get("resolution720p")) {
            Game.changeResolution(1280, 720); // Switch to 1280x720
        } else if (e.getKeyCode() == keyBindings.get("resolution1080p")) {
            Game.changeResolution(1920, 1080); // Switch to 1920x1080
        } else if (e.getKeyCode() == keyBindings.get("resolution1440p")) {
            Game.changeResolution(2560, 1440); // Switch to 2560x1440
        }
    }
}