package ivan;



public class Utils {

    public static boolean isCardsAllNull(Card[] cards){
        for (Card card : cards){
            if (card != null) return false;
        }
        return true;
    }
}
