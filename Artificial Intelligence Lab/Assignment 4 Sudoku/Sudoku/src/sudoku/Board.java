package sudoku;

public class Board {
    private int[][] boardArray;
    
    public Board(int[][] a){
        boardArray = new int[9][9];
        for(int i=0;i<9;i++){
            for (int j=0;j<9;j++)
                boardArray[i][j]=a[i][j];
        }
    }
    public int getTile(int i,int j){
        return boardArray[i][j];
    }
    public void setTile(int i,int j,int value){
        boardArray[i][j]=value;
    }
    public int[][] getWholeBoard(){
        int[][] a = new int[9][9];
        for(int i=0;i<9;i++)
            for (int j=0;j<9;j++)
               a[i][j]=boardArray[i][j];
        return a;
    }
}

