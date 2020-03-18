import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;

public class Game extends JFrame implements KeyListener
{	
	// Game Settings
	String name = "2048";
	int numStartingTiles = 2;
	
	// Player Specific Settings
	UserProfile p1;
	int p1GridWidth = 5;
	int p1GridHeight = 5;
	
	UserProfile p2;
	int p2GridWidth = 5;
	int p2GridHeight = 5;
	
	UserProfile activePlayer;
	
	
	// Constructor
	Game()
	{
		// Adds a key listener to a jframe
		addKeyListener(this);
		setSize(100, 100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Blank implementation
	public void keyTyped(KeyEvent e) {}
	
	// Blank implementation
	public void keyReleased (KeyEvent e) {}
	
	// Sends key press to the grids for each player
	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		
		this.activePlayer.updateScore(this.activePlayer.getGrid().onPlayerInput(keyCode));
		this.activePlayer.getGrid().addRandomTile();
		this.activePlayer.incrementTurn();
		
		this.displayTurn();

		// If a player loses, set the active player to player 2
		if (this.checkPlayerLoss())
		{
			this.activePlayer = this.p2;
		}

		// If both players ended their games, display scores and terminate
		if (!this.p1.getAlive() && !this.p2.getAlive())
		{
			this.displayEndScreen();
			System.exit(0);
		}
	}
	
	// Sets up the grids for each player
	private void startGame()
	{
		this.p1.getGrid().setUp(numStartingTiles);
		this.p2.getGrid().setUp(numStartingTiles);
		
		this.activePlayer = this.p1;
		this.displayTurn();
	}
	
	// Display's the active player's current turn
	private void displayTurn()
	{
		System.out.printf("\n\n\n\n\n");
		System.out.printf("[Player '%s' Turn]\n", activePlayer.getUsername());
		System.out.printf("Current Score: %d\n", activePlayer.getScore());
		System.out.printf("Turn Number: %d\n", activePlayer.getTurn());
		System.out.println();
		
		activePlayer.getGrid().draw();
	}
	
	// Checks to see if the current player lost the game
	private Boolean checkPlayerLoss()
	{
		if(this.activePlayer.getGrid().isFull())
		{
			this.activePlayer.setAlive(false);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Displays the game's settings in the console
	private void displaySettings()
	{
		System.out.printf("[Game Settings]\n");
		System.out.printf("Game: %s\n", this.name);
		System.out.printf("numStartingTiles: %d\n", this.numStartingTiles);
		System.out.println();
	}
	
	// Requests user input and creates user profiles for players
	private void createPlayers()
	{
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please enter player 1's name: ");
		String p1Name = input.next();
		
		System.out.print("Please enter player 2's name: ");
		String p2Name = input.next();
		
		this.p1 = new UserProfile(p1Name, new Grid(p1GridWidth, p1GridHeight));
		this.p2 = new UserProfile(p2Name, new Grid(p2GridWidth, p2GridHeight));
	}

	
	private void displayEndScreen()
	{
		System.out.printf("\n\n\n\n\n");
		System.out.printf("[Game Over]\n");
		System.out.printf("Player '%s' achieved a score of %d in %d turns!\n", this.p1.getUsername(), this.p1.getScore(), this.p1.getTurn());
		System.out.printf("Player '%s' achieved a score of %d in %d turns!\n", this.p2.getUsername(), this.p2.getScore(), this.p2.getTurn());
		System.out.println();
		
		if (p1.getScore() > p2.getScore())
			System.out.printf("Player '%s' Wins!\n", this.p1.getUsername());
		else if (p2.getScore() > p1.getScore())
			System.out.printf("Player '%s' Wins!\n", this.p2.getUsername());
		else
			System.out.printf("Tie Game!\n");
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		
		game.displaySettings();
		game.createPlayers();
		game.startGame();
		
		// The rest of the game is handled in keyPressed
	}
}