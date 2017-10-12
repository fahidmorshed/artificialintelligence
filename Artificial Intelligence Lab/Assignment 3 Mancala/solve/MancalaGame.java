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
public class MancalaGame extends Game
{
    public int boardConfig[][];
    public boolean isButtonPressed;
    public String whichButtonPressed;
    public MancalaGUI gameGUI;
    public int turn;
    public boolean againToMove;
    
    public MancalaGame(Agent a, Agent b) {
        super(a, b);
        a.setRole(0);
        b.setRole(1);
        againToMove=false;
        isButtonPressed=false;
        
        initialize(false);
    }
    public void play()
	{
		updateMessage("Starting " + name + " between "+ agent[0].name+ " and "+ agent[1].name+".");
		//int turn = random.nextInt(2);
		turn=0;
		//System.out.println(agent[turn].name+ " makes the first move.");
		initialize(false);
		
		while(!isFinished())
		{
			updateMessage(agent[turn].name+ "'s turn. ");
			agent[turn].makeMove(this);
			showGameState();
			
		        
		}
		
		if (winner != null)
			updateMessage(winner.name+ " wins!!!");
		else	
			updateMessage("Game drawn!!");
		
	}
	
    @Override
    boolean isFinished() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int agent1finished=0,agent2finished=0;
        
        for(int i=0;i<6;i++)
        {
        agent1finished |=boardConfig[0][i];
        agent2finished |=boardConfig[1][i];
        
        }
        
        if( (agent1finished & agent2finished)==0)
        {
        winner=findWinner();
        return true;
        }
        
        
        return false;
    }

    @Override
    void initialize(boolean fromFile) {
        boardConfig=new int[2][7];  //[0][0:5]-->agent a pots,[0][6]-->agent a mancala ,same goes for agent b
        
        for(int i=0;i<7;i++)
        {
        if(i==6)
            boardConfig[0][i]=boardConfig[1][i]=0;
        else
            boardConfig[0][i]=boardConfig[1][i]=4;
        
        }
  
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void showGameState() {
        for(int i=0;i<7;i++)
            System.out.print(boardConfig[0][i]+" ");
        System.out.println("");
        for(int i=0;i<7;i++)
            System.out.print(boardConfig[1][i]+" ");
        System.out.println("");
         
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void updateMessage(String msg) {
        System.out.println(msg);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void setConfigAgain(int row,int col)
    {
    againToMove=false;    
    int current=boardConfig[row][col];
    boardConfig[row][col]=0;
    int lastpot=-1;
    if(current==0)return;
    for(int i=col+1;i<7;i++)
      {
      boardConfig[row][i]+=1;
      current-=1;
      lastpot=i;
      if(current==0)break;
      }
    if(current==0)
    {
        if(lastpot==6)againToMove=true;    
        else if(lastpot!=6 && boardConfig[row][lastpot]==1)
          {
          boardConfig[row][6]+=(boardConfig[row][lastpot]+boardConfig[(row+1)%2][5-lastpot]);
          boardConfig[row][lastpot]=boardConfig[(row+1)%2][5-lastpot]=0;
          
          }
        return;
    }
    for(int i=0;i<7;i++)
      {
      boardConfig[(row+1)%2][i]+=1;
      current-=1;
      if(current==0)break;
      }
    
    
    }
    
    public Agent findWinner()
    {
    if(boardConfig[0][6]==boardConfig[1][6])return null;
    return (boardConfig[0][6]>boardConfig[1][6])?agent[0]:agent[1];
    }
}
