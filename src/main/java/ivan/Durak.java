package ivan;

import java.util.ArrayList;
import java.util.Scanner;

import static ivan.ConsoleCard.printCards;
import static ivan.Utils.*;

public class Durak {
    private DurakSettings settings;
    private PlayTable playTable;
    private ArrayList<Robot> robots = new ArrayList<>();
    private Human humanPlayer = null;
    private Scanner input = new Scanner(System.in);
    private boolean defenderTookCards = false; // Флаг, что защищающийся взял карты

    private void gameCycle() {
        System.out.println("Козырь:");
        System.out.println(this.playTable.getTrumpCard().getConsoleCard());

        while (!isSomeoneWin(this.playTable.getQueueOfPlayers())) {
            printGameState();

            Player currentPlayer = playTable.activePlayer;

            if (currentPlayer == humanPlayer) {
                humanTurn();
            } else {
                robotTurn((Robot) currentPlayer);
            }

            // Очищаем стол после завершения раунда
            clearTableAfterRound();

            // Добираем карты
            updateCards();

            // Определяем следующего игрока
            determineNextPlayer();

            defenderTookCards = false; // Сбрасываем флаг
        }

        endGame();
    }

    private void determineNextPlayer() {
        if (defenderTookCards) {
            // Если защищающийся взял карты, ходит следующий игрок после него
            int defenderIndex = playTable.getQueueOfPlayers().indexOf(playTable.defendingPlayer);
            int nextIndex = (defenderIndex + 1) % playTable.getQueueOfPlayers().size();
            playTable.activePlayer = playTable.getQueueOfPlayers().get(nextIndex);

            // Защищается следующий игрок после активного
            int defendingIndex = (nextIndex + 1) % playTable.getQueueOfPlayers().size();
            playTable.defendingPlayer = playTable.getQueueOfPlayers().get(defendingIndex);
        } else {
            // Если защита успешна, ходит следующий игрок после атакующего
            int attackerIndex = playTable.getQueueOfPlayers().indexOf(playTable.activePlayer);
            int nextIndex = (attackerIndex + 1) % playTable.getQueueOfPlayers().size();
            playTable.activePlayer = playTable.getQueueOfPlayers().get(nextIndex);

            // Защищается следующий игрок после активного
            int defendingIndex = (nextIndex + 1) % playTable.getQueueOfPlayers().size();
            playTable.defendingPlayer = playTable.getQueueOfPlayers().get(defendingIndex);
        }
    }

    private void printGameState() {
        System.out.println("\n================================");
        System.out.println("Ходит: Игрок " + playTable.activePlayer.getPlayerID() +
                " (защищается: Игрок " + playTable.defendingPlayer.getPlayerID() + ")");
        System.out.println("Карт в колоде: " + playTable.getDeck().getPlayDeck().size());
        System.out.println("================================");
    }

    private void humanTurn() {
        System.out.println("\n=== ВАШ ХОД ===");
        System.out.println("Ваши карты:");
        printPlayerCards(humanPlayer);

        // Атака
        System.out.println("\nВы атакуете игрока " + playTable.defendingPlayer.getPlayerID());

        Card attackCard = chooseAttackCardHuman();
        if (attackCard == null) {
            System.out.println("У вас нет карт для атаки!");
            return;
        }

        playTable.getDeck().addCardToAttDeck(attackCard);
        humanPlayer.removeCard(attackCard);

        System.out.println("Вы положили: ");
        printCards(attackCard.getConsoleCard());

        // Защита робота
        if (playTable.defendingPlayer instanceof Robot) {
            Robot defendingRobot = (Robot) playTable.defendingPlayer;
            boolean defenseSuccessful = handleRobotDefense(defendingRobot, attackCard);

            if (defenseSuccessful) {
                // Подкидывание карт человеком после успешной защиты
                handleHumanAdditionsAfterDefense(defendingRobot);

                // Подкидывание карт другими роботами
                handleRobotAdditionsAfterDefense(defendingRobot);
            }
        }
    }

    private boolean handleRobotDefense(Robot defendingRobot, Card attackCard) {
        System.out.println("\n--- Робот " + defendingRobot.getPlayerID() + " защищается ---");

        ArrayList<Card> attackCards = new ArrayList<>(playTable.getDeck().getAttDeck());
        ArrayList<Card> defenseCards = new ArrayList<>();
        ArrayList<Card> cardsToBeat = new ArrayList<>(attackCards);

        // Робот пытается отбить все карты по порядку
        for (Card cardToBeat : cardsToBeat) {
            Card defenseCard = defendingRobot.chooseDefenseCard(cardToBeat, playTable.getTrumpCard());

            if (defenseCard == null) {
                System.out.println("Робот " + defendingRobot.getPlayerID() + " не может отбить карту:");
                printCards(cardToBeat.getConsoleCard());
                System.out.println("Робот забирает все карты со стола!");

                defendingRobot.addCards(playTable.getDeck().getAttDeck());
                defendingRobot.addCards(playTable.getDeck().getDefDeck());
                playTable.getDeck().getAttDeck().clear();
                playTable.getDeck().getDefDeck().clear();

                defenderTookCards = true; // Защищающийся взял карты
                return false;
            } else {
                System.out.println("Робот отбивает карту:");
                printCards(cardToBeat.getConsoleCard());
                System.out.println("картой:");
                printCards(defenseCard.getConsoleCard());

                playTable.getDeck().addCardToDefDeck(defenseCard);
                defendingRobot.removeCard(defenseCard);
                defenseCards.add(defenseCard);
            }
        }

        System.out.println("Робот успешно отбился!");
        return true;
    }

    private void handleHumanAdditionsAfterDefense(Robot defendingRobot) {
        System.out.println("\n--- Фаза подкидывания ---");

        if (canPlayerAddCards(humanPlayer)) {
            System.out.print("Хотите подкинуть еще карты? (1-да, 0-нет): ");
            int choice = getValidChoice(0, 1);

            while (choice == 1 && canPlayerAddCards(humanPlayer)) {
                System.out.println("Ваши карты:");
                printPlayerCards(humanPlayer);

                System.out.println("Карты на столе:");
                printCards(arrCardsToConsoleCards(playTable.getDeck().getActiveCards()));

                System.out.print("Выберите карту для подкидывания (1-" + humanPlayer.getNumCards() + "): ");
                int cardChoice = getValidChoice(1, humanPlayer.getNumCards());
                Card selectedCard = humanPlayer.getCards().get(cardChoice - 1);

                // Проверяем, можно ли подкинуть эту карту
                boolean canAdd = false;
                for (Card tableCard : playTable.getDeck().getActiveCards()) {
                    if (selectedCard.getNumber() == tableCard.getNumber()) {
                        canAdd = true;
                        break;
                    }
                }

                if (canAdd) {
                    playTable.getDeck().addCardToAttDeck(selectedCard);
                    humanPlayer.removeCard(selectedCard);
                    System.out.println("Вы подкинули карту:");
                    printCards(selectedCard.getConsoleCard());

                    // Робот должен отбить подкинутую карту
                    System.out.println("Робот пытается отбить подкинутую карту...");
                    Card defenseCard = defendingRobot.chooseDefenseCard(selectedCard, playTable.getTrumpCard());

                    if (defenseCard == null) {
                        System.out.println("Робот не может отбить подкинутую карту и забирает все!");
                        defendingRobot.addCards(playTable.getDeck().getAttDeck());
                        defendingRobot.addCards(playTable.getDeck().getDefDeck());
                        playTable.getDeck().getAttDeck().clear();
                        playTable.getDeck().getDefDeck().clear();

                        defenderTookCards = true; // Защищающийся взял карты
                        return; // Выходим из цикла подкидывания
                    } else {
                        System.out.println("Робот отбивает подкинутую карту:");
                        printCards(defenseCard.getConsoleCard());
                        playTable.getDeck().addCardToDefDeck(defenseCard);
                        defendingRobot.removeCard(defenseCard);
                    }
                } else {
                    System.out.println("Эту карту нельзя подкинуть! Выберите карту того же номинала.");
                }

                if (canPlayerAddCards(humanPlayer)) {
                    System.out.print("\nХотите подкинуть еще карты? (1-да, 0-нет): ");
                    choice = getValidChoice(0, 1);
                } else {
                    System.out.println("У вас больше нет подходящих карт для подкидывания.");
                    choice = 0;
                }
            }
        } else {
            System.out.println("У вас нет карт для подкидывания.");
        }
    }

    private void handleRobotAdditionsAfterDefense(Robot defendingRobot) {
        // Другие роботы могут подкидывать только если защищающийся робот успешно отбился
        System.out.println("\nДругие роботы могут подкинуть карты...");

        for (Robot robot : robots) {
            if (robot != defendingRobot && robot != humanPlayer && robot.getNumCards() > 0) {
                if (robot.decideToAddMoreCards(playTable.getDeck().getActiveCards())) {
                    Card cardToAdd = findCardToAdd(robot, playTable.getDeck().getActiveCards());

                    if (cardToAdd != null) {
                        System.out.println("Робот " + robot.getPlayerID() + " подкидывает карту!");
                        printCards(cardToAdd.getConsoleCard());

                        playTable.getDeck().addCardToAttDeck(cardToAdd);
                        robot.removeCard(cardToAdd);

                        // Защищающийся робот должен отбить подкинутую карту
                        System.out.println("Робот " + defendingRobot.getPlayerID() + " пытается отбить подкинутую карту...");
                        Card defenseCard = defendingRobot.chooseDefenseCard(cardToAdd, playTable.getTrumpCard());

                        if (defenseCard == null) {
                            System.out.println("Робот " + defendingRobot.getPlayerID() + " не может отбить подкинутую карту!");
                            defendingRobot.addCards(playTable.getDeck().getAttDeck());
                            defendingRobot.addCards(playTable.getDeck().getDefDeck());
                            playTable.getDeck().getAttDeck().clear();
                            playTable.getDeck().getDefDeck().clear();

                            defenderTookCards = true; // Защищающийся взял карты
                            return;
                        } else {
                            System.out.println("Робот " + defendingRobot.getPlayerID() + " отбивает подкинутую карту:");
                            printCards(defenseCard.getConsoleCard());
                            playTable.getDeck().addCardToDefDeck(defenseCard);
                            defendingRobot.removeCard(defenseCard);
                        }
                    }
                }
            }
        }
    }

    private Card chooseAttackCardHuman() {
        if (humanPlayer.getCards().isEmpty()) {
            return null;
        }

        System.out.print("Выберите карту для атаки (1-" + humanPlayer.getNumCards() + "): ");
        int choice = getValidChoice(1, humanPlayer.getNumCards());
        return humanPlayer.getCards().get(choice - 1);
    }

    private void robotTurn(Robot robot) {
        System.out.println("\n=== ХОДИТ РОБОТ " + robot.getPlayerID() + " ===");

        // Атака робота
        Card attackCard = robot.chooseAttackCard(playTable.getDeck().getActiveCards(), playTable.getTrumpCard());

        if (attackCard == null) {
            System.out.println("У робота нет карт для атаки!");
            return;
        }

        playTable.getDeck().addCardToAttDeck(attackCard);
        robot.removeCard(attackCard);

        System.out.println("Робот " + robot.getPlayerID() + " атакует картой:");
        printCards(attackCard.getConsoleCard());

        // Защита человека
        if (playTable.defendingPlayer == humanPlayer) {
            boolean defenseSuccessful = handleHumanDefenseAgainstRobot(robot, attackCard);

            if (defenseSuccessful) {
                // Робот может подкинуть карты после успешной защиты человека
                handleRobotAdditionsAfterHumanDefense(robot);

                // Другие роботы тоже могут подкинуть
                handleOtherRobotsAdditions(robot);
            }
        } else {
            // Защищается другой робот
            Robot defendingRobot = (Robot) playTable.defendingPlayer;
            boolean defenseSuccessful = handleRobotVsRobotDefense(defendingRobot, attackCard);

            if (defenseSuccessful) {
                // Атакующий робот может подкинуть
                if (robot.decideToAddMoreCards(playTable.getDeck().getActiveCards())) {
                    Card cardToAdd = findCardToAdd(robot, playTable.getDeck().getActiveCards());

                    if (cardToAdd != null) {
                        System.out.println("Робот " + robot.getPlayerID() + " подкидывает карту!");
                        printCards(cardToAdd.getConsoleCard());

                        playTable.getDeck().addCardToAttDeck(cardToAdd);
                        robot.removeCard(cardToAdd);

                        // Защищающийся робот должен отбить
                        Card defenseCard = defendingRobot.chooseDefenseCard(cardToAdd, playTable.getTrumpCard());

                        if (defenseCard == null) {
                            System.out.println("Робот " + defendingRobot.getPlayerID() + " не может отбить подкинутую карту!");
                            defendingRobot.addCards(playTable.getDeck().getAttDeck());
                            defendingRobot.addCards(playTable.getDeck().getDefDeck());
                            playTable.getDeck().getAttDeck().clear();
                            playTable.getDeck().getDefDeck().clear();

                            defenderTookCards = true;
                        } else {
                            System.out.println("Робот " + defendingRobot.getPlayerID() + " отбивает подкинутую карту:");
                            printCards(defenseCard.getConsoleCard());
                            playTable.getDeck().addCardToDefDeck(defenseCard);
                            defendingRobot.removeCard(defenseCard);
                        }
                    }
                }
            }
        }
    }

    private boolean handleHumanDefenseAgainstRobot(Robot attackingRobot, Card attackCard) {
        System.out.println("\n=== ВАША ЗАЩИТА ===");
        System.out.println("Карта для отбивания:");
        printCards(attackCard.getConsoleCard());

        // Человек защищается
        return humanDefense(attackCard);
    }

    private boolean humanDefense(Card attackCard) {
        System.out.println("\nВаши карты:");
        printPlayerCards(humanPlayer);

        ArrayList<Card> beatingCards = getBeatingCards(attackCard, humanPlayer.getCards(), playTable.getTrumpCard());

        if (beatingCards.isEmpty()) {
            System.out.println("Вы не можете отбиться и забираете карты!");
            humanPlayer.addCards(playTable.getDeck().getAttDeck());
            humanPlayer.addCards(playTable.getDeck().getDefDeck());
            playTable.getDeck().getAttDeck().clear();
            playTable.getDeck().getDefDeck().clear();

            defenderTookCards = true; // Защищающийся взял карты
            return false;
        } else {
            System.out.println("Вы можете отбиться следующими картами:");
            for (int i = 0; i < beatingCards.size(); i++) {
                System.out.print((i + 1) + ". ");
                printCards(beatingCards.get(i).getConsoleCard());
            }

            System.out.print("Выберите карту для отбивания (1-" + beatingCards.size() + "): ");
            int choice = getValidChoice(1, beatingCards.size());
            Card selectedCard = beatingCards.get(choice - 1);

            playTable.getDeck().addCardToDefDeck(selectedCard);
            humanPlayer.removeCard(selectedCard);

            System.out.println("Вы отбились картой:");
            printCards(selectedCard.getConsoleCard());
            return true;
        }
    }

    private boolean handleRobotVsRobotDefense(Robot defendingRobot, Card attackCard) {
        System.out.println("\nРобот " + defendingRobot.getPlayerID() + " защищается...");

        Card defenseCard = defendingRobot.chooseDefenseCard(attackCard, playTable.getTrumpCard());

        if (defenseCard == null) {
            System.out.println("Робот " + defendingRobot.getPlayerID() + " не может отбиться и забирает карты!");
            defendingRobot.addCards(playTable.getDeck().getAttDeck());
            defendingRobot.addCards(playTable.getDeck().getDefDeck());
            playTable.getDeck().getAttDeck().clear();
            playTable.getDeck().getDefDeck().clear();

            defenderTookCards = true;
            return false;
        } else {
            System.out.println("Робот " + defendingRobot.getPlayerID() + " отбивается картой:");
            printCards(defenseCard.getConsoleCard());

            playTable.getDeck().addCardToDefDeck(defenseCard);
            defendingRobot.removeCard(defenseCard);
            return true;
        }
    }

    private void handleRobotAdditionsAfterHumanDefense(Robot attackingRobot) {
        if (attackingRobot.decideToAddMoreCards(playTable.getDeck().getActiveCards())) {
            Card cardToAdd = findCardToAdd(attackingRobot, playTable.getDeck().getActiveCards());

            if (cardToAdd != null) {
                System.out.println("Робот " + attackingRobot.getPlayerID() + " подкидывает карту!");
                printCards(cardToAdd.getConsoleCard());

                playTable.getDeck().addCardToAttDeck(cardToAdd);
                attackingRobot.removeCard(cardToAdd);

                // Человек должен отбить подкинутую карту
                System.out.println("Вы должны отбить подкинутую карту!");
                boolean defenseSuccess = humanDefense(cardToAdd);

                if (!defenseSuccess) {
                    return; // Человек взял карты
                }
            }
        }
    }

    private void handleOtherRobotsAdditions(Robot attackingRobot) {
        for (Robot otherRobot : robots) {
            if (otherRobot != attackingRobot && otherRobot != humanPlayer) {
                if (otherRobot.decideToAddMoreCards(playTable.getDeck().getActiveCards())) {
                    Card cardToAdd = findCardToAdd(otherRobot, playTable.getDeck().getActiveCards());

                    if (cardToAdd != null) {
                        System.out.println("Робот " + otherRobot.getPlayerID() + " подкидывает карту!");
                        printCards(cardToAdd.getConsoleCard());

                        playTable.getDeck().addCardToAttDeck(cardToAdd);
                        otherRobot.removeCard(cardToAdd);

                        // Человек должен отбить подкинутую карту
                        System.out.println("Вы должны отбить подкинутую карту!");
                        boolean defenseSuccess = humanDefense(cardToAdd);

                        if (!defenseSuccess) {
                            return; // Человек взял карты
                        }
                    }
                }
            }
        }
    }

    private Card findCardToAdd(Robot robot, ArrayList<Card> tableCards) {
        for (Card robotCard : robot.getCards()) {
            for (Card tableCard : tableCards) {
                if (robotCard.getNumber() == tableCard.getNumber()) {
                    return robotCard;
                }
            }
        }
        return null;
    }

    private boolean canPlayerAddCards(Player player) {
        if (player.getCards().isEmpty()) return false;

        for (Card playerCard : player.getCards()) {
            for (Card tableCard : playTable.getDeck().getActiveCards()) {
                if (playerCard.getNumber() == tableCard.getNumber()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void clearTableAfterRound() {
        // Если количество отбитых карт равно количеству атакующих карт, очищаем стол
        if (playTable.getDeck().getDefDeck().size() == playTable.getDeck().getAttDeck().size()) {
            System.out.println("\nРаунд завершен. Все карты сброшены.");
            playTable.getDeck().getAttDeck().clear();
            playTable.getDeck().getDefDeck().clear();
        }
    }

    private void updateCards() {
        // Сначала добирает карты активный игрок
        while (playTable.activePlayer.getNumCards() < 6 && !playTable.getDeck().getPlayDeck().isEmpty()) {
            Card card = playTable.getDeck().getPlayDeck().remove(0);
            playTable.activePlayer.addCard(card);
        }

        // Потом добирает карты защищающийся игрок
        while (playTable.defendingPlayer.getNumCards() < 6 && !playTable.getDeck().getPlayDeck().isEmpty()) {
            Card card = playTable.getDeck().getPlayDeck().remove(0);
            playTable.defendingPlayer.addCard(card);
        }

        // Затем добирают остальные игроки
        for (Player player : playTable.getPlayers()) {
            if (player != playTable.activePlayer && player != playTable.defendingPlayer) {
                while (player.getNumCards() < 6 && !playTable.getDeck().getPlayDeck().isEmpty()) {
                    Card card = playTable.getDeck().getPlayDeck().remove(0);
                    player.addCard(card);
                }
            }
        }
    }

    private boolean isSomeoneWin(ArrayList<Player> players) {
        // Проверяем, есть ли игроки без карт
        for (Player player : players) {
            if (player.getNumCards() == 0 && playTable.getDeck().getPlayDeck().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void endGame() {
        System.out.println("\n=== ИГРА ОКОНЧЕНА ===");

        // Находим проигравшего (того, у кого остались карты)
        for (Player player : playTable.getPlayers()) {
            if (player.getNumCards() > 0) {
                if (player == humanPlayer) {
                    System.out.println("Вы проиграли! У вас осталось " + player.getNumCards() + " карт.");
                } else {
                    System.out.println("Робот " + player.getPlayerID() + " проиграл!");
                }
            } else {
                if (player == humanPlayer) {
                    System.out.println("Вы выиграли!");
                } else {
                    System.out.println("Робот " + player.getPlayerID() + " выиграл!");
                }
            }
        }
    }

    private int getValidChoice(int min, int max) {
        int choice = -1;
        while (choice < min || choice > max) {
            if (input.hasNextInt()) {
                choice = input.nextInt();
            } else {
                input.next();
            }

            if (choice < min || choice > max) {
                System.out.print("Введите число от " + min + " до " + max + ": ");
            }
        }
        return choice;
    }

    public Durak(DurakSettings settings, int gameType, int robotCount) {
        this.settings = settings;
        ArrayList<Player> players = new ArrayList<>();

        if (gameType == 2) {
            humanPlayer = new Human(0);
            players.add(humanPlayer);

            for (int i = 1; i < settings.getCountOfPlayers(); i++) {
                Robot robot = new Robot(i);
                robots.add(robot);
                players.add(robot);
            }
        } else {
            for (int i = 0; i < settings.getCountOfPlayers(); i++) {
                Robot robot = new Robot(i);
                robots.add(robot);
                players.add(robot);
            }
        }

        this.playTable = new PlayTable(players, this.settings.getCountOfCards());
        this.playTable.initTable();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int gameType = 0, countOfPlayers = 0, countOfCards = 0;

        System.out.println("=== ИГРА В ПОДКИДНОГО ДУРАКА ===");
        System.out.println("Выберите режим игры:");
        System.out.println("2 - человек против роботов");
        System.out.println("3 - роботы против роботов");

        System.out.print("Режим игры (2, 3): ");
        while (gameType != 2 && gameType != 3) {
            gameType = sc.hasNextInt() ? sc.nextInt() : 0;
            if (gameType != 2 && gameType != 3) {
                System.out.print("Режим игры (2, 3): ");
            }
        }

        System.out.print("Выберите кол-во игроков (2-6): ");
        while (countOfPlayers < 2 || countOfPlayers > 6) {
            countOfPlayers = sc.hasNextInt() ? sc.nextInt() : 0;
            if (countOfPlayers < 2 || countOfPlayers > 6) {
                System.out.print("Выберите кол-во игроков (2-6): ");
            }
        }

        System.out.print("Выберите кол-во карт (36, 52, 54): ");
        while (countOfCards != 36 && countOfCards != 52 && countOfCards != 54) {
            countOfCards = sc.hasNextInt() ? sc.nextInt() : 0;
            if (countOfCards != 36 && countOfCards != 52 && countOfCards != 54) {
                System.out.print("Выберите кол-во карт (36, 52, 54): ");
            }
        }

        if (gameType == 2) {
            System.out.println("Будет " + (countOfPlayers - 1) + " робот(ов)");
        }

        DurakSettings settings = new DurakSettings();
        settings.setSettings(countOfPlayers, countOfCards);

        System.out.println("\n=== НАСТРОЙКИ ИГРЫ ===");
        System.out.println(settings.settingsToString());
        System.out.println("\nИгра начинается...");

        Durak durak = new Durak(settings, gameType, countOfPlayers - 1);
        durak.gameCycle();
        sc.close();
    }
}