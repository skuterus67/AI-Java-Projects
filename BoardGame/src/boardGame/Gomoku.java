package boardGame;

public class Gomoku {

	public int[][] board = new int[15][15];
	public int possibleMoves = 0;
	public int winner = 0;
	
	Gomoku(){
		for(int i=0; i<this.board.length; i++){
			for(int j=0; j<this.board.length; j++){
				board[i][j] = 0;
				possibleMoves++;
			}
		}
	}
	
	Gomoku(Gomoku game){
		for(int i=0; i<this.board.length; i++){
			for(int j=0; j<this.board.length; j++){
				this.board[i][j] = game.board[i][j];
			}
		}
		this.possibleMoves = game.possibleMoves;
		this.winner = game.winner;
	}
	
	public void setStone(int row, int column, boolean startingPlayer){
		if(this.getStone(row, column) == 0){
			if(startingPlayer)
				board[row][column] = 1;
			else
				board[row][column] = -1;
			possibleMoves--;
		}
		else{
			System.out.println("Place taken. Choose another coordinates");
		}
	}
	
	public void takeStone(int row, int column){
		board[row][column] = 0;
		possibleMoves++;
	}
	
	public int getStone(int row, int column){
		if(row < 0 || column < 0 || row >= this.board.length || column >= this.board.length){
			return -2;
		}
		else
			return this.board[row][column];
	}
	
	public int isMyNearby(int row, int column, int player){
		int combo = 0;
		if(this.getStone(row, column) == player){
			if(this.getStone(row-1, column-1) == player)
				combo++;
			if(this.getStone(row-1, column) == player)
				combo++;
			if(this.getStone(row-1, column+1) == player)
				combo++;
			if(this.getStone(row, column-1) == player)
				combo++;
			if(this.getStone(row, column+1) == player)
				combo++;
			if(this.getStone(row+1, column-1) == player)
				combo++;
			if(this.getStone(row+1, column) == player)
				combo++;
			if(this.getStone(row+1, column+1) == player)
				combo++;
			if(combo != 0)
				return combo;
			else
				return combo;
		}
		else
			return combo;
	}
	
//	public int checkCombos(int row, int column, int player){
//		int comboArr = 0;
//		comboArr = this.checkCombo(player);
////		comboArr[1] = this.checkComboColumn(row, column, player);
////		comboArr[2] = this.checkComboDiagonalRight(row, column, player);
////		comboArr[3] = this.checkComboDiagonalLeft(row, column, player);
//		return comboArr;
//	}
	
	public int[] checkCombo(int row, int column, int player){
		int[] comboArr = new int[4];
		int temp0 = 0, temp1 = 0, temp2 = 0, temp3 = 0;
		int mod = 8;
		for(int x=row-4, y=column-4; x<row+5 && y<column+5; x++, y++){
			if(this.getStone(row, y) == player){
				temp0++;
				comboArr[0] += 10 ^ (temp0);
			}
			else if(this.getStone(row, y) == player * (-1)){
				temp0 = 0;
				comboArr[0] = 0;
			}
			if(this.getStone(x, y) == player){
				temp1++;
				comboArr[1] += 10 ^ (temp1);
			}
			else if(this.getStone(x, y) == player * (-1)){
				temp1 = 0;
				comboArr[1] = 0;
			}
			if(this.getStone(x, column) == player){
				temp2++;
				comboArr[2] += 10 ^ (temp2);
			}
			else if(this.getStone(x, column) == player * (-1)){
				temp2 = 0;
				comboArr[2] = 0;
			}
			if(this.getStone(x, y+mod) == player){
				temp3++;
				comboArr[3] += 10 ^ (temp3);
			}
			else if(this.getStone(x, y+mod) == player * (-1)){
				temp3 = 0;
				comboArr[3] = 0;
			}
			mod = mod - 2;
		}
		return comboArr;
	}
	
//	public int checkComboColumn(int row, int column, int player){
//		int temp = 0;
//		if(this.getStone(row, column) == player && this.getStone(row, column-1) == player){
//			temp = this.checkComboRow(row, column-1, player);
//			temp++;
//		}
//		else if(this.getStone(row, column) == player && this.getStone(row, column+1) == player){
//			temp = this.checkComboRow(row, column+1, player);
//			temp++;
//		}
//		return temp;
//	}
//	
//	public int checkComboDiagonalRight(int row, int column, int player){
//		int temp = 0;
//		if(this.getStone(row, column) == player && this.getStone(row-1, column-1) == player){
//			temp = this.checkComboRow(row-1, column-1, player);
//			temp++;
//		}
//		else if(this.getStone(row, column) == player && this.getStone(row+1, column+1) == player){
//			temp = this.checkComboRow(row+1, column+1, player);
//			temp++;
//		}
//		return temp;
//	}
//	
//	public int checkComboDiagonalLeft(int row, int column, int player){
//		int temp = 0;
//		if(this.getStone(row, column) == player && this.getStone(row-1, column+1) == player){
//			temp = this.checkComboRow(row-1, column+1, player);
//			temp++;
//		}
//		else if(this.getStone(row, column) == player && this.getStone(row+1, column-1) == player){
//			temp = this.checkComboRow(row+1, column-1, player);
//			temp++;
//		}
//		return temp;
//	}
	
	public boolean checkForWin(int row, int column, int player){
		if(this.checkForWinRow(row, column, player) || this.checkForWinColumn(row, column, player) || this.checkForWinDiagonal(row, column, player)){
			this.winner = player;
			return true;
		}
		else
			return false;
	}
	
	public boolean checkForWinRow(int row, int column, int player){
		int cnt = 0;
		for(int i=0; i<this.board.length; i++){
			if(this.getStone(i, column) == player){
				cnt++;
			}
			else
				cnt = 0;
			if(cnt == 5)
				return true;
		}
		return false;
	}
	
	public boolean checkForWinColumn(int row, int column, int player){
		int cnt = 0;
		for(int i=0; i<this.board.length; i++){
			if(this.getStone(row, i) == player){
				cnt++;
			}
			else
				cnt = 0;
			if(cnt == 5)
				return true;
		}
		return false;
	}
	
	public boolean checkForWinDiagonal(int row, int column, int player){
		return this.checkDiagonalRight(row, column, player) || this.checkDiagonalLeft(row, column, player);
	}
	
	public boolean checkDiagonalRight(int row, int column, int player){
		int startingRow = row;
		int startingColumn = column;
		while(startingRow > 0 && startingColumn > 0){
			startingRow--;
			startingColumn--;
		}
		int cnt = 0;
		for(int i=startingRow, j=startingColumn; i<this.board.length && j<this.board.length; i++, j++){
			if(this.getStone(i, j) == player){
				cnt++;
			}
			else
				cnt = 0;	
			if(cnt == 5)
				return true;
		}
		return false;
	}
	
	public boolean checkDiagonalLeft(int row, int column, int player){
		int startingRow = row;
		int startingColumn = column;
		while(startingRow > 0 && startingColumn < this.board.length){
			startingRow--;
			startingColumn++;
		}
		int cnt = 0;
		for(int i=startingRow, j=startingColumn; i<this.board.length && j>0; i++, j--){
			if(this.getStone(i, j) == player){
				cnt++;
			}
			else
				cnt = 0;	
			if(cnt == 5)
				return true;
		}
		return false;
	}
	
	public static void main (String[] args){
//		Gomoku game = new Gomoku();
//		game.setStone(0, 8, true);
//		System.out.println(game.checkForWin(0,0,1));
//		game.setStone(1, 7, true);
//		System.out.println(game.checkForWin(1,1,1));
//		game.setStone(2, 6, true);
//		System.out.println(game.checkForWin(2,2,1));
//		game.setStone(3, 5, true);
//		System.out.println(game.checkForWin(3,3,1));
//		game.setStone(4, 4, true);
//		System.out.println(game.checkForWin(4,4,1));
	}
	
}
