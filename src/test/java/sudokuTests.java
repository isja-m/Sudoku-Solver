import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.*;

public class sudokuTests {
    @Test
    void wrongLengthString() {
        assertFalse(sudoku.checkSudokuString("12390"));
    }
    
    @Test
    void nonnumericalString() {
        assertFalse(sudoku.checkSudokuString("0008200905000000003080400071000000400064a2503000090010093004000004035200000700900"));
    }

    @Test
    void correctString() {
        assertTrue(sudoku.checkSudokuString("000820090500000000308040007100000040006402503000090010093004000004035200000700900"));
    }
}
