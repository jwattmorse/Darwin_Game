
import structure5.*;

/*
 * Jacob Watt-Morse and Emily Hoyt
 * 5/5/15 
 *
 *
 * This module includes the functions necessary to keep track of the
 * creatures in a two-dimensional world. In order for the design to be
 * general, the interface adopts the following design: <p>
 * 1. The contents have generic type E.  <p>
 * 2. The dimensions of the world array are specified by the client. <p>
 *
 *
 * This class creates the World that will hold all of the positions possible 
 * for the Creatures to be in. Controls what is updated when we update the
 * display using WorldMap as this class allows us to get and set and check 
 * positions to see if they are in range.
 * 
 */

public class World<E> {

    protected int h;  //Holds the height of the matrix/grid
    protected int w;  //Holds the width of the matrix/grid
    protected Matrix<E> world;  //World to hold the positions for the creatures

    /**
     * This function creates a new world consisting of width columns 
     * and height rows, each of which is numbered beginning at 0. 
     * A newly created world contains no objects. 
     */
    public World(int w, int h) {
	this.h = h;
	this.w = w;
	world = new Matrix<E>(h, w);
    }

    /**
     * Returns the height of the world.
     */
    public int height() {
	return h;
    }

    /**
     * Returns the width of the world.
     */
    public int width() {
	return w;
    }

    /**
     * Returns whether pos is in the world or not.
     * @pre  pos is a non-null position.
     * @post returns true if pos is an (x,y) location in 
     *       the bounds of the board.
     */
    boolean inRange(Position pos) {
        return 0 <= pos.getX() && pos.getX() < w && 0<= pos.getY() && pos.getY() < h;

    }

    /**
     * Set a position on the board to contain c.
     * @pre  pos is a non-null position on the board.
     */
    public void set(Position pos, E c) {
	if(this.inRange(pos)){
	    world.set(pos.getX(), pos.getY(), c);
	}
    }

    /**
     * Return the contents of a position on the board.
     * @pre  pos is a non-null position on the board.
     */
    public E get(Position pos) {
	return world.get(pos.getX(), pos.getY());
    }


    public static void main(String s[]) {

	//Test world is a five by five matrix
	World<Integer> test = new World<Integer>(5,5);
	Integer HOPE = 0;

	//Adding values to matrix representation of world
	for ( int x=0; x<5; x++){
	    for (int y=0; y<5; y++){
		Position hi = new Position(x,y);
		test.set(hi,HOPE);
		HOPE++;
	    }
	}

	//Printing out a String representation of the board
	String testprint = "";
		
	for ( int x=0; x<5; x++){
	    for (int y=0; y<5; y++){
		Position hi = new Position(x,y);
		Integer current = test.get(hi);
		testprint = testprint + current;
	    }
	}
	System.out.println( testprint );

	//Testing methods used to manipulate and access world
	System.out.println("" + test.height() + test.width());
	Position outOfRange = new Position(10,15);
	System.out.println(test.inRange(outOfRange));
    }
	
}
