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
			
			if (alphaBeta && currNode.getParent() != null) {
				currNode.setAlpha(currNode.getParent().getAlpha());
				currNode.setBeta(currNode.getParent().getBeta());
			}
			
			//Is this a terminal node?
			if ((currNode.getMap().getPossibleMoves(Othello.PLAYER1).size() == 0  && 
				 currNode.getMap().getPossibleMoves(Othello.PLAYER2).size() == 0) || 
				 currNode.getSteps() >= depthBound) {
				
				//set estimate value based on heuristic (score in this case)
				if (player == Othello.PLAYER1) {
					currNode.setEstimate(currNode.getMap().getWhiteCount());
				}
				else if (player == Othello.PLAYER2) {
					currNode.setEstimate(currNode.getMap().getBlackCount());
				}
				
				while (true) {
					
					//Update alpha/beta value
					if (alphaBeta) {
						if (currNode.getLabel() == Node.MAX) {
							if (currNode.getEstimate() > currNode.getAlpha()) {
								currNode.setAlpha(currNode.getEstimate());
							}
						}
						else if (currNode.getLabel() == Node.MIN) {
							if (currNode.getEstimate() < currNode.getBeta()) {
								currNode.setBeta(currNode.getEstimate());
							}
						}
					}
					
					//If this is the root of the tree exit
					if (currNode.getParent() == null)
						break;
					
					//Set the estimate of the current nodes parent
					if (currNode.getParent().getLabel() == Node.MAX) {
						if (currNode.getEstimate() >= currNode.getParent().getEstimate()) {
							currNode.getParent().setEstimate(currNode.getEstimate());
						}
					}
					else if (currNode.getParent().getLabel() == Node.MIN) {
						if (currNode.getEstimate() <= currNode.getParent().getEstimate()) {
							currNode.getParent().setEstimate(currNode.getEstimate());
						}
					}
					
					if (alphaBeta) {
						Node parent = currNode.getParent();
						if (parent.getLabel() == Node.MAX) {
							if (parent.getEstimate() > parent.getAlpha()) {
								parent.setAlpha(parent.getEstimate());
							}
						}
						else if (parent.getLabel() == Node.MIN) {
							if (parent.getEstimate() < parent.getBeta()) {
								parent.setBeta(parent.getEstimate());
							}
						}
						
						//beta cut
						if (parent.getLabel() == Node.MAX) {
							if (parent.getEstimate() >= parent.getBeta()) {
								for (Node n: parent.getChildren()) {
									fringe.remove(n);
								}
							}
						}
						//alpha cut
						else if (parent.getLabel() == Node.MIN) {
							if (parent.getEstimate() <= parent.getAlpha()) {
								for (Node n: parent.getChildren()) {
									fringe.remove(n);
								}
							}
						}
					}
					
					//If there are siblings remaining exit
					if (fringe.size() > 0 && currNode.getParent().getChildren().contains(fringe.peek())) {		
						break;
					}
					//Otherwise continue up the tree
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
				if (alphaBeta) {
					node.setAlpha(currNode.getAlpha());
					node.setBeta(currNode.getBeta());
				}
				
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
		System.out.println("Coordinate: " + startState.getMaxChild().toString());
		System.out.println("Estimate: " + startState.getEstimate());
		return startState.getMaxChild();
	}
}