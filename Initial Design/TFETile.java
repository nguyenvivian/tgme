import java.util.Random;

public class TFETile {
	
	public int value;
	public Coordinate coord;
	Boolean isMerged;

	
	TFETile(int x,int y,int val){
		this.coord = new Coordinate(x,y);
		this.value = val;
        this.isMerged = false;
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

	
}

