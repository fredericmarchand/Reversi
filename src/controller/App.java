package controller;

import model.Coordinate;
import model.Othello;
import model.Node;
import view.GameBoard;

public class App {
	
	public static final int PLAYER_VS_AI 	= 0;
	public static final int AI_VS_AI 		= 1;
	
	public static int gameType;
	public static int playerTurn;
	
	public static void main(String args[]) throws InterruptedException {
		
		gameType = AI_VS_AI;
		
		playerTurn = Othello.PLAYER1;
		
		Othello m = new Othello();
		GameBoard gb = new GameBoard("Othello", m);
		gb.setVisible(true);
		gb.update(m);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (;;) {
			Coordinate move;
			if (gameType == AI_VS_AI) {
				if (m.getPossibleMoves(Othello.PLAYER1).size() != 0) {
					move = Search.MiniMax(new Node(m, null, Node.MAX, Othello.PLAYER2), Othello.PLAYER1, true, Search.HEURISTIC_MOBILITY, 5);
					m.handleSelection(move.getRow(), move.getCol(), Othello.PLAYER1);
					gb.update(m);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
			if (gameType == PLAYER_VS_AI && playerTurn == Othello.PLAYER2) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (m.getPossibleMoves(Othello.PLAYER2).size() != 0) {
					move = Search.MiniMax(new Node(m, null, Node.MAX, Othello.PLAYER1), Othello.PLAYER2, true, Search.HEURISTIC_SCORE, 2);
					m.handleSelection(move.getRow(), move.getCol(), Othello.PLAYER2);
					gb.update(m);
				}
				playerTurn = Othello.PLAYER1;
			}
	
			if (gameType == AI_VS_AI) {
				if (m.getPossibleMoves(Othello.PLAYER2).size() != 0) {
					move = Search.MiniMax(new Node(m, null, Node.MAX, Othello.PLAYER1), Othello.PLAYER2, true, Search.HEURISTIC_MOBILITY, 1);
					m.handleSelection(move.getRow(), move.getCol(), Othello.PLAYER2);
					gb.update(m);
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
