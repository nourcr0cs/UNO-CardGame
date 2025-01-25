
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

//import cardGame.Bot.Human;



public class Game {

	 private List<Player> players;
	    private Deck deck;
	    private Card topCard;
	    //private Card playedCard;
	    private int numberPlayer11;
	    private int indexOfCurrentPlayer = 0;
	    private Stack<Card> pileOfGame; // Stack to represent the pile of the cards that played
	  //  private Player player ;

       
       public  Stack<Card> getPileOfGame() {
			return pileOfGame;
		}

		public void setPileOfGame(Stack<Card> pileOfGame) {
			this.pileOfGame = pileOfGame;
		}

		public int getPileSize() {
		    return pileOfGame.size();
		}
		public  List<Card> getPileCardsExceptTop() {
		    if (pileOfGame.isEmpty()) {
		        return new ArrayList<>();
		    }
        List<Card> cards = new ArrayList<>(pileOfGame);
		    cards.remove(cards.size() - 1);  // Remove the top card from the list
		    return cards;
		}
		public void clearPileExceptTop() {
		    if (pileOfGame.size() > 1) {
		        Card topCard = pileOfGame.pop(); // Remove and keep the top card
		        pileOfGame.clear();              // Clear all cards from the pile
		        pileOfGame.push(topCard);         // Put the top card back
		    }
		}
		
Scanner scanner = new Scanner(System.in);

public void setTopCard(Card topCard) {
    if (topCard == null) {
        throw new IllegalArgumentException("Top card cannot be null!");
    }
    this.topCard = topCard;
    this.pileOfGame.push(topCard);
    System.out.println("Top card set to: " + topCard);
}

public Game() {
    this.pileOfGame =  new Stack<>();
    this.deck = new Deck();
    players = new ArrayList<>();// array list to store the players

}

public void setDeck(Deck deck) {
    if (deck == null) {
        throw new IllegalArgumentException("Deck cannot be null");
    }
    this.deck = deck;
}

// Getter method for the deck (optional)
public Deck getDeck() {
    return this.deck;
}
// shuffling

public void shuffleDeck() {

    deck.shuffleDeck();
}

public void applyEffectCard(Player currentPlayer) {
    Card playedCard = getTopCard(); // Get the card that was just played

    if (playedCard == null) {
        System.out.println("No card has been played yet. Skipping effect application.");
        return; // Exit if no card was played
    }

    // Apply the effect of the played card
    playedCard.effectCard(currentPlayer, deck, this);

    // Determine the next player based on the card's effect
    switch (playedCard.checkCardType()) {
        case 1: // Skip
            System.out.println("Skip effect activated.");
            indexOfCurrentPlayer = (indexOfCurrentPlayer + 2) % numberPlayer11; // Skip the next player
            break;
        case 2: // +2
            System.out.println("+2 effect activated.");
            indexOfCurrentPlayer = (indexOfCurrentPlayer + 1) % numberPlayer11; // Move to the next player
            break;
        case 3: // Wild +4
            System.out.println("Wild +4 effect activated.");
            indexOfCurrentPlayer = (indexOfCurrentPlayer + 1) % numberPlayer11; // Move to the next player
            break;
        case 4: // Reverse
            System.out.println("Reverse effect activated.");
            reverseEffect(); // Reverse the direction of play
            break;
        default: // Normal play
            System.out.println("No special effect activated.");
            indexOfCurrentPlayer = (indexOfCurrentPlayer + 1) % numberPlayer11; // Move to the next player
            break;
    }
}


	    
	    // Game Initialization
	    public void startGame() {
	        players = new ArrayList<>();
	        deck = new Deck();
	        
	        initPlayers(players);
	        
	       	  shuffleDeck();
	        distributeCards();
	        //dealInitialCards();
	       // topCard = deck.drawCard(deck);
	        if (players == null || players.isEmpty()) {
	            throw new IllegalStateException("Cannot start the game without players!");
	        }
	        System.out.println("Game starting...");
	        System.out.println("Number of players: " + players.size());
	        
	        for (Player player : players) {
	            System.out.println("Player: " + player.getName());
	        }
	        indexOfCurrentPlayer = 0;
	        
	        if (topCard == null) {
	            throw new IllegalStateException("Failed to draw initial top card");
	        }
	        
	       // this.topCard = this.deck.drawCard(deck);
	        // Draw the initial top card and set it correctly
	        
	        this.topCard = deck.drawingFromDeck(pileOfGame);
	        if (this.topCard == null) {
	            throw new IllegalStateException("Deck is empty. Cannot start the game!");
	        }
	        pileOfGame.push(topCard);
	        System.out.println("Deck initialized with cards: " + deck.size());
	        System.out.println("Top card initialized: " + this.topCard);

	    }
//---------------------------------------------------------------------------------------------
	  
	    public void drawCard(Player player) {
	        if (deck.isEmpty()) {
	            System.out.println("Deck is empty. Skipping draw action.");
	            return;  // Avoid crashing the game when deck is empty
	        }
	        Card drawnCard = deck.drawingFromDeck(pileOfGame);
	        player.addCard(drawnCard);
	        System.out.println(player.getName() + " drew a card: " + drawnCard);
	    }
	    
	    public boolean isMoveValid(Card card, Card topCard) {
	    	 if (this.topCard == null) {
	    	        throw new IllegalStateException("Top card is not set!");
	    	    }
	        return card.getColor().equals(topCard.getColor()) || 
	               card.getValue().equals(topCard.getValue()) ||
	               card.getColor().equals("Wild");
	        
	    }

	  
	    public boolean checkWinner(Player currentPlayer) {
	        if (currentPlayer.getHandSize() == 0) {
	            System.out.println(currentPlayer.getName() + " wins the game!");
	            resetGame(); // End the game or reset it
	            return true;
	        }
	        return false;
	    }

	    // Game State Management
	    public void displayGameState() {
	        System.out.println("Top Card: " + topCard);
	        System.out.println("Current Player: " + players.get(indexOfCurrentPlayer).getName());
	        for (Player player : players) {
	            System.out.println(player.getName() + "'s hand size: " + player.getHandSize());
	        }
	    }

	    // Rule Enforcement
	    public void penalizePlayer(Player player) {
	        for (int i = 0; i < 2; i++) {
	            drawCard(player);
	        }
	        System.out.println(player.getName() + " has been penalized with 2 cards.");
	    }

	    // Interactive Console Interface
	    public Card getAIAction(Player aiPlayer) {
	        // This is a simple AI that plays the first valid card it finds
	        for (Card card : aiPlayer.getHand()) {
	            if (isMoveValid(card, topCard)) {
	                return card;
	            }
	        }
	        return null; // If no valid move, return null to draw a card
	    }

	    

	    public void resetGame() {
	        players.clear();
	        deck = new Deck();
	        shuffleDeck();
	        startGame();
	    }

	    public void loadProgress() {
	        // This method would typically load game state from a file or database
	        System.out.println("Loading game progress...");
	        // Implement loading logic here
	    }

	

//---------------------------------------------------------------------------------------
	 
	    public void initPlayers(List<Player> playerList) {
	        if (playerList == null) {
	            throw new IllegalArgumentException("Player list cannot be null!");
	        }

	        System.out.println("Enter the number of players (2-4):");

	        int numberOfPlayers;
	        while (true) {
	            try {
	                numberOfPlayers = scanner.nextInt();
	                if (numberOfPlayers >= 2 && numberOfPlayers <= 4) {
	                    break; // Valid input
	                } else {
	                    System.out.println("Invalid number of players. Please enter a number between 2 and 4:");
	                }
	            } catch (Exception e) {
	                System.out.println("Invalid input. Please enter a valid number:");
	                scanner.nextLine(); // Clear invalid input
	            }
	        }

	        scanner.nextLine(); // Consume the leftover newline character
	        int aiCount = 1; // Counter for AI names

	        for (int i = 0; i < numberOfPlayers; i++) {
	            String type;
	            String name;

	            while (true) {
	                System.out.println("Enter the type of player " + (i + 1) + " (Human/Ai):");
	                type = scanner.nextLine().trim();

	                if (type.equalsIgnoreCase("Human")) {
	                    // Read the name of the human player
	                    System.out.println("Enter the name of Human player " + (i + 1) + ":");
	                    name = scanner.nextLine().trim();
	                    if (!name.isEmpty()) {
	                        playerList.add( new Player.Human(name)); // Add Human player to the list
	                        System.out.println("Human player '" + name + "' added successfully.");
	                        break;
	                    } else {
	                        System.out.println("Player name cannot be empty. Please try again.");
	                    }
	                } else if (type.equalsIgnoreCase("Ai")) {
	                    // Assign a name to the AI player
	                    name = "Bot " + aiCount++;
	                    playerList.add(new Player.Bot(name)); // Add AI player to the list
	                    System.out.println("AI player '" + name + "' added successfully.");
	                    break;
	                } else {
	                    System.out.println("Invalid type. Please enter 'Human' or 'Ai'.");
	                }
	            }
	        }

	        // Assign the provided playerList to the game's internal list
	        this.players = new ArrayList<>(playerList);
            this.numberPlayer11=players.size();
            
	        System.out.println("All players have been added successfully!");
	        System.out.println("Players information:");
	        for (Player player : players) {
	            System.out.println("- " + player.getName() + " (" + (player instanceof Player.Human ? "Human" : "AI") + ")");
	        }
	    }





// display the informaition of each player
public void displayPlayers() {

    System.out.println("players informaitions :");
    for (Player player : players) {

        System.out.println("Name :" + player.getName());
        System.out.println("Type :" + player.getType());

    }

}

// check if i can remove the value numberofdraw from the deck

public boolean checkSize(int numberOfDraw) {

    if (deck.size() > numberOfDraw) {
        return true;
    } else {
        return false;
    }
}

// distribute cards to players


public void distributeCards() {

    // to check if the deck size is enough to deal cards
    if (!checkSize(7 * numberPlayer11)) {

        System.out.println("Not enough cards in the deck to distribute");
        return;
    }

    for (Player player : players) {

        // give 7 cards for each players

        for (int i = 0; i < 7; i++) {

            // use the methode of drawing 1 of class deck 7 times to add 7 cards
            player.addCard(deck.drawingFromDeck(pileOfGame));
        }

    }
    System.out.println("Cards distributed successfully.");

}

// return the index of current player that intialized with 0

public Player getcurrentplayer() {
	 if (players == null || players.isEmpty()) {
	        throw new IllegalStateException("No players available in the game!");
	    }
	 // Ensure currentPlayerIndex is within bounds
	    if (indexOfCurrentPlayer< 0 ||indexOfCurrentPlayer >= players.size()) {
	        throw new IllegalStateException("Current player index is out of bounds!");
	    }
    return players.get(indexOfCurrentPlayer);

}

// get top card to know how we gonna chose the next player

public Card getTopCard() {
	 if (pileOfGame == null || pileOfGame.isEmpty()) {
		 
	        return null; 
	    }
    return pileOfGame.peek();
}


public void playTurn(Player player) {
    Card topCard = getTopCard();

    // Get the player's move
    Card cardToPlay = player.makeMove(topCard);

    if (cardToPlay == null) {
        // The player wants to draw a card
        drawCard(player);
    } else {
        // The player wants to play a card
        if (validateAction(player, cardToPlay)) {
            player.removeFromHand(cardToPlay);
            pileOfGame.push(cardToPlay);
            System.out.println(player.getName() + " played: " + cardToPlay);
        } else {
            System.out.println("Invalid move! Drawing a card instead.");
            drawCard(player);
        }
    }
}

// determine how is the next player in the game and aplicate the effect of card
public void playCard() {
    Player currentPlayer = getcurrentplayer(); // Get the current player

    // Handle the player's turn (play a card or draw a card)
    playTurn(currentPlayer);

    // Check if the current player has won the game
    if (checkWinner(currentPlayer)) {
        System.out.println("Congratulations! " + currentPlayer.getName() + " has won the game!");
        System.exit(0); // End the game
    }

    // Apply the effect of the played card (if any)
    applyEffectCard(currentPlayer);
}








public void reverseEffect() {
    System.out.println("Play direction reversed.");

}

public void draw2Effect(Player player, Deck deckGame) {

    player.drawCard(deckGame);
    player.drawCard(deckGame);
    System.out.println(player.getName() + " has drawn 2 cards!");

}

public void wildEffect(Player player, Deck deckGame) {

    for (int i = 0; i < 4; i++) {
        player.drawCard(deckGame); }
    
        System.out.println(player.getName() + " has drawn 4 cards!");

        // Allow the current player to choose a new color
        String newColor = choseColor();
        System.out.println("The color is now " + newColor + ".");
        // Set the new color on the top card
        //getTopCard().setColor(newColor);
     // Set the new color on the top card
        
        Card topCard = getTopCard();
        if (topCard != null) {
            topCard.setColor(newColor);
        } else {
            System.out.println("Error: No top card available to set the color.");
        }
}

public void skipEffect(Player player) {

    System.out.println(player.getName() + " has skipped");

}

// check the validation of the card before adding it in the pile card game


  public boolean validateAction(Player player, Card card) {
	  
    if (this.topCard == null) {
        System.out.println("Warning: Top card is not set. Initializing...");
        this.topCard = deck.drawingFromDeck(pileOfGame);
        pileOfGame.push(topCard);
    }

    if (!player.getHand().contains(card)) {
        System.out.println("Invalid action: Card is not in the player's hand.");
        return false;
    }

    if (!isMoveValid(card, topCard)) {
        System.out.println("Invalid action: Card is not compatible with the top card.");
        return false;
    }

    return true;
}
 


public String choseColor() {
    System.out.println("Choose a new color to play (Yellow, Red, Blue, Green):");
      String color;
    //Scanner scanner = new Scanner(System.in);
   
    
    
    while (true) {
        color = scanner.nextLine().trim();
        if (color.equalsIgnoreCase("Yellow") || color.equalsIgnoreCase("Red") ||
            color.equalsIgnoreCase("Blue") || color.equalsIgnoreCase("Green")) {
            return color; // Valid color entered
        } else {
            System.out.println("Invalid color. Please try again: Yellow, Red, Blue, or Green.");
        }
    }
}

public void addPlayer(Player player) {

	players.add(player);
	 }


public int getPlayerCount() {
    if (players == null) {
        return 0; //aucun joueur n'est présent
    }
    return players.size(); // Retourne le nombre de joueurs dans la liste
}

}
