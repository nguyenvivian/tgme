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
	public void handleArrows(String dir){
		bool possible = false;
		do{
			possible = false;
			for (Tile tile : this.Board) {
				if(move(dir, tile))
					possible = true;
			}
		}while(possible);
	}
	public bool move(String dir, Tile t){
		Coordinate targetCoord = t.coord;
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
				Tile t2 = this.Board[targetCoord.x,targetCoord.y];
				if (t.value == t2.value && !t2.merged){
					merge(t,t2);
					return true;
				}
				return false;
			}	
			else{
				this.Board[t.coord.x,t.coord.y] = null;
				this.Board[targetCoord.x,targetCoord.y] = t;
				t.coord = targetCoord;
				return true;
			}
		}
		else{
			return false;
		}
	}
	public bool isValid(Coordinate c){
		return (c.x > 0 && c.x < this.boardWidth) || (c.y > 0 && c.y < this.boardHeight);
	}
	public bool isTile(Coordinate c){
		return this.Board[c.x,c.y];
	}
	public void merge(Tile t1,Tile t2){
		this.Board
		t2.value = t2.value * 2;
		t2.merged = true;
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
