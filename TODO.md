-------------------------------------------------------------------
# on VCS commit a9cec1ab267727342f624a096969aad5487579ff

Events working just fine now for both current and new players, 
    although: drawn eventrectangles and objects from EventHandler stay at same place as first map while transitioning to the other. 
    This only occur if application has not been exited and restarted. After re-enter all is fine.

* Problem isolated to the ResetGame() in SaveLoadResetGame. Somethings off in that method since that is the only method used when:
        
        - Creating new profile -> sets you directly into new game and error occurs
        - Start new game, identical error occurs
        - 
-------------------------------------------------------------------
# Fix offset between menubuttons responsive on window size


# Start, implement, in prioritized order from up and down:

    1. Objects, pickable for player and store in inventory
    2. Interactive Inventory for player
    3. Stats, viewable for player (Visible health on mainscreen)
    Above sort of done ^ -------------------

    4. Player able to attack. Attackanimation.

    5. NPC + interactable
    
    6. Monster/Enemy, attackable


















999. Extra -- . Screen size (for steam: 1920x1080) aspect ratio 16:9. Had to use general scaling factor for entire game (couldnt figure out any other way to do it in order for the game to be viewable and playable on 1920x1080)? Help if anyone else have any other idea? Is scaling the only way to do it for new screens with higher resolution today? 











DONE TODO:s
-------------------------------------------------------------------
on VCS commit - 7cd0e30b942b5a894e11d3727261cea44a6e494c

* Implement Map class, 
    1.1 - start implementing transition between multiple maps

* Refactor gigantic CollisionChecker class into several smaller components based on 
        i. Straight,
        ii. Diagonal, 
        iii. Boundary, 
        iiii. Tiles,

To easier distinguish what component does what and not, uhuh-ehe.. :S *nervous laugh* also for reminder for one-self to remember all of this and be able to explain it eventual further someday 
-------------------------------------------------------------------

* Fix why Events disappear for current player + also no new events work for new players == the initialization+values for the current player was only available for the first created player, not new ones. There was no

      public void setPlayer(Player player) {
        this.player = player;
        if (eventHandler != null) {
            eventHandler.setPlayer(player);
        }
    }

    Get/set for a new player. This caused all initial (at first game start) values for events for new players (as well as old ones) break and no events was anymore available.

-------------------------------------------------------------------
* Accessible Characterstate, stats for player. Visible render of current heart health to screen
