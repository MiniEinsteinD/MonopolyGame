(Daniah Mohammed st#101145902 + Umniyah Mohammed st#101158792)

This is the second deliverable for milestone #2 of the Monopoly Project. The changes made to previous version will be included in a separte file called "ChangeLogs.txt".

	We started by reading the milestone's description, then we updated the initial 
UML diagram that was created in milestone #1. Based on the UML diagram, the workload was then divided 
between the members of the group.


Description:
------------
Our program is a GUI-based version of Monopoly.
The controllers available include choosing the number of players, displaying the state of the active player, allowing players to 
buy properties, roll, ask for help, and pass turns.
Events such as landing on a property that is already owned by another player, the bankruptcy
of a player, etc. are dispalyed when needed.


Usage:
------
The player will be welcomed and asked to choose the number of players; then, colors are assigned to players.
The controllers available for the player to enter are as follows:
choose the number of players: Diplays the numbers from 2-8 and allow the player to select the number of players.
state: Displays the state of the active player.
roll: Rolls two dice to determine how many steps to move the active player, dipalys the new location, 
	and pays any rent. If you rolled doubles, roll again.
buy: Buys a property for the active player. Does not work if you don't have enough money, or the property 
	is already owned.
pass: Passes the active player's turn to the next solvent player.
The player loses when their total amount is a negative number.


Known issues:
--------------
The code functions properly; therefore, there are no issues with our current iteration.


Roadmap ahead:
--------------
	The next iteration of our project will be a refined GUI-based version of the game displayed in a JFrame; user input is via the mouse. 
Also, unit tests for the Model will be done.
The main focus will be on adding additional features: houses, hotels, 
and special properties and squares such as jail, “Go”, railroad, utility. 
Also, the ability to use any number of “AI” players.

KNOWN ISSUE: THE UML DIAGRAM ORIGINAL FILE WAS DELETED, LAST PUSH WAS WHAT WE SUBMITTED

Authors:
--------
Daniah  Mohammed
Umniyah Mohammed
Ethan Leir
Ethan Houlahan