package edu.truman.kjh2511;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JComponent;

public class GridPrinter extends JComponent{
	private final int X = 30;
	private final int Y = 30;
	private final int BOX_SIZE = 30;
	private final int ROWS = 22;
	private final int COLUMNS = 10;
	private int START_X = 150;
	private int START_Y = 0;
	private Board board = new Board();
	private Rectangle2D[][] grid;
	private TetrisShape s = new TetrisShape(START_X, START_Y, BOX_SIZE, board);
	private ArrayList<Color> colors;
	public GridPrinter(){
		/**
		 * Constructor for the GridPrinter class
		 */
		//Initializes the colors of the color array
		colors = new ArrayList<Color>();
		colors.add(new Color(255, 0, 0));
		colors.add(new Color(0, 255, 0));
		colors.add(new Color(0, 0, 255));
		colors.add(new Color(255, 0, 255));
		colors.add(new Color(0, 255, 255));
		colors.add(new Color(255, 255, 0));
		//Initializes grid
		grid = new Rectangle2D[10][22];
		for(int i = 0; i < ROWS; i ++){
			for (int j = 0; j < COLUMNS; j++){
				grid[j][i] = new Rectangle2D.Double(X * j, Y * i + 150, BOX_SIZE, BOX_SIZE);
			}
		}
	}
	public void moveLeft(){
		/**
		 * Wrapper class for the moveLeft() method of the TetrisShape class
		 * @return void
		 */
		s.moveLeft();
		repaint();
	}
	public void moveRight(){
		/**
		 * Wrapper class for the moveRight() method of the TetrisShape class
		 * @return void
		 */
		s.moveRight();
		repaint();
	}
	public void rotate(){
		/**
		 * Wrapper class for the rotate() method of the TetrisShape class
		 * @return void
		 */
		s.rotate();
		repaint();
	}
	public void fall(){
		/**
		 * "Falls" the current shape in control by the GridPrinter.
		 * Then, determines if a new shape is needed
		 * @return void
		 */
		boolean needNew = false;
		if (s.fall()){
			needNew = true;
		}else {
			for (Rectangle2D r : s.getRects()){
				if (s.getLowestRow() == 22){
					s.lockBoard();
					needNew = true;
					System.out.println("!!!At the bottom!");
					}else if (s.getLowestRow() > 0 && s.getLowestRow() < 21 && board.getSquares()[(int) r.getX()/30][s.getLowestRow() + 1] == true){
					s.lockBoard();
					needNew = true;
				}
			}	
		}
		if (needNew){
			s = new TetrisShape(START_X, START_Y, BOX_SIZE, board);
		}
		repaint();
	}
	public Color randomColor(){
		/**
		 * Simply returns a pseudo-random color from the colors Array
		 * @return Color, the random color chosen
		 */
		Random r = new Random();
		return (colors.get(r.nextInt(colors.size())));
	}
	public void paintComponent(Graphics g){
		/**
		 * Override of the PaintComponent method of the JComponent class.
		 * @param Graphics g, the Graphics component passed in 
		 * "behind the scenes" by the Java language
		 * @return void
		 */
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0));
		for (Rectangle2D r: s.getRects()){
			g2.fill(r);
		}
		g2.setColor(randomColor());
		//Draw the grid, filling shapes if necessary
		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLUMNS; j++){
				if(board.getSquares()[j][i] == true){
					if(!board.filledRow(i)){
						g2.setColor(new Color(255, 0, 0));
						g2.fill(grid[j][i]);
					}
						g2.setColor(new Color(0, 0, 255));
						g2.draw(grid[j][i]);
				}else {
					g2.setColor(new Color(0, 0, 255));
					g2.draw(grid[j][i]);
				}
			}
		}
		
	}
}
