package mygame;

import java.util.Scanner;

import mygame.omok.CTxtOmok;
import mygame.omok.CWinOmok;

public class MyGame {

	public static void main(String[] args) 
	{
		CTerminal.println("게임을 선택하시오 (종료: 0번).");
		CTerminal.println("1번 텍스트오목");
		CTerminal.println("2번 그래픽오목");
		CTerminal.println("3번 텍스트지뢰찾기");
		CTerminal.print("게임 선택: ");
		Scanner input = new Scanner(System.in);
		int menu = input.nextInt();
		
		switch(menu)
		{
			case 0:
				CTerminal.println("게임을 종료");
				CTerminal.println("시스템을 종료합니다");
				break;
			case 1:
				(new CTxtOmok(input)).play();			
				break;
			case 2:
				new CWinOmok();
				break;			
			case 3:
				break;
		}
		if (input != null) input.close();
	}
}
