/**
 * Sample Board
 * <p/>
 *     0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.*;
import java.io.*;
import java.lang.*;

public class Board {
public final int NUM_START_TILES = 2;
public final int TWO_PROBABILITY = 90;
public final int GRID_SIZE;


private final Random random;
private int[][] grid;
private int score;

/* Constructor Header Comments
 * Name:        Board
 * Purpose:     Constructs a fresh board with random tiles
 * Parameters:  int boardSize - the side size of the board
 *              Random random -random number generator
 *
 */

public Board(int boardSize, Random random) {

        this.random = random;
        GRID_SIZE = boardSize;
        this.score =0;
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i=0; i<NUM_START_TILES; i++) //call the method addRandomTile
        {                                     //NUM_START_TILES times.
                addRandomTile();
        }
}

/* Constructor Header Comments
 * Name:        Board
 * Purpose:     Constructs a board off of an input file
 * Parameters:  String inputBoard - the file you take in to construct board
 *              Random random -random number generator
 *
 */

public Board(String inputBoard, Random random) throws IOException {
        this.random = random;
        File sourceFile = new File (inputBoard);
        // create a file object scanner can read
        Scanner input = new Scanner (sourceFile);
        //read the file

        GRID_SIZE = input.nextInt();
        this.score = input.nextInt();
        this.grid = new int [GRID_SIZE][GRID_SIZE];
        //create a new int 2D array grid
        for(int row = 0; row < GRID_SIZE; row++)
        {
                for (int column = 0; column < GRID_SIZE; column++)
                {
                        grid[row][column]=input.nextInt();
                }
        }
        //runs through the loop and assign the numbers in the file
        //to grid

}

/* Method Header Comments
 * Name:         saveBoard
 * Purpose:      Saves the current board to a file
 * Parameters:   String outputBoard - the board file we save to
 * Return:       void
 *
 */

public void saveBoard(String outputBoard) throws IOException {

        //create a print writer object
        PrintWriter output = new PrintWriter(outputBoard);
        //use a print writer to print out the board
        output.println(GRID_SIZE);
        //print grid size on the first line
        output.println(score);
        //print out the score on the second line
        for (int row = 0; row < GRID_SIZE; row++)
        {
                for (int column = 0; column < GRID_SIZE; column++)
                {
                        output.print(grid[row][column]+" ");
                }
                output.println();
        }
        //print out the int in each 2D array cell, as I loop through grid
        output.close();
        //close printwriter, so the output will be saved to the board file
}

/* Method Header Comments
 * Name:         addRandomTile
 * Purpose:      Add a random tile either of value 2 or 4 to a
 *        random empty space on the board
 * Parameters:   none
 * Return:       void
 *
 */

public void addRandomTile() {
        int count = 0;

        for (int row = 0; row < GRID_SIZE; row++)
        {
                for (int column =0; column < GRID_SIZE; column++)
                {
                        if (grid[row][column]==0)
                        {
                                count++;
                        }
                }
        }
        //go through the grid, count how many 0s are in the board,
        //increment count if there is a 0 tile.
        if (count == 0)
        {
                return;
        }
        //if count is 0, exit the method

        int location = random.nextInt(count);
        int value = random.nextInt(100);
        // location is a random number from 0 to count-1
        // value is a random value from 0 to 99

        int tile = 0; //tile value
        if (value < TWO_PROBABILITY)
        {
                tile = 2;
        }else
        {
                tile = 4;
        }
        //90% chance of getting a 2
        //10% chance of getting a 4

        int loop = 0; // loop is the number that will be incremented
        //by how many times the loop is ran, if the if
        //statement holds

        for (int row = 0; row < GRID_SIZE; row++) //loop through the grid array
        {
                for (int column = 0; column < GRID_SIZE; column++)
                {
                        if (grid[row][column]== 0) //if there is an empty spot
                        {
                                if (loop==location) //if the random tile is chosen
                                {
                                        grid[row][column]= tile; //assign the tile value to the
                                } //selected random tile
                                loop++; //increment loop
                        }
                }
        }

}

/* Method Header Comments
 * Name:         rotate
 * Purpose:      Rotates the board by 90 degrees clockwise or counter
 *        clockwise, depending on the boolean statement
 *
 * Parameters:   boolean rotateClockwise - true value -> rotate clockwise
 *              false value ->rotate counter
 *                     clockwise
 * Return:       void
 *
 */
// Rotates the board by 90 degrees clockwise or 90 degrees counter-clockwise.
// If rotateClockwise == true, rotates the board clockwise , else rotates
// the board counter-clockwise
public void rotate(boolean rotateClockwise) {
        int [][] temp = new int[GRID_SIZE][GRID_SIZE];
        //create a new temp int 2D array to store the values we are getting
        if (rotateClockwise) //if boolean is true
        {
                for (int row= 0; row < GRID_SIZE; row++)//loop through the 2D array
                {
                        for(int column=0; column<GRID_SIZE; column++)
                        {
                                temp[row][column]=grid[GRID_SIZE-column-1][row];
                                //grab a value from grid and store it in temp
                        }
                }
                this.grid = temp; //assign the changed array back to grid
        }else{ //else rotate counter clockwise
                for (int row= 0; row < GRID_SIZE; row++) //loop through 2D array
                {
                        for(int column=0; column<GRID_SIZE; column++)
                        {
                                temp[row][column]=grid[column][GRID_SIZE-row-1];
                                // grab a value from grid and store it in temp
                        }
                }
                this.grid = temp; //assign the changed array back to grid
        }
}

//Complete this method ONLY if you want to attempt at getting the extra credit
//Returns true if the file to be read is in the correct format, else return
//false
public static boolean isInputFileCorrectFormat(String inputFile) {
        //The try and catch block are used to handle any exceptions
        //Do not worry about the details, just write all your conditions inside the
        //try block
        try {
                //write your code to check for all conditions and return true if it satisfies
                //all conditions else return false
                return true;
        } catch (Exception e) {
                return false;
        }
}


/* Method Header Comments
 * Name:       move
 * Purpose:    Performs the move Operation
 * Parameters: Direction direction - the direction we pass in,
 *     UP, DOWN, LEFT, or RIGHT.
 * Return:
 *
 *
 */
public boolean move(Direction direction) {
        //if can move in the direction passed in
        if (canMove(direction))
        {
                if(direction==Direction.UP)
                {
                        return moveUp();

                }else if(direction==Direction.DOWN)
                {

                        return moveDown();

                }else if(direction==Direction.LEFT)
                {

                        return moveLeft();

                }else if(direction==Direction.RIGHT)
                {

                        return moveRight();

                }
        }

        return false;
}

/* Method Header Comments
 * Name:       isGameOver
 * Purpose:    Check to see if the game is over.
 * Parameters: None
 * Return:     true if it can move in any direction,
 *             false if it can't move.
 *
 */
public boolean isGameOver() {
        //if we cannot move in any directions,
        //then return false, meaning game is over.
        if (canMove(Direction.UP))
        {
                return false;
        } else if ( canMove(Direction.DOWN))
        {
                return false;
        } else if (canMove(Direction.LEFT))
        {
                return false;
        } else if (canMove(Direction.RIGHT))
        {
                return false;
        }else
        {
                System.out.println("Game Over!");
                System.out.println("Your final score is: "+this.score);
                return true;
        }

}

/* Method Header Comments
 * Name:       canMove
 * Purpose:    Determine if we can move in a given direction
 * Parameters: Direction direction - the direction we pass in,
 *     UP, DOWN, LEFT, or RIGHT.
 * Return:     true if it can move in any direction,
 *             false if it can't move.
 *
 */

public boolean canMove(Direction direction) {
        if (direction == Direction.UP)
        {
                //if the direction passed in is UP, and it can move up

                return canMoveUp();

        }else if (direction == Direction.DOWN)
        {
                //if the direction passed in is DOWN, and it can move down

                return canMoveDown();

        }else if (direction == Direction.LEFT)
        {
                //if the direction passed in is LEFT, and it can move left

                return canMoveLeft();


        }else if (direction == Direction.RIGHT)
        {
                //if the direction passed in is RIGHT, and it can move right

                return canMoveRight();


        }
        //if none of the cases were true, return false

        return false;

}

// Return the reference to the 2048 Grid
public int[][] getGrid() {
        return this.grid;
}

// Return the score
public int getScore() {
        return this.score;
}



/* Method Header Comments
 * Name:       canMoveLeft
 * Purpose:    Determine if we can move left.
 * Parameters: None
 * Return:     true if it can move left,
 *             false if it can't.
 *
 */
private boolean canMoveLeft() {
        //loop through each row first, then column
        for (int row = 0; row <GRID_SIZE; row++)
        {
                for (int column = 0; column < GRID_SIZE-1; column++)
                {
                        int leftTile = grid[row][column];
                        int rightTile = grid[row][column+1];

                        //1st case:  if the left tile is 0, and there are
                        //any non zero tiles on the right of it.
                        if (leftTile == 0 && rightTile > 0)
                        {
                                return true;

                                //2nd case: if a tile has a value of x, and the tile on the right
                                //side of the tile also has a value of x.
                        }else if (leftTile >0 && rightTile >0 && leftTile == rightTile)
                        {
                                return true;
                        }
                }
        }
        return false;
}



/* Method Header Comments
 * Name:       canMoveRight
 * Purpose:    Determine if we can move right
 * Parameters: None
 * Return:     true if it can move right,
 *             false if it can't.
 *
 */
private boolean canMoveRight(){
        //loop through each row first, then column
        for (int row = 0; row <GRID_SIZE; row++)
        {
                for (int column = 0; column <GRID_SIZE -1; column++)
                {
                        int leftTile = grid[row][column];
                        int rightTile = grid[row][column+1];

                        //1st case:  if the right tile is 0, and there are
                        //any non zero tiles on the left of it.
                        if ( rightTile == 0 && leftTile > 0 )
                        {
                                return true;

                                //2nd case: if a tile has a value of x, and the tile on the left
                                //side of the tile also has a value of x.
                        }else if (rightTile > 0&& leftTile >0 && rightTile == leftTile)
                        {
                                return true;
                        }
                }
        }
        return false;
}



/* Method Header Comments
 * Name:       canMoveUp
 * Purpose:    Determine if we can move up
 * Parameters: None
 * Return:     true if it can move up,
 *             false if it can't.
 *
 */
private boolean canMoveUp() {
        //loop through each column and row
        for (int row = 0; row <GRID_SIZE-1; row++)
        {
                for (int column = 0; column < GRID_SIZE; column++)
                {
                        int upperTile = grid[row][column];
                        int lowerTile = grid[row+1][column];

                        //1st case:  if the upper tile is 0, and there are
                        //any non zero tiles below it.
                        if (upperTile ==0 && lowerTile > 0)
                        {
                                return true;

                                //2nd case: if a tile has a value of x, and the tile below
                                //the tile also has a value of x.
                        }else if (upperTile > 0&& lowerTile >0 && upperTile == lowerTile )
                        {
                                return true;
                        }
                }
        }
        return false;
}



/* Method Header Comments
 * Name:       canMoveDown
 * Purpose:    Determine if we can move down
 * Parameters: None
 * Return:     true if it can move down,
 *             false if it can't.
 *
 */
private boolean canMoveDown() {
        //loop through each column and row
        for (int row = 0; row <GRID_SIZE-1; row++)
        {
                for (int column = 0; column < GRID_SIZE; column++)
                {
                        int upperTile = grid[row][column];
                        int lowerTile = grid[row+1][column];

                        //1st case:  if the lower tile is 0, and there are
                        //any non zero tiles above it.
                        if (lowerTile == 0 && upperTile >0)
                        {
                                return true;

                                //2nd case: if a tile has a value of x, and the tile above
                                //the tile also has a value of x.
                        }else if (lowerTile > 0&& upperTile>0 & upperTile == lowerTile)
                        {
                                return true;
                        }
                }
        }
        return false;
}


/* Method Header Comments
 * Name:       moveLeft
 * Purpose:    Perform move operation to the left
 * Parameters: None
 * Return:     true if it's moved.
 *        false if it's not moved
 *
 *
 */
private boolean moveLeft(){
        //arraylist size without zeroes
        //create a new ArrayList of Integer Object
        //loop through the row
        for(int row=0; row<GRID_SIZE; row++)
        {
                ArrayList<Integer> intArray = new ArrayList<Integer> ();
                for(int column=0; column< GRID_SIZE; column++)
                {
                        //add non-zero tiles to array
                        if(grid[row][column]!=0)
                        {
                                intArray.add(grid[row][column]);
                        }
                }
                //compare the left and right tiles, combine them
                //if they are the same.
                for (int i= 0; i<intArray.size()-1; i++)
                {
                        int right= intArray.get(i+1);
                        int left= intArray.get(i);

                        if(left==right)
                        {
                                int sum = left+right;
                                intArray.set(i,sum);
                                intArray.remove(i+1);
                                this.score += sum;
                                i++;
                        }
                }
                //add the filler zero
                int arraysize= intArray.size();
                for(int filler=GRID_SIZE-arraysize; filler>0; filler--)
                {
                        intArray.add(0);
                }
                //assigning it back to the grid
                for(int j=0; j<intArray.size(); j++)
                {
                        grid[row][j]=intArray.get(j);
                }
        }
        return true;
}


/* Method Header Comments
 * Name:       moveRight
 * Purpose:    Perform move operation to the right
 * Parameters: None
 * Return:     true if it's moved.
 *
 *
 */

private boolean moveRight(){
        //arraylist size without zeroes
        //create a new ArrayList of Integer Object
        //loop through the row
        for(int row=0; row<GRID_SIZE; row++)
        {
                ArrayList<Integer> intArray = new ArrayList<Integer> ();
                for(int column=0; column< GRID_SIZE; column++)
                {
                        //add non-zero tiles to array
                        if(grid[row][column]!=0)
                        {
                                intArray.add(grid[row][column]);
                        }
                }
                //compare the left and right tiles, combine them
                //if they are the same.
                for (int i= intArray.size()-1; i>0; i--)
                {
                        int left= intArray.get(i-1);
                        int right= intArray.get(i);

                        if(left==right)
                        {
                                int sum = left+right;
                                intArray.set(i,sum);
                                intArray.remove(i-1);
                                this.score += sum;
                                i--;
                        }
                }
                //add the filler zero
                int arraysize= intArray.size();
                for(int filler=0; filler< GRID_SIZE-arraysize; filler++)
                {
                        intArray.add(filler,0);
                }
                //assigning it back to the grid
                for(int j=0; j<intArray.size(); j++)
                {
                        grid[row][j]=intArray.get(j);
                }
        }
        return true;
}



/* Method Header Comments
 * Name:       moveUp
 * Purpose:    Perform move operation upward
 * Parameters: None
 * Return:     true if it's moved.
 *
 *
 */

private boolean moveUp(){

        //arraylist size without zeroes
        //create a new ArrayList of Integer Object
        //loop through the column
        for(int column=0; column<GRID_SIZE; column++)
        {
                ArrayList<Integer> intArray = new ArrayList<Integer> ();
                for(int row=0; row< GRID_SIZE; row++)
                {
                        //add non-zero tiles to array
                        if(grid[row][column]!=0)
                        {
                                intArray.add(grid[row][column]);
                        }
                }
                //compare the below and above tiles, combine them
                //if they are the same.
                for (int i= 0; i<intArray.size()-1; i++)
                {
                        int below= intArray.get(i+1);
                        int above= intArray.get(i);

                        if(above==below)
                        {
                                int sum = above+below;
                                intArray.set(i,sum);
                                intArray.remove(i+1);
                                this.score += sum;
                                i++;
                        }
                }
                //add the filler zero
                int arraysize= intArray.size();
                for(int filler=GRID_SIZE-arraysize; filler>0; filler--)
                {
                        intArray.add(0);
                }
                //assigning it back to the grid
                for(int j=0; j<intArray.size(); j++)
                {
                        grid[j][column]=intArray.get(j);
                }
        }
        return true;


}


/* Method Header Comments
 * Name:       moveDown
 * Purpose:    Perform move operation to the left
 * Parameters: None
 * Return:     boolean
 *
 *
 */

private boolean moveDown(){
        //create a new ArrayList of Integer Object
        //loop through the column
        for(int column=0; column<GRID_SIZE; column++)
        {
                ArrayList<Integer> intArray = new ArrayList<Integer> ();
                for(int row=0; row< GRID_SIZE; row++)
                {
                        //add non-zero tiles to array
                        if(grid[row][column]!=0)
                        {
                                intArray.add(grid[row][column]);
                        }
                }
                //compare the below and above tiles, combine them
                //if they are the same.
                for (int i= intArray.size()-1; i>0; i--)
                {
                        int below= intArray.get(i);
                        int above= intArray.get(i-1);

                        if(above==below)
                        {
                                int sum = above+below;
                                intArray.set(i,sum);
                                intArray.remove(i-1);
                                this.score += sum;
                                i--;
                        }
                }
                //add the filler zero
                int arraysize= intArray.size();
                for(int filler=0; filler<GRID_SIZE-arraysize; filler++)
                {
                        intArray.add(filler,0);
                }
                //assigning it back to the grid
                for(int j=0; j<intArray.size(); j++)
                {
                        grid[j][column]=intArray.get(j);
                }
        }
        return true;
}

@Override
public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
                for (int column = 0; column < GRID_SIZE; column++)
                        outputString.append(grid[row][column] == 0 ? "    -" :
                                            String.format("%5d", grid[row][column]));

                outputString.append("\n");
        }
        return outputString.toString();
}
}
