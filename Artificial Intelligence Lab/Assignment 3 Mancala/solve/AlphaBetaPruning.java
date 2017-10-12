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

public class AlphaBetaPruning {
    /*search function based on given array*/
    public static int findMove(int board[][])
    {
        int col=-1,v=-10000000;
        for(int i=0;i<6;i++)
          {
          int vprime=findMaxMove(board, -10000000, 10000000, 15);
          if(vprime>v)
           {
           v=vprime;
           col=i;
           }
          }
        return col;
    
    }
    
    
    public static int findMaxMove(int board[][],int alpha,int beta,int depth)
    {
    int v=-10000000;
    int vprime=findIfFinished(board);
    if(vprime!=0)return vprime;
    
    if(depth==0)
        return findUtilityValue(board);
    
    for(int i=0;i<6;i++)
      {
      int newBoard[][]=findNewState(board, i,0);
      boolean toMoveAgain=findIfAgainToMove(board, i, 0);
      if(toMoveAgain)
               v=Math.max(v, findMaxMove(newBoard, alpha, beta, depth-1));
      else 
          v=Math.min(v,findMinMove(newBoard, alpha, beta, depth-1));
      
      if(v>=beta)return v;
      alpha=Math.max(alpha,v);
    
      }
      
    
    return v;
    }
    
    public static int findMinMove(int board[][],int alpha,int beta,int depth)
    {
    
    int v=10000000;
    int vprime=findIfFinished(board);
    if(vprime!=0)return vprime;
    
    if(depth==0)
        return findUtilityValue(board);
    
    for(int i=0;i<6;i++)
      {
      int newBoard[][]=findNewState(board, i,1);
      boolean toMoveAgain=findIfAgainToMove(board, i, 1);
      if(toMoveAgain)
               v=Math.min(v, findMinMove(newBoard, alpha, beta, depth-1));
      else 
          v=Math.max(v,findMaxMove(newBoard, alpha, beta, depth-1));
      
      if(v<=alpha)return v;
      beta=Math.min(beta,v);
    
      }
      
    
    return v;
    
    }
    
    public static int findUtilityValue(int board[][])
    {
    
    int v=-1;
    
    return v;
    }
    
    public static int findIfFinished(int board[][])
    {
    int v=0;
    int agent1finished=0,agent2finished=0;
        
        for(int i=0;i<6;i++)
        {
        agent1finished |=board[0][i];
        agent2finished |=board[1][i];
        
        }
        
        if( (agent1finished & agent2finished)==0)
        {
        if(agent1finished==0)v= -1000000;
        v= 1000000;
        }
        
        
        
       return v;
    
    }
    
    public static boolean findIfAgainToMove(int board[][],int movePot,int player)
    {
    boolean againToMove=false;
    
    return againToMove;
    
    }
    
    public static int[][] findNewState(int board[][],int movePot,int player)
    {
    int changedBoard[][]=board;
    
   
    return changedBoard;
    
    }
    
}
