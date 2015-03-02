package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import model.Coordinate;
import model.Othello;
import model.Node;

public class Search {
	
	public static LinkedList<Coordinate> MiniMax(Node startState, Othello map, int player, boolean alphaBeta){
			
		List<Node> visited = new LinkedList<Node>();
		Stack<Node> fringe = new Stack<Node>();
		
		fringe.add(startState);
				
		for (;;) {
			
			if (fringe.size() == 0) {
				System.out.println("Empty fringe: Stuck\nExiting in 3 seconds...");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.exit(1);
			}
			
			Node currNode = fringe.pop();
									
			boolean exists = false;
			for (Node n: visited) {
				if (currNode.equalState(n)) {
					exists = true;
				}
			}
			
			//If the node state was already visited don't add the sub-states in the fringe
			if (exists == true) {
				continue;
			}
			
			//Are there possible moves left?
			if (currNode.getMap().getPossibleMoves(Othello.PLAYER1).size() == 0 && 
				currNode.getMap().getPossibleMoves(Othello.PLAYER2).size() == 0) {
				LinkedList<Coordinate> list = Node.getPath(currNode);
				return list;
			}
			
			//Add possible moves to fringe
			int value = 0;
			
			for (Coordinate c : currNode.getMap().getPossibleMoves(player)) {
				Node node = new Node(c.getRow(), c.getCol(), map, currNode, Node.alternateLabel(currNode.getLabel()));
				node.getMap().handleSelection(node.getRow(), node.getCol(), player);
								
				//score heuristic
				if (player == Othello.PLAYER1) {
					value = node.getMap().getWhiteCount();
				}
				else {
					value = node.getMap().getBlackCount();
				}
				currNode.setEstimate(estimate);				
				
				node.setSteps(currNode.getSteps() + 1);
				fringe.push(node);
			}
			visited.add(currNode);
		}		
	}
}