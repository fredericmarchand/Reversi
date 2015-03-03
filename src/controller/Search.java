package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import model.Coordinate;
import model.Othello;
import model.Node;

public class Search {
	
	public static Coordinate MiniMax(Node startState, int player, boolean alphaBeta, int depthBound){
			
		List<Node> visited = new LinkedList<Node>();
		Stack<Node> fringe = new Stack<Node>();
		
		fringe.add(startState);
		
		while (fringe.size() > 0) {

			Node currNode = fringe.pop();
			
			boolean exists = false;
			for (Node n: visited) {
				if (currNode.equalState(n)) {
					exists = true;
					break;
				}
			}
			
			//If the node state was already visited don't add the sub-states in the fringe
			if (exists == true) {
				continue;
			}
			
			visited.add(currNode);
			
			//Are there possible moves left?
			if ((currNode.getMap().getPossibleMoves(Othello.PLAYER1).size() == 0  && 
				 currNode.getMap().getPossibleMoves(Othello.PLAYER2).size() == 0) || 
				 currNode.getSteps() >= depthBound) {
				
				if (player == Othello.PLAYER1) {
					currNode.setEstimate(currNode.getMap().getWhiteCount());
				}
				else if (player == Othello.PLAYER2) {
					currNode.setEstimate(currNode.getMap().getBlackCount());
				}
				
				while (currNode.getParent() != null) {
					if (currNode.getParent().getLabel() == Node.MAX) {
						if (currNode.getEstimate() > currNode.getParent().getEstimate())
							currNode.getParent().setEstimate(currNode.getEstimate());
					}
					else if (currNode.getParent().getLabel() == Node.MIN) {
						if (currNode.getEstimate() < currNode.getParent().getEstimate())
							currNode.getParent().setEstimate(currNode.getEstimate());
					}
					currNode = currNode.getParent();
				}
				continue;
			}
			
			//Add possible moves to fringe
			for (Coordinate c : currNode.getMap().getPossibleMoves(Othello.switchPlayer(currNode.getPlayer()))) {
				
				Node node = new Node(currNode.getMap(), currNode, Node.alternateLabel(currNode.getLabel()), Othello.switchPlayer(currNode.getPlayer()));			
				node.setSteps(currNode.getSteps() + 1);
				node.getMap().handleSelection(c.getRow(), c.getCol(), Othello.switchPlayer(currNode.getPlayer()));
				node.setMove(c.getRow(), c.getCol());
				
				currNode.addChild(node);
				
				if (node.getLabel() == Node.MAX) {
					node.setEstimate(Integer.MIN_VALUE);
				}
				else if (node.getLabel() == Node.MIN) {
					node.setEstimate(Integer.MAX_VALUE); 
				}
				
				fringe.push(node);
			}
			
		}
		System.out.println("Player " + player + " node count: " + (fringe.size() + visited.size()));
		System.out.println(startState.getMaxChild().toString());
		return startState.getMaxChild();
	}
}