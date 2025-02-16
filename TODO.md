1. on VCS commit 7cd0e30b942b5a894e11d3727261cea44a6e494c

Eventrect works just fine transitioning between maps with current player. Saving and going out of the game and creating a new user/profile breaks the eventrect for both players. No idea why. 
Major Bugsearch on this one. 

- Gamestate not being properly reset when have clicked create new user? - But why break for every other user as well? 

- The profile.dat files that cannot handle multiple saved event states? are the information for the save.dat files becoming too complex?

-



3. Fix offset between menubuttons responsive on window size

















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