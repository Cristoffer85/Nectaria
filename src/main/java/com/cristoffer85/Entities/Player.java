package com.cristoffer85.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.cristoffer85.Main.KeyHandler;

import java.awt.*;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private int x, y;
    private final int size;
    private final int moveSpeed;
    private final int acceleration = 1;
    private final int deceleration = 1;

    private int velocityX = 0;
    private int velocityY = 0;

    public void move(KeyHandler keyHandler, Rectangle boundary, Rectangle obstacle) {
    // Define acceleration logic using a map for horizontal and vertical movement
    Map<String, Runnable> keyActions = Map.of(
        "moveLeft", () -> velocityX = Math.max(velocityX - acceleration, -moveSpeed),
        "moveRight", () -> velocityX = Math.min(velocityX + acceleration, moveSpeed),
        "moveUp", () -> velocityY = Math.max(velocityY - acceleration, -moveSpeed),
        "moveDown", () -> velocityY = Math.min(velocityY + acceleration, moveSpeed)
    );

    // Apply key actions if keys are pressed
    keyActions.forEach((key, action) -> {
        if (keyHandler.isKeyPressed(key)) {
            action.run();
        }
    });

    // Decelerate if no keys are pressed
    if (!keyHandler.isKeyPressed("moveLeft") && !keyHandler.isKeyPressed("moveRight")) {
        velocityX = applyDeceleration(velocityX);
    }
    if (!keyHandler.isKeyPressed("moveUp") && !keyHandler.isKeyPressed("moveDown")) {
        velocityY = applyDeceleration(velocityY);
    }

    // Process movement and snapping
    x = processMovement(x, velocityX, boundary.width, obstacle, true);
    y = processMovement(y, velocityY, boundary.height, obstacle, false);
}

// Deceleration logic (reuse for both X and Y)
private int applyDeceleration(int velocity) {
    if (velocity > 0) return Math.max(velocity - deceleration, 0);
    if (velocity < 0) return Math.min(velocity + deceleration, 0);
    return 0;
}

// Movement processing with boundary and collision handling
private int processMovement(int current, int velocity, int boundaryLimit, Rectangle obstacle, boolean isHorizontal) {
    int projected = current + velocity;

    // Boundary snapping
    if (projected < 0) return 0;
    if (projected > boundaryLimit - size) return boundaryLimit - size;

    // Collision detection
    Rectangle projectedRect = isHorizontal
        ? new Rectangle(projected, y, size, size) // Horizontal movement
        : new Rectangle(x, projected, size, size); // Vertical movement

    if (projectedRect.intersects(obstacle)) {
        return isHorizontal
            ? (velocity > 0 ? obstacle.x - size : obstacle.x + obstacle.width) // Snap horizontally
            : (velocity > 0 ? obstacle.y - size : obstacle.y + obstacle.height); // Snap vertically
    }

    // No collision, return projected position
    return projected;
}


    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);
    }
}