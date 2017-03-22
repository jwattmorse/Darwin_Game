
import structure5.*; //Data-Structure Package
import java.util.*;
import java.util.Random;

/* 
 * Emily Hoyt and Jacob Watt-Morse
 *
 * This class holds all the information for an invidual creature.
 * It has two constructors, one which is utilized when a creature
 * is in infected so it starts at the step line that the infector
 * has commaned the infecty to strat at. It also has method that
 * handles a move. It determines what move to do and how to
 * execute it. It also has several methods that simply return
 * information about the creature.
 */ 


public class Creature {

    protected Species spec; //Creature's Species
    protected int direct; //Creature's direction
    protected Position p; //Creture's current position
    protected World<Creature> w; //World creature is playing in
    protected Instruction current; //Instruction creature is carrying out  
    protected String color; //color of creature
    protected char abrev; //Abreviation to go on creature
    protected int step; //step in Instruction vector that the current insturction is held, useful for properly incrementing instructions
    protected Random r; // random number generator to deal with random mehtod
    
    /*
     * Create a creature of the given species, with the indicated
     * position and direction and world.
     */
    public Creature(Species species,
		    World<Creature> world,
		    Position pos,
		    int dir) {
	    
	spec = species;
	direct = dir;
	p = pos;
	w = world;
	
	color = spec.getColor();
	abrev = spec.getName().charAt(0);

	current = spec.programStep(1);
	step = 1;//Start at one because steps are ordered starting at 1
	
	WorldMap.displaySquare(p, abrev, direct, color); //adds creature to display
	w.set(p, this);
	r = new Random();
    }

    //Second Constructor used if creature is infected
    //Added parameter of step so creature starts at instructed step
    //from the creature that infected it
    public Creature(Species species,
		    World<Creature> world,
		    Position pos,
		    int dir, int intr) {
	spec = species;
	direct = dir;
	p = pos;
	w = world;
	
	color = spec.getColor();
	abrev = spec.getName().charAt(0);

	current = spec.programStep(1);
	step = intr; //Start at one because steps are ordered starting at 1
	WorldMap.displaySquare(p, abrev, direct, color); //Start at one because steps are ordered starting at 1
	w.set(p, this); // adds creature to world
	r = new Random ();
    }

    /*
     * Return the species of the creature.
     */
    public Species species() {
	return spec;
    }

    /*
     * Return the current direction of the creature.
     */
    public int direction() {
	return direct;
    }

    /**
     * Return the position of the creature.
     */
    public Position position() {
	return p;
    }

    /*
     * Execute steps from the Creature's program until 
     * a hop, left, right, or infect instruction is executed.
     * Uses a series of if statements that determine which move
     * is being executed and then exectues those commands. For
     * if statements or comands that check a condition without
     * doing anything (if infect is called when not next to any
     * enemy) the mehtod calls takeoneTurn() to call with the next
     * command until hop, left, right or infect is called. 
     */
    public void takeOneTurn() {

	// if the current command is hop
	if(current.getOpcode() == 1){
	    
	    //checks to make sure nothing is in front of creature or that
	    //its at a wall
	    if ( w.inRange(p.getAdjacent(direct)) && w.get(p.getAdjacent(direct)) == null){
		
		w.set(p, null);
		WorldMap.displaySquare(p, ' ', 0, " ");
		p = p.getAdjacent(direct);
	   	w.set(p, this);//changes where the creature is in the world
		WorldMap.displaySquare(p, abrev, direct, color); //changes where the creature is in the display
	    }
	    step = current.getAddress();
	    current = spec.programStep(step);

	    
	} else if(current.getOpcode() == 2){
	    //If the current command is left
	    direct = this.leftFrom(direct);//changes direction
	    step = current.getAddress();
	    current = spec.programStep(step);
	    WorldMap.displaySquare(p, abrev, direct, color);
	    
	} else if(current.getOpcode() == 3){
	    //if the current command is right
	    direct = this.rightFrom(direct);
	    step = current.getAddress();
	    current = spec.programStep(step);
	    WorldMap.displaySquare(p, abrev, direct, color);
	    
	}else if(current.getOpcode() == 4){

	    //if the current code is infect
	    //Checking if the space infront of the creature is an enemy
	    if(w.inRange(p.getAdjacent(direct)) && w.get(p.getAdjacent(direct)) != null && !(w.get(p.getAdjacent(direct)).species().getName()).equals(spec.getName())){
	        
		 Creature toinfect = w.get(p.getAdjacent(direct));
		 Creature toreplace = new Creature( spec, w, toinfect.position(), toinfect.direction(), current.getAddress());

		 w.set(p.getAdjacent(direct), toreplace);
		 Darwin.players.remove(toinfect);
		 Darwin.players.add(toreplace);

		 WorldMap.displaySquare(toinfect.position(), abrev, toinfect.direction(), color);
		 step++; //will atomatically got to next step
		 current = spec.programStep(step);
		 
	     } else{

		//Increments step to next step ensuring that the specific step is in bounds  
		step = step%spec.programSize();
		step = step + 1;
		current = spec.programStep(step);
		takeOneTurn();
	      }
	    
	}else if(current.getOpcode() == 5){
	    //code for ifEmpty
	    //condition to check if the next space is empty
	    if(w.inRange(p.getAdjacent(direct)) &&  w.get(p.getAdjacent(direct)) == null){

		//goes to step associated with if statement
		step = current.getAddress();
		current = spec.programStep(step);
		takeOneTurn(); //calls takeoneturn() to exectute instruction at that step
		
	    }else {
		//increments step responsibly
		step = step%spec.programSize();
		step = step + 1;
		current = spec.programStep(step);
		takeOneTurn();//executes instruction at next step
	    }
	    
	} else if (current.getOpcode() == 6){

	    //code for ifwall
	    //condtion which checks if the creature is at wall
	    if(!w.inRange(p.getAdjacent(direct))){
		
		step = current.getAddress();
		current = spec.programStep(step);
		takeOneTurn();
		  
	    }else {
		step = step%spec.programSize();
		step = step + 1;
		current = spec.programStep(step);
		takeOneTurn();
	    }
	    
	} else if (current.getOpcode() == 7) {
	    //code for ifsame
	    //condition which checks if other adjectent creature is a friend
	    if(w.inRange(p.getAdjacent(direct)) && w.get(p.getAdjacent(direct)) != null && w.get(p.getAdjacent(direct)).species().getName().equals(spec.getName())){
		
		step = current.getAddress();
		current = spec.programStep(step);
		takeOneTurn();
		
	    } else{
		step = step%spec.programSize();
		step = step + 1;
		current = spec.programStep(step);
		takeOneTurn();
	    }
	    
	} else if(current.getOpcode( )== 8) {
	    //code for ifenemy
	    //condition which checks if the adjacent creature is an enemy
	    if(w.inRange(p.getAdjacent(direct)) && w.get(p.getAdjacent(direct)) != null && !(w.get(p.getAdjacent(direct)).species().getName()).equals(spec.getName())){
		
		step = current.getAddress();
		current = spec.programStep(step);
		takeOneTurn();

	    } else{
		step = step%spec.programSize();
		step = step + 1;
		current = spec.programStep(step);
		takeOneTurn();
	      }
	    
	} else if(current.getOpcode() == 9) {
	    //code for if random
	    //creates a random int either 0 or 1 and which one it is

	    int check = r.nextInt(1);
	    if (check == 0){ // if int=0 execute associated command

		step = current.getAddress();
		current = spec.programStep(step);
		takeOneTurn();

	    } else{         //else go to next step 

		step = step%spec.programSize();
		step = step + 1;
		current = spec.programStep(step);
		takeOneTurn();
	    }
	    
	} else if(current.getOpcode() == 10){
	    //Dealing with go
	    //goes to next step
	    step = current.getAddress();
	    current = spec.programStep(step);
	    takeOneTurn();
	}
    }

    /*
     * Return the compass direction the is 90 degrees left of
     * the one passed in.
     */
    public static int leftFrom(int direction) {
	Assert.pre(0 <= direction && direction < 4, "Bad direction");
	return (4 + direction - 1) % 4;
    }

    /*
     * Return the compass direction the is 90 degrees right of
     * the one passed in.
     */
    public static int rightFrom(int direction) {
	Assert.pre(0 <= direction && direction < 4, "Bad direction");
	return (direction + 1) % 4;
    }

    /*
     * Test code
     */
    public static void main(String st[]) {

	// test creature code here.
	Species sp = new Species("Food.txt");
	Species s = new Species("Flytrap.txt");

	World worl = new World<Creature>(5, 5);
	WorldMap.createWorldMap(5,5);
	int dir = 0;
	Position posi = new Position(2, 2);
	Position pos2 = new Position(2, 3);

	Creature c = new Creature(sp, worl, posi, dir);
	Creature d = new Creature(s, worl, pos2, dir);
	

	System.out.println(c.species().programToString());
	System.out.println(d.species().programToString());

	c.takeOneTurn();
	d.takeOneTurn();
	
	System.out.println("" + c.direction());
	System.out.println("" + d.direction());

	System.out.println(c.position().toString());
	System.out.println(d.position().toString());

	
	
    }


}
