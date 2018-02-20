public class GameBoardState extends GameBoard{
    public int value;

    public GameBoardState(GameBoard board){
        this.state = board.state;                           // Copy state
        this.columnSizes = board.columnSizes.clone();       // Deep copy sizes
        this.postions = new char[board.positions.length][]; // Deep copy player tags
        for(int i=0;i<board.positions.length;i++){
            this.positions[i] = board.positions[i].clone();
        }
    }
}