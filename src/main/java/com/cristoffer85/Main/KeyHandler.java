package com.cristoffer85.Main;

import com.cristoffer85.States.StatesResources.StateDefinitions;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class KeyHandler extends KeyAdapter {
    private final Set<Integer> keysPressed = new HashSet<>();
    private final Map<String, Integer> keyBindings;
    private final GamePanel gamePanel;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        keyBindings = Map.of(
            "moveLeft", KeyEvent.VK_A,
            "moveRight", KeyEvent.VK_D,
            "moveUp", KeyEvent.VK_W,
            "moveDown", KeyEvent.VK_S,
            "pause", KeyEvent.VK_ESCAPE
        );
        initialize();
    }

    // Initialize necessary key handling methods
    private void initialize() {
        gamePanel.addKeyListener(this);
        gamePanel.setFocusable(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
        handlePause(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    public boolean isKeyPressed(String action) {
        return keysPressed.contains(keyBindings.get(action));
    }

    // Specific individual key handling methods below == IF pressing ESC/Pause when in game, pause the game - IF pressing ESC/Pause when in pause menu, resume the game. Sure theres another more beautiful way of doing this but it works for now.
    private void handlePause(KeyEvent e) {
        if (e.getKeyCode() == keyBindings.get("pause")) {
            if (gamePanel.getCurrentState() == StateDefinitions.GAME) {
                gamePanel.changeGameState(StateDefinitions.PAUSE_MENU);
            } else if (gamePanel.getCurrentState() == StateDefinitions.PAUSE_MENU) {
                gamePanel.changeGameState(StateDefinitions.GAME);
            }
        }
    }
}