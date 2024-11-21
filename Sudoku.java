import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class Sudoku {
    public static void main(String []args) {
        sudokuArray sudoku;
        if (checkSudokuString(args[0])) {
            sudoku = new sudokuArray(args[0]);
        } else {
            return;
        }
        
        System.out.println("Initial state:");
        sudoku.printSudoku();
        
        if (sudoku.isValid()) {
            System.out.println("This sudoku seems valid, lets try to solve it.");
        } else {
            System.out.println("This is not a valid sudoku!");
            return;
        }

        double start = System.nanoTime();
        Boolean solved = sudoku.recursiveReduce();
        double stop = System.nanoTime();
        double solveTime = (stop - start)/1000000000;
        
        if (solved) {
            System.out.printf("Solved in %f.3 seconds.%n", solveTime);
        } else {
            System.out.println("No solution found, because the sudoku is not feasible.");
        }        
    }

    public static Boolean checkSudokuString(String sudokuString) {
        Pattern numPat = Pattern.compile("[^0-9]");
        Matcher numMatch = numPat.matcher(sudokuString);
        if (numMatch.find()) {
            System.out.println("Error: The sudoku string contains a non-numerical character.");
            return false;
        } else if (sudokuString.length() != 81) {
            System.out.println("Error: The sudoku string is not of length 81.");
            return false;
        } else {
            return true;
        }
    }
}



