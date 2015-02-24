package model;

import java.util.Random;

public class Othello {
	
	public static final int EMPTY = 0;
	public static final int WHITE = 1;
	public static final int BLACK = 2;
	
	public static final int INVALID_MOVE   = 0;
	public static final int VALID_MOVE     = 1;	
	
	public static final int PLAYER1 = WHITE;
	public static final int PLAYER2 = BLACK;
	
	private static final int UP_LEFT 	= 0;
	private static final int UP 		= 1;
	private static final int UP_RIGHT 	= 2;
	private static final int RIGHT 		= 3;
	private static final int DOWN_RIGHT = 4;
	private static final int DOWN 		= 5;
	private static final int DOWN_LEFT 	= 6;
	private static final int LEFT 		= 7;	
	
	private int rows; 		//amount of squares
	private int columns;  	//amount of squares
	
	private int map[][];
	
	public Othello(){
		rows = 8;
		columns = 8;
		map = new int[rows][columns];
		for (int x = 0; x < rows; ++x) {
			for (int y = 0; y < columns; ++y) {
				map[x][y] = EMPTY;
			}
		}
		map[3][3] = WHITE;
		map[4][4] = WHITE;
		map[4][3] = BLACK;
		map[3][4] = BLACK;
	}
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int[][] getMap() {
		return map;
	}
	
	public int getWhiteCount() {
		int count = 0;
		for (int x = 0; x < rows; ++x) {
			for (int y = 0; y < columns; ++y) {
				if (map[x][y] == Othello.WHITE)
					count++;
			}
		}
		return count;
	}
	
	public int getBlackCount() {
		int count = 0;
		for (int x = 0; x < rows; ++x) {
			for (int y = 0; y < columns; ++y) {
				if (map[x][y] == Othello.BLACK)
					count++;
			}
		}
		return count;
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public boolean surroundedByOpponent(int row, int col, int color) {
		int opponent = 0;
		if (color == BLACK)
			opponent = WHITE;
		else
			opponent = BLACK;
		
		for (int i = row-1; i <= row+1; ++i) {
			for (int j = col-1; j <= col+1; ++j) {
				if (i == row && j == col)
					continue;
				if (i < 0 || j < 0 || i >= 8 || j >= 8)
					continue;
				if (map[i][j] == opponent)
					return true;
			}
		}
		return false;
	}
	
	public boolean findDirection(int row, int col, int color, int direction) {
		int rowInc = 0, colInc = 0;
		
		for (int i = 0; i < 8; ++i) {
			switch (direction) {
			case UP_LEFT: 
				rowInc -= 1;
				colInc -= 1;
				break;
			case UP: 
				rowInc -= 1;
				break;
			case UP_RIGHT:
				rowInc -= 1;
				colInc += 1;
				break;
			case RIGHT: 
				colInc += 1;
				break;
			case DOWN_RIGHT: 
				rowInc += 1;
				colInc += 1;
				break;
			case DOWN: 
				rowInc += 1;
				break;
			case DOWN_LEFT: 
				rowInc += 1;
				colInc -= 1;
				break;
			case LEFT: 
				colInc -= 1;
				break;	
			}
			
			if ((row+rowInc) < 0 || (col+colInc) < 0 || (row+rowInc) >= 8 || (col+colInc) >= 8) {
				//System.out.println(false);
				return false;
			}
			if (map[row + rowInc][col + colInc] == EMPTY) {
				//System.out.println(false);
				return false;
			}
			if (map[row + rowInc][col + colInc] == color && i == 0) {
				//System.out.println(false);
				return false;
			}
			if (map[row + rowInc][col + colInc] == color) {
				//System.out.println(true);
				return true;
			}
		}
		return false;
	}
	
	public void colorBetweenCoordinates(int row1, int col1, int row2, int col2, int color) {
		int rowDiff = row2 - row1;
		int colDiff = col2 - col1;
		int rowInc = 0;
		int colInc = 0;
		
		map[row1][col1] = color;
		while ((row1 + rowInc) != row2 || (col1 + colInc) != col2) {
			if (rowDiff < 0) 
				rowInc -= 1;
			if (rowDiff > 0) 
				rowInc += 1;
			if (colDiff < 0) 
				colInc -= 1;
			if (colDiff > 0) 
				colInc += 1;
	
			map[row1 + rowInc][col1 + colInc] = color;
		}
	}
	
	public void colorSquares(int row, int col, int color, int direction) {
		int rowInc = 0, colInc = 0;
		
		for (int i = 0; i < 8; ++i) {
			switch (direction) {
			case UP_LEFT: 
				rowInc -= 1;
				colInc -= 1;
				break;
			case UP: 
				rowInc -= 1;
				break;
			case UP_RIGHT:
				rowInc -= 1;
				colInc += 1;
				break;
			case RIGHT: 
				colInc += 1;
				break;
			case DOWN_RIGHT: 
				rowInc += 1;
				colInc += 1;
				break;
			case DOWN: 
				rowInc += 1;
				break;
			case DOWN_LEFT: 
				rowInc += 1;
				colInc -= 1;
				break;
			case LEFT: 
				colInc -= 1;
				break;	
			}
			
			if ((row+rowInc) < 0 || (col+colInc) < 0 || (row+rowInc) >= 8 || (col+colInc) >= 8) {
				break;
			}
			if (map[row + rowInc][col + colInc] == EMPTY) {
				break;
			}
			if (map[row + rowInc][col + colInc] == color && i == 0) {
				break;
			}
			if (map[row + rowInc][col + colInc] == color) {
				colorBetweenCoordinates(row, col, row + rowInc, col + colInc, color);
				break;
			}
		}
	}

	public int validMove(int row, int col, int player)
	{
		boolean valid = false;
		
		if (surroundedByOpponent(row, col, player)) {
			valid |= findDirection(row, col, player, UP_LEFT);
			valid |= findDirection(row, col, player, UP);
			valid |= findDirection(row, col, player, UP_RIGHT);
			valid |= findDirection(row, col, player, RIGHT);
			valid |= findDirection(row, col, player, DOWN_RIGHT);
			valid |= findDirection(row, col, player, DOWN);
			valid |= findDirection(row, col, player, DOWN_LEFT);
			valid |= findDirection(row, col, player, LEFT);
		}
		System.out.println(valid);
		if (valid) {
			return VALID_MOVE;
		}
		return INVALID_MOVE;
	}
	
	public boolean handleSelection(int row, int col, int player) {
		boolean validMove = validMove(row, col, player) == VALID_MOVE;
		if (validMove) {
			for (int i = UP_LEFT; i <= LEFT; ++i)
				colorSquares(row, col, player, i);
		}
		return validMove;
	}
	
	public static void main(String args[]) {
		Othello m = new Othello();
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				System.out.print(m.getMap()[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println(m.validMove(3, 2, Othello.BLACK));
	}
	
}
