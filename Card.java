
public abstract class Card {
	 private String value;
	    private String color;
	    private String type;

	    public Card(String value, String color, String type) {
	        this.value = value;
	        this.color = color;
	        this.type = type;
	    }

	    public String getValue() {
	        return value;
	    }

	    public String getColor() {
	        return color;
	    }

	    public String getType() {
	        return type;
	    }

	    public void setColor(String color ){
	        this.color=color;
	    }

	    // will use this logic later on i will just leave it here until then
	    public boolean isCompatible(Card comparator) {
	        return (comparator.getType().equals("Wild") ||
	                comparator.getColor().equals(this.color) ||
	                comparator.getValue().equals(this.value));
	    }

	    // to check the value of top card to know which player is the next
	    // this specila card do not allow the next player to play
	    public boolean nextNextPlayer(Card card) {
	        return card.getType().equalsIgnoreCase("skip") ||
	                card.getType().equalsIgnoreCase("draw") ||
	                card.getType().equalsIgnoreCase("wild") ||
	                card.getValue().equals("+2");
	    }

	    // to check if we should to reverse the game

	    public boolean isReverseCard() {
	        return this.getType().equalsIgnoreCase("special") &&
	                this.getValue().equalsIgnoreCase("Reverse");
	    }


	    public abstract void effectCard(Player player, Deck deck,Game game);


	    // to specify the effect of card
	    public int checkCardType() {
	        // Check if the card is a Skip card
	        if (this.getValue().equalsIgnoreCase("Skip")) {
	            return 1;
	        }
	        // Check if the card is a +2 card
	        if (this.getValue().equals("+2")) {
	            return 2;
	        }
	        // Check if the card is a +4 Wild card
	        if (this.getValue().equals("+4") && this.getType().equalsIgnoreCase("Wild")) {
	            return 3;
	        }
	        // If none of the above, return 0
	        return 0;
	    }

	    // cards from 0-9
	    public static class  SimpleCard extends Card {
	        public SimpleCard(String value, String color) {
	            super(value, color, "simple");
	        }

	        @Override

	        public void effectCard(Player player,Deck deck,Game game){
	        // the simple card have no effect 

	        }
	    }

	    // cards like ChangeColor and Draw four
	    public static class WildCard extends Card {
	        public WildCard(String value) {
	            super(value, "none", "wild");
	        }



	        @Override

	        public void effectCard(Player player,Deck deck,Game game){
	        
	        // use the methode of class game 
	        game.wildEffect(player, deck);
	        
	        }
	    }

	    // cards like Skip/Draw two/Reverse
	    public static class SpecialCard extends Card {
	        public SpecialCard(String value, String color) {
	            super(value, color, "special");
	        }
	        @Override
	        public void effectCard(Player player,Deck deck , Game game ){
	        switch (this.getValue()){
	          case "Skip" :       
	               game.skipEffect(player);
	               break;
	          case "Draw" :
	               game.draw2Effect(player, deck);
	               break;
	          case "Reverse" :
	               game.reverseEffect();
	               break;

	           }
	        }
	    }
}
