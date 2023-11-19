package Banko;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JPanel;

public class Board extends JPanel
{
    // the board's rows, columns, and total money bags
    protected int rows;
    protected int columns;
    protected int money_bags;

    // the buttons on the board
    public Button[][] square;

    // the game frame
    public Banko app;

    public Board(Banko app, int rows, int columns, int money_bags)
    {
        // store the frame
        this.app = app;

        // store the rows, columns, and money bags on the board
        this.rows = rows;
        this.columns = columns;
        this.money_bags = money_bags;

        // create the board and set its layout
        square = new Button[rows][columns];
        GridLayout grid = new GridLayout(rows, columns);
        setLayout(grid);

        // set up each button
        for (int row = 0; row < rows; row++)
        {
            for (int column = 0; column < columns; column++)
            {
                // create the button
                Button button = new Button(this, row, column);

                // add it to the board
                square[row][column] = button;

                // assign the frame to monitor button clicks
                button.addActionListener(app);

                // add the button to the user interface
                add(button);
            }
        }
        setup();
    }

    // set up the board for gameplay
    public final void setup()
    {
        // set up each button for gameplay
        for (int row = 0; row < rows; row++)
        {
            for (int column = 0; column < columns; column++)
            {
                square[row][column].setup();
            }
        }

        // count the number of money bags hidden so far
        int hidden = 0;
        while (hidden < money_bags)
        {
            // choose a random position for the money bag
            int row = (int)Math.floor(Math.random() * rows);
            int column = (int)Math.floor(Math.random() * columns);
            if (square[row][column].state == -2)
                // there's already a money bag in that square
                continue;

            // hide a money bag and increment the count
            square[row][column].state = -2;
            hidden++;
        }
        // count the number of bags in adjacent squares
        for (int row = 0; row < rows; row++)
            for (int column = 0; column < columns; column++)
                if (square[row][column].state == 0)
                    // count the bags adjacent to this square
                    square[row][column].near = getBagCount(row, column);
    }

    // determine the money bags adjacent to a square
    private int getBagCount(int row, int column)
    {
        // start counting
        int count = 0;

       /* set the lower and upper boundaries of the search area around
           a square, making sure not to go outside the game board */
        int above = (row > 0) ? row - 1 : row;
        int below = (row < rows - 1) ? row + 1 : row;
        int left = (column > 0) ? column - 1 : column;
        int right = (column < columns - 1) ? column + 1 : column;
        for (int i = above; i <= below; i++)
            for (int j = left; j <= right; j++)
                if (square[i][j].state == -2)
                    /* a money bag is nearby, so increment the count
                       for this square */
                    count++;
        return count;
    }

    // set the margins of the game board to 15 pixels
    @Override
    public Insets getInsets()
    {
        // set the top, left, bottom and right inset margins
        return new Insets(15, 15, 15, 15);
    }
}