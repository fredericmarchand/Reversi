package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import model.Coordinate;
import model.Othello;
import model.Node;

public class Search {
	
	public static final int HEURISTIC_SCORE = 0;
	public static final int HEURISTIC_MOBILITY = 1;
	
	public static Coordinate MiniMax(Node startState, int player, boolean alphaBeta, int heuristic, int depthBound){
			
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
			
			//Is this a terminal node? or max tree depth reached
			if (isTerminalNode(currNode) || currNode.getSteps() >= depthBound) {
				
				//set estimate value based on heuristic (score in this case)
				currNode.setEstimate(getEstimate(currNode, player, heuristic));
				
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
	
	public static boolean isTerminalNode(Node node) {
		return (node.getMap().getPossibleMoves(Othello.PLAYER1).size() == 0  && 
				node.getMap().getPossibleMoves(Othello.PLAYER2).size() == 0);
	}
	
	public static int getEstimate(Node node, int player, int heuristic) {
		if (heuristic == HEURISTIC_SCORE)
			return getScoreHeuristicEstimate(node, player);
		else if (heuristic == HEURISTIC_MOBILITY)
			return getMobilityHeuristicEstimate(node, player);
		return 0;
	}
	
	public static int getMobilityHeuristicEstimate(Node node, int player) {
		int estimate = 0;
		//Corners are the best positions
		if (node.getMove().equals(new Coordinate(0, 0)) ||
			node.getMove().equals(new Coordinate(0, Othello.COLS)) ||
			node.getMove().equals(new Coordinate(Othello.ROWS, 0)) ||
			node.getMove().equals(new Coordinate(Othello.ROWS, Othello.COLS)) ) {
			estimate = 10000;
		}
		//Positions around the corners are horrible 
		else if (node.getMove().equals(new Coordinate(0, 1)) ||
			node.getMove().equals(new Coordinate(1, 0)) ||
			node.getMove().equals(new Coordinate(1, 1)) ||
			node.getMove().equals(new Coordinate(Othello.ROWS-2, Othello.COLS-1)) ||
			node.getMove().equals(new Coordinate(Othello.ROWS-2, Othello.COLS-2)) ||
			node.getMove().equals(new Coordinate(Othello.ROWS-1, Othello.COLS-2)) ||
			node.getMove().equals(new Coordinate(Othello.ROWS-2, 1)) ||
			node.getMove().equals(new Coordinate(Othello.ROWS-2, 0)) ||
			node.getMove().equals(new Coordinate(Othello.ROWS-1, 1)) ||
			node.getMove().equals(new Coordinate(1, Othello.COLS-2)) ||
			node.getMove().equals(new Coordinate(0, Othello.COLS-2)) ||
			node.getMove().equals(new Coordinate(1, Othello.COLS-1))) {
			estimate =  -10000;
		}
		
		if (estimate == 0){
		//Otherwise return the amount of possible moves
			estimate =  node.getMap().getPossibleMoves(node.getPlayer()).size();
		}
		
		//if (player == node.getPlayer())
		return estimate;
		//return -estimate;
	}

	
	public static int getScoreHeuristicEstimate(Node node, int player) {
		int estimate = 0;
		if (player == Othello.PLAYER1)
			estimate = node.getMap().getWhiteCount();
		else if (player == Othello.PLAYER2)
			estimate = node.getMap().getBlackCount();
		return estimate;	
	}
}