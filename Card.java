public class Card {

        private String value;
        private String color;
    
        public Card(String value,String color) {
            this.value = value;
            this.color = color;
        }
    
        public String getValue() {
            return value;
        }
    
        public String getColor() {
            return color;
        }


         /*will use this logic later on i will just leave it here until then
         public boolean isCompatible(Card comparator) {
            return (
              comparator.getColor().equals(this.color) || 
              comparator.getValue().equals(this.value)
            );
          }*/
        
    }
        
        //cards from 0-9
        class SimpleCard extends Card {
            public SimpleCard(String value, String color) {
                super(value, color);
            }
        }
        
        //cards like ChangeColor and Draw four
        class WildCard extends Card {
            public WildCard(String value) {
                super(value, "none");
            }
        }

        // subclass from WildCard
        class Multi extends WildCard {
            public Multi(){
                super("multi");
            }
        }

        class PlusFour extends WildCard {
            public PlusFour(){
                super("+4");
            }
        }

        //cards like Skip/Draw two/Reverse
        class SpecialCard extends Card {
            public SpecialCard(String value, String color) {
                super(value, color);
            }
        }
        
        class Skip extends SpecialCard {
            public Skip (String color) {
                super("Skip", color);
            }
        }

        class Reverse extends SpecialCard {
            public Reverse (String color) {
                super ("Reverse",color);
            }
        }

        class PlusTwo extends SpecialCard {
            public PlusTwo (String color) {
                super("+2", color);
            }
        }





    
    
    
    

