
package sullivanfinalblackjack;

//Importing libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

//This class represetns a Card as a JComponent
public class Card extends JComponent {
    
    //Declaring and initializing fields
    private final String[] NUMBERS = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"}; //Possible card numbers
    private final String[] SUITS = {"♥","♦","♣","♠"}; //Possible card suits
    private final String NUMBER; //Card number
    private final String SUIT; //Card suit
    private final String FRONT = "FRONT"; //Front of card
    private final String BACK = "BACK"; //Back of card
    
    //This method constructs a new Card component
    //Precondition(s): Card number as an index of NUMBERS, card suit as an index of SUITS
    //Postcondition(s): New Card component
    public Card(int numIndex, int suitIndex) {
        this.setLayout(new CardLayout());
        this.setPreferredSize(new Dimension(100,140)); //Card size defaults to 100x140 when possible
        this.NUMBER = NUMBERS[numIndex]; //Initializes card number
        this.SUIT = SUITS[suitIndex]; //Initializes card suit
        JPanel front = new JPanel(new BorderLayout()); //Card front
        JLabel back = new JLabel(); //Card back
        try {
            BufferedImage cardBack = ImageIO.read(new File("resources/CARDBACK.png")); //Sets card back to image of card back
            back = new JLabel(new ImageIcon(cardBack));
        }
        catch (IOException e) {
            back.setBackground(Color.BLUE); //If image not avaiable, sets card back to blue
        }
        this.add(front, FRONT);
        this.add(back, BACK);
        JLabel label = new JLabel(NUMBER+SUIT); //Top left label
        JLabel label2 = new JLabel(NUMBER+SUIT, SwingConstants.RIGHT); //Bottom right label
        if(SUIT.equals("♥") || SUIT.equals("♦")) { //Label text red if suit is red
            label.setForeground(Color.RED);
            label2.setForeground(Color.RED);
        }
        front.add(label, BorderLayout.PAGE_START);
        front.add(label2, BorderLayout.PAGE_END);
        this.addMouseListener(new MouseAdapter() {
            
           //This method flips a card when it is pressed
           //Precondition(s): Mouse press as MouseEvent
           //Postcondition(s): Flipped card
           @Override
           public void mousePressed(MouseEvent e) {
               ((Card) e.getSource()).flip();
           }
           
           //This method flips a card when it is released
           //Precondition(s): Mouse release as MouseEvent
           //Postcondition(s): Flipped card
           @Override
           public void mouseReleased(MouseEvent e) {
               ((Card) e.getSource()).flip();
           }
        });        
    }
    
    //This method flips a card.
    //Precondition(s): N/A
    //Postcondition(s): Card flipped to other side
    private void flip() {
        ((CardLayout) this.getLayout()).next(this);
    }
    
    //This method shows the front of the card.
    //Precondition(s): N/A
    //Postcondition(s): Shows card front
    public void front() {
        ((CardLayout) this.getLayout()).show(this, FRONT);
    }
    
    //This method shows the back of the card.
    //Precondition(s): N/A
    //Postcondition(s): Shows card back
    public void back() {
        ((CardLayout) this.getLayout()).show(this, BACK);
    }
    
    //This method gets the values of the card based on the number.
    //Precondition(s): N/A
    //Postcondition(s): Card value as an int
    public int getValue() {
        if(NUMBER.equals("J") || NUMBER.equals("Q") || NUMBER.equals("K")) return 10;
        else if(NUMBER.equals("A")) return 11;
        else return Integer.parseInt(NUMBER);
    } 
}
