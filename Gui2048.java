import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

/* Class Header
 *
 * Gui2048 is a class that use Graphic User Interface and JavaFX to create
 * the graphic for the game 2048. Before, we were only able to play the
 * game on the terminal console. However, now we can play it in GUI, with
 * all the colorful tiles and background!
 *
 *
 */


public class Gui2048 extends Application
{
//fields
private String outputBoard;     // The filename for where to save the Board
private Board board;     // The 2048 Game Board

private static final int TILE_WIDTH = 106;

private static final int TEXT_SIZE_LOW = 55;     // Low value tiles (2,4,8,etc)
private static final int TEXT_SIZE_MID = 45;     // Mid value tiles
                                                 //(128, 256, 512)
private static final int TEXT_SIZE_HIGH = 35;     // High value tiles
                                                  //(1024, 2048, Higher)

// Fill colors for each of the Tile values
private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
private static final Color COLOR_2 = Color.rgb(238, 228, 218);
private static final Color COLOR_4 = Color.rgb(237, 224, 200);
private static final Color COLOR_8 = Color.rgb(242, 177, 121);
private static final Color COLOR_16 = Color.rgb(245, 149, 99);
private static final Color COLOR_32 = Color.rgb(246, 124, 95);
private static final Color COLOR_64 = Color.rgb(246, 94, 59);
private static final Color COLOR_128 = Color.rgb(237, 207, 114);
private static final Color COLOR_256 = Color.rgb(237, 204, 97);
private static final Color COLOR_512 = Color.rgb(237, 200, 80);
private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
private static final Color COLOR_OTHER = Color.BLACK;
private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242);
// For tiles >= 8

private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101);
// For tiles < 8

private GridPane pane;

/** Add your own Instance Variables here */
private static final int TEXT_SIZE_5 = 25;     //for text with 5 charactaters
private int colspan;     //column span
private int rowspan;     //row span

private int currentScore;     //current score
private Rectangle square;    //rectangle square
private Rectangle[][] tile;    //rectangle 2D array
private Text value;     //Text with the number
private Text[][] number;     //Text 2D array
private Text score;     //the score converted into Text

private double factor3;     //ExtraCredit 1A works for GRID_SIZE 5,6...
private Scene scene;     //the scene that's displayed




/* Method Header Comments
 * Name:       start
 * Purpose:    Creating the gui, similar purpose as the play()
 *             in GameManager.java (PSA3-4)
 * Parameters: Stage primaryStage -takes in the Stage object
 *                                 and modify it
 * Return:     void
 *
 *
 */

@Override
public void start(Stage primaryStage)
{
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        // Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setStyle("-fx-background-color: rgb(187, 173, 160)");
        // Set the spacing between the Tiles
        if(board.GRID_SIZE<=4)
        {
                pane.setHgap(15);
                pane.setVgap(15);

                //Extra Credit Part1A
        }else if(board.GRID_SIZE>4)
        {
                double factor1 = 16/(double)(board.GRID_SIZE*board.GRID_SIZE);
                pane.setHgap(15*factor1);
                pane.setVgap(15*factor1);
        }

        /** Add your Code for the GUI Here */

//Setting up the window

        scene = new Scene(pane);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("Gui2048");
        primaryStage.setScene(scene);
        primaryStage.show();


//Title and Score

        //Title, color, font, position.
        Text title = new Text();
        title.setText("2048");
        final int titleS = 50; //Title Size
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, titleS));
        title.setFill(COLOR_VALUE_DARK);
        pane.add(title, 0, 0, 2, 1);
        GridPane.setHalignment(title, HPos.CENTER);


        //initialize instance variables
        this.colspan = board.GRID_SIZE;
        this.rowspan = board.GRID_SIZE+1;
        this.tile = new Rectangle[rowspan][colspan];
        this.number = new Text[rowspan][colspan];

        //Score, color, font position
        this.score = new Text();
        this.score.setText("Score:" + board.getScore());//Score color
        final int scoreS = 30;
        this.score.setFont(Font.font("Times New Roman", FontWeight.BOLD,
                                     scoreS));
        this.score.setFill(COLOR_VALUE_DARK);
        pane.add(this.score, colspan-2,0,2,1);
        GridPane.setHalignment(this.score, HPos.CENTER);


        //getGrid from the 2048 Board
        int[][] grid =board.getGrid();

        //loop through the grid to get the values on the board
        for(int row =0; row < board.GRID_SIZE; row++)
        {
                for(int col =0; col<board.GRID_SIZE; col++)
                {
                        int num = grid[row][col];

                        //Extra Credit Part1A
                        if(board.GRID_SIZE<=4)
                        {
                                this.factor3 = 1.0;
                        }else if(board.GRID_SIZE>4)
                        {
                                this.factor3 = (16/(double)(board.GRID_SIZE*board.GRID_SIZE))
                                               *1.25;
                        }


                        //update the rectangle objects and text objects
                        this.square = new Rectangle ();
                        if (board.GRID_SIZE<=4)
                        {
                                this.square.setWidth(TILE_WIDTH);
                                this.square.setHeight(TILE_WIDTH);
                                //cases where GRID_SIZE is great than 4
                                //Extra Credit Part1A
                        } else if(board.GRID_SIZE>4)
                        {
                                double factor = 16/(double)(board.GRID_SIZE*board.GRID_SIZE);
                                this.square.setWidth(TILE_WIDTH*factor);
                                this.square.setHeight(TILE_WIDTH*factor);
                        }
                        this.value = new Text();


                        // case 0: set the color to empty, no text
                        if (num ==0)
                        {
                                this.square.setFill(COLOR_EMPTY);
                                this.value.setText("");

                                //case 2: set the text to 2, text color to COLOR_VALUE_DARK,
                                //rectangle color to COLOR_2
                        }else if (num ==2)
                        {
                                this.square.setFill(COLOR_2);
                                this.value.setText("2");
                                this.value.setFont(Font.font("Times New Roman",
                                                             FontWeight.BOLD, TEXT_SIZE_LOW*this.factor3));
                                this.value.setFill(COLOR_VALUE_DARK);

                                ////case 4: set the text to 4, text color to COLOR_VALUE_DARK,
                                //rectangle color to COLOR_4.
                        }else if (num ==4)
                        {
                                this.square.setFill(COLOR_4);
                                this.value.setText("4");
                                this.value.setFont(Font.font("Times New Roman",
                                                             FontWeight.BOLD, TEXT_SIZE_LOW*this.factor3));
                                this.value.setFill(COLOR_VALUE_DARK);
                        }
                        //align value to the center
                        GridPane.setHalignment(this.value, HPos.CENTER);
                        //adding all the  this.square to the Rectangle
                        this.tile[row+1][col]=this.square;
                        this.number[row+1][col]= this.value;


                        //add square and value to the grid pane
                        pane.add(this.tile[row+1][col], col, row+1, 1, 1);
                        pane.add(this.number[row+1][col], col, row+1, 1, 1);


                }
        }

        //invoke events
        scene.setOnKeyPressed(new myKeyHandler());

}

/** Add your own Instance Methods Here */


/* Method Header Comments
 * Name:       update
 * Purpose:    updating the GUI each time after move method is called
 * Parameters: none
 * Return:     void
 *
 *
 */

public void update()
{
        //get the 2D array from Board, and assign it to the new 2D array
        //grid
        int[][] grid = board.getGrid();

        //loop through the grid
        for(int row =0; row < board.GRID_SIZE; row++)
        {
                for(int col =0; col<board.GRID_SIZE; col++)
                {
                        //to get each of the grid's number in the 2D array
                        //assign it to num
                        int num = grid[row][col];
                        int roow = row+1;

                        //act according to different cases of num
                        switch (num)
                        {
                        // case 0: set the color to empty, no text
                        case 0:

                                this.tile[roow][col].setFill(COLOR_EMPTY);
                                this.number[roow][col].setText("");
                                break;

                        //case 2: set the text to 2, text color to COLOR_VALUE_DARK,
                        //rectangle color to COLOR_2, font to TEXT_SIZE_LOW
                        case 2:

                                this.tile[roow][col].setFill(COLOR_2);
                                this.number[roow][col].setText("2");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_LOW*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_DARK);
                                break;

                        //case 4: set the text to 4, text color to COLOR_VALUE_DARK,
                        //rectangle color to COLOR_4, font to TEXT_SIZE_LOW
                        case 4:

                                this.tile[roow][col].setFill(COLOR_4);
                                this.number[roow][col].setText("4");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_LOW*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_DARK);
                                break;

                        //case 8: set the text to 8, text color to COLOR_VALUE_DARK,
                        //rectangle color to COLOR_8, font to TEXT_SIZE_LOW
                        case 8:

                                this.tile[roow][col].setFill(COLOR_8);
                                this.number[roow][col].setText("8");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_LOW*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_DARK);
                                break;

                        //case 16: set the text to 16, text color to COLOR_VALUE_LIGHT,
                        //rectangle color to COLOR_16, font to TEXT_SIZE_LOW
                        case 16:

                                this.tile[roow][col].setFill(COLOR_16);
                                this.number[roow][col].setText("16");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_LOW*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 32: set the text to 32, text color to COLOR_VALUE_LIGHT,
                        //rectangle color to COLOR_32, font to TEXT_SIZE_LOW
                        case 32:

                                this.tile[roow][col].setFill(COLOR_32);
                                this.number[roow][col].setText("32");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_LOW*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 64: set the text to 64, text color to COLOR_VALUE_LIGHT,
                        //rectangle color to COLOR_64, font to TEXT_SIZE_LOW
                        case 64:

                                this.tile[roow][col].setFill(COLOR_64);
                                this.number[roow][col].setText("64");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_LOW*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 128: set the text to 128, text color to
                        //COLOR_VALUE_LIGHT, rectangle color to COLOR_128, font to
                        //TEXT_SIZE_MID
                        case 128:

                                this.tile[roow][col].setFill(COLOR_128);
                                this.number[roow][col].setText("128");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_MID*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 256: set the text to 256, text color to
                        //COLOR_VALUE_LIGHT, rectangle color to COLOR_256, font to
                        //TEXT_SIZE_MID
                        case 256:

                                this.tile[roow][col].setFill(COLOR_256);
                                this.number[roow][col].setText("256");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_MID*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 512: set the text to 512, text color to
                        //COLOR_VALUE_LIGHT, rectangle color to COLOR_512, font to
                        //TEXT_SIZE_MID
                        case 512:

                                this.tile[roow][col].setFill(COLOR_512);
                                this.number[roow][col].setText("512");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_MID*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 1024: set the text to 1024, text color to
                        //COLOR_VALUE_LIGHT, rectangle color to COLOR_1024, font to
                        //TEXT_SIZE_HIGH
                        case 1024:

                                this.tile[roow][col].setFill(COLOR_1024);
                                this.number[roow][col].setText("1024");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_HIGH*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 2048: set the text to 2048, text color to
                        //COLOR_VALUE_LIGHT, rectangle color to COLOR_2048, font to
                        //TEXT_SIZE_HIGH
                        case 2048:

                                this.tile[roow][col].setFill(COLOR_2048);
                                this.number[roow][col].setText("2048");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_HIGH*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 4096: set the text to 4096, text color to
                        //COLOR_VALUE_LIGHT, rectangle color to COLOR_OTHER, font to
                        //TEXT_SIZE_HIGH
                        case 4096:

                                this.tile[roow][col].setFill(COLOR_OTHER);
                                this.number[roow][col].setText("4096");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_HIGH*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 8192: set the text to 8192, text color to
                        //COLOR_VALUE_LIGHT, rectangle color to COLOR_OTHER, font to
                        //TEXT_SIZE_HIGH
                        case 8192:

                                this.tile[roow][col].setFill(COLOR_OTHER);
                                this.number[roow][col].setText("8192");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_HIGH*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 16384: set the text to 16384, text color to
                        //COLOR_VALUE_LIGHT, rectangle color to COLOR_OTHER, font to
                        //TEXT_SIZE_5
                        case 16384:

                                this.tile[roow][col].setFill(COLOR_OTHER);
                                this.number[roow][col].setText("16384");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_5*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        //case 16384: set the text to 16384, text color to
                        //COLOR_VALUE_LIGHT, rectangle color to COLOR_OTHER, font to
                        //TEXT_SIZE_5
                        case 32768:

                                this.tile[roow][col].setFill(COLOR_OTHER);
                                this.number[roow][col].setText("32768");
                                this.number[roow][col].setFont(Font.font("Times New Roman",
                                                                         FontWeight.BOLD, TEXT_SIZE_5*this.factor3));
                                this.number[roow][col].setFill(COLOR_VALUE_LIGHT);
                                break;

                        default: break;

                        }//end of switch

                }//end of inner for loop
        } //end of outer for loop


        //Updating the score
        this.score.setText("Score:" + board.getScore());
        GridPane.setHalignment(this.score, HPos.CENTER);

}

/* Method Header Comments
 * Name:       overlay
 * Purpose:    this method is invoked by the handle method at the end of
 *             the game. This method then would create a transparent
 *             layer printing GAME OVER!
 * Parameters: none
 * Return:     void
 *
 *
 */
public void overlay()
{
        //creating a new rectangle, set it to the width and height of the
        //scene, and set the color to the transparent color
        Rectangle endgameRec = new Rectangle();
        endgameRec.setWidth(500);
        endgameRec.setHeight(600);
        endgameRec.setFill(COLOR_GAME_OVER);

        //creating a new text, printing "Game Over!" with font.
        Text gameover = new Text();
        gameover.setText("Game Over!");
        gameover.setFont(Font.font("Times New Roman",FontWeight.BOLD, 60));

        //set alignment of the rectangle box, and the text
        this.pane.setHalignment(endgameRec, HPos.CENTER);
        this.pane.setHalignment(gameover, HPos.CENTER);

        //add the rectangle and text over the finished gameboard
        //at 0,0 spanning colspan and rowspan
        this.pane.add(endgameRec,0,0,this.colspan,this.rowspan);
        this.pane.add(gameover,0,0,this.colspan,this.rowspan);

        return;
}



/* Class Header
 *
 * myKeyHandler is a inner class that implements from the EventHandler
 * <KeyEvent> interface. It contains a single handle method, which is
 * overrided with the purpose of GUI2048.
 *
 */

private class myKeyHandler implements EventHandler<KeyEvent>
{

/* Method Header Comments
 * Name:       handle
 * Purpose:    detects the Key the user presses, and act accordingly
 *             base on the cases.
 * Parameters: KeyEvent e --keys pressed on the keyboard by user
 * Return:     void
 *
 *
 */
@Override
public void handle (KeyEvent e)
{
        if(board.isGameOver())
        {
                return;
        }

        switch(e.getCode())
        {
        //when press the up key, check if it can move up, if it can
        //then move up, add a random tile, print moving up, and
        //update the GUI with update() method.
        case UP: if (board.canMove(Direction.UP))
                {
                        board.move(Direction.UP);
                        board.addRandomTile();
                        System.out.println ("Moving UP");
                        update();
                }

                break;

        //when press the down key, check if it can move down, if it
        //can, then move up, add a random tile, print moving down, and
        //update the GUI with update() method.
        case DOWN: if (board.canMove(Direction.DOWN))
                {
                        board.move(Direction.DOWN);
                        board.addRandomTile();
                        System.out.println ("Moving DOWN");
                        update();
                }

                break;

        //when press the right key, check if it can move right, if it
        //can, then move right, add a random tile, print moving right,
        //and update the GUI with update() method.
        case RIGHT: if (board.canMove(Direction.RIGHT))
                {
                        board.move(Direction.RIGHT);
                        board.addRandomTile();
                        System.out.println ("Moving RIGHT");
                        update();
                }

                break;

        //when press the left key, check if it can move left, if it
        //can, then move left, add a random tile, print moving left,
        //and update the GUI with update() method.
        case LEFT: if (board.canMove(Direction.LEFT))
                {
                        board.move(Direction.LEFT);
                        board.addRandomTile();
                        System.out.println("Moving LEFT");
                        update();
                }

                break;

        //when press the "s" key on the key board, print
        //"Saving board to the output board file", catch any exception
        //and print an error meessage if any exceptions are caught
        case S: System.out.println ("Saving board to: "+outputBoard);
                try
                {
                        board.saveBoard(outputBoard);
                } catch (Exception exc)
                {
                        System.err.println ("saveBoard threw "+exc);
                }
                break;

        //when press the "r" key on the key board, rotate the board,
        //print "Rotating board"

        case R: board.rotate(true);
                update();
                System.out.println("Rotating the board");
                break;

        //when a key not listed above is pressed, print out
        //"Invalid move"
        default: System.out.println("Invalid Move");
                break;

        }//end of switch
        if (board.isGameOver())
        {
                overlay();
                return;
        }

}
}






/** DO NOT EDIT BELOW */

// The method used to process the command line arguments
private void processArgs(String[] args)
{
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
                printUsage();
                System.exit(-1);
        }

        // Process all the arguments
        for(int i = 0; i < args.length; i += 2)
        {
                if(args[i].equals("-i"))
                { // We are processing the argument that specifies
                  // the input file to be used to set the board
                        inputBoard = args[i + 1];
                }
                else if(args[i].equals("-o"))
                { // We are processing the argument that specifies
                  // the output file to be used to save the board
                        outputBoard = args[i + 1];
                }
                else if(args[i].equals("-s"))
                { // We are processing the argument that specifies
                  // the size of the Board
                        boardSize = Integer.parseInt(args[i + 1]);
                }
                else
                { // Incorrect Argument
                        printUsage();
                        System.exit(-1);
                }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
                outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
                boardSize = 4;

        // Initialize the Game Board
        try{
                if(inputBoard != null)
                        board = new Board(inputBoard, new Random());
                else
                        board = new Board(boardSize, new Random());
        }
        catch (Exception e)
        {
                System.out.println(e.getClass().getName() +
                                   " was thrown while creating a " +
                                   "Board from file " + inputBoard);
                System.out.println("Either your Board(String, Random) " +
                                   "Constructor is broken or the file isn't " +
                                   "formated correctly");
                System.exit(-1);
        }
}

// Print the Usage Message
private static void printUsage()
{
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " +
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " +
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " +
                           "default \"2048.board\" file will be used");
        System.out.println("  -s [size]  -> Specifies the size of the 2048" +
                           "board if an input file hasn't been");
        System.out.println("                specified.  If both -s and -i" +
                           "are used, then the size of the board");
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
}
}
