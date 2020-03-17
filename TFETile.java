import java.util.Random;

public class TFETile {
	
	public int value;
	public Coordinate coord;
	Boolean isMerged;
	
	TFETile(){
		coord = findEmptySpace();
        value = assignRandomValue();
        isMerged = false;
	}
	public int getValue(){
		return this.value;
	}
	public void printTile(){
		System.out.print(value);
	}
	public Coordinate getCoord(){
		return this.coord;
	}

	public Boolean getIsMerged() {
		return isMerged;
	}
	public void setIsMerged(Boolean isMerged) {
		this.isMerged = isMerged;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

	private Coordinate findEmptySpace() {		
		while(true) {
			int x = Random.nextInt(Board.getBoardWidth());
			int y = Random.nextInt(Board.getBoardHeight());
			
			if (Board.getGameBoard()[x][y] == null) {
				return new Coordinate(x, y);
			}
		}
	}
	
	private int assignRandomValue() {
		//tiles can start as a 2 or a 4
		int multiplier = Random.nextInt(1) + 1; //returns 0/1 + 1
		return multiplier * 2; // 1 or 2 * 2 = 2 or 4
		
	}
	
}

