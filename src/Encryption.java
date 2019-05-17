import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
/**
* This is the Encryption 
*
* @author Amelia Tran
* @version 4/09/2019
*/

public class Encryption {
	// declare global variables
	private static String[][] alphabet= {
			{"A", "B", "C", "D", "E"},
			{"F", "G", "H", "I", "J"},
			{"K", "L", "M", "N", "O"},
			{"P", "Q/Z", "R", "S", "T"},
			{"U", "V", "W", "X", "Y"},
		};
	private static final int MAX_ROW = 5;
	private static final int MAX_COL = 5;
	
	/** main method to scan the files 
	* @param args -main method argument
	*/
	public static void main (String[] args) throws FileNotFoundException {				

		// Open a scanner to get input 
		Scanner in = new Scanner(System.in);
		System.out.println("Please enter your message: ");
		// read the whole message
		String reader = in.nextLine();
		String mes = "";

		// traverse through the message
		for (int i = 0; i < reader.length() ; i++) {
			char letter = reader.charAt(i);
			// ignore the non-letters and convert
			// the lowercase to uppercase letters
			if (Character.isLetter(letter)) {		
				if(Character.isLowerCase(letter))
					mes = mes + Character.toUpperCase(letter);
			 	else
					mes = mes + String.valueOf(letter);
			} else if (Character.isWhitespace(letter)) 
				mes = mes + " ";
		}

		// return the polished message 
		System.out.println(mes);
		String mesNum = "";

		// encrypt the message
		for (int indexMes = 0; indexMes < mes.length() ; indexMes++) {
			char mesLetter = mes.charAt(indexMes);
			if (Character.isWhitespace(mesLetter))
				mesNum = mesNum + " ";
			else
				mesNum = mesNum + encodeLetter(Character.toString(mesLetter));
		}

		// print the encryption of the message
		System.out.println(mesNum);
		
		String keyword ="";
		String test = "";
		boolean correctKeyWord = false;
		// while loop until the keyword is entered correctly
		while (!correctKeyWord) {
			System.out.println("Please enter your key word: ");
			keyword = in.nextLine();
			
			//check if the key word has duplicates
			if (uniqueChar(keyword) && onlyLetter(keyword)) {
				for (int i = 0; i < keyword.length() ; i++) {
					char let = keyword.charAt(i);	
					// convert lowercase to uppercase letters if needed
					if (Character.isLowerCase(let)) 
						test = test + Character.toUpperCase(let);
					else 
						test = test + String.valueOf(let);
					// set the boolean to true to break the while loop
				}
			correctKeyWord = true;
			}
		} 

		int count = 0;
		// count the blank space in the encryption of the message
		for (int i=0; i<mesNum.length(); i++) 
			if (Character.isWhitespace(mesNum.charAt(i))) 
				count++;
		
		System.out.println(test);
		// the row of the new 2D array grid 
		// +2 because the 1st row is for the keyword
		int totalRow = (int) (mesNum.length()-count)/keyword.length() + 2;
		int index = 0;
		
		// create a 2D array grid
		String[][] grid = new String[totalRow][keyword.length()];
		// put the keyword letters in the 1st row of the grid
		for (int i = 0; i<keyword.length(); i++) {
			grid[0][i] = String.valueOf(test.charAt(i));
			System.out.print(grid[0][i] + " ");
		}

		System.out.println();

		// put the encrypted message (the numbers) into the grid
		for (int i = 1; i < totalRow; i++) {
			for (int j = 0; j < keyword.length(); j++) {
				if (index < mesNum.length()) 
					// skip any blank space
					if (Character.isWhitespace(mesNum.charAt(index))) 
						index++;
				
				if (index < mesNum.length()) { 
					grid[i][j] = String.valueOf(mesNum.charAt(index));
					index++;		
				} 

				// if the cell is null, convert it to blank space
				if (grid[i][j] == null) 
					grid[i][j] = " ";
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		
		int testNum = 0;
		String temp = "";
		// rearrange the grid so that the letters of the keyword are in order
		for (int i = 0; i < keyword.length()-1; i++) {
			for (int j=i+1; j<keyword.length();j++) {
				testNum = grid[0][i].compareTo( grid[0][j]);
				if (testNum>=0) {
					temp = grid[0][j];
					grid[0][j] = grid[0][i];
					grid[0][i] = temp;
					
					// shift the corresponding columns of digits along with 
					// the letters
					for (int row =1; row < keyword.length(); row++) {
						temp = grid[row][j];
						grid[row][j] = grid[row][i];
						grid[row][i] = temp;
					}
				}
			}
		}
		System.out.println();

		// print the updated grid
		for (int i = 0; i < totalRow; i++) {
			for (int j = 0; j < keyword.length(); j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		
		String finalResult = "";
		// write the encrypted message as a sequence of digits, column by column
		for (int j = 0; j < keyword.length(); j++) {
			for (int i = 1; i < totalRow; i++) {
				finalResult = String.valueOf(grid[i][j]);
				if ((i==totalRow-1) && (!grid[i][j].equals(" "))){
					finalResult = finalResult + " ";
				} 
				System.out.print(finalResult);
			}	
		}	
		System.out.println();	
	}


	/** helper method to encode letter
	* @param letter - the string
	* @return String the row and col index of the letter
	*/
	public static String encodeLetter(String letter) {
		String result = "";
		// traverse the alphabet
        for (int row = 0; row < MAX_ROW; row++) 
            for (int col = 0; col < MAX_COL; col++) 
            	// if the letters match, return the index row and index col
            	if (alphabet[row][col].equals(letter)) 
            		// convert the int indexes to string type
            		result = String.valueOf(row) + String.valueOf(col);
		return result;
	}

	/** method to check if the string has duplicates
	* @param str - the string
	* @return boolean true/false
	*/
	public static boolean uniqueChar(String str) {
        // If at any time we encounter 2 same 
        // characters, return false 
        for (int i = 0; i < str.length(); i++) 
            for (int j = i + 1; j < str.length(); j++) 
            	// if there is a duplicate in the string
                if (str.charAt(i) == str.charAt(j)) 
                    return false; 
        // Otherwise return true 
        return true; 
    } 

    /** method to check if the string has non-letters
	* @param str - the string
	* @return boolean true/false
	*/

	public static boolean onlyLetter(String str) {
		for (int i = 0; i < str.length(); i++){ 
			char let = str.charAt(i);	 
			if (!Character.isLetter(let)) {
				return false;
			} 
		}
		return true;
	}
}


