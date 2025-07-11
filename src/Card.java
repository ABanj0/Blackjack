public class Card {


    private final int color; // color
    private final int value; // value


    public final static int CLUBS = 0;
    public final static int DIAMONDS = 1;
    public final static int HEARTS = 2;
    public final static int SPADES = 3;

    public final static int ACE = 1;
    public final static int JACK = 11;
    public final static int QUEEN = 12;
    public final static int KING = 13;

/*    public enum Suits {
        Clubs(0), Diamonds(1), Hearts(2), Spades(3);

        int SuitValue;

        Suits(int suitValue) {
            this.SuitValue = suitValue;
        }
    }*/


    public enum Color {
        Diamonds, Hearts, Clubs, Spades
    }

    public enum Value {
        Two(2), Three(3), Four(4), Five(5), Six(6), Seven(7),
        Eight(8), Nine(9), Ten(10), Jack(10), Queen(10), King(10), Ace(11);

        int FaceValue;

        Value(int FaceValue) {
            this.FaceValue = FaceValue;
        }
    }


    public Card(int value, int color ){
        this.color = color;
        this.value = value;

    }


    public int getColor(){
        return color;

    }

    public int getValue(){
        return value;
    }

    public String ColorToString(){
        String colorString = "";
        switch ( color ){
            case CLUBS -> {
                return "Clubs"; }
            case SPADES ->{
                return "Spades";}
            case HEARTS ->
                    {return "Hearts";}
            case DIAMONDS ->
                    {return "Diamonds";}
            default -> {return "Error";}
        }

    }


// Make Jack, Queen, King and Ace equal to (1,11,12,13)
    public String ValueToString(){
        String valueString = " ";
        switch (value) {
            case 1 -> {
                return "Ace";
            }
            case 2 -> {
                return "Two";
            }
            case 3 -> {
                return "Three";
            }
            case 4 -> {
                return "Four";
            }
            case 5 -> {
                return "Five";
            }
            case 6 -> {
                return "Six";
            }
            case 7 -> {
                return "Seven";
            }
            case 8 -> {
                return "Eight";
            }
            case 9 -> {
                return "Nine";
            }
            case 10 -> {
                return "Ten";
            }
            case 11 -> {
                return "Jack";
            }
            case 12 -> {
                return "Queen";
            }
            case 13 -> {
                return "King";
            }
        default -> {return "Error";}
        }

    }

    public String toString(){
        return ValueToString() + " of " + ColorToString();
    }



}


