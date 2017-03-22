
import structure5.*;
import java.io.*;
import java.util.Scanner;

/*
 * Jacob Watt-Morse and Emily Hoyt
 * 5/5/15 
 *
 * The individual creatures in the world are all representatives of
 * some species class and share certain common characteristics, such
 * as the species name and the program they execute.  Rather than copy
 * this information into each creature, this data can be recorded once
 * as part of the description for a species and then each creature can
 * simply include the appropriate species pointer as part of its
 * internal data structure.
 * <p>
 * 
 * To encapsulate all of the operations operating on a species within
 * this abstraction, this class provides a constructor that will read a file
 * containing the name of the creature and its program. To make the folder
 * structure more manageable, the species files for each creature are
 * stored in a subfolder named Creatures.  This, creating the Species
 * for the file Hop.txt will causes the program to read in
 * "Creatures/Hop.txt".
 * 
 *
 * Note: The instruction addresses start at one, not zero.
 *
 * The Species class creates a vector of associations of integers with integers that stores the various Strings
 * in a creature file by translating the Strings into opcodes and addresses that can be used to 
 * make the instruction that the creature of a certain species will follow when taking a turn. The vector will allow
 * the creature of a certain species to keep track of the order of instructions given in a certain program.
 *
 *
 */
public class Species {

    protected String name;   //Holds the name of the species
    protected String color;   //Holds the color of the species when it appears in the grid

    //Holds the opcodes and addresses of Instructions read through a file passed to the constructor
    protected Vector<Association<Integer, Integer>> instruct; 
    
    /**
     * Create a species for the given file. Reads in all the String representations of instructions
     * in the creature file and store them in a vector that can be accessed by the creature when 
     * the creature is taking a turn. The integers that are stored for each instruction represent
     * the opcode and addresses.
     * @pre fileName exists in the Creature subdirectory.
     */
    public Species(String fileName)  {
	instruct = new Vector<Association<Integer, Integer>> ();

	//Next instruction to implement
	Integer addressToGo;
	Scanner input = 
	    new Scanner(new FileStream("Creatures" + 
				       java.io.File.separator + 
				       fileName));
	name  = input.nextLine();
	color = input.nextLine();

	String cur = input.nextLine();
	//Puts instructions in a vector by storing opcodes and addresses for all different possible commands
	while(input.hasNextLine()  && ! cur.equals(".")){
	    if( cur.contains("hop")){
		//the addresses for commands that do not expect an address to jump to given in the
		//creature file just have an address that goes to the next instruction
		instruct.add(new Association<Integer, Integer> ( 1, instruct.size() + 2));
		cur = input.nextLine();
	    } else if( cur.contains("left")){
		instruct.add(new Association<Integer, Integer> ( 2, instruct.size() + 2));
		cur = input.nextLine();
	    } else if(cur.contains("right")){
		instruct.add(new Association<Integer, Integer> ( 3, instruct.size() + 2));
		cur = input.nextLine();
	    } else if(cur.contains("infect")){
		//If command can have an address to jump to after it, addressToGo is set to that address
		if (cur.indexOf(" ") != -1){
		    addressToGo = Integer.parseInt(cur.substring(cur.indexOf(" ") + 1));
		    instruct.add(new Association<Integer, Integer> ( 4, addressToGo));
		} else {
		    instruct.add(new Association<Integer, Integer> (4, 1));
		}
		cur = input.nextLine();
	    } else if(cur.contains("ifempty")){
		addressToGo = Integer.parseInt(cur.substring(cur.indexOf(" ") + 1));		    
		instruct.add(new Association<Integer, Integer> ( 5, addressToGo));
		cur = input.nextLine();
	    } else if(cur.contains("ifwall")){
		addressToGo = Integer.parseInt(cur.substring(cur.indexOf(" ") + 1));		    
		instruct.add(new Association<Integer, Integer> ( 6, addressToGo));
		cur = input.nextLine();
	    } else if(cur.contains("ifsame")){
		addressToGo = Integer.parseInt(cur.substring(cur.indexOf(" ") + 1));		    
		instruct.add(new Association<Integer, Integer> ( 7, addressToGo));
		cur = input.nextLine();
	    } else if(cur.contains("ifenemy")){
		addressToGo = Integer.parseInt(cur.substring(cur.indexOf(" ") + 1));		    
		instruct.add(new Association<Integer, Integer> ( 8, addressToGo));
		cur = input.nextLine();
	    } else if(cur.contains("ifrandom")){
		addressToGo = Integer.parseInt(cur.substring(cur.indexOf(" ") + 1));		    
		instruct.add(new Association<Integer, Integer> ( 9, addressToGo));
		cur = input.nextLine();
	    } else if(cur.contains("go")){
		addressToGo = Integer.parseInt(cur.substring(cur.indexOf(" ") + 1));		    
		instruct.add(new Association<Integer, Integer> ( 10, addressToGo));
		cur = input.nextLine();
	    } else {
		System.out.println("Invalid Instruction");
	    }
	}

    }


    /**
     * Return the name of the species.
     */
    public String getName() {
	return name;
    }

    /**
     * Return the color of the species.
     */
    public String getColor() {
	return color;
    }

    /**
     * Return the number of instructions in the program.
     */
    public int programSize() {
	return instruct.size();
    }


    /**
     * Return an instruction from the program by accessing the opcode and address from the vector
     * used to store instructions from the creature file
     * @pre  1 <= i <= programSize().
     * @post returns instruction i of the program.
     */
    public Instruction programStep(int i) {
	Assert.condition( i >= 1 && i <= programSize(), "Out of Range of Program Size");
	
	Association<Integer, Integer> step = instruct.get(i - 1);
	Integer op = step.getKey();
	Integer address = step.getValue();

	return new Instruction(op, address);

    }

    /**
     * Return a String representation of the program.
     */
    public String programToString() {
	String s = "";
	for (int i = 1; i <= programSize(); i++) {
	    s = s + (i) + ": " + programStep(i) + "\n";
	}
	return s;
    }

    
    public static void main(String s[]) {

	//Tests codes for different creature files being read in
	Species sp = new Species("Hop.txt");
	System.out.println(sp.getName());
	System.out.println("first step should be hop: " + sp.programStep(1)); 

	/* test all other operations here */
	System.out.println(sp.getColor());
	System.out.println(sp.programToString());
	System.out.println("" + sp.programSize());
	
    }

}
   
