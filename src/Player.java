import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand; // The cards in the plyaer's hand.




    Player(){
        hand = new ArrayList<>();
    }



    public ArrayList<Card> getHand() {
        return hand;
    }



    public void clear() {
        hand.clear();

    }

    public void add(Card card) {
       if ( card == null){
           throw new NullPointerException("Error");
       }
        else{
            hand.add(card);
       }
    }



    public void remove(Card card) {
       hand.remove(card);
    }



    public void remove(int position) {
      if(position >= 0 && position < hand.size()){
          this.hand.remove(position);
      }
      else{
          throw new IllegalArgumentException("Error");
      }
    }



    public int totalCards() {
       return hand.size();
    }



    public Card getCard(int position) {
      if(position >= 0 && position < hand.size()){
          return hand.get(position);
      }
        else{
            throw new IllegalArgumentException("Error");
      }
    }




    public int getHandTotal() {
        int cardcount;
        int total = 0;
        boolean ace = false;
        for(int i = 0; i< hand.size(); i++) {
            cardcount = hand.get(i).getValue();

        if(cardcount>10) {
            cardcount = 10;
        }
        else if (cardcount == 1){
            ace = true;
        }
        total += cardcount;}

        if (ace && total + 10 <= 21) {
            total += 10;
        }
        return total;





    }


    public void sortByColor() {
        ArrayList<Card> newhand = new ArrayList<>();
        while(hand.size() > 0){
            int position = 0;
            Card card = hand.get(0);
            for(int i = 0; i< hand.size(); i++){
                Card card1 = hand.get(i);
                if( card1.getColor() < card.getColor() || (card1.getColor() == card.getColor() && card1.getValue() < card.getValue())){
                    position = i;
                    card = card1;
                }
            }
        hand.remove(position);
            newhand.add(card);
        }
    hand = newhand;
    }





    public void sortByValue() {
        ArrayList<Card> newhand = new ArrayList<>();
        while(hand.size() > 0){
            int position = 0;
            Card card = hand.get(0);
            for(int i = 0; i< hand.size(); i++){
                Card card1 = hand.get(i);
                if( card1.getValue() < card.getValue() || (card1.getValue() == card.getValue() && card1.getColor() < card.getColor())){
                    position = i;
                    card = card1;
                }
            }
            hand.remove(position);
            newhand.add(card);
        }
        hand = newhand;
    }

}