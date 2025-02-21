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
            "pause", KeyEvent.VK_ESCAPE,
            "character", KeyEvent.VK_C
        );
        initialize();
    }

    private void initialize() {
        gamePanel.addKeyListener(this);
        gamePanel.setFocusable(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
        PauseState(e);
        CharacterState(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    public boolean isKeyPressed(String action) {
        return keysPressed.contains(keyBindings.get(action));
    }

    // PauseState key toggling
    private void PauseState(KeyEvent e) {
        if (e.getKeyCode() == keyBindings.get("pause")) {
            if (gamePanel.getCurrentState() == StateDefinitions.GAME) {
                gamePanel.changeGameState(StateDefinitions.PAUSE_MENU);
            } else if (gamePanel.getCurrentState() == StateDefinitions.PAUSE_MENU) {
                gamePanel.changeGameState(StateDefinitions.GAME);
            }
        }
    }

    // CharacterState key toggling
    private void CharacterState(KeyEvent e) {
        if (e.getKeyCode() == keyBindings.get("character")) {
            if (gamePanel.getCurrentState() == StateDefinitions.GAME) {
                gamePanel.changeGameState(StateDefinitions.CHARACTER_STATE);
            } else if (gamePanel.getCurrentState() == StateDefinitions.CHARACTER_STATE) {
                gamePanel.changeGameState(StateDefinitions.GAME);
            }
        }
    }
}