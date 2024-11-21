import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class sudokuArray {
    /* This class contains the information and logic regarding the sudoku grid as a whole.
     * Local operations on sudoku cells are handled by the 'sudokuCell' class.
     * 'Potentials' refers to the list of numbers a cell could potentially be filled with.
    */
    private sudokuCell[][] cellArray;
    private ArrayList<int[]> nonReducedCells = new ArrayList<int[]>(); // Filled cells that need to be checked by the reduction algorithm.
    private ArrayList<int[]> nonReducedUnfilledCells = new ArrayList<int[]>(); // Same, but for unfilled cells.
    private Boolean isValid;

    public sudokuArray(String sudokuString) {
        cellArray = new sudokuCell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cellArray[i][j] = new sudokuCell((int)sudokuString.charAt(9*i + j) - '0');
            }
        }

        buildReducedLists();
        isValid = checkValid();
    }

    public sudokuArray(sudokuCell[][] sudokuArray) {
        cellArray = sudokuArray;
        buildReducedLists();
        isValid = checkValid();
    }

    public void buildReducedLists() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cellArray[i][j].isFilled()) {
                    nonReducedCells.add(new int[] {i,j});
                } else {
                    nonReducedUnfilledCells.add(new int[] {i,j});
                }
            }
        }
    }

    public Boolean checkValid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cellArray[i][j].hide();
                if(!checkValidCell(i, j)) {return false;}
                cellArray[i][j].reveal();
            }
        }
        return true;
    }

    public Boolean checkValidCell(int row, int col) {
        int number = cellArray[row][col].cellNum();
        if (cellArray[row][col].isFilled()
            && (numIsInRow(number, row) || numIsInCol(number, col) || numIsInBlock(number, row, col))) {
            return false;
        }
        return true;
    }
    
    public Boolean numIsInRow(int number, int row) {
        for (int i = 0; i < 9; i++) {
            if (cellArray[row][i].isFilled() && !(cellArray[row][i].isHidden())
                && cellArray[row][i].cellNum() == number) {
                    return true;
            }
        }
        return false;
    }
    
    public Boolean numIsInCol(int number, int col) {
        for (int i = 0; i < 9; i++) {
            if (cellArray[i][col].isFilled() && !(cellArray[i][col].isHidden())
                && cellArray[i][col].cellNum() == number) {
                    return true;
            }
        }
        return false;
    }
    
    public Boolean numIsInBlock(int number, int row, int col) {
        int blockRow = row/3;
        int blockCol = col/3;
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (cellArray[3*blockRow + i][3*blockCol + j].isFilled() && !(cellArray[row][col].isHidden())
                    && cellArray[3*blockRow + i][3*blockCol + j].cellNum() == number) {
                        return true;
                }
            }
        }
        return false;
    }

    public void printSudoku() {
        for  (int i = 0; i < 9; i++) {            
            if (i % 3 == 0) {
                System.out.println("###################");
            } else {
                System.out.println("-------------------");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.printf("#");
                } else {
                    System.out.printf("|");
                }
                System.out.printf("" + cellArray[i][j].toString());
            }
            System.out.println("#");
        }
        System.out.println("###################");
    }

    public void removeFromPotentials(int row, int col, int number) {
        cellArray[row][col].removeFromPotentials(number);
        if (cellArray[row][col].potentialsSize() == 0 && !cellArray[row][col].isFilled()) {
            isValid = false;
        }
        if (!cellArray[row][col].isFilled()) {
            nonReducedUnfilledCells.add(new int[] {row,col});
        }
    }

    // Three methods that remove the number in the indicated cell from all related cells.
    public void updateRowPotentials(int row, int col) {
        cellArray[row][col].hide();
        for (int i = 0; i < 9; i++) {
            removeFromPotentials(row, i, cellArray[row][col].cellNum());
        }
        cellArray[row][col].reveal();
    }

    public void updateColPotentials(int row, int col) {
        cellArray[row][col].hide();
        for (int i = 0; i < 9; i++) {
            removeFromPotentials(i, col, cellArray[row][col].cellNum());
        }
        cellArray[row][col].reveal();
    }
    
    public void updateBlockPotentials(int row, int col) {
        cellArray[row][col].hide();
        int blockRow = row/3;
        int blockCol = col/3;
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                removeFromPotentials(3*blockRow + i, 3*blockCol + j, cellArray[row][col].cellNum());
            }
        }
        cellArray[row][col].reveal();
    }

    public void basicReduce() {
        /* A basic reduction algorithm that exhaustively appliesthe following reduction rules:
         * - If a filled cell has not been checked, remove its entry from the lists of all cells
         *   in the same row, collumn or block.
         * - If an unfilled cell has only one number left in its list, fill the cell with that number.
        */
        while (nonReducedCells.size() > 0 || nonReducedUnfilledCells.size() > 0) {
            while (nonReducedCells.size() > 0 ) {
                int row = nonReducedCells.get(0)[0];
                int col = nonReducedCells.get(0)[1];
                updateRowPotentials(row, col);
                updateColPotentials(row, col);
                updateBlockPotentials(row, col);
                nonReducedCells.remove(nonReducedCells.get(0));
            }
            while (nonReducedUnfilledCells.size() > 0 && nonReducedCells.size() == 0 ) {
                int row = nonReducedUnfilledCells.get(0)[0];
                int col = nonReducedUnfilledCells.get(0)[1];
                nonReducedUnfilledCells.remove(nonReducedUnfilledCells.get(0));
                cellArray[row][col].checkPotentialsForFill();
                if (cellArray[row][col].isFilled()) {
                    nonReducedCells.add(new int[] {row,col});
                }
            }
        }
    }
    
    public Boolean recursiveReduce(sudokuCell[][] recurseArray) {
        /* A recursive branching algoirthm that fist applies basicReduce and then branches on the
         * cell with the smallest list. For each number in that list branch on the sudoku with that
         * number, entered in that cell, and recurse. The boolean returned indicate whether a solution
         * has been found.
        */
        sudokuArray recursiveSudoku = new sudokuArray(recurseArray);
        recursiveSudoku.basicReduce();
        if (recursiveSudoku.isSolved()) {
            System.out.println("Solution found!");
            recursiveSudoku.printSudoku();
            return true;
        }
        if (!recursiveSudoku.isValid()) {
            return false;
        }
        if (!recursiveSudoku.isSolved()) {
            int[] location = recursiveSudoku.findSmallestPotential();
            sudokuCell[][] cells = recursiveSudoku.giveCellArray();
            for (int number : cells[location[0]][location[1]].givePotentials()) {
                sudokuCell[][] newCells = new sudokuCell[9][9];
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        newCells[i][j] = new sudokuCell(cells[i][j].cellNum());
                    }
                }
                newCells[location[0]][location[1]] = new sudokuCell(number);
                Boolean solved = recursiveReduce(newCells);
                if (solved) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean recursiveReduce() {
        return recursiveReduce(cellArray);
    }

    // The following methods return some basic properties of the sudoku
    public Boolean isValid() {
        return isValid;
    }

    public sudokuCell[][] giveCellArray() {
        return cellArray;
    }

    public Boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!cellArray[i][j].isFilled()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[] findSmallestPotential() {
        // Return location of a/the cell with the smallest list of potential entries.
        int smallestPotential = 10;
        int[] location = {-1,-1};
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int size = cellArray[i][j].potentialsSize();
                if(!cellArray[i][j].isFilled() && size < smallestPotential) {
                    smallestPotential = size;
                    location[0] = i;
                    location[1] = j;
                }
            }
        }
        return location;
    }
}