package Banko;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Banko extends JFrame implements ActionListener
{
    // the money inside a money bag
    public static int REWARD = 1000;

    // the cost of opening an empty bag
    public static int COST = 250;

    // the size of the game board and total number of money bags
    public static int ROW_COUNT = 9;
    public static int COLUMN_COUNT = 14;
    public static int BAG_COUNT = 10;

    // the fields that report how a player's doing
    private final JTextField moneyField;
    private final JTextField foundField;

    // the restart button
    public JButton restart;

    // a player's money and the number of money bags found
    public int money;
    public int found;

    // the game board
    private final Board board;

    // create the frame
    public Banko()
    {
        // call the superclass and give the frame a title
        super("Banko");

        // set its layout manager
        BorderLayout border = new BorderLayout();

        setLayout(border);

        // create the top panel
        JPanel top = new JPanel();

        // create the "Money:" label and text field
        JLabel moneyLabel = new JLabel("Money: $");
        moneyField = new JTextField("", 8);

        // prevent it from being edited
        moneyField.setEditable(false);

        // create the "Found:" label and text field
        JLabel foundLabel = new JLabel("Found: ");
        foundField = new JTextField("", 8);
        foundField.setEditable(false);

        // create the Restart button
        restart = new JButton("Restart");

        // assign the frame to monitor clicks of this button
        restart.addActionListener(this);

        // add the components to the top panel
        top.add(moneyLabel);
        top.add(moneyField);
        top.add(foundLabel);
        top.add(foundField);
        top.add(restart);

        // add the panel to the border's topmost position
        add(top, BorderLayout.NORTH);

        // create the game board
        board = new Board(this, ROW_COUNT, COLUMN_COUNT, BAG_COUNT);

        // add the board to the border's center position
        add(board, BorderLayout.CENTER);

        // set up the game
        setup();

        // set the size of the frame
        setSize(650, 450);

        // make the application end when the frame is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // display the user interface
        setVisible(true);
    }

    // set up the frame for gameplay
    public final void setup()
    {
        found = -1;
        money = 0;

        // give the player starting money
        earnMoney();
    }

    // take money from the player for opening an empty bag
    public void spendMoney()
    {
        // deduct funds and display the new total
        money = money - COST;
        moneyField.setText("" + money);
        if (money <= 0)
            // the player's broke, so end the game
            revealBoard();
    }

    // award money to the player for finding a money bag
    public void earnMoney()
    {
        // add funds and display the total
        money = money + REWARD;
        moneyField.setText("" + money);

        // count the newly found bag
        found++;
        foundField.setText("" + found + " of " + BAG_COUNT);
        if (found >= BAG_COUNT)
            // the player's found all bags, so end the game
            revealBoard();
    }

    // reveal the entire board at game's end
    public void revealBoard()
    {
        // inspect every square on the board
        for (int row = 0; row < ROW_COUNT; row++)
        {
            for (int column = 0; column < COLUMN_COUNT; column++)
            {
                // get the current button
                Button button = board.square[row][column];
                if (button.state == -2)
                {
                    // display this money bag
                    button.found = true;
                    button.revealMoney();
                }
                else
                {
                    // set up happy and sad colors
                    Color green = new Color(204, 255, 204);
                    Color red = new Color(255, 204, 204);
                    if (money > 0)
                        // the player won, so make this empty square green
                        button.setBackground(green);
                    else
                        // the player lost, so make this empty square red
                        button.setBackground(red);
                }
                if (button.state == 0)
                    // this square has never been cleared, so do so now
                    button.clear(false);
            }
        }
    }

    // load a button graphic using its filename
    public ImageIcon getImageIcon(String filename)
    {
        // load an image icon
        ImageIcon icon = new ImageIcon(filename);
        return icon;
    }

    // monitor button clicks on the game board and restart button
    @Override
    public void actionPerformed(ActionEvent event)
    {
        // determine the button the player clicked
        Object source = event.getSource();
        if (source instanceof Button)
        {
            // the button's on the game board
            Button button = (Button) event.getSource();
            if (button.state == -2)
                // it contains a money bag, so reveal it
                button.revealMoney();
            else
                // it doesn't contain a money bag, so clear it
                button.clear(true);

        }
        else
        {
            // the restart button was clicked
            // set up the frame anew
            setup();

            // set up the board too
            board.setup();
        }
    }

    public static void main(String[] arguments)
    {
        // start the application
        new Banko();
    }
}