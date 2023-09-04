package mygame.omok;

import java.util.Scanner;

import mygame.CTerminal;

public class CTxtOmok extends COmok 
{
	private CTerminal term;
	
	public static void main(String[] args) 
	{
		(new CTxtOmok()).play();
	}

	public CTxtOmok()
	{
		term = new CTerminal();
	}
	
	public CTxtOmok(Scanner _input)
	{
		term = new CTerminal(_input);
	}
	
	public void play()
	{
		int row = 0;
		int col = 0;
		drawBoard();
		do 
		{
			try
			{				
				CTerminal.print(String.format("%s가 놓을 위치를 정하시오[1~15][1~15]: ", getCurrentPlayer()));
				row = term.inputInt();
				col = term.inputInt();
				placeStone(row, col);
				
				if (findFiveStones(row, col))
				{
					drawBoard();
					String answer = term.inputWithMessage(String.format("%s의 승리입니다. 게임을 더 하시겠습니까? (Y/N)", getWinner()));
					if (answer.toLowerCase().trim().equals("y"))
						initVariables();
					else
						break;
				}
				else
				{
					drawBoard();
				}
			}
			catch (Exception ex)
			{
				CTerminal.println(String.format("에러 : %s", ex.getMessage()));
			}			
		} 
		while (count < MaxCount);
		
		CTerminal.println("게임을 종료합니다.");
		term.close();
	}
	
	@Override
	public void drawBoard()
	{
		CTerminal.println("  1   2   3   4   5   6   7   8   9  10  11  12  13  14  15");
		for (int i = 1; i < BoardSize; i++)
		{
			CTerminal.println("-------------------------------------------------------------");
			CTerminal.println(String.format("| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %s | %d",
					board[i][1], board[i][2], board[i][3], board[i][4], board[i][5],
					board[i][6], board[i][7], board[i][8], board[i][9], board[i][10],
					board[i][11], board[i][12], board[i][13], board[i][14], board[i][15], i));			
		}
		CTerminal.println("-------------------------------------------------------------");		
	}
}