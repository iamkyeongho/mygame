package mygame.omok;

public abstract class COmok_Org implements IOmok
{
	public abstract void DrawBoard();

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
	public COmok_Org()
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
	
	public void PlaceStone(int row, int col) throws Exception
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
	
	public void ClearBoard()
	{
		initVariables();
	}
	
	protected boolean findCols(int row, int col)
	{
		return this.findCols(row, col, OMOK);
	}		
	protected boolean findCols(int row, int col, int omok)
	{
		int find = 1;
		char myturn = board[row][col];
		char stone = IOmok.BLANK;
		
		for (int i = 1; i < omok; i++)
		{
			if (!checkPosition(row-i, col)) break;			
			stone = board[row-i][col];
			if(stone == myturn)
				find++;
			else
				break;
		}
		for (int i = 1; i < omok; i++)
		{
			if (!checkPosition(row+i, col)) break;			
			stone = board[row+i][col];
			if(stone == myturn)
				find++;
			else
				break;
		}
		
		if (find == omok) return true;
		else return false;	
	}

	protected boolean findRows(int row, int col)
	{
		return this.findRows(row, col, OMOK);
	}
	protected boolean findRows(int row, int col, int omok)
	{
		int find = 1;
		char myturn = board[row][col];
		char stone = IOmok.BLANK;
		
		for(int i = 1; i < omok; i++) 
		{	
			if (!checkPosition(row, col-i)) break;			
			stone = board[row][col-i];
			if(stone == myturn)
				find++;
			else
				break;
		}
		for(int i = 1; i < omok; i++) 
		{		
			if (!checkPosition(row, col+i)) break;			
			stone = board[row][col+i];
			if(stone == myturn)
				find++;
			else
				break;
		}

		if (find == omok) return true;
		else return false;	
	}
	
	
	protected boolean findDiagonals(int row, int col)
	{
		return this.findDiagonals(row, col, OMOK);
	}
	protected boolean findDiagonals(int row, int col, int omok)
	{
		int find = 1;
		char myturn = board[row][col];
		char stone = IOmok.BLANK;
		
		// 아래쪽 + 오른쪽
		for(int i = 1; i < omok; i++)
		{
			if (!checkPosition(row+i, col+i)) break;			
			stone = board[row+i][col+i];
			if(stone == myturn)
				find++;
			else
				break;			
		}
		// 윗쪽 + 왼쪽
		for(int i = 1; i < omok; i++)
		{		
			if (!checkPosition(row-i, col-i)) break;			
			stone = board[row-i][col-i];
			if(stone == myturn)
				find++;
			else
				break;
		}
		if (find == omok) return true;
		
		// 방향 바꿔서 다시 초기화.
		find = 1;
		// 아래쪽 + 왼쪽
		for(int i = 1; i < omok; i++)
		{
			if (!checkPosition(row+i, col-i)) break;			
			stone = board[row+i][col-i];
			if(stone == myturn)
				find++;
			else
				break;
		}
		// 윗쪽 + 오른쪽
		for(int i = 1; i < omok; i++)
		{		
			if (!checkPosition(row-i, col+i)) break;			
			stone = board[row-i][col+i];
			if(stone == myturn)
				find++;
			else
				break;
		}
		
		if (find == omok) return true;
		else return false;	
	}
	
	/*
	protected boolean findFiveStones(int row, int col)
	{
		if (findCols(row, col))
		{
			winner = getBoardStone(row, col);
			return true;
		}
		if (findRows(row, col))
		{
			winner = getBoardStone(row, col);
			return true;
		}
		if (findDiagonals(row, col))
		{
			winner = getBoardStone(row, col);
			return true;
		}
		return false;
	}
	*/
	
	protected boolean findFiveStones(int row, int col)
	{
		char stone = board[row][col];
		
		// 수직 | (위 + 아래)
		int count = 1;
		count = count + countSameStones(row, col, stone, 1, 0);
		count = count + countSameStones(row, col, stone, -1, 0);
		if (count == OMOK)
		{
			winner = stone;
			return true;
		}
		
		// 수평 - (왼쪽 + 오른쪽)
		count = 1;
		count = count + countSameStones(row, col, stone, 0, 1);
		count = count + countSameStones(row, col, stone, 0, -1);
		if (count == OMOK)
		{
			winner = stone;
			return true;
		}
				
		// 대각선 \
		count = 1;
		count = count + countSameStones(row, col, stone, -1, -1);
		count = count + countSameStones(row, col, stone, 1, 1);
		if (count == OMOK)
		{
			winner = stone;
			return true;
		}
		// 대각선 /
		count = 1;
		count = count + countSameStones(row, col, stone, -1, 1);
		count = count + countSameStones(row, col, stone, 1, -1);
		if (count == OMOK)
		{
			winner = stone;
			return true;
		}
		
		
		return false;
	}
	
	
	protected boolean checkDoubleThree(int row, int col)
	{
		char stone = getCurrentPlayer();
		 
		int countCol = this.countSameStones(row, col, stone, 1, 0);
		countCol = countCol + this.countSameStones(row, col, stone, -1, 0);
		int countRow = this.countSameStones(row, col, stone, 0, 1);
		countRow = countRow + this.countSameStones(row, col, stone, 0, -1);
		
		if (countRow >= 2 && countCol >= 2)
			return true;
		else 
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
}
