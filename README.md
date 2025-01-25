New try of a cleaner, good code 2D RPG game in Java, using Java Swing.

Use of @Lombok right now - In order to keep better overviewable logic of code, and understand whats happening and easier debug and implementation. All for scalability.

Features right now include:

# Player
- Correct collision for player against both vertical and diagonal objects
- Sprite update, decided on how many number of sprites you want really
- Acceleration/Deceleration of your player
- Cameralogic moving the map under the player, while also "clamp" values near border of map, ensuring the map stops moving when player near border.

# Common
- Sprite and Tile logic read and based from complete Tile- and SpriteSheets, optimizing memory performance
- Basic resolution implemented with scalefactor right now (Scalefactor can be altered, depending on your graphic preferences/view) best is no scaling at all i heard, but harder view on modern screens if not.

# Map
- Map read from .txt-file with arrayvalues from tilesheet recalculated based on tilesize, meaning you could set individual tilesizes for your game by a few clicks

# States
- Basic start implementation of different states (Have different states = Main menu-state, Game-state, Pause-state etc) implemented in what i would call a very easy and logic followed solution 
