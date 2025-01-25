import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
      Game unoGame = new Game();

      // Initialize and shuffle the deck
      unoGame.setDeck(new Deck());
      unoGame.shuffleDeck();

      // Add players to the game
      System.out.println("Initializing players...");
      unoGame.initPlayers(new ArrayList<>()); // Create and pass an empty list to initialize players.

      // Distribute cards to players
      System.out.println("Distributing cards...");
      unoGame.distributeCards();

      // Initialize the top card
      Card topCard = unoGame.getDeck().drawingFromDeck();
      if (topCard == null) {
          System.out.println("Error: Deck is empty. Cannot start the game!");
          return;
      }
      System.out.println("Game starts with top card: " + topCard);

      // Main game loop
      while (true) {
          // Current player makes a move
          unoGame.playTurn(unoGame.getcurrentplayer());

          // Check for a winner
          if (unoGame.checkWinner(unoGame.getcurrentplayer())) {
              System.out.println("Game over!");
              break;
          }

          // Move to the next player
          unoGame.playCard();
      }
  }
}