package mygame.minesweeper;

import java.util.Random;

import mygame.CTerminal;
import mygame.CUtility;

public class CMinesweeper 
{
	public static final int BoardSize = 7;
	public static final char BLANK = ' ';
	public static final String DEFAULT_CELL = "🔲";
	
	public long minesCount = Math.round(BoardSize*BoardSize*0.1);
	public long findMinesCount = 0;
	public long nonMinesCount = BoardSize*BoardSize - minesCount;
	
	protected char[][]board = new char[BoardSize][BoardSize];
	protected boolean[][] mines = new boolean[BoardSize][BoardSize];
	protected boolean[][] openBoard = new boolean [BoardSize][BoardSize];

	protected boolean findMines = false; 
	protected CTerminal term;
	protected Random rand = new Random();
	
	public CMinesweeper()
	{	
		initialize(new CTerminal());		
	}

	public CMinesweeper(CTerminal _term)
	{
		initialize(_term);		
	}
	
	public void dispose()
	{
		CTerminal.println("게임을 종료합니다!");
		if (term != null)
			term.close();
	}
	
	protected void initialize(CTerminal _term)
	{
		term = _term;
		initBoard();	
		CTerminal.println("게임을 시작합니다!");
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
	
	protected boolean checkPosition(int row, int col)
	{
		if (row >= 0 && row < BoardSize && col >= 0 && col < BoardSize)
			return true;
		else
			return false;
	}
	
	public void printBoard()
	{
		CTerminal.println("------------------");
		for(int i = 0; i < BoardSize; i++)
		{
			if(i == 0)
			{
				for(int c = 0; c < BoardSize; c++)
				{
					CTerminal.print(String.format("%s%d", BLANK, c));
				}
				CTerminal.println("");
			}
			for(int j = 0; j < BoardSize; j++)
			{
				if(!openBoard[i][j])
				{
					if (j == BoardSize-1) 
						CTerminal.print(String.format("%s %d", DEFAULT_CELL, i));
					else
						CTerminal.print(DEFAULT_CELL);
				}
				else 
				{
					if (j == BoardSize-1)
						CTerminal.print(String.format(" %s %d", board[i][j], i));
					else
						CTerminal.print(String.format(" %s", board[i][j]));
				}
			}
			CTerminal.println("");
		}
		CTerminal.println("------------------");		
	}
	
	public void printMines()
	{
		CTerminal.println(String.format("지뢰 갯수 = %d(%d)",  minesCount, nonMinesCount));
		CTerminal.println("------------------");
		for(int i = 0; i < BoardSize; i++)
		{
			if(i == 0)
			{
				for(int c = 0; c < BoardSize; c++)
				{
					CTerminal.print(String.format("%s%d", BLANK, c));
				}
				CTerminal.println("");
			}
			for(int j = 0; j < BoardSize; j++)
			{
				if ((j == BoardSize-1) && (i < BoardSize)) 
					CTerminal.print(String.format(" %d %d", CUtility.boolToInt(mines[i][j]), i));					
				else
					CTerminal.print(String.format(" %d", CUtility.boolToInt(mines[i][j])));
			}
			CTerminal.println("");
		}
		CTerminal.println("------------------");
	}	
	
	public void plantMines()
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
	
	protected void openCell()
	{
		while(nonMinesCount > 0) 
		{			
			CTerminal.print("오픈할 셀의 좌표를 입력해주세요 (row col): ");
			int rowUser = term.inputInt();			
			int colUser = term.inputInt();
			if (!checkPosition(rowUser,  colUser))
			{
				CTerminal.println("범위를 벗어났습니다. 다시 입력해주세요!");
				continue;
			}
			if(openBoard[rowUser][colUser])
			{
				CTerminal.println("이미 개방한 셀입니다. 다시 입력해주세요!");
				continue;
			}
			
			boolean currentCell = mines[rowUser][colUser];
			if(!currentCell)
			{
				openBoard[rowUser][colUser] = true;
				checkCellAround(rowUser, colUser);				
			}
			else
			{
				findMines = true;
				CTerminal.println("지뢰가 있는 셀입니다!");
			}
			break;
		}
		if (nonMinesCount == 0)
		{
			findMines = true;
			CTerminal.println("지뢰를 모두 찾았습니다!");
		}
	}
	
	public int countMinesNearCell(int row, int col)
	{
		int count = 0;
		for(int i = -1; i < 2; i++)
		{
			for(int j = -1; j < 2; j++)
			{
				if (i == 0 && j == 0) continue;				
				if (!checkPosition(row+i,  col+j)) continue;		
				boolean isMineCell = mines[row+i][col+j];
				if (isMineCell)
					count++;
			}
		}
		return count;
	}
	
	protected void checkCellAround(int row, int col)
	{
		nonMinesCount--;
		int count = countMinesNearCell(row, col);
		if(count == 0)
		{	
			board[row][col] = BLANK;
			for(int i = -1; i < 2; i++)
			{
				for(int j = -1; j < 2; j++)
				{
					int nRow = row + i;
					int nCol = col + j;
					if (!checkPosition(nRow, nCol)) continue;
					
					if(!openBoard[nRow][nCol])
					{
						openBoard[nRow][nCol] = true;
						checkCellAround(nRow, nCol);
					}
				}
			}
		}
		else if(count > 0)
		{
			board[row][col] = (char)(count+'0'); 
		}
	}	
	
	public void play()
	{
		do 
		{
			openCell();		
			printBoard();
			printMines();
		}
		while (!findMines);		
	}
	
	public static void main(String[] args) 
	{
		CMinesweeper ms = new CMinesweeper();
		ms.printBoard();	
		ms.plantMines();		
		ms.printMines();
		ms.play();
		ms.dispose();
	}
}
