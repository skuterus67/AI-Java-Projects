package letterMosaicBackTracking;

public class Mosaic {
	
	int size;
	char[][] variables;
	char[] domain = {'x', 'o'};
	
	
	Mosaic(int size){
		this.size = size;
		this.variables = new char[size][size];
		initialFiller(this.variables, size);
	}
	
	void initialFiller(char[][] variables, int size){
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				variables[i][j] = 'a';
			}
		}
	}
	
	boolean checkConstraint3InARow(char[][] assignment, int row, int column, char variable){ //check every value insertion
		assignment[row][column] = variable;
		if (column == 0 || column == 1)
			return true;
		else if (assignment[row][column] == assignment[row][column-1] && assignment[row][column] == assignment[row][column-2])
			return false;
		else
			return true;
	}
	
	boolean checkConstraint3InAColumn(char[][] assignment, int row, int column, char variable){ // check every value insertion
		assignment[row][column] = variable;
		if (row == 0 || row == 1)
			return true;
		else if (assignment[row][column] == assignment[row-1][column] && assignment[row][column] == assignment[row-2][column])
			return false;
		else
			return true;
	}
	
	boolean checkConstraintSameRow(char[][] assignment, int row, int column, char variable){ // check every row end
		assignment[row][column] = variable;
		if (column == this.size-1){
			if (row != 0){
				char[] rowResult = assignment[row];
				for(int i=row; i>0; i--){
					char[] temp = assignment[i-1];
					int chkSum = 0;
					for (int j=0; j<size; j++){
						if(rowResult[j] == temp[j])
							chkSum++;
					}
					if(chkSum == size){
						return false;
					}
				}
				return true;
			}
			else
				return true;
		}
		else
			return true;
	}
	
	boolean checkConstraintSameColumn(char[][] assignment, int row, int column, char variable){ // check every column end
		assignment[row][column] = variable;
		if (row == this.size-1 && column != 0){
				char[] columnResult = new char[this.size];
				for (int i=0; i<this.size; i++){
					columnResult[i] = assignment[i][column];
				}
				char[] temp = new char[this.size];
				for(int j=column; j>0; j--){
					for (int i=0; i<this.size; i++){
						temp[i] = assignment[i][j-1];
					}
					int chkSum = 0;
					for (int i=0; i<this.size; i++){
						if(columnResult[i] == temp[i])
							chkSum++;
					}
					if(chkSum == size){
						return false;
					}
				}
				return true;
			}
			else
				return true;
	}
	
	boolean checkConstraintEachCell(char[][] assignment, int row, int column, char variable){
		return this.checkConstraint3InAColumn(assignment, row, column, variable)
				&& this.checkConstraint3InARow(assignment, row, column, variable)
				&& this.checkConstraintSameColumn(assignment, row, column, variable)
				&& this.checkConstraintSameRow(assignment, row, column, variable);
	}
	
	boolean checkForCompletion(char[][] assignment){
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				if(assignment[i][j] == 'a')
					return false;
			}
		}
		return true;
	}

	boolean recursiveBackTracking(char[][] assignment, int row, int column){
		if(!checkForCompletion(assignment)){
			for (char variable : domain){
				if (this.checkConstraintEachCell(assignment, row, column, variable)){
					assignment[row][column] = variable;
					if (row != this.size-1 && column != this.size-1){
						if(this.recursiveBackTracking(assignment, row, column+1))
							return true;
					}
					else if (row != this.size-1 && column == this.size-1){
						if(this.recursiveBackTracking(assignment, row+1, 0))
							return true;
					}
					else if (row == this.size-1 && column != this.size-1){
						if(this.recursiveBackTracking(assignment, row, column+1))
							return true;
					}
					else if (row == this.size-1 && column == this.size-1){
						if(this.recursiveBackTracking(assignment, row, column))
							return true;
					}
				}
			}
			assignment[row][column] = 'a';
			return false;
		}
		return true;		
	}
	
	void backTracking(int row, int column){
		this.recursiveBackTracking(this.variables, row, column);
	}
	
	public static void main (String[] args){
	
		Mosaic letterMosaic = new Mosaic(12);
		long startTime = System.nanoTime();
		letterMosaic.backTracking(0, 0);
		long endTime = System.nanoTime();
		double time = (endTime - startTime) / 1000000;
		System.out.print("\n");
		for(int i = 0; i < letterMosaic.size; i++){
			for(int j = 0; j < letterMosaic.size; j++){
				System.out.print(letterMosaic.variables[i][j] + " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
		System.out.println(time + " miliseconds");
		}
		
	}

