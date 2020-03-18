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
		Boolean moreMovesPossible = false;
		do
		{
			moreMovesPossible = false;
			
			switch(dir)
			{
			case LEFT:
			case UP:
				for (Tile[] col : this.tiles)
				{
					for (Tile currentTile : col)
					{
						if (currentTile != null && move(currentTile, dir))
			                moreMovesPossible = true;
					}
				}
				break;
			case DOWN:
				for (int row = 0; row < this.width; row++)
			    {
			        for (int col = this.height - 1; col >= 0; col--)
			        {
			        	Tile currentTile = this.tiles[row][col];
			        	if (currentTile != null && move(currentTile, dir))
			                moreMovesPossible = true;
			        }
			    }
				break;
			case RIGHT:
				for (int row = this.width - 1; row >= 0; row--)
			    {
			        for (int col = 0; col < this.height; col++)
			        {
			        	Tile currentTile = this.tiles[row][col];
			        	if (currentTile != null && move(currentTile, dir))
			                moreMovesPossible = true;
			        }
			    }
				break;
			}
		}
		while(moreMovesPossible);
		
		// Reset tiles merging property
		for (Tile[] col : this.tiles)
		{
			for (Tile currentTile : col)
			{
				if (currentTile != null)
					currentTile.setMerged(false);
			}
		}
	}
	
	// Moves a tile one coordinate in the specified direction
	public Boolean move(Tile t, Direction dir)
	{
		//System.out.printf("[Moving]\n");
		//System.out.printf("Moving Tile %s at %s\n", t.getType(), t.getCoordinate().toString()); ///////////////////////////////////////////DEBUG
		
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
		
		//System.out.printf("Moving to location %s\n", targetCoord.toString()); ////////////////////////////////////////////////////////DEBUG
		
		Boolean successful = false;
		if(inBounds(targetCoord))
		{
			if(!isEmpty(targetCoord)) // If the new location has a tile
			{
				// Get tile on the grid
				Tile t2 = this.tiles[targetCoord.x][targetCoord.y];
				
				// Merge tiles if they are the same type
				if (t.getType().equals(t2.getType()) && !t.getMerged() && !t2.getMerged())
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

		return successful;
	}
	
	// Moves the first tile to the second tiles location and merges them
	private void merge(Tile t1, Tile t2)
	{
		// Receive points for merging
		currentMovePoints += t1.getScore();
		
		t2.setScore(t2.getScore() * 2);
		t2.setType(Integer.toString(t2.getScore()));
		t2.setMerged(true);
		
		this.removeTile(t1.getCoordinate());
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
			{
				newTile = null;
			}
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
			// System.out.printf("Adding Tile to Board at %s with type '%s'\n", this.tiles[tilePos.x][tilePos.y].getCoordinate().toString(), this.tiles[tilePos.x][tilePos.y].getType()); ///// DEBUG!
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
