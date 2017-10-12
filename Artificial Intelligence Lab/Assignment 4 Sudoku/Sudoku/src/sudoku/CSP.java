package sudoku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CSP {

    ArrayList<Domain> DomainList;
    Board sudokuBoard;
    int recursiveCount;
    int valueSelection,variableSelection;
    
    
    public CSP(ArrayList<Domain> a, Board b) {
        DomainList = a;
        sudokuBoard = b;
        recursiveCount = valueSelection=variableSelection = 0;
    }

    public Board solve() {
        ArrayList<Domain> domainsList = (ArrayList<Domain>) DomainList.clone();
        Board board2 = new Board(sudokuBoard.getWholeBoard());
        return BackTrackSolve(domainsList, board2);
    }

    public boolean isSolved(Board board) {
        int[][] a = board.getWholeBoard();
        boolean check = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (a[i][j] == 0) {
                    check = false;
                }
            }
        }
        return check;
    }

    private Board BackTrackSolve(ArrayList<Domain> domainsList, Board board2) {
        // this is the main function that does the backtrack+heuristic for variable selection and value selection.
        return null;
    }

    private boolean isConsistent(Board board2) {
        int[][] a = board2.getWholeBoard();
        RowConstraint con1 = new RowConstraint();
        ColumnConstraint con2 = new ColumnConstraint();
        SquareConstraint con3 = new SquareConstraint();
        boolean check = true;
        if (con1.isSatisfied(a) == false) {
            check = false;
        }
        if (con2.isSatisfied(a) == false) {
            check = false;
        }
        if (con3.isSatisfied(a) == false) {
            check = false;
        }
        return check;
    }

}
