
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Uno {

	 
	    //================================================================
	public static void main(String[] args) {
	    Game unoGame = new Game();
	    unoGame.setDeck(new Deck());
	    unoGame.shuffleDeck();

	    System.out.println("Initializing players...");
	    unoGame.initPlayers(new ArrayList<>());

	    System.out.println("Distributing cards...");
	    unoGame.distributeCards();

	    // Ensure top card is drawn before starting
	    Card topCard = unoGame.getDeck().drawingFromDeck(unoGame.getPileOfGame());
	    unoGame.setTopCard(topCard);
	    //unoGame.pileOfGame.push(topCard);

	    if (topCard == null) {
	        System.out.println("Error: Deck is empty. Cannot start the game!");
	        return;
	    }

	    System.out.println("Game starts with top card: " + topCard);

	    while (true) {
	        unoGame.playTurn(unoGame.getcurrentplayer());

	        if (unoGame.checkWinner(unoGame.getcurrentplayer())) {
	            System.out.println("Game over!");
	            break;
	        }

	        unoGame.playCard();
	    }
	}
	}

	


	   
	
	    


