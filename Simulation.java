package dfsbfs;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Simulation {
	public static final int SCREEN_HEIGHT = 80;
	public static void main (String [] args) {
		//make sure that the number of arguments to the program is correct
		if (args.length < 2) {
			System.err.printf("Error: Incorrect usage.%n");
			System.err.printf("\tUSAGE: java MazeRun inputFile method%n");
			System.exit (1);
		}
		
		//verify that the input file exists and can be read
		//then open it for reading
		File inputFile = new File (args[0]);
		if ( !(inputFile.exists() && inputFile.canRead() ) )
		{
			System.err.printf("Error: Cannot read file %s.%n", args[0]);
			System.exit (1);
		}
		Scanner in = null;
		
		try {
			in = new Scanner (inputFile);
		} catch (FileNotFoundException e) {
			System.err.printf("Error: Cannot read file %s.%n", args[0]);
			System.exit (1);
		}
		
		//read the maze representation from the file 
		char [][] charMazeFromFile = getCharMaze( in );		
		in.close();		
		
		//create maze object
		Labyrinth maze = new Labyrinth(charMazeFromFile);	
		
		//start the animation by clearing the screen and printing the maze
		clearScreen();
		System.out.println(maze);
		
		//use the algorithm indicated by the second command line argument 
		if (args[1].startsWith("stack")) {
			//run the search for way out algorithm using stack
			searchForWayOut(maze, new PossibleLocationsStack() );
		}
		else if (args[1].startsWith("queue") ) {
			//run the search for way out algorithm using queue
			searchForWayOut(maze, new PossibleLocationsQueue() );
		}
		else {
			System.err.printf("Error: Incorrect usage.%n");
			System.err.printf("\tUSAGE: java MazeRun inputFile method%n");
			System.err.printf("\n\n   Valid methods are 'queue' and 'stack'.\n");
			System.exit (1);
		}
	}
	public static char [][]  getCharMaze ( Scanner in ) {
		StringBuffer mazeFromFile = new StringBuffer();
		//read the file content
		int rowsCount = 0;
		while ( in.hasNext() ) {
			mazeFromFile.append( in.nextLine() + "\n" );
			rowsCount++;
		}
		//split into an array of rows 
		String [] mazeFromFileMatrix = mazeFromFile.toString().split("\n"); 
		//convert into a 2D array of characters 
		char [][] charMazeFromFile = new char[rowsCount] [] ;
		for (int i = 0; i < rowsCount; i++) {
			charMazeFromFile[i] = mazeFromFileMatrix[i].toCharArray();
		}
		
		return charMazeFromFile;
	}
	public static void searchForWayOut (Labyrinth maze, 
			PossibleLocations listOfSquares)  {
		if (listOfSquares == null || maze == null )
			throw new NullPointerException(); 
		Location start = maze.generateStartPosition();
		Location current = null;
		ArrayList<Location> neighbors = null;
		boolean foundWayOut = false;
		listOfSquares.add( start );

		while (!listOfSquares.isEmpty()) {
			current = listOfSquares.remove(); 
			if ( current == null ) throw new LabyrinthSearchException ("removed element null"); 
			if (maze.getSquareType(current).isVisited() )
				continue;
			if (maze.getSquareType(current).isWayOut() ) {
				foundWayOut = true;
				break;
			}
			neighbors = maze.getNeighbors(current);
			for (int i = 0; i < neighbors.size(); i++) {
				listOfSquares.add(neighbors.get(i));
			}
			maze.setSquareToVisited(current);
						
			clearScreen();
			System.out.println(maze);	
			//after every step wait 300 milliseconds 
			try{Thread.sleep(300);}
			catch(InterruptedException e){}
		}
		if (foundWayOut) {
			maze.setSquareToExit(current);
			clearScreen();
			System.out.println(maze);
			System.out.println("You found the way out!");
		}
		else {
			System.out.println("There is no way out of here!");
		}
	}
	public static void clearScreen( ) {
		for (int i = 0; i < SCREEN_HEIGHT; i++ )		{
			System.out.println(" ");
		}
	}
	
}
