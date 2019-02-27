package hitoriSolver;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {

	boolean checkForCompletion(ArrayList<Cell> solution, int size){
		for(int i = 0; i < solution.size(); i++){
			if(!solution.get(i).checked)
				return false;
		}
		return true;
	}
	
	boolean checkSameNumberInRow(ArrayList<Cell> solution, int row, int column, int size){
		if(solution.get((row * size) + column).crossed)
			return true;
		else{
			int number = solution.get((row * size) + column).value;
			for(int i = 0; i < size; i++){
				if(((row * size) + i) != ((row * size) + column)){
					if(number == solution.get((row * size) + i).value && !solution.get((row * size) + i).crossed && solution.get((row * size) + i).checked)
						return false;
				}
			}
			return true;
		}
	}
	
	boolean checkSameNumberInColumn(ArrayList<Cell> solution, int row, int column, int size){
		if(solution.get((row * size) + column).crossed)
			return true;
		else{
			int number = solution.get((row * size) + column).value;
			for(int i = 0; i < size; i++){
				if(((row * size) + i) != ((row * size) + column)){
					if(number == solution.get((i * size) + column).value && !solution.get((i * size) + column).crossed && solution.get((i * size) + column).checked)
						return false;
				}
			}
			return true;
		}
	}
	
	boolean checkIfNeighbouringCellsCrossed(ArrayList<Cell> solution, int row, int column, int size){
		if(!solution.get((row * size) + column).crossed)
			return true;
		else{
			if(row < size - 1){
				if(solution.get(((row + 1) * size) + column).crossed)
					return false;
			}
			if(row > 0){
				if(solution.get(((row - 1) * size) + column).crossed)
					return false;
			}
			if(column < size - 1){
				if(solution.get((row * size) + column + 1).crossed)
					return false;
			}
			if(column > 0){
				if(solution.get((row * size) + column - 1).crossed)
					return false;
			}
		}
		return true;
	}
	
	int findFirstUncrossed(ArrayList<Cell> solution, int size){
		int i;
		for(i = 0; i < (size * size); i++){
			if(!solution.get(i).crossed)
				break;
		}
		return i;
	}
	
	int calculateUncrossed(ArrayList<Cell> solution, int size){
		int j = 0;
		for(int i = 0; i < (size * size); i++){
			if(!solution.get(i).crossed)
				j++;
		}
		return j;
	}
	
	boolean checkIfAllConected(ArrayList<Cell> solution, int size){
		Stack<Cell> visitedCells = new Stack<Cell>();
		int startIndex = this.findFirstUncrossed(solution, size);
		int row = startIndex / size;
		int column = startIndex % size;
		visitedCells = this.connectionCheckingRecurrence(visitedCells, startIndex, solution, row, column, size);
		if(visitedCells.size() == this.calculateUncrossed(solution, size))
			return true;
		else
			return false;
	}
	
	Stack<Cell> connectionCheckingRecurrence(Stack<Cell> visitedCells, int startIndex, ArrayList<Cell> solution, int row, int column, int size){
		if(startIndex - 1 >= 0 && solution.get(startIndex - 1).row == row){
			if(!solution.get(startIndex - 1).crossed && !this.isCellOnStack(visitedCells, solution.get(startIndex - 1))){
				if(!isCellOnStack(visitedCells, solution.get(startIndex)))
					visitedCells.push(solution.get(startIndex));
				visitedCells = this.connectionCheckingRecurrence(visitedCells, startIndex - 1, solution, row, column - 1, size);
			}
		}
		if(startIndex + size < size * size){
			if(!solution.get(startIndex + size).crossed && !this.isCellOnStack(visitedCells, solution.get(startIndex + size))){
				if(!isCellOnStack(visitedCells, solution.get(startIndex)))
					visitedCells.push(solution.get(startIndex));
				visitedCells = this.connectionCheckingRecurrence(visitedCells, startIndex + size, solution, row + 1, column, size);
			}
		}
		if(startIndex + 1 < size * size && solution.get(startIndex + 1).row == row){
			if(!solution.get(startIndex + 1).crossed && !this.isCellOnStack(visitedCells, solution.get(startIndex + 1))){
				if(!isCellOnStack(visitedCells, solution.get(startIndex)))
					visitedCells.push(solution.get(startIndex));
				visitedCells = this.connectionCheckingRecurrence(visitedCells, startIndex + 1, solution, row, column + 1, size);
			}
		}
		if(startIndex - size >= 0 && !solution.get(startIndex - size).equals(null)){
			if(!solution.get(startIndex - size).crossed && !this.isCellOnStack(visitedCells, solution.get(startIndex - size))){
				if(!isCellOnStack(visitedCells, solution.get(startIndex)))
					visitedCells.push(solution.get(startIndex));
				visitedCells = this.connectionCheckingRecurrence(visitedCells, startIndex - size, solution, row - 1, column, size);
			}
		}
		if(!isCellOnStack(visitedCells, solution.get(startIndex)))
			visitedCells.push(solution.get(startIndex));
		return visitedCells;
	}
	
	boolean isCellOnStack(Stack<Cell> visitedCells, Cell cell){
		if(visitedCells == null)
			return false;
		else{
			for(int i = 0; i < visitedCells.size(); i++){
				if(visitedCells.get(i).equals(cell))
					return true;
			}
			return false;
		}
	}
	
	boolean checkConstraintsEachCell(ArrayList<Cell> solution, int row, int column, int size, boolean shouldCross){
		solution.get((row * size) + column).setCrossed(shouldCross);
		if(this.checkSameNumberInRow(solution, row, column, size)
				&& this.checkSameNumberInColumn(solution, row, column, size)
				&& this.checkIfNeighbouringCellsCrossed(solution, row, column, size)
				&& this.checkIfAllConected(solution, size)){
			solution.get((row * size) + column).setCrossed(false);
			return true;
		}
		else{
			solution.get((row * size) + column).setCrossed(false);
			return false;
		}
			
	}
	
	boolean backTracking(ArrayList<Cell> solution, int row, int column, int size){
		if(!this.checkForCompletion(solution, size)){
			boolean shouldCross;
			for(int i = 0; i < 2; i++){
				if (i == 0)
					shouldCross = true;
				else
					shouldCross = false;
				if(this.checkConstraintsEachCell(solution, row, column, size, shouldCross)){
					solution.get((row * size) + column).setCrossed(shouldCross);
					solution.get((row * size) + column).setChecked(true);
					if (row != size - 1 && column != size - 1){
						if(this.backTracking(solution, row, column+1, size))
							return true;
						else
							solution.get((row * size) + column).setChecked(false);
					}
					else if (row != size-1 && column == size-1){
						if(this.backTracking(solution, row+1, 0, size))
							return true;
						else
							solution.get((row * size) + column).setChecked(false);
					}
					else if (row == size-1 && column != size-1){
						if(this.backTracking(solution, row, column+1, size))
							return true;
						else
							solution.get((row * size) + column).setChecked(false);
					}
					else if (row == size-1 && column == size-1){
						if(this.backTracking(solution, row, column, size))
							return true;
						else
							solution.get((row * size) + column).setChecked(false);
					}
				}
			}
			solution.get((row * size) + column).setCrossed(false);
			solution.get((row * size) + column).setChecked(false);
			return false;
		}
		return true;
	}
	
	public static void main (String[] args){
		
		int size = 10; // 4, 6, 8, 10
		int hitoriIndex = 5; // 0 - size 4; 1,2 - size 6; 3,4, - size 8; 5 - size 10
		Solver hitoriSolver = new Solver();
		Hitori hitori = new Hitori(size);
		hitori.createDiagram(hitori.puzzles[hitoriIndex]);
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				System.out.print(hitori.diagram.get((i * size) + j).value + " ");
			}
			System.out.println(" ");
		}
		System.out.println(" ");
		long startTime = System.nanoTime();
		boolean response = hitoriSolver.backTracking(hitori.solution, 0, 0, size);
		long endTime = System.nanoTime();
		double time = (endTime - startTime) / 1000000;
		if(response){
			for (int i = 0; i < size; i++){
				for (int j = 0; j < size; j++){
					if(hitori.solution.get((i * size) + j).crossed)
						System.out.print("X ");
					else
						System.out.print(hitori.solution.get((i * size) + j).value + " ");
				}
				System.out.println(" ");
			}
		}
		System.out.print("\n");
		System.out.println(time + " miliseconds");
	}

}

class Cell{
	
	int row;
	int column;
	int value;
	boolean checked;
	boolean crossed;
	boolean[] checksPerformed = {false, false};
	
	Cell(int row, int column, int value){
		this.row = row;
		this.column = column;
		this.value = value;
		this.checked = false;
		this.crossed = false;
	}
	
	void setChecked(boolean checked){
		this.checked = checked;
	}
	
	void setCrossed(boolean crossed){
		this.crossed = crossed;
	}
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		else if(obj == null || this.getClass() != obj.getClass())
			return false;
		return (this.row == ((Cell)obj).row
				&& this.column == ((Cell)obj).column
				&& this.value == ((Cell)obj).value);
	}
}

class Hitori {
	
	int size;
	ArrayList<Cell> diagram;
	ArrayList<Cell> solution;
	String[] puzzles = {"2;2;2;4;1;4;2;3;2;3;2;1;3;4;1;2;", 
			"3;6;2;6;5;6;5;1;3;6;2;2;5;6;1;6;4;3;6;2;6;5;1;5;1;1;4;1;3;5;3;3;5;2;6;5;",
			"3;2;5;6;5;1;6;3;1;3;4;3;4;3;5;4;1;6;5;3;6;1;6;3;4;5;4;2;6;2;1;6;3;5;3;5;",
			"5;2;8;8;3;4;7;1;2;1;6;4;6;5;5;7;8;4;5;4;7;3;4;1;3;4;4;6;6;2;3;8;6;2;7;2;1;4;8;3;1;6;6;3;8;7;5;2;5;3;1;4;4;2;6;3;4;5;8;7;8;1;3;4;",
			"3;8;1;5;4;2;7;2;8;1;6;3;6;7;4;4;6;1;4;7;2;1;5;8;5;2;6;1;8;3;1;7;4;7;5;7;6;4;2;8;8;4;3;7;3;2;1;6;2;8;7;4;5;4;6;4;8;3;1;8;1;6;4;1;",
			"2;9;6;6;10;3;8;8;1;2;9;9;4;5;8;10;1;3;6;8;5;6;2;5;4;2;10;3;8;9;6;5;3;10;9;1;9;2;5;4;2;3;4;1;8;2;5;3;2;10;10;1;5;2;6;9;3;4;1;7;7;8;1;2;5;6;6;6;10;3;4;9;8;2;2;7;9;10;9;4;1;10;9;9;7;4;4;4;3;6;2;1;10;10;3;8;7;5;7;2;"
			};
	
	Hitori(int size){
		this.size = size;
		this.diagram = new ArrayList<Cell>(size * size);
		this.solution = new ArrayList<Cell>(size * size);
	}
	
	void createDiagram (String puzzle){
		String[] splitted = puzzle.split(";");
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				this.diagram.add(new Cell(i, j, Integer.parseInt(splitted[(i * this.size) + j])));
				this.solution.add(new Cell(i, j, Integer.parseInt(splitted[(i * this.size) + j])));
			}
		}
	}
	
}