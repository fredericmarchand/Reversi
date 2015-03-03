package controller;

import model.Coordinate;
import model.Othello;
import model.Node;
import view.GameBoard;

public class App {
	
	public static void main(String args[]) throws InterruptedException {
		Othello m = new Othello();
		GameBoard gb = new GameBoard("Snake Game", m);
		gb.setVisible(true);
		gb.update(m);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (;;) {
			Coordinate move;
			if (m.getPossibleMoves(Othello.PLAYER1).size() != 0) {
				move = Search.MiniMax(new Node(m, null, Node.MAX, Othello.PLAYER2), Othello.PLAYER1, false, 2);
				m.handleSelection(move.getRow(), move.getCol(), Othello.PLAYER1);
				//m.printBoard();
				gb.update(m);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (m.getPossibleMoves(Othello.PLAYER2).size() != 0) {
				move = Search.MiniMax(new Node(m, null, Node.MAX, Othello.PLAYER1), Othello.PLAYER2, false, 1);
				m.handleSelection(move.getRow(), move.getCol(), Othello.PLAYER2);
				//m.printBoard();
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
