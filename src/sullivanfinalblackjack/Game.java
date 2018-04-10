package sullivanfinalblackjack;

//Importing libraries
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//This class represents the Game as a JPanel
public class Game extends JPanel {

    //Declaring and initializing fields
    private Shuffleable cards; //Stack of cards
    private JPanel buttonPane; //Panel containing JButtons
    private JPanel handsPane; //Panel containing Hands
    private CardLayout cardLayout = new CardLayout(); //Layout of playersPane
    private DealerHand dealerHand; //Dealer's Hand
    private ArrayList<PlayerHand> playerHands = new ArrayList<>(); //Collection of PlayerHands
    private final List<String> PLAYERNAMES = Arrays.asList("Player 1", "Player 2", "Player 3", "Player 4", "Player 5", "Player 6"); //Collecting of player names
    private JPanel playersPane; //Panel containing PlayerHands
    private PlayerHand currentHand; //Current PlayerHand displayed in 
    private JLabel moneyLabel; //Label displaying current player's money

    //This method constructs a new Game panel.
    //Precondition(s): Number of players as an int, number of decks as an int, money as an int
    //Postcondition(s): New Game panel
    public Game(int players, int decks, int money) {
        if (decks == 1) { //Deck if only using one deck
            cards = new Deck(players);
        } else { //Shoe if using more than one deck
            cards = new Shoe(decks);
        }
        this.setLayout(new BorderLayout());
        handsPane = new JPanel(new GridLayout(2, 1, 10, 10)); //Hands Pane split into DealerHand and PlayerHands
        dealerHand = new DealerHand(cards);
        playersPane = new JPanel(cardLayout);
        for (int i = 0; i < players; i++) { //Adds PlayerHands to playersPane
            PlayerHand p = new PlayerHand(PLAYERNAMES.get(i), money, cards);
            playerHands.add(p);
            playersPane.add(p, PLAYERNAMES.get(i));
        }
        playersPane.setBackground(Color.GRAY);
        handsPane.add(dealerHand);
        handsPane.add(playersPane);
        this.add(handsPane, BorderLayout.CENTER);
        currentHand = playerHands.get(0); //Sets initial hand to Player 1
        createButtonPane(); //Creates buttonPane and adds it to panel
    }

    //This method makes a player stand and ends the hand if all players are standing / busted
    //Precondition(s): Player's hand as PlayerHand
    //Postcondition(s): Changes player hand and potentially ends hand
    private void stand(PlayerHand p) {
        nextHand(); //Moves to next player
        if (playerHands.indexOf(currentHand) == 0) { //If next player is Player 1
            endHand(); //Ends the hand
        }
    }

    //This method creates a new Button panel and adds it to the Game panel
    //Precondition(s): N/A
    //Postcondition(s): Button panel added to Game panel
    private void createButtonPane() {
        buttonPane = new JPanel(new CardLayout());
        JPanel betPane = new JPanel(new GridLayout(1, 2));
        JButton betButton = new JButton("Place Bet"); //Button for placing bet
        betButton.addActionListener(new ActionListener() {

            //This method places a bet and moves to the next player.
            //Precondition(s): Button press as ActionEvent
            //Postcondition(s): Sets the bet and moves to the next hand
            @Override
            public void actionPerformed(ActionEvent e) {
                currentHand.bet();
                betNextHand();
            }
        });
        moneyLabel = new JLabel("Money: $" + currentHand.getMoney()); //Initializes money label
        betPane.add(betButton);
        betPane.add(moneyLabel);
        buttonPane.add(betPane);
        JPanel hitPane = new JPanel(new GridLayout(1, 2));
        JButton hitButton = new JButton("Hit"); //Button for hitting
        hitButton.addActionListener(new ActionListener() {

            //This method hits and checks to see if the player busted.
            //Precondition(s): Button press as ActionEvent
            //Postcondition(s): Hits and moves to the next hand if player busted
            @Override
            public void actionPerformed(ActionEvent e) {
                currentHand.hit();
                if (currentHand.getBusted()) {
                    nextHand();
                    if (playerHands.indexOf(currentHand) == 0) {
                        endHand();
                    }
                }
            }
        });
        JButton standButton = new JButton("Stand"); //Button for standing
        standButton.addActionListener(new ActionListener() {

            //This method makes the current player stand.
            //Precondition(s): N/A
            //Postcondition(s): Current player stands
            @Override
            public void actionPerformed(ActionEvent e) {
                stand(currentHand);
            }
        });
        hitPane.add(hitButton);
        hitPane.add(standButton);
        buttonPane.add(hitPane);
        this.add(buttonPane, BorderLayout.SOUTH); //Adds buttonPane to Game panel
    }

    //This method updates the text of the money label
    //Precondition(s): N/A
    //Postcondition(s): Updated text of money label
    private void updateMoneyLabel() {
        moneyLabel.setText("Money: $" + currentHand.getMoney());
        moneyLabel.revalidate();
    }

    //This method sets a player's bet, draws two cards, and moves to the next hand.
    //      If it is the last player in the order, then it also draws a card for the dealer and changes the card of the button panel.
    //Precondition(s): N/A
    //Postcondition(s): Player's bet set, player's hand drawn, and current player changed
    private void betNextHand() {
        currentHand.hit(); //Player hits twice
        currentHand.hit();
        nextHand(); //Moves to next player's hand
        if (playerHands.indexOf(currentHand) == 0) { //If the next hand is Player 1
            dealerHand.hit(); //Dealer hits
            ((CardLayout) buttonPane.getLayout()).next(buttonPane); //Allows players to hit and stand
        }
    }

    //This method changes the current visible player hand and updates their money label.
    //Precondition(s): N/A
    //Postcondition(s): Next player hand visible with updated money label
    private void nextHand() {
        cardLayout.next(playersPane); //Visible hand changes to next hand
        if (playerHands.indexOf(currentHand) == playerHands.size() - 1) { //If current hand is the last hand in the ArrayList
            currentHand = playerHands.get(0); //Current hand becomes Player 1
        } else { //If current hand is not last
            currentHand = playerHands.get(playerHands.indexOf(currentHand) + 1); //Current hand is Player n + 1
        }
        updateMoneyLabel();
    }

    //This method calculates the winners and losers and clears the hands to start the next hand.
    //Precondition(s): N/A
    //Postcondition(s): Money changed based on wins and losses and next hand starts
    private void endHand() {
        boolean anyStanding = false; //Any hands not busted
        if (cards instanceof Deck) {
            cards.shuffle(); //Shuffles cards if they are a Deck
        }
        for (PlayerHand p : playerHands) {
            if (!p.getBusted()) {
                anyStanding = true; //If any hand is not busted anyStanding is true
            }
        }
        if (anyStanding) {
            dealerHand.finish(); //Dealer finishes hand if at least one player not busted
        }
        if (dealerHand.getBusted()) {
            for (PlayerHand h : playerHands) {
                if (!h.getBusted()) {
                    h.win(); //If dealer busts, all standing players win
                }
            }
        } else { //If dealer does not bust, winners and losers are calculated
            for (PlayerHand h : playerHands) {
                if (!h.getBusted()) {
                    if (h.getValue() > dealerHand.getValue()) {
                        h.win();
                    } else if (h.getValue() == dealerHand.getValue()) {
                        h.tie();
                    } else {
                        h.lose();
                    }
                }
            }
        }
        ((CardLayout) buttonPane.getLayout()).next(buttonPane); //Bet button shown
        clearHands(); //Hands cleared
        if (playerHands.size() > 0) {
            currentHand = playerHands.get(0);
            cardLayout.first(playersPane);
            updateMoneyLabel();
        }
    }

    //This method clears everyone's hand, removes bankrupted players, and ends the game if all players are bankrupt.
    //Precondition(s): N/A
    //Postcondition(s): Clears all hands, players with no money removed, and game over if no player has money.
    private void clearHands() {
        dealerHand.clearHand(); //Clears DealerHand
        ArrayList<PlayerHand> removeList = new ArrayList<>(); //List of PlayerHands to be removed
        for (PlayerHand p : playerHands) { //For all player hands
            p.clearHand(); //Clears player hand
            if (p.getMoney() == 0) {
                removeList.add(p); //Sets player to be removed if money is 0
            }
        }
        //Removing all players with no money
        for (PlayerHand r : removeList) {
            playerHands.remove(r);
            playersPane.remove(r);
        }
        //Ends game if no players left
        if (playerHands.isEmpty()) {
            buttonPane.add(new JPanel(), "end");
            ((CardLayout) buttonPane.getLayout()).show(buttonPane, "end");
            JOptionPane.showMessageDialog(this, "Game Over.  Select 'New Game' from the menu to create a new game.", "GAME OVER!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
