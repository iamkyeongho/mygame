package mygame;

import java.util.Scanner;

public class CTerminal 
{
	Scanner input = null;
	
	public CTerminal()
	{
		input = new Scanner(System.in);
	}
	
	public CTerminal(Scanner _input)
	{
		input = _input;
	}
	
	public static void Print(String message)
	{
		System.out.print(message);		
	}
	public static void Println(String message)
	{
		System.out.println(message);		
	}
	
	public String InputString()
	{
		return input.next();
	}
	public int InputInt()
	{
		return input.nextInt();
	}
	
	public String InputWithMessage(String message)
	{
		CTerminal.Print(message);
		return input.next();
	}
	
	public void Close()
	{
		if (input != null) input.close();
	}
}
