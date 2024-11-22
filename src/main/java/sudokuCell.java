import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

public class sudokuCell {
    /* This class contains the information concerning a single cell in the sudoku grid.
     * If the cell is not filled, it maintains a list of possible numbers that could be 
     * entered in the cell (potential numbers). The class does not keep track of the
     * location of the cell in the grid.
    */
    private ArrayList<Integer> potentialNumbers; 
    private int filledNumber;
    private Boolean isFilled;
    private Boolean isHidden;

    public sudokuCell(int number) {
        if (number != 0) {
            fillCell(number);
            potentialNumbers = new ArrayList<Integer>(Arrays.asList(new Integer[]{number}));
        } else {
            potentialNumbers = new ArrayList<Integer>(Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9}));

            fillCell(0);
            isFilled = false;
        }
        isHidden = false;
    }

    public String toString() {
        if (isFilled) {
            return filledNumber + "";
        } else {
            return " ";
        }
    }

    public void fillCell(int number) {
        filledNumber = number;
        isFilled = true;
    }

    public void checkPotentialsForFill() {
        if (potentialNumbers.size() == 1) {
            fillCell(potentialNumbers.get(0));
        }
    }

    public int potentialsSize() {
        return potentialNumbers.size();
    }

    public Boolean potentialNumbersContains (int number) {
        return potentialNumbers.contains(number);
    }

    public void removeFromPotentials(int number) {
        if (potentialNumbersContains(number)) {
            potentialNumbers.removeAll(Arrays.asList(number));
        }
    }

    public void hide() {
        isHidden = true;
    }

    public void reveal() {
        isHidden = false;
    }

    // The methods below all return private variables.
    public ArrayList<Integer> givePotentials() {
        return potentialNumbers;
    }

    public int cellNum() {
        return filledNumber;
    }

    public Boolean isFilled() {
        return isFilled;
    }

    public Boolean isHidden() {
        return isHidden;
    }
}