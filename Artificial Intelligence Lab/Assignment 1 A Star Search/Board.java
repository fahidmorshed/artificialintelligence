import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Board 
{
	private int g;
	private int board[][];
	private Board parent = null;
	public Board(int[][] colors, Board parent,int g) 
	{	
		// construct a board from an N-by-N
		this.board = colors;											//array of colors	
		this.parent=parent;						 					// (where colors[i][j] = color in								 					//row i, column j)
		this.g = g;
	}
	
	public int f() 
	{
		return g+heuristic1();
	}
	// returns the estimated distance from current board to final state using heuristic1
	public int heuristic1() 
	{
		return 0;	
	}
	// returns the estimated distance from current board to final state using heuristic2
	public int heuristic2()  
	{
		return 0;
	}
	// is this board the goal board? i.e., all color same. 
	public boolean isGoal() 
	{
		return false;
	}
	
	public int[][] getCopy(int board[][])
	{
		int [] [] nxtBoard = new int[board.length][board[0].length];
		for (int i=0; i<board.length;i++)
		{
			for(int j=0;j<board[i].length;j++)
			{
					nxtBoard[i][j]  = board[i][j];
			}
		}
		return nxtBoard;
	}
	// all neighboring boards
	public ArrayList<Board> neighbors() 
	{
		
	}
	// does this board equal y?
	public boolean equals(Object y) 
	{
		return false;
	}
	
	public int get_g()
	{
		return g;
	}
	
	public Board getParent() {
		return parent;
	}

	// string representation of the
	//board (in the output format specified below)
	public String toString() 
	{
		String str="\ng: "+g+" h:"+heuristic1()+"\n";
		for (int i=0; i<board.length;i++)
		{
			for(int j=0;j<board[i].length;j++)
			{
				str += (board[i][j]+" ");
			}
			str +="\n";
		}
		return str;
//		System.out.println();

	}

	// for testing purpose
	//public static void main(String[] args) 
	//{
			
	//}
}



