package ivan;



import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

import static ivan.ConsoleCard.printCards;


public class Durak {
    private DurakSettings settings;
    private PlayTable playTable;


    private void gameCycle() {
        attackPhase(playTable.activePlayer);
        while (!isSomeoneWin(this.playTable.getQueueOfPlayers())) {

            defencePhase();
            fakePhase();
            updateInfo();
        }
    }

    public void attackPhase(Player player) {
        System.out.println("Вы атакуете. Выберете карту:");
        printCards(player.getConsoleCards());
    }

    public void defencePhase() {

    }

    public void fakePhase() {

    }

    public void updateInfo() {

    }

    public boolean isSomeoneWin(ArrayList<Player> players) {
        for (Player player : players) {
            if (player.getNumCards() == 0) return true;
        }
        return false;
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


    public static void main(String[] args) throws InterruptedException  {
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
        while (countOfPlayers != 2 && countOfPlayers != 3 && countOfPlayers != 4  && countOfPlayers != 5
                && countOfPlayers != 6 && countOfPlayers != 7 && countOfPlayers != 8 && countOfPlayers != 9){
            countOfPlayers = sc.hasNextInt() ? sc.nextInt() : 0;
            if (countOfPlayers != 2 && countOfPlayers != 3 && countOfPlayers != 4  && countOfPlayers != 5
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
