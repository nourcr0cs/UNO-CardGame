

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;



public class Game  {

	 private List<Player> players;
	    private Deck deck;
	    private Card topCard;
	    private Card playedCard;
	    private int numberPlayer11;
	    private int indexOfCurrentPlayer = 0;
	    private Stack<Card> pileOfGame; // Stack to represent the pile of the cards that played
	    


Scanner scanner = new Scanner(System.in);



public Game() {

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

	        Card playedCard = getTopCard();

	        // apply the effect of the played card using the effectCard method
	        playedCard.effectCard(players.get((indexOfCurrentPlayer + 1) % numberPlayer11), deck, this);

	        // chose the next player depending on the effect card
	        switch (getTopCard().checkCardType()) {
	            case 1: // Skip
	                System.out.println("Skip effect activated.");
	                indexOfCurrentPlayer = (indexOfCurrentPlayer + 2) % numberPlayer11; // Skip next player
	                break;
	            case 2: // +2
	                System.out.println("+2 effect activated.");
	                indexOfCurrentPlayer = (indexOfCurrentPlayer + 2) % numberPlayer11; // Skip next player
	                break;
	            case 3: // Wild +4
	                System.out.println("Wild +4 effect activated.");
	                indexOfCurrentPlayer = (indexOfCurrentPlayer + 2) % numberPlayer11; // Skip next player
	                break;
	            case 4: // reverse
	                indexOfCurrentPlayer = (numberPlayer11 - 1 - indexOfCurrentPlayer) % numberPlayer11;
	            default: // Normal play
	                System.out.println("Simple card has been played");
	                indexOfCurrentPlayer = (indexOfCurrentPlayer + 1) % numberPlayer11;
	                break;
	        }

	    }

	    
	    // Game Initialization
	    public void startGame() {
	        players = new ArrayList<>();
	        deck = new Deck();
	        shuffleDeck();
	        dealInitialCards();
	        topCard = deck.drawCard(deck);
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
	        
	        this.topCard = this.deck.drawCard(deck);
	        
	        if (this.topCard == null) {
	            throw new IllegalStateException("Deck is empty. Cannot start the game!");
	        }
	        System.out.println("Deck initialized with cards: " + deck.size());
	        System.out.println("Top card initialized: " + this.topCard);

	    }

	    // Turn Management
	   /* public void playTurn1(Player player) {
	        displayGameState();
	        Card playedCard = getAIAction(player);
	        if (playedCard != null && isMoveValid(playedCard, topCard)) {
	            player.removeCard(playedCard);
	            topCard = playedCard;
	            applyEffectCard(player);
	        } else {
	            drawCard(player);
	        }
	        checkWinner();
	        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	    }*/
      
	    // Gameplay Mechanics
	    public void drawCard(Player player) {
	        Card drawnCard = deck.drawCard(deck);
	        player.addCard(drawnCard);
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

	    // Helper methods
	    public void dealInitialCards() {
	        for (Player player : players) {
	            for (int i = 0; i < 7; i++) {
	                drawCard(player);
	            }
	        }
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
	                        playerList.add(new Human(name)); // Add Human player to the list
	                        System.out.println("Human player '" + name + "' added successfully.");
	                        break;
	                    } else {
	                        System.out.println("Player name cannot be empty. Please try again.");
	                    }
	                } else if (type.equalsIgnoreCase("Ai")) {
	                    // Assign a name to the AI player
	                    name = "Bot " + aiCount++;
	                    playerList.add(new Bot(name)); // Add AI player to the list
	                    System.out.println("AI player '" + name + "' added successfully.");
	                    break;
	                } else {
	                    System.out.println("Invalid type. Please enter 'Human' or 'Ai'.");
	                }
	            }
	        }

	        // Assign the provided playerList to the game's internal list
	        this.players = new ArrayList<>(playerList);

	        System.out.println("All players have been added successfully!");
	        System.out.println("Players information:");
	        for (Player player : players) {
	            System.out.println("- " + player.getName() + " (" + (player instanceof Human ? "Human" : "AI") + ")");
	        }
	    }



// enter the players

/*public void initPlayers() {

    System.out.println("enter the number of players (2-4) :");

    numberPlayer11 = scanner.nextInt();

    // reading the number of players
    while (numberPlayer11 > 4 || numberPlayer11 < 2) {

        System.out.println("enter the number of players (2-4) :");

        numberPlayer11 = scanner.nextInt();
    }

    // read the name and type of player and we store it in array list

    scanner.nextLine(); // Consume the newline character
    int aiCount = 1; // counter for ai names

    for (int i = 0; i < numberPlayer11; i++) {

        String type;
        String name;

        System.out.println("enter the type of player " + (i + 1) + " (Human/Ai):");
        type = scanner.nextLine();

        while (true) {

            type = scanner.nextLine();

            // assign name based on the type
            if (type.equalsIgnoreCase("Human")) {
                // read the name until the user enter a valide choice
                System.out.println("enter the name of the human player " + (i + 1) + ":");
                name = scanner.nextLine();
                this.players.add(new Human(name)); // creat a human player
                break;

            } else if (type.equalsIgnoreCase("Ai")) {
                // for AI automatically assign a name like "Bot 1" "Bot 2"
                name = "Bot " + aiCount++;
                this.players.add(new Bot(name)); // creat a Ai player
                break;
            } else {
                System.out.println("Invalid type. Please enter 'Human' or 'Ai'");
            }
        }

        System.out.println("players added succefuly");
        

    }
}
*/
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
            player.addCard(deck.drawingFromDeck());
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
    Player currentPlayer = getcurrentplayer();

  
    displayGameState();// args : topCard
    if (playedCard != null) {
        topCard = playedCard;
    } else {
        // If no card was played, you might want to draw a new top card
        topCard = deck.drawCard(deck);
        if (topCard == null) {
            // Handle the case when the deck is empty
            shuffleDeck();
            topCard = deck.drawCard(deck);
        }
    }
    // Check if the current player has a playable card
    if (!currentPlayer.hasPlayableCard(topCard)) {
        System.out.println(currentPlayer.getName() + " has no playable card. Drawing one...");
        drawCard(currentPlayer);

    } else {
        // Player has a playable card
        Card selectedCard = currentPlayer.selectCardToPlay(topCard);

        // Validate the action before removing from the hand of current player
        if (!validateAction(currentPlayer, selectedCard)) {
            System.out.println("Action not allowed. Player must draw a card.");
            currentPlayer.drawCard(deck); // draw a card

        } else {
            // play the selected card
            currentPlayer.removeCard(selectedCard);
            pileOfGame.push(selectedCard);
            System.out.println(currentPlayer.getName() + " played " + selectedCard);
        }
    }
}

// determine how is the next player in the game and aplicate the effect of card

public void playCard() {
    Player currentPlayer = getcurrentplayer();

    // call the play turn to play card or draw
    playTurn(currentPlayer);

    // Check if the current player has won the game
    if (checkWinner(currentPlayer)) {
        System.out.println("Congratulations! " + currentPlayer.getName() + " has won the game!");
        System.exit(0); // game over
    }

    // Apply the effect card after playing the card
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
        player.drawCard(deckGame);
        System.out.println(player.getName() + " has drawn 4 cards!");

        // Allow the current player to choose a new color
        String newColor = choseColor();
        System.out.println("The color is now " + newColor + ".");
        // Set the new color on the top card
        getTopCard().setColor(newColor);

    }
}

public void skipEffect(Player player) {

    System.out.println(player.getName() + " has skipped");

}

// check the validation of the card before adding it in the pile card game

public boolean validateAction(Player player, Card card) {
    // Get the top card on the pile
    Card topCard = getTopCard();

    // Check if the card is in the player's hand
    if (player.hasNoCardsLeft()) {
        System.out.println("Invalid action: Card is not in the player's hand.");
        return false;
    }

    // Check if the card is compatible with the top card
    if (isMoveValid(card, topCard)) {
        System.out.println("Invalid action: Card is not compatible with the top card.");
        return false;
    }

    return true;
}

public String choseColor() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Chose the new color to play  ");
    String color = scanner.nextLine();

    while (true) {
        if (color.equalsIgnoreCase("Yellow") || color.equalsIgnoreCase("Red") ||
                color.equalsIgnoreCase("Blue") || color.equalsIgnoreCase("Green")) {
            scanner.close();
            return color;
        } else {
            System.out.println("Invalid color pleas try again chose : Yellow || Red || Green || Blue ");
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


	
