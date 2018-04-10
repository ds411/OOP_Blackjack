package sullivanfinalblackjack;

//Importing libraries
import java.awt.*;

public class DealerHand extends Hand {

    //This method constructs a new DealerHand panel.
    //Precondition(s): Cards as Shuffleable
    //Postcondition(s): New DealerHand panel
    public DealerHand(Shuffleable cards) {
        super(cards); //Constructs Hand
        this.getNameLabel().setText("Dealer's Hand:"); //Sets name label text
        this.setBackground(Color.PINK); //Background color pink
    }

    //This method finishes the hand by the dealer hitting until the hand value is at least 17
    //Precondition(s): N/A
    //Postcondition(s): Dealer finished hitting
    public void finish() {
        while (this.getValue() < 17) hit(); //Hits until value is at least 17
    }
    
    //This method adds a card to the hand and calculates the value of the hand.  It also sets the cards to show their fronts by default.
    //Precondition(s): N/A
    //Postcondition(s): Card added to hand, updated hand value, and cards flipped to the front
    @Override
    public void hit() {
        super.hit(); //Hit method from Hand
        for(Component c : this.getCardPane().getComponents()) if(c instanceof Card) ((Card) c).front(); //Makes all Cards front side up
    }
}
