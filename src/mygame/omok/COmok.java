package mygame.omok;

public abstract class COmok implements IOmok
{
	public abstract void drawBoard();

	protected int count = 0;	
	protected char winner = IOmok.BLANK;
	public char getWinner()
	{
		return this.winner;
	}
	
	// 현재 플레이어의 돌
	protected char currentPlayer = IOmok.WHITE;
	public char getCurrentPlayer()
	{
		return this.currentPlayer;
	}
	public void setCurrentPlayer(char _player)
	{
		this.currentPlayer = _player;
	}
	
	// 오목판 
	protected char[][] board = new char[BoardSize][BoardSize];
	public char getBoardStone(int x, int y)
	{		
		return board[x][y];	
	}
	public void setBoardStone(int x, int y, char _stone)
	{
		this.board[x][y] = _stone;
	}
	
	
	// 오목 클래스 초기화
	public COmok()
	{ 
		this.initVariables();
	}	
	protected void initBoard()
	{
		for(int i = 1; i < BoardSize; i++)
		{
			for(int j = 1; j < BoardSize; j++)
			{
				board[i][j] =  IOmok.BLANK;
			}
		}
	}	
	protected void initVariables()
	{
		count = 0;
		currentPlayer = IOmok.WHITE;
		initBoard();
	}
	protected boolean checkPosition(int row, int col)
	{
		if (row > 0 && row < BoardSize && col > 0 && col < BoardSize)
			return true;
		else
			return false;
	}	
	
	public void placeStone(int row, int col) throws Exception
	{
		if (checkPosition(row, col) == false)
			throw new Exception(String.format("위치는 1부터 %d사이의 정수입니다.", BoardSize-1));
		if (board[row][col] != IOmok.BLANK)
			throw new Exception("이미 돌이 있습니다. 다른 위치에 놓아주세요!");	
		if (checkDoubleThree(row, col))
			throw new Exception("쌍삼입니다. 다른 위치에 놓아주세요!");
		
		board[row][col] = currentPlayer;		
		count++;
		if (currentPlayer == IOmok.WHITE)
			currentPlayer = IOmok.BLACK;
		else
			currentPlayer = IOmok.WHITE;
	}
	
	public void clearBoard()
	{
		initVariables();
	}	
	
	protected boolean findFiveStones(int row, int col)
	{
		char stone = board[row][col];
		
		// 수직 | (위 + 아래)
		int count = 1;
		count = count + countSameStones(row, col, stone, 1, 0) 
					  + countSameStones(row, col, stone, -1, 0);
		if (count == OMOK)
		{
			winner = stone;
			return true;
		}
		
		// 수평 - (왼쪽 + 오른쪽)
		count = 1;
		count = count + countSameStones(row, col, stone, 0, 1) 
					  + countSameStones(row, col, stone, 0, -1);
		if (count == OMOK)
		{
			winner = stone;
			return true;
		}
				
		// 대각선 \
		count = 1;
		count = count + countSameStones(row, col, stone, -1, -1)
					  + countSameStones(row, col, stone, 1, 1);
		if (count == OMOK)
		{
			winner = stone;
			return true;
		}
		// 대각선 /
		count = 1;
		count = count + countSameStones(row, col, stone, -1, 1)
					  + countSameStones(row, col, stone, 1, -1);
		if (count == OMOK)
		{
			winner = stone;
			return true;
		}
		return false;
	}
	
	protected int countSameStones(int row, int col, char stone, int rowIncrement, int colIncrement)
	{
		int count = 0;
		int r = row + rowIncrement;
		int c = col + colIncrement;
		
		while (r > 0 && r < BoardSize && c > 0 && c < BoardSize && board[r][c] == stone)
		{
			count++;
	        r += rowIncrement;
	        c += colIncrement;
		}
		return count;
	}
	
	protected boolean checkDoubleThree(int row, int col)
	{
		int countCol = 0;
		int countRow = 0;
		char stone = getCurrentPlayer();
		 
		// 수직(|)으로 3개인지 확인.
		countCol = 1 + countSameStones(row, col, stone, 1, 0) + countSameStones(row, col, stone, -1, 0);
		if (countCol == 3)
		{
			// 수직으로 3개 이상이면 각 row별로 3개인지 확인.
			for (int i = 1; i <= countCol; i++)
			{
				countRow = 1 + countSameStones(row-i, col, stone, 0, 1) + countSameStones(row-i, col, stone, 0, -1)
							 + countSameStones(row+i, col, stone, 0, 1) + countSameStones(row+i, col, stone, 0, -1);				// 수직 과 수평으로 3개 이상이므로 쌍삼.
				if (countRow == 3)
				{
					return true;
				}
			}		
		}
		// 수평(-)으로 3개인지 확인.
		countRow = 1 + countSameStones(row, col, stone, 0, 1) + countSameStones(row, col, stone, 0, -1);
		if (countRow == 3)
		{
			// 수평으로 3개이면 각 col별로 3개인지 확인.
			for (int i = 1; i <= countRow; i++)
			{
				countCol = 1 + countSameStones(row, col-i, stone, 1, 0) + countSameStones(row, col-i, stone, -1, 0)
							 + countSameStones(row, col+i, stone, 1, 0) + countSameStones(row, col+i, stone, -1, 0);							
				// 수직 과 수평으로 3개 이상이므로 쌍삼.
				if (countCol == 3)
				{
					return true;
				}
			}		
		}
		
		return false;
	}	
}
