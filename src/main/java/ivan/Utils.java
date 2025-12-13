package ivan;

import java.util.ArrayList;

import static ivan.ConsoleCard.printCards;

public class Utils {

    public static void printTableState(ArrayList<Card> attackCards, ArrayList<Card> defenseCards) {
        System.out.println("\n=== ТЕКУЩЕЕ СОСТОЯНИЕ СТОЛА ===");

        if (attackCards.isEmpty() && defenseCards.isEmpty()) {
            System.out.println("Стол пуст");
            return;
        }

        System.out.println("Атакующие карты:");
        if (attackCards.isEmpty()) {
            System.out.println("  Нет атакующих карт");
        } else {
            printCards(arrCardsToConsoleCards(attackCards));
        }

        System.out.println("\nЗащитные карты:");
        if (defenseCards.isEmpty()) {
            System.out.println("  Нет защитных карт");
        } else {
            printCards(arrCardsToConsoleCards(defenseCards));
        }
        System.out.println("================================\n");
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