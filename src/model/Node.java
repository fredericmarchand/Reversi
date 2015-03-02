package model;

import java.util.Collections;
import java.util.LinkedList;

public class Node {

	private static final int MAX = 0;
	private static final int MIN = 1;
	
	//State information
	private Node previous;
	private int row;
	private int col;
	private Othello map;
	private int estimate;
	private int steps;
	private int label; //max or min
	
	public Node(int row, int col, Othello map, Node previous, int label) {
		this.setRow(row);
		this.setCol(col);
		this.map = new Othello(map);
		steps = 0;
		estimate = 0;
		this.previous = previous;
		this.label = label;
	}
	
	public Node(int row, int col, Othello map, int estimate, int steps, int label, Node previous) {
		this.setRow(row);
		this.setCol(col);
		this.map = new Othello(map);
		this.setSteps(steps);
		this.setEstimate(estimate);
		this.previous = previous;
		this.label = previous.label;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	public Othello getMap() {
		return map;
	}
	
	public boolean equalState(Node node) {
		if (node.row == this.row && node.col == this.col)
			return true;
		return false;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}
	
	public static LinkedList<Coordinate> getPath(Node node) {
		LinkedList<Coordinate> path = new LinkedList<Coordinate>();
		Node currNode = node;
		while (currNode.getPrevious() != null) {
			path.add(new Coordinate(currNode.getRow(), currNode.getCol()));
			currNode = currNode.getPrevious();
		}
		
		Collections.reverse(path);
		return path;
	}

	public int getSteps() {
		return steps;
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
	
	public int getLabel() {
		return label;
	}
	
	public static int alternateLabel(int label) {
		if (label == MAX) {
			return MIN;
		}
		else return MAX;
	}
	
}
