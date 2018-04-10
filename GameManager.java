import java.util.*;
import java.io.*;

public class GameManager {
// Instance variables
private Board board;     // The actual 2048 board
private String outputFileName;     // File to save the board to when exiting



/* Constructor Header Comments
 * Name:       GameManager
 * Purpose:    Generate new game
 * Parameters: int boardSize - the side size of board
 *       String outputBoard - file to saved to
 *       Random random - random number generator
 */

public GameManager(int boardSize, String outputBoard, Random random) {

        if ( boardSize < 2) //to account for an invalid boardSize
        {
                boardSize = 4;
        }

        this.board = new Board(boardSize, random); //new Board
        this.outputFileName=outputBoard;         //File to save the board
}

/* Constructor Header Comments
 * Name:       GameManager
 * Purpose:    Load a saved game
 * Parameters:  String inpputBoard - file loaded
 *       String outputBoard - filed to save to
 *       Random random -random number generator
 */
// GameManager Constructor
// Load a saved game
public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
        System.out.println("Loading Board from " + inputBoard);
        this.board = new Board (inputBoard, random); //load board from input file
        this.outputFileName = outputBoard;         //File to save the baord
}

/* Method Header Comments
 *
 * Name:      play
 * Purpose:
 *    Main play loop
 *   Takes in input from the user to specify moves to execute
 *    valid moves are:
 *       k - Move up
 *       j - Move Down
 *       h - Move Left
 *       l - Move Right
 *       q - Quit and Save Board
 *
 *   If an invalid command is received then print the controls
 *   to remind the user of the valid moves.
 *
 *   Once the player decides to quit or the game is over,
 *   save the game board to a file based on the outputFileName
 *   string that was set in the constructor and then return
 *
 *   If the game is over print "Game Over!" to the terminal
 *
 *
 * Parameters: none
 * Return: void
 *
 */
public void play() throws IOException
{
        // Initializing variables
        int gridSize = board.GRID_SIZE;
        int score = board.getScore();
        int[][] gameGrid = board.getGrid();

        // Printing controls, size, score
        this.printControls();
        System.out.println( board );

        // Taking user input
        Scanner input = new Scanner( System.in );
        while( input.hasNext() )
        {
                String toDirect = input.next();

                // If quit and save
                if( toDirect.equals( "q" ) )
                {
                        board.saveBoard( outputFileName );
                }

                // If move is valid

                //GOING UP
                else if( toDirect.equals( "k" ) )
                {
                        Direction direct = Direction.UP;
                        if( board.canMove( direct ) )
                        {
                                board.move( direct );
                                board.addRandomTile();
                                System.out.println( board );
                        }
                        else
                        {
                                System.out.println( "Error: Move is inapplicable." );
                        }
                }

                //GOING DOWN
                else if( toDirect.equals( "j" ) )
                {
                        Direction direct = Direction.DOWN;
                        if( board.canMove( direct ) )
                        {
                                board.move( direct );
                                board.addRandomTile();
                                System.out.println( board );
                        }
                        else
                        {
                                System.out.println( "Error: Move is inapplicable." );
                        }
                }

                //GOING LEFT
                else if( toDirect.equals( "h" ) )
                {
                        Direction direct = Direction.LEFT;
                        if( board.canMove( direct ) )
                        {
                                board.move( direct );
                                board.addRandomTile();
                                System.out.println( board );
                        }
                        else
                        {
                                System.out.println( "Error: Move is inapplicable." );
                        }
                }

                //GOING RIGHT
                else if( toDirect.equals( "l" ) )
                {
                        Direction direct = Direction.RIGHT;
                        if( board.canMove( direct ) )
                        {
                                board.move( direct );
                                board.addRandomTile();
                                System.out.println( board );
                        }
                        else
                        {
                                System.out.println( "Error: Move is inapplicable." );
                        }
                }

                // If move is invalid
                else
                {
                        System.out.println( "Error: Move is invalid." );
                        this.printControls();
                }

                // If the game is over
                if( board.isGameOver() )
                {
                        return;
                }
        }
}


// Print the Controls for the Game
private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    k - Move Up");
        System.out.println("    j - Move Down");
        System.out.println("    h - Move Left");
        System.out.println("    l - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
}
}
