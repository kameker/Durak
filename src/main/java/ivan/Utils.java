package ivan;


import java.util.ArrayList;

public class Utils {

    public static boolean isCardsAllNull(Card[] cards){
        for (Card card : cards){
            if (card != null) return false;
        }
        return true;
    }
    public static boolean heHasMoreSameCards(ArrayList<Card> cards, ArrayList<Card> newCards){
        for (Card card : newCards){
            for (Card card2 : cards){
                if (card.getNumber() == card2.getNumber()) return true;
            }
        }
        return false;
    }
    public static boolean youCanSkip(ArrayList<Card> cards, ArrayList<Card> cards2){
        for (Card card : cards){
            for (Card card2 : cards2){
                if (card.getNumber() == card2.getNumber()) return true;
            }
        }
        return false;
    }
    public static ArrayList<ConsoleCard> arrCardsToConsoleCards(ArrayList<Card> cards){
        ArrayList<ConsoleCard> consoleCards = new ArrayList<>();
        for (Card card : cards){
            consoleCards.add(card.getConsoleCard());
        }
        return consoleCards;
    }
}
