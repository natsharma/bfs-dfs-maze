package dfsbfs;
public enum SquareType {
	WALL     ('x'),
	CORRIDOR (' '),
	WAYOUT   ('o'),
	VISITED  ('.'),
	START    ('*'),
	EXIT     ('E');
	private char symbol; 
	SquareType ( char symbol ) {
		this.symbol = symbol;
	}
	public String toString () {
		switch (symbol) {
		//U+2588	█	FULL BLOCK
		//U+2591	░	LIGHT SHADE
		//U+25C9	◉	
		case '*': return "\u25c9";
		case 'x': return "\u2588"; 
		case '.': return "\u2591";
		case ' ': return " ";
		case 'o': return " "; 
		case 'E': return "E";
		}
		return "?";
	}
	public boolean canBeSet() {
		if (this.equals(SquareType.CORRIDOR)) 
			return true;
		else
			return false;
	}
	public boolean isWayOut () {
		if (this.equals(SquareType.WAYOUT))
			return true;
		else 
			return false;
	}
	public boolean isVisited () {
		if (this.equals(SquareType.VISITED))
			return true;
		else 
			return false;
	}
	public boolean isWall () {
		if (this.equals(SquareType.WALL))
			return true;
		else 
			return false;
	}
	public boolean isStart () {
		if (this.equals(SquareType.START))
			return true;
		else 
			return false;
	}
	public static SquareType fromChar (char symbol) {
		if (symbol == 'x') return SquareType.WALL;
		else if (symbol == ' ')  return SquareType.CORRIDOR;
		else if (symbol == 'o')  return SquareType.WAYOUT;
		return null;
	}

}
