
public class Board {
	int boardWidth;
	int boardHeight;
	Tile[][] gameBoard;
	
	Board(int boardWidth, int boardHeight){
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		gameBoard = new Tile[boardWidth][boardHeight];
	}
	public void printBoard(){
		for(int row = 0; row < this.boardWidth;row++){
			for(int col = 0; col < this.boardHeight;col++){
				if(gameBoard[row][col].empty()) 
					System.out.print(" . ");
				else
					gameBoard[row][col].printTile();
			System.out.print('\n');	
		}
	}
}
