package letterMosaicBackTracking;

import java.util.Stack;

public class MosaicFC {
	
	int size;
	char[][] variables;
	char[] domain = {'x', 'o'};
	char[][] personalizedDomain, copyPersonalizedDomain;
	Stack<char[][]> foundDomains = new Stack<char[][]>();
	
	
	MosaicFC(int size){
		this.size = size;
		this.variables = new char[size][size];
		initialFiller(this.variables, size);
		createPersonalizedDomain(size);
	}
	
	void initialFiller(char[][] variables, int size){
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				variables[i][j] = 'a';
			}
		}
	}
	
	void createPersonalizedDomain(int size){
		this.personalizedDomain = new char[size * size][this.domain.length];
		this.copyPersonalizedDomain = new char[size * size][this.domain.length];
		for(int i = 0; i < size * size; i++){
			this.personalizedDomain[i] = domain;
			this.copyPersonalizedDomain[i] = domain;
		}
	}
	
	
	boolean checkConstraint3InARow(char[][] assignment, int row, int column, char variable){ //check every value insertion
		assignment[row][column] = variable;
		//char[] tempDomain = this.copyPersonalizedDomain[(row * this.size) + column];
		//if (tempDomain != null){
			if (column == 0){
				assignment[row][column] = 'a';
				return true;
			}
				
			else if (column == 1){
//				if (assignment[row][column] == assignment[row][column-1]){
//					this.copyPersonalizedDomain[(row * this.size) + column + 1] = this.deleteVariableFromDomain(this.copyPersonalizedDomain[(row * this.size) + column + 1], variable);
//					return true;
//				}
				assignment[row][column] = 'a';
				return true;			
			}
			else if (assignment[row][column] == assignment[row][column-1] && assignment[row][column] == assignment[row][column-2]){
				assignment[row][column] = 'a';
				return false;
			}
				
			else if (!(assignment[row][column] == assignment[row][column-1] && assignment[row][column] == assignment[row][column-2])){
//				if (assignment[row][column] == assignment[row][column-1]){
//					if (row == this.size - 1 && column == this.size - 1)
//						return true;
//					this.copyPersonalizedDomain[(row * this.size) + column + 1] = this.deleteVariableFromDomain(this.copyPersonalizedDomain[(row * this.size) + column + 1], variable);
//					return true;
//				}
				assignment[row][column] = 'a';
				return true;
			}
		//}
		assignment[row][column] = 'a';
		return false;
	}
	
	boolean checkConstraint3InAColumn(char[][] assignment, int row, int column, char variable){ // check every value insertion
		assignment[row][column] = variable;
//		char[] tempDomain = this.copyPersonalizedDomain[(row * this.size) + column];
//		if (tempDomain != null){
			if (row == 0){
				assignment[row][column] = 'a';
				return true;
			}
				
			else if (row == 1){
//				if (assignment[row][column] == assignment[row-1][column]){
//					this.copyPersonalizedDomain[((row + 1) * this.size) + column] = this.deleteVariableFromDomain(this.copyPersonalizedDomain[((row + 1) * this.size) + column], variable);
//					return true;
//				}
				assignment[row][column] = 'a';
				return true;			
			}
			else if (assignment[row][column] == assignment[row-1][column] && assignment[row][column] == assignment[row-2][column]){
				assignment[row][column] = 'a';
				return false;
			}
				
			else if (!(assignment[row][column] == assignment[row-1][column] && assignment[row][column] == assignment[row-2][column])){
//				if (assignment[row][column] == assignment[row-1][column]){
//					if (row != this.size - 1){
//						this.copyPersonalizedDomain[((row + 1) * this.size) + column] = this.deleteVariableFromDomain(this.copyPersonalizedDomain[((row + 1) * this.size) + column], variable);
//						return true;
//					}
//					else
//						return true;
//				}
				assignment[row][column] = 'a';
				return true;
			}
//		}
		assignment[row][column] = 'a';
		return false;
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
						assignment[row][column] = 'a';
						return false;
					}
				}
				assignment[row][column] = 'a';
				return true;
			}
			else{
				assignment[row][column] = 'a';
				return true;
			}
				
		}
		else{
			assignment[row][column] = 'a';
			return true;
		}
			
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
						assignment[row][column] = 'a';
						return false;
					}
				}
				assignment[row][column] = 'a';
				return true;
			}
			else{
				assignment[row][column] = 'a';
				return true;
			}
				
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
	
	boolean checkForEmptyDomain(int row, int column, char[][] domains){
		//int counter = 0;
		for(int i = (row * this.size) + column; i < this.size * this.size; i++){
			if(domains[i] == null)
				return false;
		}
//		if(counter == this.size * this.size - ((row * this.size) + column))
//			
		return true;
	}
	
	char[][] copyDomain(char[][] domains){
		char[][] temp = new char[this.size * this.size][this.domain.length];
		for(int i = 0; i < this.size * this.size; i++){
			for(int j = 0; j < domains[i].length; j++){
				temp[i][j] = domains[i][j];
				if(temp[i][j] == '\u0000')
					System.out.println("Hej Monika");
			}
		}
		this.adjustDomain(temp);
		return temp;
	}
	
	void adjustDomain(char[][] domains){
		for(int i = 0; i < this.size * this.size; i++){
			if(domains[i][1] == '\u0000'){
				char[] temp = new char[1];
				temp[0] = domains[i][0];
				domains[i] = temp;
			}
			if(domains[i][0] == '\u0000'){
				domains[i] = null;
			}
		}
	}
	
	void restoreDomain(int row, int column, char[][] domains){
		domains = this.foundDomains.peek();
	}
	
	char[][] forwardChecking(char[][] assignment, int row, int column, char[][] domains, char variable){
		int domainsIndex = (row * this.size) + column;
		assignment[row][column] = variable;
		for(int i = domainsIndex + 1; i < this.size * this.size; i++){
			if(!this.checkForEmptyDomain(row, column, domains)){
				int newColumn = i % this.size;
				int newRow = i / this.size;
				for(int j = 0; j < domains[i].length; j++){
					if(!this.checkForEmptyDomain(row, column, domains)){
						if(!this.checkConstraintEachCell(assignment, newRow, newColumn, domains[i][j])){
							domains[i] = this.deleteVariableFromDomain(domains[i], domains[i][j]);
						}
					}
					else{
						this.foundDomains.pop();
						this.restoreDomain(row, column, domains);
					}
				}
			}
		}
		assignment[row][column] = 'a';
		return domains;
	}
	
	char[] deleteVariableFromDomain(char[] domain, char variable){
		if (domain.length == 2){
			char unusedVariable = 0;
			switch(variable){
				case 'x':
					unusedVariable = 'o';
					break;
				case 'o':
					unusedVariable = 'x';
					break;
			}
			domain = new char[1];
			domain[0] = unusedVariable;
		}
		else if (domain.length == 1){
			if (variable == domain[0]){
				domain = null;
			}
		}
		return domain;
	}
	
	boolean recursiveBackTracking(char[][] assignment, int row, int column, char[][] domains){
		if(!checkForCompletion(assignment)){
			for (char variable : domains[(row * this.size) + column]){
				if (this.checkConstraintEachCell(assignment, row, column, variable)){
					//this.copyPersonalizedDomain = this.copyDomain();
					if (this.checkForEmptyDomain(row, column, domains)){
						char[][] copyOfDomains = this.copyDomain(domains);
						copyOfDomains = this.forwardChecking(assignment, row, column, copyOfDomains, variable);
						this.foundDomains.push(copyOfDomains);
						assignment[row][column] = variable;
						if (row != this.size-1 && column != this.size-1){
							if(this.recursiveBackTracking(assignment, row, column+1, copyOfDomains)){
								return true;
							}
						}
						else if (row != this.size-1 && column == this.size-1){
							if(this.recursiveBackTracking(assignment, row+1, 0, copyOfDomains)){
								return true;
							}
						}
						else if (row == this.size-1 && column != this.size-1){
							if(this.recursiveBackTracking(assignment, row, column+1, copyOfDomains)){
								return true;
							}
						}
						else if (row == this.size-1 && column == this.size-1){
							if(this.recursiveBackTracking(assignment, row, column, copyOfDomains)){
								return true;
							}
						}
					}
				}
			}
			assignment[row][column] = 'a';
			return false;
		}
		return true;		
	}
	
	void backTracking(int row, int column){
		this.recursiveBackTracking(this.variables, row, column, this.personalizedDomain);
	}
	
	public static void main (String[] args){
	
		MosaicFC letterMosaic = new MosaicFC(13);
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

