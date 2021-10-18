import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

enum Rank {
    ACE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING;
}

enum Suit {
    HEARTS,
    SPADES,
    DIAMONDS,
    CLUBS;
}

class Card {
        
    private Rank rank;
    private Suit suit;
    
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public void display() {
        System.out.print(this.rank);
        System.out.print(this.suit);
    }

    public int getValue() {
        switch (this.rank) {
            case ACE:
                return 14;
            case TWO:
                return 2;
            case THREE:
                return 3;
            case FOUR:
                return 4;
            case FIVE: 
                return 5;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
                return 10;
            case JACK:
                return 11;
            case QUEEN:
                return 12;
            case KING:
                return 13;
            default:
                return 0;
        }

    }
}

class Deck {

    private ArrayList<Card> deckList;

    public Deck() {
        deckList = new ArrayList<Card>();

        Card currentCard;
        for (Suit s: Suit.values()) {
            for (Rank r: Rank.values()) {
                currentCard = new Card(r, s);
                deckList.add(currentCard);
            }
        }
    }

    public void display() {
        Card currCard;
        int counter = 1;
        for (int i = 0; i < deckList.size(); i++) {
            currCard = deckList.get(i);
            currCard.display();
            System.out.print(" ");
            if (i > 0) {
                if (counter == 13) {
                    System.out.println();
                    counter = 0;
                }
            }
            counter++;
        }
    }

    public void shuffle() {
        Random rand = new Random();
        Card card1;
        for (int i = 0; i < 10000; i++) {
            int generandom = rand.nextInt(deckList.size());
            card1 = deckList.get(generandom);
            deckList.add(0, card1);
            deckList.remove(generandom + 1);
        }
    }

    public Card deal() {
        Card topCard;
        if (!deckList.isEmpty()) {
            topCard = deckList.get(0);
            deckList.remove(0);
            return topCard;
        }
        else {
            return null;
        }
    }

    public int cardsLeft() {
        return deckList.size();
    }

}

class Hand {
    
    private ArrayList<Card> hand;

    public Hand(Deck deck) {
        hand = new ArrayList<Card>();
        if (deck == null) {
            System.out.println("Deck is Null.");
        }
        else {
            for (int i = 0; i < 26; i++) {
            hand.add(deck.deal());
            }
        }
    }

    public Card deal() {
        Card topCard;
        if (!hand.isEmpty()) {
            topCard = hand.get(0);
            hand.remove(0);
            return topCard;
        }
        else {
            return null;
        }
    }

    public void display() {
        Card currCard;
        int counter = 1;
        for (int i = 0; i < hand.size(); i++) {
            currCard = hand.get(i);
            currCard.display();
            System.out.print(" ");
            if (i > 0) {
                if (counter == 13) {
                    System.out.println();
                    counter = 0;
                }
            }
            counter++;
        }
    }

    public int cardsLeft() {
        return hand.size();
    }

    public void placeAtBottom(ArrayList<Card> pot) {
        for (int i = 0; i < pot.size(); i++) {
            hand.add(pot.get(i));
        }
    }

}

public class War {

    public static int mainMenu(Scanner scan) {
        System.out.println("1.) Create New Deck");
        System.out.println("2.) Shuffle Deck");
        System.out.println("3.) Show Deck");
        System.out.println("4.) Play War");
        System.out.println("5.) Exit");
        System.out.print("Enter the number for what you want to do: ");
        int input = scan.nextInt();
        System.out.println();
        return input;
    }

    public static int max(Card c1, Card c2) {
        if (c1.getValue() > c2.getValue()) {
            System.out.println("Player 1 wins.");
            return -1;
        }
        else if (c1.getValue() < c2.getValue()) {
            System.out.println("Player 2 wins.");
            return 1;
        }
        else if (c1.getValue() == c2.getValue()) {
            System.out.println("We have a War!");
            return 0;
        }
        System.out.println("Card comparison function (max) is not working properly.");
        return 2;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to War!");
        Scanner scanner = new Scanner(System.in);
        int menuVal = mainMenu(scanner);
        Deck deck = null;
        while (menuVal != 5) {
            if (menuVal == 1) {
                deck = new Deck();
            }
            else if (menuVal == 2) {
                if (deck == null) {
                    System.out.println("Please create a new deck first!");
                }
                else {
                    deck.shuffle();
                }
            }
            else if (menuVal == 3) {
                if (deck == null) {
                    System.out.println("Please create a new deck first!"); 
                }
                else {
                    deck.display();
                }
            }
            else if (menuVal == 4) {
                if (deck == null) {
                    System.out.println("Please create a new deck first!"); 
                }
                else {
                    int maxBattles;
                    do {
                        System.out.println("Enter the maximum number of battles you'd like to play: ");
                            maxBattles = scanner.nextInt();
                        if (maxBattles < 1) {
                        System.out.println("You must choose a positive integer!");
                        }
                    } while (maxBattles < 1);
                    System.out.println("Dealing 26 cards to each player.");
                    Hand h1 = new Hand(deck);
                    Hand h2 = new Hand(deck);
                    System.out.println("Player 1's hand: ");
                    h1.display();
                    System.out.println("Player 2's hand: ");
                    h2.display();
                    int battleCounter = 0;
                    boolean endGame = false;
                    System.out.print("Press any key to continue... ");
                    scanner.next();
                    ArrayList<Card> pot = new ArrayList<Card>();
                    while ((battleCounter < maxBattles) && (h1.cardsLeft() != 0) && (h2.cardsLeft() != 0) && !endGame) {
                        Card card1, card2;
                        System.out.print("Player 1 plays: ");
                        card1 = h1.deal();
                        pot.add(card1);
                        card1.display();
                        System.out.println();
                        System.out.print("Player 2 plays: ");
                        card2 = h2.deal();
                        pot.add(card2);
                        card2.display();
                        System.out.println();
                        int maximum = max(card1, card2);
                        if (maximum == -1) {
                            h1.placeAtBottom(pot);
                            System.out.println("Player 1 number of cards: " + h1.cardsLeft());
                            System.out.println("Player 2 number of cards: " + h2.cardsLeft());
                            pot.clear();
                        }
                        else if (maximum == 1) {
                            h2.placeAtBottom(pot);
                            System.out.println("Player 1 number of cards: " + h1.cardsLeft());
                            System.out.println("Player 2 number of cards: " + h2.cardsLeft());
                            pot.clear();
                        }
                        else if (maximum == 0) {
                            Card fUP1, fUP2; //FaceUp card variables
                            while (maximum == 0) {
                                if (h1.cardsLeft() < 4 || h2.cardsLeft() < 4) {
                                    endGame = true;
                                    break;
                                }
                                pot.add(h1.deal());
                                pot.add(h1.deal());
                                pot.add(h1.deal());
                                pot.add(h2.deal());
                                pot.add(h2.deal());
                                pot.add(h2.deal());
                                System.out.println("Three cards from each player are now face down.");
                                System.out.print("Player 1 plays: ");
                                fUP1 = h1.deal();
                                pot.add(fUP1);
                                fUP1.display();
                                System.out.println();
                                System.out.print("Player 2 plays: ");
                                fUP2 = h2.deal();
                                pot.add(fUP2);
                                fUP2.display();
                                System.out.println();
                                maximum = max(fUP1, fUP2);
                                if (maximum == -1) {
                                    h1.placeAtBottom(pot);
                                    System.out.println("Player 1 number of cards: " + h1.cardsLeft());
                                    System.out.println("Player 2 number of cards: " + h2.cardsLeft());
                                    pot.clear();
                                }
                                else if (maximum == 1) {
                                    h2.placeAtBottom(pot);
                                    System.out.println("Player 1 number of cards: " + h1.cardsLeft());
                                    System.out.println("Player 2 number of cards: " + h2.cardsLeft());
                                    pot.clear();
                                }
                                else if (maximum == 0) {
                                    System.out.println("Prepare for another War!");
                                }   
                            }
                        }
                        battleCounter++;
                    }
                    if (h1.cardsLeft() == 0) {
                        System.out.println("Player 2 has won the game!");
                    }
                    else if (h2.cardsLeft() == 0) {
                        System.out.println("Player 1 has won the game!");
                    }
                    else if (h1.cardsLeft() > h2.cardsLeft() && endGame) {
                        System.out.println("Player 1 has won the game! Player 2 does not have enough cards for a War!");
                    }
                    else if (h1.cardsLeft() < h2.cardsLeft() && endGame) {
                        System.out.println("Player 2 has won the game! Player 1 does not have enough cards for a War!");
                    }
                    else if (h1.cardsLeft() > h2.cardsLeft()) {
                        System.out.println("Player 1 has won the game!");
                    }
                    else if (h1.cardsLeft() < h2.cardsLeft()) {
                        System.out.println("Player 2 has won the game!");
                    }
                    else if (h1.cardsLeft() == h2.cardsLeft() && battleCounter == maxBattles) {
                        System.out.println("Max Battles have been played and it is a true tie!");
                    }
                    else if (h1.cardsLeft() == h2.cardsLeft()) {
                        System.out.println("Player 2 has won the game!");
                    }
                }
            }
            menuVal = mainMenu(scanner);
        }
        scanner.close(); 
    }    

}
