import java.util.*;

public class Card {

        private String value;
        private String color;
        private String type;
    
        public Card(String value,String color,String type ) {
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

         //will use this logic later on i will just leave it here until then
         public boolean isCompatible(Card comparator) {
            return (
              comparator.getType().equals("Wild") ||
              comparator.getColor().equals(this.color) || 
              comparator.getValue().equals(this.value)
            );
          }
        
    }
        
        //cards from 0-9
        class SimpleCard extends Card {
            public SimpleCard(String value, String color) {
                super(value, color, "simple");
            }
        }
        
        //cards like ChangeColor and Draw four
        class WildCard extends Card {
            public WildCard(String value) {
                super(value, "none", "wild");
            }
        }

        //cards like Skip/Draw two/Reverse
        class SpecialCard extends Card {
            public SpecialCard(String value, String color) {
                super(value, color, "special");
            }
        }
        
        
    
    
    
    

