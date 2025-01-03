import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public int getHandSize() {
        return this.hand.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public void removeCard(Card card) {
        this.hand.remove(card);
    }

    public boolean hasNoCardsLeft() {
        return this.hand.isEmpty();
    }

    public boolean hasPlayableCard(Card topCard) {
        for (Card card : this.hand) {
            if (isValidPlay(card, topCard)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isValidPlay(Card card, Card topCard) {
        return card.getColor().equals(topCard.getColor()) || 
               card.getValue().equals(topCard.getValue());
    }

    public void playCard(Card card) {
        this.hand.remove(card);
    }

    public boolean drawCard(Deck deck) {
        if (deck.isEmpty()) {
            return false;
        }
        Card card = deck.drawingFromDeck();
        this.hand.add(card);
        return true;
    }


    public Card selectCardToPlay(Card topCard) {
        for (Card card : this.hand) {
            if (isValidPlay(card, topCard)) {
                return card;
            }
        }
        return null;
    }

    public abstract void makeMove(Card topCard, Deck deck);

    public void displayHand() {
        System.out.println(this.name + "'s hand: " + this.hand);
    }

    @Override
    public String toString() {
        return "Player{name='" + name + "', hand=" + hand + '}';
    }
}

class Bot extends Player {
    public Bot(String name) {
        super(name);
    }

    @Override
    public void makeMove(Card topCard, Deck deck) {
        Card cardToPlay = selectCardToPlay(topCard);
        if (cardToPlay != null) {
            playCard(cardToPlay);
            System.out.println(getName() + " played: " + cardToPlay);
        } else {
            drawCard(deck);
            System.out.println(getName() + " drew a card.");
        }
    }
}

class Human extends Player {
    private final Scanner scanner;

    public Human(String name) {
        super(name);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void makeMove(Card topCard, Deck deck) {
        while (true) {
            displayGameState(topCard);
            String input = getInput();

            if (input.equalsIgnoreCase("draw")) {
                if (drawCard(deck)) {
                    System.out.println("You drew a card.");
                    return;
                }
                System.out.println("Error: The deck is empty.");
                continue;
            }

            try {
                int cardIndex = Integer.parseInt(input) - 1;
                if (isValidCardIndex(cardIndex)) {
                    Card selectedCard = getHand().get(cardIndex);
                    if (isValidPlay(selectedCard, topCard)) {
                        playCard(selectedCard);
                        System.out.println("You played: " + selectedCard);
                        return;
                    }
                    System.out.println("Error: That card cannot be played on " + topCard);
                } else {
                    System.out.println("Error: Please enter a number between 1 and " + getHandSize());
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a number or 'draw'");
            }
        }
    }

    private void displayGameState(Card topCard) {
        System.out.println("\nYour hand: " + getHand());
        System.out.println("Top card: " + topCard);
        System.out.println("Enter card number (1-" + getHandSize() + ") or 'draw':");
    }

    private String getInput() {
        return scanner.nextLine().trim();
    }

    private boolean isValidCardIndex(int index) {
        return index >= 0 && index < getHandSize();
    }
}