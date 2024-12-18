import java.util.*;


public class Deck{

    //attrib
    private List<Card> deck;
    String[] colors = {"Yellow", "Red", "Blue", "Green"};


    //constructor
    public Deck() {
        this.deck = new ArrayList<>();
        initDeck();
    }


    // in Total we have 96 cards 
    // number 0 : one for each color (4)
    // number 1-9 : two for each color (2*4*9)
    // wild cards : 4/4 (8)
    // special cards : 3 for each color (3*4)


    public void initDeck() {
    //Colored Cards From 0 to 9
    for (int i = 0; i <= 9; i++) {
    this.deck.add(new SimpleCard(Integer.toString(i), "Yellow"));
    this.deck.add(new SimpleCard(Integer.toString(i), "Red"));
    this.deck.add(new SimpleCard(Integer.toString(i), "Blue"));
    this.deck.add(new SimpleCard(Integer.toString(i), "Green"));
    }

    // because 0 should only be one per color we start from one (1-9 x2 per color)
    for (int i = 1; i <= 9; i++) {
    this.deck.add(new SimpleCard(Integer.toString(i), "Yellow"));
    this.deck.add(new SimpleCard(Integer.toString(i), "Red"));
    this.deck.add(new SimpleCard(Integer.toString(i), "Blue"));
    this.deck.add(new SimpleCard(Integer.toString(i), "Green"));
}

    // 4 cards for +4 et switch color
    for (int i = 0; i < 4; i++) {
    this.deck.add(new Multi()); //change color card can be put anytime 
    this.deck.add(new PlusFour());//+4 
}


    // Skip, Reverse, +2
    for (int i = 0; i < 4; i++) {
      String color;
      color = colors[i];
      this.deck.add( new Reverse(color) );
      this.deck.add( new Skip(color) );
      this.deck.add( new PlusTwo(color) );
    }

  }


  // shuffling
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void drawingFromDeck() {
        if (deck.isEmpty()){
            System.out.println("empty deck");
        } else 
        {
            deck.remove(deck.size() - 1);
        }
        }


    public void resetDeck() {
            this.deck.clear(); 
            initDeck();        
            shuffleDeck();    
            }
    }    


