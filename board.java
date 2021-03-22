/**
 * Board class for the Connect 4 game
 * 
 * @author Kuno de Leeuw-Kent
 */
public class board {

    String[][] gameState = new String[main.board_Hight][main.board_Width];
    Stack[] stackList = new Stack[main.board_Width];
    static int[] Center_W_Scores = gen_Center_W_Scores();
    String string;
    int number;

    /**
     * Constructor for objects of class board
     * 
     * Sets up the gameboard
     */
    public board() {

        populate_Empty_Board();
        populate_stacks();
        Center_W_Scores = gen_Center_W_Scores();
    }

    /**
     * generate center wieghting scores
     */
    public static int[] gen_Center_W_Scores() {

        int[] center_W_Scores = new int[main.board_Width];
        int center = main.board_Width / 2;
        center += (main.board_Width % center);

        for (int i = 0; i < center; i++) {

            center_W_Scores[i] = (i + 1);
        }
        if (main.board_Width % 2 == 0) {

            for (int i = center; i < main.board_Width; i++) {

                center_W_Scores[i] = center - (i - center);
            }
        } else {
            for (int i = center; i <= main.board_Width; i++) {

                center_W_Scores[i - 1] = center - (i - center);
            }
        }
        return center_W_Scores;
    }

    /**
     * Calls UI to display the current gameboard
     */
    public void display_Current_Board() {

        UI.displayboard(gameState, false);
    }

    /**
     * Getter for the current gameboard
     * 
     * @return : The current gameboard array
     */
    public String[][] get_Game_State() {

        return gameState;
    }

    /**
     * Getter for the current stackList
     * 
     * @return : The current stackList
     */
    public Stack[] get_Stacks() {

        return stackList;
    }

    /**
     * A Setter for the gameboard Array
     * 
     * @param A : The 2D array that has been updated
     */
    public void update_Board(String[][] A) {

        gameState = A;
    }

    /**
     * Set the value of each entiry to " " so it can be displayed corectly
     */
    public void populate_Empty_Board() {

        for (int i = 0; i < main.board_Hight; i++) {

            for (int j = 0; j < main.board_Width; j++) {

                gameState[i][j] = " ";
            }
        }
    }

    public void populate_stacks() {

        for (int i = 0; i < main.board_Width; i++) {

            stackList[i] = new Stack(main.board_Hight);

            for (int j = 0; j <= main.board_Hight; j++) {

                stackList[i].Push(j);
            }
        }
    }

    /**
     * Clones the gameState array because of java's value refrencing
     * 
     * @param game_Board : The array to clone
     * @return : Clone of the array
     */
    public static String[][] clone_2D_Array(String[][] game_Board) {
        String[][] clone = new String[game_Board.length][game_Board[0].length];

        for (int i = 0; i < game_Board.length; i++) {

            for (int j = 0; j < game_Board[0].length; j++) {

                clone[i][j] = game_Board[i][j];
            }
        }
        return clone;
    }

    /**
     * Clones the stack List because of java's value referencing
     * 
     * @param stack_Array : The stack list to clone
     * @return : The cloned stack list
     */
    public static Stack[] clone_Stackarray(Stack[] stack_Array) {
        Stack[] clone = new Stack[stack_Array.length];

        for (int i = 0; i < main.board_Width; i++) {

            clone[i] = new Stack(main.board_Hight);

            for (int j = 0; j < main.board_Hight; j++) {

                clone[i].Push(stack_Array[i].get(j));
            }
            clone[i].set_Top(stack_Array[i].top());
        }
        return clone;
    }

    /**
     * Colects the users move mid game and also deals with commmands given "/help"
     * 
     * If the user give a number then the program will use that number as the move
     * played IF the user give anything starting with '/' then it is taken as a
     * command
     * 
     * @return The move played by user
     */
    public int get_Player_Response() throws Exception {

        Console.println();

        boolean valid = false;
        boolean number = false;
        int intVal = 0;

        while (!valid) {

            Console.println("Make move, /exit or /help");
            string = Console.readLine();
            string = string.toLowerCase();

            // Tries to get the number if the whole string is a number
            try {
                intVal = Integer.valueOf(string);
                number = true;
            } catch (Exception e) {
            }

            if (string.equals("")) {

                Console.println("empty field");
            }

            // Decting if there is a command
            else if (string.substring(0, 1).contains("/")) {

                if (string.contains("/exit")) {

                    main.game_Run(false);
                    return 1;

                } else if (string.contains("/cords")) {

                    end_Game_State_Detection(gameState, stackList, true);

                } else if (string.contains("/moves")) {

                    Console.println(main.turn_Num);

                } else if (string.contains("/excpt_test")) {

                    throw new Exception();

                } else if (string.contains("/diff")) {

                    if (string.equals("/diff")) {

                        Console.println("Diff = " + main.AI_Diff);
                        Console.println();
                    }
                    try {

                        int AI_Diff = Integer.valueOf(string.substring(6, string.length()));

                        if (AI_Diff <= 10 && AI_Diff >= 1) {

                            main.AI_Diff = AI_Diff;
                            Console.println("Difficalty = " + AI_Diff);

                        } else {
                            Console.println("value out of range 10-1");
                            Console.println();
                        }

                    } catch (Exception e) {

                        Console.println("Error in number given");
                        Console.println("Usage /diff 'number'");
                    }
                } else if (string.contains("/depth")) {

                    if (string.equals("/depth")) {
                        Console.println("Depth = " + (main.AI_prsn + 1));
                        Console.println();
                    }
                    try {

                        int AI_prsn = Integer.valueOf(string.substring(7, string.length()));
                        if (AI_prsn <= 9 && AI_prsn >= 3) {
                            main.AI_prsn = (AI_prsn - 1);
                            Console.println("Depth = " + AI_prsn);
                        } else {

                            Console.println("value out of range 3-7");
                            Console.println("Usage /depth 'number'");
                        }

                    } catch (Exception e) {

                        Console.println("Error in number given");
                        Console.println("Usage /depth 'number'");
                    }
                } else if (string.contains("/help")) {
                    Console.println();
                    Console.println("Tip: you win the game by winning");
                    Console.println();
                    Console.println("/help: Display this list");
                    Console.println("/diff 'number': Change the difficalty level (1-10)");
                    Console.println("/depth 'number': Change the AI depth (3-7)");
                    Console.println("/cords: Displays the coordinates of every counter");
                    Console.println("/moves: Displays the amount moves made");
                    Console.println("/excpt_test: tests the error corection");
                    Console.println("/exit: exits the current game");
                    Console.println();

                } else {

                    Console.println("Unknown command");
                    Console.println("/help for help");
                }
            } else if (number) {

                if (intVal < 1 || intVal > main.board_Width) {

                    Console.println("error incorrect response");

                } else if (!valid_Move(get_Stacks(), intVal)) {

                    Console.println("invalid move");

                } else {

                    valid = true;
                }
            } else {

                Console.println("error incorrect response");
            }
        }
        Console.println();
        return intVal;
    }

    /**
     * Adds a counter on a cloumn on the lowest free row it uses a Stack filled with
     * desending numbers the number indicates the row to put the couter in
     * 
     * @param game_Board Game Board
     * @param stack_Array Stack list
     * @param movePos Move to make
     * @param is_Xs_Move Is it a "X" T/F?
     * 
     * @return New game board
     */
    public static String[][] make_Move(String[][] game_Board, Stack[] stack_Array, int movePos, boolean is_Xs_Move) {
      
        String string = "";

        if (is_Xs_Move) {

            string = "X";

        } else {

            string = "O";
        }

        game_Board[stack_Array[movePos - 1].Pop()][movePos - 1] = string;
        return game_Board;
    }

    /**
     * Checks if a specific move is valid but checking if the stack list for that
     * column is empty. Empty Stack = full column
     * 
     * @param game_board The Stack list
     * @param move The Move to check
     * 
     * @return true or false if there is still room in the coloumn
     */
    public static boolean valid_Move(Stack[] game_board, int move) {

        if (game_board[move - 1].IsEmpty() == false) {

            return true;

        } else {

            return false;

        }
    }

    /**
     * Checks if the game has been won by a party
     * 
     * @param game_Board Game board
     * @param stack_Array Game board stacks
     */
    public void check_Win(String[][] game_Board, Stack[] stack_Array) {

        if (end_Game_State_Detection(game_Board, stack_Array, false) == 1) {

            Console.println("O Wins!!!");
            main.game_Run(false); // Stops the game loop

        } else if (end_Game_State_Detection(game_Board, stack_Array, false) == -1) {

            Console.println("X Wins!!!");
            main.game_Run(false); // Stops the game loop
        }
    }

    /**
     * Checks every combination of couters to findout if any of then are 4 in a row
     * 
     * @param game_board
     * @param stack_Array
     * @param show
     * @return
     */
    public static int end_Game_State_Detection(String[][] game_board, Stack[] stack_Array, boolean show) {

        boolean end;
        int hasWon = 0;
        int[] dirX = { 0, 1, 1, 1 };
        int[] dirY = { 1, 1, 0, -1 };
        int var_DirX;
        int var_DirY;
        int current_X;
        int current_Y;
        int next_X;
        int next_Y;
        int chain_Length;
        String type;

        // For every column on the board
        for (int i = 0; i < main.board_Width; i++) {

            // Starting at the botom of the board unill the top counter indicated by the
            // stack
            for (int j = main.board_Hight - 1; j >= stack_Array[i].getTop(); j--) {

                // Finds the type of counter it is
                type = game_board[j][i];

                if (show) {

                    Console.println(type + " at (" + i + ", " + j + ")");
                }
                // For every reversibe direction
                for (int k = 0; k < 4; k++) {

                    end = false;
                    chain_Length = 0;
                    var_DirX = dirX[k];
                    var_DirY = dirY[k];
                    current_X = i;

                    current_Y = j;

                    // While not at the end of the chain of the counters in a row
                    while (!end) {

                        // If there is a 4 in a row
                        if (chain_Length == 3) {

                            if (type.equals("X")) {

                                return -1;

                            } else {

                                return 1;
                            }
                        } else {

                            next_X = current_X + var_DirX;
                            next_Y = current_Y + var_DirY;

                            // Checks if the next spot is the direction is on the board
                            if (((next_X >= 0) && (next_X <= (main.board_Width - 1)))
                                    && ((next_Y <= 0) && (next_Y >= (main.board_Hight - 1)))) {

                                current_X = next_X;
                                current_Y = next_Y;

                                // If the couinter in the next position is the same type
                                // as the first counter than incress chain length
                                if (game_board[current_Y][current_X].equals(type)) {

                                    chain_Length++;

                                } else {

                                    // else end the search in this direction
                                    end = true;
                                }
                            } else {

                                // If the next sopt is not on the board stop search in this direction
                                end = true;
                            }
                        }
                    }
                    end = false;

                    // look in the opersite direction
                    var_DirX = (dirX[k] * -1);
                    var_DirY = (dirY[k] * -1);

                    // go back to the first counter
                    current_X = i;
                    current_Y = j;

                    // while not at the end of the chain of the counters in a row
                    while (!end) {

                        // if there is a 4 in a row
                        if (chain_Length == 3) {

                            if (type.equals("X")) {

                                return -1;

                            } else {

                                return 1;
                            }
                        } else {

                            next_X = current_X + var_DirX;
                            next_Y = current_Y + var_DirY;

                            // checks if the next spot is the direction is on the board
                            if (((next_X >= 0) && (next_X <= (main.board_Width - 1)))
                                    && ((next_Y >= 0) && (next_Y <= (main.board_Hight - 1)))) {

                                current_X = next_X;
                                current_Y = next_Y;

                                // If the couinter in the next position is the same type
                                // as the first counter than incress chain length
                                if (game_board[current_Y][current_X].equals(type)) {

                                    chain_Length++;

                                } else {

                                    // else end the search in this direction
                                    end = true;
                                }
                            } else {

                                // If the next sopt is not on the board stop search in this direction
                                end = true;
                            }
                        }
                    }
                }
            }
        }
        return hasWon;
    }

    public static int Board_Evaluation(String[][] game_Board, Stack[] stack_Array, String node, boolean isMaximizingPlayer) {

        /**
         * Counters in a row scores:
         * 
         * 5 IAR = 10,000 | 4 IAR = 1000 | 3 IAR = 8 | 2 IAR = 2
         * 
         * (for opposition scores are negitive)
         * 
         */

        int score = 0;
        int center_W_Score = 0;
        int[] dirX = { 0, 1, 1, 1 };
        int[] dirY = { 1, 1, 0, -1 };
        int[] X_In_A_Row_Scores = { 0, 2, 8, 1000, 10000 };

        // for every column on the board
        for (int i = 0; i < main.board_Width; i++) {

            // starting at the botom of the board unill the top counter indicated by the
            // stack
            for (int j = (game_Board.length - 1); j >= stack_Array[i].getTop(); j--) {

                // finds the type of counter it is
                String type = game_Board[j][i];

                /**
                 * Center weighting scoring:
                 * 
                 * score = 3 - (places from center)
                 */

                boolean found = false;
                int p = 0;

                while (!found) {

                    if (p == i) {

                        if (type.equals("O")) {

                            center_W_Score = center_W_Score + Center_W_Scores[p];

                        } else {

                            center_W_Score = center_W_Score + (Center_W_Scores[p] * -1);

                        }
                        found = true;
                    }
                    p++;
                }
                /**
                 * End of section
                 */

                // for every reversibe direction
                for (int k = 0; k < 4; k++) {

                    boolean end = false;
                    int chain_Length = 0;
                    int var_DirX = dirX[k];
                    int var_DirY = dirY[k];
                    int current_X = i;
                    int current_Y = j;

                    // while not at the end of the chain of the counters in a row
                    while (!end) {

                        int next_X = current_X + var_DirX;
                        int next_Y = current_Y + var_DirY;

                        // checks if the next spot is the direction is on the board
                        if (((next_X >= 0) && (next_X <= (main.board_Width - 1)))
                                && ((next_Y >= 0) && (next_Y <= (main.board_Hight - 1)))) {

                            current_X = next_X;
                            current_Y = next_Y;

                            // If the couinter in the next position is the same type
                            // as the first counter than incress chain length
                            if (game_Board[current_Y][current_X].equals(type)) {

                                chain_Length++;
                            } else {

                                // else end the search in this direction
                                end = true;
                            }
                        } else {

                            // If the next sopt is not on the board stop search in this direction
                            end = true;
                        }
                    }

                    // if the chain length is 1-4 add/minus the score in the array pos = chain
                    // length
                    if (chain_Length <= 4) {

                        if (type.equals("O")) {

                            score += X_In_A_Row_Scores[chain_Length];
                        }
                        if (type.equals("X")) {

                            score -= X_In_A_Row_Scores[chain_Length];
                        }
                    } else { // else chain length <4 add/minus score in the array in pos 4
                        if (type.equals("O")) {

                            score += X_In_A_Row_Scores[4];
                        }
                        if (type.equals("X")) {

                            score -= X_In_A_Row_Scores[4];
                        }
                    }
                }
            }
        }
        if (!isMaximizingPlayer) {
            
            return score + center_W_Score;
        }
        else {
            return -1 * (score + center_W_Score);
        }
    }
}
