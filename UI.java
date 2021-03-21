/**
 * Displays the game board to the console
 *
 * @author Kuno de Leeuw-Kent
 */
public class UI {

    public UI() {

    }

    /**
     * Displaying a board to the console
     * @param board the array that will be printed
     * @param clear whether the console will be pre-cleared
     */
    public static void displayboard(String[][] board, boolean clear) {

        // if C if true the subroutien will clear the console
        if (clear) {

            System.out.print("\f");
        }
        Console.println("(X = user, O = AI/player 2)");

        for (int i = 0; i < main.board_Hight; i++) { // For every hight

            printlines(); // print over line

            for (int j = 0; j < main.board_Width; j++) {

                // print every every item in the row
                Console.print("| ");
                Console.print(board[i][j]);
                Console.print(" ");
            }
            // add final line and end line
            Console.print("|");
            Console.println();
        }
        printlines(); // print under line
        
    }

    /**
     * Prints the top and botom line
     */
    public static void printlines()
    {
        for (int i = 0; i < main.board_Width; i++){
            Console.print("----"); 
        }
        Console.println("-");
    }

    /**
     * Prints a arrow in the column where the AI made their last move
     * 
     * @param pos column to print arrow
     */
    public static void printArrow(int pos)
    {
        int num = (pos - 1);
        Console.print("  "); 
        for (int i = 0; i < (num); i++){
            Console.print("    "); 
        }
        Console.println("↓");
    }
}
