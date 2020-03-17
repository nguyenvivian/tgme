import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Game extends JFrame implements KeyListener
{	
	// Game Settings
	String gameName = "2048";
	int numStartingTiles = 2;
	
	// Player Specific Settings
	int p1GridWidth = 5;
	int p1GridHeight = 5;
	
	int p2GridWidth = 5;
	int p2GridHeight = 5;
	
	// 2048 Specific Settings
	
	// Unused TMGE Settings
	
	// Class Variables
	Grid p1Grid;
	Grid p2Grid;
	
	// Constructor
	Game()
	{
		// Adds a key listener to an invisible jframe
		addKeyListener(this);
		setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p1Grid = new Grid(p1GridWidth, p1GridHeight);
		p2Grid = new Grid(p2GridWidth, p2GridHeight);
	}

	// Blank implementation
	public void keyTyped(KeyEvent e) {}
	
	// Blank implementation
	public void keyReleased (KeyEvent e) {}
	
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		p1Grid.onPlayerInput(keyCode);
		p2Grid.onPlayerInput(keyCode);
	}
	
	public void StartGame()
	{
		p1Grid.setUp(numStartingTiles);
		p2Grid.setUp(numStartingTiles);
		
		
	}
	
	public static void main(String[] args)
	{
		Game g = new Game();
		
		Boolean running = true;
		while (running)
		{
			
			running = false;
		}
		
	}
}