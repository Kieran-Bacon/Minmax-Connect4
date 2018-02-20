import java.util.ArrayList;

public class ComputerPlayer extends Player{

    public void play(GameBoard board){}

    /**
     * Return a list of possible moves that can be made
     */
    private ArrayList nextStates(GameBoard board, char playerTag){
        ArrayList actions = new ArrayList();
        for(int i=0;i<GameBoard.length;i++){
            if(board.isValidMove(GameBoard.COLUMNS[i])){
                GameBoardState newState = new GameBoardState(board);
                newState.makeMove(playerTag, GameBoard.COLUMNS[i]);
                actions.add(newState);
            }
        }
        return actions;
    }
}