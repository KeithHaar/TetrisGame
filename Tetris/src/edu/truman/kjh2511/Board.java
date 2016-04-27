package edu.truman.kjh2511;

public class Board {
	private boolean[][] squares;
	public Board(){
		squares = new boolean[10][22];
		for (int i = 0; i < 10; i++){
			for (int j = 0; j < 22; j++){
				squares[i][j] = false;
			}
		}
	}
	public void lockBoard(int a, int b){
		squares[a][b] = true;
	}
	public boolean[][] getSquares(){
		return squares;
	}
	public boolean filledRow(int row){
		for (int i = 0; i < 10; i++){
			if (squares[i][row] == false){
				return false;
			}
		}
		for (int j = row; j > 0; j--){
			for (int i = 0; i < 10; i++){
				squares[i][j] = squares[i][j - 1];
			}
		}
		for (int k = 0; k < 10; k++){
			squares[k][0] = false;
		}
		return true;
	}
}
