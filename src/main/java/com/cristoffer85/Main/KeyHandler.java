package com.cristoffer85.Main;

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
            "moveDown", KeyEvent.VK_S
        );
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    public boolean isKeyPressed(String action) {
        return keysPressed.contains(keyBindings.get(action));
    }
}