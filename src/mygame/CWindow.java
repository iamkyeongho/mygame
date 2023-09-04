package mygame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class CWindow extends JFrame 
{	
	private static final long serialVersionUID = -3637719460731281429L;

	private String title = "게임";
	private int width = 800;
	private int height = 800;
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	public CWindow()
	{
		this.initWindow(width, height);
	}

	public CWindow(int width, int height)
	{
		this.initWindow(width, height);
	}
	
	private void initWindow(int width, int height)
	{
		this.setSize(width, height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void setSize(int _width, int _height)
	{
		width = _width;
		height = _height;
		this.setSize(width, height);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	
	
	public static void showMessageBox(String message)
	{
		JOptionPane.showMessageDialog(null, message);	
	}
	
	public static int showOptionBox(String message, String title)
	{
		return JOptionPane.showOptionDialog(null, message, title, 
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, "Yes");	
	} 
	
	public static void printMessage(JTextArea textArea, String message)
	{
		textArea.setText(String.format("   %s", message));	
	}
}
