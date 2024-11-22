import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.common.primitives.Ints;

public class sudokuCellTests {

    sudokuCell cell1;
    sudokuCell cell2;

    @BeforeEach
    void setupEach() {
        cell1 = new sudokuCell(0);
        cell2 = new sudokuCell(0);
    }

    @Test
    void uniqueCells() {
        assertNotEquals(cell1, cell2);
    }

    @Test
    void correctPrint() {
        cell1.fillCell(1);
        assertEquals("1 ", cell1.toString() + cell2.toString());
    }

    @Test
    void refillCell() {
        cell1.fillCell(1);
        cell1.fillCell(2);
        assertEquals(2, cell1.cellNum());
    }

    @Test
    void removePotentialInList() {
        cell1.removeFromPotentials(9);
        assertArrayEquals(new int[] {1,2,3,4,5,6,7,8}, Ints.toArray(cell1.givePotentials()));
    }

    @Test
    void removePotentialNotInList() {
        cell1.removeFromPotentials(9);
        cell1.removeFromPotentials(9);
        assertArrayEquals(new int[] {1,2,3,4,5,6,7,8}, Ints.toArray(cell1.givePotentials()));
    }

    @Test
    void checkTwoPotentials() {
        for (int i = 1; i < 8; i++) {
            cell1.removeFromPotentials(i);
        }
        cell1.checkPotentialsForFill();
        assertFalse(cell1.isFilled());
    }

    @Test
    void checkOnePotential() {
        for (int i = 1; i < 9; i++) {
            cell1.removeFromPotentials(i);
        }
        cell1.checkPotentialsForFill();
        assertTrue(cell1.isFilled());
    }

    @Test
    void checkNoPotential() {
        for (int i = 1; i < 10; i++) {
            cell1.removeFromPotentials(i);
        }
        cell1.checkPotentialsForFill();
        assertFalse(cell1.isFilled());
    }
}
