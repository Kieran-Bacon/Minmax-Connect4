import java.util.Queue;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Connect4{

    private static GameBoard board;
    private static Queue<Player> players;
    
    public static void main(String Args[]){

        Scanner input = new Scanner(System.in);

        System.out.println("================================");
        System.out.println("|| Kieran's Java Connect 4 AI ||");
        System.out.println("================================\n");
        System.out.println("Would you like to go first against the computer? (Y)");
        
        char resp = input.next().charAt(0);

        players = new ArrayDeque<Player>();
        if(resp=='Y'){
            players.add(new HumanPlayer());
            players.add(new ComputerPlayer());
        } else {
            players.add(new ComputerPlayer());
            players.add(new HumanPlayer());
        }

        board = new GameBoard();
        board.display();
        while(!board.isConcluded()){
            Player nextPlayer = players.remove();
            nextPlayer.play(board);
            board.display();
            players.add(nextPlayer);
        }
        input.close();
    }
}