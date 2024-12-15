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
    
    

