package ivan;

import java.util.ArrayList;

import static ivan.ConsoleCard.printCards;

public class Utils {
    public static ArrayList<ConsoleCard> arrCardsToConsoleCards(ArrayList<Card> cards){
        ArrayList<ConsoleCard> consoleCards = new ArrayList<>();
        for (Card card : cards){
            consoleCards.add(card.getConsoleCard());
        }
        return consoleCards;
    }

    public static ArrayList<Card> getBeatingCards(Card attackCard, ArrayList<Card> defenseCards, Card trumpCard) {
        ArrayList<Card> beatingCards = new ArrayList<>();
        for (Card card : defenseCards) {
            if (card.isBeating(attackCard, trumpCard)) {
                beatingCards.add(card);
            }
        }
        return beatingCards;
    }

    public static void printPlayerCards(Player player) {
        System.out.println("Карты игрока " + player.getPlayerID() + ":");
        if (player.getCards().isEmpty()) {
            System.out.println("  Нет карт");
        } else {
            printCards(arrCardsToConsoleCards(player.getCards()));
        }
    }
}