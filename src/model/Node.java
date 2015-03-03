package model;

import java.util.ArrayList;

public class Node {

	public static final int MAX = 0;
	public static final int MIN = 1;
	
	//State information
	private Node parent;
	private Othello map;
	private Coordinate move;
	private int player;
	private int estimate;
	private int steps;
	private int label; //max or min
	private ArrayList<Node> children;
	
	public Node(Othello map, Node previous, int label, int player) {
		this.map = new Othello(map);
		steps = 0;
		estimate = 0;
		this.player = player;
		this.parent = previous;
		this.label = label;
		children = new ArrayList<Node>();
		move = new Coordinate(0, 0);
	}
	
	public Node(Othello map, int estimate, int steps, int label, int player, Node previous) {
		this.map = new Othello(map);
		this.setSteps(steps);
		this.setEstimate(estimate);
		this.parent = previous;
		this.label = previous.label;
		this.player = player;
		children = new ArrayList<Node>();
		move = new Coordinate(0, 0);
	}
	
	public Coordinate getMove() {
		return move;
	}
	
	public void setMove(int row, int col) {
		move.setRow(row);
		move.setCol(col);
	}
	
	public Othello getMap() {
		return map;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public boolean equalState(Node node) {
		boolean status = true;
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if (node.getMap().getMap()[i][j] != map.getMap()[i][j])
					status = false;
			}
		}
		return status;
	}
	
	/*public static LinkedList<Coordinate> getPath(Node node) {
		LinkedList<Coordinate> path = new LinkedList<Coordinate>();
		Node currNode = node;
		while (currNode.getParent() != null) {
			path.add(new Coordinate(currNode.getRow(), currNode.getCol()));
			currNode = currNode.getParent();
		}
		
		Collections.reverse(path);
		return path;
	}*/

	public int getSteps() {
		return steps;
	}

	public int getPlayer() {
		return player;
	}
	
	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getEstimate() {
		return estimate;
	}

	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}
	
	public void addChild(Node child) {
		children.add(child);
	}
	
	public ArrayList<Node> getChildren() {
		return children;
	}
	
	public int getLabel() {
		return label;
	}
	
	public int getMinEstimateFromChildren() {
		int min = children.get(0).getEstimate();
		for (Node n: children) {
			if (n.getEstimate() < min) {
				min = n.getEstimate();
			}
		}
		return min;
	}
	
	public Coordinate getMaxChild() {
		Node maxNode = children.get(0);
		for (Node n: children) {
			if (n.getEstimate() > maxNode.getEstimate()) {
				maxNode = n;
			}
		}
		return maxNode.getMove();
	}
	
	public int getMaxEstimateFromChildren() {
		int max = children.get(0).getEstimate();
		for (Node n: children) {
			if (n.getEstimate() > max) {
				max = n.getEstimate();
			}
		}
		return max;
	}
	
	public static int alternateLabel(int label) {
		if (label == MAX) {
			return MIN;
		}
		else return MAX;
	}
	
}
