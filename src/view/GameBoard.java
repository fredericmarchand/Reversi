package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
//import java.util.LinkedList;
//import java.util.Scanner;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;





//import controller.Search;
//import model.Coordinate;
import model.Map;


public class GameBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton[][] GUI;
	private Map map;

	private int turn;
	
	private static Color WHITE_COLOR = Color.white;
	private static Color BLACK_COLOR = Color.black;

	public GameBoard(String title, Map map) {
		super(title);
		turn = Map.PLAYER1;
		GUI = new JButton[8][8];
		buildWindow();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 800);
		this.map = map;
		
		for (int row = 0; row < 8; row++){
			for (int col = 0; col < 8; col++){
				GUI[row][col].addActionListener(new ActionListener() {//an action listener is created for each button on the board
					public void actionPerformed(ActionEvent event) {
						handleButtonPress((JButton)event.getSource());
					}
				});
			}
		}
	}
	
	public void toggleTurn() {
		if (turn == Map.PLAYER1)
			turn = Map.PLAYER2;
		else
			turn = Map.PLAYER1;
	}
		
	//Function for AI
	public void clickButton(int row, int col) {
		GUI[row][col].doClick();
	}
	
	public void handleButtonPress(JButton source){
		int row = -1, col = -1;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (GUI[i][j] == source) {
					row = i;
					col = j;
					break;
				}
			}
			if (row != -1)
				break;
		}
		if (map.handleSelection(row, col, turn)) {
			update(map);
			this.toggleTurn();
		}
	}
	
	private void buildWindow(){//method used to construct the window
		int rows = 8;
		int columns = 8;
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 5); // default spacing
		setLayout(layout);
		Border border = LineBorder.createGrayLineBorder();

		JPanel centerPanel = new JPanel(new GridLayout(rows, columns, 0, 0));
		for (int row = 0; row < rows; row++){
			for (int col = 0; col < columns; col++){
				GUI[row][col] = new JButton();
				GUI[row][col].setOpaque(true);
				GUI[row][col].setBorder(border);
				centerPanel.add(GUI[row][col]);
			}
		}
		constraints.gridx = 0;
		constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        layout.setConstraints(centerPanel, constraints);
		add(centerPanel);
	}
	
	public void update(Map map){
		for (int x = 0; x < map.getRows(); ++x) {
			for (int y = 0; y < map.getColumns(); ++y) {
				switch(map.getMap()[x][y])
				{
					case Map.EMPTY:
						break;
					case Map.WHITE:
						GUI[x][y].setForeground(WHITE_COLOR);
						GUI[x][y].setBackground(WHITE_COLOR);
						GUI[x][y].setEnabled(false);
						break;
					case Map.BLACK:
						GUI[x][y].setForeground(BLACK_COLOR);
						GUI[x][y].setBackground(BLACK_COLOR);
						GUI[x][y].setEnabled(false);
						break;
				}
			}
		}
	}
	
	public static void main(String args[]) {
		
		//Manual controls
		/*@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		while(true){
			String line = "";
	        while(sc.hasNextLine()) {
	        	line = sc.next(); 
	        	break;
	        }
	        switch (line.charAt(0)){
		        case 'w':
		        	m.moveSnakeInDirection(Snake.UP);
		        	break;
		        case 'a':
		        	m.moveSnakeInDirection(Snake.LEFT);
		        	break;
		        case 's':
		        	m.moveSnakeInDirection(Snake.DOWN);
		        	break;
		        case 'd':
		        	m.moveSnakeInDirection(Snake.RIGHT);
		        	break;
	        }
			gb.update(m);
		}*/
	}
}
