package ivan;

import java.util.ArrayList;

import static ivan.ConsoleCard.printCards;
import static ivan.Utils.arrCardsToConsoleCards;

public class GameStatePrinter {

    public static void printGameState(PlayTable playTable) {
        System.out.println("\n=== ТЕКУЩЕЕ СОСТОЯНИЕ ===");

        // Козырь
        System.out.println("Козырь: ");
        if (playTable.getTrumpCard() != null) {
            printCards(playTable.getTrumpCard().getConsoleCard());
        }

        // Карты на столе
        System.out.println("\nКарты на столе:");
        ArrayList<Card> activeCards = playTable.getDeck().getActiveCards();
        if (activeCards.isEmpty()) {
            System.out.println("  Стол пуст");
        } else {
            printCards(arrCardsToConsoleCards(activeCards));
        }

        // Оставшиеся карты в колоде
        System.out.println("\nКарт в колоде: " + playTable.getDeck().getPlayDeck().size());

        // Состояние игроков
        System.out.println("\nСостояние игроков:");
        for (Player player : playTable.getPlayers()) {
            System.out.print("Игрок " + player.getPlayerID());
            if (player == playTable.activePlayer) {
                System.out.print(" (ходит сейчас)");
            }
            if (player == playTable.defendingPlayer) {
                System.out.print(" (защищается)");
            }
            System.out.println(": " + player.getNumCards() + " карт");
        }
        System.out.println("=====================\n");
    }
}