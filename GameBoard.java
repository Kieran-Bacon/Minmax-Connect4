public class GameBoard{

    public static final char[] COLUMNS = {"A","B","C","D","E","F","G"};

    protected GameState state;
    protected int[] columnSizes;
    protected char[][] positions;

    public GameBoard(){
        state = GameState.RUNNING;
        columnSizes = new int[7];
        positions = new char[7][6];
    }

    public Boolean isValidMove(char decision){

        int columnID = GameBoard.charToColumn(decision);

        if( columnID >= 0 & columnID < 7 & columnSizes[columnID] < 5){
            return true;
        }
        return false;
    }

    public Boolean isConcluded(){
        if(state == GameState.RUNNING) return false;
        return true;
    }

    public void display(){

        if(this.isConcluded()){
            System.out.println("***GAME OVER!***");
            if(this.state == GameState.HUMAN) System.out.println("Congratulations on the win!");
            if(this.state == GameState.COMPUTER) System.out.println("Bested but not beat!");
            if(this.state == GameState.DRAW) System.out.println("Honourable end!");
        }

        for( int y = 0; y < 6; y++){
            for( int x = 0; x < 7; x++){
                System.out.print(" | " + positions[x][5-y]);
                if(positions[x][5-y] == '\0') System.out.print(' ');
            }
            System.out.print(" |\n");
        }
        System.out.print(" ==A===B===C===D===E===F===G==\n");
    }

    public void makeMove(char playerTag, char decision){

        if(this.isValidMove(decision)){
            int cid = GameBoard.charToColumn(decision);
            positions[cid][columnSizes[cid]] = playerTag;
            columnSizes[cid]++;

            this.updateState();
        } else {
            throw new Error("Tried to make an illegal move|" + playerTag + "|" + decision);
        }
    }

    private void updateState(){
        
        // Horizontal wins
        for(int y=0;y<6;y++){
            for(int x=0;x<4; x++){
                if((positions[x][y] == positions[x+1][y]) &
                   (positions[x+1][y] == positions[x+2][y]) &
                   (positions[x+2][y] == positions[x+3][y]))
                {
                    if(positions[x][y] == 'H'){state = GameState.HUMAN; return;}
                    if(positions[x][y] == 'C'){state = GameState.COMPUTER; return;}
                }
            }
        }

        // Vertical wins
        for(int x=0;x<7;x++){
            for(int y=0;y<3;y++){
                if((positions[x][y] == positions[x][y+1]) &
                    (positions[x][y+1] == positions[x][y+2]) &
                    (positions[x][y+2] == positions[x][y+3]))
                {
                    if(positions[x][y] == 'H'){state = GameState.HUMAN;return;}
                    if(positions[x][y] == 'C'){state = GameState.COMPUTER;return;}
                }
            }
        }

        // Diagonal / wins
        for(int x=0;x<4;x++){
            for(int y=0;y<3;y++){
                if((positions[x][y] == positions[x+1][y+1]) &
                    (positions[x+1][y+1] == positions[x+2][y+2]) &
                    (positions[x+2][y+2] == positions[x+3][y+3]))
                {
                    if(positions[x][y] == 'H'){state = GameState.HUMAN;return;}
                    if(positions[x][y] == 'C'){state = GameState.COMPUTER;return;}
                }
            }
        }

        // Diagonal \ wins
        for(int x=6;x>2;x--){
            for(int y=5;y>2; y--){
                if((positions[x][y] == positions[x-1][y-1]) &
                   (positions[x-1][y-2] == positions[x-2][y-2]) &
                   (positions[x-2][y-2] == positions[x-3][y-3]))
                {
                    if(positions[x][y] == 'H'){state = GameState.HUMAN;return;}
                    if(positions[x][y] == 'C'){state = GameState.COMPUTER;return;}
                }
            }
        }

        // Draw
        int count = 0;
        for(int i=0;i<7;i++){
            count += columnSizes[i];
        }
        if(count==35) state = GameState.DRAW;
    }

    private static int charToColumn(char c){
        return (int) c - 65;
    }


}