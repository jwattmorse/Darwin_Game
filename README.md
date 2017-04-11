# Darwin_Game

## Executive Summary:
A game I implemented using a previously made graphics package, World Map. The game is a board of creatures that perform a set of instructions provided by a user. The creatures battle for supremacy by infecting enemy creatures until the board is filled with just one type of creature. Note: structure5 is a java package that holds a variety of data structures, including queues, vectors ect.


## Team Composition and Workload Split:	
Worked with fellow Williams student Emily Hoyt. Shared design and implementation duties. The World Map method was implemented by Williams Professor Duane Baily.

## Outline of Code

### Creature.java
This class holds all the information about a specific creature within the game, including its instruction set, what world the creature is in and where it is on that board. The two constructors, enable the infective step in the game by reinitializing the creature as its infected type.

### Darwin.java
Initializes the game by setting up the game board, creating species and placing creatures on the board. 

### Instruction.java
Represents a singular instruction in java.

### Position.java
Represents a position on the board

### Species.java
Holds all relevant information about a species. It reads in a file and codes the information of moves as integers within a vector.

### World.java
Method hold relevant information about gameplay at a given time. In particular, it holds positon of specific creates in arrays.

### WorldMap.java
Handles the graphical interface.
