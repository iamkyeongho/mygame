package mygame.minesweeper;

import java.util.Random;

public class CMinesweeper 
{
	public static final int BoardSize = 7;
	public static final char BLANK = ' ';
	protected char[][]board = new char[BoardSize][BoardSize];
	protected boolean[][] mines = new boolean[BoardSize][BoardSize];
	private boolean[][] openBoard = new boolean [BoardSize][BoardSize];
	
	private int xUser;
	private int yUser;


	public double minesCount = (BoardSize*BoardSize)*0.3;
	private double non_minesNum = BoardSize - minesCount;

	Random rand = new Random();
	
	public CMinesweeper()
	{
		
	}
	
	protected void initBoard()
	{
		for(int i = 1; i < BoardSize; i++)
		{
			for(int j = 1; j < BoardSize; j++)
			{
				board[i][j] =  BLANK;
			}
		}
	}	
	protected void printBoard()
	{
		for(int i = 0; i < BoardSize; i++)
		{
			if(i == 0)
			{
				for(int c = 0; c < BoardSize; c++)
				{
					System.out.print(" "+c);
				}
				System.out.println();
			}
			for(int j = 0; j <BoardSize; j++)
			{
				if(!openBoard[i][j])
				{
					if((j == BoardSize-1)&&(i < BoardSize)) {
						System.out.print("🔲 "+i);
					}else
					System.out.print("🔲");
				}else {
					if((j == BoardSize-1)&&(i < BoardSize))
					{
						System.out.print(board[i][j]+" "+i);
					}else
					System.out.print(" "+board[i][j]);
				}
			}System.out.println();
		}
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

	}

}
