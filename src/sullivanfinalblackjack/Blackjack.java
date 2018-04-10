package sullivanfinalblackjack;

//importing libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

//This class represents the Blackjack GUI as a JFrame
public class Blackjack extends JFrame {

    //Declaring fields
    private JPanel gamePane; //Panel containing the Game

    //This method constructs a new Blackjack GUI
    //Precondition(s): N/A
    //Postcondition(s): Creates new Blackjack GUI
    public Blackjack() {
        super("Blackjack"); //Sets JFrame title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Sets program to end when JFrame closed
        this.setSize(1000, 800); //Sets size to 1000x800
        gamePane = new JPanel();
        createMenuBar(); //Creates and populates menu bar
        this.setVisible(true); //Shows JFrame
        createNewGame(); //Creates new Game and adds it to gamePane

    }

    //This method creates a new menu bar and adds it to the GUI
    //Precondition(s): N/A
    //Postcondition(s): New menu bar added to GUI
    private void createMenuBar() {
        JMenuBar bar = new JMenuBar(); //Menu bar
        JMenu file = new JMenu("File"); //File menu
        JMenuItem newGame = new JMenuItem("New Game"); //New Game menu item
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())); //New Game shortcut set to CTRL+N
        newGame.addActionListener(new ActionListener() { 
            
            //This method creates a new Game when this menu option is selected.
            //Precondition(s): Menu selection as ActionEvent
            //Postcondition(s): New Game
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewGame();
            }
        });
        JMenuItem exit = new JMenuItem("Exit"); //Exit menu item
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())); //Exit shortcut set to CTRL+X
        exit.addActionListener(new ActionListener() {
            
            //This method exits the program when this menu option is selected.
            //Precondition(s): Menu selection as ActionEvent
            //Postcondition(s): Program exits
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        //Adding menu items to menu
        file.add(newGame);
        file.add(exit);
        //Adding menu to menu bar
        bar.add(file);
        //Setting JFrame's menu bar
        this.setJMenuBar(bar);
    }

    //This method creates a new Game and adds it to the GUI
    //Precondition(s): N/A
    //Postcondition(s): New Game added to GUI
    private void createNewGame() {
        String[] options = {"Create Game", "Cancel"}; //Options for option box
        JSpinner playerSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1)); //Spinner for selecting number of players
        playerSpinner.setEditor(new JSpinner.DefaultEditor(playerSpinner)); //Makes spinner uneditable
        JSpinner deckSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1)); //Spinner for selecting number of decks
        deckSpinner.setEditor(new JSpinner.DefaultEditor(deckSpinner));
        JSpinner moneySpinner = new JSpinner(new SpinnerNumberModel(500, 100, 2000, 100)); //Spinner for selecting starting money
        moneySpinner.setEditor(new JSpinner.DefaultEditor(moneySpinner));
        JPanel newGamePane = new JPanel(new GridLayout(3, 2));
        newGamePane.add(new JLabel("Number of Players: ", SwingConstants.LEFT));
        newGamePane.add(playerSpinner);
        newGamePane.add(new JLabel("Number of Decks: ", SwingConstants.LEFT));
        newGamePane.add(deckSpinner);
        newGamePane.add(new JLabel("Starting Money: ", SwingConstants.LEFT));
        newGamePane.add(moneySpinner);
        //Option box for selecting values
        int option = JOptionPane.showOptionDialog(this, newGamePane, "New Game Options", JOptionPane.NO_OPTION, JOptionPane.OK_OPTION, null, options, options[0]);
        if (option == JOptionPane.OK_OPTION) { //If user presses "Create Game" in option box
            this.remove(gamePane); //Removes current Game
            gamePane = new Game((int) playerSpinner.getValue(), (int) deckSpinner.getValue(), (int) moneySpinner.getValue()); //Creates new Game
            this.add(gamePane); //Adds new Game
            revalidate(); //Refreshes GUI
        }
    }
}
