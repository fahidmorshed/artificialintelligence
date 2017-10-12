/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mymancala;

/**
 *
 * @author zak
 */
public class HumanMancalaAgent extends Agent{

    public HumanMancalaAgent(String name) {
        super(name);
    }

    @Override
    public void makeMove(Game game) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        MancalaGame mcg=(MancalaGame)game;
        int row,col;
        while(true)
        {while(mcg.isButtonPressed==false);
        System.out.println("YES");
        mcg.isButtonPressed=false;
        System.out.println(mcg.whichButtonPressed);
        int rowcol=Integer.parseInt(mcg.whichButtonPressed);
         row=rowcol/10;
        col=rowcol%10;
        if(mcg.turn==row)break;
        }
        mcg.setConfigAgain(row, col);
        mcg.gameGUI.setBoard();
        
        if(mcg.againToMove==false)mcg.turn=(mcg.turn+1)%2;
    }
    
}
