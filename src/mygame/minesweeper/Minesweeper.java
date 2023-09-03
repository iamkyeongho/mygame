package mygame.minesweeper;

import java.util.*;

public class Minesweeper
{
	private int xUser;
	private int yUser;
	private int BoardSize = 7;
	private boolean[][] openBoard = new boolean [BoardSize][BoardSize];
	private char[][]board = new char[BoardSize][BoardSize];
	private boolean[][] mines = new boolean[BoardSize][BoardSize];
	public double minesCount = (BoardSize*BoardSize)*0.3;
	private double non_minesNum = BoardSize - minesCount;
	Scanner input = new Scanner(System.in); 
	Random rand = new Random();
	
	public static void main(String[] args) 
	{
		Minesweeper ms = new Minesweeper();
		ms.play();
	}
	public Minesweeper()
	{
		
	}
	public void play()
	{
		Start_Logic();
	}
	public void Start_Logic()
	{
		int count = 0;
		initializeBoard();
		plant_Mines();
		
		do {
			printBoard();
			OpenCell();
			
			count++;
		}while(count < 20);
		if(non_minesNum == 0)
		{
			System.out.println("지뢰를 정확히 모두 찾으셨습니다!");
		}
		System.out.print("시스템 종료");
	}
	public void plant_Mines()
	{
		int count = 0;	 
		while(count < minesCount)
		{
			int x = rand.nextInt(BoardSize);
			int y = rand.nextInt(BoardSize);
			if(!mines[x][y])
			{
				mines[x][y] = true;
				count++;
			}
		}
	}
	public void OpenCell()
	{
		while(true) {
		xUser = input.nextInt();
		yUser = input.nextInt();
		if(openBoard[xUser][yUser])
		{
			System.out.println("이미 개방한 셀입니다");
			continue;
		}
		if(!mines[xUser][xUser])
		{
			non_minesNum--;
			openBoard[xUser][yUser] = true;
			check_cell_empty(xUser,yUser);
		}
		break;
		}
	}
	public int check_nearCell_count(int xrow,int xcol)
	{
		int count = 0;
		for(int i = -1; i < 2; i++)
		{
			for(int j = -1; j < 2; j++)
			{
				if(mines[xrow+i][xrow+j] && (xrow+i<BoardSize) && (xrow+i>=0) && (xcol+i<BoardSize) && (xcol+i>=0))
				{
					count++;
				}
			}
		}
		return count;
	}
	
	public void check_cell_empty(int xrow,int xcol)
	{
		int count = check_nearCell_count(xrow,xcol);
		if(count == 0)
		{	
			board[xrow][xcol] = ' ';
			for(int i = -1; i < 2; i++)
			{
				for(int j = -1; j < 2; j++)
				{
					int nRow = xrow + i;
					int nCol = xcol + j;
					if(!openBoard[nRow][nCol]&&(nRow >=0)&&(nRow < BoardSize)&&(nCol >= 0)&&(nCol < BoardSize))
					{
						openBoard[nRow][nCol] = true;
						check_cell_empty(nRow,nCol);
					}
				}
			}
		}else if(count > 0)
		{
			board[xrow][xcol] = (char)(count+'0'); 
		}
	}
	
	public void printBoard()
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
	
	public void initializeBoard()
	{
		for(int i = 0; i <BoardSize; i++)
		{
			for(int j = 0; j <BoardSize; j++)
			{
				board[i][j] = ' ';
			}
		}
	}
}