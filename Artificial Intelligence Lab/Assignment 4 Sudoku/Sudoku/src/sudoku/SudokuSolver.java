/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SudokuSolver {

    public static void main(String[] args) {
       
        long startTime = System.currentTimeMillis();
        File puzzle = new File("30_easy.txt");
     //   File puzzle = new File("35_easy.txt");
     //   File puzzle = new File("49_medium.txt");
     //   File puzzle = new File("50_medium.txt");
     //   File puzzle = new File("51_medium.txt");
     //   File puzzle = new File("52_medium.txt");
     //   File puzzle = new File("75_medium.txt");
     //   File puzzle = new File("76_medium.txt");
     //   File puzzle = new File("77_hard.txt");
     //   File puzzle = new File("78_hard.txt");
     //   File puzzle = new File("79_hard.txt");
      //  File puzzle = new File("80_hard.txt");
      //  File puzzle = new File("impossible_puzzle.txt");
        
        Scanner scan = null;
        try {
            scan = new Scanner(puzzle);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i = 0, j = 0;
        int[][] board = new int[9][9];

        while (scan.hasNext()) {
            int row = scan.nextInt();
            board[i][j] = row;
            j = (j + 1) % 9;
            if (j == 0) {
                i = (i + 1) % 9;
            }
        }
        Board gameBoard = new Board(board);
        ArrayList<Domain> DomainList = new ArrayList<Domain>();
      
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    DomainList.add(new Domain(i, j));
                }
            }
        }
        for (int k = 0; k < DomainList.size(); k++) {
            DomainList.get(k).propagate(board);
        }
        
        /*
        for (int k = 0; k < DomainList.size(); k++) {
            System.out.println(DomainList.get(k).i+" "+DomainList.get(k).j+" "+DomainList.get(k).domains);
        }
        System.out.println("------------------"); 
        */
        CSP csp = new CSP(DomainList,gameBoard);
        Board result = csp.solve();
        Object b = result.getWholeBoard();
        int[][] c;
        if (b==null){
            System.out.println("NULL");
            return;
        }
        else{
            c=(int[][])b;
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                System.out.print(c[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("Variable Selection count "+csp.variableSelection
        +"\nValue Selection count "+csp.valueSelection);
        System.out.println("Time in ms "+totalTime);
    }
}
