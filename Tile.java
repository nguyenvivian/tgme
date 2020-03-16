
public class Tile {
	String tileName;
	TileType tileType; //parent class for all tile types
	//tile score move to tile type... unless matching is determined by board instead of the type of tile? 
	Boolean hasGravity;
	int matchLength; //length that is required for a match
	Boolean matchDiagonal;
	Boolean matchVertical;
	Boolean matchHorizontal;
	
	
	Tile(){
		
	}
	
	public void checkMatch() {
		//check for a match
		if (checkDiagonal() || checkVertical() || checkHorizontal()) {
			//handle match
		}
		
	}
	
	private Boolean checkDiagonal() {
		//make a list of coordinates of all the pieces that match?
		//introduce field variable that keep track of all the matched pieces
		return false;
		
	}
	private Boolean checkVertical() {
		return false;
			
	}
	private Boolean checkHorizontal() {
		return false;
		
	}
}
