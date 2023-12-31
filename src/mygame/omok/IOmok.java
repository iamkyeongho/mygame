package mygame.omok;

public interface IOmok 
{
	public static final int BoardSize = 16;
	public static final int MaxCount = 225;
	public static final int OMOK = 5;
	
	public static final char WHITE = 'O';
	public static final char BLACK = '⬤';
	public static final char BLANK = ' ';	
	
	public void drawBoard();
	public void clearBoard();
	public void placeStone(int x, int y) throws Exception;	
}
