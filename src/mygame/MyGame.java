package mygame;

import java.util.Scanner;

import mygame.omok.CTxtOmok;
import mygame.omok.CWinOmok;

public class MyGame {

	public static void main(String[] args) 
	{
		System.out.print("게임을 선택하시오 (종료: 0번).");
		System.out.print("\n1번 텍스트오목");
		System.out.print("\n2번 그래픽오목");
		System.out.print("\n3번 텍스트지뢰찾기");
		System.out.print("\n게임 선택: ");
		Scanner input = new Scanner(System.in);
		int menu = input.nextInt();
		
		switch(menu)
		{
			case 0:
				System.out.print("\n게임을 종료");
				System.out.print("\n시스템을 종료합니다");
				break;
			case 1:
				(new CTxtOmok(input)).Play();			
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
