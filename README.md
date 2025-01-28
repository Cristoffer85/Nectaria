New try of a cleaner, good code 2D RPG game in Java, using Java Swing.

Use of @Lombok right now - In order to keep better overviewable logic of code, and understand whats happening and easier debug and implementation. All for scalability.

Features right now include:

## Player
- Correct collision and movement for player against both vertical and diagonal objects
- Sprite update, decided on how many number of sprites you want really
- Acceleration/Deceleration of your player
- Cameralogic moving the map under the player, while also "clamp" values near border of map, ensuring the map stops moving when player near border.
- Tiles, straight collision

## Common
- Sprite and Tile logic read and based from complete Tile- and SpriteSheets, optimizing memory performance
- Basic resolution implemented with scalefactor right now (Scalefactor can be altered, depending on your graphic preferences/view) best is no scaling at all i heard, but harder view on modern screens if not.

## Map
- Map read from .txt-file with arrayvalues from tilesheet recalculated based on tilesize, meaning you could set individual tilesizes for your game by a few clicks

## States
- Basic start implementation of different states (Have different states = Main menu-state, Game-state, Pause-state etc) implemented in what i would call a very easy and logic followed solution 





# Why i wanted to do this?
- I wanted to dive deeper into clean code practice, main mindset "Why am i writing this?" and why did i do it this way before? Writing and implementing something sometimes very complicated and snarky as game development which include alot of classes and frameworks and libraries talking to each other, with also its fair share of mathematics, made it an absolute requirement and necessity of writing easy(-ier) to understand code and be able to maintain for both myself and other team- and fellow developers further on.

Acquiring this mindset where you never give up, and start thinking alot more -Before you actually start coding is such a valuable lesson and follow the red threads (as said in Sweden) from 0 to finish gains you so much actual knowledge. 
While also using less generated code and less help from it (when doing it = read more of the actual text generated explaining why this is doing and what its good for etc) leverages your personal self further.

# Choice of language
- Right now its Java, since its my main educated language and knowledge base. Further on i see no problem trying to translate it to other languages like etc C# for example. Maybe even C++ its trickier but its doable.
