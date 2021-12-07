(Daniah Mohammed - 101145902)

This is milestone #4 of the Monopoly Project. The changes made to previous version will be included in a separate file called "ChangeLogs.txt".

	We started by reading the milestone's description, then we updated the 
UML diagram that was created in milestone #3. Based on the UML diagram, the workload was then divided 
between the members of the group.

Description:
------------
Our program is a GUI-based version of Monopoly. Save/load features were added to this iteration using Java Serialization. 
In addition, an international versions with custom street names, values and currencies were also added to the final iteration of the project.
The controllers available include choosing the number of players, choosing the number of bot players,
displaying the state of the active player, allowing players to buy properties, roll, ask for help, and pass turns.
Another functionality that was added to this iteration is building houses when specific conditions are met. 
Events such as landing on a property that is already owned by another player, the bankruptcy
of a player, etc. are displayed when required.

Usage:
------
The player will be welcomed and asked to choose the number of players and the number of bot players; then, colors are assigned to players.
The controllers available for the player to enter are as follows:
choose the number of players: Displays the numbers from 2-8 and allow the player to select the number of players.
roll Dice: Rolls two dice to determine how many steps to move the active player, displays the new location,
	and pays any rent. If you rolled doubles, roll again.
Buy Current Property: Buys a property for the active player. Does not work if you don't have enough money, or the property 
	is already owned.
View Player Portfolio: Displays the state of the active player.
End Turn: Passes the active player's turn to the next solvent player.
View Available Tile To Build: Displays the tiles that the player may build on.
Help: Provides the player with a guide to understand the controllers provided.
The player loses when their total amount is a negative number.

Known issues:
--------------
	- VersionFormat type not stored in XML file (required some extra code for implementation in frame)
	- When player bot in jail: does not display actions of bot to user

Roadmap ahead:
--------------
	This iteration is the final iteration of our project. No further updates will be added. 

Authors:
--------
Daniah Mohammed
Umniyah Mohammed
Ethan Leir
Ethan Houlahan