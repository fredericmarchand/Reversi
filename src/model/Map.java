package model;

import java.util.Random;

public class Map {
	
	public static final int EMPTY = 0;
	public static final int WHITE = 1;
	public static final int BLACK = 2;
	
	public static final int INVALID_MOVE   = 0;
	public static final int VALID_MOVE     = 1;	
	
	public static final int PLAYER1 = 0;
	public static final int PLAYER2 = 1;
	
	private int rows; 		//amount of squares
	private int columns;  	//amount of squares
	
	private int map[][];
	
	public Map(){
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
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}

	public int validMove(int row, int col)
	{

		return INVALID_MOVE;
	}
	
	public void handleSelection(int row, int col, int player) {
		if (player == PLAYER1)
			map[row][col] = BLACK;
		else if (player == PLAYER2)
			map[row][col] = WHITE;
	}
	
}
