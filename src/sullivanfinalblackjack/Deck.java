package sullivanfinalblackjack;

//Importing libraries
import java.util.Stack;
import java.util.Collections;

//This class represents a Deck as Shuffleable
public class Deck implements Shuffleable {

    //Declaring and initializing fields
    private Stack<Card> cardPile = new Stack<>(); //Card pile
    private Stack<Card> discardPile = new Stack<>(); //Discard pile

    //This method constructs a new Deck object.
    //Precondition(s): Number of players as an int
    //Postcondition(s): New Deck object
    public Deck(int players) {
        for (int i = 0; i < 13; i++) { //Populates card pile
            for (int j = 0; j < 4; j++) {
                Card c = new Card(i, j);
                if (players != 1) {
                    c.back(); //Sets cards with back up if more than one player
                }
                cardPile.push(c);
            }
        }
        shuffle(); //Shuffles deck
    }

    //This method draws a Card from the Deck.
    //Precondition(s): N/A
    //Postcondition(s): Draws a Card from the card pile, adds it to the discard pile, and returns the Card
    @Override
    public Card draw() {
        Card c = cardPile.pop(); //Removes card from card pile
        discardPile.push(c); //Adds card to discard pile
        return c; //Returns card
    }

    //This method shuffles the Deck.
    //Precondition(s): N/A
    //Postcondition(s): Cards removed from discard pile and added to card pile, card pile shuffled
    @Override
    public void shuffle() {
        cardPile.addAll(discardPile); //Adds discard pile to card pile
        discardPile.clear(); //Removes all from discard pile
        Collections.shuffle(cardPile); //Shuffles card pile
    }
}
