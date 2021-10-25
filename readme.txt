
This is the first deliverable (completely new), so there are no changes made to previous versions.

	We started by reading the milestone's description, then we created an initial 
UML diagram. Based on the first version of the UML diagram, the workload was then divided 
between the members of the group.


Description:
------------
Our program is a text-based playable version of Monopoly.
The commands available include displaying the state of the players, allowing players to 
buy properties and pass turns.
Events such as landing on a property that is already owned by another player, the bankruptcy
of a player,ect. are printed to the console when needed.
**add a description of the choice of data structures and relevant operations**
**providing an initial design and implementation for the Model part of the MVC**


Usage:
------
The player will be welcomed and asked to enter a number of players; then, colors are assigned to players.
The commands available for the player to enter are as follows:
state: Prints the state of the active player.
roll: Rolls two dice to determine how many steps to move the active player, prints the new location, 
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
	The next iteration of our project will be a GUI-based version of the game displayed 
in a JFrame; user input is via the mouse. Also, unit tests for the Model will be done.


Authors:
--------
Daniah  Mohammed
Umniyah Mohammed
Ethan Lier
Ethan Houlahan