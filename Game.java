import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Game {





/* 
 
 */








    public void endGame(Player winner) {
        Printer.printEnd(winner);
  }

    public LinkedList<Card> getDeck() {
        return this.deck;
  } 
  
    public List<Player> getPlayers() { 
        return this.players; 
    }

    public ListIterator getPlayersIterator() {
        return this.playersIterator;
    }

    public int getPlayersNum() {
        return this.playersNum;
    }
        public int getSumToDraw() {
            return this.sumToDraw;
    }

    public void resetSumToDraw() {
        //this.sumToDraw = 0;
    }
}
