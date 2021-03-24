
/**
 * Kuno's stacker - (remember to rotate your stack of plates every so often)
 * 
 * @author Kuno de Leeuw-Kent
 */
public class Stack {

    int[] s;
    int top;

    /**
     * Constructor for the Stacker
     * 
     * @param - size of the array
     */
    public Stack(int size) {

        s = new int[size]; // creates the array size "size"
        top = 0; // creats the varible for the position of the top of the stack
    }

    /**
     * Check whether stack is empty
     *
     * @return True or False
     */
    public boolean IsEmpty() {

        if (top == 0) // if the top is at the bottom
        {
            return true; // say it's empty

        } else {

            return false; // say it's not empty
        }
    }

    /**
     * returns the top position (counting from 1 at bottom)
     * 
     * @return int top
     */
    public int getTop() {

        return top; // returns the var top
    }

    /**
     * Return the item from the top of a stack and removes it
     *
     * @return the item from the top of the stack
     */
    public int Pop() {

        if (!IsEmpty()) // if not empty
        {
            top--; // move down one
            return s[top]; // read data value

        } else {
            return -1;
        }
    }

    /**
     * Add an item to the top of a stack
     *
     * @param item - the new item to be added to the stack
     */
    public void Push(int item) {

        if (top < s.length) // if the stack isn't already full
        {
            s[top] = item; // store value
            top++; // move up one
        }
    }

    /**
     * Return the item from the top of a stack
     * 
     * @return - the item on the top of the stack
     */
    public int peek() {

        if (!IsEmpty()) // if not empty
        {
            return s[top - 1]; // reasd data value down one

        } else {
            return -1;
        }
    }

    /**
     * @param position, in stack
     * 
     * @return value at the position specified
     */
    public int get(int position) {

        return s[position];
    }

    /**
     * @param position, sets pointer to value position
     */
    public void set_Top(int position) {

        top = position;
    }
}
