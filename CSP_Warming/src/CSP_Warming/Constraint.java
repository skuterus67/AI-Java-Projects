package CSP_Warming;

import java.util.Scanner;

public class Constraint {
	
	static String subAlphabet;
	static String alphabet="abcdefghijklmnopqrstuvwxyz";
	static int breakFlag=0;
	
	public static boolean checkRowForLetterNeighbour(char[] row, char letter, int row_letter_index){
		if (row_letter_index!=0)
		{
			if (row[row_letter_index-1]==letter){
				return false;
			}
			else
				return true;
		}
		else
			return true;
	}
	
	public static boolean checkColumnForLetterNeighbour(char[][] mosaic, char letter, int col_letter_index, int row_index){
		if (row_index==0){
			return true;
		}
		else {
			char[] row= mosaic[row_index-1];
			if (row[col_letter_index]==letter){
				return false;
			}
			else
				return true;
		}
	}
	
	public static void getLetterNumber(){
		int letter_no=0;
		Scanner scanner = new Scanner (System.in);
		System.out.println("How many different letters would you like to have in your mosaic? \n");
		letter_no = Integer.parseInt(scanner.next());
		scanner.close();
		subAlphabet=alphabet.substring(0, letter_no);
	}
	
	public static boolean checkLetter(char[][] mosaic, char letter, int i, int j){ //j is number of row, i is number of column
		if (checkRowForLetterNeighbour(mosaic[j], letter, i)){
			if (checkColumnForLetterNeighbour(mosaic, letter, i, j)){
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	
	public static char[][] populateMosaic(char[][] mosaic, int length, int width){
		getLetterNumber();
		int c=0, i, j;
		char letter=subAlphabet.charAt(c);
		iterator: {
			for (j=0;j<width;j++){
				for (i=0;i<length;i++){
					for (c=0; c<subAlphabet.length();c++){
						letter=subAlphabet.charAt(c);
						if (checkLetter(mosaic, letter, i, j)){
							mosaic[j][i]=letter;
							break;
						}
					}
					if (mosaic[j][i]==0){
						System.out.println("The mosaic cannot be done! \n");
						breakFlag=1;
						break iterator;
					}
				}
			}
		}
		return mosaic;
	}

}
