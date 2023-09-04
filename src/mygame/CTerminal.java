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
	
	public static void print(String message)
	{
		System.out.print(message);		
	}
	public static void println(String message)
	{
		System.out.println(message);		
	}
	
	public String inputString()
	{
		return input.next();
	}
	public int inputInt()
	{
		return input.nextInt();
	}
	
	public String inputWithMessage(String message)
	{
		CTerminal.print(message);
		return input.next();
	}
	
	public void close()
	{
		if (input != null) input.close();
	}
}
