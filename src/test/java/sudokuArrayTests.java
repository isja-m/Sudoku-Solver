import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.google.common.primitives.Ints;

import java.util.*;

public class sudokuArrayTests {
    static sudokuArray sudoku;
    static sudokuArray basicReducedSudoku;
    static sudokuArray recursiveReducedSudoku;
    static String sudokuString = "100400500008062000764038900005600040083000701000913005000040000300000058200001070";
    static String difficultSudokuString = "070005290000006081006800003020903000080200910010000000000070860500002070064500020";

    @BeforeAll
    static void setupAll() {
        basicReducedSudoku = new sudokuArray(sudokuString);
        recursiveReducedSudoku = new sudokuArray(difficultSudokuString).recursiveReduce();
        basicReducedSudoku.basicReduce();
    }

    @BeforeEach
    void setupEach() {
        sudoku = new sudokuArray(sudokuString);
    }

    @ParameterizedTest
    @CsvSource({"0,0,1", "2,6,9", "3,7,4", "4,1,8"})
    void correctlyFilled(int row, int col, int value) {
        sudokuCell[][] cells = sudoku.giveCellArray();
        assertEquals(value, cells[row][col].cellNum());
    }

    @ParameterizedTest
    @CsvSource({"0,1", "3,4", "1,7", "6,2"})
    void correctlyUnfilled(int row, int col) {
        sudokuCell[][] cells = sudoku.giveCellArray();
        assertFalse(cells[row][col].isFilled());
    }

    @ParameterizedTest
    @CsvSource({"0,0", "2,6", "3,7", "4,1"})
    void filledInReducedList(int row, int col) {
        sudoku.buildReducedLists();
        Boolean contained = false;
        for (int[] entry : sudoku.giveNonReducedCells()) {
            if (Arrays.equals(entry, new int[] {row,col})) {
                contained = true;
            }
        }
        assertTrue(contained);
    }

    @ParameterizedTest
    @CsvSource({"0,1", "3,4", "1,7", "6,2"})
    void unfilledReducedList(int row, int col) {
        sudoku.buildReducedLists();
        Boolean contained = false;
        for (int[] entry : sudoku.giveNonReducedUnfilledCells()) {
            if (Arrays.equals(entry, new int[] {row,col})) {
                contained = true;
            }
        }
        assertTrue(contained);
    }

    @Test
    void checkInvalidSudoku() {
        sudokuArray invalidSudoku = new sudokuArray("100401500008062000764038900005600040083000701000913005000040000300000058200001070");
        assertFalse(invalidSudoku.checkValid());
    }

    @Test
    void checkValidSudoku() {
        sudokuArray invalidSudoku = new sudokuArray(sudokuString);
        assertTrue(invalidSudoku.checkValid());
    }

    @Test
    void rowUpdate() {
        sudoku.updateRowPotentials(3, 3);
        assertArrayEquals(new int[] {1,2,3,4,5,7,8,9}, Ints.toArray(sudoku.giveCellArray()[3][6].givePotentials()));
    }

    @Test
    void colUpdate() {
        sudoku.updateColPotentials(3, 3);
        assertArrayEquals(new int[] {1,2,3,4,5,7,8,9}, Ints.toArray(sudoku.giveCellArray()[1][3].givePotentials()));
    }

    @Test
    void blockUpdate() {
        sudoku.updateBlockPotentials(3, 3);
        assertArrayEquals(new int[] {1,2,3,4,5,7,8,9}, Ints.toArray(sudoku.giveCellArray()[4][4].givePotentials()));
    }

    @ParameterizedTest
    @CsvSource({"0,1,3", "2,7,1", "4,7,9", "4,0,6"})
    void basicReduceWorks(int row, int col, int value) {
        sudokuCell[][] cells = basicReducedSudoku.giveCellArray();
        assertEquals(value, cells[row][col].cellNum());
    }

    @ParameterizedTest
    @CsvSource({"0,0,8", "2,7,4", "3,7,5", "3,0,4"})
    void recursiveReduceWorks(int row, int col, int value) {
        sudokuCell[][] cells = recursiveReducedSudoku.giveCellArray();
        assertEquals(value, cells[row][col].cellNum());
    }
}
