import java.util.ArrayList;
import java.util.HashSet;

public class ColumnConstraint implements Constraint {
		private static final int BOARD_SIZE = 9;
		@Override
		public boolean isSatisfied(ArrayList<Variable> variables) {

			// Recreate the board as 2D Array
			int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
			for (Variable var : variables) {
				Tile t = (Tile) var;
				board[t.getRow()][t.getColumn()] = (int) t.value;
			}
			HashSet<Integer> numberChecker = new HashSet<Integer>();

			// Iterate through each col
			for (int j = 0; j < BOARD_SIZE; j++) {

				// Iterate through each row
				for (int i = 0; i < BOARD_SIZE; i++) {
					int curr = board[i][j];

					// Check if val is set
					if (curr != -1) {
						// If it is contained return false.
						if (numberChecker.contains(curr))
							return false;
						// If it is not contained, add it.
						else
							numberChecker.add(curr);
					}
				}

				numberChecker = new HashSet<Integer>();
			}

			return true;
		}
	}