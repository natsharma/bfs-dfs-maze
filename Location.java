package dfsbfs;
public class Location {
	//row and column locations
	private int row;
	private int column;
	public Location ( int row, int column) {
		this.row = row;
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
	
}
