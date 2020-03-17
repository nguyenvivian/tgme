import java.util.ArrayList;

public class Board {
	int boardWidth;
	int boardHeight;
	TFETile[][] gameBoard;
	ArrayList<TFETile> availableMove;
	
	Board(int boardWidth, int boardHeight){
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		gameBoard = new TFETile[boardWidth][boardHeight];
		availableMove= new ArrayList<TFETile>();
	}
	public void makeBoard(){
		for(int row = 0; row < this.boardWidth;row++){
			for(int col = 0; col < this.boardHeight;col++){
					
			System.out.print('\n');	
			}
		}
	}
	public void handleArrows(String dir){
		Boolean possible = false;
		do{
			possible = false;
			for (TFETile[] tile : this.gameBoard) {
				if(move(dir, tile))
					possible = true;
			}
		}while(possible);
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
			targetCoord = new Coordinate(targetCoord.x,targetCoord.y+1);

		}
		if(dir == "down"){
			targetCoord = new Coordinate(targetCoord.x,targetCoord.y-1);
		}
		
		if(isValid(targetCoord)){ 
			if(isTile(targetCoord)){
				Tile t2 = this.gameBoard[targetCoord.x,targetCoord.y];
				if (tile.value == t2.getValue() && !t2.getIsMerged()){
					merge(tile,t2);
					return true;
				}
				return false;
			}	
			else{
				this.gameBoard[tile.coord.x,t.coord.y] = null;
				this.gameBoard[targetCoord.x,targetCoord.y] = tile;
				tile.coord = targetCoord;
				return true;
			}
		}
		else{
			return false;
		}
	}
	public Boolean isValid(Coordinate c){
		return (c.x > 0 && c.x < this.boardWidth) || (c.y > 0 && c.y < this.boardHeight);
	}
	public Boolean isTile(Coordinate c){
		return this.gameBoard[c.x,c.y];
	}
	public void merge(TFETile t1,TFETile t2){
		t2.setValue(t2.getValue() * 2);
		t2.setIsMerged(true);
	}
	public void printBoard(){
		for(int row = 0; row < this.boardWidth;row++){
			for(int col = 0; col < this.boardHeight;col++){
				if(!isTile(new Coordinate(row,col))) {
					System.out.print(".\t");
				}
				else {
					gameBoard[row][col].printTile();
					System.out.print("\t");
				}
			System.out.print('\n');	
			}
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
