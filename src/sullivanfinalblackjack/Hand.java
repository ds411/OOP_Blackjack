package sullivanfinalblackjack;

//Importing libraries
import javax.swing.*;
import java.awt.*;
import java.util.*;

//This class represents a blackjack Hand as a JPanel
public class Hand extends JPanel {

    //Declaring and initializing fields
    private Shuffleable cards; //Stack of cards
    private boolean busted; //Hand busted
    private int value; //Value of hand
    private ArrayList<Integer> values = new ArrayList<>(); //Collection of possible values
    private JLabel nameLabel; //Label with name
    private JLabel valueLabel; //Label with value
    private JPanel cardPane; //Panel with drawn cards

    //This method constructs a new Hand.
    //Precondition(s): Cards as Shuffleable
    //Postcondition(s): New Hand panel
    public Hand(Shuffleable cards) {
        this.setLayout(new BorderLayout());
        this.cards = cards;
        this.busted = false; //Player not busted by default
        this.value = 0; //Value starts at 0
        values.add(0);
        nameLabel = new JLabel("", SwingConstants.LEFT); //Initializes name label
        valueLabel = new JLabel("Value: " + value, SwingConstants.LEFT); //Initilizes value label
        cardPane = new JPanel(new FlowLayout());
        cardPane.setBackground(Color.GREEN); //Background color green
        this.add(nameLabel, BorderLayout.NORTH);
        this.add(valueLabel, BorderLayout.SOUTH);
        this.add(cardPane, BorderLayout.CENTER);
    }

    //This method returns the owner's name, taken from their name label
    //Precondition(s): N/A
    //Postcondition(s): Player's name as a String
    public String getPlayerName() {
        return nameLabel.getText().substring(0, nameLabel.getText().indexOf('\''));
    }

    public JPanel getCardPane() {
        return cardPane;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public int getValue() {
        return value;
    }

    public boolean getBusted() {
        return busted;
    }

    //This method adds a card to the hand and calculates the new value of the hand
    //Precondition(s): N/A
    //Postcondition(s): Card added to hand and updated hand value
    public void hit() {
        Card c = cards.draw(); //Draws a card
        cardPane.add(c); //Adds card to card panel
        repaint(); //Redraws hand
        revalidate(); //Refreshes hand
        int cardValue = c.getValue(); //Gets value of card
        ArrayList<Integer> newValues = new ArrayList<>(); //Collection of new Integer values
        for (Integer i : values) {
            newValues.add(i + cardValue); //Populates new list with old list + new card value
        }
        values.clear(); //Erases old list
        values.addAll(newValues); //Sets values to newValues
        if (cardValue == 11) { //If card is an ace
            for (Integer i : values) {
                newValues.add(i - 10); //Adds values where ace is value 1
            }
            values.clear();
            values.addAll(newValues);
        }
        updateValue(); //Updates the value of the hand
    }

    //This method calculates the value of the hand, updates the value label, and busts the hand if the lowest possible value is over 21
    //Precondition(s): N/A
    //Postcondition(s): New value of hand, updated value label, and possibly bust
    private void updateValue() {
        int minValue = 100; //Minimum value to be calculated, initialized to a large number
        value = 0; //Value reinitialized to 0
        for (Integer i : values) {
            if (i > value && i <= 21) {
                value = i; //Sets value to maximum value less than or equal to 21
            }
            if (i < minValue) {
                minValue = i; //Sets minimum value to lowest value in the list
            }
        }
        if (value == 0) {
            value = minValue; //Sets value to minValue if no values 21 or less
        }
        valueLabel.setText("Value: " + value); //Sets text of value label
        if (value > 21) {
            bust(); //Busts if value greater than 21
        }
    }

    //This method busts the hand
    //Precondition(s): N/A
    //Postcondition(s): Dialog box pop-up and hand is busted
    private void bust() {
        //Dialog box pop-up
        JOptionPane.showMessageDialog(this, this.getPlayerName() + " busted with a value of: " + this.getValue(), "BUSTED!", JOptionPane.INFORMATION_MESSAGE);
        busted = true; //Hand busted
    }

    //This method clears the cards from the hand and resets the busted state and value of the hand
    //Precondition(s): N/A
    //Postcondition(s): Cleared card pane, busted is false, and value is 0.
    public void clearHand() {
        for (Component c : cardPane.getComponents()) {
            if (c instanceof Card) {
                cardPane.remove(c); //Removes all Cards from card panel
            }
        }
        busted = false; //Hand not busted
        values.clear(); //Removes all Integers from values
        values.add(0); //Sets initial value to 0
        updateValue(); //Updates value
        repaint(); //Redraws hand
    }
}
