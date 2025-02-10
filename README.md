New try of a cleaner, good code 2D RPG game in Java, using Java Swing.

Use of @Lombok right now - In order to keep better overviewable logic of code, and understand whats happening and easier debug and implementation. All for scalability.

Features right now include:

## Player
- Straight collision and movement for player against vertical/horizontal and diagonal objects. Next step to try and implement more better and solid collision handling of vectors instead, since other collision method is very tricky to optimize. Lots of un-understandable, interfering and not very logic code.
- Sprite update, decided on how many number of sprites you want really
- Acceleration/Deceleration of your player
- Cameralogic moving the map under the player, while also "clamp" values near border of map, ensuring the map stops moving when player near border. This applies responsive for any window size now choose.
- Tiles, straight ordinary collision here as well. The vector collision will try to implement here as well.

## Common
- Sprite and Tile logic read and based from complete Tile- and SpriteSheets, optimizing memory performance
- Basic resolution implemented with scalefactor right now (Scalefactor can be altered, depending on your graphic preferences/view) best is no scaling at all i heard, but harder view on modern screens if not.

## Map
- Map read from .txt-file with arrayvalues from tilesheet recalculated based on tilesize, meaning you could set individual tilesizes for your game by only a few clicks.

## States
- InitialState (Hero): when first starting game, that lets you create or choose profile
- MainMenuState: The individual main menu for your character, that lets you choose between 
        # switch user (back to initialstate)
        # resume old game (save automatically on quit), 
        # start new game
        # settings
        # exit game
- SettingsState: Lets you choose between 
        # 3 current modern window sizes
        # Graphic mode (small or SNES)
        # Fullscreen on or off
- PauseState: When pressing esc in-game, freezes current gamestate picture semi-transparent and displays a menu center screen with basic alternatives

## Save/Load/Reset
- Basic Save and Load implemented to save to a local 'profiles' folder where all current profile information is saved. Easier for making backups, and transferring between devices.





# Why i wanted to do this?
- I wanted to dive deeper into clean code practice, main mindset "Why am i writing this?" and why did i do it this way before? Writing and implementing something sometimes very complicated and snarky as game development which include alot of classes and frameworks and libraries talking to each other, with also its fair share of mathematics, made it an absolute requirement and necessity of writing easy(-ier) to understand code and be able to maintain for both myself and other team- and fellow developers further on.

Acquiring this mindset where you never give up, and start thinking alot more -Before you actually start coding is such a valuable lesson and follow the red threads (as said in Sweden) from 0 to finish gains you so much actual knowledge. 
While also using less generated code and less help from it (when doing it = read more of the actual text generated explaining why this is doing and what its good for etc) leverages your personal self further.

# Choice of language
- Right now its Java, since its my main educated language and knowledge base. Further on i see no problem trying to translate it to other languages like etc C# for example. Maybe even C++ its trickier but its doable.
