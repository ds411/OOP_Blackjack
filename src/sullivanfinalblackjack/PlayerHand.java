package sullivanfinalblackjack;

//Importing libraries
import javax.swing.*;
import java.awt.*;

//This class represents a player's hand as a Hand
public class PlayerHand extends Hand {

    //Declaring and initializing fields
    private int bet = 0; //Bet amount initialized to 0
    private int money; //Current money

    //This method constructs a new PlayerHand panel.
    //Precondition(s): Player's name as a String, money as an int, cards as Shuffleable
    //Postcondition(s): New PlayerHand panel
    public PlayerHand(String playerName, int money, Shuffleable cards) {
        super(cards); //Constructs Hand
        this.getNameLabel().setText(playerName + "'s Hand: "); //Sets name label text
        this.money = money; //Sets money to starting money
        this.setBackground(Color.CYAN); //Background color cyan
    }
    
    public int getMoney() {
        return money;
    }

    //This method allows a player to select their bet value using a spinner.
    //Precondition(s): N/A
    //Postcondition(s): Bet value changes and bet subtracted from money
    public void bet() {
        String[] options = {"OK"}; //Options for option box
        JPanel betPane = new JPanel(new GridLayout(1,2));
        JSpinner betSpinner = new JSpinner(new SpinnerNumberModel(5,0,money,5)); //Spinner for bet amount
        betSpinner.setEditor(new JSpinner.DefaultEditor(betSpinner));
        betPane.add(new JLabel("Bet amount: ", SwingConstants.LEFT));
        betPane.add(betSpinner);
        //Option box to choose bet amount
        int option = JOptionPane.showOptionDialog(this, betPane, "Place Bet", JOptionPane.NO_OPTION, JOptionPane.OK_OPTION, null, options, options[0]);
        if(option == JOptionPane.OK_OPTION) bet = (int) betSpinner.getValue(); //If player selects "OK" sets value to selected value
        else bet = 0; //Otherwise bet amount defaults to 0
        money -= bet; //Bet subtracted from money
    }
    
    //This method causes the player to win
    //Precondition(s): N/A
    //Postcondition(s): Dialog box pop-up, bet added to money twice, and bet cleared
    public void win() {
        money += bet*2; //Adds winnings to money
        //Pop-up message
        JOptionPane.showMessageDialog(this, getPlayerName() + " wins with a value of " + this.getValue(), "Win!", JOptionPane.INFORMATION_MESSAGE);
        bet = 0; //Resets bet
    }
    
    //This method causes the player to tie
    //Precondition(s): N/A
    //Postcondition(s): Dialog box pop-up, bet added to money, and bet cleared
    public void tie() {
        money += bet; //Returns bet to money
        //Pop-up message
        JOptionPane.showMessageDialog(this, getPlayerName() + " ties with a value of " + this.getValue(), "Tie!", JOptionPane.INFORMATION_MESSAGE);
        bet = 0; //Resets bet
    }
    
    //This method causes the player to lose
    //Precondition(s): N/A
    //Postcondition(s): Dialog box pop-up, bet cleared
    public void lose() {
        //Pop=up message
        JOptionPane.showMessageDialog(this, getPlayerName() + " loses with a value of " + this.getValue(), "Loss!", JOptionPane.INFORMATION_MESSAGE);
        bet = 0; //Resets bet
    }
}
