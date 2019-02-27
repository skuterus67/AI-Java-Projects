package hitoriSolver;

import java.util.ArrayList;
import java.util.Stack;

public class SolverFC {
	
	ArrayList<Cell> copyOfSolution = new ArrayList<Cell>();
	Stack<ArrayList<Cell>> foundSolutions = new Stack<ArrayList<Cell>>();

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
	
	boolean checkForCompletedChecking(int row, int column, int size, ArrayList<Cell> temporalSolution){
		for(int i = 0; i < temporalSolution.size(); i++){
			if(temporalSolution.get(i).checksPerformed[0] == true
				&& temporalSolution.get(i).checksPerformed[1] == true)
				return true;
		}
		return false;
	}
	
	ArrayList<Cell> forwardChecking(ArrayList<Cell> solution, int row, int column, int size, ArrayList<Cell> copiedSolution, boolean shouldCross){
		int startingIndex = (row * size) + column;
		solution.get(startingIndex).crossed = shouldCross;
		for(int i = startingIndex + 1; i < size * size; i++){
			if(this.checkForCompletedChecking(row, column, size, copiedSolution)){
				int newColumn = i % size;
				int newRow = i / size;
				for(int j = 0; j < solution.get(i).checksPerformed.length; j++){
					if(this.checkForCompletedChecking(newRow, newColumn, size, copiedSolution)){
						if(!this.checkConstraintsEachCell(solution, newRow, newColumn, size, shouldCross)){
							copiedSolution.get(i).checksPerformed[j] = true;
						}
					}
					else{
						this.foundSolutions.pop();
						copiedSolution = this.foundSolutions.peek();
					}
				}
			}
		}
		solution.get(startingIndex).crossed = false;
		return copiedSolution;
	}
	
	boolean backTracking(ArrayList<Cell> solution, int row, int column, int size, ArrayList<Cell> temporalSolution){
		if(!this.checkForCompletion(solution, size)){
			boolean shouldCross;
			for(int i = 0; i < 2; i++){
				if(solution.get((row * size) + column).checksPerformed[i] == true)
					continue;
				else if (i == 0)
					shouldCross = true;
				else
					shouldCross = false;
				if(this.checkConstraintsEachCell(solution, row, column, size, shouldCross)){
					if(!this.checkForCompletedChecking(row, column, size, temporalSolution)){
						ArrayList<Cell> copiedSolution = new ArrayList<Cell>(temporalSolution.size());
						copiedSolution = this.forwardChecking(solution, row, column, size, copiedSolution, shouldCross);
						this.foundSolutions.push(copiedSolution);
						solution.get((row * size) + column).setCrossed(shouldCross);
						solution.get((row * size) + column).setChecked(true);
						if (row != size - 1 && column != size - 1){
							if(this.backTracking(solution, row, column+1, size, temporalSolution))
								return true;
							else
								solution.get((row * size) + column).setChecked(false);
						}
						else if (row != size-1 && column == size-1){
							if(this.backTracking(solution, row+1, 0, size, temporalSolution))
								return true;
							else
								solution.get((row * size) + column).setChecked(false);
						}
						else if (row == size-1 && column != size-1){
							if(this.backTracking(solution, row, column+1, size, temporalSolution))
								return true;
							else
								solution.get((row * size) + column).setChecked(false);
						}
						else if (row == size-1 && column == size-1){
							if(this.backTracking(solution, row, column, size, temporalSolution))
								return true;
							else
								solution.get((row * size) + column).setChecked(false);
						}
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
		
		int size = 6; // 4, 6, 8, 10
		int hitoriIndex = 1; // 0 - size 4; 1,2 - size 6; 3,4, - size 8; 5 - size 10
		SolverFC hitoriSolver = new SolverFC();
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
		boolean response = hitoriSolver.backTracking(hitori.solution, 0, 0, size, hitoriSolver.copyOfSolution);
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
