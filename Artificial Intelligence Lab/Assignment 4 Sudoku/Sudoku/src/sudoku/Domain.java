package sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Domain {

    int i, j;
    ArrayList<Integer> domains;

    public Domain(int a, int b) {
        // i and j signifies the variable which is the unassigned cell and domains represents the domains.
        domains = new ArrayList<Integer>();
        i = a;
        j = b;
        for (int k = 1; k < 10; k++) {
            domains.add(k);
        }
    }

    public void propagate(int[][] a) {
        //this function does the constraint propagation.
    }
}
