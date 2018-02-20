import java.util.ArrayList;

public class ComputerPlayer extends Player{

    public static final char tag = 'C';
    private static final int lookahead = 10;
    private static final int[] columnValues = {0,1,2,3,2,1,0};

    private static final int ALPHA = Integer.MIN_VALUE;
    private static final int BETA = Integer.MAX_VALUE;

    public void play(GameBoard board){
        System.out.println("Computer is thinking...");
        GameBoardState newState = minmax(new GameBoardState(board), ComputerPlayer.tag, lookahead, ALPHA, BETA);

        System.out.println("The computer has decided to play in in column " + newState.move);
        board.makeMove(ComputerPlayer.tag, newState.move);
    }

    private GameBoardState minmax(GameBoardState board, char player, int depth, int alpha, int beta){

        ArrayList<GameBoardState> states = nextStates(board, player);

        if(depth == 0 || states.size() == 0){
            evaluate(board, depth);
            return board;
        }

        int index = 0, fitness;

        if(player == ComputerPlayer.tag){
            fitness = Integer.MIN_VALUE;
            for(int i=0;i<states.size();i++){
                GameBoardState eval = minmax(states.get(i), HumanPlayer.tag, depth-1, alpha, beta);
                fitness = Math.max(fitness, eval.value);

                if(fitness>alpha){index = i; alpha = fitness;}
                if(alpha>=beta) break;
            }

            GameBoardState selected = states.get(index);
            selected.value = fitness;
            return selected;
        }

        if(player == HumanPlayer.tag){
            fitness = Integer.MAX_VALUE;
            for(int i=0;i<states.size();i++){
                GameBoardState eval = minmax( states.get(i), ComputerPlayer.tag, depth-1, alpha, beta);
                fitness = Math.min(fitness, eval.value);

                if(fitness<beta){index = i; beta = fitness;}
                if(alpha>=beta) break;
            }

            GameBoardState selected = states.get(index);
            selected.value = fitness;
            return selected;
        }

        return board;
    }

    /**
     * Return a list of possible moves that can be made
     */
    private ArrayList<GameBoardState> nextStates(GameBoard board, char playerTag){
        ArrayList<GameBoardState> actions = new ArrayList<GameBoardState>();
        if(board.isConcluded()) return actions; // No next states

        for(int i=0;i<GameBoard.COLUMNS.length;i++){
            if(board.isValidMove(GameBoard.COLUMNS[i])){
                GameBoardState newState = new GameBoardState(board);
                newState.makeMove(playerTag, GameBoard.COLUMNS[i]);
                actions.add(newState);
            }
        }
        return actions;
    }

    private static void evaluate(GameBoardState board, int depth){

        for(int x=0;x<GameBoard.WIDTH; x++){
            for(int y=0;y<GameBoard.HEIGHT; y++){
                char tag = board.element(x,y);
                int[] coordinate = {x,y};

                // Push computer towards the center of the board.
                if(tag == ComputerPlayer.tag) board.value += columnValues[x];
                if(tag == HumanPlayer.tag) board.value -= columnValues[x];

                // Add value based on neighbouring positions.
                board.value += evaluatePosition(board, coordinate);
            }
        }

        if(board.state() == GameState.COMPUTER) board.value += 22*depth;
        if(board.state() == GameState.HUMAN)    board.value -= 22*depth;
    }

    private static int evaluatePosition(GameBoardState board, int[] pos){
        
        int value = 0;
        char tag = board.element(pos[0], pos[1]);

        boolean[] incounters = new boolean[8];
        for(int x=1;x<4;x++){

            // Above
            if(!incounters[0]){
                if((pos[1]+x<0) ||
                   (pos[1]+x>=GameBoard.HEIGHT) ||
                   (board.element(pos[0], pos[1]+x) != tag))
                {
                    incounters[0] = true;
                    continue;
                }
                value++;
            }

            // Top right

            if(!incounters[1]){
                if((pos[0]+x<0) ||
                   (pos[0]+x>=GameBoard.WIDTH) ||
                   (pos[1]+x<0) ||
                   (pos[1]+x>=GameBoard.HEIGHT) ||
                   (board.element(pos[0]+x, pos[1]+x) != tag))
                {
                    incounters[1] = true;
                    continue;
                }
                value++;
            }

            // Right
            if(!incounters[2]){
                if((pos[0]+x<0) ||
                   (pos[0]+x>=GameBoard.WIDTH) ||
                   (board.element(pos[0]+x, pos[1]) != tag))
                {
                    incounters[2] = true;
                    continue;
                }
                value++;
            }

            // Bottom right
            if(!incounters[3]){
                if((pos[0]+x<0) ||
                   (pos[0]+x>=GameBoard.WIDTH) ||
                   (pos[1]-x<0) ||
                   (pos[1]-x>=GameBoard.HEIGHT) ||
                   (board.element(pos[0]+x, pos[1]-x) != tag))
                {
                    incounters[3] = true;
                    continue;
                }
                value++;
            }

            // Bottom
            if(!incounters[4]){
                if((pos[1]-x<0) ||
                   (pos[1]-x>=GameBoard.HEIGHT) ||
                   (board.element(pos[0], pos[1]-x) != tag))
                {
                    incounters[4] = true;
                    continue;
                }
                value++;
            }

            // Bottom Left
            if(!incounters[5]){
                if((pos[0]-x<0) ||
                   (pos[0]-x>=GameBoard.WIDTH) ||
                   (pos[1]-x<0) ||
                   (pos[1]-x>=GameBoard.HEIGHT) ||
                   (board.element(pos[0]-x, pos[1]-x) != tag))
                {
                    incounters[5] = true;
                    continue;
                }
                value++;
            }

            // Left
            if(!incounters[6]){
                if((pos[0]-x<0) ||
                   (pos[0]-x>=GameBoard.WIDTH) ||
                   (board.element(pos[0]-x, pos[1]) != tag))
                {
                    incounters[6] = true;
                    continue;
                }
                value++;
            }

            // Top Left
            if(!incounters[7]){
                if((pos[0]-x<0) ||
                   (pos[0]-x>=GameBoard.WIDTH) ||
                   (pos[1]+x<0) ||
                   (pos[1]+x>=GameBoard.HEIGHT) ||
                   (board.element(pos[0]-x, pos[1]+x) != tag))
                {
                    incounters[7] = true;
                    continue;
                }
                value++;
            }
        }

        return value;
    }
}