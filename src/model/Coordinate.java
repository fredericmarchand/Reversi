package model;

public class Coordinate {

	private int row;
	private int col;
	
	public Coordinate(int row, int col){
		this.setRow(row);
		this.setCol(col);
	}

	public Coordinate(Coordinate coordinate) {
		coordinate.row = row;
		coordinate.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public String toString() {
		return "(" + getRow() + ", " + getCol() + ")";
	}
	
}
