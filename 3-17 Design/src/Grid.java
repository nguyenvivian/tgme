import java.awt.event.KeyEvent;
import java.util.Random;

public class Grid
{
	private int width;
	private int height;
	Tile[][] tiles;
	
	private int numTiles;
	private int currentMovePoints;
	
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

	public int getNumTiles()
	{
		return this.numTiles;
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
	
	// Checks if the entire grid is full
	public Boolean isFull()
	{		
		return numTiles == (this.tiles.length * this.tiles[0].length);
	}
	
	// Checks if the coordinate is in bounds
	public Boolean inBounds(Coordinate c)
	{
		return (c.x >= 0 && c.x < this.width) && (c.y >= 0 && c.y < this.height);
	}
	
	// Runs every time the player presses a key
	public int onPlayerInput(int keyCode)
	{
		// Reset amount of points earned for this move
		currentMovePoints = 0;
		
		if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP)
		{
			this.shift(Direction.UP);
		}
		else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN)
		{
			this.shift(Direction.DOWN);
		}
		else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT)
		{
			this.shift(Direction.LEFT);
		}
		else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT)
		{
			this.shift(Direction.RIGHT);
		}
		
		return currentMovePoints;
	}
	
	// Shifts all of the tiles to one side of the grid
	public void shift(Direction dir)
	{
		// Handles the order in which blocks are moved
		// Moves blocks closer to the side of the direction first
		int rowStart = 0;
		int rowCheck = this.width;
		int rowIncrement = 1;
		int colStart = 0;
		int colCheck = this.height;
		int colIncrement = 1;

		switch(dir)
		{
		case RIGHT:
		    rowStart = this.width;
		    rowCheck = 0;
		    rowIncrement = -1;
		    colStart = 0;
		    colCheck = this.height;
		    colIncrement = 1;
		    break;
		case UP:
		    rowStart = 0;
		    rowCheck = this.width;
		    rowIncrement = 1;
		    colStart = this.height;
		    colCheck = 0;
		    colIncrement = -1;
		    break;
		}

		Boolean moreMovesPossible = false;
		do
		{
			moreMovesPossible = false;
		    for (int row = rowStart; row < rowCheck; row += rowIncrement)
		    {
		        for (int col = colStart; col < this.height; col += colIncrement)
		        {
		            if (move(this.tiles[row][col], dir))
		                moreMovesPossible = true;
		        }
		    }
		}
		while(moreMovesPossible);
	}
	
	// Moves a tile one coordinate in the specified direction
	public Boolean move(Tile t, Direction dir)
	{
		Coordinate targetCoord = t.getCoordinate();
		
		// Move target coordinate in direction of the keypress
		switch(dir)
		{
		case RIGHT:
			targetCoord = new Coordinate(targetCoord.x+1,targetCoord.y);
			break;
		case LEFT:
			targetCoord = new Coordinate(targetCoord.x-1,targetCoord.y);
			break;
		case UP:
			targetCoord = new Coordinate(targetCoord.x,targetCoord.y-1);
			break;
		case DOWN:
			targetCoord = new Coordinate(targetCoord.x,targetCoord.y+1);
			break;
		}
		
		Boolean successful = false;
		if(inBounds(targetCoord))
		{
			if(!isEmpty(targetCoord)) // If the new location has a tile
			{
				// Get tile on the grid
				Tile t2 = this.tiles[targetCoord.x][targetCoord.y];
				
				// Merge tiles if they are the same type
				if (t.getType().equals(t2.getType()) && !t2.getMerged())
				{
					this.merge(t, t2);
					successful = true;
				}
				else
				{
					successful = false;
				}
			}	
			else
			{
				this.removeTile(t.getCoordinate());
				
				t.setCoordinate(targetCoord);
				
				successful = this.addTile(t);
			}
		}
		else
		{
			successful = false;
		}
		
		// Reset tiles merging property
		for (Tile[] col : this.tiles)
		{
			for (Tile currentTile : col)
			{
				currentTile.setMerged(false);
			}
		}
		
		return successful;
	}
	
	// Moves the first tile to the second tiles location and merges them
	private void merge(Tile t1, Tile t2)
	{
		// Receive points for merging
		currentMovePoints += t1.getScore();
		
		t2.setScore(t2.getScore() * 2);
		t2.setMerged(true);
		
	}
	
	// Prints the grid in the console
	public void draw()
	{
		Coordinate current = null;
		for(int row = 0; row < this.width; row++)
		{
			for(int col = 0; col < this.height; col++)
			{
				current = new Coordinate(col, row);
				
				if(isEmpty(current))
				{
					System.out.print(".\t");
				}
				else
				{
					this.tiles[col][row].draw();
					System.out.print("\t");
				}
			}
			System.out.println();	
		}
	}
	
	// ***2048 Specific***
	// Adds a tile to a random spot on the grid and returns the location
	public Coordinate addRandomTile()
	{
		Random r = new Random();
		
		Boolean tilePlaced = false;
		Coordinate tilePos = null;
		int tileValue = (r.nextInt(2) + 1) * 2; // ((0 or 1) + 1) * 2 is either 2 or 4
		Tile newTile = null;
		
		// Loops until it finds an open space to place the tile
		while (!tilePlaced)
		{
			// Gets random coordinate
			tilePos = new Coordinate(r.nextInt(this.width), r.nextInt(this.height));
			
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
			this.tiles[tilePos.x][tilePos.y] = t;
			this.numTiles++;
			System.out.printf("Adding Tile to Board at Pos(%d, %d) with type '%s'\n", t.getCoordinate().x, t.getCoordinate().y, t.getType());
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// Removes a tile from the grid at a coordinate
	private Boolean removeTile(Coordinate c)
	{
		if (!isEmpty(c))
		{
			this.tiles[c.x][c.y] = null;
			this.numTiles--;
			return true;
		}
		else
		{
			return false;
		}
	}
}
