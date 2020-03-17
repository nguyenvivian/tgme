import java.util.ArrayList;
import java.util.Random;

public class Board {
	int boardWidth;
	int boardHeight;
	TFETile[][] gameBoard;
	ArrayList<TFETile> availableMove;
	Random random = new Random();
	
	Board(int boardWidth, int boardHeight){
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		gameBoard = new TFETile[boardWidth][boardHeight];
		availableMove= new ArrayList<TFETile>();
	}
	public int getBoardWidth(){
		return this.boardWidth;
	}
	public int getBoardHeight(){
		return this.boardHeight;
	}
	public void makeBoard(){
		for (int i = 0;i<1;i++){ //only make 4 tiles on board for now	
			int x = random.nextInt(this.boardWidth);
			System.out.println("x when made: " +x);
			int y = random.nextInt(this.boardHeight);
			System.out.println("y when made: " +y);
			int val = assignRandomValue();
			if (this.isValid(new Coordinate(x,y)) && !this.isTile(new Coordinate(x,y))) {
				this.gameBoard[x][y] = new TFETile(x,y,val);
			}
		}
	}
	private int assignRandomValue() {
		//tiles can start as a 2 or a 4
		int multiplier = random.nextInt(2) + 1; //returns 0/1 + 1
		return multiplier * 2; // 1 or 2 * 2 = 2 or 4
	}
	public void handleArrows(String dir){
		Boolean possible = false;
		do{
			possible = false;
			for (TFETile[] tileArr : this.gameBoard) {
				for (TFETile tile : tileArr) {
					if (!(tile == null)) {
						if(move(dir, tile))
							possible = true;
					}
				}
			}
		}while(possible);
		printBoard();
	}
	
	public Boolean move(String dir, TFETile tile){
		Coordinate targetCoord = tile.getCoord();
		if(dir == "right"){
			targetCoord = new Coordinate(targetCoord.x+1,targetCoord.y);
		}
		if(dir == "left"){
			targetCoord = new Coordinate(targetCoord.x-1,targetCoord.y);
		}
		if(dir == "up"){
			targetCoord = new Coordinate(targetCoord.x,targetCoord.y-1);

		}
		if(dir == "down"){
			targetCoord = new Coordinate(targetCoord.x,targetCoord.y+1);
		}
		
		if(isValid(targetCoord)){ 
			System.out.println(targetCoord.toString());
			if(isTile(targetCoord)){
				TFETile t2 = this.gameBoard[targetCoord.x][targetCoord.y];
				if (tile.value == t2.getValue() && !t2.getIsMerged()){
					merge(tile,t2);
					return true;
				}
				return false;
			}	
			else{
				this.gameBoard[tile.coord.x][tile.coord.y] = null;
				this.gameBoard[targetCoord.x][targetCoord.y] = tile;
				tile.coord = targetCoord;
				return true;
			}
		}
		else{
			return false;
		}
	}
	public Boolean isValid(Coordinate c){
		System.out.println("isValid coord: "+c.toString());
		System.out.println("isValid bool: "+((c.x >= 0 && c.x < this.boardWidth) && (c.y >= 0 && c.y < this.boardHeight)));
		return (c.x >= 0 && c.x < this.boardWidth) && (c.y >= 0 && c.y < this.boardHeight);
	}
	public Boolean isTile(Coordinate c){
		return this.gameBoard[c.x][c.y] != null;
	}
	public void merge(TFETile t1,TFETile t2){
		t2.setValue(t2.getValue() * 2);
		t2.setIsMerged(true);
	}
	
	public void printBoard(){
		for(int row = 0; row < this.boardWidth;row++){
			for(int col = 0; col < this.boardHeight;col++){
				if(!isTile(new Coordinate(col,row))) {
					System.out.print(".\t");
				}
				else {
					gameBoard[col][row].printTile();
					System.out.print("\t");
				}
			}
			System.out.print('\n');	
		}
	}
	
	//check win/lose/continue
	public boolean checkEnd(){
		if(availableMove.size()==0) {
			System.out.println("You Lose");
			return true;
		}
		for(int row = 0; row < this.boardWidth;row++){
			for(int col = 0; col < this.boardHeight;col++){
				if(gameBoard[row][col].value==2048){
					System.out.println("You Win");
					return true;
				}
			}
		}
		return false;
	}
	
}
