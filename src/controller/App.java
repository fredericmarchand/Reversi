package controller;

import java.util.LinkedList;

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
		
		//for (;;) {
			//LinkedList<Coordinate> path = null;
			
			
			//If invalid search was selected, return
			//if (path == null)
			//	return;
			
			/*for (Coordinate c: path) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//perform move
				gb.update(m);
			}*/
		//}
	}
}
