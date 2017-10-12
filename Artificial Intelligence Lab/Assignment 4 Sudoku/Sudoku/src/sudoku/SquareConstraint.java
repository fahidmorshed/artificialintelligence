package sudoku;
import java.util.HashSet;


public class SquareConstraint {

    private static final int BOARD_SIZE = 9;
    private static final int SQUARE_SIZE = 3;
    private int[][] boardArray;

    public SquareConstraint() {
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

        HashSet<Integer> squareChecker = new HashSet<Integer>();
        for (int k = 0; k < BOARD_SIZE; k += SQUARE_SIZE) {
            for (int l = 0; l < BOARD_SIZE; l += SQUARE_SIZE) {
                for (int i = k; i < k + SQUARE_SIZE; i++) {
                    for (int j = l; j < l + SQUARE_SIZE; j++) {

                        int curr = board[i][j];

                        // Check if val is set
                        if (curr != 0) {
                            // If it is contained return false.
                            if (squareChecker.contains(curr)) {
                                return false;
                            } // If it is not contained, add it.
                            else {
                                squareChecker.add(curr);
                            }
                        }
                    }
                }
                squareChecker = new HashSet<Integer>();
            }
        }

        return true;
    }
}
