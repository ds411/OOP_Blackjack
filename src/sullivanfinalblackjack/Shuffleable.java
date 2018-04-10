
package sullivanfinalblackjack;

public interface Shuffleable {
    
    //This method draws and returns a Card from the Shuffleable object.
    //Precondition(s): N/A
    //Postcondition(s): Returns the drawn Card
    public Card draw();
    
    //This method shuffles the Cards in the Shuffleable object.
    //Precondition(s): N/A
    //Postcondition(s): Cards shuffled
    public void shuffle();
}
