import java.awt.event.KeyEvent;
import java.util.Random;

public class Grid
{
	private int width;
	private int height;
	Tile[][] tiles;
	
	private Boolean active;
	
	// Constructor
	Grid(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		tiles = new Tile[width][height];
	}
	
	// Getters and Setters
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public Boolean getActive()
	{
		return active;
	}
	
	public void setActive(Boolean active)
	{
		this.active = active;
	}
	
	// Sets up the grid with starting tiles
	public void setUp(int numStartingTiles)
	{
		for (int i = 0; i < numStartingTiles; i++)
		{
			addRandomTile();
		}
	}
		
	// Checks if the coordinate is empty on the grid
	public Boolean isEmpty(Coordinate c)
	{
		return tiles[c.x][c.y] == null;
	}
	
	// Runs every time the player presses a key
	public void onPlayerInput(int keyCode)
	{
		if (active)
		{
			if(keyCode == KeyEvent.VK_LEFT)
			{
			
			}
			else if (keyCode == KeyEvent.VK_RIGHT)
			{
				
			}
			else if (keyCode == KeyEvent.VK_UP)
			{
				
			}
			else if (keyCode == KeyEvent.VK_DOWN)
			{
				
			}
		}
	}
	
	public void draw()
	{
		
	}
	
	// ***2048 Specific***
	// Adds a tile to a random spot on the grid and returns the location
	private Coordinate addRandomTile()
	{
		Random r = new Random();
		
		Boolean tilePlaced = false;
		Coordinate tilePos = null;
		int tileValue = r.nextInt(2) + 1 * 2; // (0 or 1) + 1 * 2 is either 2 or 4
		Tile newTile = null;
		
		// Loops until it finds an open space to place the tile
		while (!tilePlaced)
		{
			// Gets random coordinate
			tilePos = new Coordinate(r.nextInt(width), r.nextInt(height));
			
			// Creates tile
			newTile = new Tile(Integer.toString(tileValue), tileValue, tilePos);
			
			// Attempts to add tile to grid
			tilePlaced = addTile(newTile);
			
			// Cleans up tile if not added
			if (!tilePlaced)
				newTile = null;
		}
		
		return tilePos;
	}
	
	// Adds a tile to the grid if the space is empty
	private Boolean addTile(Tile t)
		{
			Coordinate tilePos = t.getCoordinate();
			
			if (isEmpty(tilePos))
			{
				tiles[tilePos.x][tilePos.y] = t;
				return true;
			}
			else
			{
				return false;
			}
		}
}
