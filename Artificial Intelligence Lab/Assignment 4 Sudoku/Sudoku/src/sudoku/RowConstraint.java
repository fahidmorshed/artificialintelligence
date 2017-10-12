package sudoku;
import java.util.HashSet;

public class RowConstraint {

    private static final int BOARD_SIZE = 9;
    private int[][] boardArray;

    public RowConstraint() {
        boardArray = new int[9][9];
    }

    public boolean isSatisfied(int[][] a) {
        // Recreate the board as 2D Array
        int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = a[i][j];
            }
        }

        HashSet<Integer> numberChecker = new HashSet<Integer>();

        // Iterate through each row
        for (int i = 0; i < BOARD_SIZE; i++) {

            // Iterate through each column
            for (int j = 0; j < BOARD_SIZE; j++) {
                int curr = board[i][j];

                // Check if val is set
                if (curr != 0) {
                    // If it is contained return false.
                    if (numberChecker.contains(curr)) {
                        return false;
                    } // If it is not contained, add it.
                    else {
                        numberChecker.add(curr);
                    }
                }
            }

            numberChecker = new HashSet<Integer>();
        }
        return true;
    }
}
