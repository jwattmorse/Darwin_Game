
import structure5.*;
import java.util.Random;
import java.io.*;

/*
 * Emily Hoyt and Jacob Watt-Morse
 *
 * This is our main class which sets up the came by creating the creatures
 * of the given speices, placing them on the board, creating the baord visual
 * and runing the simulation loop, which has each creature take a turn.
 */




class Darwin {

    protected static Vector<Creature> players; //Vector that holds every creature
    protected static World<Creature> field; //World that we play in
     	
    /*
     * The array passed into main will include the arguments that
     * appeared on the command line.  For example, running "java
     * Darwin Hop.txt Rover.txt" will call the main method with s
     * being an array of two strings: "Hop.txt" and "Rover.txt".
     */
    public static void main(String s[]) {
	if (s.length == 0) {
	    System.out.println("Please Select a Character to Play With!");
	} else {
	    WorldMap.createWorldMap(15,15); //creates world map
	    players = new Vector<Creature> ();
	    field = new World<Creature> (15, 15);

	    Random dir = new Random();  // will select random int for direction
	    Random posx = new Random(); // will select position in x direction
	    Random posy = new Random(); // will select position in y direction 

	    Position possible; //variable to check if position is filled in map

	    //loop goes through and creates 10 verisions of each character
	    for ( int x=0; x<s.length; x++){
		
		Species thisguy = new Species(s[x]);

		for ( int y=0; y<10; y++){
		    possible = new Position(posx.nextInt(10), posy.nextInt(10));

		    while (field.get(possible) != null){ //loop works until finds an empty location
			 possible = new Position(posx.nextInt(10), posy.nextInt(10));
		    }

		    //adds players to vector
		    players.add(new Creature(thisguy, field, possible, dir.nextInt(4)));
		}
	    }

	    simulate();  
	}
       
    }


    public static void simulate() {

	while (true){
	    //loop goes through and makes each player take a turn
	    for(int y=0; y<players.size(); y++){
		players.get(y).takeOneTurn();
	    }
	    //Long pause 
	    WorldMap.pause(500);
	}
    }

}
