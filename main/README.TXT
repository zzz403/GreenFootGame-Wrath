------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all they need to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE:WRATH

Game Premise: As a warrior who played a crucial role in the expansion of the kingdom and held steadfast in your Christian beliefs, 
you were promised a god-like existence in the afterlife, where you would continue to protect the land. However, the deceitful emperor
 duped you into signing a contract that doomed you to an eternity of being reincarnated as malevolent spirits, unable to ascend to 
the next life. But now you have a chance to reclaim your rightful place in heaven, by rising from the depths of hell and vanquishing
 the emperor who betrayed you.

How To Play：With the basic movement, normal attack(j) and three skills plus a ult, on top of this by attack (j) + jump (space) 
and jump (space)+ attack (j) can trigger the rising dragon chop and falling attack. Among them, the three skills are (u) knock back
 and damage nearby enemies, (i) knock invincible and displaced for a distance, and (h) freeze the enemies within a distance. 
In addition, the character also has two magic weapons, one (1) can restore life value, and the other (2) can summon a fire-attracting 
dummy, but only after breaking Satan to obtain.

URLs of tutorials: for save record: https://www.geeksforgeeks.org/java-program-to-save-a-string-to-a-file/
		   for enimy face to player: https://stackoverflow.com/questions/23692077/rotate-object-to-face-point‘

Tier Write Up - 
Tier 2:
Shooting in one or more directions
Players can use "h" to release a weapon that freezes enemies and can be released from any position.
Keep track of items hit with “bullet” type objects and display on the screen
When the protagonist hits the enemy with any skill, the enemy's blood bar will be reduced (the boss's blood bar is at the top, and the monster's blood bar is above the monster)
Gravity OR Jumping
The game has a continuous downward acceleration and has two jumps
Scrolling Backgrounds
The background of the game is continuously moving, and according to the different positions of the player can choose whether the character moves or the background moves
Explosions
When a character in the game uses a skill, it will cause a large period of damage.
Power ups and/or bullet reloads and/or drop off points for collected items, etc.
The following rewards can be obtained for each kill of a small monster.
1. Gold EXP ball that restores the ult progress bar
2.Blue EXP ball to restore the progress of magic treasure
3. Probability to get hp recovery peach
4. By killing the boss can get a different magic treasure
Tier 3 
Constructors that take parameters (squirrel with an acorn/without an acorn)
Different damage display
Monsters of the same type will have different constructors
Using and making accessors and mutators
A large number of such techniques are used in this game, such as the connection between blood and blood bars, the interaction between skills, and the interaction between scenes and attacks
Animations using arrays
A lot of animation using arrays, almost every appearance of the character and skills are animated
Using getObjectsInRange
Used to determine character drops and correct character attack range

Improvements compared to the first version.
1. All the underlying code such as character and monster movement was redesigned because of the consideration of the map's moveability.
2. More hit tips: added death animations to monsters and added damage display, using a recursive algorithm when displaying damage.
3. redesigned the code of bosses and monsters, so that they have fewer subclasses under the parent class.
4. redesigned the hit decision, making the game experience more comfortable, now the game does not happen to look like the boss did not hit themselves but actually hit the situation. This method uses getObjectsInRange
5. New monsters, through the random algorithm added in the sky back and forth the airship, and will randomly drop knights
6. Added a record system, all player records will be recorded in Mark.txt
7. Added a lot of abnormal judgment in the player's fall code, so that the player's landing point in the same place every time.
8. Fixed all known bugs in the first version, such as the game will randomly crash because of the animation array, and for example, the character's attack plus jump trigger failure bugs

