
/**
 * Main class for the Connect 4 game
 * 
 * Runs the main game loops and start up options
 * 
 * @author Kuno de Leeuw-Kent
 */

public class main {

    static int board_Width = 0;
    static int board_Hight = 0;
    static int threads = 0;
    static boolean game_Run = true;
    static boolean human_Move = true;
    static int gameDiff = 0;
    static int AI_Diff = 0;
    static int AI_prsn = 0;
    static int turn_Num = 0;
    static boolean show = false;

    /**
     * Constructor for objects of class Connect_4_AI
     */
    public static void main(String[] args) {

        System.out.print("\f");
        Console.println("Welcome to Kuno's awsome Connect 4 AI");
        start();
        System.exit(0);
    }

    /**
     * Runs the main menu, gets the game paramiters and let's the play pick between
     * a 1 or 2 player game
     */
    public static void start() {

        boolean valid;
        boolean run = true;
        boolean first_Time = true;
        boolean AI = false;
        /**
         * while the program is running
         */
        while (run) {

            Console.println();
            game_Run = true;
            valid = false;

            /**
             * validation loop, runs after the first game has ended
             */
            while (!valid && !first_Time) {

                /**
                 * ask user if they want to play again
                 */
                Console.println("Ready to play again? (Y/Quit)");
                String str = Console.readLine();
                str = str.toUpperCase();

                if (str.equals("QUIT")) {

                    run = false;
                    valid = true;
                } else if (str.equals("Y")) {

                    valid = true;
                }
            }

            /**
             * Stops the program runnning if the user has selected "quit" above
             */
            if (!first_Time && !run) {

                break;
            }

            valid = false;
            AI = false;

            /**
             * Let's the user select the board width
             */
            while (!valid) {

                Console.println("Enter board Width (7 defalt, 4 min, 16 max)");
                String str = Console.readLine();

                try {

                    board_Width = Integer.valueOf(str);
                    if (board_Width > 3 && board_Width < 17) {
                        valid = true;

                    } else {
                        Console.println("invalid responce");
                        Console.println();
                    }

                } catch (Exception e) {

                    Console.println("invalid responce");
                    Console.println();
                }
            }
            valid = false;

            /**
             * Let's the user select the board hight
             */
            while (!valid) {

                Console.println("Enter board hight (6 defalt, 4 min, 16 max)");
                String str = Console.readLine();

                try {

                    board_Hight = Integer.valueOf(str);
                    if (board_Hight > 3 && board_Hight < 17) {
                        valid = true;
                    } else {
                        Console.println("invalid responce");
                        Console.println();
                    }

                } catch (Exception e) {

                    Console.println("invalid responce");
                    Console.println();
                }
            }
            valid = false;

            /**
             * let's the player decite between the 1/2 player mode
             */
            while (!valid) {

                Console.println("One player or two? (1/2)");
                String str = Console.readLine();
                str = str.toUpperCase();

                if (str.equals("1")) {

                    AI = true;
                    valid = true;

                } else if (str.equals("2")) {

                    AI = false;
                    valid = true;
                }
            }
            valid = false;

            /**
             * let's the player decite how many threads to let the program use
             */
            while (!valid && AI) {

                Console.println("AI threads");
                String str = Console.readLine();
                str = str.toUpperCase();

                try {

                    threads = Integer.valueOf(str);
                    if (threads > 0 && threads < 17) {
                        valid = true;
                    } else {
                        Console.println("invalid responce");
                        Console.println();
                    }

                } catch (Exception e) {

                    Console.println("invalid responce");
                    Console.println();
                }
            }
            valid = false;

            while (!valid) {

                /**
                 * if the player has chosen the 2 player mode start the 2 player game loop
                 * 
                 * once the game has ended program will break out of the menu loop
                 */
                if (!AI) {

                    System.out.print("\f");
                    Gameloop_2p();
                    first_Time = false;
                    break;
                }

                /**
                 * lets the user chose the difficalty value of the algorithm: 5 = difficalt, 0 =
                 * easy
                 */
                while (!valid) {

                    Console.println("Enter AI Difficalty (1-10) 1=dumb, 10=perfect play");
                    String str = Console.readLine();

                    try {

                        AI_Diff = Integer.valueOf(str);
                        if (AI_Diff <= 10 && AI_Diff >= 1) {
                            valid = true;
                        } else {
                            Console.println("invalid responce");
                            Console.println();
                        }
                    } catch (Exception e) {

                        Console.println("invalid responce");
                        Console.println();
                    }
                }
                valid = false;

                /**
                 * set the search depth of the Minimax algorthm
                 * 
                 * values > 3 if acseptible value 5-6 is perfect values > 7 is slow and overly
                 * persise
                 * 
                 * Higher values = better judement of move Lower vales = faster computer time
                 * 
                 * if the input given by the user = "ORDER 66" then the user can bypass the 1-10
                 * limmit
                 */
                while (!valid) {

                    Console.println("Enter AI presition (min 3, max 9, recomended 5)");
                    Console.println("Higher values = better judement of move");
                    Console.println("Lower vales = faster computer time");
                    String str = Console.readLine();
                    if (str.equals("order 66")) {
                        valid = true;
                        AI_prsn = -1;
                        show = true;

                        Console.println("empty field for ulimited or int");
                        str = Console.readLine();
                        try {
                            AI_prsn = (Integer.valueOf(str) - 1);
                        } catch (Exception e) {
                        }
                        break;
                    }
                    try {

                        AI_prsn = (Integer.valueOf(str) - 1);
                        if (AI_prsn <= 8 && AI_prsn >= 2) {
                            valid = true;
                            show = false;
                        } else {
                            Console.println("invalid responce");
                            Console.println();
                        }

                    } catch (Exception e) {

                        Console.println("invalid responce");
                        Console.println();
                    }

                }
                valid = false;

                /**
                 * for the 1 player version of the game this allows the user to pick if they
                 * want to go first or not
                 * 
                 * @input (Yy) = true or (Nn) = false
                 */
                while (!valid) {

                    Console.println("Do you want to go first? (Y/N)");
                    String str = Console.readLine();
                    str = str.toUpperCase();

                    if (str.equals("Y")) {

                        // if the user want to go first then gameloop is called with the goFirst
                        // paramiter set to true
                        System.out.print("\f");

                        Gameloop_AI(true);
                        valid = true;

                    } else if (str.equals("N")) {

                        // if the user does not want to go first then gameloop is called with the
                        // goFirst paramiter set to false
                        System.out.print("\f");
                        Gameloop_AI(false);
                        valid = true;
                    }
                }
                first_Time = false;
            }
            Console.println("Game End");
        }
        /**
         * exit messagae of program
         */
        Console.println();
        Console.println("Thank you for playing");
        Console.println("- Kuno");
    }

    

    public static void Gameloop_2p() {

        board board = new board();

        human_Move = true;

        board.display_Current_Board();
        turn_Num = 0;

        /**
         * The 2 player game loop
         */
        while (game_Run) {

            // Update the 2D array with the users move
            try {
                board.update_Board(board.make_Move(board.get_Game_State(), board.get_Stacks(),
                        board.get_Player_Response(), human_Move));

                // Increment the turn counter
                turn_Num++;
            } catch (Exception e) {
                Console.println("test success");
                Console.println();
                player_Switcher();
            }

            // Display the current board
            board.display_Current_Board();

            // Switch the player type
            player_Switcher();

            // Check if the user has won
            board.check_Win(board.get_Game_State(), board.get_Stacks());

            // Check if the game has come to a draw
            if (turn_Num == (board_Hight * board_Width)) {

                game_Run = false;
                Console.println("Draw!!!");
                Console.println();
            }
        }
    }

    /***
     * The one player loop for the 1 Player game mode
     * 
     * @param first decides if the user will make first play or not
     */
    public static void Gameloop_AI(boolean first) {

        board board = new board();

        // starts the turn counter
        turn_Num = 0;

        /**
         * if the user is going first then this if statment does nothing
         * 
         * however if the user isn't going first then the connect 4 AI will play
         * position 4 as it is the best first move
         */
        if (first) {

            human_Move = true;

        } else {

            human_Move = false;

            // if it is the computers turn ask the AI class for the correct move and play
            // that move
            board.update_Board(board.make_Move(board.get_Game_State(), board.get_Stacks(),
                    AI.AI(board.get_Game_State(), board.get_Stacks(), AI_prsn, AI_Diff), human_Move));

            player_Switcher();

            turn_Num++;
        }

        board.display_Current_Board();

        // Main game loop
        while (game_Run) {

            if (human_Move) {

                // if it is the users turn, ask for their move and make that move
                try {
                    board.update_Board(board.make_Move(board.get_Game_State(), board.get_Stacks(),
                            board.get_Player_Response(), human_Move));
                    // incress the turn counter
                    turn_Num++;

                } catch (Exception e) {
                    Console.println("test success");
                    Console.println();
                    player_Switcher();
                }

            } else {

                // if it is the computers turn ask the AI class for the correct move and play
                // that move
                try {
                    board.update_Board(board.make_Move(board.get_Game_State(), board.get_Stacks(),
                            AI.AI(board.get_Game_State(), board.get_Stacks(), AI_prsn, AI_Diff), human_Move));
                    // incres the turn counter
                    turn_Num++;
                }
                // recovery just in case
                catch (Exception e) {
                    Console.println();
                    Console.println("gamebreaking exception ¯\\_(ツ)_/¯");
                    Console.println();
                    for (int i = 1; i <= main.board_Width; i++) {
                        try {
                            board.update_Board(
                                    board.make_Move(board.get_Game_State(), board.get_Stacks(), i, human_Move));
                            UI.printArrow(i);
                            // incres the turn counter
                            turn_Num++;
                            break;
                        } catch (Exception f) {
                        }
                    }
                }

            }

            // if the game is running display the new board
            if (game_Run) {

                board.display_Current_Board();
            }

            // check if the game has come to it's concusion
            // if so game_Run will be set to false
            board.check_Win(board.get_Game_State(), board.get_Stacks());

            // swtich who's turn it is
            player_Switcher();

            // if the game has come to a draw
            // show that the user has drawn with the AI
            if (turn_Num == (board_Hight * board_Width)) {

                game_Run = false;
                Console.println("Draw!!!");
                Console.println();
            }
        }
    }

    // A setter for the game_Run varible
    public static void game_Run(boolean E) {

        game_Run = E;
    }

    // swtich who's turn it is
    // by inverting the value of human_Move
    public static void player_Switcher() {

        human_Move = !human_Move;
    }
}
