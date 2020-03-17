public class testBoard{
    public static void main(String[] args){
        Board board = new Board(5,5);
        PlayerInput p1 = new PlayerInput();
        board.makeBoard();
        board.printBoard();
    }
}