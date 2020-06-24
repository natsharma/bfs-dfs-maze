package dfsbfs;
import java.util.ArrayList;
import java.util.Random;
public class Labyrinth {
	
	private SquareType [][] maze;
	private int height;
	private int width;
	private Random rand = new Random(); 
	private Location start; 
	public Labyrinth (char [][] charMazeFromFile ) throws IllegalArgumentException {
		
		if (!isValid(charMazeFromFile) )
			throw new IllegalArgumentException("Parameter does not represent a valid maze"); 
		
		int row, column;
		//convert character array to SquareType object array
		SquareType [][] squareMazeFromFile = 
				new SquareType[charMazeFromFile.length][charMazeFromFile[0].length];
		
		for (row = 0; row < squareMazeFromFile.length; row++)
			for (column = 0; column < squareMazeFromFile[0].length; column++) {
				switch (charMazeFromFile[row][column]) {
				case 'x': squareMazeFromFile[row][column] = SquareType.WALL;  break;
				case ' ': squareMazeFromFile[row][column] = SquareType.CORRIDOR;  break;
 				case 'o': squareMazeFromFile[row][column] = SquareType.WAYOUT;  break;
 				default: System.err.printf("Error: Incorrect maze element.%n");
 						 System.exit (1);
				}
			}
				
		this.maze = squareMazeFromFile;
		width = maze[0].length;
		height = maze.length;
	}
	public SquareType getSquareType( Location sp) {
		if (sp.getRow() >=0 && sp.getRow() < height && sp.getColumn() >= 0 && sp.getColumn() < width )
			return maze[sp.getRow()][sp.getColumn()];
		return null;
	}
	public Location generateStartPosition ( ) { 
		//pick coordinates at random 
		int col = rand.nextInt(width);
		int row = rand.nextInt(height); 
		Location sp = new Location(row,col);
		while (!getSquareType(sp).canBeSet()){
			col = rand.nextInt(width);
			row = rand.nextInt(height); 
			sp = new Location(row,col); 
		}
		//store and return the start position selected 
		start = new Location(row, col) ; 
		markSquareToStart(); 
		return sp; 
	}
	public Location setStartPosition (int col, int row ) { 
		Location sp = new Location(row,col);
		//keep trying until valid start position coordinates are found 
		//(we cannot start from the wall, for example) 
		if (!getSquareType(sp).canBeSet()){
			return null; 
		}
		//store and return the start position selected 
		start = new Location(row, col) ; 
		markSquareToStart(); 
		return sp; 
	}
	public void setSquareToVisited ( Location sp ) {
		if ((sp.getRow() >=0 && sp.getRow() < height && sp.getColumn() >= 0 && sp.getColumn() < width )
				&& maze[sp.getRow()][sp.getColumn()].canBeSet() ) 
	
				maze[sp.getRow()][sp.getColumn()] = SquareType.VISITED;
	}
	public void setSquareToExit ( Location sp ) {
		if ((sp.getRow() >=0 && sp.getRow() < height && sp.getColumn() >= 0 && sp.getColumn() < width )
				&& maze[sp.getRow()][sp.getColumn()].isWayOut() )
			maze[sp.getRow()][sp.getColumn()] = SquareType.EXIT;
	}
	private void markSquareToStart ( ) {
		if (start != null )
			maze[start.getRow()][start.getColumn()] = SquareType.START; 
	}
	public ArrayList<Location> getNeighbors ( Location sp ) {
		ArrayList<Location> list = new ArrayList<Location>();
		int row = sp.getRow();
		int column = sp.getColumn(); 
		Location newSquarePostion = null;
		if (row >=0 && row < height && column >= 0 && column < width ) {
			
			if ( row > 0  ) {	//top
				newSquarePostion = new Location (row-1, column);
				if ( ! getSquareType(newSquarePostion).isWall() )
					if (rand.nextBoolean())
						list.add(newSquarePostion);
					else list.add(0,newSquarePostion);
			}
			
			if ( column < width-1  ) {	//right
				newSquarePostion = new Location (row, column+1);
				if ( ! getSquareType(newSquarePostion).isWall() )

					if (rand.nextBoolean())
						list.add(newSquarePostion);
					else list.add(0,newSquarePostion);
			}
			
			if ( row < height-1  ) {	//bottom
				newSquarePostion = new Location (row+1, column);
				if ( ! getSquareType(newSquarePostion).isWall() )
					if (rand.nextBoolean())
						list.add(newSquarePostion);
					else list.add(0,newSquarePostion);
			}

			if ( column > 0  ) {	//left
				newSquarePostion = new Location (row, column-1);
				if ( ! getSquareType(newSquarePostion).isWall() )
					if (rand.nextBoolean())
						list.add(newSquarePostion);
					else list.add(0,newSquarePostion);
			} 
		}
		return list; 
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	@Override
	public String toString() {
		String output = "";
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				output = output + maze[row][column].toString();
			}
			output = output + "\n";
		}
		return output;
	}
	private boolean isValid (char [][] maze ) {
		int row, column;
		
		if (maze.length < 3 || maze[0].length < 3)
			return false;
		int rowLength = maze[0].length;
		for (row = 1; row < maze.length; row++) {
			if (maze[row].length != rowLength )
				return false;
		}
		SquareType s;
		for (row = 0; row < maze.length; row++) {
			for (column = 0; column < rowLength; column++) {
				s = SquareType.fromChar(maze[row][column]);
				if ( s == null ) {
					return false;
				}
				if (s.equals(SquareType.WAYOUT)) {
					if (!( row ==0 || column == 0 || 
							row == maze.length-1 || column == rowLength - 1))
						return false;
				}
			}
		}
		//if we got here, the maze is valid
		return true;
	}

}
