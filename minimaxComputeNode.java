/**
 * Compute node For the MiniMax algorthm
 * 
 * Many instances of this class are ran at the same time.
 * Alowes full use of the prossesor
 */
class minimaxComputeNode extends AI implements Runnable {

    String node;
    String[][] game_Board;
    Stack[] stack_Array;
    int depth;
    boolean isMaximizingPlayer;
    int considered = 0;

    // Passes the varibles to the compute node
    public minimaxComputeNode(String node, String[][] game_Board, Stack[] stack_Array, int depth, boolean isMaximizingPlayer) {

        this.node = node;
        this.game_Board = game_Board;
        this.stack_Array = stack_Array;
        this.depth = depth;
        this.isMaximizingPlayer = isMaximizingPlayer;

    }

    public void run() {
        
        // Runs the mini max algorthm
        scores[Integer.valueOf(node)] = minimax(node, game_Board, stack_Array, depth, isMaximizingPlayer);

        // Adds up the total number considered
        totalConsidered += considered;

        // incress the thread completed counter
        ThreadsCompleted++;
    }

    /**
     * The minimax algorthm is the recursive algorthm that will look @param depth
     * moves ahead to evaluate a score for every posible move so a move can be
     * chosen
     * 
     * @param node                  The current string of moves made E.G. "2634"
     * @param game_Board            The current posible game Board
     * @param stack_Array           The current game board stack list
     * @param depth                 The maximum depth the algorthm can go
     * @param isMaximizingPlayer    If it is the users go then it's a minimising move
     *                              if not a maximizing move
     * 
     * @return The current score
     */
    public int minimax(String node, String[][] game_Board, Stack[] stack_Array, int depth, boolean isMaximizingPlayer) {

        int board_Score = board.end_Game_State_Detection(game_Board, stack_Array, false);

        considered++;
        roughtotalConsidered++;

        if (board_Score != 0 || search_Depth == depth) {
            
            return board.Board_Evaluation(game_Board, stack_Array, node, isMaximizingPlayer);

        } else {

            // If it's a maximizer move then the algorthm will return the highest score
            // under the current board state
            if (isMaximizingPlayer) {

                int bestVal = Min;

                for (int i = 0; i < main.board_Width; i++) {

                    if (!stack_Array[i].IsEmpty()) {

                        String[][] AI_gameState = board.clone_2D_Array(game_Board);

                        Stack[] AI_stackList = board.clone_Stackarray(stack_Array);

                        String new_Node = node;

                        int value = minimax((new_Node += i),
                                board.make_Move(AI_gameState, AI_stackList, (i + 1), false), AI_stackList, (depth + 1),
                                false);

                        bestVal = Math.max(value, bestVal);
                    }
                }   
                return bestVal;

                // If it's a minimizer move then the algorthm will return the lowest score
                // under the current board state
            } else {

                int bestVal = Max;

                for (int i = 0; i < main.board_Width; i++) {

                    if (!stack_Array[i].IsEmpty()) {

                        String[][] AI_gameState = board.clone_2D_Array(game_Board);

                        Stack[] AI_stackList = board.clone_Stackarray(stack_Array);

                        String new_Node = node;

                        int value = minimax((new_Node += i), board.make_Move(AI_gameState, AI_stackList, (i + 1), true),
                                AI_stackList, (depth + 1), true);

                        bestVal = Math.min(value, bestVal);

                    }
                }
                return bestVal;
            }
        }
    }
}
