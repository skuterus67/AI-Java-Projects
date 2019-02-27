package CSP_Warming;

import java.util.Scanner;

public class Mosaic {
	
	static int width=0;
	static int length=0;
	static String[] divider;
	static String[] numerator;
	
	public static void getMosaicSize(){
		Scanner scanner = new Scanner (System.in);
		System.out.println("Give length of the mosaic (row size): \n");
		length = Integer.parseInt(scanner.next());
		System.out.println("Give width of the mosaic (column size): \n");
		width = Integer.parseInt(scanner.next());
		scanner.close();
	}
	
	public static void drawDivider(int length){
		for (int i=0;i<length;i++){
			divider[i]="_";
		}
	}
	
	public static void drawNumerator(int length){
		for (int i=0;i<length;i++){
			numerator[i]=Integer.toString(i+1);
		}
	}
	
	public static char[][] createMosaic(){
		getMosaicSize();
		char[][] mosaic= new char[width][length];
		return mosaic;
	}
	
	public static void drawMosaic(){
		char[][] mosaic =createMosaic();
		drawNumerator(length);
		drawDivider(length);
		mosaic = Constraint.populateMosaic(mosaic,length, width);
		if (Constraint.breakFlag==1){
			return;
		}
		System.out.println(numerator + "\n");
		for (int i=0; i<width; i++){
			for (int j=0; j<length; j++){
				System.out.println(mosaic[i][j] + "|");
			}
			System.out.println("\n" + divider + "\n");
		}
	}
}
