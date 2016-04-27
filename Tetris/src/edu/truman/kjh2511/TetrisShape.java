package edu.truman.kjh2511;

import java.awt.geom.Rectangle2D;
import java.util.Random;

public class TetrisShape {
	private Rectangle2D[] rects;
	private Random r;
	private int x;
	private int y;
	private int X_START;
	private int Y_START;
	private int size;
	private int[] sides;
	private Board board;
	private int startRow;
	private int lowestRow;
	private int rightMostColumn;
	private int leftMostColumn;
	private int centerColumn;
	public TetrisShape(int a, int b, int size, Board board){
		/**
		 * Constructor for the TetrisShape class
		 * @param int a, the X coordinate of the initial position
		 * @param int b, the Y coordinate of the initial position
		 * @param int size, the size of the rectangles of the shape
		 * @param Board board, the Board associated with this game
		 */
		this.startRow = -5;
		this.lowestRow = -5;
		this.centerColumn = 5;
		this.board = board;
		this.rects = new Rectangle2D[4];
		this.sides = new int[3];
		this.r = new Random();
		this.x = a;
		this.y = b;
		this.X_START = a;
		this.Y_START = b;
		this.size = size;
		//Chose the connections that constitute the TetrisShpae
		rects[0] = new Rectangle2D.Double(x, y, size, size);
		int n;
		for (int i = 1; i < 4; i++){
			n = r.nextInt(4);
			switch(n){
			case 0:
				if (i == 1 || sides[i - 2] != 2){
					setSide(0);
				} else{
					setSide(2);
					n = 2;
				}
				break;
			case 1: 
				if (i == 1 || sides[i - 2] != 3){
					setSide(1);
				} else{
					setSide(3);
					n = 3;
				}
				break;
			case 2: 
				if (i == 1 || sides[i - 2] != 0){
					setSide(2);
				} else{
				 	setSide(0);
					n = 0;
				}
				break;
			case 3: 
				if (i == 1 || sides[i - 2] != 1){
					setSide(3);
				} else{
					setSide(1);
					n = 1;
				}
				break;
			default:
				System.out.println("oops");
			}
			sides[i - 1] = n;
			rects[i] = new Rectangle2D.Double(x, y, size, size);
			findLowestRow();
			findColumns();
		}
	}
	public Rectangle2D[] getRects(){
		return rects;
	}
	public int getLowestRow(){
		/**Accessor method for the lowestRow attribute
		 * @return int, the lowestRow value
		 */
		return this.lowestRow;
	}
	private void setSide(int i){
		/**
		 * Adjusts the necessary coordinates for each rectangle
		 * based on its connecting side
		 * @param int i, the connecting side
		 * @return void
		 */
		switch(i){
		case 0: 
			this.x += size;
			break;
		case 1: 
			this.y -= size;
			break;
		case 2: 
			this.x -= size;
			break;
		case 3: 
			this.y += size;
			break;
		default: 
			//do nothing
		}
	}
	public void moveRight(){
		/**
		 * Translates the X coordinate of each rectangle one unit 
		 * to the right
		 * @return void 
		 */
		boolean wall = false;
		for (Rectangle2D c : rects){
			if (c.getX() == 270){
				System.out.println("Wall!");
				wall = true;
			}
		}
		for (Rectangle2D r : rects){
			if (!wall){
				r.setRect(r.getX() + size, r.getY(), size, size);
			}else {
				return;	
			}
		}
		if (!wall){
			centerColumn++;
		}
		X_START += size;
		findColumns();
	}
	public void moveLeft(){
		/**
		 * Translates the X coordinate of each rectangle one unit
		 * to the left
		 * @return void
		 */
		boolean wall = false;
		for (Rectangle2D c : rects){
			if (c.getX() == 0){
				System.out.println("Wall!");
				wall = true;
			}
		}
		for (Rectangle2D r : rects){
			if (!wall){
				r.setRect(r.getX() - size, r.getY(), size, size);
			}else {
				return;
			}
			
		}
		if (!wall){
			centerColumn--;
		}
		X_START -= size;
		findColumns();
	}
	public void rotate(){
		x = X_START;
		y = Y_START;
		for (int i = 1; i < 4; i++){
			if (sides[i - 1] == 0){
				setSide(3);
				sides[i - 1] = 3;
			} else{
				setSide(sides[i - 1] - 1);
				sides[i - 1] -= 1;
			}
			rects[i].setRect(x, y, size, size);
		}
		findLowestRow();
		findColumns();
	}
	public boolean fall(){
		/**
		 * Translates the Y coordinate of each rectangle one unit
		 * downwards
		 * @return boolean, whether or not the shape has reached the bottom
		 */
		startRow++;
		lowestRow++;
		if (lowestRow == 21){
			System.out.println("At the Bottom!");
			lockBoard();
			return true;
		}
		for (Rectangle2D r : rects){
			r.setRect(r.getX(), r.getY() + size, size, size);
		}
		Y_START += size;
		findLowestRow();
		return false;
	}
	public void findLowestRow(){
		lowestRow = startRow;
		for (int s : sides){
			if (s == 3){
				lowestRow++;
			}
		}
		System.out.println("Low -> " + lowestRow);
		System.out.println("SR -> " + startRow);
	}
	public void findColumns(){
		rightMostColumn = centerColumn;
		leftMostColumn = centerColumn;
		for (int s : sides){
			if (s == 0){
				rightMostColumn++;
			}else if(s == 2){
				leftMostColumn--;
			}
		}
		System.out.println("R -> " + rightMostColumn);
		System.out.println("L -> " + leftMostColumn);
		System.out.println("CC -> " + centerColumn);
	}
	public void lockBoard(){
		/**
		 * Calls the lockBoard method of Board, giving the corresponding
		 * indices of the rectangle's current position
		 */
		board.lockBoard(centerColumn, startRow);
		for(int s : sides){
			switch(s){
			case 0: 
				centerColumn++;
				break;
			case 1: 
				startRow--;
				break;
			case 2: 
				centerColumn--;
				break;
			case 3: 
				startRow++;
				break;
			default:
				//do nothing
			}
			board.lockBoard(centerColumn, startRow);
		}
	}
}
