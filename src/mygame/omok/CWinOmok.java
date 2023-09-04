package mygame.omok;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import mygame.CWindow;

public class CWinOmok extends COmok implements ActionListener
{
	private final String TITLE = "오목";
	
	private CWindow window;
	private JPanel panelGrid;
	private JTextArea message;
	
	public static void main(String[] args) 
	{
		new CWinOmok();
	}

	public CWinOmok()
	{
		initWindow();
	}
	
	private void initWindow()
	{
		window = new CWindow();
		window.setTitle(TITLE);
		window.setLayout(new BorderLayout());
		
		panelGrid = new JPanel();
		panelGrid.setLayout(new GridLayout(BoardSize-1,BoardSize-1,3,3));	
		window.add("Center", this.panelGrid);
		drawBoard();
		
		message = new JTextArea("   오목 게임을 시작합니다. O가 놓을 위치를 클릭하세요.");		
		window.add("South", message);
		
		window.setResizable(true);
		window.setVisible(true);		
	}

	@Override
	public void drawBoard() 
	{
		for (int i = 0; i < BoardSize-1; i++)
		{
			for (int j = 0; j < BoardSize-1; j++)
			{
				JButton stone = new JButton(String.format("%s", IOmok.BLANK));
				stone.setName(String.format("%d-%d", i+1, j+1));
				stone.addActionListener(this);
				this.panelGrid.add(stone);
			}
		}		
	}
	
	@Override
	public void clearBoard() 
	{
		super.clearBoard();
		for (int i = 0; i < this.panelGrid.getComponentCount(); i++)
		{
			String name = this.panelGrid.getComponent(i).getClass().getSimpleName();
			if (name.equals("JButton"))
			{
				JButton btn = (JButton)this.panelGrid.getComponent(i);
				btn.setText(String.format("%s", IOmok.BLANK));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String eventClass = e.getSource().getClass().getSimpleName();
		if (eventClass.equals("JButton"))
		{
			JButton btn = (JButton)e.getSource(); 
			String[] position = btn.getName().split("-");			
			try 
			{
				int row = Integer.parseInt(position[0]);
				int col = Integer.parseInt(position[1]);
								
				placeStone(row, col);
				btn.setText(String.format("%c", getBoardStone(row, col)));
				
				if (findFiveStones(row, col))
				{				
					int result = CWindow.showOptionBox(String.format("%s의 승리입니다. 게임을 다시 하시겠습니까?", getWinner()), "오목");
					if (result == JOptionPane.YES_OPTION)
					{
						this.initVariables();
						this.clearBoard();
					}
					else
					{
						this.window.dispose();
					}					
				}
			} 
			catch (Exception ex) 
			{
				CWindow.showMessageBox(ex.getMessage());	
			}
		}
	}

	@Override
	public void placeStone(int row, int col) throws Exception 
	{		
		super.placeStone(row, col);		
		CWindow.printMessage(message, String.format("%c : %d-%d", getBoardStone(row, col), row, col));
	}
}
