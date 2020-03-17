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
