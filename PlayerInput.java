import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PlayerInput extends JFrame implements KeyListener
{
	
	Board board;
	
	PlayerInput()
	{
		addKeyListener(this);
		setSize(100, 100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.board = null;
	}
	
	void setBoard(Board board) {
		this.board = board;
	}
	
	public void keyTyped(KeyEvent e)
	{
		// Blank implementation
	}
	
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT)
		{
			System.out.println("left pressed");
			board.handleArrows("left");
		
		}
		else if (keyCode == KeyEvent.VK_RIGHT)
		{
			System.out.println("right pressed");
			board.handleArrows("right");
		}
		else if (keyCode == KeyEvent.VK_UP)
		{
			System.out.println("up pressed");
			board.handleArrows("up");
		}
		else if (keyCode == KeyEvent.VK_DOWN)
		{
			System.out.println("down pressed");
			board.handleArrows("down");
		}
		
	}
	
	public void keyReleased (KeyEvent e)
	{
		// Blank implementation
	}
	
	
	/*
	 * Main for Testing
	public static void main(String[] args)
	{
		PlayerInput go = new PlayerInput();
	}
	*/
}