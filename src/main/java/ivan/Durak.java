package ivan;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

import static ivan.ConsoleCard.printCards;
import static ivan.Utils.*;


public class Durak {
    private DurakSettings settings;
    private PlayTable playTable;

    private ArrayList<Player> fakePlayers = new ArrayList<>();

    private int N = 0, N2 = 0;
    private int moreCardsFlag = -1;
    private int skipFlag = -1;
    private ArrayList<Card> cards;
    private ArrayList<Card> newCards;
    private Scanner input = new Scanner(System.in);

    private void gameCycle() {
        while (!isSomeoneWin(this.playTable.getQueueOfPlayers())) {
            attackPhase(playTable.activePlayer);
            defencePhase(playTable.activePlayer);
            fakePhase();
            updateInfo();
        }
    }

    public void attackPhase(Player player) {
        System.out.println("Вы атакуете");
        System.out.print("Выберете карту:\n");
        printCards(arrCardsToConsoleCards(player.getCards()));
        while (N > player.getNumCards() || N < 1) {
            N = input.hasNextInt() ? input.nextInt() : 0;
            if (N > player.getNumCards() || N < 1) {
                System.out.print("Выберете карту:\n");
            }
        }

        this.playTable.getDeck().addCardToAttDeck(player.getCards().get(N - 1));
        player.removeCard(player.getCards().get(N - 1));
        int k = 0;
        while (heHasMoreSameCards(player.getCards(), this.playTable.getDeck().getAttDeck())) {
            System.out.print("Положить ещё карту?(0/1)");
            while (moreCardsFlag == -1) {
                moreCardsFlag = input.hasNextInt() ? input.nextInt() : -1;
                if (moreCardsFlag != 1) {
                    System.out.print("Положить ещё карту?(0/1)");
                }
            }
            if (moreCardsFlag == 1) {
                System.out.print("Выберете номер карты:\n");
                printCards(arrCardsToConsoleCards(player.getCards()));
                N = 0;
                while (N > player.getNumCards() || N < 1) {
                    N = input.hasNextInt() ? input.nextInt() : 0;
                    if (N > player.getNumCards() || N < 1) {
                        System.out.print("Выберете номер карты:\n");
                    }
                }
                this.playTable.getDeck().addCardToAttDeck(player.getCards().get(N - 1));
                player.removeCard(player.getCards().get(N - 1));
                k++;
            }
            moreCardsFlag = -1;
        }
        System.out.println("Атака завершена");
        this.playTable.nextPhaseOfGame();
    }

    public void defencePhase(Player player) {
        System.out.println("Вас атакуют этими картами:");
        printCards(arrCardsToConsoleCards(this.playTable.getDeck().getAttDeck()));
        if (youCanSkip(this.playTable.getDeck().getAttDeck(), player.getCards())) {
            System.out.print("Защититься или перевести (0/1):");
            while (skipFlag == -1) {
                skipFlag = input.hasNextInt() ? input.nextInt() : -1;
                if (skipFlag != 0 && skipFlag != 1) {
                    System.out.print("Защититься или перевести (0/1):");
                }
            }
        }
        if (skipFlag == 1) {
            System.out.println("Вы перевели атаку");
        } else {
            N2 = 0;
            if (canPlayerDef(this.playTable.getDeck().getAttDeck(), player.getCards())) {
                int m = 0;
                while (this.playTable.getDeck().getAttDeck().size() > this.playTable.getDeck().getDefDeck().size()) {
                    System.out.printf("Какой картой будете бить данную карту (1-%d):\n", player.getNumCards());
                    System.out.println(this.playTable.getDeck().getAttDeck().get(m).getConsoleCard() + "\n");
                    printCards(arrCardsToConsoleCards(player.getCards()));
                    while (N2 == 0) {
                        N2 = input.hasNextInt() ? input.nextInt() : 0;
                        if (N2 == 0 && player.getCards().get(Math.abs(N2 - 1)).isBeating(this.playTable.getDeck().getAttDeck().get(N - 1), this.playTable.getTrumpCard())) {// чо то не так
                            System.out.printf("Какой картой будете бить (1-%d):\n", player.getNumCards());
                        }
                    }
                    this.playTable.getDeck().addCardToDefDeck(player.getCards().get(N2 - 1));
                }
                System.out.println("Вы отбили атаку");
                player.removeCard(player.getCards().get(N2 - 1));
                if (canAnyPlayerFake(getPlayersWithoutDefPlayer(player), this.playTable.getDeck().getActiveCards())) {
                    this.fakePlayers = getPlayersForFake(getPlayersWithoutDefPlayer(player), this.playTable.getDeck().getActiveCards());
                }
            } else {
                System.out.println("Вы лох");
                this.playTable.activePlayer.addCards(this.playTable.getDeck().getAttDeck());
            }
        }
    }

    public void fakePhase() {
        if (fakePlayers.size() != 0) {
            System.out.println("Фаза подкидывания");
        }
    }

    public void updateInfo() {

    }

    public boolean isSomeoneWin(ArrayList<Player> players) {
        for (Player player : players) {
            if (player.getNumCards() == 0) return true;
        }
        return false;
    }
    public ArrayList<Player> getPlayersForFake(ArrayList<Player> players, ArrayList<Card> activeCards) {
        ArrayList<Player> fakePlayers = new ArrayList<>();
        boolean flag = false;
        for (Player player : players) {
            for (Card card : player.getCards()) {
                for (Card activeCard : activeCards) {
                    if (card.getNumber() == activeCard.getNumber()) {
                        flag = true;
                        fakePlayers.add(player);
                        break;
                    }
                }
                if (flag) break;
            }
        }
        return fakePlayers;
    }
    public boolean canAnyPlayerFake(ArrayList<Player> players, ArrayList<Card> activeCards) {
        for (Player player : players) {
            for (Card card : player.getCards()) {
                for (Card activeCard : activeCards) {
                    if (card.getNumber() == activeCard.getNumber()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public ArrayList<Player> getPlayersWithoutDefPlayer(Player defPlayer) {
        ArrayList<Player> players = new ArrayList<>();
        for (Player player : this.playTable.getQueueOfPlayers()) {
            if (!player.equals(defPlayer)) {
                players.add(player);
            }
        }
        return players;
    }
    public boolean canPlayerDef(ArrayList<Card> attackCards, ArrayList<Card> defCards) {
        int k = 0;
        ArrayList<Card> defCardsCopy = (ArrayList<Card>) defCards.clone();
        for (Card attC : attackCards) {
            for (Card defC : defCardsCopy) {
                if (defC.isBeating(attC, this.playTable.getTrumpCard())) {
                    k++;
                    defCardsCopy.remove(defC);
                    break;
                }
            }
        }
        return k == attackCards.size();
    }

    public Durak(DurakSettings settings) {
        this.settings = settings;
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < this.settings.getCountOfPlayers(); i++) {
            players.add(new Player(i));
        }
        this.playTable = new PlayTable(players, this.settings.getCountOfCards());
        this.playTable.initTable();

    }


    public static void main(String[] args) throws InterruptedException {
        DurakSettings settings = new DurakSettings();
        Scanner sc = new Scanner(System.in);
        int gameType = 0, countOfPlayers = 0, countOfCards = 0, countOfRobots = 0;
        System.out.println("Durak Game");

        System.out.println("Выберите режим игры: \n\t1 - человек против человека\n\t2 - человек портив машины\n\t3 - машина против машины");

        System.out.print("Режим игры(1, 2, 3): ");
        while (gameType != 1 && gameType != 2 && gameType != 3) {
            gameType = sc.hasNextInt() ? sc.nextInt() : 0;
            if (gameType != 1 && gameType != 2 && gameType != 3) {
                System.out.print("Режим игры(1, 2, 3): ");
            }
        }

        System.out.print("Выберите кол-во игроков (2, 3, 4, 5, 6, 7, 8, 9): ");
        while (countOfPlayers != 2 && countOfPlayers != 3 && countOfPlayers != 4 && countOfPlayers != 5
                && countOfPlayers != 6 && countOfPlayers != 7 && countOfPlayers != 8 && countOfPlayers != 9) {
            countOfPlayers = sc.hasNextInt() ? sc.nextInt() : 0;
            if (countOfPlayers != 2 && countOfPlayers != 3 && countOfPlayers != 4 && countOfPlayers != 5
                    && countOfPlayers != 6 && countOfPlayers != 7 && countOfPlayers != 8 && countOfPlayers != 9) {
                System.out.print("Выберите кол-во игроков (2, 3, 4, 5, 6, 7): ");
            }
        }

        if (countOfPlayers == 9) {
            countOfCards = 54;
            System.out.print("Карт - 54 ");
        } else {
            System.out.print("Выберите кол-во карт (32, 52, 54): ");
            while (countOfCards != 32 && countOfCards != 52 && countOfCards != 54) {
                countOfCards = sc.hasNextInt() ? sc.nextInt() : 0;
                if (countOfCards != 32 && countOfCards != 52 && countOfCards != 54) {
                    if (countOfPlayers > 6) {
                        System.out.print("Выберите кол-во карт (52, 54): ");
                    } else {
                        System.out.print("Выберите кол-во карт (32, 52, 54): ");
                    }
                }
            }
        }
        if (gameType == 2) {
            System.out.printf("Сколько роботов: (1 - %d): ", countOfPlayers - 1);
            while (countOfRobots == 0) {
                countOfRobots = sc.hasNextInt() ? sc.nextInt() : 0;
                if (countOfRobots == 0) {
                    System.out.printf("Сколько роботов: (1 - %d): ", countOfPlayers - 1);
                }
            }
        }
        settings.setSettings(countOfPlayers, countOfCards);
        System.out.println(settings.settingsToString());
        System.out.println("\rИгра началась.");

        Durak durak = new Durak(settings);
        durak.gameCycle();
    }
}
