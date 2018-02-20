public class GameBoardState extends GameBoard{
    public int value;
    public char player;
    public char move;

    public GameBoardState(GameBoard board){
        this.state = board.state;                           // Copy state
        this.columnSizes = board.columnSizes.clone();       // Deep copy sizes
        this.positions = new char[board.positions.length][]; // Deep copy player tags
        for(int i=0;i<board.positions.length;i++){
            this.positions[i] = board.positions[i].clone();
        }
        this.value = 0;
    }

    public void makeMove(char playerTag, char decision){
        if(this.isValidMove(decision)){

            player = playerTag; move = decision;

            int cid = charToColumn(decision);
            positions[cid][columnSizes[cid]] = playerTag;
            columnSizes[cid]++;

            updateState();
        } else {
            throw new Error("Tried to make an illegal move|" + playerTag + "|" + decision);
        }
    }
}