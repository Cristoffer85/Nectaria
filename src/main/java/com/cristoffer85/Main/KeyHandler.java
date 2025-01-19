package com.cristoffer85.Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;

public class KeyHandler extends KeyAdapter {
    private final Set<Integer> keysPressed;

    public KeyHandler(Set<Integer> keysPressed) {
        this.keysPressed = keysPressed;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    public int getPlayerMoveX() {
        int deltaX = 0;
        if (keysPressed.contains(KeyEvent.VK_A)) deltaX -= 1;
        if (keysPressed.contains(KeyEvent.VK_D)) deltaX += 1;
        return deltaX;
    }
    public int getPlayerMoveY() {
        int deltaY = 0;
        if (keysPressed.contains(KeyEvent.VK_W)) deltaY -= 1;
        if (keysPressed.contains(KeyEvent.VK_S)) deltaY += 1;
        return deltaY;
    }
}