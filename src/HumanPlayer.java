import java.util.Scanner;

public class HumanPlayer extends Player{

    private Scanner input;
    public static final char tag = 'H';

    public HumanPlayer(){
        input = new Scanner(System.in);
    }

    public void play(GameBoard board){

        char decision;

        while(true){
            System.out.println("Please select a column to play in!");
            decision = input.next().charAt(0);
            if(board.isValidMove(decision)){
                break;
            }
            System.out.println("The input was invalid.");
        }

        board.makeMove(tag, decision);
    }
}