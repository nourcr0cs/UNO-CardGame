import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Deck{

    // attr
    private List<Card> deck;
    String[] colors = {"Yellow", "Red", "Blue", "Green"};




    //constructor
    public Deck() {
        this.deck = new ArrayList<>();
        initDeck();
    }




    // in Total we have 96 cards 
    // number 0 : one for each color 
    // number 1-9 : two for each color 
    // wild cards : 4/4
    // special cards : 3 for each color


    public void initDeck() {
    //Colored Cards From 0 to 9
    for (int i = 0; i <= 9; i++) {
    this.deck.add(new Card(Integer.toString(i), "Yellow","simple"));
    this.deck.add(new Card(Integer.toString(i), "Red","simple"));
    this.deck.add(new Card(Integer.toString(i), "Blue", "simple"));
    this.deck.add(new Card(Integer.toString(i), "Green", "simple"));
    }

    // because 0 should only be one per color we start from one (1-9 x2 per color)
    for (int i = 1; i <= 9; i++) {
    this.deck.add(new Card(Integer.toString(i), "Yellow","simple"));
    this.deck.add(new Card(Integer.toString(i), "Red","simple"));
    this.deck.add(new Card(Integer.toString(i), "Blue", "simple"));
    this.deck.add(new Card(Integer.toString(i), "Green", "simple"));
}

    // 4 cards for +4 et switch color
    for (int i = 0; i < 4; i++) {
    this.deck.add(new Card("multi", "none","Wild")); //change color card
    this.deck.add(new Card("+4", "none","Wild"));
}


    // Skip, Reverse, +2
    for (int i = 0; i < 4; i++) {
      String color;
      color = colors[i];
      this.deck.add( new Card("Skip", color, "special") );
      this.deck.add( new Card("Reverse", color, "special") );
      this.deck.add( new Card("+2", color, "special") );
    }

  }

}