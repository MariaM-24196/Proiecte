package Banko;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton
{
    // the button's position
    public int row;
    public int column;

    // the button's state (-2 money, -1 cleared, 0 uncleared)
    public int state;

    // the number of adjacent money bags
    public int near;

    // true if this is a money bag that has been found
    public boolean found;

    // the game board
    private final Board board;

    // the two bag graphics
    private static ImageIcon unknownBag;
    private static ImageIcon moneyBag;


    // create a new button
    Button(Board board, int row, int column)
    {
        super("");

        // store the board that contains this button
        this.board = board;

        // store the button's position
        this.row = row;
        this.column = column;

        // set up button graphics (if necessary)
        if (unknownBag == null) {
            unknownBag = board.app.getImageIcon("unknown_bag.gif");
            moneyBag = board.app.getImageIcon("money_bag.gif");
        }

        // set up the button
        setup();
    }

    // set up the button for gameplay
    public final void setup()
    {
        fill();
        state = 0;
        near = 0;
        found = false;

        // remove the background color (from a previous game)
        setBackground(null);
    }

    // reveal an unopened bag in the square
    public void fill()
    {
        // give this button the unopened bag icon
        setIcon(unknownBag);

        // remove the label
        setText("");

        // enable the button
        setEnabled(true);
    }

    // reveal an empty square and count adjacent money bags
    public void clear(boolean clicked)
    {
        if (clicked)
        {
            /* this square was manually cleared by the user, so tell
                the frame to charge the player */
            board.app.spendMoney();
        }

        // remove the icon
        setIcon(null);

        // tell the button it is clear and disable it
        state = -1;
        setEnabled(false);
        if (near > 0)
            // reveal that one or more money bags are nearby
            setText("" + near);
        else
        {
            /* inspect the buttons above, below, left, and right
                of this one */
            Button above = above();
            Button below = below();
            Button left = left();
            Button right = right();

            /* if any of these buttons is clear and has no money bags
                nearby, clear it -- using recursion to fan out around
                the board */
            if ((above != null) && (above.state == 0))
                // clear the button above
                above.clear(false);

            if ((below != null) && (below.state == 0))
                // clear the button below
                below.clear(false);

            if ((left != null) && (left.state == 0))
                // clear the button to the left
                left.clear(false);

            if ((right != null) && (right.state == 0))
                // clear the button to the right
                right.clear(false);
        }
    }

    // reveal a money bag
    public void revealMoney()
    {
        // give this button the money bag icon
        setIcon(moneyBag);

        // remove the label
        setText("");
        if (!found)
        {
            /* this bag has never been found, so tell the frame
                to award money to the player */

            board.app.earnMoney();
            // tell this bag it has been found
            found = true;
        }
    }

    /* these four directional methods inspect four buttons adjacent
        to this one, returning null on the edges of the game board */
    public Button above()
    {
        if (row > 0)
            // return the adjacent button
            return board.square[row - 1][column];

        // return null to indicate a board edge has been passed
        return null;
    }

    public Button below()
    {
        if (row < board.rows - 1)
            return board.square[row + 1][column];
        return null;
    }

    public Button left()
    {
        if (column > 0)
            return board.square[row][column - 1];
        return null;
    }

    public Button right()
    {
        if (column < board.columns - 1)
            return board.square[row][column + 1];
        return null;
    }
}