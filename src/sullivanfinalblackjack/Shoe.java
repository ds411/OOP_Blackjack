
package sullivanfinalblackjack;

//Importing libraries
import java.util.Stack;
import java.util.Collections;

//This class represents a Shoe of cards as Shuffleable
public class Shoe implements Shuffleable {
    
    //Declaring and initializing fields
    private Stack<Card> cards = new Stack<>(); //Stack of cards
    private int decks; //Number of decks
    
    //This method creates a new Shoe object.
    //Precondition(s): Number of decks as an int
    //Postcondition(s): New Shoe object
    public Shoe(int decks) {
        this.decks = decks; //Sets number of decks
        this.shuffle(); //Populates cards and shuffles
    }
    
    //This method draws a Card from the Shoe
    //Precondition(s): N/A
    //Postcondition(s): Shuffles new cards into the Shoe if it is empty and removes and returns a Card from the Shoe
    @Override
    public Card draw() {
        if(cards.isEmpty()) this.shuffle(); //Adds new cards and shuffles if cards empty
        return cards.pop(); //Removes a Card from Shoe and returns it
    }
    
    //This method adds new Cards to the Shoe and shuffles it
    //Precondition(s): N/A
    //Postcondition(s): New Cards added to Shoe and Shoe shuffled
    @Override
    public void shuffle() {
        for(int i = 0; i < decks; i++) for(int j = 0; j < 13; j++) for(int k = 0; k < 4; k++) cards.push(new Card(j, k)); //Populates cards with Cards
        Collections.shuffle(cards); //Shuffles cards
    }
}
