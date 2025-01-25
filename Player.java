
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract  class Player {
	  private String name;
	  private String type; 
	  private Game game;
	    private List<Card> hand;

	    public Player(String name , String type) {
	        this.name = name;
	        this.type = type;
	        this.hand = new ArrayList<>();
	    }
       
	    
	    public String getName() {
	        return this.name;
	    }

	    public  abstract  String getType() ;
      // public abstract void setType();
       
	    public List<Card> getHand() {
	        return this.hand;
	    }

	    public int getHandSize() {
	        return this.hand.size();
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public void addCard(Card drawnCard) {
	        this.hand.add(drawnCard);
	    }

	    public void removeCard(Card card) {
	        this.hand.remove(card);
	    }

	    public boolean hasNoCardsLeft() {
	        return this.hand.isEmpty();
	    }

	    public boolean hasPlayableCard(Card topCard) {
	        for (Card card : this.hand) {
	            if (isMoveValid(card, topCard)) {
	                return true;
	            }
	        }
	        return false;
	    }

		    public boolean isMoveValid(Card card, Card topCard) {
	    	 if (topCard == null) {
	    	        throw new IllegalStateException("Top card is not set!");
	    	    }
	        return card.getColor().equals(topCard.getColor()) || 
	               card.getValue().equals(topCard.getValue()) ||
	               card.getColor().equals("Wild");
	    }
	    
	    
	    
	    public void removeFromHand(Card card) {
	        this.hand.remove(card);
	    }

	    public boolean drawCard(Deck deck) {
	        if (deck.isEmpty()) {
	            return false;
	        }
	        Card card = deck.drawingFromDeck(game.getPileOfGame());
	        this.hand.add(card);
	        return true;
	    }


	    public Card selectCardToPlay(Card topCard) {
	        for (Card card : this.hand) {
	            if (isMoveValid(card, topCard)) {
	                return card;
	            }
	        }
	        return null;
	    }

	  
	 
	   public  abstract Card makeMove (Card topCard) ;

	    public void displayHand() {
	        System.out.println(this.name + "'s hand: " + this.hand);
	    }

	    @Override
	    public String toString() {
	        return "Player{name='" + name + "', hand=" + hand + '}';
	    }
	
//-------------------------------------------------------------------------
	 public  static class Bot extends Player {
	    public  Bot(String name) {
	        super(name, "Bot");
	    }

	    @Override
	    public  String getType() {
	        return "Bot"; // Return the type of player
	    }
    /*@Override 
    public void setType () {

    }*/
	    @Override
	    public Card makeMove(Card topCard) {
	        Card cardToPlay = selectCardToPlay(topCard);
	        if (cardToPlay != null) {
	            // The bot wants to play this card
	            return cardToPlay;
	        } else {
	            // The bot wants to draw a card
	            return null;
	        }
	    }
	   
	    }
//-----------------------------------------------------------------------
	
	 public static class Human extends Player {
		
	    private  final Scanner scanner ;

	    public Human(String name) {
	        super(name, "Human");
	        this.scanner = new Scanner(System.in);
	    }

	    @Override
	    public String getType() {
	        return "Human"; // Return the type of player
	    }

	    @Override
	    public Card makeMove(Card topCard) {
	        while (true) {
	            // Display the current player's hand and the top card
	            System.out.println("\nYour hand:");
	            for (int i = 0; i < getHand().size(); i++) {
	                System.out.println((i + 1) + ": " + getHand().get(i));
	            }
	            System.out.println("Top card: " + topCard);
	            System.out.println("Enter the number of the card you want to play (1-" + getHandSize() + ") or 'draw' to draw a card:");

	            String input = getInput();
	            if (input.equalsIgnoreCase("draw")) {
	                // The player wants to draw a card
	                return null;
	            }

	            try {
	                int cardIndex = Integer.parseInt(input) - 1;
	                if (isValidCardIndex(cardIndex)) {
	                    Card selectedCard = getHand().get(cardIndex);
	                    if (isMoveValid(selectedCard, topCard)) {
	                        // The player wants to play this card
	                        return selectedCard;
	                    } else {
	                        System.out.println("Error: That card cannot be played on " + topCard);
	                    }
	                } else {
	                    System.out.println("Error: Please enter a number between 1 and " + getHandSize());
	                }
	            } catch (NumberFormatException e) {
	                System.out.println("Error: Please enter a number or 'draw'.");
	            }
	        }
	    }
	    
	    
	   
	    
	    
	    
	    
	    
	    private String getInput() {
	        return scanner.nextLine().trim();
	    }

	    private boolean isValidCardIndex(int index) {
	        return index >= 0 && index < getHandSize();
	    }
	}
}

