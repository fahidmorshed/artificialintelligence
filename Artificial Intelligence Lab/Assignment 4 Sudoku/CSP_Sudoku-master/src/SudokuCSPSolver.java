import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class SudokuCSPSolver {
	

	private static final int BOARD_SIZE = 9;
	private static final int SQUARE_SIZE = 9;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Read the puzzle file.
		File puzzle = new File("SudokuBoard");
		Scanner scan = null;
		try {
			scan = new Scanner(puzzle);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Iterate through the file to create the board/variables.
		ArrayList<Variable> board = new ArrayList<Variable>();
		int i = 0;
		while (scan.hasNext()) {
			String row = scan.nextLine();
			for (int j = 0; j < row.length(); j++) {
				if (row.charAt(j) == '*')
					board.add(new Tile(i, j));
				else
					board.add(new Tile(i, j, Character.getNumericValue(row
							.charAt(j))));
			}
			i++;
		}
		
		// Make the domain
		ArrayList<Object> d = new ArrayList<Object>();
		for(int x = 1; x <= BOARD_SIZE; x++)
			d.add(x);
		
		Domain domain = new Domain(d);
		
		Constraint rowchecker = new RowConstraint();
		Constraint columnchecker = new ColumnConstraint();
		Constraint squarechecker = new SquareConstraint();
		
		ArrayList<Constraint> constraints = new ArrayList<Constraint>();
		constraints.add(rowchecker);
		constraints.add(columnchecker);
		constraints.add(squarechecker);
		
		ConstraintSatisfactionProblem sudokuCSP = new ConstraintSatisfactionProblem(constraints, board, domain);
		
		BackTrackCSPSolver solver = new BackTrackCSPSolver();
		ArrayList<Variable> result = solver.backTrackSolve(sudokuCSP);
		
		printResult(result);

	}

	private static void printResult(ArrayList<Variable> result) {
		int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
		for (Variable var : result) {
			Tile t = (Tile) var;
			board[t.getRow()][t.getColumn()] = (int) t.value;
		}
		
		for(int i = 0; i < BOARD_SIZE; i++){
			for(int j = 0; j < BOARD_SIZE; j++){
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		
	}
}
