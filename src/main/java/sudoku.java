import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sudoku {
    public static void main(String []args) {
        sudokuArray sudoku;
        String sudokuString;
        if (args.length == 0) {
            sudokuString = "070005290000006081006800003020903000080200910010000000000070860500002070064500020";
        } else {
            sudokuString = args[0];
        }
        if (checkSudokuString(sudokuString)) {
            sudoku = new sudokuArray(sudokuString);
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
        sudokuArray solvedSudoku = sudoku.recursiveReduce();
        double stop = System.nanoTime();
        double solveTime = (stop - start)/1000000000;
        
        if (solvedSudoku.isSolved()) {
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



