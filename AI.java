import java.util.Random;

/**
 * AI class that can calculate a connect 4 move
 *
 * @author Kuno de Leeuw-Kent
 * @version v2.2.1
 */

public class AI {

    // initializes the constants
    static final int Min = -2147483648;
    static final int Max = 2147483647;
    static protected int totalConsidered = 0;
    static int search_Depth = 0;
    static int AI_Diff = 0;
    static int count = 0;
    static protected int ThreadsCompleted = 0;
    static long roughtotalConsidered = 0;
    static int[] scores = new int[main.board_Width];

    public AI() {

    }

    /**
     * The main subroutine for the AI class
     * 
     * @param game_Board     : The game state array
     * @param stack_Array     : The game state stacks
     * @param depth : Search depth of the algorithm
     * @param diff  : How strong of a move to play
     * @return : The move that has been decided
     */
    public static int AI(String[][] game_Board, Stack[] stack_Array, int depth, int diff) {

        // initializes the variables
        search_Depth = depth;
        AI_Diff = diff;
        ThreadsCompleted = 0;
        roughtotalConsidered = 0;
        totalConsidered = 0;
        scores = new int[main.board_Width];

        // storing the start time of the computation so total computation time can be
        // calculated
        long start_Time = System.currentTimeMillis();

        boolean wait = true;
        int count = 0;

        // for every posible move the AI could make
        for (int i = 1; i <= main.board_Width; i++) {

            wait = true;

            while (wait) {

                if (((i - 1) - ThreadsCompleted) < main.threads) {
                    wait = false;
                }

                // If there there is avalible threads
                if (!wait) {

                    // create a clone of the gameState and the stackist
                    String[][] AI_gameState = board.clone_2D_Array(game_Board);
                    Stack[] AI_stackList = board.clone_Stackarray(stack_Array);

                    // checks if the stack isn't full and the move is posible
                    if (!AI_stackList[i - 1].IsEmpty()) {

                        // creates a minimax compute node instance with the copy of the gamestate and
                        // stacklist
                        Runnable r = new minimaxComputeNode(String.valueOf(i - 1),
                                board.make_Move(AI_gameState, AI_stackList, i, false), AI_stackList, 0, false);
                        // start the node
                        new Thread(r).start();

                    } else {

                        // if the move isn't posible set the score of that move to the smallest posible
                        // value
                        scores[i - 1] = -2147483648;
                        ThreadsCompleted++;
                    }
                }
                // If there there is no avalible threads
                else {
                    // pause this thread for a millisecond
                    try {
                        Thread.sleep(1);

                    } catch (InterruptedException e) {

                        Console.println("Thread Interrupted");
                    }
                    count++;
                }
            }
        }
        // Once all the threads have started check for them to complete
        while ((ThreadsCompleted) != main.board_Width) {
            try {
                Thread.sleep(0, 250);

            } catch (InterruptedException e) {

                Console.println("Thread Interrupted");
            }

            count++;
        }

        // stores the end time so diffence can be calculated
        long end_Time = System.currentTimeMillis();

        Console.println("AI's evaluation:");

        for (int i = 1; i <= main.board_Width; i++) {
            // Prints score on to console
            if (scores[i - 1] != -2147483648) {

                // prints the calculated scores to the console
                // making sure the numbers all start on the same colum
                if (scores[i - 1] == 0) {

                    Console.println(i + ": 0");

                } else if (scores[i - 1] > 0) {

                    Console.println(i + ":+" + scores[i - 1]);

                } else {

                    Console.println(i + ":" + scores[i - 1]);
                }
            } else {

                // if the move didn't get a score print "N/A"
                Console.println(i + ": N/A");
            }
        }
        // Line spacing
        Console.println();

        // prints usefull information to the console
        System.out.println("AI took " + (end_Time - start_Time) + " milliseconds to complete");
        Console.println(totalConsidered + " possibilities considered, up to " + (search_Depth + 1) + " moves ahead");

        totalConsidered = 0;

        return make_Choice(scores);

    }

    // setter for the search depth setting
    public static void changeDepth(int x) {

        search_Depth = x;
    }

    // setter for the difficulty setting
    public static void changeDiff(int x) {

        AI_Diff = x;
    }

    /**
     * Add's randomness to the move choosing, also has a auto pass threshhold
     * dependent on diff level
     * 
     * @param scores the array of the scores
     * @return the score that has been chosen
     */
    public static int make_Choice(int[] scores) {

        Random Random = new Random();

        int[] moves = order_Moves(scores);
        int moveNum = -1;
        int diff = main.AI_Diff;
        int[] thresholds = { 1000, 800, 500, 200, 150, 100, 50, 10, 3, 0 };

        for (int i = 0; i < (main.board_Width - 1); i++) {

            int presence = Math.abs(scores[i] - scores[i + 1]);
            int ranInt = Random.nextInt(10) + 1;

            if (moveNum == -1) {

                if (scores[i] > 100000) {

                    moveNum = moves[i]; 
                } else if (presence >= thresholds[diff - 1]) {

                    moveNum = moves[i];
                    
                } else if (ranInt <= diff) {

                    moveNum = moves[i];
                }
            }
        }

        if (moveNum == -1) {

            moveNum = moves[main.board_Width - 1];
        }
        Console.println();
        Console.println("AI move played:");

        UI.printArrow(moveNum);

        return moveNum;
    }

    /**
     * Uses the buble sort algorthm find what move has the highest score
     * 
     * @param scores the array of the scores
     * @return the moves 1-board_Width in the order that scores are highest to
     *         lowest
     */
    public static int[] order_Moves(int[] scores) {

        int[] moves = new int[main.board_Width];

        for (int i = 0; i < main.board_Width; i++) {
            moves[i] = i + 1;
        }

        boolean done = false;

        while (!done) {

            int number_1;
            int number_2;
            done = true;

            for (int i = 0; i < (main.board_Width - 1); i++) {

                number_1 = scores[i];
                number_2 = scores[i + 1];

                if (number_1 >= number_2) {

                } else {
                    int temp = scores[i];
                    scores[i] = scores[i + 1];
                    scores[i + 1] = temp;
                    temp = moves[i];
                    moves[i] = moves[i + 1];
                    moves[i + 1] = temp;
                    done = false;
                }
            }
        }
        return moves;
    }
}
