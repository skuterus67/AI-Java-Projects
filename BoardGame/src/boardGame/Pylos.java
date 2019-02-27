package boardGame;

import java.util.ArrayList;

public class Pylos {

	public ArrayList<int[][]> board = new ArrayList<int[][]>();
	public int[][] level4 = new int[4][4];
	public int[][] level3 = new int[3][3];
	public int[][] level2 = new int[2][2];
	public int[][] level1 = new int[1][1];
	
	public int[] playerBalls = new int[2];
	
	public boolean canTakePlayer1;
	public boolean canTakePlayer2;
	public boolean canMovePlayer1;
	public boolean canMovePlayer2;
	
	public boolean gameOver;
	public int winner;
	
	Pylos(){
		this.board.add(0, level4);
		this.board.add(1, level3);
		this.board.add(2, level2);
		this.board.add(3, level1);
		for(int height=0; height<this.board.size(); height++){
			for(int i=0; i<this.board.get(height).length; i++){
				for(int j=0; j<this.board.get(height).length; j++){
					if(height>0)
						this.board.get(height)[i][j] = -2;
					else
						this.board.get(height)[i][j] = 0;
				}
			}
		}
		this.playerBalls[0] = 15;
		this.playerBalls[1] = 15;
		this.canTakePlayer1 = false;
		this.canTakePlayer2 = false;
		this.canMovePlayer1 = false;
		this.canMovePlayer2 = false;
		this.gameOver = false;
		this.winner = 0;
	}
	
	public int getBall(int level, int row, int column){
		if(row < 0 || column < 0 || row >= this.board.get(level).length || column >= this.board.get(level).length)
			return -2;
		return this.board.get(level)[row][column];
	}
	
	public void placeBall(int level, int row, int column, boolean startingPlayer){
		if(startingPlayer && this.playerBalls[0] != 0){
			this.board.get(level)[row][column] = 1;
			this.playerBalls[0]--;
			if(this.playerBalls[0] != 1)
				if(!this.canTakePlayer1)
					this.canTakePlayer1 = this.checkForTaking(level, row, column, 1);
		}
		else if(!startingPlayer && this.playerBalls[1] != 0){
			this.board.get(level)[row][column] = -1;
			this.playerBalls[1]--;
			if(this.playerBalls[1] != 1)
				if(!this.canTakePlayer2)
					this.canTakePlayer2 = this.checkForTaking(level, row, column, -1);
		}
	}
	
	public void takeBall(int level, int row, int column, boolean startingPlayer){
		if(startingPlayer){
			this.board.get(level)[row][column] = 0;
			this.playerBalls[0]++;
		}
		else{
			this.board.get(level)[row][column] = 0;
			this.playerBalls[1]++;
		}
	}
	
	public boolean checkForWin(){
		if(this.playerBalls[0] == 0 && this.playerBalls[1] == 0){
			this.winner = board.get(3)[0][0];
			this.gameOver = true;
			return this.gameOver;
		}
		else
			return this.gameOver;
	}
	
	public void moveBall(int fromLevel, int fromRow, int fromColumn, int toLevel, int toRow, int toColumn, boolean startingPlayer){
		this.checkBoardForMoving(fromLevel, fromRow, fromColumn);
		if(!this.hasAbove(fromLevel, fromRow, fromColumn)){
			this.takeBall(fromLevel, fromRow, fromColumn, startingPlayer);
			this.checkForAbsentBase(fromLevel, fromRow, fromColumn);
			if(this.getBall(toLevel, toRow, toColumn) == 0)
				this.placeBall(toLevel, toRow, toColumn, startingPlayer);
			else{
				this.placeBall(fromLevel, fromRow, fromColumn, startingPlayer);
				System.out.println("Can't move to this location, choose another");
			}
		}
		else
			System.out.println("Can't take this ball, choose another");
	}
	
	public void checkBoardForMoving(int level, int row, int column){
		int temp;
		temp = this.getBall(level, row, column-1); // check left ball
		if(temp != -2){ // still on board and right player?
			// 2
			temp = this.getBall(level, row-1, column-1); //check left-top ball
			if(temp != -2){ // still on board and right player?
				// 3
				temp = this.getBall(level, row-1, column); // check top ball
				if(temp != -2){ // still on board and right player?
					// 4
					this.board.get(level+1)[row-1][column-1] = 0; //"unlock" place above
				}
			}
			else{
				temp = this.getBall(level, row+1, column-1); //check left-bottom ball
				if(temp != -2){ // still on board and right player?
					// 3
					temp = this.getBall(level, row+1, column); //check bottom ball
					if(temp != -2){ // still on board and right player?
						// 4
						this.board.get(level+1)[row][column-1] = 0; //"unlock" place above
					}
				}
			}
		}
		else{
			temp = this.getBall(level, row, column+1); // check right ball
			if(temp != -2){ // still on board and right player?
				// 2
				temp = this.getBall(level, row-1, column+1); // check right-top ball
				if(temp != -2){ // still on board and right player?
					// 3
					temp = this.getBall(level, row-1, column); //check top ball
					if(temp != -2){ // still on board and right player?
						// 4
						this.board.get(level+1)[row-1][column] = 0; //"unlock" place above
					}
				}
				else{
					temp = this.getBall(level, row+1, column+1); //check left-bottom ball
					if(temp != -2){ // still on board and right player?
						// 3
						temp = this.getBall(level, row+1, column); //check bottom ball
						if(temp != -2){ // still on board and right player?
							// 4
							this.board.get(level+1)[row][column] = 0; //"unlock" place above
						}
					}
				}
			}
		}
	}
	
	public void checkForAbsentBase(int level, int row, int column){
		if(this.getBall(level, row, column) != -2)
			this.board.get(level+1)[row][column] = -2;
		if(this.getBall(level, row-1, column) != -2)
			this.board.get(level+1)[row-1][column] = -2;
		if(this.getBall(level, row, column-1) != -2)
			this.board.get(level+1)[row][column-1] = -2;
		if(this.getBall(level, row-1, column-1) != -2)
			this.board.get(level+1)[row-1][column-1] = -2;
	}
	
	public boolean hasAbove(int level, int row, int column){
		if(this.getBall(level+1, row-1, column-1) % 2 != 0
				&& this.getBall(level+1, row-1, column) % 2 != 0
				&& this.getBall(level+1, row, column-1) % 2 != 0
				&& this.getBall(level+1, row, column) % 2 != 0){
			return true;
		}
		else
			return false;
	}
	
	public boolean checkForTaking(int level, int row, int column, int player){
		int temp;
		temp = this.getBall(level, row, column-1); // check left ball
		if(temp != -2 && temp == player){ // still on board and right player?
			// 2
			temp = this.getBall(level, row-1, column-1); //check left-top ball
			if(temp != -2 && temp == player){ // still on board and right player?
				// 3
				temp = this.getBall(level, row-1, column); // check top ball
				if(temp != -2 && temp == player){ // still on board and right player?
					// 4
					return true;
				}
			}
			else{
				temp = this.getBall(level, row+1, column-1); //check left-bottom ball
				if(temp != -2 && temp == player){ // still on board and right player?
					// 3
					temp = this.getBall(level, row+1, column); //check bottom ball
					if(temp != -2 && temp == player){ // still on board and right player?
						// 4
						return true;
					}
				}
			}
		}
		else{
			temp = this.getBall(level, row, column+1); // check right ball
			if(temp != -2 && temp == player){ // still on board and right player?
				// 2
				temp = this.getBall(level, row-1, column+1); // check right-top ball
				if(temp != -2 && temp == player){ // still on board and right player?
					// 3
					temp = this.getBall(level, row-1, column); //check top ball
					if(temp != -2 && temp == player){ // still on board and right player?
						// 4
						return true;
					}
				}
				else{
					temp = this.getBall(level, row+1, column+1); //check left-bottom ball
					if(temp != -2 && temp == player){ // still on board and right player?
						// 3
						temp = this.getBall(level, row+1, column); //check bottom ball
						if(temp != -2 && temp == player){ // still on board and right player?
							// 4
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
