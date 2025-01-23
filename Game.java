

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;



public class Game {

	 private List<Player> players;
	    private Deck deck;
	    private Card topCard;
	    private int currentPlayerIndex;
	    private Deck deckGame11;
	    private int numberPlayer11;
	    private int indexOfCurrentPlayer = 0;
	    private Stack<Card> pileOfGame; // Stack to represent the pile of the cards that played
	    


Scanner scanner = new Scanner(System.in);



public Game() {

    this.deckGame11 = new Deck();
    players = new ArrayList<>();// array list to store the players

}

// shuffling

public void shuffleDeck1() {

    deckGame11.shuffleDeck();
}
	    
	    public void applyEffectCard(Player currentPlayer) {

	        Card playedCard = getTopCard();

	        // apply the effect of the played card using the effectCard method
	        playedCard.effectCard(players.get((indexOfCurrentPlayer + 1) % numberPlayer11), deckGame11, this);

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
	        currentPlayerIndex = 0;
	    }

	    // Turn Management
	    public void playTurn1(Player player) {
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
	    }

	    // Gameplay Mechanics
	    public void drawCard(Player player) {
	        Card drawnCard = deck.drawCard(deck);
	        player.addCard(drawnCard);
	    }

	    public boolean isMoveValid(Card card, Card topCard) {
	        return card.getColor().equals(topCard.getColor()) || 
	               card.getValue().equals(topCard.getValue()) ||
	               card.getColor().equals("Wild");
	    }

	    public void checkWinner() {
	        for (Player player : players) {
	            if (player.getHandSize() == 0) {
	                System.out.println(player.getName() + " wins the game!");
	                resetGame();
	                break;
	            }
	        }
	    }

	    // Game State Management
	    public void displayGameState() {
	        System.out.println("Top Card: " + topCard);
	        System.out.println("Current Player: " + players.get(currentPlayerIndex).getName());
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

	    // Utility Methods
	    public void shuffleDeck() {
	        deck.shuffleDeck();
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
	    private void dealInitialCards() {
	        for (Player player : players) {
	            for (int i = 0; i < 7; i++) {
	                drawCard(player);
	            }
	        }
	    }
 

//---------------------------------------------------------------------------------------



// enter the players

public void initPlayers() {

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

    if (deckGame11.size() > numberOfDraw) {
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
            player.addCard(deckGame11.drawingFromDeck());
        }

    }
    System.out.println("Cards distributed successfully.");

}

// return the index of current player that intialized with 0

public Player getcurrentplayer() {

    return players.get(indexOfCurrentPlayer);

}

// get top card to know how we gonna chose the next player

public Card getTopCard() {

    return pileOfGame.peek();
}

public void playTurn(Player player) {

    Card topCard = getTopCard();
    Player currentPlayer = getcurrentplayer();

    // Display the game state
    displayGameState(topCard);

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
            currentPlayer.drawCard(deckGame11); // draw a card

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
    playTurn1(currentPlayer);

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

public void drawCard1(Player player) {
}

public void displayGameState(Card card) {
}

public boolean checkWinner(Player player) {
    return true;
}

public boolean isMoveValid1(Card card, Card topCard) {
    return true;
}


  
 
