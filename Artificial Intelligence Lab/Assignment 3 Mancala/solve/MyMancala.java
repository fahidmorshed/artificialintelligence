/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mymancala;

/**
 *
 * @author Fahid
 */
public class MyMancala {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
             MancalaGame MCGame;
        MCGame = (MancalaGame)Solver.configGame();
        MancalaGUI mancalaGUI=new MancalaGUI(MCGame);
        mancalaGUI.runMancalaGUI();
        System.out.println("kkk");
        MCGame.gameGUI=mancalaGUI;
       MCGame.play();
   
    }
    
}
