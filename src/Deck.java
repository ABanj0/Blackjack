import javax.swing.*;

public class Deck {
    private int number;
    private Card[] deck= new Card[52];


    public Deck(){
        int i = 0;
        deck = new Card[52];
        for(int value =1; value<=13; value++){ // value
            for(int color = 0; color<=3; color++){   // color
            deck[i] = new Card(value, color);
            i++;


            }
        }
    number = 0;
    }


    public void shuffle(){
        int i;
        int max =52;
        int min = 1;
        int range = max - min;
        for( i = 0; i < 51; i++){
            int randomgen = ((int)(Math.random() * range))+ min;
            Card deck2 = deck[i];
            deck[i] = deck[randomgen];
            deck[randomgen] = deck2;
        }
        number = 0;
    }

    public int numberLeft() {
        return deck.length - number;

    }

    public Card take() {
    if (deck.length == 0){
        throw new IllegalStateException("");
    }
    number++;
    return deck[number - 1];

    }


}
