* Reset/Start new game issue when initializing a new game from saveloadresetgame.java fixed. 
Issue was improper initialization of all game objects/variables/parametres again. The events and objects werent properly reinitialized, like when pressing "resume" which calls the loadgame method.

Now the resetgame method is longer with more comments but it is alot more understandable.

------------------------------------------